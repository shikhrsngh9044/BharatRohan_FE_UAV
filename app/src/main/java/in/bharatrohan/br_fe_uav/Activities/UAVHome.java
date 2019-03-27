package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.AllRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Adapters.UAVProblemRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.Models.UavDetails;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UAVHome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UAVProblemRecyclerAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uavhome);
        new CheckInternet(this).checkConnection();
        Toolbar toolbar2 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar2);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.farmerrecycler);

        getFarmers();
    }

    private void getFarmers() {
        showProgress();
        Call<UavDetails> call =
                RetrofitClient.getInstance().getApi().getUavFarmerDetail(new PrefManager(UAVHome.this).getToken(), new PrefManager(UAVHome.this).getUserId());

        call.enqueue(new Callback<UavDetails>() {
            @Override
            public void onResponse(Call<UavDetails> call, Response<UavDetails> response) {
                hideProgress();
                UavDetails detailsResponse = response.body();

                if (response.code() == 200) {
                    if (detailsResponse != null) {
                        generateFarmerList(detailsResponse.getCropProblem());
                    } else {
                        Toast.makeText(UAVHome.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    new PrefManager(UAVHome.this).saveLoginDetails("", "", "");
                    new PrefManager(UAVHome.this).saveToken("");
                    new PrefManager(UAVHome.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(UAVHome.this).saveUserType("");
                    Toast.makeText(UAVHome.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UAVHome.this, Login.class));
                    finish();
                } else if (response.code() == 500) {
                    Toast.makeText(UAVHome.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UavDetails> call, Throwable t) {
                hideProgress();
                Toast.makeText(UAVHome.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void generateFarmerList(List<UavDetails.CropProblem> allFarmersArrayList) {

        if (allFarmersArrayList.size() < 1) {
            Toast.makeText(UAVHome.this, "No Farmer Crops Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new UAVProblemRecyclerAdapter(this, allFarmersArrayList);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

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
            startActivity(new Intent(UAVHome.this, ImpContacts.class));
            return true;
        } else if (id == R.id.action_complain) {
            startActivity(new Intent(UAVHome.this, FeedbackComplaints.class));
            return true;
        } else if (id == R.id.action_logout) {
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

            if (!strTitle.isEmpty() || !strMessage.isEmpty()) {

                Intent send = new Intent(Intent.ACTION_SENDTO);
                send.putExtra(Intent.EXTRA_SUBJECT, strTitle);
                send.putExtra(Intent.EXTRA_TEXT, strMessage);
                String uriText = "mailto:" + Uri.encode("rishabh@bharatrohan.in");
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));

            } else {
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

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
