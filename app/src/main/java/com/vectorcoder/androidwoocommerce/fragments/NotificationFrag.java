package com.vectorcoder.androidwoocommerce.fragments;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.Notification_Adapter;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.notifications.Notification;
import com.vectorcoder.androidwoocommerce.models.notifications.NotificationData;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.myinnos.androidscratchcard.ScratchCard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Muhammad Nabeel on 28/03/2019.
 */
public class NotificationFrag extends Fragment {
    
    String customerID;
    RecyclerView noti_list;
    LinearLayoutManager linearLayout;
    Notification_Adapter notification_adapter;
    List<NotificationData> notificationList;
    View v;
    DialogLoader loader;
    TextView empty_record_text;
    float percentReveal;
    ScratchCard mScratchCard;
    TextView code_txt;
    private ArrayList<String> localCheck;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.notification_list, container, false);
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.push_notification));
        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
       
        notificationList = new ArrayList<>();
        loader = new DialogLoader(getContext());

        
        initUI(v);
        
        
        return v;
    }
    
    // Inflate all xml vies here
    private void initUI(View v){
    
        empty_record_text = v.findViewById(R.id.empty_record_text);
        noti_list = v.findViewById(R.id.noti_list);
        linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        noti_list.setLayoutManager(linearLayout);
        // Initialize Adapter
    
        localCheck = new ArrayList<>();
        RequestNotificationList();
       
        
    }
    
    
    private void RequestNotificationList(){
    
        loader.showProgressDialog();
        Map<String,String> map = new HashMap<>();
        map.put("user_id",customerID);
        Call<Notification> call = APIClient.getInstance().getNotificationList(map);
        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
            
                if (response.isSuccessful()){
                    notificationList.addAll(response.body().getData());
                    loader.hideProgressDialog();
    
                    notification_adapter = new Notification_Adapter(getContext(),notificationList);
                    noti_list.setAdapter(notification_adapter);
                    notification_adapter.setOnItemClickListener(new Notification_Adapter.OnItemClick() {
                        @Override
                        public void onItemClickListener(View v, int position) {
    
                            showScratchDialog(notificationList.get(position).getCode(),
                                    notificationList.get(position).getIs_view(),
                                    notificationList.get(position).getId());
                        
                        }
                    });
                    if(notificationList.size()<1){
                        empty_record_text.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    loader.hideProgressDialog();
                    Snackbar.make(v,response.message(),Snackbar.LENGTH_SHORT).show();
                }
                
                
            }
    
            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                loader.hideProgressDialog();
                Snackbar.make(v,"CallFailure "+t.toString(),Snackbar.LENGTH_SHORT).show();
            }
        });
        
    }
    
    private void RequestNotificationUpdate(String notification_id){
        
        Map<String,String> map = new HashMap<>();
        map.put("user_id",customerID);
        map.put("notification_id",notification_id);
        Call<String> call = APIClient.getInstance().updateNotificationList(map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                
                if (response.isSuccessful()){
    
                    Snackbar.make(v, response.body(),Snackbar.LENGTH_SHORT).show();
                }
                else {
                    loader.hideProgressDialog();
                    Snackbar.make(v,response.message(),Snackbar.LENGTH_SHORT).show();
                }
                
                
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loader.hideProgressDialog();
                Snackbar.make(v,"CallFailure "+t.toString(),Snackbar.LENGTH_SHORT).show();
            }
        });
        
    }
    
    public void showScratchDialog(final String scratchTV,String isView,final String notification_id){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.scratch_card_dialog, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
    
        
        mScratchCard = (ScratchCard) dialogView.findViewById(R.id.scratchCard);
        Button dialogBtn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        code_txt = dialogView.findViewById(R.id.code_txt);
    
    
        final AlertDialog alertDialog = dialog.create();
    
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        }
        
        if(isView.equalsIgnoreCase("1")){
            code_txt.setText(scratchTV);
            mScratchCard.setVisibility(View.GONE);
        }
        else if(localCheck.contains(notification_id)){
            code_txt.setText(scratchTV);
            mScratchCard.setVisibility(View.GONE);
        }
        else {
            mScratchCard.setOnScratchListener(new ScratchCard.OnScratchListener() {
                @Override
                public void onScratch(ScratchCard scratchCard, float visiblePercent) {
                    if (visiblePercent > 0.8) {
                        mScratchCard.setVisibility(View.GONE);
                        if (scratchTV.isEmpty()) {
                            code_txt.setText(getString(R.string.no_scratch));
                            code_txt.setTextColor(getResources().getColor(R.color.colorAccentRed));
                        } else {
                            code_txt.setText(scratchTV);
                            code_txt.setTextColor(getResources().getColor(R.color.colorAccentRed));
                            RequestNotificationUpdate(notification_id);
                            localCheck.add(notification_id);
                        }
                        //Toast.makeText(getContext(), "Content Visible", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        
      
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
            
                alertDialog.dismiss();
            }
        });
    
        alertDialog.show();
    }
    
}
