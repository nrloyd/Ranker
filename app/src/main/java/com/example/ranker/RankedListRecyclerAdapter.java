package com.example.ranker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RankedListRecyclerAdapter extends RecyclerView.Adapter<RankedListRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<String> ranks;
    private final List<String> items;
    private final LayoutInflater layoutInflater;


    public RankedListRecyclerAdapter(Context context, List<String> ranks, List<String> items) {
        this.context = context;
        this.ranks = ranks;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_ranked_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String rank = ranks.get(position);
        String item = items.get(position);
        holder.textRank.setText(rank);
        holder.textTitle.setText(item);
        holder.currentPosition = position;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textRank;
        public final TextView textTitle;
        public int currentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textRank = (TextView) itemView.findViewById(R.id.rank_text);
            textTitle = (TextView) itemView.findViewById(R.id.item_text);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ResultsActivity.class);
                    intent.putExtra(ResultsActivity.GROUP_INFO, currentPosition);
                    context.startActivity(intent);
                }
            });*/

        }
    }
}
