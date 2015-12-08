package com.findg.component;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.findg.R;
import com.findg.common.Consts;
import com.findg.data.model.Contact;
import com.findg.data.model.Friend;
import com.findg.data.model.FriendGroup;
import com.findg.data.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ContactItemListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<FriendGroup> friendGroups;
    private Contact originalContact;
    private ContactItemOperationListener contactItemOperationListener;
    private LayoutInflater layoutInflater;
    private Resources resources;
    private String searchCharacters;

    public ContactItemListAdapter(Context context, ContactItemOperationListener friendOperationListener, Contact contact) {
        this.context = context;
        this.contactItemOperationListener = friendOperationListener;
        this.friendGroups = (contact.getFriendGroups() != null) ? new ArrayList<>(contact.getFriendGroups()) : new ArrayList<FriendGroup>();
        this.originalContact = contact;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resources = context.getResources();
    }

    public void setSearchCharacters(String searchCharacters) {
        this.searchCharacters = searchCharacters;
    }

    private void displayAvatarImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, Consts.UIL_USER_AVATAR_DISPLAY_OPTIONS);
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return friendGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        FriendGroup friendGroup = friendGroups.get(groupPosition);
        if (friendGroup != null && friendGroup.getFriends() != null) {
            List<Friend> friends = new ArrayList<>(friendGroup.getFriends());
            return friends.get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FriendGroup friendGroup = (FriendGroup) getGroup(groupPosition);

        //By default the group is hidden
        View hiddenView = new FrameLayout(context);

        if (friendGroup.getFriends().isEmpty()) {
            return hiddenView;
        } else {
            convertView = layoutInflater.inflate(R.layout.view_section_title_friends_list, null);
        }

        TextView headerName = (TextView) convertView.findViewById(R.id.list_title_textview);
        headerName.setText("Test Friend Group");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        Friend friend = (Friend) getChild(groupPosition, childPosition);
        User user = friend != null ? friend.getUser() : null;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_friend, null);

            viewHolder = new ViewHolder();

            viewHolder.avatarImageView = (RoundedImageView) view.findViewById(R.id.avatar_imageview);
            viewHolder.fullNameTextView = (TextView) view.findViewById(R.id.name_textview);
            viewHolder.statusTextView = (TextView) view.findViewById(R.id.status_textview);
            viewHolder.addFriendImageView = (ImageView) view.findViewById(R.id.add_friend_imagebutton);
            viewHolder.onlineImageView = (ImageView) view.findViewById(R.id.online_imageview);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (user != null) {
            if (user.getName() != null) {
                viewHolder.fullNameTextView.setText(user.getName());
            } else {
                viewHolder.fullNameTextView.setText("Unknown");
            }

            //String avatarUrl = getAvatarUrlForUser(user);
            //displayAvatarImage(avatarUrl, viewHolder.avatarImageView);

            //FriendGroup friendGroup = (FriendGroup) getGroup(groupPosition);
            //checkVisibilityItems(viewHolder, user, friendGroup);

            initListeners(viewHolder, user.getId());

            if (!TextUtils.isEmpty(searchCharacters)) {
                TextViewHelper.changeTextColorView(context, viewHolder.fullNameTextView, searchCharacters);
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void initListeners(ViewHolder viewHolder, final long userId) {
        viewHolder.addFriendImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactItemOperationListener.onAddUserClicked(userId);
            }
        });
    }

    //    private String getAvatarUrlForUser(User user) {
