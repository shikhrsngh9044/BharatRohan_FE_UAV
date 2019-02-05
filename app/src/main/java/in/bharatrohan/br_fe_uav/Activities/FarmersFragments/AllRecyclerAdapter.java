package in.bharatrohan.br_fe_uav.Activities.FarmersFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.feedingtrends.vocally.Interfaces.ItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.br_fe_uav.Activities.FarmerInfo;
import in.bharatrohan.br_fe_uav.Models.FarmerList;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class AllRecyclerAdapter extends RecyclerView.Adapter<AllRecyclerAdapter.AllViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<FarmerList.FarmersList> dataList;

    public AllRecyclerAdapter(Context context, List<FarmerList.FarmersList> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.farmers_row, parent, false);
        AllViewHolder allViewHolder = new AllViewHolder(itemView);
        return allViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllViewHolder holder, int position) {

        holder.farmerName.setText(dataList.get(position).getFarmer_name());
        holder.email.setText(dataList.get(position).getEmail());
        new PrefManager(mCtx).saveFarmerId(dataList.get(position).getId());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onPhoneClick(@NotNull View view, int position) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + dataList.get(position).getContact()));
                mCtx.startActivity(dialIntent);
            }

            @Override
            public void onSelectClick(@NotNull View view, int positions) {
                mCtx.startActivity(new Intent(mCtx, FarmerInfo.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class AllViewHolder extends RecyclerView.ViewHolder {

        TextView farmerName, email;
        ImageView phone, select;
        private ItemClickListener mClickListener;

        public AllViewHolder(View itemView) {
            super(itemView);

            farmerName = itemView.findViewById(R.id.tvFname);
            email = itemView.findViewById(R.id.tvEmail);

            phone = itemView.findViewById(R.id.imgPhone);
            select = itemView.findViewById(R.id.imgSelect);

            phone.setOnClickListener(v -> mClickListener.onPhoneClick(v, getAdapterPosition()));
            select.setOnClickListener(v -> mClickListener.onSelectClick(v, getAdapterPosition()));
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }
}
