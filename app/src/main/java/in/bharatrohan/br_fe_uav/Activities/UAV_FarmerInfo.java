package in.bharatrohan.br_fe_uav.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
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
    private TextView Name, Phone, Email, Address, farmName, farmLocation, farmArea, Call, Mail, FeName, FeMail, FeCall;
    private ImageView avatar;
    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private Boolean toggle;

    //@BindView(R.id.progress)
    private ProgressBar mProgressBar, progressBar;
    //@BindView(R.id.progress_text)
    private TextView mProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uav__farmer_info);
        init();
        new CheckInternet(this).checkConnection();

        ButterKnife.bind(this);

        registerReceiver();

        btnScanRse.setOnClickListener(v -> {
            toggle = false;
            if (askForPermission()) {
                scanBarcode();
            }
        });

        btnDownKml.setOnClickListener(v -> {
            toggle = true;
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressText.setVisibility(View.VISIBLE);
            if (checkPermission()) {
                startDownload();
            } else {
                requestPermission();
            }
        });

        Call.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + Phone.getText().toString().trim()));
            startActivity(dialIntent);
        });

        Mail.setOnClickListener(v -> {

            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(Email.getText().toString().trim());
            Uri uri = Uri.parse(uriText);
            send.setData(uri);
            startActivity(Intent.createChooser(send, "Send mail..."));
        });

        FeCall.setOnClickListener(v -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + new PrefManager(this).getFFeContact()));
            startActivity(dialIntent);
        });

        FeMail.setOnClickListener(v -> {
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(new PrefManager(this).getFFeEmail());
            Uri uri = Uri.parse(uriText);
            send.setData(uri);
            startActivity(Intent.createChooser(send, "Send mail..."));
        });
    }

    private void init() {
        btnDownKml = findViewById(R.id.btnDownKml);
        btnScanRse = findViewById(R.id.btnScanRse);
        mProgressBar = findViewById(R.id.progress);
        progressBar = findViewById(R.id.progressBar);
        mProgressText = findViewById(R.id.progress_text);
        avatar = findViewById(R.id.Avatar);
        Name = findViewById(R.id.tvName);
        Phone = findViewById(R.id.tvPhone);
        Email = findViewById(R.id.tvEmail);
        Address = findViewById(R.id.tvAddress);
        farmName = findViewById(R.id.tvFarmName);
        farmLocation = findViewById(R.id.tvFarmLocation);
        farmArea = findViewById(R.id.tvFarmArea);
        Call = findViewById(R.id.textView9);
        Mail = findViewById(R.id.textView10);
        FeName = findViewById(R.id.tvFeName);
        FeCall = findViewById(R.id.tvFeCall);
        FeMail = findViewById(R.id.tvFeMail);

        FeName.setText(new PrefManager(this).getFFeName());
        Name.setText(new PrefManager(this).getFName());
        Email.setText(new PrefManager(this).getFEmail());
        Phone.setText(new PrefManager(this).getFContact());
        Address.setText(new PrefManager(this).getFAddress());
        farmName.setText(new PrefManager(this).getFFarmName());
        farmLocation.setText(new PrefManager(this).getFFarmLocation());
        farmArea.setText(new PrefManager(this).getFFarmArea());

        if (!new PrefManager(this).getFAvatar().equals("")) {
            Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(this).getFAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(avatar, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                    Picasso.get().load("http://br.bharatrohan.in/" + new PrefManager(UAV_FarmerInfo.this).getFAvatar()).fit().centerCrop().into(avatar);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).into(avatar);
        }
    }


    private void showProgress() {
        Call.setEnabled(false);
        Mail.setEnabled(false);
        FeMail.setEnabled(false);
        FeCall.setEnabled(false);
        btnScanRse.setEnabled(false);
        btnDownKml.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        Call.setEnabled(true);
        Mail.setEnabled(true);
        FeMail.setEnabled(true);
        FeCall.setEnabled(true);
        btnScanRse.setEnabled(true);
        btnDownKml.setEnabled(true);
        progressBar.setVisibility(View.GONE);
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
                    if (barcode.displayValue.length() == 24) {
                        submitToRse(barcode.displayValue);
                    } else {

                    }
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
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().submitToRse(new PrefManager(UAV_FarmerInfo.this).getToken(), new PrefManager(UAV_FarmerInfo.this).getUserId(), rseId, new PrefManager(UAV_FarmerInfo.this).getFProblemId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(UAV_FarmerInfo.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    new PrefManager(UAV_FarmerInfo.this).saveLoginDetails("", "", "");
                    new PrefManager(UAV_FarmerInfo.this).saveToken("");
                    new PrefManager(UAV_FarmerInfo.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAV_FarmerInfo.this).saveUserType("");
                    Toast.makeText(UAV_FarmerInfo.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAV_FarmerInfo.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAV_FarmerInfo.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
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

    //for Camera source
    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(UAV_FarmerInfo.this,
                    Manifest.permission.CAMERA);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(UAV_FarmerInfo.this,
                        Manifest.permission.CAMERA)) {
                    // explain
                    showMessageOKCancel(
                            (dialogInterface, i) -> ActivityCompat.requestPermissions(UAV_FarmerInfo.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    PERMISSION_REQUEST_CODE));
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(UAV_FarmerInfo.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CODE);
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


    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UAV_FarmerInfo.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(UAV_FarmerInfo.this, android.R.color.holo_blue_light));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(UAV_FarmerInfo.this, android.R.color.holo_red_light));
        });

        dialog.show();

    }


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
                    if (toggle) {
                        startDownload();
                    } else {
                        scanBarcode();
                    }
                } else {

                    Toast.makeText(UAV_FarmerInfo.this, "Permission Denied, Please allow to proceed !", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}
