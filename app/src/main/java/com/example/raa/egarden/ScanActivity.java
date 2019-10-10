package com.example.raa.egarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class ScanActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView textQRResult;

    private QRManager qrManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (!hasPermission())
            requestPermission();

        this.surfaceView = findViewById(R.id.surfaceView);
        this.qrManager = new QRManager(this, this.surfaceView);

        this.textQRResult = findViewById(R.id.textQRResult);
    }



    public void onQRRead(final Barcode qr) {

        this.textQRResult.post(new Runnable() {
            @Override
            public void run() {
                Vibrator vib = (Vibrator) getApplicationContext()
                        .getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate((long) 500);
                textQRResult.setText(qr.displayValue);
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
