package com.vectorcoder.androidwoocommerce.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.DownloadAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.download.DownloadsModel;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Muhammad Nabeel on 07/11/2018.
 */
public class Download extends Fragment {
    
    View rootView;
    String customerID;
    
    AdView mAdView;
    TextView emptyRecord;
    AppCompatButton exploreBtn;
    FrameLayout banner_adView;
    RecyclerView orders_recycler;
    LinearLayout emptyLayout;
    
    DialogLoader dialogLoader;
    DownloadAdapter downloadAdapter;
    
    List<DownloadsModel> downloadList;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.download_fragment, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.download));
    
        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
    
    
        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_recordDownloads);
        orders_recycler = (RecyclerView) rootView.findViewById(R.id.download_recycler);
        exploreBtn = rootView.findViewById(R.id.exploreBtnDownloads);
        emptyLayout = rootView.findViewById(R.id.emptyLayoutDownloads);
    
        // Hide some of the Views
        emptyLayout.setVisibility(View.GONE);
        dialogLoader = new DialogLoader(getContext());
    
        downloadList = new ArrayList<>();
    
        
        downloadAdapter = new DownloadAdapter(getContext(),getActivity(), downloadList);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        orders_recycler.setAdapter(downloadAdapter);
        orders_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    
    
        // Request for User's Downlaod
        if (!ConstantValues.IS_GUEST_LOGGED_IN) {
            RequestMyDownloads();
        }
        else {
            emptyLayout.setVisibility(View.VISIBLE);
        }

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showHomePage();
            }
        });


        return rootView;
    }
    
    //*********** Request User's Orders from the Server ********//
    
    public void RequestMyDownloads() {
        
        dialogLoader.showProgressDialog();
        
        Call<List<DownloadsModel>> call = APIClient.getInstance()
                .getDownload
                        (
                                customerID
                        );
        
        call.enqueue(new Callback<List<DownloadsModel>>() {
            @Override
            public void onResponse(Call<List<DownloadsModel>> call, retrofit2.Response<List<DownloadsModel>> response) {
                
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    downloadList.addAll(response.body());
                    downloadAdapter.notifyDataSetChanged();
                    
                    
                    if (downloadAdapter.getItemCount() < 1)
                        emptyLayout.setVisibility(View.VISIBLE);
                    
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<List<DownloadsModel>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}
