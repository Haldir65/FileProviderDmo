package com.whatever.filereceiver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Harris on 2016/10/23.
 */

public class ReceiverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        Uri uri = ShareCompat.IntentReader.from(this).getStream();
        Bitmap bitmap = null;
        try {
            // Works with content://, file://, or android.resource:// URIs
            InputStream inputStream =
                    getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
            ((ImageView) findViewById(R.id.image)).setImageBitmap(bitmap);
            Toast.makeText(this, "Successfully receive image from another app", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            // Inform the user that things have gone horribly wrong
        }

    }
}
