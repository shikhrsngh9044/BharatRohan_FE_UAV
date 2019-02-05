package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerInfo extends AppCompatActivity {

    private TextView landInfo;
    private TextView name, contact, email, address, dob;
    public ArrayList<String> farmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farmer_info);

        init();

        landInfo.setOnClickListener(v -> {
            startActivity(new Intent(FarmerInfo.this, MyFarms.class));
        });
    }

    private void init() {

        farmList = new ArrayList<>();

        landInfo = findViewById(R.id.landDetails);

        name = findViewById(R.id.tvName);
        contact = findViewById(R.id.tvContact);
        email = findViewById(R.id.tvEmail);
        address = findViewById(R.id.tvAddress);
        dob = findViewById(R.id.tvDob);

        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(this).getToken(), new PrefManager(this).getFarmerId());

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();

                if (farmer != null) {
                    name.setText(farmer.getName());
                    contact.setText(farmer.getContact());
                    email.setText(farmer.getEmail());
                    address.setText(farmer.getFull_address());
                    dob.setText(farmer.getDob());

                    //farmList = farmer.getFarms();

                    new PrefManager(FarmerInfo.this).saveFarmCount(farmer.getFarms().size());
                } else {
                    Toast.makeText(FarmerInfo.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                Toast.makeText(FarmerInfo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
