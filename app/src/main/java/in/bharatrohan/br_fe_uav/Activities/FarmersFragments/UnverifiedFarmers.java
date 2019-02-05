package in.bharatrohan.br_fe_uav.Activities.FarmersFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.bharatrohan.br_fe_uav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnverifiedFarmers extends Fragment {


    public UnverifiedFarmers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unverified_farmers, container, false);
    }

}
