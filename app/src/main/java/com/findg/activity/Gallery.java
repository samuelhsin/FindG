package com.findg.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class Gallery extends BaseActivity {
    private final static int RESULT_SELECT_IMAGE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String TAG = Gallery.class.getSimpleName();

    String mCurrentPhotoPath;
    File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //Pick Image From Gallery

            // Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Image"), RESULT_SELECT_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SELECT_IMAGE:

                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    try {
                        Uri selectedImage = data.getData();
                        //String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        //Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        //if (cursor != null) {
                        //    cursor.moveToFirst();
                        //    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        //    String picturePath = cursor.getString(columnIndex);
                        //    cursor.close();
                        //    //return Image Path to the Main Activity
                        //    Intent returnFromGalleryIntent = new Intent();
                        //    returnFromGalleryIntent.putExtra("picturePath", picturePath);
                        //    setResult(RESULT_OK, returnFromGalleryIntent);
                        //} else {
                        //    setResult(RESULT_CANCELED, new Intent());
                        //}

                        Intent returnFromGalleryIntent = new Intent();
                        returnFromGalleryIntent.putExtra("picturePath", selectedImage);
                        setResult(RESULT_OK, returnFromGalleryIntent);

                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent returnFromGalleryIntent = new Intent();
                        setResult(RESULT_CANCELED, returnFromGalleryIntent);
                        finish();
                    }
                } else {
                    Log.i(TAG, "RESULT_CANCELED");
                    Intent returnFromGalleryIntent = new Intent();
                    setResult(RESULT_CANCELED, returnFromGalleryIntent);
                    finish();
                }
                break;
        }
    }
}
