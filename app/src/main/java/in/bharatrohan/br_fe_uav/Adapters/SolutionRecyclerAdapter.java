package in.bharatrohan.br_fe_uav.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import in.bharatrohan.br_fe_uav.Models.CropProblem;
import in.bharatrohan.br_fe_uav.R;

public class SolutionRecyclerAdapter extends RecyclerView.Adapter<SolutionRecyclerAdapter.SolutionViewHolder> {

    private Context mCtx;
    private final LayoutInflater layoutInflater;
    private List<CropProblem.Data.Solution.SolutionData> dataList;

    public SolutionRecyclerAdapter(Context context, List<CropProblem.Data.Solution.SolutionData> dataList) {
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

        holder.sol.setText(dataList.get(position).getSolText());
        holder.solNo.setText(String.valueOf(position + 1));
        holder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dataList.get(position).getSolColor())));


        holder.setItemsClickListener(new Item_ClickListener() {
            @Override
            public void onOptionClick(@NotNull View view, int position) {
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
            }

            @Override
            public void onSelectClick(@NotNull View view, int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class SolutionViewHolder extends RecyclerView.ViewHolder {

        TextView solNo, sol, option;
        ImageView tick;
        private Item_ClickListener mClickListener;

        public SolutionViewHolder(View itemView) {
            super(itemView);

            solNo = itemView.findViewById(R.id.solution_no);
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
