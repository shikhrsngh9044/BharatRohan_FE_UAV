package in.bharatrohan.br_fe_uav.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.FileUtils;
import in.bharatrohan.br_fe_uav.Models.Responses;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UAVProfile extends AppCompatActivity {

    private static final String TAG = UAVProfile.class.getSimpleName();
    private static final int REQUEST_CODE = 6384;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 124;


    private TextView name, phone, altContact, email, address, updatePic, state, district, tehsil, block, village;
    private EditText editContact, editAltContact, editAddress;
    private ProgressBar progressBar;
    private ImageView userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uavprofile);
        new CheckInternet(this).checkConnection();

        init();

        name.setText(new PrefManager(UAVProfile.this).getName());
        phone.setText(new PrefManager(UAVProfile.this).getContact());
        altContact.setText(new PrefManager(UAVProfile.this).getAltContact());
        email.setText(new PrefManager(UAVProfile.this).getEmail());
        address.setText(new PrefManager(UAVProfile.this).getAddress());
        state.setText(new PrefManager(UAVProfile.this).getState());
        district.setText(new PrefManager(UAVProfile.this).getDistrict());
        tehsil.setText(new PrefManager(UAVProfile.this).getTehsil());
        block.setText(new PrefManager(UAVProfile.this).getBlock());
        village.setText(new PrefManager(UAVProfile.this).getVillage());

        updatePic.setOnClickListener(v -> {
            if (askForPermission()) {
                showChooser();
            }
        });

        findViewById(R.id.btnUpdateOp).setOnClickListener(v -> {

            String contact1 = phone.getText().toString().trim();
            String alt_contact1 = altContact.getText().toString().trim();
            String fulladdress1 = address.getText().toString().trim();

            visibilityGone();

            editContact.setText(contact1);
            editAltContact.setText(alt_contact1);
            editAddress.setText(fulladdress1);
        });

        findViewById(R.id.btnUpdate).setOnClickListener(v -> {
            String contact1 = editContact.getText().toString().trim();
            String alt_contact1 = editAltContact.getText().toString().trim();
            String fulladdress1 = editAddress.getText().toString().trim();

            if (!validateValues()) {
                if (new PrefManager(UAVProfile.this).getUserType().equals("fe")) {
                    updateFE(contact1, alt_contact1, fulladdress1);
                } else if (new PrefManager(UAVProfile.this).getUserType().equals("uav")) {
                    updateUAV(contact1, alt_contact1, fulladdress1);
                }
            } else {
                validateValues();
            }

        });

        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            visibilityVisible();
        });

    }


    private void visibilityGone() {
        findViewById(R.id.btnUpdateOp).setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        altContact.setVisibility(View.GONE);
        address.setVisibility(View.GONE);


        editContact.setVisibility(View.VISIBLE);
        editAltContact.setVisibility(View.VISIBLE);
        editAddress.setVisibility(View.VISIBLE);
        findViewById(R.id.constraint).setVisibility(View.VISIBLE);
        findViewById(R.id.co3).setVisibility(View.VISIBLE);
    }

    private void visibilityVisible() {
        findViewById(R.id.btnUpdateOp).setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        altContact.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);

        editAddress.setVisibility(View.GONE);
        editContact.setVisibility(View.GONE);
        findViewById(R.id.constraint).setVisibility(View.GONE);
        findViewById(R.id.co3).setVisibility(View.GONE);
    }


    private Boolean validateValues() {


        String contact = editContact.getText().toString().trim();
        String altcontact = editAltContact.getText().toString().trim();
        String fulladdress = editAddress.getText().toString().trim();


        if (contact.isEmpty()) {
            editContact.setError("Contact is required");
            editContact.requestFocus();
            return true;
        }

        if (!altcontact.isEmpty() && altcontact.length() < 10) {
            editAltContact.setError("Phone must be of at least length 10");
            editAltContact.requestFocus();
            return true;
        }


        if (fulladdress.isEmpty()) {
            editAddress.setError("Address is required");
            editAddress.requestFocus();
            return true;
        }

        return false;
    }

    private void init() {
        progressBar = findViewById(R.id.progressBar);
        name = findViewById(R.id.tvUavName);
        phone = findViewById(R.id.tvUavPhone);
        altContact = findViewById(R.id.tvUavAltPhone);
        email = findViewById(R.id.tvUavEmail);
        address = findViewById(R.id.tvUavAddress);
        userAvatar = findViewById(R.id.uavAvatar);
        state = findViewById(R.id.tvState);
        district = findViewById(R.id.tvDistrict);
        tehsil = findViewById(R.id.tvTehsil);
        block = findViewById(R.id.tvBlock);
        village = findViewById(R.id.tvVillage);
        updatePic = findViewById(R.id.tvUpdatePic);


        if (!new PrefManager(UAVProfile.this).getAvatar().equals(""))
            Picasso.get().load(new PrefManager(UAVProfile.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userAvatar, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                    Picasso.get().load(R.drawable.profile_pic).into(userAvatar);
                }
            });


        editContact = findViewById(R.id.editContact);
        editAddress = findViewById(R.id.editAddress);
        editAltContact = findViewById(R.id.editAltContact);
    }


    private void showChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    private void updateFE(String contact, String alt_contact, String address) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateFeDetail(new PrefManager(UAVProfile.this).getToken(), new PrefManager(UAVProfile.this).getUserId(), contact, alt_contact, address);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {

                    new PrefManager(UAVProfile.this).saveUserDetails(new PrefManager(UAVProfile.this).getName(), contact, new PrefManager(UAVProfile.this).getEmail(), alt_contact, new PrefManager(UAVProfile.this).getAccStatus(), address, new PrefManager(UAVProfile.this).getState(), new PrefManager(UAVProfile.this).getDistrict(), new PrefManager(UAVProfile.this).getTehsil(), new PrefManager(UAVProfile.this).getBlock(), new PrefManager(UAVProfile.this).getVillage());
                    updateValues(contact, alt_contact, address);
                    Toast.makeText(UAVProfile.this, "User Updated", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    new PrefManager(UAVProfile.this).saveLoginDetails("", "", "");
                    new PrefManager(UAVProfile.this).saveToken("");
                    new PrefManager(UAVProfile.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAVProfile.this).saveUserType("");
                    Toast.makeText(UAVProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAVProfile.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAVProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(UAVProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUAV(String contact, String alt_contact, String address) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateUavDetail(new PrefManager(UAVProfile.this).getToken(), new PrefManager(UAVProfile.this).getUserId(), contact, alt_contact, address);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    new PrefManager(UAVProfile.this).saveUserDetails(new PrefManager(UAVProfile.this).getName(), contact, new PrefManager(UAVProfile.this).getEmail(), alt_contact, new PrefManager(UAVProfile.this).getAccStatus(), address, new PrefManager(UAVProfile.this).getState(), new PrefManager(UAVProfile.this).getDistrict(), new PrefManager(UAVProfile.this).getTehsil(), new PrefManager(UAVProfile.this).getBlock(), new PrefManager(UAVProfile.this).getVillage());
                    Toast.makeText(UAVProfile.this, "Update User", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    new PrefManager(UAVProfile.this).saveLoginDetails("", "", "");
                    new PrefManager(UAVProfile.this).saveToken("");
                    new PrefManager(UAVProfile.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAVProfile.this).saveUserType("");
                    Toast.makeText(UAVProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAVProfile.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAVProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(UAVProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateValues(String contact, String alt_contact, String address1) {
        phone.setText(contact);
        altContact.setText(alt_contact);
        address.setText(address1);
        visibilityVisible();
    }

    private void updateFEProfilePic(Uri imageUri) {
//Uri imageUri
        showProgress();


        File file = FileUtils.getFile(this, imageUri);
        //Objects.requireNonNull(getContentResolver().getType(imageUri))

        //File file = new File(this.getFilesDir() + "/ProfileImage/" + "Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(imageUri))), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Call<Responses.AvatarResponse> call = RetrofitClient.getInstance().getApi().updateFeAvatar(new PrefManager(UAVProfile.this).getToken(), new PrefManager(UAVProfile.this).getUserId(), profilePic);

        call.enqueue(new Callback<Responses.AvatarResponse>() {
            @Override
            public void onResponse(Call<Responses.AvatarResponse> call, Response<Responses.AvatarResponse> response) {
                hideProgress();
                Responses.AvatarResponse avatarResponse = response.body();
                if (response.code() == 200) {

                    if (avatarResponse != null) {

                        new PrefManager(UAVProfile.this).saveAvatar(avatarResponse.getAvatar());

                        //Toast.makeText(UserProfile.this, new PrefManager(UserProfile.this).getAvatar(), Toast.LENGTH_SHORT).show();
                        if (!new PrefManager(UAVProfile.this).getAvatar().equals(""))
                            Picasso.get().load(new PrefManager(UAVProfile.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userAvatar, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                                    Picasso.get().load(R.drawable.profile_pic).into(userAvatar);
                                }
                            });

                        Toast.makeText(UAVProfile.this, avatarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    new PrefManager(UAVProfile.this).saveLoginDetails("", "", "");
                    new PrefManager(UAVProfile.this).saveToken("");
                    new PrefManager(UAVProfile.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAVProfile.this).saveUserType("");
                    Toast.makeText(UAVProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAVProfile.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAVProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responses.AvatarResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(UAVProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUAVProfilePic(Uri imageUri) {
//Uri imageUri
        showProgress();


        File file = FileUtils.getFile(this, imageUri);
        //Objects.requireNonNull(getContentResolver().getType(imageUri))

        //File file = new File(this.getFilesDir() + "/ProfileImage/" + "Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(imageUri))), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part profilePic = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);

        Call<Responses.AvatarResponse> call = RetrofitClient.getInstance().getApi().updateUavAvatar(new PrefManager(UAVProfile.this).getToken(), new PrefManager(UAVProfile.this).getUserId(), profilePic);

        call.enqueue(new Callback<Responses.AvatarResponse>() {
            @Override
            public void onResponse(Call<Responses.AvatarResponse> call, Response<Responses.AvatarResponse> response) {
                hideProgress();
                Responses.AvatarResponse avatarResponse = response.body();
                if (response.code() == 200) {

                    if (avatarResponse != null) {

                        new PrefManager(UAVProfile.this).saveAvatar(avatarResponse.getAvatar());

                        //Toast.makeText(UserProfile.this, new PrefManager(UserProfile.this).getAvatar(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load(new PrefManager(UAVProfile.this).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(userAvatar, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                                Picasso.get().load(R.drawable.profile_pic).into(userAvatar);
                            }
                        });

                        Toast.makeText(UAVProfile.this, avatarResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    new PrefManager(UAVProfile.this).saveLoginDetails("", "", "");
                    new PrefManager(UAVProfile.this).saveToken("");
                    new PrefManager(UAVProfile.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAVProfile.this).saveUserType("");
                    Toast.makeText(UAVProfile.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAVProfile.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAVProfile.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Responses.AvatarResponse> call, Throwable t) {
                hideProgress();
                Toast.makeText(UAVProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                final Uri uri = data.getData();
                Log.i(TAG, "Uri = " + uri.toString());

                if (new PrefManager(UAVProfile.this).getUserType().equals("fe")) {
                    updateFEProfilePic(uri);
                } else if (new PrefManager(UAVProfile.this).getUserType().equals("uav")) {
                    updateUAVProfilePic(uri);
                }

                /*CropImage.activity(uri).setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setBorderLineColor(Color.RED)
                        .setGuidelinesColor(Color.GREEN)
                        .setMinCropWindowSize(500, 500)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setFixAspectRatio(true)
                        .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                        .setAutoZoomEnabled(true)
                        .start(UserProfile.this);*/
            }
        }
        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {

                File directory = new File(this.getFilesDir(), "ProfileImage");
                if (!directory.exists()) {
                    directory.mkdir();
                }

                File imagefile = new File(directory, "Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

                Uri resultUri = result.getUri();
                File thumb_filePath = new File(Objects.requireNonNull(resultUri.getPath()));

                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(150)
                            .setMaxWidth(150)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);


                    FileOutputStream fileos = new FileOutputStream(imagefile);
                    //assert thumb_bitmap != null;
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileos);
                    fileos.flush();
                    fileos.close();
                    MediaStore.Images.Media.insertImage(getContentResolver(), thumb_bitmap, "Profile Pic", "profile pic user");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                *//*try {

                } catch (IOException e) {
                    e.printStackTrace();
                }*//*

                File filePath = new File(this.getFilesDir() + "/ProfileImage");
                File newFile = new File(filePath,"Pic_" + new PrefManager(UserProfile.this).getPhone() + ".jpg");

                Uri imageUri = FileProvider.getUriForFile(this,"in.bharatrohan.fileprovider",newFile);

                updateProfilePic(imageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/

        //updateProfilePic(uri);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean askForPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            int hasCallPermission = ContextCompat.checkSelfPermission(UAVProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                // Ask for permission
                // need to request permission
                if (ActivityCompat.shouldShowRequestPermissionRationale(UAVProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // explain
                    showMessageOKCancel(
                            (dialogInterface, i) -> ActivityCompat.requestPermissions(UAVProfile.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    REQUEST_CODE_ASK_PERMISSIONS));
                    // if denied then working here
                } else {
                    // Request for permission
                    ActivityCompat.requestPermissions(UAVProfile.this,
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
                    Toast.makeText(UAVProfile.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UAVProfile.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(UAVProfile.this, android.R.color.holo_blue_light));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(UAVProfile.this, android.R.color.holo_red_light));
        });

        dialog.show();

    }
}
