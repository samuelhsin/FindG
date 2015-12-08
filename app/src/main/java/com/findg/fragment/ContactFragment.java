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
import com.findg.data.model.Friend;
import com.findg.utils.KeyboardUtils;
import com.findg.component.ContactItemListAdapter;
import com.findg.component.ContactItemOperationListener;
import com.findg.data.model.Contact;

import java.util.Collection;

/**
 * Created by samuelhsin on 2015/10/25.
 */
public class ContactFragment extends BaseFragment implements AbsListView.OnScrollListener {

    private SearchView searchView;

    private String constraint;

    /* contact item list */
    private int page = -1; // first loading
    private ExpandableListView contactItemListView;
    private LinearLayout listLoadingView;
    private TextView emptyListTextView;

    /* contact item list adapter*/
    private ContactItemListAdapter contactItemListAdapter;

    private Contact favorite;
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

        initContactList();

        return view;
    }

    private void initUI(View view) {
        contactItemListView = (ExpandableListView) view.findViewById(R.id.contacts_expandablelistview);
        listLoadingView = (LinearLayout) getBaseFragmentActivity().getLayoutInflater().inflate(R.layout.view_load_more, null);
        contactItemListView.addFooterView(listLoadingView);
        emptyListTextView = (TextView) view.findViewById(R.id.empty_list_textview);
    }

    private void initListeners() {
        contactItemListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtils.hideKeyboard(getBaseFragmentActivity());
                return false;
            }
        });

        contactItemListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // nothing do
                return true;
            }
        });

        contactItemListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Friend friend = (Friend) contactItemListAdapter.getChild(groupPosition, childPosition);
                return false;
            }
        });
    }

    private void initContactItemListAdapter() {
        //sortLists();

        ContactItemOperationListener contactItemOperationListener = new ContactItemOperationListener() {
            @Override
            public void onAddUserClicked(long userId) {

            }
        };
        contactItemListAdapter = new ContactItemListAdapter(getBaseFragmentActivity(), contactItemOperationListener, contact);
        contactItemListAdapter.setSearchCharacters(constraint);
        contactItemListView.setAdapter(contactItemListAdapter);
        contactItemListView.setGroupIndicator(null);
        contactItemListView.setOnScrollListener(this);

        expandAll();
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
        searchView.setMaxWidth(Integer.MAX_VALUE);

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

    private void initContactList() {
        //friendGroupList.clear();
        initFavorite();
        initContact();
        initContactItemListAdapter();
    }

    private void initFavorite() {

    }

    private void initContact() {
        Collection collection = this.getDatabaseHelper().getSelf(getUserSn()).getContacts();
        if (!collection.isEmpty()) {
            contact = (Contact) collection.toArray()[0];
        }
    }

    private void expandAll() {
        int count = contactItemListAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            contactItemListView.expandGroup(i);
        }
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

            constraint = query;

            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {

            constraint = query;

            return true;
        }
    }

}
