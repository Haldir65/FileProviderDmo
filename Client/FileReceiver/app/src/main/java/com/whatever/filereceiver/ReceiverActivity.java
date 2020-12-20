package com.whatever.filereceiver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.view.ViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Harris on 2016/10/23.
 */

public class ReceiverActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setOnTouchListener(this);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ViewCompat.animate(view).alpha(0.8f).setDuration(100).scaleX(1.2f).scaleY(1.2f).translationZ(20);
                return true;
            case MotionEvent.ACTION_UP:
                ViewCompat.animate(view).alpha(1f).setDuration(100).scaleX(1f).scaleY(1f).translationZ(0);
                return true;
            default:
                break;
        }

        return false;
    }
}
