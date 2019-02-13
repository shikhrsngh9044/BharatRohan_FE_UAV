package in.bharatrohan.br_fe_uav.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feedingtrends.vocally.Interfaces.Item_ClickListener;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Models.FarmSolution;
import in.bharatrohan.br_fe_uav.R;

public class UAVProblemRecyclerAdapter extends RecyclerView.Adapter<UAVProblemRecyclerAdapter.FarmerViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private ArrayList<FarmSolution> dataList;

    public UAVProblemRecyclerAdapter(Context context, ArrayList<FarmSolution> dataList) {
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

        holder.name.setText(String.valueOf(dataList.get(position).getSolNo()));
        holder.address.setText(dataList.get(position).getSol());

        holder.setItemsClickListener((view, position1) -> {

        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class FarmerViewHolder extends RecyclerView.ViewHolder {

        TextView name, address, call;
        ImageView farmerImg, select;
        private Item_ClickListener mClickListener;

        public FarmerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvUavName);
            address = itemView.findViewById(R.id.tvUavAddress);
            call = itemView.findViewById(R.id.tvCall);
            farmerImg = itemView.findViewById(R.id.farmerImg);
            select = itemView.findViewById(R.id.imageView5);

            select.setOnClickListener(v -> mClickListener.onOptionClick(v, getAdapterPosition()));
        }

        public void setItemsClickListener(Item_ClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }
}
