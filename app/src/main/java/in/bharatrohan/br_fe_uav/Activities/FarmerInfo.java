package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerInfo extends AppCompatActivity {

    private TextView landInfo;
    private TextView name, contact, email, address, dob;
    public ArrayList<String> farmList;
    private ProgressBar progressBar;
    private Button btnVerify;
    private ImageView profilePic;
    private String imageString;
    private boolean isVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farmer_info);
        new CheckInternet(this).checkConnection();

        init();

        landInfo.setOnClickListener(v -> {
            startActivity(new Intent(FarmerInfo.this, FarmerFarms.class));
        });

        btnVerify.setOnClickListener(v -> {
            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().verifyFarmer(new PrefManager(FarmerInfo.this).getToken(), new PrefManager(FarmerInfo.this).getFarmerId());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        btnVerify.setVisibility(View.GONE);
                        init();
                        Toast.makeText(FarmerInfo.this, "Farmer Verified Successfully!!", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 401) {
                        new PrefManager(FarmerInfo.this).saveLoginDetails("", "", "");
                        new PrefManager(FarmerInfo.this).saveToken("");
                        new PrefManager(FarmerInfo.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                        new PrefManager(FarmerInfo.this).saveUserType("");
                        Toast.makeText(FarmerInfo.this, "Token Expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FarmerInfo.this, Login.class));
                        finish();
                    } else if (response.code() == 400) {
                        Toast.makeText(FarmerInfo.this, "Bad Request", Toast.LENGTH_SHORT).show();
                        //Vaifation failed
                    } else if (response.code() == 500) {
                        Toast.makeText(FarmerInfo.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });
    }

    private void init() {

        farmList = new ArrayList<>();

        landInfo = findViewById(R.id.landDetails);

        name = findViewById(R.id.tvUavName);
        contact = findViewById(R.id.tvText);
        email = findViewById(R.id.tvUavEmail);
        address = findViewById(R.id.tvUavAddress);
        dob = findViewById(R.id.tvDob);
        progressBar = findViewById(R.id.progressBar);
        btnVerify = findViewById(R.id.btnVerify);
        profilePic = findViewById(R.id.profileImage);

        getDetail();

    }

    private void getDetail() {
        showProgress();
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(this).getToken(), new PrefManager(this).getFarmerId());

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                hideProgress();
                Farmer farmer = response.body();
                if (response.code() == 200) {
                    if (farmer != null) {


                        if (farmer.getAvatar() != null) {

                            Picasso.get().load("http://br.bharatrohan.in/" + farmer.getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).noFade().into(profilePic, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load("http://br.bharatrohan.in/" + farmer.getAvatar()).fit().centerCrop().noFade().into(profilePic);
                                }
                            });

                        } else {
                            Picasso.get().load(R.drawable.profile_pic).into(profilePic);
                        }


                        name.setText(farmer.getName());
                        contact.setText(farmer.getContact());
                        email.setText(farmer.getEmail());
                        address.setText(farmer.getFull_address());
                        dob.setText(farmer.getDob());
                        if (!farmer.getVerified()) {
                            btnVerify.setVisibility(View.VISIBLE);
                        }
                        new PrefManager(FarmerInfo.this).saveUavId(farmer.getUav_id());
                        new PrefManager(FarmerInfo.this).saveFarmCount(farmer.getFarms().size());
                    } else {
                        Toast.makeText(FarmerInfo.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    new PrefManager(FarmerInfo.this).saveLoginDetails("", "", "");
                    new PrefManager(FarmerInfo.this).saveToken("");
                    new PrefManager(FarmerInfo.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(FarmerInfo.this).saveUserType("");
                    Toast.makeText(FarmerInfo.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FarmerInfo.this, Login.class));
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(FarmerInfo.this, "Bad Request", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 500) {
                    Toast.makeText(FarmerInfo.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(FarmerInfo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        landInfo.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        landInfo.setEnabled(true);
    }


}
