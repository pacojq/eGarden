package com.example.raa.egarden;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class ScanActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private TextView textQRResult;

    private MediaPlayer mediaPlayerCoin;
    private MediaPlayer mediaPlayerIncorrect;

    private QRManager qrManager;
    private Intent cropDataIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (!hasPermission())
            requestPermission();

        this.mediaPlayerCoin = MediaPlayer.create(this, R.raw.coin_sound);
        this.mediaPlayerIncorrect = MediaPlayer.create(this, R.raw.incorrect_sound);

        this.surfaceView = findViewById(R.id.surfaceView);
        this.qrManager = new QRManager(this, this.surfaceView);

        this.textQRResult = findViewById(R.id.textQRResult);
        this.textQRResult.setText(R.string.scan_start);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(ScanActivity.this, MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }



    public void onQRRead(final Barcode qr) {

        this.textQRResult.post(new Runnable() {
            @Override
            public void run() {
                Vibrator vib = (Vibrator) getApplicationContext()
                        .getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate((long) 500);

                // Check correct QR
                try {
                    Crop crop = Crop.FromString(qr.displayValue);
                }
                catch (Exception e) {
                    textQRResult.setText(R.string.scan_error);
                    textQRResult.setTextColor(Color.YELLOW);
                    mediaPlayerIncorrect.start();
                    return;
                }

                textQRResult.setTextColor(Color.GREEN);
                textQRResult.setText(R.string.scan_success);
                mediaPlayerCoin.start();

                cropDataIntent = new Intent(ScanActivity.this, CropDataActivity.class);
                cropDataIntent.putExtra("serialized",	qr.displayValue);
                startActivity(cropDataIntent);
            }
        });
    }








    public boolean hasPermission() {
        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != 0)
            return;

        boolean granted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        this.qrManager.onRequestPermissionsResult(granted);
    }


}
