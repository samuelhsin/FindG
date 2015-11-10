package com.findg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.*;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.findg.R;
import com.findg.component.ContactItemListAdapter;
import com.findg.component.ContactItemOperationListener;
import com.findg.data.model.Contact;

/**
 * Created by samuelhsin on 2015/10/25.
 */
public class ContactFragment extends BaseFragment implements AbsListView.OnScrollListener {

    private SearchView searchView;

    /* contact item list */
    private int page = -1; // first loading
    private ExpandableListView contactItemListView;
    private LinearLayout listLoadingView;
    private TextView emptyListTextView;

    /* contact item list adapter*/
    private ContactItemListAdapter contactItemListAdapter;

    private Contact contact;

    public ContactFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        contactItemListView = (ExpandableListView) view.findViewById(R.id.contacts_expandablelistview);
        listLoadingView = (LinearLayout) getBaseFragmentActivity().getLayoutInflater().inflate(R.layout.view_load_more, null);
        contactItemListView.addFooterView(listLoadingView);
        emptyListTextView = (TextView) view.findViewById(R.id.empty_list_textview);
    }

    private void initContactItemListAdapter() {
        //sortLists();

        ContactItemOperationListener contactItemOperationListener = new ContactItemOperationListener() {
            @Override
            public void onAddUserClicked(int userId) {

            }
        };
        contactItemListAdapter = new ContactItemListAdapter(getBaseFragmentActivity(), contactItemOperationListener, contact);
        //contactItemListAdapter.setSearchCharacters(constraint);
        // contactItemListView.setAdapter(friendsListAdapter);
        // contactItemListView.setGroupIndicator(null);
        // contactItemListView.setOnScrollListener(this);

        // expandAll();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (page == -1) {
            contactItemListView.removeFooterView(listLoadingView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friend_list_menu, menu);
        SearchOnActionListener searchOnActionListener = new SearchOnActionListener();
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(searchOnActionListener);

        //shared action
        // ShareActionProvider share = new ShareActionProvider(getContext());
        // share.setShareIntent(createShareIntent());
        // MenuItemCompat.setActionProvider(searchItem, share);
        //ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(searchItem);
        // if (share != null) {
        //     share.setOnShareTargetSelectedListener(searchOnActionListener);
        // }

    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "this text will be shared");
        return shareIntent;
    }

    private void initAllUsers() {
        contact = this.getDatabaseHelper().getSelf(getUserSn()).getContact();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private class SearchOnActionListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

}
