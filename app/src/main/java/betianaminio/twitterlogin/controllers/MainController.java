package betianaminio.twitterlogin.controllers;

import android.content.Context;

import betianaminio.twitterlogin.interfaces.IApplicationListener;
import betianaminio.twitterlogin.interfaces.IRetrieveUserData;
import betianaminio.twitterlogin.models.TwitterProvider;
import betianaminio.twitterlogin.models.UserLogged;

/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public class MainController {

    private UserLogged mUserLogged;

    public MainController(final IApplicationListener applicationListener){

        TwitterProvider.getInstance().retrieveUserData(new IRetrieveUserData() {
            @Override
            public void onRetrieveDataSuccess(UserLogged userLogged) {

                MainController.this.mUserLogged = userLogged;

                applicationListener.onTaskCompleted();
            }

            @Override
            public void onFailedToRetrieveData(String error) {

                applicationListener.onFailedToCompleteTask(error);
            }
        });

    }

    public void logOut(Context context){

        TwitterProvider.getInstance().logOut(context);
    }

    public String getUserName(){

        if ( this.mUserLogged != null )
            return this.mUserLogged.getName();

        return null;
    }

    public String getUserPhoto(){

        if ( this.mUserLogged != null)
            return this.mUserLogged.getUrlPhoto();

        return null;
    }
}
