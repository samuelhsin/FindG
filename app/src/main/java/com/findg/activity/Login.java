package com.findg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.findg.R;
import com.findg.common.Consts;
import com.findg.utils.DateUtils;
import com.findg.data.model.Contact;
import com.findg.data.model.Friend;
import com.findg.data.model.FriendGroup;
import com.findg.data.model.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuelhsin on 2015/10/24.
 */
public class Login extends BaseActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        Firebase.setAndroidContext(this);

        final Login loginActivity = this;
        final Firebase ref = new Firebase("https://findg.firebaseio.com/");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if (username.isEmpty()) {
                    username = "test@test.findg.com";
                }
                if (password.isEmpty()) {
                    password = "test";
                }
                if (!username.isEmpty() && !password.isEmpty()) {
                    ref.authWithPassword(username, password,
                            new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    // Authentication just completed successfully :)
                                    Map<String, String> map = new HashMap<>();
                                    map.put("provider", authData.getProvider());
                                    if (authData.getProviderData().containsKey("displayName")) {
                                        map.put("displayName", authData.getProviderData().get("displayName").toString());
                                    }
                                    //last login time
                                    map.put("lastLoginTime", DateUtils.getDateTimeStr(DateUtils.nowCST()));
                                    String userSn = authData.getUid();
                                    ref.child("users").child(userSn).setValue(map);
                                    User user = getDBHelper().getSelf(userSn);
                                    //----for testing----
                                    User testFriend = getDBHelper().getTestFriend();
                                    Contact contact = (Contact) user.getContacts().toArray()[0];
                                    if (!contact.isUserExistInContact(testFriend)) {
                                        FriendGroup friendGroup = new FriendGroup(contact);
                                        friendGroup.setFriendGroupType(Consts.FriendGroupType.User);
                                        getDBHelper().create(friendGroup);
                                        Friend friend = new Friend(friendGroup, testFriend);
                                        getDBHelper().create(friend);
                                    }
                                    //-------------------
                                    Intent intent = new Intent("com.findg.activity.Main");
                                    intent.putExtra("userSn", userSn);
                                    startActivity(intent);

                                }

                                @Override
                                public void onAuthenticationError(FirebaseError error) {
                                    Toast.makeText(loginActivity, "Username/Password error!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(loginActivity, "Username/Password error!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
