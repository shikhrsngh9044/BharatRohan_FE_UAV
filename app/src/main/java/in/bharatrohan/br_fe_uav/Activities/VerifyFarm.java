package in.bharatrohan.br_fe_uav.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyFarm extends AppCompatActivity {

    private Button verify;
    private ImageView mapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_farm);

        verify = findViewById(R.id.verifyFarm);
        mapImage = findViewById(R.id.mapImage);

        Picasso.get().load("http://bfe82c68.ngrok.io/" + new PrefManager(this).getFarmImage()).into(mapImage);

        verify.setOnClickListener(v -> farmVerify());
    }


    private void farmVerify() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().verifyFarm(new PrefManager(VerifyFarm.this).getToken(), new PrefManager(VerifyFarm.this).getFarmId());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(VerifyFarm.this, "Farm Verified!", Toast.LENGTH_SHORT).show();
                    VerifyFarm.this.finish();
                } else {
                    Toast.makeText(VerifyFarm.this, "Some error occurred.Try after some time!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(VerifyFarm.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
