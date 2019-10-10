package com.example.raa.egarden;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    private SurfaceHolder failedHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (!hasPermission())
            requestPermission();

        this.surfaceView = findViewById(R.id.surfaceView);
        ViewGroup.LayoutParams svLayout = this.surfaceView.getLayoutParams();

        this.barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        this.cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(svLayout.width, svLayout.height)
                .build();

        this.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (!hasPermission()) {
                    failedHolder = holder;
                    return;
                }

                try {
                    cameraSource.start(holder);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }














    private boolean hasPermission() {
        return ActivityCompat
                .checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != 0)
            return;

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (failedHolder != null) {
                try {
                    cameraSource.start(failedHolder);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                failedHolder = null;
            }
        }
    }
}
