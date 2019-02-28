package in.bharatrohan.br_fe_uav.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import in.bharatrohan.br_fe_uav.R;

public class BarcodeReaderActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        cameraPreview = findViewById(R.id.surfaceView);

        if (askForPermission()) {
            createCameraSource();
        }
    }

    protected void createCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024).build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(BarcodeReaderActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
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

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcode = detections.getDetectedItems();

                if (barcode.size() > 0) {
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcode.valueAt(0));
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();
                }
            }
        });
    }


    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(BarcodeReaderActivity.this,
                    Manifest.permission.CAMERA);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(BarcodeReaderActivity.this,
                        Manifest.permission.CAMERA)) {
                    // explain
                    showMessageOKCancel(
                            (dialogInterface, i) -> ActivityCompat.requestPermissions(BarcodeReaderActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CODE_ASK_PERMISSIONS));
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(BarcodeReaderActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }

                return false;
            } else {
                // permission granted and calling function working
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createCameraSource();
                } else {
                    // Permission Denied
                    Toast.makeText(BarcodeReaderActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeReaderActivity.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(BarcodeReaderActivity.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(BarcodeReaderActivity.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }

}
