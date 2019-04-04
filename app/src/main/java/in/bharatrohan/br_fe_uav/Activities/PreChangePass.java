package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreChangePass extends AppCompatActivity {

    private EditText username, email;
    private Button next;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_change_pass);
        init();

        next.setOnClickListener(v -> {
            if (!validateForm()) {
                String strEmail = email.getText().toString().trim();
                String strUsername = username.getText().toString().trim();

                if (strUsername.contains("fe")) {
                    new PrefManager(PreChangePass.this).saveUserType("fe");
                    getOtpRequestFe(strEmail);

                } else if (strUsername.contains("uav")) {
                    new PrefManager(PreChangePass.this).saveUserType("uav");
                    getOtpRequestUav(strEmail);
                }
            } else {
                validateForm();
            }
        });
    }

    private void init() {
        username = findViewById(R.id.editUsername);
        email = findViewById(R.id.editEmail);
        next = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.progressBar);
    }


    private void getOtpRequestUav(String email) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getOtpUav(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    new PrefManager(PreChangePass.this).saveTempEmail(email);
                    startActivity(new Intent(PreChangePass.this, ChangePassword.class));
                    Toast.makeText(PreChangePass.this, "Otp sent successfully.Check your registered mail", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(PreChangePass.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreChangePass.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(PreChangePass.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(PreChangePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getOtpRequestFe(String email) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getOtpFe(email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    new PrefManager(PreChangePass.this).saveTempEmail(email);
                    startActivity(new Intent(PreChangePass.this, ChangePassword.class));
                    Toast.makeText(PreChangePass.this, "Otp sent successfully.Check your registered mail", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(PreChangePass.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PreChangePass.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(PreChangePass.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(PreChangePass.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean validateForm() {

        String strEmail = email.getText().toString().trim();
        String strUsername = username.getText().toString().trim();

        if (strEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return true;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            email.setError("Enter a valid Email!!");
            email.requestFocus();
            return true;
        }

        if (strUsername.isEmpty()) {
            email.setError("Username is required!");
            email.requestFocus();
            return true;
        }

        return false;
    }


    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

        email.setEnabled(false);
        username.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);

        email.setEnabled(false);
        username.setEnabled(false);
    }
}
