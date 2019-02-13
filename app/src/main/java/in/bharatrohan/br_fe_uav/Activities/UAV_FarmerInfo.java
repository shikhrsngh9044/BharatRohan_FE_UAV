package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import in.bharatrohan.br_fe_uav.R;

public class UAV_FarmerInfo extends AppCompatActivity {


    private Button btnDownKml, btnScanRse;
    private TextView Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uav__farmer_info);
        init();

        btnScanRse.setOnClickListener(v -> {
            scanBarcode();
        });
    }

    private void init() {
        btnDownKml = findViewById(R.id.btnDownKml);
        btnScanRse = findViewById(R.id.btnScanRse);
        Qr = findViewById(R.id.tvPhone);
    }


    public void scanBarcode() {
        Intent intent = new Intent(this, BarcodeReaderActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (requestCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    Qr.setText(barcode.displayValue);
                    Toast.makeText(UAV_FarmerInfo.this, barcode.displayValue.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UAV_FarmerInfo.this, "No QR found", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
