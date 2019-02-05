package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.FeDetails;
import in.bharatrohan.br_fe_uav.Models.LoginFE;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText editEmail, editPass;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);

        if (!new PrefManager(Login.this).getToken().equals("")) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        loginBtn.setOnClickListener(v -> {
            UserLogin();
        });
    }


    private void UserLogin() {
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (email.isEmpty()) {
            editEmail.setError("Phone no. is required!");
            editEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            editPass.setError("Password is required");
            editPass.requestFocus();
            return;
        }

        if (pass.length() < 4) {
            editPass.setError("Password should be atleast 4 character Long");
            editPass.requestFocus();
            return;
        }

        LoginFE loginFarmer = new LoginFE(email, pass);

        Call<LoginFE> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(loginFarmer);

        call.enqueue(new Callback<LoginFE>() {
            @Override
            public void onResponse(Call<LoginFE> call, Response<LoginFE> response) {
                LoginFE loginResponse = response.body();

                if (response.code() == 200) {

                    if (loginResponse != null) {

                        new PrefManager(Login.this).saveLoginDetails(email, pass, loginResponse.getId());
                        new PrefManager(Login.this).saveToken(loginResponse.getToken());
                        Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getFeDetails();
                        startActivity(new Intent(Login.this, MainActivity.class));
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

            }
        });


    }

    private void getFeDetails() {
        Call<FeDetails> call = RetrofitClient
                .getInstance()
                .getApi()
                .getFeDetail(new PrefManager(Login.this).getToken(), new PrefManager(Login.this).getFeId());

        call.enqueue(new Callback<FeDetails>() {
            @Override
            public void onResponse(Call<FeDetails> call, Response<FeDetails> response) {
                FeDetails detailResponse = response.body();

                if (detailResponse != null) {
                    new PrefManager(Login.this).saveFEDetails(detailResponse.getName(), detailResponse.getContact(), detailResponse.getEmail(), detailResponse.getAvatar(), detailResponse.getAlt_contact(), detailResponse.getAccStatus(), detailResponse.getAddress(), detailResponse.getJobLocation().getState(), detailResponse.getJobLocation().getDisrict(), detailResponse.getJobLocation().getTehsil(), detailResponse.getJobLocation().getBlock(), detailResponse.getJobLocation().getVillage().toString());

                }else {
                    Toast.makeText(Login.this, "Details not Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeDetails> call, Throwable t) {
                //Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
