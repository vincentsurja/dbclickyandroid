package com.vectorcoder.androidwoocommerce.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.ViewPagerSimpleAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.banner_model.BannerDetails;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class HomePage_2 extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();

    StartAppRequests startAppRequests;

    ShimmerFrameLayout shimmerFrameLayoutBanners;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_2, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
//        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        startAppRequests = new StartAppRequests(getContext());

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        shimmerFrameLayoutBanners = rootView.findViewById(R.id.shimmerFrameBanners_home2);
        shimmerFrameLayoutBanners.setVisibility(View.GONE);

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty()) {
            new MyTask().execute();
        } else
            continueSetup();


        return rootView;

    }

    private void continueSetup() {

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        for (int i = 0; i < allCategoriesList.size(); i++) {
            if (allCategoriesList.get(i).getParent() != 0) {
                allSubCategoriesList.add(allCategoriesList.get(i));
            }
        }

        setupSlidingBanner();

        // Setup ViewPagers
        setupViewPager(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);


        // Setup CustomTabs for all the Categories
        setupCustomTabs();

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
            getFragmentManager().beginTransaction().replace(R.id.bannerFrameHome2, bannerStyle).commit();
    }


    //*********** Setup the given ViewPager ********//

    private void setupCustomTabs() {

        // Initialize New View for custom Tab
        View tabOne = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

        // Set Text of custom Tab
        TextView tabText1 = (TextView) tabOne.findViewById(R.id.myTabs_text);
        tabText1.setText(getString(R.string.all));

        // Set Icon of custom Tab
        ImageView tabIcon1 = (ImageView) tabOne.findViewById(R.id.myTabs_icon);
        tabIcon1.setImageResource(R.drawable.ic_list);

        // Add tabOne to TabLayout at index 0
        tabLayout.getTabAt(0).setCustomView(tabOne);


        for (int i = 0; i < allSubCategoriesList.size(); i++) {

            // Initialize New View for custom Tab
            View tabNew = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);

            // Set Text of custom Tab
            TextView tabText2 = (TextView) tabNew.findViewById(R.id.myTabs_text);
            tabText2.setText(allSubCategoriesList.get(i).getName().replace("&amp;", "&"));

            // Set Icon of custom Tab
            ImageView tabIcon2 = (ImageView) tabNew.findViewById(R.id.myTabs_icon);
            if (allSubCategoriesList.get(i).getImage() != null && allSubCategoriesList.get(i).getImage().getSrc() != null) {
                Glide.with(getContext()).load(allSubCategoriesList.get(i).getImage().getSrc()).into(tabIcon2);
            } else {
                Glide.with(getContext()).load(R.drawable.ic_categories).into(tabIcon2);
            }


            // Add tabTwo to TabLayout at specified index
            tabLayout.getTabAt(i + 1).setCustomView(tabNew);
        }
    }


    //*********** Setup the given ViewPager ********//

    private void setupViewPager(ViewPager viewPager) {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerSimpleAdapter viewPagerAdapter = new ViewPagerSimpleAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerAdapter.addFragment(new All_Products(), getString(R.string.all));


        for (int i = 0; i < allSubCategoriesList.size(); i++) {

            // Add CategoryID to new Bundle for Fragment arguments
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", allSubCategoriesList.get(i).getId());

            // Initialize Category_Products Fragment with specified arguments
            Fragment fragment = new All_Category_Products();
            fragment.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerAdapter.addFragment(fragment, allSubCategoriesList.get(i).getName().replace("&amp;", "&"));
        }

        // Set the number of pages that should be retained to either side of the current page
        viewPager.setOffscreenPageLimit(0);
        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerAdapter);
    }


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            shimmerFrameLayoutBanners.setVisibility(View.VISIBLE);
            shimmerFrameLayoutBanners.startShimmer();
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

            shimmerFrameLayoutBanners.stopShimmer();
            shimmerFrameLayoutBanners.setVisibility(View.GONE);

            if (result.equalsIgnoreCase("1")) {
                continueSetup();
            }

        }

    }

}
