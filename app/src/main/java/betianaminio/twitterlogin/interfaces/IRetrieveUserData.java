package betianaminio.twitterlogin.interfaces;

import betianaminio.twitterlogin.models.UserLogged;

/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public interface IRetrieveUserData {

    void onRetrieveDataSuccess( UserLogged userLogged);
    void onFailedToRetrieveData( String error );
}
