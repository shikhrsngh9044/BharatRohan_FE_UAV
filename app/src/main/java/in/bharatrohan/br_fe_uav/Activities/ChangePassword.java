package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
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
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    private Button getOtp, btnChangePass;
    private EditText editPass, editConfPass, editOtp;
    private TextView resendOtp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();

        getOtp.setOnClickListener(v -> showDialog());

        resendOtp.setOnClickListener(v -> {
            if (new PrefManager(ChangePassword.this).getUserType().equals("uav")) {
                getOtpRequestUav(new PrefManager(this).getEmail());
            } else if (new PrefManager(ChangePassword.this).getUserType().equals("fe")) {
                getOtpRequestFe(new PrefManager(this).getEmail());
            }
        });

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
        getOtp = findViewById(R.id.btnGetOtp);
        btnChangePass = findViewById(R.id.btnChangePass);
        editPass = findViewById(R.id.editPass);
        editConfPass = findViewById(R.id.editConfPass);
        editOtp = findViewById(R.id.editOtp);
        resendOtp = findViewById(R.id.tvResend);
        progressBar = findViewById(R.id.progressBar);
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChangePassword.this);
        View view = getLayoutInflater().inflate(R.layout.change_pass_dialog, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText email = view.findViewById(R.id.editEmail);
        Button btnOk = view.findViewById(R.id.btnOk);

        email.setText(new PrefManager(ChangePassword.this).getEmail());

        btnOk.setOnClickListener(view1 -> {
            String strEmail = email.getText().toString().trim();

            if (strEmail.isEmpty()) {
                email.setError("Email is required!");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                email.setError("Enter a valid Email!!");
                email.requestFocus();
                return;
            }

            if (new PrefManager(ChangePassword.this).getUserType().equals("uav")) {
                getOtpRequestUav(strEmail);
            } else if (new PrefManager(ChangePassword.this).getUserType().equals("fe")) {
                getOtpRequestFe(strEmail);
            }


            alertDialog.dismiss();
        });
    }


    private void getOtpRequestUav(String email) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getOtpUav(new PrefManager(ChangePassword.this).getToken(), email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Otp sent successfully.Check your registered mail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "Some error occurred. Please try again later!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changePassRequestUav() {
        String pass = editPass.getText().toString().trim();
        String otp = editOtp.getText().toString().trim();
        showProgress();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changePassUav(new PrefManager(ChangePassword.this).getToken(), new PrefManager(ChangePassword.this).getEmail(), otp, pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Password changed successfully!! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "Some error occurred. Please try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getOtpRequestFe(String email) {
        showProgress();
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().getOtpFe(new PrefManager(ChangePassword.this).getToken(), email);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Otp sent successfully.Check your registered mail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "Some error occurred. Please try again later!!", Toast.LENGTH_SHORT).show();
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

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().changePassFe(new PrefManager(ChangePassword.this).getToken(), new PrefManager(ChangePassword.this).getEmail(), otp, pass);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(ChangePassword.this, "Password changed successfully!! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePassword.this, "Some error occurred. Please try again later!", Toast.LENGTH_SHORT).show();
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

        getOtp.setEnabled(false);
        btnChangePass.setEnabled(false);
        editPass.setEnabled(false);
        editConfPass.setEnabled(false);
        editOtp.setEnabled(false);
        resendOtp.setEnabled(false);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);

        getOtp.setEnabled(true);
        btnChangePass.setEnabled(true);
        editPass.setEnabled(true);
        editConfPass.setEnabled(true);
        editOtp.setEnabled(true);
        resendOtp.setEnabled(true);
    }
}
