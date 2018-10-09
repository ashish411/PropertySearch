package com.example.ashis.propertysearch;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ashis.propertysearch.data.PropertyContract;

import java.util.ArrayList;

/**
 * Created by ashis on 8/11/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    final private ListItemClickListener mOnClickListener;
    private String dealerOwner;

    public RecyclerViewAdapter(Cursor mCursor, Context mContext, ListItemClickListener listener) {
        this.mCursor = mCursor;
        this.mContext = mContext;
        this.mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onItemClick(int click);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.single_item_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        long rowNo = mCursor.getInt(mCursor.getColumnIndex(PropertyContract.PropertyEntry._ID));
        String price = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PRICE));
        String plot = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PLOT));
        String sector = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_SECTOR));
        String pkt = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_PKT));
        String area = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_AREA));
        String remarks = mCursor.getString(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_REMARKS));
        int count = mCursor.getInt(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_DEALER));
        int colour = mCursor.getInt(mCursor.getColumnIndex(PropertyContract.PropertyEntry.COLUMN_COLOR));
        holder.itemView.setBackgroundColor(Utility.getColor(colour));
        if (colour==1 || colour==3){
            holder.mRowInfoTxt.setTextColor(Color.WHITE);
        }
        else {
            holder.mRowInfoTxt.setTextColor(Color.BLACK);
        }



        if (count == 1){
            dealerOwner = "Dealer";
        }
        else if (count == 0){
            dealerOwner = "Mine";
        }



        int size = Integer.parseInt(area);
        if (size>400){
            area = size + " sqFt";
        } else if (size<=400){
            area = size + " m";
        }


        holder.itemView.setTag(rowNo);
        ArrayList<String> mList = new ArrayList<>();
        mList.add(sector);
        mList.add(pkt);
        mList.add(plot);
        mList.add(area);
        mList.add(price + "lac");
        mList.add(remarks);
        String s = TextUtils.join("/",mList);
//        holder.mRowInfoTxt.setText((position + 1) + ". " +
//                sector + "/" + pkt + "/"+plot + "/" +area + "/"+bedroom + "/"+floor + "/"+price +
//                " lac/"+societyName+"/"+remarks);
        holder.mRowInfoTxt.setText((position+1)+"."+s);

//        Property mProp = mList.get(position);
//
//        holder.mRowInfoTxt.setText(String.valueOf(mProp.getRowNo()));
//        holder.mPriceTxt.setText("Rs "+mProp.getPrice());
//        holder.mPlotTxt.setText(mProp.getPlot());
//        holder.mSectorTxt.setText(mProp.getSector());

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRowInfoTxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRowInfoTxt = (TextView) itemView.findViewById(R.id.rowInfo);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mCursor.moveToPosition(getAdapterPosition());
            mOnClickListener.onItemClick(mCursor.getInt(mCursor.getColumnIndex(PropertyContract.PropertyEntry._ID)));

        }
    }



}
