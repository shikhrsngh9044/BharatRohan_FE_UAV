package in.bharatrohan.br_fe_uav.Activities.FarmersFragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
import in.bharatrohan.br_fe_uav.Models.UnFarmers;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class UnRecyclerAdapter extends RecyclerView.Adapter<UnRecyclerAdapter.UnViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<FarmerList.FarmersList> dataList;

    public UnRecyclerAdapter(Context context, List<FarmerList.FarmersList> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.farmers_row, parent, false);
        UnViewHolder unViewHolder = new UnViewHolder(itemView);
        return unViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnViewHolder holder, int position) {

        holder.farmerName.setText(dataList.get(position).getFarmer_name());
        holder.email.setText(dataList.get(position).getEmail());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onPhoneClick(@NotNull View view, int position) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + dataList.get(position).getContact()));
                mCtx.startActivity(dialIntent);
            }

            @Override
            public void onSelectClick(@NotNull View view, int position) {
                new PrefManager(mCtx).saveIsVisit(false);
                new PrefManager(mCtx).saveFarmerId(dataList.get(position).getId());
                mCtx.startActivity(new Intent(mCtx, FarmerInfo.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class UnViewHolder extends RecyclerView.ViewHolder {

        TextView farmerName, email;
        ImageView phone;
        ConstraintLayout select;
        private ItemClickListener mClickListener;

        public UnViewHolder(View itemView) {
            super(itemView);

            farmerName = itemView.findViewById(R.id.tvFname);
            email = itemView.findViewById(R.id.tvEmail);

            phone = itemView.findViewById(R.id.imgPhone);
            select = itemView.findViewById(R.id.constraintLayout8);

            phone.setOnClickListener(v -> mClickListener.onPhoneClick(v, getAdapterPosition()));
            select.setOnClickListener(v -> mClickListener.onSelectClick(v, getAdapterPosition()));
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }
}
