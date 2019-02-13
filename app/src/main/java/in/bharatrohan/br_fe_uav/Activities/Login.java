package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.FeDetails;
import in.bharatrohan.br_fe_uav.Models.LoginFE;
import in.bharatrohan.br_fe_uav.Models.LoginUAV;
import in.bharatrohan.br_fe_uav.Models.UavDetails;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText editEmail, editPass;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);


        if (!new PrefManager(Login.this).getToken().equals("")) {
            if (new PrefManager(Login.this).getUserType().equals("fe")) {
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            } else if (new PrefManager(Login.this).getUserType().equals("uav")) {
                startActivity(new Intent(Login.this, UAVHome.class));
                finish();
            }
        }

        loginBtn.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String pass = editPass.getText().toString().trim();
            if (!validateForm()) {
                if (email.contains("fe")) {
                    Fe_Login(email, pass);
                } else if (email.contains("uav")) {
                    Uav_Login(email, pass);
                }
            } else {
                validateForm();
            }
        });

    }


    private void Fe_Login(String email, String pass) {
        showProgress();

        LoginFE loginFarmer = new LoginFE(email, pass);

        Call<LoginFE> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginFE(loginFarmer);

        call.enqueue(new Callback<LoginFE>() {
            @Override
            public void onResponse(Call<LoginFE> call, Response<LoginFE> response) {
                hideProgress();
                LoginFE loginResponse = response.body();

                if (response.code() == 200) {

                    if (loginResponse != null) {

                        new PrefManager(Login.this).saveLoginDetails(email, pass, loginResponse.getId());
                        new PrefManager(Login.this).saveToken("Bearer " + loginResponse.getToken());
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();


                        if (email.contains("fe") && loginResponse.getUserType().equals("fe")) {
                            new PrefManager(Login.this).saveUserType("fe");
                            getFeDetails();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "User not Authorized!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (response.code() == 400) {
                    Toast.makeText(Login.this, "Validation Failed. Invalid Credentials", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 401) {
                    Toast.makeText(Login.this, "User not registered.Please register Yourself", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 500) {
                    Toast.makeText(Login.this, "Something went wrong.Please try Again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginFE> call, Throwable t) {
                hideProgress();
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void Uav_Login(String email, String pass) {
        showProgress();
        LoginUAV loginUAV = new LoginUAV(email, pass);

        Call<LoginUAV> call = RetrofitClient.getInstance().getApi().loginUAV(loginUAV);

        call.enqueue(new Callback<LoginUAV>() {
            @Override
            public void onResponse(Call<LoginUAV> call, Response<LoginUAV> response) {
                hideProgress();
                LoginUAV loginResponse = response.body();

                if (response.code() == 200) {

                    if (loginResponse != null) {

                        new PrefManager(Login.this).saveLoginDetails(email, pass, loginResponse.getId());
                        new PrefManager(Login.this).saveToken("Bearer " + loginResponse.getToken());
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();


                        if (email.contains("uav") && loginResponse.getUserType().equals("uav")) {
                            new PrefManager(Login.this).saveUserType("uav");
                            getUavDetails();
                            startActivity(new Intent(Login.this, UAVHome.class));
                        } else {
                            Toast.makeText(Login.this, "User not Authorized!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (response.code() == 400) {
                    Toast.makeText(Login.this, "Validation Failed. Invalid Credentials", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 401) {
                    Toast.makeText(Login.this, "User not registered.Please register Yourself", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 500) {
                    Toast.makeText(Login.this, "Something went wrong.Please try Again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginUAV> call, Throwable t) {

            }
        });
    }

    private void getUavDetails() {
        Call<UavDetails> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUavDetail(new PrefManager(Login.this).getToken(), new PrefManager(Login.this).getUserId());

        call.enqueue(new Callback<UavDetails>() {
            @Override
            public void onResponse(Call<UavDetails> call, Response<UavDetails> response) {
                UavDetails detailResponse = response.body();

                if (detailResponse != null) {

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < detailResponse.getJobLocation().getVillage().size(); i++) {

                        sb.append(detailResponse.getJobLocation().getVillage().get(i).getVillage_name());
                        sb.append("\n");
                    }
                    String villages = sb.toString();
                    //Toast.makeText(Login.this, villages, Toast.LENGTH_SHORT).show();
                    new PrefManager(Login.this).saveUserDetails(detailResponse.getName(), detailResponse.getContact(), detailResponse.getEmail(), detailResponse.getAlt_contact(), detailResponse.getAccStatus(), detailResponse.getAddress(), detailResponse.getJobLocation().getState().getState_name(), detailResponse.getJobLocation().getDistrict().getDistrict_name(), detailResponse.getJobLocation().getTehsil().getTehsil_name(), detailResponse.getJobLocation().getBlock().getBlock_name(), villages);

                } else {
                    Toast.makeText(Login.this, "Details not Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UavDetails> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateForm() {

        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Phone no. is required!");
            editEmail.requestFocus();
            return true;
        }

        if (pass.isEmpty()) {
            editPass.setError("Password is required");
            editPass.requestFocus();
            return true;
        }

        if (pass.length() < 4) {
            editPass.setError("Password should be atleast 4 character Long");
            editPass.requestFocus();
            return true;
        }

        return false;
    }

    private void getFeDetails() {
        Call<FeDetails> call = RetrofitClient
                .getInstance()
                .getApi()
                .getFeDetail(new PrefManager(Login.this).getToken(), new PrefManager(Login.this).getUserId());

        call.enqueue(new Callback<FeDetails>() {
            @Override
            public void onResponse(Call<FeDetails> call, Response<FeDetails> response) {
                FeDetails detailResponse = response.body();

                if (detailResponse != null) {

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < detailResponse.getJobLocation().getVillage().size(); i++) {

                        sb.append(detailResponse.getJobLocation().getVillage().get(i).getVillage_name());
                        sb.append("\n");
                    }
                    String villages = sb.toString();
                    //Toast.makeText(Login.this, villages, Toast.LENGTH_SHORT).show();
                    new PrefManager(Login.this).saveUserDetails(detailResponse.getName(), detailResponse.getContact(), detailResponse.getEmail(), detailResponse.getAlt_contact(), detailResponse.getAccStatus(), detailResponse.getAddress(), detailResponse.getJobLocation().getState().getState_name(), detailResponse.getJobLocation().getDistrict().getDistrict_name(), detailResponse.getJobLocation().getTehsil().getTehsil_name(), detailResponse.getJobLocation().getBlock().getBlock_name(), villages);

                } else {
                    Toast.makeText(Login.this, "Details not Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeDetails> call, Throwable t) {
                //Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        editEmail.setEnabled(false);
        editPass.setEnabled(false);
        loginBtn.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        editEmail.setEnabled(true);
        editPass.setEnabled(true);
        loginBtn.setEnabled(true);
    }
}
