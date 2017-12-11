package com.community.jboss.leadmanagement.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.jboss.leadmanagement.Classes.Contact;
import com.community.jboss.leadmanagement.R;

import java.util.List;

/**
 * Created by carbonyl on 10/12/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView contact_name;
        ImageView contact_avatar;

        ViewHolder(View view) {
            super(view);
            contact_name = view.findViewById(R.id.contact_name);
            contact_avatar = view.findViewById(R.id.contact_avatar);
        }
    }

    public ContactAdapter(List<Contact> myDataset) {
        contacts = myDataset;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_cell, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Contact contact = contacts.get(position);
        holder.contact_name.setText(contact.getName());

        //TODO Avatar
//        holder.contact_avatar.setImageResource(contact.getAvatar);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
