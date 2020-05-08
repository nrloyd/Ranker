package com.example.ranker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<RankGroup> groups;
    private final LayoutInflater layoutInflater;

    public GroupRecyclerAdapter(Context context, List<RankGroup> groups) {
        this.context = context;
        this.groups = groups;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_group_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankGroup group = groups.get(position);
        holder.textTitle.setText(group.getTitle());
        holder.currentPosition = position;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textTitle;
        public int currentPosition;
        public final ImageButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            button = (ImageButton) itemView.findViewById(R.id.edit_list_button);

            textTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if unsorted, start RankerActivity
                    if(groups.get(currentPosition).isSorted() == 0) {
                        Intent intent = new Intent(context, RankerActivity.class);
                        intent.putExtra(RankerActivity.GROUP_INFO, currentPosition);
                        context.startActivity(intent);
                    } //if sorted, skip straight to ResultsActivity
                    else {
                        Intent intent = new Intent(context, ResultsActivity.class);
                        intent.putExtra(RankerActivity.GROUP_INFO, currentPosition);
                        context.startActivity(intent);
                    }
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CreateListActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
