package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.bharatrohan.br_fe_uav.R;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        showDialog();
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


            ///Server Operation


            alertDialog.dismiss();
        });
    }
}
