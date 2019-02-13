package in.bharatrohan.br_fe_uav.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class UAVProfile extends AppCompatActivity {

    private TextView name, phone, email, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uavprofile);

        init();

        name.setText(new PrefManager(UAVProfile.this).getName());
        phone.setText(new PrefManager(UAVProfile.this).getContact());
        email.setText(new PrefManager(UAVProfile.this).getEmail());
        address.setText(new PrefManager(UAVProfile.this).getAddress());

    }

    private void init() {
        name = findViewById(R.id.tvUavName);
        phone = findViewById(R.id.tvUavPhone);
        email = findViewById(R.id.tvUavEmail);
        address = findViewById(R.id.tvUavAddress);
    }
}
