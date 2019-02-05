package in.bharatrohan.br_fe_uav.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Api.RetrofitClient;
import in.bharatrohan.br_fe_uav.Models.Farm;
import in.bharatrohan.br_fe_uav.Models.Farmer;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFarms extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private int farm_count;
    private FarmerInfo farmerInfo = new FarmerInfo();
    private int farmNo;
    String farmId;
    private ArrayList<String> farmList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_farms);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mFragmentAdapter != null) {
            mFragmentAdapter.notifyDataSetChanged();
        }

    }

    private void initViews() {

        farm_count = new PrefManager(this).getFarmerFarmCount();

        viewPager = findViewById(R.id.viewpager);

        mTabLayout = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                saveFarmId(tab.getPosition());
                showFarmInfo();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setDynamicFragmentToTabLayout();
    }


    private void setDynamicFragmentToTabLayout() {
        for (int i = 1; i <= farm_count; i++) {

            mTabLayout.addTab(mTabLayout.newTab().setText("Land: " + i));
        }
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);
    }

    private void saveFarmId(int farmNo) {
        Call<Farmer> call = RetrofitClient.getInstance().getApi().getFarmerDetail(new PrefManager(MyFarms.this).getToken(), new PrefManager(MyFarms.this).getFarmerId());

        call.enqueue(new Callback<Farmer>() {
            @Override
            public void onResponse(Call<Farmer> call, Response<Farmer> response) {
                Farmer farmer = response.body();

                if (farmer != null) {
                    farmList = farmer.getFarms();
                    farmId = farmList.get(farmNo);
                    //Toast.makeText(getActivity(), farmId, Toast.LENGTH_SHORT).show();
                    new PrefManager(MyFarms.this).saveFarmId(farmId);
                } else {
                    Toast.makeText(MyFarms.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farmer> call, Throwable t) {
                Toast.makeText(MyFarms.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFarmInfo() {
        Call<Farm> call = RetrofitClient.getInstance().getApi().getFarmDetail(new PrefManager(this).getToken(), new PrefManager(MyFarms.this).getFarmId());

        call.enqueue(new Callback<Farm>() {
            @Override
            public void onResponse(Call<Farm> call, Response<Farm> response) {
                Farm farm = response.body();
                if (farm != null) {
                    //Toast.makeText(getActivity(), farmId, Toast.LENGTH_SHORT).show();
                    new PrefManager(MyFarms.this).saveFarmDeatils(farm.getData().getFarm_name(), farm.getData().getCrop().getCrop_name());

                    new PrefManager(MyFarms.this).saveCropId(farm.getData().getCrop().getCrop_id());
                } else {
                    Toast.makeText(MyFarms.this, "Some error occurred.Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Farm> call, Throwable t) {

            }
        });


    }

}
