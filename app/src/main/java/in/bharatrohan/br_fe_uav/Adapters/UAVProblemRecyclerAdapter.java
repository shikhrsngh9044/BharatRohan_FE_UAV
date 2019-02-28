package in.bharatrohan.br_fe_uav.Adapters;

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

import com.bumptech.glide.Glide;
import com.feedingtrends.vocally.Interfaces.ItemClickListener;
import com.feedingtrends.vocally.Interfaces.Item_ClickListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import in.bharatrohan.br_fe_uav.Activities.UAV_FarmerInfo;
import in.bharatrohan.br_fe_uav.Models.FarmSolution;
import in.bharatrohan.br_fe_uav.Models.UavDetails;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class UAVProblemRecyclerAdapter extends RecyclerView.Adapter<UAVProblemRecyclerAdapter.FarmerViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<UavDetails.CropProblem> dataList;

    public UAVProblemRecyclerAdapter(Context context, List<UavDetails.CropProblem> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.uav_farmer_list_item, parent, false);
        FarmerViewHolder solutionViewHolder = new FarmerViewHolder(itemView);
        return solutionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerViewHolder holder, int position) {

        holder.name.setText(dataList.get(position).getFarmer().getFarmerName());
        holder.address.setText(dataList.get(position).getFarmer().getFull_address());
        Picasso.get().load(dataList.get(position).getFarmer().getAvatar()).fit().centerCrop().into(holder.farmerImg);

        holder.setItemsClickListener(new ItemClickListener() {
            @Override
            public void onPhoneClick(@NotNull View view, int position) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + dataList.get(position).getFarmer().getContact()));
                mCtx.startActivity(dialIntent);
            }

            @Override
            public void onSelectClick(@NotNull View view, int position) {
                new PrefManager(mCtx).saveFarmerDetails(dataList.get(position).getProblemId(), dataList.get(position).getFarmer().getEmail(), dataList.get(position).getFarmer().getFarmerName(), dataList.get(position).getFarmer().getContact(), dataList.get(position).getFarmer().getFull_address(), dataList.get(position).getFarmer().getAvatar(), dataList.get(position).getFarm().getFarmName(), dataList.get(position).getFarm().getLocation(), dataList.get(position).getFarm().getFarmArea(), dataList.get(position).getFarm().getKml(), dataList.get(position).getFe().getName(), dataList.get(position).getFe().getContact(), dataList.get(position).getFe().getEmail());
                mCtx.startActivity(new Intent(mCtx, UAV_FarmerInfo.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class FarmerViewHolder extends RecyclerView.ViewHolder {

        TextView name, address, call;
        ImageView farmerImg, select;
        private ItemClickListener mClickListener;

        public FarmerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvUavName);
            address = itemView.findViewById(R.id.tvUavAddress);
            call = itemView.findViewById(R.id.tvCall);
            farmerImg = itemView.findViewById(R.id.farmerImg);
            select = itemView.findViewById(R.id.imageView5);

            select.setOnClickListener(v -> mClickListener.onSelectClick(v, getAdapterPosition()));
            call.setOnClickListener(v -> mClickListener.onPhoneClick(v, getAdapterPosition()));
        }

        public void setItemsClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }
}
