package in.bharatrohan.br_fe_uav.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.AllFarmer;
import in.bharatrohan.br_fe_uav.Activities.FarmersFragments.UnverifiedFarmers;
import in.bharatrohan.br_fe_uav.Activities.VisitsFragments.DailyVisit;
import in.bharatrohan.br_fe_uav.Activities.VisitsFragments.UpcomingVisit;

public class VisitsFragmentAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public VisitsFragmentAdapter(FragmentManager fm, int NumOfTabs) {
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
                DailyVisit dailyVisit = new DailyVisit();
                return dailyVisit;
            }

            case 1: {
                UpcomingVisit upcomingVisit = new UpcomingVisit();
                return upcomingVisit;
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
