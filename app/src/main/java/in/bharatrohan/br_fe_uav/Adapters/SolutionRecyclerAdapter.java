package in.bharatrohan.br_fe_uav.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.feedingtrends.vocally.Interfaces.ItemClickListener;
import com.feedingtrends.vocally.Interfaces.Item_ClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import in.bharatrohan.br_fe_uav.Activities.FarmerInfo;
import in.bharatrohan.br_fe_uav.Models.AllFarmers;
import in.bharatrohan.br_fe_uav.Models.FarmSolution;
import in.bharatrohan.br_fe_uav.R;

public class SolutionRecyclerAdapter extends RecyclerView.Adapter<SolutionRecyclerAdapter.SolutionViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private ArrayList<FarmSolution> dataList;

    public SolutionRecyclerAdapter(Context context, ArrayList<FarmSolution> dataList) {
        mCtx = context;
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.soloution_row, parent, false);
        SolutionViewHolder solutionViewHolder = new SolutionViewHolder(itemView);
        return solutionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewHolder holder, int position) {

        holder.solNo.setText(String.valueOf(dataList.get(position).getSolNo()));
        holder.sol.setText(dataList.get(position).getSol());

        holder.setItemsClickListener((view, position1) -> {
            PopupMenu popupMenu = new PopupMenu(mCtx, holder.option);
            popupMenu.inflate(R.menu.solution_option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.apply: {
                        return true;
                    }

                    case R.id.comment: {
                        return true;
                    }

                    case R.id.fe_comment: {
                        return true;
                    }

                    default:
                        return false;
                }

            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class SolutionViewHolder extends RecyclerView.ViewHolder {

        TextView solNo, sol,option;
        ImageView tick;
        private Item_ClickListener mClickListener;

        public SolutionViewHolder(View itemView) {
            super(itemView);

            solNo = itemView.findViewById(R.id.solution_no);
            sol = itemView.findViewById(R.id.tv_solution);
            sol = itemView.findViewById(R.id.tv_solution);

            tick = itemView.findViewById(R.id.imgTick);
            option = itemView.findViewById(R.id.tvOptionDot);

            option.setOnClickListener(v -> mClickListener.onOptionClick(v, getAdapterPosition()));
        }

        public void setItemsClickListener(Item_ClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }
    }
}
