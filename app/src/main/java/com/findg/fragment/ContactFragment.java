package com.findg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.findg.R;
import com.findg.data.model.Contact;

/**
 * Created by samuelhsin on 2015/10/25.
 */
public class ContactFragment extends BaseFragment {

    private Contact contacts;

    public ContactFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    private void initAllUsers() {
        contacts = this.getDatabaseHelper().getSelf(getUserSn()).getContacts();
    }

}
