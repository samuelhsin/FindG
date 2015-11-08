package com.findg.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.findg.R;
import com.findg.activity.Login;
import com.firebase.client.Firebase;

/**
 * Created by samuelhsin on 2015/11/8.
 */
public class LogoutPreference extends DialogPreference {

    public LogoutPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setDialogLayoutResource(R.layout.dialog_action_confirm);

        //TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AvatarPreference, defStyleAttr, 0);
        // avatar = ta.getDrawable(R.styleable.AvatarPreference_avatar);

    }

    public LogoutPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        builder.setTitle("Logout");
        builder.setPositiveButton(null, null);
        builder.setNegativeButton(null, null);
        super.onPrepareDialogBuilder(builder);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

    }

    @Override
    protected void onBindDialogView(final View view) {

        TextView textView = (TextView) view.findViewById(R.id.lblMessage);

        textView.setText("Are you sure to logout?");

        Button btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        final Firebase ref = new Firebase("https://findg.firebaseio.com/");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.unauth();

                Intent intent = new Intent(view.getContext(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                view.getContext().startActivity(intent);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        super.onBindDialogView(view);

    }
}
