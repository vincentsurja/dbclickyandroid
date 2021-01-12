package com.vectorcoder.androidwoocommerce.models.device_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AppSettingsDetails {
    
    @SerializedName("status")
    @Expose
    private String status;
 /*   @SerializedName("setting_id")
    @Expose
    private String settingId;*/
    @SerializedName("facebook_app_id")
    @Expose
    private String facebookAppId;
    @SerializedName("facebook_secret_id")
    @Expose
    private String facebookSecretId;
    @SerializedName("facebook_login")
    @Expose
    private String facebookLogin;
    @SerializedName("google_login")
    @Expose
    private String googleLogin;
    @SerializedName("contact_us_email")
    @Expose
    private String contactUsEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
   /* @SerializedName("os_android")
    @Expose
    private String osAndroid;
    @SerializedName("fcm_ios")
    @Expose
    private String fcmIos;
    @SerializedName("fcm_desktop")
    @Expose
    private String fcmDesktop;
    @SerializedName("app_logo")
    @Expose
    private String appLogo;
    @SerializedName("os_android_sender_id")
    @Expose
    private String osAndroidSenderId;
    @SerializedName("fcm_ios_sender_id")
    @Expose
    private String fcmIosSenderId;*/
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("current_currency_name")
    @Expose
    private String currencyName;
    @SerializedName("new_product_duration")
    @Expose
    private String newProductDuration;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_text")
    @Expose
    private String notificationText;
   /* @SerializedName("lazzy_loading_effect")
    @Expose
    private String lazzyLoadingEffect;
    @SerializedName("footer_button")
    @Expose
    private String footerButton;*/
    @SerializedName("cart_button")
    @Expose
    private String cartButton;
   /* @SerializedName("featured_category")
    @Expose
    private String featuredCategory;*/
    @SerializedName("notification_duration")
    @Expose
    private String notificationDuration;
    @SerializedName("home_style")
    @Expose
    private String homeStyle;
    @SerializedName("banner_style")
    @Expose
    private String bannerStyle;
    @SerializedName("card_style")
    @Expose
    private String cardStyle;

    @SerializedName("wish_list_page")
    @Expose
    private String wishListPage;
    @SerializedName("edit_profile_page")
    @Expose
    private String editProfilePage;
    
    @SerializedName("one_signal_notification")
    @Expose
    private String one_signal_notification;

  /*  @SerializedName("shipping_address_page")
    @Expose
    private String shippingAddressPage;*/
    @SerializedName("my_orders_page")
    @Expose
    private String myOrdersPage;
    @SerializedName("contact_us_page")
    @Expose
    private String contactUsPage;
    @SerializedName("about_us_page")
    @Expose
    private String aboutUsPage;
    @SerializedName("news_page")
    @Expose
    private String newsPage;
    @SerializedName("intro_page")
    @Expose
    private String introPage;
    @SerializedName("setting_page")
    @Expose
    private String settingPage;
    @SerializedName("share_app")
    @Expose
    private String shareApp;
    @SerializedName("rate_app")
    @Expose
    private String rateApp;
    @SerializedName("site_url")
    @Expose
    private String siteUrl;
    @SerializedName("admob")
    @Expose
    private String admob;
    @SerializedName("admob_id")
    @Expose
    private String admobId;
    @SerializedName("ad_unit_id_banner")
    @Expose
    private String adUnitIdBanner;
    @SerializedName("ad_unit_id_interstitial")
    @Expose
    private String adUnitIdInterstitial;
    @SerializedName("category_style")
    @Expose
    private String categoryStyle;
   /* @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("google_analytic_id")
    @Expose
    private String googleAnalyticId;
    @SerializedName("themes")
    @Expose
    private String themes;*/
    /*@SerializedName("ios_admob_id")
    @Expose
    private String iosAdmobId;
    @SerializedName("ios_ad_unit_id_banner")
    @Expose
    private String iosAdUnitIdBanner;
    @SerializedName("ios_ad_unit_id_interstitial")
    @Expose
    private String iosAdUnitIdInterstitial;
    @SerializedName("ios_admob")
    @Expose
    private String iosAdmob;*/
    @SerializedName("about_page")
    @Expose
    private String aboutPage;
    @SerializedName("refund_page")
    @Expose
    private String refundPage;
    @SerializedName("privacy_page")
    @Expose
    private String privacyPage;
    @SerializedName("terms_page")
    @Expose
    private String termsPage;

    @SerializedName("about_page_id")
    @Expose
    private String about_page_id;
    @SerializedName("refund_page_id")
    @Expose
    private String refund_page_id;
    @SerializedName("privacy_page_id")
    @Expose
    private String privacy_page_id;
    @SerializedName("terms_page_id")
    @Expose
    private String terms_page_id;




    @SerializedName("filter_max_price")
    @Expose
    private String filterMaxPrice;
    @SerializedName("one_page_checkout")
    @Expose
    private String onePageCheckout;
    @SerializedName("checkout_process")
    @Expose
    private String guestCheckout;
    
   /* @SerializedName("sidebar_menu_icon")
    @Expose
    private String sidebar_menu_icon;*/
    
    @SerializedName("cancel_order_button")
    @Expose
    private String cancel_order_button;
    
    @SerializedName("cancel_order_hours")
    @Expose
    private String cancel_order_hours;
    
    @SerializedName("bill_ship_info")
    @Expose
    private String bill_ship_info;
    
    @SerializedName("downloads")
    @Expose
    private String downloads;
    @SerializedName("mvf_enabled")
    @Expose
    private String dokan_enabled;
    
    @SerializedName("wp_point_reward")
    @Expose
    private String wp_point_reward;
    @SerializedName("wp_multi_currency")
    @Expose
    private String wp_multi_currency;
    @SerializedName("delivery_tracking")
    @Expose
    private String delivery_tracking;
    @SerializedName("wpml_enabled")
    @Expose
    private String wpml_enabled;
    @SerializedName("tracking_url")
    @Expose
    private String tracking_url;
    
    @SerializedName("geo_fencing")
    @Expose
    private String geofencing;
    /*@SerializedName("geo_fencing_data")
    @Expose
    private List<GeofencingList> geofencingList;*/
    

    public String getOne_signal_notification() {
        return one_signal_notification;
    }
    
    public void setOne_signal_notification(String one_signal_notification) {
        this.one_signal_notification = one_signal_notification;
    }

    public String getTracking_url() {
        return tracking_url;
    }
    
    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }
    public String getWpml_enabled() {
        return wpml_enabled;
    }
    
    public void setWpml_enabled(String wpml_enabled) {
        this.wpml_enabled = wpml_enabled;
    }
    
    public String getWp_point_reward() {
        return wp_point_reward;
    }
    
    public void setWp_point_reward(String wp_point_reward) {
        this.wp_point_reward = wp_point_reward;
    }
    
    public String getWp_multi_currency() {
        return wp_multi_currency;
    }
    
    public void setWp_multi_currency(String wp_multi_currency) {
        this.wp_multi_currency = wp_multi_currency;
    }
    
    public String getDelivery_tracking() {
        return delivery_tracking;
    }
    
    public void setDelivery_tracking(String delivery_tracking) {
        this.delivery_tracking = delivery_tracking;
    }
    
    public String getDokan_enabled() {
        return dokan_enabled;
    }
    
    public void setDokan_enabled(String dokan_enabled) {
        this.dokan_enabled = dokan_enabled;
    }
    
    public String getDownloads() {
        return downloads;
    }
    
    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }
    
    public String getBill_ship_info() {
        return bill_ship_info;
    }
    
    public void setBill_ship_info(String bill_ship_info) {
        this.bill_ship_info = bill_ship_info;
    }
    
    
    
    public String getCancel_order_hours() {
        return cancel_order_hours;
    }
    
    public void setCancel_order_hours(String cancel_order_hours) {
        this.cancel_order_hours = cancel_order_hours;
    }
    
    
    
    /*public String getSidebar_menu_icon() {
        return sidebar_menu_icon;
    }
    
    public void setSidebar_menu_icon(String sidebar_menu_icon) {
        this.sidebar_menu_icon = sidebar_menu_icon;
    }*/
    
    /*public String getOne_page_checkout() {
        return one_page_checkout;
    }
    
    public void setOne_page_checkout(String one_page_checkout) {
        this.one_page_checkout = one_page_checkout;
    }*/
    
    public String getCancel_order_button() {
        return cancel_order_button;
    }
    
    public void setCancel_order_button(String cancel_order_button) {
        this.cancel_order_button = cancel_order_button;
    }
    
    
    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
   /* public String getSettingId() {
        return settingId;
    }
    
    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }*/
    
    public String getFacebookAppId() {
        return facebookAppId;
    }
    
    public void setFacebookAppId(String facebookAppId) {
        this.facebookAppId = facebookAppId;
    }
    
    public String getFacebookSecretId() {
        return facebookSecretId;
    }
    
    public void setFacebookSecretId(String facebookSecretId) {
        this.facebookSecretId = facebookSecretId;
    }
    
    public String getFacebookLogin() {
        return facebookLogin;
    }
    
    public void setFacebookLogin(String facebookLogin) {
        this.facebookLogin = facebookLogin;
    }
    
    public String getGoogleLogin() {
        return googleLogin;
    }
    
    public void setGoogleLogin(String googleLogin) {
        this.googleLogin = googleLogin;
    }
    
    public String getContactUsEmail() {
        return contactUsEmail;
    }
    
    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZip() {
        return zip;
    }
    
    public void setZip(String zip) {
        this.zip = zip;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
  /*  public String getOsAndroid() {
        return osAndroid;
    }
    
    public void setOsAndroid(String osAndroid) {
        this.osAndroid = osAndroid;
    }
    
    public String getFcmIos() {
        return fcmIos;
    }
    
    public void setFcmIos(String fcmIos) {
        this.fcmIos = fcmIos;
    }
    
    public String getFcmDesktop() {
        return fcmDesktop;
    }
    
    public void setFcmDesktop(String fcmDesktop) {
        this.fcmDesktop = fcmDesktop;
    }
    
    public String getAppLogo() {
        return appLogo;
    }
    
    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }
    
    public String getOsAndroidSenderId() {
        return osAndroidSenderId;
    }
    
    public void setOsAndroidSenderId(String osAndroidSenderId) {
        this.osAndroidSenderId = osAndroidSenderId;
    }
    
    public String getFcmIosSenderId() {
        return fcmIosSenderId;
    }
    
    public void setFcmIosSenderId(String fcmIosSenderId) {
        this.fcmIosSenderId = fcmIosSenderId;
    }*/
    
    public String getAppName() {
        return appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getCurrencyName() {
        return currencyName;
    }
    
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public String getNewProductDuration() {
        return newProductDuration;
    }
    
    public void setNewProductDuration(String newProductDuration) {
        this.newProductDuration = newProductDuration;
    }
    
    public String getNotificationTitle() {
        return notificationTitle;
    }
    
    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
    
    public String getNotificationText() {
        return notificationText;
    }
    
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
    
  /*  public String getLazzyLoadingEffect() {
        return lazzyLoadingEffect;
    }
    
    public void setLazzyLoadingEffect(String lazzyLoadingEffect) {
        this.lazzyLoadingEffect = lazzyLoadingEffect;
    }
    
    public String getFooterButton() {
        return footerButton;
    }
    
    public void setFooterButton(String footerButton) {
        this.footerButton = footerButton;
    }*/
    
    public String getCartButton() {
        return cartButton;
    }
    
    public void setCartButton(String cartButton) {
        this.cartButton = cartButton;
    }
    
   /* public String getFeaturedCategory() {
        return featuredCategory;
    }
    
    public void setFeaturedCategory(String featuredCategory) {
        this.featuredCategory = featuredCategory;
    }
    */
    public String getNotificationDuration() {
        return notificationDuration;
    }
    
    public void setNotificationDuration(String notificationDuration) {
        this.notificationDuration = notificationDuration;
    }
    
    public String getHomeStyle() {
        return homeStyle;
    }
    
    public void setHomeStyle(String homeStyle) {
        this.homeStyle = homeStyle;
    }

    public String getBannerStyle() {
        return bannerStyle;
    }

    public void setBannerStyle(String bannerStyle) {
        this.bannerStyle = bannerStyle;
    }

    public String getCardStyle() {
        return cardStyle;
    }

    public void setCardStyle(String cardStyle) {
        this.cardStyle = cardStyle;
    }

    public String getWishListPage() {
        return wishListPage;
    }
    
    public void setWishListPage(String wishListPage) {
        this.wishListPage = wishListPage;
    }
    
    public String getEditProfilePage() {
        return editProfilePage;
    }
    
    public void setEditProfilePage(String editProfilePage) {
        this.editProfilePage = editProfilePage;
    }
    
   /* public String getShippingAddressPage() {
        return shippingAddressPage;
    }
    
    public void setShippingAddressPage(String shippingAddressPage) {
        this.shippingAddressPage = shippingAddressPage;
    }*/
    
    public String getMyOrdersPage() {
        return myOrdersPage;
    }
    
    public void setMyOrdersPage(String myOrdersPage) {
        this.myOrdersPage = myOrdersPage;
    }
    
    public String getContactUsPage() {
        return contactUsPage;
    }
    
    public void setContactUsPage(String contactUsPage) {
        this.contactUsPage = contactUsPage;
    }
    
    public String getAboutUsPage() {
        return aboutUsPage;
    }
    
    public void setAboutUsPage(String aboutUsPage) {
        this.aboutUsPage = aboutUsPage;
    }
    
    public String getNewsPage() {
        return newsPage;
    }
    
    public void setNewsPage(String newsPage) {
        this.newsPage = newsPage;
    }
    
    public String getIntroPage() {
        return introPage;
    }
    
    public void setIntroPage(String introPage) {
        this.introPage = introPage;
    }
    
    public String getSettingPage() {
        return settingPage;
    }
    
    public void setSettingPage(String settingPage) {
        this.settingPage = settingPage;
    }
    
    public String getShareApp() {
        return shareApp;
    }
    
    public void setShareApp(String shareApp) {
        this.shareApp = shareApp;
    }
    
    public String getRateApp() {
        return rateApp;
    }
    
    public void setRateApp(String rateApp) {
        this.rateApp = rateApp;
    }
    
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    public String getAdmob() {
        return admob;
    }
    
    public void setAdmob(String admob) {
        this.admob = admob;
    }
    
    public String getAdmobId() {
        return admobId;
    }
    
    public void setAdmobId(String admobId) {
        this.admobId = admobId;
    }
    
    public String getAdUnitIdBanner() {
        return adUnitIdBanner;
    }
    
    public void setAdUnitIdBanner(String adUnitIdBanner) {
        this.adUnitIdBanner = adUnitIdBanner;
    }
    
    public String getAdUnitIdInterstitial() {
        return adUnitIdInterstitial;
    }
    
    public void setAdUnitIdInterstitial(String adUnitIdInterstitial) {
        this.adUnitIdInterstitial = adUnitIdInterstitial;
    }
    
    public String getCategoryStyle() {
        return categoryStyle;
    }
    
    public void setCategoryStyle(String categoryStyle) {
        this.categoryStyle = categoryStyle;
    }
    
   /* public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getGoogleAnalyticId() {
        return googleAnalyticId;
    }
    
    public void setGoogleAnalyticId(String googleAnalyticId) {
        this.googleAnalyticId = googleAnalyticId;
    }
    
    public String getThemes() {
        return themes;
    }
    
    public void setThemes(String themes) {
        this.themes = themes;
    }
    */
   /* public String getIosAdmobId() {
        return iosAdmobId;
    }
    
    public void setIosAdmobId(String iosAdmobId) {
        this.iosAdmobId = iosAdmobId;
    }
    
    public String getIosAdUnitIdBanner() {
        return iosAdUnitIdBanner;
    }
    
    public void setIosAdUnitIdBanner(String iosAdUnitIdBanner) {
        this.iosAdUnitIdBanner = iosAdUnitIdBanner;
    }
    
    public String getIosAdUnitIdInterstitial() {
        return iosAdUnitIdInterstitial;
    }
    
    public void setIosAdUnitIdInterstitial(String iosAdUnitIdInterstitial) {
        this.iosAdUnitIdInterstitial = iosAdUnitIdInterstitial;
    }
    
    public String getIosAdmob() {
        return iosAdmob;
    }
    
    public void setIosAdmob(String iosAdmob) {
        this.iosAdmob = iosAdmob;
    }*/
    
    public String getAboutPage() {
        return aboutPage;
    }
    
    public void setAboutPage(String aboutPage) {
        this.aboutPage = aboutPage;
    }
    
    public String getRefundPage() {
        return refundPage;
    }
    
    public void setRefundPage(String refundPage) {
        this.refundPage = refundPage;
    }
    
    public String getPrivacyPage() {
        return privacyPage;
    }
    
    public void setPrivacyPage(String privacyPage) {
        this.privacyPage = privacyPage;
    }
    
    public String getTermsPage() {
        return termsPage;
    }
    
    public void setTermsPage(String termsPage) {
        this.termsPage = termsPage;
    }




    public String getAbout_page_id() {
        return about_page_id;
    }

    public void setAbout_page_id(String about_page_id) {
        this.about_page_id = about_page_id;
    }

    public String getRefund_page_id() {
        return refund_page_id;
    }

    public void setRefund_page_id(String refund_page_id) {
        this.refund_page_id = refund_page_id;
    }

    public String getPrivacy_page_id() {
        return privacy_page_id;
    }

    public void setPrivacy_page_id(String privacy_page_id) {
        this.privacy_page_id = privacy_page_id;
    }

    public String getTerms_page_id() {
        return terms_page_id;
    }

    public void setTerms_page_id(String terms_page_id) {
        this.terms_page_id = terms_page_id;
    }


    public String getFilterMaxPrice() {
        return filterMaxPrice;
    }
    
    public void setFilterMaxPrice(String filterMaxPrice) {
        this.filterMaxPrice = filterMaxPrice;
    }
    
    public String getOnePageCheckout() {
        return onePageCheckout;
    }
    
    public void setOnePageCheckout(String onePageCheckout) {
        this.onePageCheckout = onePageCheckout;
    }
    
    public String getGuestCheckout() {
        return guestCheckout;
    }
    
    public void setGuestCheckout(String guestCheckout) {
        this.guestCheckout = guestCheckout;
    }
  
    public String getGeofencing() {
        return geofencing;
    }
    
    public void setGeofencing(String geofencing) {
        this.geofencing = geofencing;
    }
    
  /*  public List<GeofencingList> getGeofencingList() {
        return geofencingList;
    }
    
    public void setGeofencingList(List<GeofencingList> geofencingList) {
        this.geofencingList = geofencingList;
    }
    */
}
