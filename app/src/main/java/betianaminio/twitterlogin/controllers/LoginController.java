package betianaminio.twitterlogin.controllers;

import android.app.Activity;
import android.content.Intent;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import betianaminio.twitterlogin.interfaces.IApplicationListener;
import betianaminio.twitterlogin.models.TwitterProvider;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public class LoginController {

    private static final String TWITTER_KEY    = "YOUR_TWITTER_API_KEY";
    private static final String TWITTER_SECRET = "YOUR_TWITTER_API_SECRET_KEY";

    private Activity mWrapperActivity;

    public LoginController(Activity activity){

        this.mWrapperActivity = activity;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this.mWrapperActivity, new Twitter(authConfig));

    }

    public void initializeLoginProvider(IApplicationListener applicationListener){

        TwitterProvider.getInstance().initialize(this.mWrapperActivity, applicationListener);

    }

    public void onActivityResult( int request_code, int result_code, Intent data){

        TwitterProvider.getInstance().onActivityResult(request_code,result_code,data);
    }
}
