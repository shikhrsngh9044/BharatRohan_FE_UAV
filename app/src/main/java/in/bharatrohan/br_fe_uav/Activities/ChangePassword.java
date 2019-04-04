package in.bharatrohan.br_fe_uav.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    private Button btnChangePass;
    private EditText editPass, editConfPass, editOtp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        new CheckInternet(this).checkConnection();
        init();

        btnChangePass.setOnClickListener(v -> {
            if (!validateForm()) {
                if (new PrefManager(ChangePassword.this).getUserType().equals("uav")) {
                    changePassRequestUav();
                } else if (new PrefManager(ChangePassword.this).getUserType().equals("fe")) {
                    changePassRequestFe();
                }
            } else {
                validateForm();
            }
        });

    }

    private void init() {
        btnChangePass = findViewById(R.id.btnChangePass);
        editPass = findViewById(R.id.editPass);
        editConfPass = findViewById(R.id.editConfPass);
        editOtp = findViewById(R.id.editOtp);
        progressBar = findViewById(R.id.progressBar);
    }


    private void changePassRequestUav() {
        String pass = editPass.getText().toString().trim();
        String otp = editOtp.getText().toString().trim();
        showProgress();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changePassUav(new PrefManager(ChangePassword.this).getTempEmail(), otp, pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Password changed successfully!! ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();

                } else if (response.code() == 404) {
                    Toast.makeText(ChangePassword.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(ChangePassword.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassRequestFe() {
        String pass = editPass.getText().toString().trim();
        String otp = editOtp.getText().toString().trim();
        showProgress();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changePassFe(new PrefManager(ChangePassword.this).getTempEmail(), otp, pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Password changed successfully!! ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();
                } else if (response.code() == 404) {
                    Toast.makeText(ChangePassword.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePassword.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(ChangePassword.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private Boolean validateForm() {
        String pass = editPass.getText().toString().trim();
        String conf_pass = editConfPass.getText().toString().trim();
        String otp = editOtp.getText().toString().trim();

        if (pass.isEmpty()) {
            editPass.setError("Password is required!!");
            editPass.requestFocus();
            return true;
        }

        if (pass.length() < 4) {
            editPass.setError("Password length should not less than 4 character");
            editPass.requestFocus();
            return true;
        }

        if (!conf_pass.equals(pass)) {
            editConfPass.setError("Confirm Password is not matching!!");
            editConfPass.requestFocus();
            return true;
        }

        if (otp.isEmpty()) {
            editOtp.setError("OTP is required!!");
            editOtp.requestFocus();
            return true;
        }

        return false;
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

        btnChangePass.setEnabled(false);
        editPass.setEnabled(false);
        editConfPass.setEnabled(false);
        editOtp.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);

        btnChangePass.setEnabled(true);
        editPass.setEnabled(true);
        editConfPass.setEnabled(true);
        editOtp.setEnabled(true);
    }
}
