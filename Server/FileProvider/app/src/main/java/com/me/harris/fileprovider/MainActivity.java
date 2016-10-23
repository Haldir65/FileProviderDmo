package com.me.harris.fileprovider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Environment.MEDIA_MOUNTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FILES_AUTHORITY = "com.me.harris";
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String PIC_NAME = "minion_pic.png";

    private ImageView mImageView;
    private TextView mTextView1, mTextView2;
    private Button mButton1, mButton2;
    public static final int REQUEST_WRITE_ENTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }

    private void writeBitmapToSdCard() {
        if (ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请给权限", Toast.LENGTH_SHORT).show(); //user has rejected once
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_ENTERNAL_STORAGE);
            } else {
                //simply request permission
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_ENTERNAL_STORAGE);
            }
        } else {
            writePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_ENTERNAL_STORAGE) {
            if (grantResults.length == 0) {
                return;
            }
            if (grantResults[0] == PERMISSION_GRANTED) {
                writePicture();
            }
        }
    }

    /**
     * do stuffs now that we have the permission
     */
    private void writePicture() {
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED && Environment.getExternalStorageState().equals(MEDIA_MOUNTED)) {
            File DownloadDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + Environment.DIRECTORY_DOWNLOADS);
            File file = new File(DownloadDirectory, PIC_NAME);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                Toast.makeText(this, "图片写到DownLoad目录完成", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void ShareImage() {
        File imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOWNLOADS, PIC_NAME);
        if (imageFile.exists()) {
            Uri uriToImage = FileProvider.getUriForFile(
                    this, FILES_AUTHORITY, imageFile);
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setStream(uriToImage)
                    .getIntent();
// Provide read access
            shareIntent.setData(uriToImage);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(shareIntent, 1007);
            }
        } else {
            Toast.makeText(this, "未找到文件!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // call the receiving app now!
                writeBitmapToSdCard();
                break;
            case R.id.button2:
                ShareImage();
                break;
            default:
                break;
        }
    }
}
