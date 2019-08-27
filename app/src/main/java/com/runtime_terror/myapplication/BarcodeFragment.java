package com.runtime_terror.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.runtime_terror.myapplication.activities.MenuActivity;

import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class BarcodeFragment extends Fragment {

    SurfaceView cameraView;
    CameraSource cameraSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.barcode_fragment, container, false);
        cameraView = view.findViewById(R.id.camera_view);

        setupBarcode(container);
        setupAnimations(view);

        return view;
    }

    private void setupAnimations(View view) {
        view.findViewById(R.id.scanner).setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade));
        view.findViewById(R.id.scanner_dash).setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_transition));
    }

    private void processBarcode(final SparseArray<Barcode> barcode) {
        if(barcode.valueAt(0).valueFormat != 7) {
            Log.d("Scanner", "The QR code has an incorrect format.");
            return;
        }
        String barcodeData = barcode.valueAt(0).displayValue;

        if(barcodeData.length() < 12 || !barcodeData.substring(1,13).equals("RESTAURANTS/")) {
            Log.d("Scanner", "The QR code is not a valid reference.");
            return;
        }

        Log.d("Scanner", barcodeData);
        Intent intent = new Intent(getContext(), MenuActivity.class);
        intent.putExtra(EXTRA_MESSAGE, barcodeData);
        startActivity(intent);
        //cameraSource.stop();// TODO: I swear something ain't right with this.
        //cameraSource.release();//same
        //cameraSource = null;//same
    }

    public void setupBarcode(ViewGroup container) {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(container.getContext(), barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ex) {
                        Log.e("camera source error", ex.toString());
                    }
                }  else
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 1);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes.size() != 0){
                    processBarcode(barcodes);
                }
            }
        });
    }

}
