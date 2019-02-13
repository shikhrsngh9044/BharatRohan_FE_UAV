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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnverifiedFarmers extends Fragment {

    private UnRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<FarmerList.FarmersList> unVerifiedFarmersArrayList;

    public UnverifiedFarmers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unverified_farmers, container, false);

        init(view);

        return view;
    }

    private void init(View view) {
        unVerifiedFarmersArrayList = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recycler);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        Call<FarmerList> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUnFarmerList(new PrefManager(getActivity()).getToken(), new PrefManager(getActivity()).getUserId(), false);

        call.enqueue(new Callback<FarmerList>() {
            @Override
            public void onResponse(Call<FarmerList> call, Response<FarmerList> response) {
                hideProgress();
                if (response.body() != null) {
                    generateFarmerList(response.body().getFarmersLists());
                } else {
                    Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerList> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateFarmerList(List<FarmerList.FarmersList> allFarmersArrayList) {

        for (int i = 0; i < allFarmersArrayList.size(); i++) {
            if (!allFarmersArrayList.get(i).getVerified()) {

                unVerifiedFarmersArrayList.add(allFarmersArrayList.get(i));
            }
        }

        adapter = new UnRecyclerAdapter(getActivity(), unVerifiedFarmersArrayList);

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

}
