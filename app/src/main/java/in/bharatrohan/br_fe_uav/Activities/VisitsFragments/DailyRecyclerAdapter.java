package in.bharatrohan.br_fe_uav.Activities.VisitsFragments;

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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import in.bharatrohan.br_fe_uav.Activities.FarmerInfo;
import in.bharatrohan.br_fe_uav.Activities.MainActivity;
import in.bharatrohan.br_fe_uav.Models.FeVisitsModel;
import in.bharatrohan.br_fe_uav.PrefManager;
import in.bharatrohan.br_fe_uav.R;

public class DailyRecyclerAdapter extends RecyclerView.Adapter<DailyRecyclerAdapter.DailyViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<FeVisitsModel.Farmer> dataList;

    public DailyRecyclerAdapter(Context mCtx, List<FeVisitsModel.Farmer> dataList) {
        this.mCtx = mCtx;
        layoutInflater = LayoutInflater.from(mCtx);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.uav_farmer_list_item, parent, false);
        DailyViewHolder allViewHolder = new DailyViewHolder(itemView);
        return allViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.address.setText(dataList.get(position).getFull_address());

        if (dataList.get(position).getAvatar() != null) {
            Picasso.get().load("br.bharatrohan.in" + dataList.get(position).getAvatar()).fit().centerCrop().networkPolicy(NetworkPolicy.OFFLINE).into(holder.farmerImg, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    //Toast.makeText(UserProfile.this, "Didn't got Pic", Toast.LENGTH_SHORT).show();
                    Picasso.get().load(R.drawable.profile_pic).fit().centerCrop().into(holder.farmerImg);
                }
            });
        } else {
            Picasso.get().load(R.drawable.profile_pic).fit().centerCrop().into(holder.farmerImg);
        }


        holder.setItemsClickListener(new ItemClickListener() {
            @Override
            public void onPhoneClick(@NotNull View view, int position) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + dataList.get(position).getContact()));
                mCtx.startActivity(dialIntent);
            }

            @Override
            public void onSelectClick(@NotNull View view, int position) {
                new PrefManager(mCtx).saveFarmerId(dataList.get(position).getFarmer_id());
                mCtx.startActivity(new Intent(mCtx, FarmerInfo.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, call;
        ImageView farmerImg, select;
        private ItemClickListener mClickListener;

        public DailyViewHolder(View itemView) {
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
