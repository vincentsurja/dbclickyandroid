package com.vectorcoder.androidwoocommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.vectorcoder.androidwoocommerce.R;

public class BannerItemFragment extends Fragment {

    ImageView imageView;

    String pathToImage;

    public BannerItemFragment(String image) {
        this.pathToImage = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_banner, container, false);

        imageView = rootView.findViewById(R.id.imageView);

        Glide.with(getActivity())
                .load(pathToImage)
                .into(imageView);

        return rootView;
    }
}
