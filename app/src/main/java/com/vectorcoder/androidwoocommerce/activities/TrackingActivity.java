package com.vectorcoder.androidwoocommerce.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;

/**
 * Created by Muhammad Nabeel on 21/02/2019.
 */
public class TrackingActivity extends AppCompatActivity {
    private boolean isRedirected;
    WebView webView;
    Toolbar toolbar;
    ActionBar actionBar;
    String URL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_dialog);
    
        Intent intent=getIntent();
        if(intent!=null) {
            URL = intent.getStringExtra(ConstantValues.TrackingURL);
        }
        
        webView = findViewById(R.id.DialogWebView);
    
        // setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.app_name));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        startWebView(webView,URL);
        
    }
    
    private void startWebView(WebView webView, String url) {
        
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;
            
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                isRedirected = true;
                return false;
            }
            
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }
            
            public void onLoadResource (WebView view, String url) {
                if (!isRedirected) {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(TrackingActivity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }
                
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    isRedirected=true;
                    
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    
                    
                    
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
            
        });
        
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
            finish();
            
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            case android.R.id.home:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    super.onBackPressed();
                    finish();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
            
        }
    }
}
