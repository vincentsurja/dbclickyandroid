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
import androidx.viewpager.widget.ViewPager;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.google.android.material.tabs.TabLayout;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.ViewPagerCustomAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.banner_model.BannerDetails;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class HomePage_1 extends Fragment {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    StartAppRequests startAppRequests;
    FragmentManager fragmentManager;
    Fragment productsFragment;

    RecentlyViewed recentlyViewed;
    ProductsNewest productsNewest;
    ProductsOnSale productsOnSale;
    ProductsFeatured productsFeatured;

    boolean isActive;
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getView() != null){
            isActive = true;
            init();
        }else if(isVisibleToUser && getView() == null){
            isActive = false;
        }else{
            isActive = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (productsNewest != null && productsOnSale != null && productsFeatured != null && recentlyViewed != null) {
                productsNewest.invalidateProducts();
                productsOnSale.invalidateProducts();
                productsFeatured.invalidateProducts();
                recentlyViewed.invalidateProducts();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_1, container, false);
        
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);
    
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        startAppRequests = new StartAppRequests(getContext());

        if(!isActive){
            init();
        }
        
        
        return rootView;

    }
    
    private void init(){
        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        // Setup ViewPagers
        setupViewPagerOne(viewPager);
    
        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);
    
        fragmentManager = getFragmentManager();

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            continueSetup();

    }

    private void continueSetup(){
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        setupSlidingBanner();

        // Add Products Fragment to specified FrameLayout
        productsFragment = new Products();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSubFragment", true);
        productsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.products_fragment_home1, productsFragment).commit();

        // Add RecentlyViewed Fragment to specified FrameLayout
        recentlyViewed = new RecentlyViewed();
        fragmentManager.beginTransaction().replace(R.id.recently_viewed_fragment_home1, recentlyViewed).commit();
    }


    private void setupSlidingBanner() {
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
            getFragmentManager().beginTransaction().replace(R.id.bannerFrameHome1, bannerStyle).commit();
    }

    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);

        // Initialize Fragments
        productsNewest = new ProductsNewest();
        productsOnSale = new ProductsOnSale();
        productsFeatured = new ProductsFeatured();
    
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
                continueSetup();
            }

        }

    }


}

