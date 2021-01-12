package com.vectorcoder.androidwoocommerce.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.adapters.CategoryListAdapter_3;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;

import java.util.ArrayList;
import java.util.List;


public class Categories_new_7 extends Fragment {
    
    Boolean isHeaderVisible = false;
    
    TextView emptyText, headerText;
    RecyclerView category_recycler;
    
    CategoryListAdapter_3 categoryListAdapter;
    
    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> mainCategoriesList;
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.categories, container, false);
        
        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        }
        
        allCategoriesList = new ArrayList<>();
        
        // Get CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        
        
        // Binding Layout Views
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.categories_header);
        category_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);
        category_recycler.setBackgroundColor(getResources().getColor(R.color.white));
        NestedScrollView scroll_container = (NestedScrollView) rootView.findViewById(R.id.scroll_container);
        
        scroll_container.setNestedScrollingEnabled(false);
        category_recycler.setNestedScrollingEnabled(false);
        
        
        // Hide some of the Views
        emptyText.setVisibility(View.GONE);
        
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.categories));
        }
        
        
        mainCategoriesList= new ArrayList<>();
        
        for (int i=0;  i<allCategoriesList.size();  i++) {
            
            int count_sub_categories = 0;
            
            for (int x=0;  x<allCategoriesList.size();  x++) {
                if (allCategoriesList.get(x).getParent() == allCategoriesList.get(i).getId())
                    count_sub_categories += 1;
            }
            
            allCategoriesList.get(i).setSub_categories(count_sub_categories);
            
            if (allCategoriesList.get(i).getParent() == 0) {
                mainCategoriesList.add(allCategoriesList.get(i));
            }
        }
        
        
        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_3(getContext(), mainCategoriesList, false,true);
        
        // Set the Adapter and LayoutManager to the RecyclerView
        category_recycler.setAdapter(categoryListAdapter);
        category_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        
        categoryListAdapter.notifyDataSetChanged();
        
        
        return rootView;
    }
    
}

