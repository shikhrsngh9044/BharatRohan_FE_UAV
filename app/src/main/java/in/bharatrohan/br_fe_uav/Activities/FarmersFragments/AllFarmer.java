package in.bharatrohan.br_fe_uav.Activities.FarmersFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.AllFarmers;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFarmer extends Fragment {

    private AllRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    public AllFarmer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_farmer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);

        Call<FarmerList> call = RetrofitClient
                .getInstance()
                .getApi()
                .getFarmerList(new PrefManager(getActivity()).getToken(), new PrefManager(getActivity()).getFeId());

        call.enqueue(new Callback<FarmerList>() {
            @Override
            public void onResponse(Call<FarmerList> call, Response<FarmerList> response) {

                if (response.body() != null) {
                    generateFarmerList(response.body().getFarmersLists());
                } else {
                    Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerList> call, Throwable t) {

            }
        });


        ;
    }

    private void generateFarmerList(List<FarmerList.FarmersList> allFarmersArrayList) {
        adapter = new AllRecyclerAdapter(getActivity(), allFarmersArrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
