package in.bharatrohan.br_fe_uav.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.Download;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import in.bharatrohan.br_fe_uav.Services.DownloadService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UAV_FarmerInfo extends AppCompatActivity {


    private Button btnDownKml, btnScanRse;
    private TextView Name, Phone, Email, Address, farmName, farmLocation, farmArea;
    private ImageView avatar;
    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;

    //@BindView(R.id.progress)
    private ProgressBar mProgressBar;
    //@BindView(R.id.progress_text)
    private TextView mProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uav__farmer_info);
        init();
        ButterKnife.bind(this);

        registerReceiver();

        btnScanRse.setOnClickListener(v -> {
            scanBarcode();
        });

        btnDownKml.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressText.setVisibility(View.VISIBLE);
            if (checkPermission()) {
                startDownload();
            } else {
                requestPermission();
            }
        });
    }

    private void init() {
        btnDownKml = findViewById(R.id.btnDownKml);
        btnScanRse = findViewById(R.id.btnScanRse);
        mProgressBar = findViewById(R.id.progress);
        mProgressText = findViewById(R.id.progress_text);
        Name = findViewById(R.id.tvName);
        Phone = findViewById(R.id.tvPhone);
        Email = findViewById(R.id.tvEmail);
        Address = findViewById(R.id.tvAddress);
        farmName = findViewById(R.id.tvFarmName);
        farmLocation = findViewById(R.id.tvFarmLocation);
        farmArea = findViewById(R.id.tvFarmArea);

        Name.setText(new PrefManager(this).getFName());
        Email.setText(new PrefManager(this).getFEmail());
        Phone.setText(new PrefManager(this).getFContact());
        Address.setText(new PrefManager(this).getFAddress());
        farmName.setText(new PrefManager(this).getFFarmName());
        farmLocation.setText(new PrefManager(this).getFFarmLocation());
        farmArea.setText(new PrefManager(this).getFFarmArea());
    }


    public void scanBarcode() {
        Intent intent = new Intent(this, BarcodeReaderActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (requestCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    //Qr.setText(barcode.displayValue);
                    submitToRse(barcode.displayValue);
                    //Toast.makeText(UAV_FarmerInfo.this, barcode.displayValue.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UAV_FarmerInfo.this, "No QR found", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void submitToRse(String rseId) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().submitToRse(new PrefManager(UAV_FarmerInfo.this).getToken(), new PrefManager(UAV_FarmerInfo.this).getUserId(), rseId, new PrefManager(UAV_FarmerInfo.this).getFProblemId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(UAV_FarmerInfo.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UAV_FarmerInfo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startDownload() {

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);

    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(MESSAGE_PROGRESS)) {

                Download download = intent.getParcelableExtra("download");
                mProgressBar.setProgress(download.getProgress());
                if (download.getProgress() == 100) {

                    mProgressText.setText("File Download Complete");

                } else {

                    mProgressText.setText(String.format("Downloaded (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));

                }
            }
        }
    };

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startDownload();
                } else {

                    Toast.makeText(UAV_FarmerInfo.this, "Permission Denied, Please allow to proceed !", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
