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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.runtime_terror.myapplication.activities.MenuActivity;

import java.io.IOException;

public class BarcodeFragment extends Fragment {

    View view;
    SurfaceView cameraView;
    CameraSource cameraSource;
    boolean isProcessing;
    private String TAG = "Scanner";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.barcode_fragment, container, false);
        cameraView = view.findViewById(R.id.camera_view);

        setupBarcode(container);
        setupAnimations(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isProcessing = false;
    }

    private void setupAnimations(View view) {
        view.findViewById(R.id.scanner).setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade));
        view.findViewById(R.id.scanner_dash).setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_transition));
    }

    private void processBarcode(final SparseArray<Barcode> barcode) {

        isProcessing = true;
        String barcodeData = barcode.valueAt(0).displayValue;
        Log.d(TAG, barcodeData);

        if(barcode.valueAt(0).valueFormat != 7 || barcodeData.length() < 25 || !barcodeData.substring(0,3).equals("\\\\:") || barcodeData.charAt(23) != '/') {
            animateInvalidQR();

            Log.d(TAG, "The QR code is invalid:");
            Log.d(TAG, barcode.valueAt(0).valueFormat + "");
            Log.d(TAG, barcodeData.length() + "");
            Log.d(TAG, barcodeData.substring(1,4));
            Log.d(TAG, barcodeData.charAt(23) + "");
            // TODO: Display a dialog fragment for the user.
        }
        else {
            startActivityWithAnimation(barcodeData);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isProcessing = false;
                    }
                }, 1400);
            }
        });


    }

    public void animateInvalidQR() {
        view.findViewById(R.id.invalid_qr).setAlpha(1.0f);
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.longer_repeated_fade);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.findViewById(R.id.invalid_qr).setAlpha(0.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.findViewById(R.id.invalid_qr).startAnimation(anim);
    }

    public void startActivityWithAnimation(String barcodeData) {
        view.findViewById(R.id.valid_qr).setAlpha(1.0f);
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.valid_qr_fade_in);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(TAG, "QR code passed the validation check.");
                Intent intent = new Intent(getContext(), MenuActivity.class);
                intent.putExtra("rest", barcodeData.substring(barcodeData.indexOf(":") + 1, barcodeData.indexOf("/")));
                intent.putExtra("tableNr", barcodeData.substring(barcodeData.indexOf("/") + 1));
                getActivity().startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.findViewById(R.id.valid_qr).startAnimation(anim);
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
//                Log.d("isProcessing", isProcessing + "");
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() != 0 && !isProcessing){
                    Log.d(TAG, "Scanned something:");
                    processBarcode(barcodes);
                }
            }
        });
    }
}
