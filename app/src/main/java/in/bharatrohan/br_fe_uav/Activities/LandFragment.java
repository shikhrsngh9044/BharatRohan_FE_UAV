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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Adapters.SolutionRecyclerAdapter;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.FarmSolution;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandFragment extends Fragment {
    private SolutionRecyclerAdapter adapter;
    private ArrayList<FarmSolution> solutionArrayList;
    private RecyclerView recyclerView;
    private TextView tvCVisit, verifyFarm, farmStatus;
    private TextView landName, cropName;
    private ProgressBar progressBar;
    private ArrayList<String> farmList = new ArrayList<>();
    private String token, farmerId;

    public static LandFragment newInstance() {
        return new LandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
        solutionArrayList = new ArrayList<>();

        ListSolution();

        adapter = new SolutionRecyclerAdapter(getActivity(), solutionArrayList);
        recyclerView = view.findViewById(R.id.recycler);

        tvCVisit = view.findViewById(R.id.tvCvisit);
        farmStatus = view.findViewById(R.id.tvFarmStatus);
        verifyFarm = view.findViewById(R.id.tvVerifyFarm);
        landName = view.findViewById(R.id.tvLandName);
        cropName = view.findViewById(R.id.tvCropName);
        progressBar = view.findViewById(R.id.progressBar);


    }

    private void setVerifyFarmStatus(Boolean status) {
        if (status) {
            farmStatus.setText("Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_green_dark);
        } else {
            farmStatus.setText("Not Verified");
            farmStatus.setBackgroundResource(android.R.color.holo_red_dark);
        }
    }

    private void ListSolution() {
        solutionArrayList.add(new FarmSolution(1, "Kribhco Khad"));
        solutionArrayList.add(new FarmSolution(2, "Pesticide"));
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
                if (farm != null) {
                    if (farm.getData().getVerified() != null) {
                       // new PrefManager(getContext()).saveFarmStatus(farm.getData().getVerified());
                        setVerifyFarmStatus(farm.getData().getVerified());
                    }
                    new PrefManager(getContext()).saveFarmImage(farm.getData().getMap_image());
                    landName.setText(farm.getData().getFarm_name());
                    cropName.setText(farm.getData().getCrop().getCrop_name());
                } else {
                    Toast.makeText(getContext(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
