package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;

import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.fragments.My_Cart;
import com.vectorcoder.androidwoocommerce.fragments.Product_Description;
import com.vectorcoder.androidwoocommerce.databases.User_Favorites_DB;
import com.vectorcoder.androidwoocommerce.databases.User_Recents_DB;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductMetaData;

import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;


/**
 * ProductAdapter is the adapter class of RecyclerView holding List of Products in All_Products and other Product relevant Classes
 **/

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private Boolean isGridView;
    private Boolean isHorizontal;

    private User_Recents_DB recents_db;
    private User_Favorites_DB favorites_db;
    private List<ProductDetails> productList;
    int defaultSmLayoutId;
    int defaultLgLayoutId;

    public ProductAdapter(Context context, List<ProductDetails> productList, Boolean isHorizontal) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        recents_db = new User_Recents_DB();
        favorites_db = new User_Favorites_DB();
        setupDefaultLayoutId();
    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        // Check which Layout will be Inflated
        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(defaultSmLayoutId, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(isGridView ? defaultLgLayoutId : R.layout.layout_product_list_lg, parent, false);
        }

        // Return a new holder instance
        return new MyViewHolder(itemView);
    }


    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (position != productList.size() && productList.get(position).getStatus().equalsIgnoreCase("publish")) {

            // Get the data model based on Position
            final ProductDetails product = productList.get(position);


            // Check if the Product is already in the Cart
/*
            if (My_Cart.checkCartHasProduct(product.getId())) {
                holder.product_checked.setVisibility(View.VISIBLE);
            } else {
                holder.product_checked.setVisibility(View.GONE);
            }
*/


            // Set Product Image on ImageView with Glide Library
            Glide.with(context)
                    .load(product.getImages().get(0).getSrc())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.cover_loader.stopShimmer();
                            holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.cover_loader.stopShimmer();
                            holder.cover_loader.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.product_cover);


            holder.product_title.setText(product.getName());
            product.setImage(product.getImages().get(0).getSrc());

            // Set WebView for Product's Price
            String styleSheet = "<style> " +
                    "body{margin:0; padding:0; color:#000000; font-size:0.60em;} " +
                    "img{display:inline; height:auto; max-width:100%;}" +
                    "</style>";

            holder.product_price_webView.setVerticalScrollBarEnabled(false);
            holder.product_price_webView.setHorizontalScrollBarEnabled(false);
            holder.product_price_webView.setBackgroundColor(Color.TRANSPARENT);
            holder.product_price_webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
            holder.product_price_webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            holder.product_price_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            holder.product_price_webView.loadDataWithBaseURL(null, styleSheet + product.getPriceHtml(), "text/html", "utf-8", null);


            String[] categoryIDs = new String[product.getCategories().size()];
            String[] categoryNames = new String[product.getCategories().size()];
            if (product.getCategories().size() > 0) {

                for (int i = 0; i < product.getCategories().size(); i++) {
                    categoryIDs[i] = String.valueOf(product.getCategories().get(i).getId());
                    categoryNames[i] = product.getCategories().get(i).getName();
                }

                product.setCategoryIDs(TextUtils.join(",", categoryIDs));
                product.setCategoryNames(TextUtils.join(",", categoryNames));
            } else {
                product.setCategoryIDs("");
                product.setCategoryNames("");
            }


            if (Utilities.checkNewProduct(product.getDateCreated())) {
                holder.product_tag_new.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_new.setVisibility(View.GONE);
            }


            if (product.isOnSale()) {
                holder.product_tag_sale.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_sale.setVisibility(View.GONE);
            }


            if (product.isFeatured()) {
                holder.product_tag_featured.setVisibility(View.VISIBLE);
            } else {
                holder.product_tag_featured.setVisibility(View.GONE);
            }


            holder.product_like_btn.setOnCheckedChangeListener(null);

            if (favorites_db.getUserFavorites().contains(product.getId())) {
                product.setIsLiked("1");
                holder.product_like_btn.setChecked(true);
            } else {
                product.setIsLiked("0");
                holder.product_like_btn.setChecked(false);
            }

            // Handle the Click event of product_like_btn ToggleButton
            holder.product_like_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.product_like_btn.isChecked()) {
                        product.setIsLiked("1");
                        holder.product_like_btn.setChecked(true);

                        // Add the Product to User's Favorites
                        if (!favorites_db.getUserFavorites().contains(product.getId())) {
                            favorites_db.insertFavoriteItem(product.getId());
                        }
                    } else {
                        product.setIsLiked("0");
                        holder.product_like_btn.setChecked(false);

                        // Remove the Product from User's Favorites
                        if (favorites_db.getUserFavorites().contains(product.getId())) {
                            favorites_db.deleteFavoriteItem(product.getId());
                        }
                    }
                }
            });


            holder.product_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoProductDetails(product);
                }
            });

            // Handle the Click event of product_thumbnail ImageView
            holder.product_cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoProductDetails(product);
                }
            });


            // Handle the Click event of product_checked ImageView
            holder.product_checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoProductDetails(product);
                }
            });


            // Check the Button's Visibility
            if (ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED) {

                holder.product_add_cart_btn.setVisibility(View.VISIBLE);
                holder.product_add_cart_btn.setOnClickListener(null);

                if (product.getType().equalsIgnoreCase("simple")) {
                    if (!product.isInStock()) {
                        holder.product_add_cart_btn.setText(context.getString(R.string.outOfStock));
                        holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
                    } else {
                        holder.product_add_cart_btn.setText(context.getString(R.string.addToCart));
                        holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_green));
                    }

                    holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (product.isInStock()) {

                                // Add Product to User's Cart
                                addProductToCart(product);

                                //holder.product_checked.setVisibility(View.VISIBLE);
                                Utilities.animateCartMenuIcon(context, (MainActivity) context);

                                Snackbar.make(view, context.getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    holder.product_card_img_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (product.isInStock()) {

                                // Add Product to User's Cart
                                addProductToCart(product);

                                //holder.product_checked.setVisibility(View.VISIBLE);
                                Utilities.animateCartMenuIcon(context, (MainActivity) context);

                                Snackbar.make(v, context.getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    holder.product_add_cart_btn.setText(context.getString(R.string.view_product));
                    holder.product_add_cart_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_green));

                    holder.product_add_cart_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoProductDetails(product);
                        }
                    });
                    holder.product_card_img_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoProductDetails(product);
                        }
                    });
                }


            } else {
                // Make the Button Invisible
                holder.product_add_cart_btn.setVisibility(View.GONE);
            }

        }

        // Get Redirect For DeepLinking

    }

    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }


    private void gotoProductDetails(ProductDetails product) {

        // Get Product Info
        Bundle itemInfo = new Bundle();
        itemInfo.putParcelable("productDetails", product);

        Fragment fragment = new Product_Description();

        fragment.setArguments(itemInfo);
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .hide(((MainActivity) context).currentFragment)
                .add(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();


        // Add the Product to User's Recently Viewed Products
        if (!recents_db.getUserRecents().contains(product.getId())) {
            recents_db.insertRecentItem(product.getId());
        }
    }


    //********** Toggles the RecyclerView LayoutManager *********//

    public void toggleLayout(Boolean isGridView) {
        this.isGridView = isGridView;
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ShimmerFrameLayout cover_loader;
        Button product_add_cart_btn;
        ImageButton product_card_img_btn;
        WebView product_price_webView;
        ToggleButton product_like_btn;
        ImageView product_cover, product_checked, product_tag_new;
        TextView product_title, product_category, product_tag_sale, product_tag_featured;


        public MyViewHolder(final View itemView) {
            super(itemView);

            cover_loader = itemView.findViewById(R.id.shimmerFrame);
            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);
            product_cover = (ImageView) itemView.findViewById(R.id.product_cover);
            product_tag_new = (ImageView) itemView.findViewById(R.id.product_item_tag_new);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_tag_sale = (TextView) itemView.findViewById(R.id.product_tag_sale);
            product_tag_featured = (TextView) itemView.findViewById(R.id.product_tag_featured);
            product_add_cart_btn = (Button) itemView.findViewById(R.id.product_card_Btn);
            product_card_img_btn = itemView.findViewById(R.id.product_card_img_btn);
            product_like_btn = (ToggleButton) itemView.findViewById(R.id.product_like_btn);
            product_price_webView = (WebView) itemView.findViewById(R.id.product_price_webView);
            product_category = (TextView) itemView.findViewById(R.id.product_category);

            product_checked.setVisibility(View.GONE);

        }

    }


    //********** Adds the Product to User's Cart *********//

    private void addProductToCart(ProductDetails product) {

        CartDetails cartDetails = new CartDetails();

        double productBasePrice = 0.0;

        if (product.getPrice() != null && !TextUtils.isEmpty(product.getPrice()))
            productBasePrice = Double.parseDouble(product.getPrice());

        List<ProductMetaData> productMetaData = new ArrayList<>();
        List<ProductAttributes> selectedAttributes = new ArrayList<>();

        // Set Product's Price and Quantity
        product.setCustomersBasketQuantity(1);
        product.setSelectedVariationID(product.getSelectedVariationID());
        product.setProductsFinalPrice(String.valueOf(productBasePrice));
        product.setTotalPrice(String.valueOf(productBasePrice));

        // Set Customer's Basket Product and selected Attributes Info
        cartDetails.setCartProduct(product);
        cartDetails.setCartProductMetaData(productMetaData);
        cartDetails.setCartProductAttributes(selectedAttributes);


        // Add the Product to User's Cart with the help of static method of My_Cart class
        My_Cart.AddCartItem(cartDetails);
        // Adding Product ID for fetching product via api
        My_Cart.AddCartItemID(cartDetails.getCartProduct().getId(), 1);

        // Recreate the OptionsMenu
        ((MainActivity) context).invalidateOptionsMenu();

    }

    private void setupDefaultLayoutId() {
        switch (ConstantValues.DEFAULT_PRODUCT_CARD_STYLE){
            case 0:
                defaultSmLayoutId = R.layout.layout_product_0_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_0_grid_lg;
                break;
            case 1:
                defaultSmLayoutId = R.layout.layout_product_1_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_1_grid_lg;
                break;
            case 2:
                defaultSmLayoutId = R.layout.layout_product_2_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_2_grid_lg;
                break;
            case 3:
                defaultSmLayoutId = R.layout.layout_product_3_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_3_grid_lg;
                break;
            case 4:
                defaultSmLayoutId = R.layout.layout_product_4_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_4_grid_lg;
                break;
            case 5:
                defaultSmLayoutId = R.layout.layout_product_5_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_5_grid_lg;
                break;
            case 6:
                defaultSmLayoutId = R.layout.layout_product_6_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_6_grid_lg;
                break;
            case 7:
                defaultSmLayoutId = R.layout.layout_product_0_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_0_grid_lg;
                break;
            case 8:
                defaultSmLayoutId = R.layout.layout_product_8_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_8_grid_lg;
                break;
            case 9:
                defaultSmLayoutId = R.layout.layout_product_9_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_9_grid_lg;
                break;
            case 10:
                defaultSmLayoutId = R.layout.layout_product_10_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_10_grid_lg;
                break;
            case 11:
                defaultSmLayoutId = R.layout.layout_product_11_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_11_grid_lg;
                break;
            case 12:
                defaultSmLayoutId = R.layout.layout_product_12_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_12_grid_lg;
                break;
            case 13:
                defaultSmLayoutId = R.layout.layout_product_13_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_13_grid_lg;
                break;
            case 14:
                defaultSmLayoutId = R.layout.layout_product_14_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_14_grid_lg;
                break;
            case 15:
                defaultSmLayoutId = R.layout.layout_product_15_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_15_grid_lg;
                break;
            case 16:
                defaultSmLayoutId = R.layout.layout_product_16_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_16_grid_lg;
                break;
            case 17:
                defaultSmLayoutId = R.layout.layout_product_17_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_17_grid_lg;
                break;
            case 18:
                defaultSmLayoutId = R.layout.layout_product_18_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_18_grid_lg;
                break;
            case 19:
                defaultSmLayoutId = R.layout.layout_product_19_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_19_grid_lg;
                break;
            case 20:
                defaultSmLayoutId = R.layout.layout_product_20_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_20_grid_lg;
                break;
            case 21:
                defaultSmLayoutId = R.layout.layout_product_21_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_21_grid_lg;
                break;
            case 22:
                defaultSmLayoutId = R.layout.layout_product_22_grid_sm;
                defaultLgLayoutId = R.layout.layout_product_22_grid_lg;
                break;
        }
    }


}

