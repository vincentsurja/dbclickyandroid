package com.vectorcoder.androidwoocommerce.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class HomePage_8 extends Fragment {


    FragmentManager fragmentManager;
    StartAppRequests startAppRequests;
    List<CategoryDetails> allCategoriesList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_8, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        startAppRequests = new StartAppRequests(getContext());

        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Get FragmentManager
        fragmentManager = getFragmentManager();

        // Add ProductsNewest Fragment to specified FrameLayout
        Fragment productsNewest = new ProductsNewest();
        productsNewest.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.newest_fragment_home8, productsNewest).commit();


        // Add ProductsOnSale Fragment to specified FrameLayout
        Fragment productsOnSale = new ProductsOnSale();
        productsOnSale.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.super_deals_fragment_home8, productsOnSale).commit();


        // Add FeaturedProducts Fragment to specified FrameLayout
        Fragment allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putBoolean("on_sale", false);
        bundleInfo.putBoolean("featured", true);
        bundleInfo.putBoolean("is_bottombar_dissabled", true);
        allProducts.setArguments(bundleInfo);
        fragmentManager.beginTransaction().replace(R.id.featured_fragment_home8, allProducts).commit();

        if (allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            setupCategories();

        return rootView;

    }

    private void setupCategories() {

        // Add Category Fragment to specified FrameLayout
        Fragment categoryFragment = new Categories_1_horizontal_small();
        Bundle categoryBundle = new Bundle();
        categoryBundle.putBoolean("isMenuItem", false);
        categoryBundle.putBoolean("isHeaderVisible", false);
        categoryFragment.setArguments(categoryBundle);
        fragmentManager.beginTransaction().replace(R.id.category_fragment_home8, categoryFragment).commit();

    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Check for Internet Connection from the static method of Helper class
            if (Utilities.isNetworkAvailable(getContext())) {

                // Call the method of StartAppRequests class to process App Startup Requests
                startAppRequests.RequestAllCategories(startAppRequests.page_number);

                return "1";
            } else {

                return "0";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equalsIgnoreCase("1")) {
                setupCategories();
            }

        }

    }


}
