package com.findg.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.findg.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by samuelhsin on 2015/10/24.
 */
public class Login extends Activity {

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
                                    ref.child("users").child(authData.getUid()).setValue(map);

                                    startActivity(new Intent("com.findg.activity.Main"));

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
