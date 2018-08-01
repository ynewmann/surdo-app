package ua.example.ioeug.surdomay;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by Asus on 03.05.2017.
 */

public class Test1 extends AppCompatActivity {

    Test1Fragment test1Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        test1Fragment = (Test1Fragment) getSupportFragmentManager().findFragmentById(R.id.quizFragment);
        test1Fragment.resetQuiz();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            test1Fragment.ShowGameOver();
        }
        return true;
    }
}
