package org.calmarj.sportboxrssreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.calmarj.sportboxrssreader.R;
import org.calmarj.sportboxrssreader.activity.DetailActivity;
import org.calmarj.sportboxrssreader.model.Channel;
import org.calmarj.sportboxrssreader.model.Item;


public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.ItemViewHolder> {

    private Channel channel;
    private Context mContext;

    public RSSAdapter(Channel channel, Context context) {
        this.channel = channel;
        this.mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rss_item, viewGroup, false);
        return new ItemViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindItem(channel.getItems().get(position));
    }

    @Override
    public int getItemCount() {
        return channel.getItems().size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTitleTextView;
        private final TextView mDescTextView;

        private Item item;

        private Context mContext;

        public ItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            mTitleTextView = (TextView) itemView.findViewById(R.id.info_text_title);
            mDescTextView = (TextView) itemView.findViewById(R.id.info_text_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (item != null) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.INTENT_TAG, item.getLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        }

        public void bindItem(Item item) {
            this.item = item;
            mTitleTextView.setText(item.getTitle());
            mDescTextView.setText(Html.fromHtml(item.getDescription()));
        }
    }


}
