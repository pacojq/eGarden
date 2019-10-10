package com.example.raa.egarden;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class QRManager {

    ScanActivity scanActivity;

    SurfaceView surfaceView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    private SurfaceHolder failedHolder;
    Barcode currentQR;


    public QRManager(final ScanActivity scanActivity, SurfaceView surfaceView) {

        this.scanActivity = scanActivity;
        this.surfaceView = surfaceView;

        ViewGroup.LayoutParams svLayout = this.surfaceView.getLayoutParams();

        this.barcodeDetector = new BarcodeDetector.Builder(this.scanActivity)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        this.cameraSource = new CameraSource.Builder(this.scanActivity, barcodeDetector)
                .setRequestedPreviewSize(svLayout.width, svLayout.height)
                .build();



        this.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (!scanActivity.hasPermission()) {
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


        this.barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() <= 0)
                    return;

                Barcode qr = qrCodes.valueAt(0);

                if (currentQR != null) {
                    if (qr.displayValue.equals(currentQR.displayValue))
                        return;
                }

                currentQR = qr;
                scanActivity.onQRRead(qr);
            }
        });


    }






    public void onRequestPermissionsResult(boolean granted) {
        if (!granted)
            return;

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
