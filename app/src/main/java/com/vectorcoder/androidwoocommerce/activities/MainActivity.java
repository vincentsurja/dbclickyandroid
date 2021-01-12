package com.vectorcoder.androidwoocommerce.activities;


import android.annotation.SuppressLint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.LayerDrawable;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawableWrapper;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.fragments.CurrencyFrag;
import com.vectorcoder.androidwoocommerce.fragments.Download;
import com.vectorcoder.androidwoocommerce.fragments.ExtraSettings;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_1;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_10;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_2;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_3;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_4;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_5;
import com.vectorcoder.androidwoocommerce.fragments.Categories_1;
import com.vectorcoder.androidwoocommerce.fragments.Categories_2;
import com.vectorcoder.androidwoocommerce.fragments.Categories_3;
import com.vectorcoder.androidwoocommerce.fragments.Categories_4;
import com.vectorcoder.androidwoocommerce.fragments.Categories_5;
import com.vectorcoder.androidwoocommerce.fragments.Categories_6;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_6;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_7;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_8;
import com.vectorcoder.androidwoocommerce.fragments.HomePage_9;
import com.vectorcoder.androidwoocommerce.fragments.Languages;
import com.vectorcoder.androidwoocommerce.fragments.MeFragment;
import com.vectorcoder.androidwoocommerce.fragments.News;
import com.vectorcoder.androidwoocommerce.fragments.About;
import com.vectorcoder.androidwoocommerce.fragments.My_Cart;
import com.vectorcoder.androidwoocommerce.fragments.NotificationFrag;
import com.vectorcoder.androidwoocommerce.fragments.Points_Fragment;
import com.vectorcoder.androidwoocommerce.fragments.Products;
import com.vectorcoder.androidwoocommerce.fragments.Shipping_Address;
import com.vectorcoder.androidwoocommerce.fragments.WishList;
import com.vectorcoder.androidwoocommerce.fragments.My_Orders;
import com.vectorcoder.androidwoocommerce.fragments.ContactUs;
import com.vectorcoder.androidwoocommerce.fragments.Update_Account;
import com.vectorcoder.androidwoocommerce.fragments.SearchFragment;
import com.vectorcoder.androidwoocommerce.fragments.SettingsFragment;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;
import com.vectorcoder.androidwoocommerce.utils.DrawerLocker;
import com.vectorcoder.androidwoocommerce.utils.ExampleNotificationOpenedHandler;
import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.utils.LocaleHelper;
import com.vectorcoder.androidwoocommerce.utils.NotificationScheduler;
import com.vectorcoder.androidwoocommerce.app.MyAppPrefsManager;
import com.vectorcoder.androidwoocommerce.receivers.AlarmReceiver;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.NotificationBadger;
import com.vectorcoder.androidwoocommerce.adapters.DrawerExpandableListAdapter;
import com.vectorcoder.androidwoocommerce.models.drawer_model.Drawer_Items;
import com.vectorcoder.androidwoocommerce.models.device_model.AppSettingsDetails;


/**
 * MainActivity of the App
 **/

public class MainActivity extends AppCompatActivity implements DrawerLocker {

    Toolbar toolbar;
    ActionBar actionBar;

    ImageView drawer_header;
    DrawerLayout drawerLayout;
    NavigationView navigationDrawer;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    SharedPreferences sharedPreferences;
    MyAppPrefsManager myAppPrefsManager;

    ExpandableListView main_drawer_list;
    DrawerExpandableListAdapter drawerExpandableAdapter;

    boolean doublePressedBackToExit = false;

    public static String mSelectedItem;
    private static final String SELECTED_ITEM_ID = "selected";
    public ActionBarDrawerToggle actionBarDrawerToggle;

    List<Drawer_Items> listDataHeader = new ArrayList<>();
    Map<Drawer_Items, List<Drawer_Items>> listDataChild = new HashMap<>();

    AppSettingsDetails appSettings;

    String product_ID;


    public Fragment defaultHomeFragment;
    public Fragment defaultCategoryFragment;
    public Fragment currentFragment;

    public HomePage_1 homePage_1;
    public HomePage_2 homePage_2;
    public HomePage_3 homePage_3;
    public HomePage_4 homePage_4;
    public HomePage_5 homePage_5;
    public HomePage_6 homePage_6;
    public HomePage_7 homePage_7;
    public HomePage_8 homePage_8;
    public HomePage_9 homePage_9;
    public HomePage_10 homePage_10;
    public Categories_1 categories_1;
    public Categories_2 categories_2;
    public Categories_3 categories_3;
    public Categories_4 categories_4;
    public Categories_5 categories_5;
    public Categories_6 categories_6;
    public Products newest;
    public Products superDeals;
    public Products featured;
    public NotificationFrag pushNotifications;
    public Points_Fragment rewardPoints;
    public Update_Account myAccount;
    public My_Orders myOrders;
    public WishList myFavorites;
    public News news;
    public Shipping_Address addAddress;
    public Download download;
    public About about;
    public ContactUs contactUs;
    public SettingsFragment settingsFragment;
    public MeFragment meFragment;
    public ExtraSettings extraSettings;

    //*********** Called when the Activity is becoming Visible to the User ********//

