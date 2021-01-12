package com.vectorcoder.androidwoocommerce.activities;


import androidx.annotation.Nullable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.onesignal.OneSignal;
import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.app.MyAppPrefsManager;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.user_model.UserData;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.utils.LocaleHelper;
import com.vectorcoder.androidwoocommerce.utils.SendTokenToServer;
import com.vectorcoder.androidwoocommerce.utils.ValidateInputs;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.user_model.UserDetails;


/**
 * Login activity handles User's Email, Facebook and Google Login
 **/


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    View parentView;
    Boolean showGuest = false;
    Boolean cartNavigation = false;
    
    Toolbar toolbar;
    ActionBar actionBar;

    EditText user_email, user_password;
    TextView forgotPasswordText, signupText;
    Button loginBtn, guest_button, facebookLoginBtn, googleLoginBtn;
    
    DialogLoader dialogLoader;
    
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGoogleSignInOptions;

    private static final int RC_SIGN_IN = 100;
    
    String token;  // Device token


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("show_guest")) {
                showGuest = getIntent().getExtras().getBoolean("show_guest", false);
            }
            if (getIntent().getExtras().containsKey("cart_navigation")) {
                cartNavigation = getIntent().getExtras().getBoolean("cart_navigation", false);
            }
        }
    
        // Get Device token from pref
       
       if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
           token = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
        } else if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {
           FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Login.this,  new OnSuccessListener<InstanceIdResult>() {
               @Override
               public void onSuccess(InstanceIdResult instanceIdResult) {
                   token = instanceIdResult.getToken();
                   Log.e("newToken",token);
            
               }
           });
          
        }
       
     /*   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        token = preferences.getString(ConstantValues.FIREBASE_TOKEN,"");*/
    
    
        // Initialize Facebook SDk for Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initializing Google SDK for Google Login
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        
        setContentView(R.layout.login);
        
        // setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.login));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        
        // Binding Layout Views
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        guest_button = (Button) findViewById(R.id.guest_button);
        facebookLoginBtn = (Button) findViewById(R.id.facebookLoginBtn);
        googleLoginBtn = (Button) findViewById(R.id.googleLoginBtn);
        signupText = (TextView) findViewById(R.id.login_signupText);
        forgotPasswordText = (TextView) findViewById(R.id.forgot_password_text);
    
        parentView = signupText;
    
        if (showGuest) {
            guest_button.setVisibility(View.VISIBLE);
        } else {
            guest_button.setVisibility(View.GONE);
        }
        
        if (ConstantValues.IS_GOOGLE_LOGIN_ENABLED) {
            googleLoginBtn.setVisibility(View.VISIBLE);
        } else {
            googleLoginBtn.setVisibility(View.GONE);
        }
        
        if (ConstantValues.IS_FACEBOOK_LOGIN_ENABLED) {
            facebookLoginBtn.setVisibility(View.VISIBLE);
        } else {
            facebookLoginBtn.setVisibility(View.GONE);
        }
        

        dialogLoader = new DialogLoader(Login.this);
        
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
    
        
        user_email.setText(sharedPreferences.getString("userName", ""));


        // Register Callback for Facebook LoginManager
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // Get Access Token and proceed Facebook Registration
                String accessToken = loginResult.getAccessToken().getToken();
                processFacebookRegistration(accessToken);
                
            }
            @Override
            public void onCancel() {
                // If User Canceled
            }
            @Override
            public void onError(FacebookException e) {
                // If Login Fails
                Toast.makeText(Login.this, "FacebookException : "+e, Toast.LENGTH_LONG).show();
            }
        });
        
        
        
        // Initializing Google API Client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Login.this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
        
        
        
        // Handle on Forgot Password Click
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_input, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                final EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
    
                dialog_button.setText(getString(R.string.send));
                dialog_title.setText(getString(R.string.forgot_your_password));
                
    
                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        if (ValidateInputs.isValidEmail(dialog_input.getText().toString().trim())) {
                            // Request for Password Reset
                            processForgotPassword(dialog_input.getText().toString());
                            
                        }
                        else {
                            Snackbar.make(parentView, getString(R.string.invalid_email), Snackbar.LENGTH_LONG).show();
                        }
    
                        alertDialog.dismiss();
                    }
                });
            }
        });
    
    
    
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUp Activity
                startActivity(new Intent(Login.this, Signup.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateLogin();

                if (isValidData) {

                    // Proceed User Login
                    processLogin();
                }
            }
        });


        // Handle Facebook Login Button click
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }

                // Login and Access User Date
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email"));
            }
        });
    
        
        // Handle Google Login Button click
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (mGoogleApiClient.isConnected()){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                }

                // Get the Google SignIn Intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                
                // Start Activity with Google SignIn Intent
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    
    
        // Handle Guest Login Button click
        guest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantValues.IS_GUEST_LOGGED_IN = true;
                
                // Navigate back to MainActivity
                if (!cartNavigation)
                    startActivity(new Intent(Login.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            }
        });
    
     
    }
    
    
    
    //*********** Called if Connection fails for Google Login ********//
    
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If Connection fails for GoogleApiClient
    }
    
    
    
    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Handle Activity Result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
        
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
    }



    //*********** Get Google Account Details from GoogleSignInResult ********//

    private void handleGoogleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            // Proceed Google Registration
            processGoogleRegistration(acct);

        } else {
            // If Login Fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }



    //*********** Proceed Login with User Email and Password ********//

    private void processLogin() {

        dialogLoader.showProgressDialog();

        Call<UserData> call = APIClient.getInstance()
                .processLogin
                        (
                                "cool",
                                user_email.getText().toString().trim(),
                                user_password.getText().toString().trim()
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    
                    if ("ok".equalsIgnoreCase(response.body().getStatus())  &&  response.body().getCookie() != null) {
    
                        // Request to send user id and token to server
                        new SendTokenToServer(token,response.body().getUserDetails().getId());
                        // Get the User Details from Response
                        UserDetails userDetails = response.body().getUserDetails();
                        userDetails.setCookie(response.body().getCookie());
                        
                        
                        if (response.body().getId() != null) {
                            userDetails.setId(response.body().getId());
                        }
                        else {
                            userDetails.setId(userDetails.getId());
                        }
                        
                        
                        if (response.body().getUser_login() != null) {
                            userDetails.setUsername(response.body().getUser_login());
                        }
                        else {
                            userDetails.setUsername(userDetails.getUsername());
                        }
    
                        
                        if (userDetails.getName() != null) {
                            userDetails.setDisplay_name(userDetails.getName());
                        }
    
                        
                        ((App) getApplicationContext().getApplicationContext()).setUserDetails(userDetails);
    
    
                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getId());
                        editor.putString("userCookie", userDetails.getCookie());
                        editor.putString("userEmail", userDetails.getEmail());
                        editor.putString("userName", userDetails.getUsername());
                        editor.putString("userDisplayName", userDetails.getDisplay_name());
                        editor.putString("userPicture", "");
    
                        if (userDetails.getPicture() != null  &&  userDetails.getPicture().getData() != null)
                            if (!TextUtils.isEmpty(userDetails.getPicture().getData().getUrl()))
                                editor.putString("userPicture", userDetails.getPicture().getData().getUrl());
    
                        editor.putBoolean("isLogged_in", true);
                        editor.apply();
    
    
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_GUEST_LOGGED_IN = false;
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
    
                        // Navigate back to MainActivity
                        if (!cartNavigation) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                        
                    }
                    else if ("ok".equalsIgnoreCase(response.body().getStatus())) {
                        if (response.body().getMsg() != null)
                            Snackbar.make(parentView, response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.body().getError() != null)
                            Snackbar.make(parentView, response.body().getError(), Snackbar.LENGTH_SHORT).show();
                    }

                }
                else {
                    Converter<ResponseBody, UserData> converter = APIClient.retrofit.responseBodyConverter(UserData.class, new Annotation[0]);
                    UserData userData;
                    try {
                        userData = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        userData = new UserData();
                    }
    
                    Toast.makeText(Login.this, "Error : "+userData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Proceed Forgot Password Request ********//

    private void processForgotPassword(String email) {
    
        dialogLoader.showProgressDialog();

        Call<UserData> call = APIClient.getInstance()
                .processForgotPassword
                        (
                                "cool",
                                email
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
    
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    // Show the Response Message
                    if (response.body().getMsg() != null) {
                        Snackbar.make(parentView, response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                    }
                    else if (response.body().getError() != null) {
                        Snackbar.make(parentView, response.body().getError(), Snackbar.LENGTH_LONG).show();
                    }

                }
                else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }
    
                    Toast.makeText(Login.this, "Error : "+error.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed Facebook Registration Request ********//

    private void processFacebookRegistration(String access_token) {

        dialogLoader.showProgressDialog();
        
        Log.i("access_token", access_token);
    

        Call<UserData> call = APIClient.getInstance()
                .facebookRegistration
                        (
                                "cool",
                                access_token
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();
    
                if (response.isSuccessful()) {
    
                    if ("ok".equalsIgnoreCase(response.body().getStatus())  &&  response.body().getCookie() != null) {
    
                        // Request to send user id and token to server
                        new SendTokenToServer(token,response.body().getUserDetails().getId());
                        // Get the User Details from Response
                        UserDetails userDetails = response.body().getUserDetails();
                        userDetails.setId(response.body().getId());
                        userDetails.setCookie(response.body().getCookie());
                        userDetails.setUsername(response.body().getUser_login());
    
                        if (userDetails.getName() != null) {
                            userDetails.setDisplay_name(userDetails.getName());
                        }
    
                        ((App) getApplicationContext().getApplicationContext()).setUserDetails(userDetails);
                        
                        
                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getId());
                        editor.putString("userCookie", userDetails.getCookie());
                        editor.putString("userEmail", userDetails.getEmail());
                        editor.putString("userName", userDetails.getUsername());
                        editor.putString("userDisplayName", userDetails.getDisplay_name());
                        editor.putString("userPicture", "");
    
                        if (userDetails.getPicture() != null  &&  userDetails.getPicture().getData() != null)
                            if (!TextUtils.isEmpty(userDetails.getPicture().getData().getUrl()))
                                editor.putString("userPicture", userDetails.getPicture().getData().getUrl());
    
                        editor.putBoolean("isLogged_in", true);
                        editor.apply();
    
                        
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_GUEST_LOGGED_IN = false;
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
    
                        // Navigate back to MainActivity
                        if (!cartNavigation) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            
                    }
                    else if ("ok".equalsIgnoreCase(response.body().getStatus())) {
                        if (response.body().getMsg() != null)
                            Snackbar.make(parentView, response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.body().getError() != null)
                            Snackbar.make(parentView, response.body().getError(), Snackbar.LENGTH_SHORT).show();
                    }
        
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }
        
                    Toast.makeText(Login.this, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed Google Registration Request ********//

    private void processGoogleRegistration(GoogleSignInAccount account) {

        dialogLoader.showProgressDialog();

        String photoURL = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

        Call<UserData> call = APIClient.getInstance()
                .googleRegistration
                        (
                                "cool",
                                account.getIdToken(),
                                account.getId(),
                                account.getEmail(),
                                account.getGivenName(),
                                account.getFamilyName(),
                                account.getDisplayName(),
                                photoURL
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
    
                dialogLoader.hideProgressDialog();
    
                if (response.isSuccessful()) {
    
                    if ("ok".equalsIgnoreCase(response.body().getStatus())  &&  response.body().getCookie() != null) {
    
                        // Request to send user id and token to server
                        new SendTokenToServer(token,response.body().getUserDetails().getId());
    
                        // Get the User Details from Response
                        UserDetails userDetails = response.body().getUserDetails();
                        userDetails.setId(response.body().getId());
                        userDetails.setCookie(response.body().getCookie());
                        userDetails.setUsername(response.body().getUser_login());
    
                        if (userDetails.getName() != null) {
                            userDetails.setDisplay_name(userDetails.getName());
                        }
    
                        ((App) getApplicationContext().getApplicationContext()).setUserDetails(userDetails);
    
    
                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getId());
                        editor.putString("userCookie", userDetails.getCookie());
                        editor.putString("userEmail", userDetails.getEmail());
                        editor.putString("userName", userDetails.getUsername());
                        editor.putString("userDisplayName", userDetails.getDisplay_name());
                        editor.putString("userPicture", "");
    
                        if (userDetails.getPicture() != null  &&  userDetails.getPicture().getData() != null)
                            if (!TextUtils.isEmpty(userDetails.getPicture().getData().getUrl()))
                                editor.putString("userPicture", userDetails.getPicture().getData().getUrl());
    
                        editor.putBoolean("isLogged_in", true);
                        editor.apply();
    
    
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_GUEST_LOGGED_IN = false;
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
    
                        // Navigate back to MainActivity
                        if (!cartNavigation) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            
                    }
                    else if ("ok".equalsIgnoreCase(response.body().getStatus())) {
                        if (response.body().getMsg() != null)
                            Snackbar.make(parentView, response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.body().getError() != null)
                            Snackbar.make(parentView, response.body().getError(), Snackbar.LENGTH_SHORT).show();
                    }
        
                } else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }
        
                    Toast.makeText(Login.this, "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Validate Login Form Inputs ********//

    private boolean validateLogin() {
        if (!ValidateInputs.isValidName(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_first_name));
            return false;
        }
        else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        }
        else {
            return true;
        }
    }
    
    
    
    //*********** Set the Base Context for the ContextWrapper ********//
    
    @Override
    protected void attachBaseContext(Context newBase) {
    
        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";
    
        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }
    
    
    
    //*********** Called when the Activity has detected the User pressed the Back key ********//
    
    @Override
    public void onBackPressed() {
    
        // Navigate back to MainActivity
        if (!cartNavigation)
            startActivity(new Intent(Login.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            case android.R.id.home:
    
                // Navigate back to MainActivity
                if (!cartNavigation)
                    startActivity(new Intent(Login.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                
                return true;
            
            default:
                return super.onOptionsItemSelected(item);
                
        }
    }
    
}

