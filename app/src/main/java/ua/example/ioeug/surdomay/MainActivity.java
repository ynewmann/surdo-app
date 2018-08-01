package ua.example.ioeug.surdomay;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imBtnAlf;
    private ImageButton imBtnTest1;
    private ImageButton imBtnTest2;
    private ImageButton imBtnTrans;
    private ImageButton prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        imBtnAlf = (ImageButton) findViewById(R.id.imBtnAlf);
        imBtnAlf.setOnClickListener(this);

        imBtnTest1 = (ImageButton) findViewById(R.id.imBtnTest1);
        imBtnTest1.setOnClickListener(this);

        imBtnTest2 = (ImageButton) findViewById(R.id.imBtnTest2);
        imBtnTest2.setOnClickListener(this);

        imBtnTrans = (ImageButton) findViewById(R.id.imBtnTrans);
        imBtnTrans.setOnClickListener(this);

        prof = (ImageButton) findViewById(R.id.prof);
        prof.setOnClickListener(this);

        Test1Fragment test1 = new Test1Fragment();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBtnAlf:
                Intent intent1 = new Intent(this, Alfabet.class);
                startActivity(intent1);
                break;
            case R.id.imBtnTest1:
                Intent intent2 = new Intent(this, Test1.class);
                startActivity(intent2);
                break;
            case R.id.imBtnTest2:
                Intent intent3 = new Intent(this, Test2.class);
                startActivity(intent3);
                break;
            case R.id.imBtnTrans:
                Intent intent4 = new Intent(this, Translate.class);
                startActivity(intent4);
                break;
            case R.id.prof:
                Intent intent5 = new Intent(this, TabResult.class);
                startActivity(intent5);
                break;
        }
    }

}
