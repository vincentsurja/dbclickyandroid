package com.vectorcoder.androidwoocommerce.utils;

import android.app.Activity;
import android.content.Context;
import androidx.core.content.ContextCompat;

import com.vectorcoder.androidwoocommerce.R;

import java.util.Calendar;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

/**
 * Created by Muhammad Nabeel on 30/04/2019.
 */
public class DeepLinking {
    
    static BranchUniversalObject buo;
    public static void createDeepLink(Activity activity,Context context, String id,boolean isProductDesc){
    
        buo = new BranchUniversalObject();
        LinkProperties lp = new LinkProperties()
                .setChannel("facebook")
                .setFeature("sharing")
                .setCampaign("content 123 launch")
                .setStage("new user")
                .addControlParameter("store_id",id)
                .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));
        if(isProductDesc) {
            lp.addControlParameter("product_id", id);
        }
        else {
            lp.addControlParameter("store_id", id);
        }
        ShareSheetStyle ss = new ShareSheetStyle(context, context.getString(R.string.check_this_out), context.getString(R.string.this_stuff_is_awesome))
                .setCopyUrlStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_send), context.getString(R.string.copy), context.getString(R.string.added_to_clipboard))
                .setMoreOptionStyle(ContextCompat.getDrawable(context, android.R.drawable.ic_menu_search), context.getString(R.string.show_more))
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.WHATS_APP)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                .setAsFullWidthStyle(true)
                .setSharingTitle(context.getString(R.string.share_with));
    
        buo.showShareSheet(activity, lp,  ss,  new Branch.BranchLinkShareListener() {
            @Override
            public void onShareLinkDialogLaunched() {
            }
            @Override
            public void onShareLinkDialogDismissed() {
            }
            @Override
            public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
            }
            @Override
            public void onChannelSelected(String channelName) {
            }
        });
    
    }
}
