package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.bharatrohan.br_fe_uav.Adapters.SolutionRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.Models.CropProblem;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.SolutionComment;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmDetails extends AppCompatActivity {

    private SolutionRecyclerAdapter adapter;
    private MaterialSpinner problemSpinner;
    private ArrayList<String> solutionArrayList, problemId;
    private RecyclerView recyclerView;
    private TextView tvCVisit, verifyFarm, farmStatus, imageWarn, solWarn, landName, cropName;
    private EditText comment;
    private ProgressBar progressBar;
    private String token, farmerId, problem_id, farmImg;
    private ArrayAdapter<String> adapter1;
    private boolean isFirst, isVerified;
    private ImageView solImage, headImage;
    private Button sendSolStatus;
    public ArrayList<CropProblem.Data.Solution.SolutionData> problemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_farm_details);
        initViews();

        tvCVisit.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateVisit.class));
            new PrefManager(this).saveFarmImage(farmImg);
        });

        verifyFarm.setOnClickListener(v -> {
            startActivity(new Intent(this, VerifyFarm.class));
            finish();
        });

        sendSolStatus.setOnClickListener(v -> {
            sendSolutionStatus();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFarmInfo(new PrefManager(FarmDetails.this).getFarmId());
    }

    private void initViews() {

        new CheckInternet(this).checkConnection();
        token = new PrefManager(this).getToken();
        farmerId = new PrefManager(this).getFarmerId();

        headImage = findViewById(R.id.imageView12);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);
        problemSpinner = findViewById(R.id.spinnerVisits);
        solImage = findViewById(R.id.solutionImage);
        sendSolStatus = findViewById(R.id.solStatus);

        recyclerView = findViewById(R.id.recycler);

        tvCVisit = findViewById(R.id.tvCvisit);
        farmStatus = findViewById(R.id.tvFarmStatus);
        verifyFarm = findViewById(R.id.tvVerifyFarm);
        comment = findViewById(R.id.comments);
        landName = findViewById(R.id.tvLandName);
        cropName = findViewById(R.id.tvCropName);
        progressBar = findViewById(R.id.progressBar);
        imageWarn = findViewById(R.id.textView23);
        solWarn = findViewById(R.id.textView24);


        comment.setMovementMethod(new ScrollingMovementMethod());

        showFarmInfo(new PrefManager(FarmDetails.this).getFarmId());
    }


    private void showFarmInfo(String farmId) {
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(token, farmId);

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                hideProgress();
                Farm farm = response.body();

                if (response.code() == 200) {

                    if (farm != null) {
                        if (farm.getData().getVerified()) {
                            farmStatus.setText("Verified");
                            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
                            verifyFarm.setVisibility(View.GONE);
                            tvCVisit.setVisibility(View.VISIBLE);
                        } else {
                            farmStatus.setText("Not Verified");
                            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
                            verifyFarm.setVisibility(View.VISIBLE);
                            tvCVisit.setVisibility(View.GONE);
                        }

                        farmImg = farm.getData().getMap_image();
                        problemId = new ArrayList<>();
                        problemId.clear();
                        problemId.addAll(farm.getData().getProblemId());
                        landName.setText(farm.getData().getFarm_name());
                        cropName.setText(farm.getData().getCrop().getCrop_name());
                        new PrefManager(FarmDetails.this).saveCropId(farm.getData().getCrop().getCrop_id());
                        isFirst = true;
                        initProblemSpinner();

                    } else {
                        Toast.makeText(FarmDetails.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    new PrefManager(FarmDetails.this).saveLoginDetails("", "", "");
                    new PrefManager(FarmDetails.this).saveToken("");
                    new PrefManager(FarmDetails.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(FarmDetails.this).saveUserType("");
                    Toast.makeText(FarmDetails.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FarmDetails.this, Login.class));
                    FarmDetails.this.finish();
                } else if (response.code() == 400) {
                    Toast.makeText(FarmDetails.this, "Bad Request", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 500) {
                    Toast.makeText(FarmDetails.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                hideProgress();
                Toast.makeText(FarmDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initProblemSpinner() {

        //reversing problem Id arrayList
        Collections.reverse(problemId);

        if (problemId.size() > 0) {

            solImage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            solWarn.setVisibility(View.GONE);
            imageWarn.setVisibility(View.GONE);

            solutionArrayList = new ArrayList<>();

            for (int i = 0; i < problemId.size(); i++) {
                solutionArrayList.add("Solution " + (i + 1));
            }

            //reversing problem tag arrayList
            Collections.reverse(solutionArrayList);

            adapter1 = new ArrayAdapter<>(FarmDetails.this, android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);


            if (isFirst) {
                getSolution(problemId.get(0));
            }

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
                problem_id = problemId.get(position);
                getSolution(problem_id);
            });


        } else {
            solutionArrayList = new ArrayList<>();
            solutionArrayList.add("N/A");
            adapter1 = new ArrayAdapter<>(FarmDetails.this, android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> Toast.makeText(FarmDetails.this, "No solutions are given", Toast.LENGTH_SHORT).show());
        }


    }


    private void getSolution(String prob_id) {
        showProgress();

        Call<CropProblem> call = RetrofitClient.getInstance().getApi().getProblemDetail(new PrefManager(FarmDetails.this).getToken(), prob_id);

        call.enqueue(new Callback<CropProblem>() {
            @Override
            public void onResponse(Call<CropProblem> call, Response<CropProblem> response) {
                hideProgress();
                CropProblem solutionList = response.body();
                if (response.code() == 200) {
                    if (solutionList != null) {
                        //generateSolList(solutionList.getData().getSolution().getSolutionDataList());
                        comment.setText(solutionList.getData().getSolution().getFe_comment());
                        //Toast.makeText(getContext(), solutionList.getData().getSolution().getSolutionDataList().toString(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load("http://br.bharatrohan.in/" + solutionList.getData().getSolution().getSolutionImage()).into(solImage);

                        if (solutionList.getData().getSolution().getSolutionDataList().size() > 0) {
                            comment.setVisibility(View.VISIBLE);
                            problemList = new ArrayList<>();
                            problemList.addAll(solutionList.getData().getSolution().getSolutionDataList());

                            adapter = new SolutionRecyclerAdapter(FarmDetails.this, solutionList.getData().getSolution().getSolutionDataList(), position -> {

                                problemList.get(position).set_status(true);
                                sendSolStatus.setVisibility(View.VISIBLE);
                                //Toast.makeText(getContext(), problemList.get(position).get_status().toString(), Toast.LENGTH_SHORT).show();
                            });

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FarmDetails.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } else if (response.code() == 400) {
                    Toast.makeText(FarmDetails.this, "Bad Request", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 500) {
                    Toast.makeText(FarmDetails.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(FarmDetails.this, "Record not found!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    new PrefManager(FarmDetails.this).saveLoginDetails("", "", "");
                    new PrefManager(FarmDetails.this).saveToken("");
                    new PrefManager(FarmDetails.this).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(FarmDetails.this).saveUserType("");
                    Toast.makeText(FarmDetails.this, "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FarmDetails.this, Login.class));
                    FarmDetails.this.finish();
                }
            }

            @Override
            public void onFailure(Call<CropProblem> call, Throwable t) {

            }
        });
    }


    private void sendSolutionStatus() {
        showProgress();

        String fe_comment = " ";
        fe_comment = comment.getText().toString().trim();

        SolutionComment solutionComment = new SolutionComment(problem_id, fe_comment, problemList);

        Call<SolutionComment> call = RetrofitClient.getInstance().getApi().solutionStatus(new PrefManager(FarmDetails.this).getToken(), solutionComment);

        call.enqueue(new Callback<SolutionComment>() {
            @Override
            public void onResponse(Call<SolutionComment> call, Response<SolutionComment> response) {
                hideProgress();
                if (response.code() == 200) {
                    Toast.makeText(FarmDetails.this, "Status and Comment Updated!", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(FarmDetails.this, "Bad Request", Toast.LENGTH_SHORT).show();
                    //Vaifation failed
                } else if (response.code() == 500) {
                    Toast.makeText(FarmDetails.this, "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 404) {
                    Toast.makeText(FarmDetails.this, "Record not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SolutionComment> call, Throwable t) {
                hideProgress();
                Toast.makeText(FarmDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


   /* private void generateSolList(List<CropProblem.Data.Solution.SolutionData> allSolArrayList) {
        problemList = new ArrayList<>();
        problemList.addAll(allSolArrayList);

        adapter = new SolutionRecyclerAdapter(FarmDetails.this, allSolArrayList, position -> {

            problemList.get(position).set_status(true);
            sendSolStatus.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), problemList.get(position).get_status().toString(), Toast.LENGTH_SHORT).show();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FarmDetails.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FarmDetails.this, FarmerFarms.class));
        finish();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
