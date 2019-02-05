package in.bharatrohan.br_fe_uav.Activities.FarmersFragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Models.UnFarmers;
import in.bharatrohan.br_fe_uav.R;

public class UnRecyclerAdapter extends RecyclerView.Adapter<UnRecyclerAdapter.UnViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private ArrayList<UnFarmers> dataList;

    public UnRecyclerAdapter(Context context, ArrayList<UnFarmers> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.un_farmers_row, parent, false);
        UnViewHolder unViewHolder = new UnViewHolder(itemView);
        return unViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnViewHolder holder, int position) {
        holder.phone.setOnClickListener(v -> {

        });

        holder.select.setOnClickListener(v->{

        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class UnViewHolder extends RecyclerView.ViewHolder {

        TextView farmerName, upVisit;
        ImageView phone, select;

        public UnViewHolder(View itemView) {
            super(itemView);

            farmerName = itemView.findViewById(R.id.tvFname);
            upVisit = itemView.findViewById(R.id.tvUpVisit);

            phone = itemView.findViewById(R.id.imgPhone);
            select = itemView.findViewById(R.id.imgSelect);
        }
    }
}
