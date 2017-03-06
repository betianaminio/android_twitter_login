package betianaminio.twitterlogin.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import betianaminio.twitterlogin.R;
import betianaminio.twitterlogin.controllers.MainController;
import betianaminio.twitterlogin.interfaces.IApplicationListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private MainController mMainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textViewName = (TextView)findViewById(R.id.text_view_user_name);
        final CircleImageView imageViewPhoto = (CircleImageView)findViewById(R.id.image_view_user_photo);

        this.mMainController = new MainController(new IApplicationListener() {
            @Override
            public void onTaskCompleted() {
                textViewName.setText( MainActivity.this.mMainController.getUserName());
                Glide.with(MainActivity.this).load(MainActivity.this.mMainController.getUserPhoto()).dontAnimate().into(imageViewPhoto);

            }

            @Override
            public void onFailedToCompleteTask(String error) {
                Toast.makeText(MainActivity.this,getString(R.string.error_message) + error, Toast.LENGTH_LONG).show();
            }

        });


    }

    public void onLogOutButtonClick( View view ){

        logOut();
    }

    @Override
    public void onBackPressed(){

        logOut();

    }

    private void logOut(){

        this.mMainController.logOut(this.getApplicationContext());
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }
}
