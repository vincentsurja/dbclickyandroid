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
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.banner_model.BannerDetails;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class HomePage_3 extends Fragment {

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    StartAppRequests startAppRequests;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_3, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        startAppRequests = new StartAppRequests(getContext());

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Get FragmentManager
        FragmentManager fragmentManager = getFragmentManager();

        // Add ProductsNewest Fragment to specified FrameLayout
        Fragment productsNewest = new ProductsNewest();
        productsNewest.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.newest_fragment_home3, productsNewest).commit();

        // Add ProductsOnSale Fragment to specified FrameLayout
        Fragment productsOnSale = new ProductsOnSale();
        productsOnSale.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.super_deals_fragment_home3, productsOnSale).commit();

        // Add ProductsFeatured Fragment to specified FrameLayout
        Fragment productsFeatured = new ProductsFeatured();
        productsFeatured.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.featured_fragment_home3, productsFeatured).commit();

        // Add Recently Fragment to specified FrameLayout
        Fragment recentlyViewed = new RecentlyViewed();
        recentlyViewed.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.recently_viewed_fragment_home3, recentlyViewed).commit();


        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else {
            setupBannerSlider();
        }

        return rootView;

    }
    
    
    
    //*********** Setup the BannerSlider with the given List of BannerImages ********//
    
    private void setupBannerSlider() {

        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        Fragment bannerStyle = null;

        switch (ConstantValues.DEFAULT_BANNER_STYLE) {
            case 0:
                bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                break;
            case 1:
                bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                break;
            case 2:
                bannerStyle = new BannerStyle2(bannerImages, allCategoriesList);
                break;
            case 3:
                bannerStyle = new BannerStyle3(bannerImages, allCategoriesList);
                break;
            case 4:
                bannerStyle = new BannerStyle4(bannerImages, allCategoriesList);
                break;
            case 5:
                bannerStyle = new BannerStyle5(bannerImages, allCategoriesList);
                break;
            case 6:
                bannerStyle = new BannerStyle6(bannerImages, allCategoriesList);
                break;
        }

        if (bannerStyle != null)
            getFragmentManager().beginTransaction().replace(R.id.bannerFrameHome3, bannerStyle).commit();
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
                startAppRequests.RequestBanners();
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
                setupBannerSlider();
            }
        }
    }


}
