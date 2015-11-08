package com.findg.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.findg.R;

/**
 * Created by samuelhsin on 2015/10/29.
 */
public class AvatarPreference extends Preference {

    private Drawable avatar = null;

    public AvatarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //setLayoutResource(R.layout.preference_avatar);

        //TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AvatarPreference, defStyleAttr, 0);
        // avatar = ta.getDrawable(R.styleable.AvatarPreference_avatar);

    }

    public AvatarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.pref_avatarImage);
        if (imageView != null) {
            if (avatar != null) {
                imageView.setImageDrawable(avatar);
            } else {
                imageView.setImageResource(R.drawable.question_faq_icon);
            }
        }

    }

    public void setAvatar(Drawable avatar) {
        if (this.avatar == null && avatar != null
                || avatar != null && !avatar.equals(this.avatar)) {
            this.avatar = avatar;
            notifyChanged();
        }
    }

    public void setAvatar(Bitmap avatar) {
        if (avatar != null) {
            this.avatar = new BitmapDrawable(avatar);
            notifyChanged();
        }
    }

}