    @Override
    protected void onStart() {
        super.onStart();

        if (myAppPrefsManager.isFirstTimeLaunch()) {
            NotificationScheduler.setReminder(MainActivity.this, AlarmReceiver.class);
            if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
                StartAppRequests.RegisterDeviceForFCM(MainActivity.this);
            }

        }

        myAppPrefsManager.setFirstTimeLaunch(false);
//        User_Cart_DB.initCartInstance();

    }

    //*********** Called when the Activity is first Created ********//

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSettings = ((App) getApplicationContext()).getAppSettingsDetails();
        MobileAds.initialize(this, ConstantValues.ADMOBE_ID);

        // Get MyAppPrefsManager
        myAppPrefsManager = new MyAppPrefsManager(MainActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        // Binding Layout Views
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationDrawer = (NavigationView) findViewById(R.id.main_drawer);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        floatingActionButton = findViewById(R.id.settingsFabBtn);


        // Get ActionBar and Set the Title and HomeButton of Toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(ConstantValues.APP_HEADER);

        // Setup Expandable DrawerList
        setupExpandableDrawerList();

        // Setup Expandable Drawer Header
        setupExpandableDrawerHeader();
        // Initialize ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Hide OptionsMenu
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Recreate the OptionsMenu
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // Handle ToolbarNavigationClickListener with OnBackStackChangedListener
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                // Check BackStackEntryCount of FragmentManager
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                    // Set new ToolbarNavigationClickListener
                    actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close the Drawer if Opened
                            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                                drawerLayout.closeDrawers();
                            } else {
                                // Pop previous Fragment from BackStack
                                getSupportFragmentManager().popBackStack();
                            }
                        }
                    });
                    actionBar.setHomeButtonEnabled(true);
                    actionBar.setDisplayHomeAsUpEnabled(true);

                } else {
                    // Set DrawerToggle Indicator and default ToolbarNavigationClickListener
                    actionBar.setTitle(getResources().getString(R.string.app_name));
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                    if (ConstantValues.NAVIGATION_STYLE.equals("side"))
                        setDrawerEnabled(true);
                    else
                        enableBottomNavigation(true);
                }
            }
        });

        setupBottomNavigation();

        // Select SELECTED_ITEM from SavedInstanceState
        mSelectedItem = savedInstanceState == null ? ConstantValues.DEFAULT_HOME_STYLE : savedInstanceState.getString(SELECTED_ITEM_ID);
        setupDefaultHomePage(mSelectedItem);

        if (ConstantValues.IS_CLIENT_ACTIVE) {
            floatingActionButton.setVisibility(View.GONE);
        }

