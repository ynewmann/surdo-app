package ua.example.ioeug.surdomay;

import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.percent.PercentFrameLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Asus on 03.05.2017.
 */

public class Translate extends AppCompatActivity {

//    private EditText editTextTrans;
    private LinearLayout linearLayout;
    private View.OnClickListener editText;
    private LinearLayout.LayoutParams imViewParams;
    private HorizontalScrollView horizontalScrollView;
    private EditText editTextTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editTextTrans = (EditText) findViewById(R.id.editTextTrans);

        linearLayout = (LinearLayout)findViewById(R.id.linearLayout1);

        //layout params for every EditText
        imViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final EditText editTextTrans = (EditText)findViewById(R.id.editTextTrans);
        editTextTrans.setOnKeyListener(new View.OnKeyListener()
                                  {
                                      public boolean onKey(View v, int keyCode, KeyEvent event)
                                      {
                                          if(event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER))
                                          {
                                              // сохраняем текст, введенный до нажатия Enter в переменную
                                              String strName = editTextTrans.getText().toString().toUpperCase();
//                                              String strName = "привет".toString().toUpperCase();
//                                              editTextTrans.setText(strName);
                                              getCreateView(strName);
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );

       /* horizontalScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 horizontalScrollView.pageScroll(2);
            }
        });*/
    }

    public void getCreateView(String str){

        linearLayout.removeAllViewsInLayout();

        char[] word = str.toCharArray();
        for (int i = 0; i<word.length; i++) {
//            editTextTrans.setSelection(i);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(500, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageBitmap(getBitmapFromAssets("alfabet/" + word[i] +".png"));
            linearLayout.addView(imageView);
        }
    }


    public Bitmap getBitmapFromAssets(String fileName){
        AssetManager assetManager = getAssets();
        InputStream is = null;
        try{

            is = assetManager.open(String.valueOf(fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
