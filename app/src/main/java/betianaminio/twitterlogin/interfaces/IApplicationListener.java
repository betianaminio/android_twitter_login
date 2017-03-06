package betianaminio.twitterlogin.interfaces;

/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public interface IApplicationListener {

    void onTaskCompleted();
    void onFailedToCompleteTask( String error );
}
