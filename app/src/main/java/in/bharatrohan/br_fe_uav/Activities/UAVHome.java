package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class UAVHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uavhome);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            startActivity(new Intent(UAVHome.this, UAVProfile.class));
            return true;
        } else if (id == R.id.action_pass) {
            startActivity(new Intent(UAVHome.this, ChangePassword.class));
            return true;
        } else if (id == R.id.action_drone_issue) {
            showDialogDrone();
            return true;
        } else if (id == R.id.action_imp_contacts) {
            startActivity(new Intent(UAVHome.this, UAV_FarmerInfo.class));
            return true;
        } else if (id == R.id.action_complain) {
            showDialogComplain();
            return true;
        }else if (id==R.id.action_logout){
            new PrefManager(UAVHome.this).saveLoginDetails("", "", "");
            new PrefManager(UAVHome.this).saveToken("");
            new PrefManager(UAVHome.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
            new PrefManager(UAVHome.this).saveUserType("");
            startActivity(new Intent(UAVHome.this, Login.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogDrone() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UAVHome.this);
        View view = getLayoutInflater().inflate(R.layout.drone_issue_dialog, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText title = view.findViewById(R.id.editTitle);
        EditText message = view.findViewById(R.id.editMessage);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(view1 -> {
            String strTitle = title.getText().toString().trim();
            String strMessage = message.getText().toString().trim();

            if (strTitle.isEmpty()) {
                title.setError("Title is required!");
                title.requestFocus();
                return;
            }

            if (strMessage.isEmpty()) {
                message.setError("Message is required!");
                message.requestFocus();
                return;
            }


            ///Server Operation


            alertDialog.dismiss();
        });
    }

    private void showDialogComplain() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UAVHome.this);
        View view = getLayoutInflater().inflate(R.layout.complain_issue_dialog, null);
        dialogBuilder.setView(view);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.show();

        EditText title = view.findViewById(R.id.editTitle);
        EditText message = view.findViewById(R.id.editMessage);
        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(view1 -> {
            String strTitle = title.getText().toString().trim();
            String strMessage = message.getText().toString().trim();

            if (strTitle.isEmpty()) {
                title.setError("Title is required!");
                title.requestFocus();
                return;
            }

            if (strMessage.isEmpty()) {
                message.setError("Message is required!");
                message.requestFocus();
                return;
            }


            ///Server Operation


            alertDialog.dismiss();
        });
    }
}
