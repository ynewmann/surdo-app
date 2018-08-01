package ua.example.ioeug.surdomay;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Asus on 03.05.2017.
 */

public class Alfabet extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();

    Button[] button = new Button[32];

    private String[] xmlList;
    LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfabet);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        Collections.addAll(arrayList, getResources().getStringArray(R.array.alfabet_big));

        xmlList = getResources().getStringArray(R.array.alfabet_big);

        list = (LinearLayout)findViewById(R.id.list);

        for (int i = 0; i<xmlList.length; i++){
            button[i] = new Button(this);
            button[i].setText(xmlList[i]);
            button[i].setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            button[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button[i].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            button[i].setOnClickListener(clickLetter);
            button[i].setId(i);
            list.addView(button[i]);
        }
    }

    View.OnClickListener clickLetter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i<32; i++){
                button[i].getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            }
            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            pager.setCurrentItem(button[v.getId()].getId());
//            button[v.getId()].getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        }
    };

}
