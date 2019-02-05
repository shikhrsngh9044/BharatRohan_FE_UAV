package in.bharatrohan.br_fe_uav.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.AllFarmer;
import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.UnverifiedFarmers;

public class FarmersFragmentAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public FarmersFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
//        Bundle b = new Bundle();
//        b.putInt("position", position);
//        Fragment frag = LandFragment.newInstance();
//        frag.setArguments(b);
//        return frag;

        switch (position) {
            case 0: {
                AllFarmer allFarmer = new AllFarmer();
                return allFarmer;
            }

            case 1: {
                UnverifiedFarmers unverifiedFarmers = new UnverifiedFarmers();
                return unverifiedFarmers;
            }

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
