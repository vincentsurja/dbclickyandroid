package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;
import com.vectorcoder.androidwoocommerce.fragments.Products;
import com.vectorcoder.androidwoocommerce.fragments.SubCategories_5;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;

import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapter_3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    boolean isSubCategory;
    boolean ishorizontal;

    Context context;
    List<CategoryDetails> categoriesList;


    public CategoryListAdapter_3(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory,
                                 boolean ishorizontal) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
        this.ishorizontal = ishorizontal;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        
        if(!ishorizontal) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories_3, parent, false);
            return new MyViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_category_style6, parent, false);
            return new MyViewHolder2(itemView);
        }
        
    }
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);
    
        if(!ishorizontal) {
            
            CategoryListAdapter_3.MyViewHolder holder0 = (CategoryListAdapter_3.MyViewHolder)holder;
            // Set Category Image on ImageView with Glide Library
            if (categoryDetails.getImage() != null && categoryDetails.getImage().getSrc() != null) {
                Glide.with(context)
                        .load(categoryDetails.getImage().getSrc())
                        .error(R.drawable.placeholder)
                        .into(holder0.category_image);
            } else {
                Glide.with(context)
                        .load(R.drawable.placeholder)
                        .into(holder0.category_image);
            }


            holder0.category_title.setText(categoryDetails.getName().replace("&amp;", "&"));
            holder0.category_products.setText(categoryDetails.getCount() + " " + context.getString(R.string.products));
        }
        else {
            CategoryListAdapter_3.MyViewHolder2 holder2 = (CategoryListAdapter_3.MyViewHolder2)holder;
            // Set Category Image on ImageView with Glide Library
            if (categoryDetails.getImage() != null && categoryDetails.getImage().getSrc() != null) {
                Glide.with(context)
                        .load(categoryDetails.getImage().getSrc())
                        .error(R.drawable.placeholder)
                        .into(holder2.category_image);
            } else {
                Glide.with(context)
                        .load(R.drawable.placeholder)
                        .into(holder2.category_image);
            }
    
    
            holder2.category_title.setText(categoryDetails.getName().replace("&amp;", "&"));
           
        }
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        CircularImageView category_image;
        LinearLayout category_card;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = (LinearLayout) itemView.findViewById(R.id.category_card);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
            category_products = (TextView) itemView.findViewById(R.id.category_products);
            category_image = (CircularImageView) itemView.findViewById(R.id.category_image);

            category_card.setOnClickListener(this);
        }
        // Handle Click Listener on Category item
        @Override
        public void onClick(View view) {
            // Get Category Info
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", categoriesList.get(getAdapterPosition()).getId());
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());

            Fragment fragment;
    
            if (isSubCategory  ||  categoriesList.get(getAdapterPosition()).getSub_categories() == 0) {
                // Navigate to Products Fragment
                fragment = new Products();
            } else {
                // Navigate to SubCategories Fragment
                fragment = new SubCategories_5();
            }

            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .hide(((MainActivity) context).currentFragment)
                    .add(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public class MyViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        CircularImageView category_image;
        LinearLayout category_card;
        TextView category_title;
        
        
        public MyViewHolder2(final View itemView) {
            super(itemView);
            category_card = (LinearLayout) itemView.findViewById(R.id.category_card);
            category_title = (TextView) itemView.findViewById(R.id.category_title);
           // category_products = (TextView) itemView.findViewById(R.id.category_products);
            category_image = (CircularImageView) itemView.findViewById(R.id.category_image);
            
            category_card.setOnClickListener(this);
        }
    
        @Override
        public void onClick(View v) {
            // Get Category Info
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", categoriesList.get(getAdapterPosition()).getId());
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());
    
            Fragment fragment;
    
            if (isSubCategory  ||  categoriesList.get(getAdapterPosition()).getSub_categories() == 0) {
                // Navigate to Products Fragment
                fragment = new Products();
            } else {
                // Navigate to SubCategories Fragment
                fragment = new SubCategories_5();
            }
    
            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .hide(((MainActivity) context).currentFragment)
                    .add(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}

