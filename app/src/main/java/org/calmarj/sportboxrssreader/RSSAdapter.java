package org.calmarj.sportboxrssreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.calmarj.sportboxrssreader.utils.RSSChannel;
import org.calmarj.sportboxrssreader.utils.RSSItem;

/**
 * Created by calmarj on 02.11.15.
 */
public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.RSSItemViewHolder> {

    private RSSChannel rssChannel;
    private Context mContext;

    public RSSAdapter(RSSChannel rssChannel, Context context) {
        this.rssChannel = rssChannel;
        this.mContext = context;
    }

    @Override
    public RSSItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rss_item, viewGroup, false);
        return new RSSItemViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(RSSItemViewHolder holder, int position) {
        holder.bindItem(rssChannel.getItems().get(position));
    }

    @Override
    public int getItemCount() {
        return rssChannel.getItems().size();
    }


    public static class RSSItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTextView;

        private RSSItem item;

        private Context mContext;

        public RSSItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.mContext = context;
            mTextView = (TextView) itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        public TextView getTextView() {
            return mTextView;
        }

        @Override
        public void onClick(View v) {
            if (item != null) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.INTENT_TAG, item.getLink());
                mContext.startActivity(intent);
            }
        }

        public void bindItem(RSSItem item) {
            this.item = item;
            mTextView.setText(item.getTitle());
        }
    }


}
