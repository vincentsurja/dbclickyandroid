package com.vectorcoder.androidwoocommerce.adapters;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.models.points.PointsList;

import java.util.List;

/**
 * Created by Muhammad Nabeel on 01/03/2019.
 */
public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.MyViewHolder> {
    
    Context context;
    List<PointsList> pointsLists;
    
    
    public PointsAdapter(Context context, List<PointsList> ordersList) {
        this.context = context;
        this.pointsLists = ordersList;
    }
    
    //********** Called to Inflate a Layout from XML and then return the Holder *********//
    
    @Override
    public PointsAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_points_layout, parent, false);
        
        return new PointsAdapter.MyViewHolder(itemView);
    }
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(final PointsAdapter.MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final PointsList pointsList = pointsLists.get(position);
        
     /*   if (Integer.parseInt(pointsList.getPoints())<=0) {
            holder.point_amout.setText("0");
        }
        else {*/
            holder.point_amout.setText(pointsList.getPoints());
       // }
    
        holder.event_from.setText(pointsList.getType());
        holder.point_date.setText(pointsList.getDate());
        
    }
    
    //********** Returns the total number of items in the data set *********//
    
    @Override
    public int getItemCount() {
        return pointsLists.size();
    }
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        
        TextView event_from,point_amout,point_date;
        public MyViewHolder(final View itemView) {
            super(itemView);
    
            event_from = itemView.findViewById(R.id.event_from);
            point_amout = itemView.findViewById(R.id.point_amout);
            point_date = itemView.findViewById(R.id.point_date);
        }
    }
}