/*
        if (appSettings != null) {

            // Select SELECTED_ITEM from SavedInstanceState
            mSelectedItem = savedInstanceState == null ? getString(R.string.actionHome) + " " + appSettings.getHomeStyle() : savedInstanceState.getString(SELECTED_ITEM_ID);
            // Navigate to SelectedItem
            drawerSelectedItemNavigation(mSelectedItem);

        }
*/

        // This if condition indicate if user tab on notification outside of application
        if (ExampleNotificationOpenedHandler.Notification_FLAG.equalsIgnoreCase("1")) {
            Handler handler = new Handler();  // Use this handle because the main fragment need to some delay
            // for replacing its view
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerSelectedItemNavigation(getString(R.string.push_notification));
                }
            }, 4000);

        }

        // This is local braodcast receiver if user click on in app notification.
        // there will be a trigger fire which will let it know either it should replace to notification Fragment
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter(ConstantValues.NOTIFICATION_MAIN));


    }

    //Check if it is from notification Click
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String notiCheck = intent.getStringExtra(ConstantValues.NOTIFICATION_FLAG);
                if (notiCheck != null && notiCheck.equalsIgnoreCase("1")) {
                    drawerSelectedItemNavigation(getString(R.string.push_notification));
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void setupBottomNavigation() {
        if (ConstantValues.NAVIGATION_STYLE.equals("bottom")) {
            setDrawerEnabled(false);
        } else {
            enableBottomNavigation(false);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem1) {
                switch (menuItem1.getItemId()) {
                    case R.id.navigation_home:
                        if (currentFragment == defaultCategoryFragment)
                            return false;
                        MainActivity.this.drawerSelectedItemNavigation(ConstantValues.DEFAULT_HOME_STYLE);
                        actionBar.setTitle(MainActivity.this.getResources().getString(R.string.app_name));
                        return true;
                    case R.id.navigation_category:
                        MainActivity.this.drawerSelectedItemNavigation(ConstantValues.DEFAULT_CATEGORY_STYLE);
                        actionBar.setTitle(MainActivity.this.getResources().getString(R.string.categories));
                        return true;
                    case R.id.navigation_wishlit:
                        if (currentFragment == myFavorites)
                            return false;
                        if (ConstantValues.IS_USER_LOGGED_IN) {
                            MainActivity.this.drawerSelectedItemNavigation(MainActivity.this.getString(R.string.actionFavourites));
                            return true;
                        }
                        MainActivity.this.startActivity(new Intent(MainActivity.this, Login.class));
                        return false;
                    case R.id.navigation_news:
                        if (currentFragment == news)
                            return false;
                        MainActivity.this.drawerSelectedItemNavigation(MainActivity.this.getString(R.string.actionNews));
                        return true;
                    case R.id.navigation_me:
                        if (currentFragment == meFragment)
                            return false;
                        drawerSelectedItemNavigation(getString(R.string.actionMe));
                        MainActivity.this.drawerSelectedItemNavigation(MainActivity.this.getString(R.string.app_name));
                        return true;

                }
                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraSettings = new ExtraSettings();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_fragment, extraSettings)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


    //*********** Setup Header of Navigation Drawer ********//

    public void setupExpandableDrawerHeader() {

        // Binding Layout Views of DrawerHeader
        drawer_header = (ImageView) findViewById(R.id.drawer_header);
        CircularImageView drawer_profile_image = (CircularImageView) findViewById(R.id.drawer_profile_image);
        TextView drawer_profile_name = (TextView) findViewById(R.id.drawer_profile_name);
        TextView drawer_profile_email = (TextView) findViewById(R.id.drawer_profile_email);
        TextView drawer_profile_welcome = (TextView) findViewById(R.id.drawer_profile_welcome);

        // Check if the User is Authenticated
        if (ConstantValues.IS_USER_LOGGED_IN) {
            // Check User's Info from SharedPreferences
            if (!TextUtils.isEmpty(sharedPreferences.getString("userEmail", ""))) {

                // Set User's Name, Email and Photo
                drawer_profile_email.setText(sharedPreferences.getString("userEmail", ""));

                if (!TextUtils.isEmpty(sharedPreferences.getString("userDisplayName", ""))) {
                    drawer_profile_name.setText(sharedPreferences.getString("userDisplayName", ""));
                } else {
                    drawer_profile_name.setText(sharedPreferences.getString("userName", ""));
                }

                if (!TextUtils.isEmpty(sharedPreferences.getString("userPicture", "")))
                    Glide.with(this)
                            .load(sharedPreferences.getString("userPicture", "")).asBitmap()
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(drawer_profile_image);

            } else {
                // Set Default Name, Email and Photo
                drawer_profile_image.setImageResource(R.drawable.profile);
                drawer_profile_name.setText(getString(R.string.login_or_signup));
                drawer_profile_email.setText(getString(R.string.login_or_create_account));
            }

        } else {
            // Set Default Name, Email and Photo
            drawer_profile_welcome.setVisibility(View.GONE);
            drawer_profile_image.setImageResource(R.drawable.profile);
            drawer_profile_name.setText(getString(R.string.login_or_signup));
            drawer_profile_email.setText(getString(R.string.login_or_create_account));
        }


        // Handle DrawerHeader Click Listener
        drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {

                    // Navigate to Update_Account Fragment
/*
                    Fragment fragment = new SettingsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
*/

                    drawerSelectedItemNavigation(getString(R.string.actionAccount));
                    // Close NavigationDrawer
                    drawerLayout.closeDrawers();

                } else {
                    // Navigate to Login Activity
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });
    }

    //*********** Setup Expandable List of Navigation Drawer ********//

    public void setupExpandableDrawerList() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();


        if (appSettings != null) {

            
        /*    listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionShop)));
    
            if ("1".equalsIgnoreCase(appSettings.getOne_signal_notification()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_action_notifications,getString(R.string.push_notification)));
            if ("1".equalsIgnoreCase(appSettings.getEditProfilePage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
            if ("1".equalsIgnoreCase(appSettings.getMyOrdersPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_order, getString(R.string.actionOrders)));
            if ("1".equalsIgnoreCase(appSettings.getWp_point_reward()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_action_medal, getString(R.string.actionRewards)));
            if ("1".equalsIgnoreCase(appSettings.getWishListPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_favorite, getString(R.string.actionFavourites)));
           
            if ("1".equalsIgnoreCase(appSettings.getIntroPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_intro, getString(R.string.actionIntro)));
            if ("1".equalsIgnoreCase(appSettings.getBill_ship_info()))
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    listDataHeader.add(new Drawer_Items(R.drawable.map_marker, getString(R.string.address_info)));
                }
            if ("1".equalsIgnoreCase(appSettings.getNewsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_newspaper, getString(R.string.actionNews)));
            if ("1".equalsIgnoreCase(appSettings.getContactUsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
            if ("1".equalsIgnoreCase(appSettings.getAboutUsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_info, getString(R.string.actionAbout)));
            if ("1".equalsIgnoreCase(appSettings.getShareApp()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionShareApp)));
            if ("1".equalsIgnoreCase(appSettings.getRateApp()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionRateApp)));
            if ("1".equalsIgnoreCase(appSettings.getDownloads()))
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    listDataHeader.add(new Drawer_Items(R.drawable.download, getString(R.string.download)));
                }
            if ("1".equalsIgnoreCase(appSettings.getSettingPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_settings, getString(R.string.actionSettings)));
            
            // Add last Header Item in Drawer Header List
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
            } else {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
            }
            
            
            if (!ConstantValues.IS_CLIENT_ACTIVE) {
                List<Drawer_Items> home_styles = new ArrayList<>();
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle6)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle7)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle8)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle9)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle10)));

                List<Drawer_Items> category_styles = new ArrayList<>();
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

                List<Drawer_Items> shop_childs = new ArrayList<>();
                shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.Sale)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.Featured)));


                // Add Child to selective Headers
                listDataChild.put(listDataHeader.get(0), home_styles);
                listDataChild.put(listDataHeader.get(1), category_styles);
                listDataChild.put(listDataHeader.get(2), shop_childs);
            }*/


            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionShop)));

            if ("1".equalsIgnoreCase(appSettings.getOne_signal_notification()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_action_notifications, getString(R.string.push_notification)));
            if ("1".equalsIgnoreCase(appSettings.getEditProfilePage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
            if ("1".equalsIgnoreCase(appSettings.getMyOrdersPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_order, getString(R.string.actionOrders)));
            if ("1".equalsIgnoreCase(appSettings.getWp_point_reward()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_action_medal, getString(R.string.actionRewards)));
            if ("1".equalsIgnoreCase(appSettings.getWishListPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_favorite, getString(R.string.actionFavourites)));

            if ("1".equalsIgnoreCase(appSettings.getIntroPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_intro, getString(R.string.actionIntro)));
            if ("1".equalsIgnoreCase(appSettings.getBill_ship_info()))
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    listDataHeader.add(new Drawer_Items(R.drawable.map_marker, getString(R.string.address_info)));
                }
            if ("1".equalsIgnoreCase(appSettings.getNewsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_newspaper, getString(R.string.actionNews)));
            if ("1".equalsIgnoreCase(appSettings.getContactUsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
            if ("1".equalsIgnoreCase(appSettings.getAboutUsPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_info, getString(R.string.actionAbout)));
            if ("1".equalsIgnoreCase(appSettings.getShareApp()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionShareApp)));
            if ("1".equalsIgnoreCase(appSettings.getRateApp()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionRateApp)));
            if ("1".equalsIgnoreCase(appSettings.getDownloads()))
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    listDataHeader.add(new Drawer_Items(R.drawable.download, getString(R.string.download)));
                }
            if ("1".equalsIgnoreCase(appSettings.getSettingPage()))
                listDataHeader.add(new Drawer_Items(R.drawable.ic_settings, getString(R.string.actionSettings)));

            // Add last Header Item in Drawer Header List
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
            } else {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
            }


            if (!ConstantValues.IS_CLIENT_ACTIVE) {
                List<Drawer_Items> home_styles = new ArrayList<>();
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle6)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle7)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle8)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle9)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle10)));

                List<Drawer_Items> category_styles = new ArrayList<>();
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

                List<Drawer_Items> shop_childs = new ArrayList<>();
                shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.Sale)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.Featured)));


                // Add Child to selective Headers
                listDataChild.put(listDataHeader.get(0), home_styles);
                listDataChild.put(listDataHeader.get(1), category_styles);
                listDataChild.put(listDataHeader.get(2), shop_childs);
            }


        } else {

            listDataHeader.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.actionHome)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.actionCategories)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_cart, getString(R.string.actionShop)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_account, getString(R.string.actionAccount)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_order, getString(R.string.actionOrders)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_favorite, getString(R.string.actionFavourites)));
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_action_medal, getString(R.string.actionRewards)));
            }
            listDataHeader.add(new Drawer_Items(R.drawable.ic_intro, getString(R.string.actionIntro)));
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.map_marker, getString(R.string.address_info)));
            }
            listDataHeader.add(new Drawer_Items(R.drawable.ic_newspaper, getString(R.string.actionNews)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_info, getString(R.string.actionAbout)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_chat_bubble, getString(R.string.actionContactUs)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_share, getString(R.string.actionShareApp)));
            listDataHeader.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.actionRateApp)));
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.download, getString(R.string.download)));
            }
            listDataHeader.add(new Drawer_Items(R.drawable.ic_settings, getString(R.string.actionSettings)));
            // Add last Header Item in Drawer Header List
            if (ConstantValues.IS_USER_LOGGED_IN) {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
            } else {
                listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
            }


            if (!ConstantValues.IS_CLIENT_ACTIVE) {
                List<Drawer_Items> home_styles = new ArrayList<>();
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle1)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle2)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle3)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle4)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle5)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle6)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle7)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle8)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle9)));
                home_styles.add(new Drawer_Items(R.drawable.ic_home, getString(R.string.homeStyle10)));

                List<Drawer_Items> category_styles = new ArrayList<>();
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle1)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle2)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle3)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle4)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle5)));
                category_styles.add(new Drawer_Items(R.drawable.ic_categories, getString(R.string.categoryStyle6)));

                List<Drawer_Items> shop_childs = new ArrayList<>();
                shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.Sale)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
                shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.Featured)));


                // Add Child to selective Headers
                listDataChild.put(listDataHeader.get(0), home_styles);
                listDataChild.put(listDataHeader.get(1), category_styles);
                listDataChild.put(listDataHeader.get(2), shop_childs);
            }


        }


        // Initialize DrawerExpandableListAdapter
        drawerExpandableAdapter = new DrawerExpandableListAdapter(this, listDataHeader, listDataChild);

        // Bind ExpandableListView and set DrawerExpandableListAdapter to the ExpandableListView
        main_drawer_list = (ExpandableListView) findViewById(R.id.main_drawer_list);
        main_drawer_list.setAdapter(drawerExpandableAdapter);

        drawerExpandableAdapter.notifyDataSetChanged();


        // Handle Group Item Click Listener
        main_drawer_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (drawerExpandableAdapter.getChildrenCount(groupPosition) < 1) {
                    // Navigate to Selected Main Item
                    if (groupPosition == 0) {
                        drawerSelectedItemNavigation(ConstantValues.DEFAULT_HOME_STYLE);
                    } else if (groupPosition == 1) {
                        drawerSelectedItemNavigation(ConstantValues.DEFAULT_CATEGORY_STYLE);
                    } else {
                        drawerSelectedItemNavigation(listDataHeader.get(groupPosition).getTitle());
                    }
                }
                return false;
            }
        });


        // Handle Child Item Click Listener
        main_drawer_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // Navigate to Selected Child Item
                drawerSelectedItemNavigation(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getTitle());
                return false;
            }
        });


        // Handle Group Expand Listener
        main_drawer_list.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });
        // Handle Group Collapse Listener
        main_drawer_list.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

    }
    
    
   /* private void defaultIconPNG(){
        navigationDrawer.setItemIconTintList(null);
        listDataHeader.add(new Drawer_Items(R.drawable.home, getString(R.string.actionHome)));
        listDataHeader.add(new Drawer_Items(R.drawable.category, getString(R.string.actionCategories)));
        listDataHeader.add(new Drawer_Items(R.drawable.shop, getString(R.string.actionShop)));
    
        if ("1".equalsIgnoreCase(appSettings.getEditProfilePage()))
            listDataHeader.add(new Drawer_Items(R.drawable.locked, getString(R.string.actionAccount)));
        if ("1".equalsIgnoreCase(appSettings.getMyOrdersPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.orders, getString(R.string.actionOrders)));
        if ("1".equalsIgnoreCase(appSettings.getWishListPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.wishlist, getString(R.string.actionFavourites)));
        if ("1".equalsIgnoreCase(appSettings.getIntroPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.intro, getString(R.string.actionIntro)));
        if ("1".equalsIgnoreCase(appSettings.getNewsPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.news, getString(R.string.actionNews)));
        if ("1".equalsIgnoreCase(appSettings.getContactUsPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.about, getString(R.string.actionContactUs)));
        if ("1".equalsIgnoreCase(appSettings.getAboutUsPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.phone, getString(R.string.actionAbout)));
        if ("1".equalsIgnoreCase(appSettings.getShareApp()))
            listDataHeader.add(new Drawer_Items(R.drawable.share, getString(R.string.actionShareApp)));
        if ("1".equalsIgnoreCase(appSettings.getRateApp()))
            listDataHeader.add(new Drawer_Items(R.drawable.rating, getString(R.string.actionRateApp)));
        if ("1".equalsIgnoreCase(appSettings.getSettingPage()))
            listDataHeader.add(new Drawer_Items(R.drawable.setting, getString(R.string.actionSettings)));
    
        // Add last Header Item in Drawer Header List
        if (ConstantValues.IS_USER_LOGGED_IN) {
            listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogout)));
        } else {
            listDataHeader.add(new Drawer_Items(R.drawable.ic_logout, getString(R.string.actionLogin)));
        }
    
    
        if (!ConstantValues.IS_CLIENT_ACTIVE) {
            List<Drawer_Items> home_styles = new ArrayList<>();
            home_styles.add(new Drawer_Items(R.drawable.home, getString(R.string.homeStyle1)));
            home_styles.add(new Drawer_Items(R.drawable.home, getString(R.string.homeStyle2)));
            home_styles.add(new Drawer_Items(R.drawable.home, getString(R.string.homeStyle3)));
            home_styles.add(new Drawer_Items(R.drawable.home, getString(R.string.homeStyle4)));
            home_styles.add(new Drawer_Items(R.drawable.home, getString(R.string.homeStyle5)));
        
            List<Drawer_Items> category_styles = new ArrayList<>();
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle1)));
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle2)));
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle3)));
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle4)));
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle5)));
            category_styles.add(new Drawer_Items(R.drawable.category, getString(R.string.categoryStyle6)));
        
            List<Drawer_Items> shop_childs = new ArrayList<>();
            shop_childs.add(new Drawer_Items(R.drawable.ic_sale, getString(R.string.Sale)));
            shop_childs.add(new Drawer_Items(R.drawable.ic_arrow_up, getString(R.string.Newest)));
            shop_childs.add(new Drawer_Items(R.drawable.ic_star_circle, getString(R.string.Featured)));
        
        
            // Add Child to selective Headers
            listDataChild.put(listDataHeader.get(0), home_styles);
            listDataChild.put(listDataHeader.get(1), category_styles);
            listDataChild.put(listDataHeader.get(2), shop_childs);
        }
    
    }*/


    //*********** Navigate to given Selected Item of NavigationDrawer ********//

    private void drawerSelectedItemNavigation(String selectedItem) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        
       /* if(product_ID!=null && !product_ID.isEmpty()){
    
            Bundle bundle = new Bundle();
            bundle.putInt("itemID", Integer.parseInt(product_ID));
            // Navigate to any selected HomePage Fragment
            fragment = new Product_Description();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
    
            drawerLayout.closeDrawers();
        }*/

        if (selectedItem.equalsIgnoreCase(getString(R.string.actionHome))) {
            mSelectedItem = selectedItem;

            // Navigate to any selected HomePage Fragment
            if (homePage_1 == null) {
                homePage_1 = new HomePage_1();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();

            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_1;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle1))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage1 Fragment
            if (homePage_1 == null) {
                homePage_1 = new HomePage_1();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_1)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_1;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle2))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage2 Fragment
            if (homePage_2 == null) {
                homePage_2 = new HomePage_2();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_2)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_2)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_2;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle3))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage3 Fragment
            if (homePage_3 == null) {
                homePage_3 = new HomePage_3();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_3)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_3)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_3;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle4))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage4 Fragment
            if (homePage_4 == null) {
                homePage_4 = new HomePage_4();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_4)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_4)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_4;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle5))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_5 == null) {
                homePage_5 = new HomePage_5();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_5;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle6))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_6 == null) {
                homePage_6 = new HomePage_6();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_6)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_6)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_6;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle7))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_7 == null) {
                homePage_7 = new HomePage_7();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_7)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_7)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_7).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_7;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle8))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_8 == null) {
                homePage_8 = new HomePage_8();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_8)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_8)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_8).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_8;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle9))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_9 == null) {
                homePage_9 = new HomePage_9();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_9).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_9;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle10))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_10 == null) {
                homePage_10 = new HomePage_10();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_10)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_10)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_10).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_10;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.app_name));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionCategories))) {
            mSelectedItem = selectedItem;

            if (categories_1 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_1 = new Categories_1();
                categories_1.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_1, getString(R.string.categoryStyle1))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(getString(R.string.actionHome)).commit();
            }
            currentFragment = categories_1;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle1))) {
            mSelectedItem = selectedItem;

            if (categories_1 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_1 = new Categories_1();
                categories_1.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_1, getString(R.string.categoryStyle1))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_1).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_1;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle2))) {
            mSelectedItem = selectedItem;

            if (categories_2 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_2 = new Categories_2();
                categories_2.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_2, getString(R.string.categoryStyle2))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_2).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_2;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle3))) {
            mSelectedItem = selectedItem;

            if (categories_3 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_3 = new Categories_3();
                categories_3.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_3, getString(R.string.categoryStyle3))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_3;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle4))) {
            mSelectedItem = selectedItem;

            if (categories_4 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_4 = new Categories_4();
                categories_4.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_4, getString(R.string.categoryStyle4))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_4).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_4;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle5))) {
            mSelectedItem = selectedItem;

            if (categories_5 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_5 = new Categories_5();
                categories_5.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_5, getString(R.string.categoryStyle5))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_5).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_5;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle6))) {
            mSelectedItem = selectedItem;

            if (categories_6 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_6 = new Categories_6();
                categories_6.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_6, getString(R.string.categoryStyle6))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_6).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_6;

            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShop))) {
            mSelectedItem = selectedItem;
            if (newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "date");
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                // Navigate to Products Fragment
                newest = new Products();
                newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();


            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = newest;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionShop));


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Sale))) {
            mSelectedItem = selectedItem;
            if (superDeals == null) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("on_sale", true);
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                superDeals = new Products();
                superDeals.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, superDeals)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(superDeals).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = superDeals;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.Sale));


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Newest))) {
            mSelectedItem = selectedItem;
            if (newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "date");
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                newest = new Products();
                newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();

            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = newest;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Featured))) {
            mSelectedItem = selectedItem;

            if (featured == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("featured", true);
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                featured = new Products();
                featured.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, featured)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(featured).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = featured;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.featured));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.push_notification))) {
            mSelectedItem = selectedItem;

            if (pushNotifications == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                pushNotifications = new NotificationFrag();
                pushNotifications.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, pushNotifications)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(pushNotifications).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }

            currentFragment = pushNotifications;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.push_notification));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionRewards))) {
            mSelectedItem = selectedItem;

            if (rewardPoints == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isMenuItem", true);

                // Navigate to Products Fragment
                rewardPoints = new Points_Fragment();
                rewardPoints.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, rewardPoints)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(rewardPoints).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = rewardPoints;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionRewards));


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAccount))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;
                if (myAccount == null) {
                    // Navigate to Update_Account Fragment
                    myAccount = new Update_Account();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, myAccount)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(myAccount).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = myAccount;
                drawerLayout.closeDrawers();
                actionBar.setTitle(getString(R.string.actionAccount));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionOrders))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;
                if (myOrders == null) {
                    // Navigate to My_Orders Fragment
                    myOrders = new My_Orders();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, myOrders)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(myOrders).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = myOrders;
                drawerLayout.closeDrawers();
                actionBar.setTitle(getString(R.string.actionOrders));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionFavourites))) {
            mSelectedItem = selectedItem;

            if (myFavorites == null) {
                // Navigate to WishList Fragment
                myFavorites = new WishList();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, myFavorites)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(myFavorites).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = myFavorites;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionFavourites));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionNews))) {
            mSelectedItem = selectedItem;
            if (news == null) {
                // Navigate to News Fragment
                news = new News();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, news)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(news).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = news;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionNews));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.address_info))) {
            mSelectedItem = selectedItem;

            if (addAddress == null) {
                Bundle bundle = new Bundle();
                bundle.putString("shipping", "1");
                // Navigate to News Fragment
                addAddress = new Shipping_Address();
                addAddress.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, addAddress)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(addAddress).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = addAddress;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.address_info));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.download))) {
            mSelectedItem = selectedItem;

            if (download == null) {
                download = new Download();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, download)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(download).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = download;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.download));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionIntro))) {
            mSelectedItem = selectedItem;

            // Navigate to IntroScreen
            startActivity(new Intent(getBaseContext(), IntroScreen.class));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAbout))) {
            mSelectedItem = selectedItem;

            if (about == null) {
                // Navigate to About Fragment
                about = new About();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, about)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(about).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = about;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionAbout));

        } else if (selectedItem.equalsIgnoreCase(

                getString(R.string.actionShareApp))) {
            mSelectedItem = selectedItem;

            // Share App with the help of static method of Utilities class
            Utilities.shareMyApp(MainActivity.this);

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(

                getString(R.string.actionRateApp))) {
            mSelectedItem = selectedItem;

            // Rate App with the help of static method of Utilities class
            Utilities.rateMyApp(MainActivity.this);

            drawerLayout.closeDrawers();

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionContactUs))) {
            mSelectedItem = selectedItem;
            if (contactUs == null) {
                // Navigate to ContactUs Fragment
                contactUs = new ContactUs();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, contactUs)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(contactUs).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = contactUs;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionContactUs));

        } else if (selectedItem.equalsIgnoreCase(

                getString(R.string.actionSettings))) {
            mSelectedItem = selectedItem;

            if (settingsFragment == null) {
                // Navigate to SettingsFragment Fragment
                settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, settingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(settingsFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = settingsFragment;
            drawerLayout.closeDrawers();
            actionBar.setTitle(getString(R.string.actionSettings));

        }else if (selectedItem.equalsIgnoreCase(getString(R.string.actionMe))) {
            mSelectedItem = selectedItem;

            // Navigate to SettingsFragment Fragment
            if (meFragment == null) {
                meFragment = new MeFragment();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, meFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(meFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = meFragment;
            drawerLayout.closeDrawers();
        }
        else if (selectedItem.equalsIgnoreCase(

                getString(R.string.actionLogin))) {
            mSelectedItem = selectedItem;

            // Navigate to Login Activity
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

        } else if (selectedItem.equalsIgnoreCase(

                getString(R.string.actionLogout))) {
            mSelectedItem = selectedItem;

            // Edit UserID in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userID", "");
            editor.putString("userCookie", "");
            editor.putString("userPicture", "");
            editor.putString("userName", "");
            editor.putString("userDisplayName", "");
            editor.apply();

            // Set UserLoggedIn in MyAppPrefsManager
            MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(this);
            myAppPrefsManager.setUserLoggedIn(false);

            // Set isLogged_in of ConstantValues
            ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();


            // Navigate to Login Activity
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }

    }

    private void setupDefaultHomePage(String defaultHome) {
        if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle1))) {
            homePage_1 = new HomePage_1();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_1, getString(R.string.homeStyle1)).commit();
            currentFragment = homePage_1;
            defaultHomeFragment = homePage_1;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle2))) {
            homePage_2 = new HomePage_2();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_2, getString(R.string.homeStyle2)).commit();
            currentFragment = homePage_2;
            defaultHomeFragment = homePage_2;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle3))) {
            homePage_3 = new HomePage_3();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_3, getString(R.string.homeStyle3)).commit();
            currentFragment = homePage_3;
            defaultHomeFragment = homePage_3;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle4))) {
            homePage_4 = new HomePage_4();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_4, getString(R.string.homeStyle4)).commit();
            currentFragment = homePage_4;
            defaultHomeFragment = homePage_4;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle5))) {
            homePage_5 = new HomePage_5();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_5, getString(R.string.homeStyle5)).commit();
            currentFragment = homePage_5;
            defaultHomeFragment = homePage_5;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle6))) {
            homePage_6 = new HomePage_6();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_6, getString(R.string.homeStyle6)).commit();
            currentFragment = homePage_6;
            defaultHomeFragment = homePage_6;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle7))) {
            homePage_7 = new HomePage_7();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_7, getString(R.string.homeStyle7)).commit();
            currentFragment = homePage_7;
            defaultHomeFragment = homePage_7;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle8))) {
            homePage_8 = new HomePage_8();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_8, getString(R.string.homeStyle8)).commit();
            currentFragment = homePage_8;
            defaultHomeFragment = homePage_8;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle9))) {
            homePage_9 = new HomePage_9();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_9, getString(R.string.homeStyle9)).commit();
            currentFragment = homePage_9;
            defaultHomeFragment = homePage_9;
        } else if (defaultHome.equalsIgnoreCase(getResources().getString(R.string.homeStyle10))) {
            homePage_10 = new HomePage_10();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_10, getString(R.string.homeStyle10)).commit();
            currentFragment = homePage_10;
            defaultHomeFragment = homePage_10;
        }

    }

    //*********** Called by the System when the Device's Configuration changes while Activity is Running ********//

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Configure ActionBarDrawerToggle with new Configuration
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    //*********** Invoked to Save the Instance's State when the Activity may be Temporarily Destroyed ********//

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the Selected NavigationDrawer Item
        outState.putString(SELECTED_ITEM_ID, mSelectedItem);
    }

    //*********** Set the Base Context for the ContextWrapper ********//

    @Override
    protected void attachBaseContext(Context newBase) {

        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode) || ConstantValues.LANGUAGE_CODE == null)
            languageCode = ConstantValues.LANGUAGE_CODE = "en";

        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //*********** Creates the Activity's OptionsMenu ********//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar_menu Menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem shareItem = menu.findItem(R.id.toolbar_ic_share);

        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        shareItem.setVisible(false);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView image = (ImageView) inflater.inflate(R.layout.layout_animated_ic_cart, null);

        Drawable itemIcon = cartItem.getIcon().getCurrent();
        image.setImageDrawable(itemIcon);

        cartItem.setActionView(image);


        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My_Cart Fragment
                Fragment fragment = new My_Cart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });


        // Tint Menu Icons with the help of static method of Utilities class
        Utilities.tintMenuIcon(MainActivity.this, searchItem, R.color.white);
        Utilities.tintMenuIcon(MainActivity.this, cartItem, R.color.white);

        return true;
    }


    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // Clear OptionsMenu if NavigationDrawer is Opened
        if (drawerLayout.isDrawerOpen(navigationDrawer)) {

            MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
            MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
            MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
            MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
            if ("1".equalsIgnoreCase(appSettings.getWpml_enabled())) {
                languageItem.setVisible(true);
            } else {
                languageItem.setVisible(false);
            }
            if ("1".equalsIgnoreCase(appSettings.getWp_multi_currency())) {
                currencyItem.setVisible(true);
            } else {
                currencyItem.setVisible(false);
            }
            searchItem.setVisible(false);
            cartItem.setVisible(false);

        } else {
            MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

            // Get No. of Cart Items with the static method of My_Cart Fragment
            int cartSize = My_Cart.getCartSize();


            // if Cart has some Items
            if (cartSize > 0) {

                // Animation for cart_menuItem
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(1);

                cartItem.getActionView().startAnimation(animation);
                cartItem.getActionView().setAnimation(null);


                LayerDrawable icon = null;
                Drawable drawable = cartItem.getIcon();

                if (drawable instanceof DrawableWrapper) {
                    drawable = ((DrawableWrapper) drawable).getWrappedDrawable();
                } else if (drawable instanceof WrappedDrawable) {
                    drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
                }


                if (drawable instanceof LayerDrawable) {
                    icon = (LayerDrawable) drawable;
                } else if (drawable instanceof DrawableWrapper) {
                    DrawableWrapper wrapper = (DrawableWrapper) drawable;
                    if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                        icon = (LayerDrawable) wrapper.getWrappedDrawable();
                    }
                }

//                icon = (LayerDrawable) drawable;


                // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
                if (icon != null)
                    NotificationBadger.setBadgeCount(this, icon, String.valueOf(cartSize));


            } else {
                // Set the Icon for Empty Cart
                cartItem.setIcon(R.drawable.ic_cart_empty);
            }

        }

        return super.onPrepareOptionsMenu(menu);
    }


    //*********** Called whenever an Item in OptionsMenu is Selected ********//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();


        switch (item.getItemId()) {

            case R.id.toolbar_ic_language:

                drawerLayout.closeDrawers();

                // Navigate to Languages Fragment
                fragment = new Languages();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;
            case R.id.toolbar_ic_currency:

                drawerLayout.closeDrawers();

                // Navigate to Languages Fragment
                fragment = new CurrencyFrag();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_search:

                SearchFragment.FLAG_SEARCHED = false;
                // Navigate to SearchFragment Fragment
                fragment = new SearchFragment();
                //fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_cart:

                // Navigate to My_Cart Fragment
                fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            default:
                break;
        }

        return true;
    }


    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {

        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();

        // Check if NavigationDrawer is Opened
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        }
        // Check if BackStack has some Fragments
        else if (fm.getBackStackEntryCount() > 0) {
            // Pop previous Fragment
            fm.popBackStack();
        }
        // Check if doubleBackToExitPressed is true
        else {
            if (currentFragment == defaultHomeFragment)
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            else
                showHomePage();
        }
    }

    public void showHomePage() {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(defaultHomeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        currentFragment = defaultHomeFragment;
        if (ConstantValues.NAVIGATION_STYLE.equals("bottom")) {
            ((MenuItem) bottomNavigationView.getMenu().getItem(0)).setChecked(true);
        }
        actionBar.setTitle(getString(R.string.app_name));
    }


    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawerLayout.setDrawerLockMode(lockMode);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }

    @SuppressLint("RestrictedApi")
    public void enableBottomNavigation(boolean isEnabled) {
        if (isEnabled) {
            if (bottomNavigationView.getVisibility() == View.GONE)
                bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            if (bottomNavigationView.getVisibility() == View.VISIBLE)
                bottomNavigationView.setVisibility(View.GONE);
        }

        if (!ConstantValues.IS_CLIENT_ACTIVE)
            floatingActionButton.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
    }

    public void toggleNavigaiton(boolean isEnabled) {
        if (ConstantValues.NAVIGATION_STYLE.equals("side")) {
            setDrawerEnabled(isEnabled);
        } else {
            enableBottomNavigation(isEnabled);

        }
    }
}