//        return user.getAvatarUrl();
//    }
//
//    @Override
//    public int getGroupCount() {
//        return friendGroupList.size();
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        List<User> userList = friendGroupList.get(groupPosition).getUserList();
//        return userList.size();
//    }
//
//    @Override
//    public Object getGroup(int groupPosition) {
//        return friendGroupList.get(groupPosition);
//    }
//
//    @Override
//    public Object getChild(int groupPosition, int childPosition) {
//        List<User> userList = friendGroupList.get(groupPosition).getUserList();
//        return userList.get(childPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return true;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
//        FriendGroup friendGroup = (FriendGroup) getGroup(groupPosition);
//
//        //By default the group is hidden
//        View hiddenView = new FrameLayout(context);
//
//        if (friendGroup.getUserList().isEmpty()) {
//            return hiddenView;
//        } else {
//            view = layoutInflater.inflate(R.layout.view_section_title_friends_list, null);
//        }
//
//        TextView headerName = (TextView) view.findViewById(R.id.list_title_textview);
//        headerName.setText(friendGroup.getHeaderName());
//
//        return view;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
//                             ViewGroup parent) {
//        ViewHolder viewHolder;
//        User user = (User) getChild(groupPosition, childPosition);
//
//        if (view == null) {
//            view = layoutInflater.inflate(R.layout.list_item_friend, null);
//
//            viewHolder = new ViewHolder();
//
//            viewHolder.avatarImageView = (RoundedImageView) view.findViewById(R.id.avatar_imageview);
//            viewHolder.fullNameTextView = (TextView) view.findViewById(R.id.name_textview);
//            viewHolder.statusTextView = (TextView) view.findViewById(R.id.status_textview);
//            viewHolder.addFriendImageView = (ImageView) view.findViewById(R.id.add_friend_imagebutton);
//            viewHolder.onlineImageView = (ImageView) view.findViewById(R.id.online_imageview);
//
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        if (user.getFullName() != null) {
//            viewHolder.fullNameTextView.setText(user.getFullName());
//        } else {
//            viewHolder.fullNameTextView.setText(user.getUserId());
//        }
//
//        String avatarUrl = getAvatarUrlForUser(user);
//        displayAvatarImage(avatarUrl, viewHolder.avatarImageView);
//
//        FriendGroup friendGroup = (FriendGroup) getGroup(groupPosition);
//        checkVisibilityItems(viewHolder, user, friendGroup);
//
//        initListeners(viewHolder, user.getUserId());
//
//        if (!TextUtils.isEmpty(searchCharacters)) {
//            TextViewHelper.changeTextColorView(context, viewHolder.fullNameTextView, searchCharacters);
//        }
//
//        return view;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return true;
//    }
//
//    private void checkVisibilityItems(ViewHolder viewHolder, User user, FriendGroup friendGroup) {
//        if (FriendGroup.GROUP_POSITION_ALL_USERS == friendGroup.getHeaderId()) {
//            checkVisibilityItemsAllUsers(viewHolder);
//        } else if (FriendGroup.GROUP_POSITION_MY_CONTACTS == friendGroup.getHeaderId()) {
//            checkVisibilityItemsMyContacts(viewHolder, user);
//        }
//    }
//
//    private void checkVisibilityItemsAllUsers(ViewHolder viewHolder) {
//        viewHolder.addFriendImageView.setVisibility(View.VISIBLE);
//        viewHolder.onlineImageView.setVisibility(View.GONE);
//        viewHolder.statusTextView.setVisibility(View.GONE);
//    }
//
//    private void checkVisibilityItemsMyContacts(ViewHolder viewHolder, User user) {
//        String status;
//
//        Friend friend = UsersDatabaseManager.getFriendById(context, user.getUserId());
//
//        if (friend == null) {
//            return;
//        }
//
//        String relationStatus = friend.getRelationStatus();
//
//        boolean isAddedFriend;
//
//        isAddedFriend = relationStatus.equals(QBFriendListHelper.RELATION_STATUS_NONE) && friend
//                .isPendingStatus();
//        if (isAddedFriend) {
//            viewHolder.onlineImageView.setVisibility(View.GONE);
//            status = resources.getString(R.string.frl_pending_request_status);
//        } else {
//            status = user.getOnlineStatus(context);
//        }
//
//        viewHolder.addFriendImageView.setVisibility(View.GONE);
//        viewHolder.statusTextView.setText(status);
//
//        viewHolder.statusTextView.setVisibility(View.VISIBLE);
//
//        setStatusVisibility(viewHolder, user.isOnline());
//    }
//
//    private void setStatusVisibility(ViewHolder viewHolder, boolean status) {
//        if (status) {
//            viewHolder.onlineImageView.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.onlineImageView.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    public void filterData(String query) {
//        query = query.toLowerCase();
//
//        friendGroupList.clear();
//
//        if (query.isEmpty()) {
//            friendGroupList.addAll(originalContact);
//        } else {
//            for (FriendGroup friendGroup : originalContact) {
//                List<User> userList = friendGroup.getUserList();
//                List<User> newUserList = new ArrayList<User>();
//                for (User user : userList) {
//                    if (user.getFullName().toLowerCase().contains(query) || user.getFullName()
//                            .toLowerCase().contains(query)) {
//                        newUserList.add(user);
//                    }
//                }
//                if (newUserList.size() > ConstsCore.ZERO_INT_VALUE) {
//                    FriendGroup nContinent = new FriendGroup(friendGroup.getHeaderId(),
//                            friendGroup.getHeaderName(), newUserList);
//                    friendGroupList.add(nContinent);
//                }
//            }
//        }
//
//        notifyDataSetChanged();
//    }
//
    private static class ViewHolder {

        public RoundedImageView avatarImageView;
        public TextView fullNameTextView;
        public TextView statusTextView;
        public ImageView addFriendImageView;
        public ImageView onlineImageView;
    }
}