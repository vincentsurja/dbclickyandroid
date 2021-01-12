package com.vectorcoder.androidwoocommerce.fragments;


import androidx.annotation.Nullable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class ProductsFeatured extends Fragment {

    String customerID;
    boolean isHeaderVisible,isHorizontal,isViewAll;;
    private Call<List<ProductDetails>> networkCall;
    
    ProgressBar loadingProgress;
    TextView emptyRecord, headerText;
    RecyclerView featured_recycler;

    ProductAdapter productAdapter;

    List<ProductDetails> featuredProductsList;
    boolean isGridView;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    AppCompatTextView  view_all_txt;
    Fragment fragment;
    FragmentManager fragmentManager;


    public void invalidateProducts(){
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        if(getArguments()!=null) {
            isHorizontal = getArguments().getBoolean("isHorizontal");
            isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            if(getArguments().containsKey("isViewAll")) {
                isViewAll = getArguments().getBoolean("isViewAll");
            }

        }

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        view_all_txt = rootView.findViewById(R.id.view_all_txt);
        loadingProgress = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        featured_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);


        // Replace fragment
        fragmentManager = getFragmentManager();

        view_all_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("featured", true);
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                fragment = new Products();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });
    
        
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.featured));
        }
        if(isViewAll){
            view_all_txt.setVisibility(View.VISIBLE);
        }
        else {
            view_all_txt.setVisibility(View.GONE);
        }


        featuredProductsList = new ArrayList<>();
        

        // RecyclerView has fixed Size
        featured_recycler.setHasFixedSize(true);
        if(!isHorizontal) {
            featured_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            // Initialize the ProductAdapter for RecyclerView
            productAdapter = new ProductAdapter(getContext(), featuredProductsList, true);


        }
        else {
            // Request for gridLayout
            isGridView = true;
            // Initialize GridLayoutManager and LinearLayoutManager
            gridLayoutManager = new GridLayoutManager(getContext(), 2);
            linearLayoutManager = new LinearLayoutManager(getContext());
            // Initialize the ProductAdapter for RecyclerView
            productAdapter = new ProductAdapter(getContext(), featuredProductsList, false);
            featured_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
            setRecyclerViewLayoutManager(isGridView);
        }

        // Set the Adapter and LayoutManager to the RecyclerView
        featured_recycler.setAdapter(productAdapter);
        // Request for Most Sold Products
        RequestFeaturedItems();


        return rootView;
    }
    //*********** Switch RecyclerView's LayoutManager ********//

    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;

        // If a LayoutManager has already been set, get current Scroll Position
        if (featured_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) featured_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        productAdapter.toggleLayout(isGridView);

        featured_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        featured_recycler.setAdapter(productAdapter);

        featured_recycler.scrollToPosition(scrollPosition);
    }


    //*********** Adds Products returned from the Server to the topSellerProductsList ********//
    
    private void addProducts(List<ProductDetails> productList) {
        
        // Add Products to mostLikedProductList
        if (productList.size() > 0) {
            featuredProductsList.addAll(productList);
        }
    
        for (int i=0;  i<productList.size();  i++) {
            if (productList.get(i).getStatus() != null  &&  !"publish".equalsIgnoreCase(productList.get(i).getStatus())) {
                for (int j=0;  j<featuredProductsList.size();  j++) {
                    if (productList.get(i).getId() == featuredProductsList.get(j).getId()) {
                        featuredProductsList.remove(j);
                    }
                }
            }
        }
        
        productAdapter.notifyDataSetChanged();
        
        
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    }



    //*********** Request all the Products from the Server based on the Sales of Products ********//

    private void RequestFeaturedItems() {
    
        loadingProgress.setVisibility(View.VISIBLE);
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("featured", "true");
        params.put("lang",ConstantValues.LANGUAGE_CODE);
        params.put("currency",ConstantValues.CURRENCY_CODE);
    
        networkCall = APIClient.getInstance()
                .getAllProducts
                        (
                                params
                        );
    
        networkCall.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
    
                loadingProgress.setVisibility(View.GONE);
                
                if (response.isSuccessful()) {
                
                    addProducts(response.body());
                
                }
                else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }

//                    Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        
            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                loadingProgress.setVisibility(View.GONE);
                if (!call.isCanceled())
                    Toast.makeText(App.getContext(), "NetworkCallFailure : "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public void onPause() {

        // Check if NetworkCall is being executed
        if (networkCall.isExecuted()){
            // Cancel the NetworkCall
            networkCall.cancel();
        }

        super.onPause();
    }
}