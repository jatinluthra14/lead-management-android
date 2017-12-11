package com.community.jboss.leadmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.community.jboss.leadmanagement.Classes.Contact;
import com.community.jboss.leadmanagement.Classes.Number;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by carbonyl on 10/12/2017.
 */

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.add_contact_toolbar) Toolbar toolbar;
    @BindView(R.id.contact_name_field) EditText contactNameField;
    @BindView(R.id.contact_number_field) EditText contactNumberField;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                createContact();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_contact, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_contact);

        ButterKnife.bind(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_black_24dp));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //TODO Add multiple numbers
    private void createContact() {

        String _name = contactNameField.getText().toString();
        String _number = contactNumberField.getText().toString();

        Contact contact = new Contact(_name);
        contact.save();

        Number number = new Number(_number, contact);
        number.save();

        this.finish();
    }
}
