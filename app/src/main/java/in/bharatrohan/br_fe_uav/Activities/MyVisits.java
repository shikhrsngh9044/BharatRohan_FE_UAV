package in.bharatrohan.br_fe_uav.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.bharatrohan.br_fe_uav.Adapters.FarmersFragmentAdapter;
import in.bharatrohan.br_fe_uav.Adapters.VisitsFragmentAdapter;
import in.bharatrohan.br_fe_uav.R;

public class MyVisits extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private ImageView headImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_visits);
        initViews();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        headImage = findViewById(R.id.head_img);
        Picasso.get().load(R.drawable.my_farm_header).fit().centerCrop().into(headImage);

        mTabLayout = findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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


        mTabLayout.addTab(mTabLayout.newTab().setText("Daily Visit"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Upcoming Visit"));

        VisitsFragmentAdapter mFragmentAdapter = new VisitsFragmentAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager.setAdapter(mFragmentAdapter);
        viewPager.setCurrentItem(0);
    }


}
