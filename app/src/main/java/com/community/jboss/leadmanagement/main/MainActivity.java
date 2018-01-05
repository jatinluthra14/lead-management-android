package com.community.jboss.leadmanagement.main;


import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.community.jboss.leadmanagement.PermissionManager;
import com.community.jboss.leadmanagement.R;
import com.community.jboss.leadmanagement.SettingsActivity;
import com.community.jboss.leadmanagement.main.contacts.ContactsFragment;
import com.community.jboss.leadmanagement.main.contacts.editcontact.EditContactActivity;
import com.community.jboss.leadmanagement.main.contacts.importcontact.ImportContactActivity;
import com.community.jboss.leadmanagement.main.groups.GroupsFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int ID = 512;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private MainActivityViewModel mViewModel;
    private PermissionManager permissionManager;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mSignInClient;
    private SignInButton signInButton;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        mViewModel.getSelectedNavItem().observe(this, this::displayNavigationItem);

        permissionManager = new PermissionManager(this, this);

        try{
            FirebaseApp.initializeApp(this, new FirebaseOptions.Builder()
                    .setApiKey("AIzaSyCKgYMhe6jTk7_B8_U9tevbKMNO6WUI6Ok")
                    .setApplicationId("1:327376189668:android:78ce05f231d482c3")
                    .setDatabaseUrl("https://lead-management-android.firebaseio.com")
                    .setGcmSenderId("327376189668")
                    .setStorageBucket("dev-sand-dee96.appspot.com").build());

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("327376189668-tga30up0ovg9v7gm3bqmfaf1cip0rvk4.apps.googleusercontent.com")
                    .requestEmail()
                    .build();
            mSignInClient = GoogleSignIn.getClient(this, gso);
            mAuth = FirebaseAuth.getInstance();
        }catch (IllegalStateException ignored){

        }
        
        if (!permissionManager.permissionStatus(Manifest.permission.READ_PHONE_STATE)) {
            permissionManager.requestPermission(ID, Manifest.permission.READ_PHONE_STATE);
        }

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Set initial selected item to Contacts
        if (savedInstanceState == null) {
            selectInitialNavigationItem();
        }

        initFab();

        signInButton = navigationView.getHeaderView(0).findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signOutButton = navigationView.getHeaderView(0).findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    @Override
     public void onStart() {
                super.onStart();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 105) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, task2 -> {
                            if (task2.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                updateUI(null);
                            }
                        });
            } catch (Exception ignored) {

            }
        }

    }


    private void signOut() {
        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
            updateUI(null);
        }
    }

    private void updateUI(FirebaseUser user) {
        TextView userText = navigationView.getHeaderView(0).findViewById(R.id.userText);
        TextView emailText = navigationView.getHeaderView(0).findViewById(R.id.emailText);

        if (user != null) {
            userText.setText(user.getDisplayName());
            emailText.setText(user.getEmail());
            signInButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            userText.setText("Signed Out");
            emailText.setText("");
            signInButton.setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }
    }

    private void signIn() {
        Intent signInIntent = mSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 105);
    }

    private void selectInitialNavigationItem() {
        final @IdRes int initialItem = R.id.nav_contacts;
        onNavigationItemSelected(navigationView.getMenu().findItem(initialItem));
        navigationView.setCheckedItem(initialItem);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }else if( id == R.id.action_import ){
            if(permissionManager.permissionStatus(Manifest.permission.READ_CONTACTS)){
                startActivity(new Intent(MainActivity.this,ImportContactActivity.class));
            }else{
                permissionManager.requestPermission(109,Manifest.permission.READ_CONTACTS);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final MainActivityViewModel.NavigationItem navigationItem;
        switch (item.getItemId()) {
            case R.id.nav_contacts:
                navigationItem = MainActivityViewModel.NavigationItem.CONTACTS;
                break;
            case R.id.nav_groups:
                navigationItem = MainActivityViewModel.NavigationItem.GROUPS;
                break;
            case R.id.nav_settings:
                navigationItem = MainActivityViewModel.NavigationItem.SETTINGS;
                break;
            default:
                Timber.e("Failed to resolve selected navigation item id");
                throw new IllegalArgumentException();

        }
        mViewModel.setSelectedNavItem(navigationItem);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayNavigationItem(MainActivityViewModel.NavigationItem navigationItem) {
        MainFragment newFragment;

        switch (navigationItem) {
            case CONTACTS:
                newFragment = new ContactsFragment();
                break;
            case GROUPS:
                newFragment = new GroupsFragment();
                break;
            case SETTINGS:
                startActivity(new Intent(this, SettingsActivity.class));
                return;
            default:
                Timber.e("Failed to resolve selected NavigationItem");
                throw new IllegalArgumentException();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, newFragment)
                .commit();
        setTitle(newFragment.getTitle());
    }

    public void initFab() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment instanceof ContactsFragment) {
            fab.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), EditContactActivity.class)));
            fab.setImageResource(R.drawable.ic_add_white_24dp);
        }
    }
}
