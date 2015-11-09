package com.findg.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import com.findg.R;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by samuelhsin on 2015/10/30.
 */
public class ImageCropping extends BaseActivity {

    private ImageView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropping);
        resultView = (ImageView) findViewById(R.id.cropped_image);
        Crop.pickImage(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cropping, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select_image) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().withMaxSize(300, 300).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            //resultView.setImageURI(Crop.getOutput(result));
            Intent intent = new Intent();
            intent.putExtra("croppedUri", Crop.getOutput(result));
            setResult(RESULT_OK, intent);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED, new Intent());
        } else {
            setResult(RESULT_CANCELED, new Intent());
        }
        finish();
    }

}
