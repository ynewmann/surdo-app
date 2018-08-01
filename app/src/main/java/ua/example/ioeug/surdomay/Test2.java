package ua.example.ioeug.surdomay;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by Asus on 03.05.2017.
 */

public class Test2 extends AppCompatActivity {

    Test2Fragment test2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        test2Fragment = (Test2Fragment) getSupportFragmentManager().findFragmentById(R.id.test2Para);
        test2Fragment.resQuizPara();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            test2Fragment.ShowGameOver();
        }
        return true;
    }
}
