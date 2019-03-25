package in.bharatrohan.br_fe_uav.Activities.VisitsFragments;


import android.content.Intent;
import android.os.Bundle;
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

import in.bharatrohan.br_fe_uav.Activities.Login;
import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.FeVisitsModel;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyVisit extends Fragment {

    private DailyRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<FeVisitsModel.Farmer> upcomingVisitsList;


    public DailyVisit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_visit, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        upcomingVisitsList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler);
        progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getList();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /*if (isVisibleToUser && isResumed()) {
            getList();
        }*/
    }

    private void getList() {
        showProgress();

        Call<FeVisitsModel> call = RetrofitClient.getInstance().getApi().getUpcomingVisit(new PrefManager(getContext()).getToken(), new PrefManager(getContext()).getUserId());

        call.enqueue(new Callback<FeVisitsModel>() {
            @Override
            public void onResponse(Call<FeVisitsModel> call, Response<FeVisitsModel> response) {
                hideProgress();
                FeVisitsModel feVisitsModel = response.body();
                if (response.code() == 200) {
                    if (feVisitsModel != null) {
                        generateFarmerList(feVisitsModel.getFarmerTodayList());
                    } else {
                        Toast.makeText(getActivity(), "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<FeVisitsModel> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateFarmerList(List<FeVisitsModel.Farmer> allFarmersArrayList) {

        if (allFarmersArrayList.size() < 1) {
            Toast.makeText(getContext(), "There is no farmer to show", Toast.LENGTH_SHORT).show();
        }

        adapter = new DailyRecyclerAdapter(getContext(), allFarmersArrayList);

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
