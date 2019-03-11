package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.Models.CreatesVisit;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVisit extends AppCompatActivity {

    private int etId = 0;
    private int count = 1;
    private LinearLayout ll;
    private Button add, remove, create, skip;
    private List<EditText> quesEds = new ArrayList<>();
    private List<EditText> ansEds = new ArrayList<>();
    private List<CreatesVisit.Questions> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_visit);
        new CheckInternet(this).checkConnection();
        ll = findViewById(R.id.linearLayout);
        add = findViewById(R.id.addQ);
        remove = findViewById(R.id.removeQ);
        create = findViewById(R.id.create);
        skip = findViewById(R.id.skip);

        questionsList = new ArrayList<>();

        add.setOnClickListener(v -> {
            addEdits();
        });

        remove.setOnClickListener(v -> {
            removeEdits();
        });

        create.setOnClickListener(v -> {
            upload();
        });

        skip.setOnClickListener(v -> {
            this.finish();
        });

    }

    private void upload() {

        if (quesEds.size() != 0) {

            for (int i = 0; i < quesEds.size(); i++) {
                questionsList.add(i, new CreatesVisit.Questions(quesEds.get(i).getText().toString(), ansEds.get(i).getText().toString()));
            }

            CreatesVisit createsVisit = new CreatesVisit(new PrefManager(this).getFarmerId(), new PrefManager(this).getFarmId(), new PrefManager(this).getUavId(), new PrefManager(this).getCropId(), new PrefManager(this).getUserId(), questionsList);

            Call<CreatesVisit> call = RetrofitClient.getInstance().getApi().createVisit(new PrefManager(CreateVisit.this).getToken(), createsVisit);

            call.enqueue(new Callback<CreatesVisit>() {
                @Override
                public void onResponse(Call<CreatesVisit> call, Response<CreatesVisit> response) {
                    CreatesVisit createsVisit1 = response.body();

                    if (response.code() == 201) {
                        if (createsVisit1 != null) {
                            new PrefManager(CreateVisit.this).saveProblemId(createsVisit1.getData().getProblem().getId());
                            Toast.makeText(CreateVisit.this, createsVisit1.getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateVisit.this, UploadImage.class));
                            finish();
                        } else {
                            Toast.makeText(CreateVisit.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                        }
                    } else if (response.code() == 401) {
                        new PrefManager(CreateVisit.this).saveLoginDetails("", "", "");
                        new PrefManager(CreateVisit.this).saveToken("");
                        new PrefManager(CreateVisit.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                        new PrefManager(CreateVisit.this).saveUserType("");
                        Toast.makeText(CreateVisit.this, "Token Expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateVisit.this, Login.class));
                        finish();
                    } else if (response.code() == 500) {
                        Toast.makeText(CreateVisit.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CreatesVisit> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(CreateVisit.this, "Enter questions and answers first", Toast.LENGTH_SHORT).show();
        }

    }

    private void addEdits() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(dpToPx(15), dpToPx(10), dpToPx(15), dpToPx(10));


        EditText et1 = new EditText(this);
        quesEds.add(et1);
        et1.setLayoutParams(lp);
        et1.setBackgroundResource(R.drawable.rounded_edittext);
        et1.setHint("Enter Question " + count);
        et1.setGravity(Gravity.CENTER);
        et1.setTextColor(getResources().getColor(R.color.textColor));
        et1.setHintTextColor(666666);
        et1.setId(etId + 1);
        ll.addView(et1);
        etId++;

        EditText et2 = new EditText(this);
        ansEds.add(et2);
        et2.setLayoutParams(lp);
        et2.setBackgroundResource(R.drawable.rounded_edittext);
        et2.setHint("Enter Answer " + count);
        et2.setGravity(Gravity.CENTER);
        et2.setTextColor(getResources().getColor(R.color.textColor));
        et2.setHintTextColor(666666);
        et2.setId(etId + 1);
        ll.addView(et2);
        etId++;

        count++;
    }

    private void removeEdits() {
        if (ll.getChildCount() != 0) {
            ll.removeViewAt(ll.getChildCount() - 1);
            ll.removeViewAt(ll.getChildCount() - 1);
            etId--;
            etId--;
            count--;
        } else {
            Toast.makeText(CreateVisit.this, "No fields left!!", Toast.LENGTH_SHORT).show();
        }
    }

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
