package com.vectorcoder.androidwoocommerce.fragments;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.adapters.OrdersListAdapter;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;

import retrofit2.Call;
import retrofit2.Callback;


public class My_Orders extends Fragment {

    View rootView;
    String customerID;
    
    AdView mAdView;
    TextView emptyRecord;
    AppCompatButton exploreBtnMyOrder;
    LinearLayout emptyLayout;
    FrameLayout banner_adView;
    RecyclerView orders_recycler;

    DialogLoader dialogLoader;
    OrdersListAdapter ordersListAdapter;

    List<OrderDetails> ordersList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_orders, container, false);

//        ((MainActivity)getActivity()).toggleNavigaiton(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionOrders));

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        
        
        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        exploreBtnMyOrder = (AppCompatButton) rootView.findViewById(R.id.exploreBtnMyOrder);
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        orders_recycler = (RecyclerView) rootView.findViewById(R.id.orders_recycler);
        emptyLayout = rootView.findViewById(R.id.emptyLayout);

        exploreBtnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showHomePage();
            }
        });

        if (ConstantValues.IS_ADMOBE_ENABLED) {
            // Initialize Admobe
            mAdView = new AdView(getContext());
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            banner_adView.addView(mAdView);
            mAdView.loadAd(adRequest);
        }
        
        

        // Hide some of the Views
        emptyLayout.setVisibility(View.GONE);
        dialogLoader = new DialogLoader(getContext());
    
        
        ordersList = new ArrayList<>();
    
    
        ordersListAdapter = new OrdersListAdapter(getContext(), ordersList);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        orders_recycler.setAdapter(ordersListAdapter);
        orders_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        
        
        // Request for User's Orders
        if (!ConstantValues.IS_GUEST_LOGGED_IN) {
            RequestMyOrders();
        }
        else {
            emptyLayout.setVisibility(View.VISIBLE);
        }


        return rootView;
    }



    //*********** Request User's Orders from the Server ********//

    public void RequestMyOrders() {

        dialogLoader.showProgressDialog();
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("per_page", String.valueOf(100));
        params.put("customer", String.valueOf(customerID));
        params.put("lang",ConstantValues.LANGUAGE_CODE);
        params.put("currency",ConstantValues.CURRENCY_CODE);
        

        Call<List<OrderDetails>> call = APIClient.getInstance()
                .getAllOrders
                        (
                                params
                        );

        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, retrofit2.Response<List<OrderDetails>> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    String orderDetail = new Gson().toJson(response.body());
                    getOrdersList(response.body());
                    //ordersList.addAll(response.body());
                    ordersListAdapter.notifyDataSetChanged();
    
    
                    if (ordersListAdapter.getItemCount() < 1)
                        emptyLayout.setVisibility(View.VISIBLE);
                    
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void getOrdersList(List<OrderDetails> orderDetails){
        
        for (int i=0;i<orderDetails.size();i++){
            if(orderDetails.get(i).getParent_id()==0){
                ordersList.add(orderDetails.get(i));
            }
        }
    }

}

