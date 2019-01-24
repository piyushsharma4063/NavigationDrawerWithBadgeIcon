package com.navigationdrawer.android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NavRecAdapter extends RecyclerView.Adapter<NavRecAdapter.ViewHolder> {

    private List<NavDrawer> mList;
    private onItemClickListerner mListerner;

    public NavRecAdapter(List<NavDrawer> mList, onItemClickListerner mListerner) {
        this.mList = mList;
        this.mListerner = mListerner;
    }

    @NonNull
    @Override
    public NavRecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.list_view_item_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NavRecAdapter.ViewHolder holder, int position) {
        NavDrawer movie = mList.get(position);
        holder.name.setText(movie.name);
        holder.imageView.setImageResource(movie.icon);
        holder.notificationCount.setText(movie.count);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, notificationCount;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.textViewName);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            notificationCount = (TextView) itemView.findViewById(R.id.notification_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListerner.onClick(getAdapterPosition());
                }
            });
        }

    }

    public interface onItemClickListerner {
        void onClick(int pos);
    }

}
