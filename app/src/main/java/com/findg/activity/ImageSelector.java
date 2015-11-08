package com.findg.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by samuelhsin on 2015/10/30.
 */
public class ImageSelector extends Activity {
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start Activity To Select Image From Gallery
        Intent gallery_Intent = new Intent(getApplicationContext(), Gallery.class);
        startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri picturePath = data.getParcelableExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            } else {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        } else if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap croppedBitmap = extras.getParcelable("data");

                Intent returnFromIntent = new Intent();
                returnFromIntent.putExtra("croppedBitmap", croppedBitmap);
                setResult(RESULT_OK, returnFromIntent);

                // Set The Bitmap Data To ImageView
                //image_capture1.setImageBitmap(selectedBitmap);
                //image_capture1.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                setResult(RESULT_CANCELED, new Intent());
            }
            finish();
        }
    }

    private void performCrop(Uri contentUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");

            // indicate image type and Uri
            //File f = new File(picUriStr);
            //Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }
}
