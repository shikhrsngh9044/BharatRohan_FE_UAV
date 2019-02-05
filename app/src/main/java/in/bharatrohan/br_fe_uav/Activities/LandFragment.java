package in.bharatrohan.br_fe_uav.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandFragment extends Fragment {
    private SolutionRecyclerAdapter adapter;
    private ArrayList<FarmSolution> solutionArrayList;
    private RecyclerView recyclerView;
    private TextView tvCVisit;
    private TextView tvLandName, tvCropName;
    private String farmId;

    public static LandFragment newInstance() {
        return new LandFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.land_fragment_layout, container, false);

        initViews(view);

        tvLandName.setText(new PrefManager(getActivity()).getLandName());
        tvCropName.setText(new PrefManager(getActivity()).getCropName());

        farmId = new PrefManager(getActivity()).getFarmId();

        tvCVisit.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateVisit.class));
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*if (!farmId.equals(new PrefManager(getActivity()).getFarmId())){
            showFarmInfo(new PrefManager(getActivity()).getFarmId());
        }else {
            showFarmInfo(farmId);
        }*/
    }

    private void initViews(View view) {
        solutionArrayList = new ArrayList<>();

        ListSolution();

        adapter = new SolutionRecyclerAdapter(getActivity(), solutionArrayList);
        recyclerView = view.findViewById(R.id.recycler);
        tvCVisit = view.findViewById(R.id.tvCvisit);
        tvLandName = view.findViewById(R.id.landName);
        tvCropName = view.findViewById(R.id.cropName);


    }


    private void ListSolution() {
        solutionArrayList.add(new FarmSolution(1, "Kribhco Khad"));
        solutionArrayList.add(new FarmSolution(2, "Pesticide"));
    }


    /*private void showFarmInfo(String farmId) {
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(new PrefManager(getActivity()).getToken(), farmId);

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                Farm farm = response.body();
                if (farm != null) {
                    //Toast.makeText(getActivity(), farmId, Toast.LENGTH_SHORT).show();
                    tvLandName.setText(farm.getData().getFarm_name());
                    tvCropName.setText(farm.getData().getCrop().getCrop_name());

                    new PrefManager(getActivity()).saveCropId(farm.getData().getCrop().getCrop_id());
                } else {
                    Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        tvCVisit.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CreateVisit.class));
        });
    }*/

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
        //showFarmInfo();
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
