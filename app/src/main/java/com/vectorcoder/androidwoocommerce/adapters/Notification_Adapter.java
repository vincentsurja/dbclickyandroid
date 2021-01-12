package com.vectorcoder.androidwoocommerce.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;
import com.vectorcoder.androidwoocommerce.models.notifications.NotificationData;

import java.util.List;

/**
 * Created by Muhammad Nabeel on 28/03/2019.
 */
public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder> {
    
    Context context;
    List<NotificationData> notificationDataList;
    private OnItemClick listener;
    
    public Notification_Adapter(Context context, List<NotificationData> notificationDataList){
        this.context = context;
        this.notificationDataList = notificationDataList;
     
    }
    
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_noti,viewGroup,false);
        
        return new MyViewHolder(v);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
    
        NotificationData notificationData = notificationDataList.get(i);
        if(notificationData.getType().equalsIgnoreCase("1")){
            myViewHolder.noti_heading.setText(R.string.noti_desc_for_scratch_card);
            myViewHolder.noti_desc.setText(notificationData.getMessage());
        }
        else {
            myViewHolder.noti_desc.setText(notificationData.getMessage());
        }
        myViewHolder.noti_time.setText(context.getString(R.string.noti_expiry)+" "+notificationData.getExpireDate());
        
        myViewHolder.main_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(view,i);
            }
        });
        
    }
    
    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }
    
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CircularImageView noti_icon;
        TextView noti_heading,noti_desc,noti_time;
        CardView main_card;
    
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
    
            noti_time = itemView.findViewById(R.id.noti_time);
            noti_desc = itemView.findViewById(R.id.noti_desc);
            noti_heading = itemView.findViewById(R.id.noti_heading);
            noti_icon = itemView.findViewById(R.id.noti_icon);
            main_card = itemView.findViewById(R.id.main_card);
            
        }
    }
    
    public interface OnItemClick{
        
        void onItemClickListener(View v,int position);
    }
    
    public void setOnItemClickListener(OnItemClick listener) {
        this.listener = listener;
    }
    
}
