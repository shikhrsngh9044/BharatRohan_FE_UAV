package in.bharatrohan.br_fe_uav.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.L;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.AllRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Adapters.SolutionRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.CheckInternet;
import in.bharatrohan.br_fe_uav.Models.CropProblem;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.FarmSolution;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.Models.Image;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandFragment extends Fragment {
    private SolutionRecyclerAdapter adapter;
    private MaterialSpinner problemSpinner;
    private ArrayList<String> solutionArrayList;
    private ArrayList<String> problemId;
    private RecyclerView recyclerView;
    private TextView tvCVisit, verifyFarm, farmStatus, comment;
    private TextView landName, cropName;
    private ProgressBar progressBar;
    private ArrayList<String> farmList = new ArrayList<>();
    private String token, farmerId;
    private ImageView img;
    private ArrayAdapter<String> adapter1;
    private boolean isverified;
    private ImageView solImage;

    public static LandFragment newInstance() {
        return new LandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CheckInternet(getContext()).checkConnection();
        token = new PrefManager(getContext()).getToken();
        farmerId = new PrefManager(getContext()).getFarmerId();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.land_fragment_layout, container, false);

        initViews(view);

        tvCVisit.setOnClickListener(v -> startActivity(new Intent(getActivity(), CreateVisit.class)));

        verifyFarm.setOnClickListener(v -> startActivity(new Intent(getActivity(), VerifyFarm.class)));

        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (new PrefManager(getActivity()).getFarmerFarmCount() == 1) {
            new PrefManager(getActivity()).saveFarmNo(0);
        }
        getFarmId(new PrefManager(getActivity()).getFarmerFarmNo());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isResumed()) {
            if (new PrefManager(getActivity()).getFarmerFarmCount() == 1) {
                new PrefManager(getActivity()).saveFarmNo(0);
            }
            getFarmId(new PrefManager(getActivity()).getFarmerFarmNo());
        }

    }

    private void initViews(View view) {
        problemSpinner = view.findViewById(R.id.spinnerVisits);
        solImage = view.findViewById(R.id.solutionImage);

        recyclerView = view.findViewById(R.id.recycler);

        tvCVisit = view.findViewById(R.id.tvCvisit);
        farmStatus = view.findViewById(R.id.tvFarmStatus);
        verifyFarm = view.findViewById(R.id.tvVerifyFarm);
        img = view.findViewById(R.id.imageView14);
        comment = view.findViewById(R.id.comments);

        ImageView img2 = view.findViewById(R.id.imageView13);
        landName = view.findViewById(R.id.tvLandName);
        cropName = view.findViewById(R.id.tvCropName);
        progressBar = view.findViewById(R.id.progressBar);


        comment.setMovementMethod(new ScrollingMovementMethod());

        if (new PrefManager(getContext()).getIsVisit()) {
            tvCVisit.setVisibility(View.VISIBLE);
            img2.setVisibility(View.VISIBLE);
        } else {
            tvCVisit.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
        }


    }

    private void setVerifyFarmStatus(Boolean status) {
        if (status) {
            farmStatus.setText("Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
            img.setVisibility(View.GONE);
            verifyFarm.setVisibility(View.GONE);
        } else {
            farmStatus.setText("Not Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
            img.setVisibility(View.VISIBLE);
            verifyFarm.setVisibility(View.VISIBLE);

        }
    }


    private void showFarmInfo(String farmId) {
        //showProgress();

        new PrefManager(getContext()).saveFarmId(farmId);
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(token, farmId);

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                hideProgress();
                Farm farm = response.body();

                if (response.code() == 200) {

                    if (farm != null) {
                        if (farm.getData().getVerified() != null) {
                            isverified = farm.getData().getVerified();
                            setVerifyFarmStatus(farm.getData().getVerified());
                        }
                        new PrefManager(getContext()).saveFarmImage(farm.getData().getMap_image());
                        problemId = new ArrayList<>();
                        problemId.addAll(farm.getData().getProblemId());
                        landName.setText(farm.getData().getFarm_name());
                        cropName.setText(farm.getData().getCrop().getCrop_name());
                        new PrefManager(getContext()).saveCropId(farm.getData().getCrop().getCrop_id());

                        initProblemSpinner();

                        /*if (problemId.size() > 0) {
                            solutionArrayList = new ArrayList<>();
                            initProblemSpinner();
                            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
                                String problem_id = problemId.get(position);

                                getSolution(problem_id);
                            });
                        } else {
                            solutionArrayList = new ArrayList<>();
                            solutionArrayList.add("N/A");
                            initProblemSpinner();
                            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
                                Toast.makeText(getContext(), "No solutions are given", Toast.LENGTH_SHORT).show();
                            });
                        }*/


                    } else {
                        Toast.makeText(getContext(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 401) {
                    new PrefManager(getContext()).saveLoginDetails("", "", "");
                    new PrefManager(getContext()).saveToken("");
                    new PrefManager(getContext()).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(getContext()).saveUserType("");
                    Toast.makeText(getContext(), "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getFarmId(int farmNo) {

        showProgress();
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(token, farmerId);

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();

                if (response.code() == 200) {

                    if (farmer != null) {
                        new PrefManager(getContext()).saveFarmerAvatar(farmer.getAvatar());
                        farmList = farmer.getFarms();
                        if (farmList.size() != 0) {
                            showFarmInfo(farmList.get(farmNo));
                        } else {
                            new PrefManager(getContext()).saveFarmNo(0);
                            Toast.makeText(getContext(), "No Farm is Registered yet!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();

                    }
                } else if (response.code() == 401) {
                    new PrefManager(getContext()).saveLoginDetails("", "", "");
                    new PrefManager(getContext()).saveToken("");
                    new PrefManager(getContext()).saveUserDetails("", "", "", "", false, "", "", "", "", "", "");
                    new PrefManager(getContext()).saveUserType("");
                    Toast.makeText(getContext(), "Token Expired", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Login.class));
                    getActivity().finish();
                } else if (response.code() == 500) {
                    Toast.makeText(getContext(), "Server Error: Please try after some time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initProblemSpinner() {

        if (problemId.size() > 0) {
            solutionArrayList = new ArrayList<>();

            for (int i = 0; i < problemId.size(); i++) {
                solutionArrayList.add("Solution " + (i + 1));
            }

            adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> {
                String problem_id = problemId.get(position);

                getSolution(problem_id);
            });

        } else {
            solutionArrayList = new ArrayList<>();

            solutionArrayList.add("N/A");
            adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, solutionArrayList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            problemSpinner.setAdapter(adapter1);

            problemSpinner.setOnItemSelectedListener((view, position, id, item) -> Toast.makeText(getContext(), "No solutions are given", Toast.LENGTH_SHORT).show());
        }


    }


    private void getSolution(String prob_id) {
        showProgress();

        Call<CropProblem> call = RetrofitClient.getInstance().getApi().getProblemDetail(new PrefManager(getContext()).getToken(), prob_id);

        call.enqueue(new Callback<CropProblem>() {
            @Override
            public void onResponse(Call<CropProblem> call, Response<CropProblem> response) {
                hideProgress();
                CropProblem solutionList = response.body();
                if (response.code() == 200) {
                    if (solutionList != null) {
                        generateSolList(solutionList.getData().getSolution().getSolutionDataList());
                        //Toast.makeText(getContext(), solutionList.getData().getSolution().getSolutionDataList().toString(), Toast.LENGTH_SHORT).show();
                        Picasso.get().load("http://br.bharatrohan.in/" + solutionList.getData().getSolution().getSolutionImage()).into(solImage);
                    }
                } else {
                    Toast.makeText(getContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CropProblem> call, Throwable t) {

            }
        });
    }

    private void generateSolList(List<CropProblem.Data.Solution.SolutionData> allSolArrayList) {

        adapter = new SolutionRecyclerAdapter(getActivity(), allSolArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        setVerifyFarmStatus(isverified);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
