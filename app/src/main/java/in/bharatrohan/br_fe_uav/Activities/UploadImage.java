package in.bharatrohan.br_fe_uav.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.bharatrohan.br_fe_uav.Adapters.MyAdapter;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.FileUtils;
import in.bharatrohan.br_fe_uav.InternetConnection;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadImage extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;

    private View parentView;

    private ListView listView;
    private ProgressBar mProgressBar;
    private Button btnChoose, btnUpload, btnSkip;
    private ArrayList<Uri> arrayList;
    private ArrayList<String> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        arrayList = new ArrayList<>();
        pathList = new ArrayList<>();

        listView = findViewById(R.id.listView);
        mProgressBar = findViewById(R.id.progressBar);
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        btnSkip = findViewById(R.id.skip);

        btnSkip.setOnClickListener(v -> this.finish());

        btnChoose.setOnClickListener(v -> {
            if (askForPermission())
                showChooser();
        });

        btnUpload.setOnClickListener(v -> uploadImagesToServer());
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        /*Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }*/


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int currentItem = 0;
                        ClipData clipData = data.getClipData();
                        while (currentItem < count) {

                            ClipData.Item item = clipData.getItemAt(currentItem);

                            Uri imageUri = item.getUri();
                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                            Log.d("Uri Selected", imageUri.toString());
                            try {
                                // Get the file path from the URI
                                String path = FileUtils.getPath(this, imageUri);
                                pathList.add(path);
                                Log.d("Multiple File Selected", path);
                                arrayList.add(imageUri);
                                MyAdapter mAdapter = new MyAdapter(UploadImage.this, arrayList);
                                listView.setAdapter(mAdapter);
                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    }
                    /* else if (data.getData() != null) {
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
                            Log.d("Single File Selected", path);
                            arrayList.add(uri);
                            MyAdapter mAdapter = new MyAdapter(UploadImage.this, arrayList);
                            listView.setAdapter(mAdapter);
                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }*/
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImagesToServer() {
        if (InternetConnection.checkConnection(UploadImage.this)) {

            showProgress();

            // create list of file parts (photo, video, ...)
            List<MultipartBody.Part> parts = new ArrayList<>();

            if (arrayList != null) {
                // create part for file (photo, video, ...)
                for (int i = 0; i < arrayList.size(); i++) {
                    parts.add(prepareFilePart("problem_img", arrayList.get(i)));
                }
            }

            // create a map of data to pass along
            RequestBody description = createPartFromString("BharatRohan");
            RequestBody size = createPartFromString("" + parts.size());

            // finally, execute the request
            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadProblemImage(new PrefManager(UploadImage.this).getToken(), new PrefManager(UploadImage.this).getProblemId(), parts);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    hideProgress();
                    if (response.isSuccessful()) {
                        Toast.makeText(UploadImage.this,
                                "Images successfully uploaded!", Toast.LENGTH_SHORT).show();
                        UploadImage.this.finish();
                    } else {
                        Toast.makeText(UploadImage.this, "Something went Wrong!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    hideProgress();
                    Toast.makeText(UploadImage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            hideProgress();
            Toast.makeText(UploadImage.this,
                    R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        btnSkip.setEnabled(false);
        btnChoose.setEnabled(false);
        btnUpload.setEnabled(false);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        btnSkip.setEnabled(true);
        btnChoose.setEnabled(true);
        btnUpload.setEnabled(true);
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        //

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileUri))), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    /**
     * Runtime Permission
     */
    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(UploadImage.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(UploadImage.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(UploadImage.this,
                                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(UploadImage.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
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
                    // Permission Granted
                    showChooser();
                } else {
                    // Permission Denied
                    Toast.makeText(UploadImage.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImage.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                        ContextCompat.getColor(UploadImage.this, android.R.color.holo_blue_light));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                        ContextCompat.getColor(UploadImage.this, android.R.color.holo_red_light));
            }
        });

        dialog.show();

    }
}
