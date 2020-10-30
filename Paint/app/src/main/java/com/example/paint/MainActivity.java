package com.example.paint;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private int defaultColor;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int PERMISSION_CODE_CAMERA = 1002;
    private static final int IMAGE_CAPTURE_CODE = 2;
    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawingView = findViewById(R.id.drawingview);
        button = findViewById(R.id.change_color_button);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        drawingView.init();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openColourPicker();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void takePicture()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED
            || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSION_CODE_CAMERA);
            }
            else{
                openCamera();
            }
        }
        else{
            openCamera();
        }
    }

    public void openCamera()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE);

    }

    public void openPictureFromGallery()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions,PERMISSION_CODE);
            }
            else{
                pickPictureFromGallery();
            }
        }
        else{
            pickPictureFromGallery();
        }


    }

    public void pickPictureFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==IMAGE_PICK_CODE){
            Uri imageUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                drawingView.mCanvas.drawBitmap(bitmap,0,0,drawingView.mPaint);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"Error while opening a picture",Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode==RESULT_OK && requestCode==IMAGE_CAPTURE_CODE){
            Uri imageUri = image_uri;
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                drawingView.mCanvas.drawBitmap(bitmap,0,0,drawingView.mPaint);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"Error while taking a picture",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickPictureFromGallery();
                }
                else{
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_CODE_CAMERA:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.eraser:
                drawingView.mCurrentShape = DrawingView.ERASER;
                drawingView.reset();
                return true;
            case R.id.clear:
                drawingView.clear();
                drawingView.reset();
                return true;
            case R.id.line:
                drawingView.mCurrentShape = DrawingView.LINE;
                drawingView.reset();
                return true;
            case R.id.smoothline:
                drawingView.mCurrentShape = DrawingView.SMOOTHLINE;
                drawingView.reset();
                return true;
            case R.id.rectangle:
                drawingView.mCurrentShape = DrawingView.RECTANGLE;
                drawingView.reset();
                return true;
            case R.id.square:
                drawingView.mCurrentShape = DrawingView.SQUARE;
                drawingView.reset();
                return true;
            case R.id.circle:
                drawingView.mCurrentShape = DrawingView.CIRCLE;
                drawingView.reset();
                return true;
            case R.id.triangle:
                drawingView.mCurrentShape = DrawingView.TRIANGLE;
                drawingView.reset();
                return true;
            case R.id.blur:
                drawingView.mCurrentShape=DrawingView.BLUR;
                drawingView.reset();
                return true;
            case R.id.text:
                drawingView.mCurrentShape = DrawingView.TEXT;
                drawingView.reset();
                return true;
            case R.id.openFromGallery:
                openPictureFromGallery();
                return true;
            case R.id.takeNewPicture:
                takePicture();
                return true;
            default:
                return true;
        }
    }

    private void openColourPicker () {

        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

                Toast.makeText(MainActivity.this, "Unavailable", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                defaultColor = color;
                drawingView.mCurrentColor=defaultColor;

            }

        });

        ambilWarnaDialog.show();

    }
}