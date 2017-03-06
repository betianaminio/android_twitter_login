package betianaminio.twitterlogin.models;

/**
 * Created by Betiana G. Miño on 06/03/2017.
 */

public class UserLogged {

    private String mName;
    private String mUrlPhoto;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getUrlPhoto() {
        return mUrlPhoto;
    }

    void setUrlPhoto(String mUrlPhoto) {
        this.mUrlPhoto = mUrlPhoto;
    }

}
