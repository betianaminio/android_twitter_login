package betianaminio.twitterlogin.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import betianaminio.twitterlogin.R;
import betianaminio.twitterlogin.interfaces.IApplicationListener;
import betianaminio.twitterlogin.interfaces.IRetrieveUserData;
import retrofit2.Call;


/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public class TwitterProvider {

    private static TwitterProvider s_instance;

    public static synchronized TwitterProvider getInstance(){

        if ( s_instance == null ){
            s_instance = new TwitterProvider();
        }

        return s_instance;
    }

    private TwitterLoginButton mLoginButton;

    private Activity mActivity;

    public void initialize(Activity activity, final IApplicationListener applicationListener){

        this.mLoginButton = (TwitterLoginButton)activity.findViewById(R.id.twitter_login_button);

        this.mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                applicationListener.onTaskCompleted();
            }

            @Override
            public void failure(TwitterException exception) {

                applicationListener.onFailedToCompleteTask(exception.getMessage());
            }
        });

    }

    public void onActivityResult( int request_code, int result_code, Intent intent){

        this.mLoginButton.onActivityResult(request_code,result_code,intent);
    }

    public void retrieveUserData(final IRetrieveUserData iRetrieveUserData){

        TwitterSession twitterSession = Twitter.getSessionManager().getActiveSession();

        Call<User> call = Twitter.getApiClient(twitterSession).getAccountService().verifyCredentials(true,false);

        call.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {

                UserLogged userLogged = new UserLogged();

                userLogged.setName( result.data.name);

                String photoUrlOriginalSize = result.data.profileImageUrl.replace("_normal", "");

                userLogged.setUrlPhoto( photoUrlOriginalSize );

                iRetrieveUserData.onRetrieveDataSuccess(userLogged);
            }

            @Override
            public void failure(TwitterException exception) {

                iRetrieveUserData.onFailedToRetrieveData(exception.getLocalizedMessage());

            }
        });
    }

    public void logOut( Context context){

        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {
            ClearCookies(context);
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
        }

    }

    public void ClearCookies(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}
