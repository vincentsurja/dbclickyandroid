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


public class HomePage_10 extends Fragment {

    StartAppRequests startAppRequests;
    FragmentManager fragmentManager;
    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_10, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
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

        // Add ProductsOnSale Fragment to specified FrameLayout
        Fragment newProductsFragment = new ProductsNewest();
        newProductsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.new_products_fragment_hom10, newProductsFragment).commit();


        // Setup ViewPagers
        setupViewPagerOne(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            continueSetup();

        return rootView;

    }

    private void continueSetup() {
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", true);
        bundle.putBoolean("isMenuItem", false);

        // Add ProductsOnSale Fragment to specified FrameLayout
        Fragment categories = new Categories_2_horizontal();
        categories.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.category_fragment_hom10, categories).commit();

        setupBannerSlider();
    }

    //*********** Setup the BannerSlider with the given List of BannerImages ********//
    private void setupBannerSlider() {
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
            getFragmentManager().beginTransaction().replace(R.id.bannerFrameHome10, bannerStyle).commit();
    }

    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {


        // Add OnSaleFragment Fragment to specified FrameLayout
        Fragment onSaleFragment = new All_Products();
        Bundle onSaleBundle = new Bundle();
        onSaleBundle.putBoolean("on_sale", true);
        onSaleBundle.putBoolean("featured", false);
        onSaleBundle.putBoolean("is_bottombar_dissabled", true);
        onSaleFragment.setArguments(onSaleBundle);


        // Add Fratured Fragment to specified FrameLayout
        Fragment featuredFragment = new All_Products();
        Bundle featuredBundle = new Bundle();
        featuredBundle.putBoolean("on_sale", false);
        featuredBundle.putBoolean("featured", true);
        featuredBundle.putBoolean("is_bottombar_dissabled", true);
        featuredFragment.setArguments(featuredBundle);


        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(onSaleFragment, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(featuredFragment, getString(R.string.featured));

        viewPager.setOffscreenPageLimit(1);

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
