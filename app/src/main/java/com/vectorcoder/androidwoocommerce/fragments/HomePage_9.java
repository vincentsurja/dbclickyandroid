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
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.ViewPagerCustomAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.ArrayList;
import java.util.List;


public class HomePage_9 extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    StartAppRequests startAppRequests;
    FragmentManager fragmentManager;
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_9, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        startAppRequests = new StartAppRequests(getContext());

        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Get FragmentManager
        fragmentManager = getFragmentManager();

        // Setup ViewPagers
        setupViewPagerOne(viewPager);
        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);


        // Add ProductsOnSale Fragment to specified FrameLayout
        Fragment allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putBoolean("on_sale", false);
        bundleInfo.putBoolean("featured", false);
        allProducts.setArguments(bundleInfo);
        fragmentManager.beginTransaction().replace(R.id.products_fragment_home9, allProducts).commit();

        if (allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            setupCategories();

        return rootView;

    }

    private void setupCategories() {
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        // Add Category Fragment to specified FrameLayout
        Bundle categoryBundle = new Bundle();
        categoryBundle.putBoolean("isHeaderVisible", false);
        categoryBundle.putBoolean("isMenuItem", false);
        Fragment categories = new Categories_1_grid();
        categories.setArguments(categoryBundle);
        fragmentManager.beginTransaction().replace(R.id.category_fragment_home9, categories).commit();

    }

    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);

        // Initialize Fragments
        Fragment productsNewest = new ProductsNewest();
        Fragment productsOnSale = new ProductsOnSale();
        Fragment productsFeatured = new ProductsFeatured();

        productsNewest.setArguments(bundle);
        productsOnSale.setArguments(bundle);
        productsFeatured.setArguments(bundle);


        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(productsNewest, getString(R.string.newest));
        viewPagerCustomAdapter.addFragment(productsOnSale, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(productsFeatured, getString(R.string.featured));


        viewPager.setOffscreenPageLimit(2);

        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerCustomAdapter);


        /*Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            //use the RTL trick here
            viewPager.setRotationY(180);
            viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }*/

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
