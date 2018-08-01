package ua.example.ioeug.surdomay;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Asus on 03.05.2017.
 */

public class PageFragmentAlfabet extends Fragment {

    int pageNumber;
    String[] xmlList;

    TextView pageHeader;

    public static PageFragmentAlfabet newInstance(int page) {
        PageFragmentAlfabet fragmentAlfabet = new PageFragmentAlfabet();
        Bundle args=new Bundle();
        args.putInt("num", page);
        fragmentAlfabet.setArguments(args);
        return fragmentAlfabet;
    }

    public PageFragmentAlfabet() {
    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View result=inflater.inflate(R.layout.fragment_alfabet, container, false);
            pageHeader=(TextView)result.findViewById(R.id.textViewLetter);
            ImageView iv = (ImageView)result.findViewById(R.id.imViewLetter);
            xmlList = getResources().getStringArray(R.array.alfabet_big);

            pageHeader.setText(xmlList[pageNumber]);
            iv.setImageBitmap(getBitmapFromAssets("alfabet/"+pageHeader.getText()+".png"));


            return result;
        }

    public Bitmap getBitmapFromAssets(String fileName){
        AssetManager assetManager = getActivity().getAssets();
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
