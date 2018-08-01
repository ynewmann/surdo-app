package ua.example.ioeug.surdomay;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.example.ioeug.surdomay.DB.DBHelper;

/**
 * Created by Asus on 13.05.2017.
 */

public class Test2Fragment extends Fragment {

    final String LOG_TAG = "myLogs";

    private TextView mStepScreen;
    private TextView mAnswerScreen;
    private Chronometer mTimeScreen;

    private Button[] btn = new Button[3];
    private ImageButton[] imBtn = new ImageButton[3];
    private TableRow[] tableRows = new TableRow[6];

    private ArrayList<String> arrayListAlfabet = new ArrayList<>();
    private ArrayList<String> varOtv = new ArrayList<>();
    private ArrayList<String> btnOtv = new ArrayList<>();
    private Animation shakeAnimation = null;

    private Drawable incorrect = null;
    private Drawable buttonshape = null;
    private Drawable select = null;
    Animation answer_otv = null;

    String str1 = null, str2 = null;

    private Integer StepCount; // кол-во ходов
    private Integer answerCount; // кол-во правельныъ ответов

    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test2, container, false);

        dbHelper = new DBHelper(getActivity());

        Resources res = getResources();
        incorrect = res.getDrawable(R.drawable.incorrect);
        buttonshape = res.getDrawable(R.drawable.buttonshape);
        select = res.getDrawable(R.drawable.select);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(2);

        answer_otv = AnimationUtils.loadAnimation(getActivity(), R.anim.answer_otv);
        answer_otv.setRepeatCount(1);

        tableRows[0] = (TableRow) view.findViewById(R.id.row1);
        tableRows[1] = (TableRow) view.findViewById(R.id.row2);
        tableRows[2] = (TableRow) view.findViewById(R.id.row3);
        tableRows[3] = (TableRow) view.findViewById(R.id.row4);
        tableRows[4] = (TableRow) view.findViewById(R.id.row5);
        tableRows[5] = (TableRow) view.findViewById(R.id.row6);

        btn[0] = (Button)view.findViewById(R.id.btn1);
        btn[1] = (Button)view.findViewById(R.id.btn2);
        btn[2] = (Button)view.findViewById(R.id.btn3);

        imBtn[0] = (ImageButton)view.findViewById(R.id.imBtn1);
        imBtn[1] = (ImageButton)view.findViewById(R.id.imBtn2);
        imBtn[2] = (ImageButton)view.findViewById(R.id.imBtn3);

        mTimeScreen = (Chronometer) view.findViewById(R.id.chronometer);
        mStepScreen = (TextView)view.findViewById(R.id.stepView);
        mAnswerScreen = (TextView)view.findViewById(R.id.answerView);

        StepCount = 0;
        mStepScreen.setText ("Попыток " + StepCount.toString());

        answerCount = 0;
        mAnswerScreen.setText ("Правильно " + answerCount.toString());

        mTimeScreen.start();

        return view;
    }

    public void resQuizPara(){

        for (int i = 0; i < 3; i++){
            btn[i].setEnabled(true);
            imBtn[i].setEnabled(true);

            btn[i].setVisibility(View.VISIBLE);
            imBtn[i].setVisibility(View.VISIBLE);

            btn[i].setBackground(buttonshape);
            imBtn[i].setBackground(buttonshape);
        }

        Random random = new Random();

        arrayListAlfabet.clear();

        Collections.addAll(arrayListAlfabet, getResources().getStringArray(R.array.alfabet_big));


//        answer = arrayListAlfabet.get(random.nextInt(arrayListAlfabet.size()));
        varOtv.clear();
        btnOtv.clear();

        varOtv.add(arrayListAlfabet.get(random.nextInt(arrayListAlfabet.size())));
        arrayListAlfabet.remove(varOtv.get(0));
        varOtv.add(arrayListAlfabet.get(random.nextInt(arrayListAlfabet.size())));
        arrayListAlfabet.remove(varOtv.get(1));
        varOtv.add(arrayListAlfabet.get(random.nextInt(arrayListAlfabet.size())));
        arrayListAlfabet.remove(varOtv.get(2));

//        Collections.shuffle(varOtv);

        imBtn[0].setImageBitmap(getBitmapFromAssets("alfabet/"+varOtv.get(0)+".png"));
        imBtn[1].setImageBitmap(getBitmapFromAssets("alfabet/"+varOtv.get(1)+".png"));
        imBtn[2].setImageBitmap(getBitmapFromAssets("alfabet/"+varOtv.get(2)+".png"));

        btnOtv.add(varOtv.get(0));
        btnOtv.add(varOtv.get(1));
        btnOtv.add(varOtv.get(2));

        Collections.shuffle(btnOtv);

        btn[0].setText(btnOtv.get(0));
        btn[1].setText(btnOtv.get(1));
        btn[2].setText(btnOtv.get(2));

        btn[0].setOnClickListener(trueBtn);
        btn[1].setOnClickListener(trueBtn);
        btn[2].setOnClickListener(trueBtn);

        imBtn[0].setOnClickListener(trueImBtn);
        imBtn[1].setOnClickListener(trueImBtn);
        imBtn[2].setOnClickListener(trueImBtn);

        shakeRow();

    }

    View.OnClickListener trueBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn1:
                    str1 = btnOtv.get(0);
                    btn[0].setBackground(select);
                    btn[1].setBackground(buttonshape);
                    btn[2].setBackground(buttonshape);
                    imBtn[0].setBackground(buttonshape);
                    imBtn[1].setBackground(buttonshape);
                    imBtn[2].setBackground(buttonshape);
                    getResult();
                    break;
                case R.id.btn2:
                    str1 = btnOtv.get(1);
                    btn[1].setBackground(select);
                    btn[0].setBackground(buttonshape);
                    btn[2].setBackground(buttonshape);
                    imBtn[0].setBackground(buttonshape);
                    imBtn[1].setBackground(buttonshape);
                    imBtn[2].setBackground(buttonshape);
                    getResult();
                    break;
                case R.id.btn3:
                    str1 = btnOtv.get(2);
                    btn[2].setBackground(select);
                    btn[1].setBackground(buttonshape);
                    btn[0].setBackground(buttonshape);
                    imBtn[0].setBackground(buttonshape);
                    imBtn[1].setBackground(buttonshape);
                    imBtn[2].setBackground(buttonshape);
                    getResult();
                    break;
            }
        }
    };



    View.OnClickListener trueImBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imBtn1:
                    str2 = varOtv.get(0);
                    imBtn[0].setBackground(select);
                    imBtn[1].setBackground(buttonshape);
                    imBtn[2].setBackground(buttonshape);
                    btn[0].setBackground(buttonshape);
                    btn[1].setBackground(buttonshape);
                    btn[2].setBackground(buttonshape);
                    getResult();
                    break;
                case R.id.imBtn2:
                    str2 = varOtv.get(1);
                    imBtn[1].setBackground(select);
                    imBtn[0].setBackground(buttonshape);
                    imBtn[2].setBackground(buttonshape);
                    btn[0].setBackground(buttonshape);
                    btn[1].setBackground(buttonshape);
                    btn[2].setBackground(buttonshape);
                    getResult();
                    break;
                case R.id.imBtn3:
                    str2 = varOtv.get(2);
                    imBtn[2].setBackground(select);
                    imBtn[1].setBackground(buttonshape);
                    imBtn[0].setBackground(buttonshape);
                    btn[0].setBackground(buttonshape);
                    btn[1].setBackground(buttonshape);
                    btn[2].setBackground(buttonshape);
                    getResult();
                    break;
            }
        }
    };

    public void getResult() {
        if (!(str2 == null) && !(str1 == null)) {
            StepCount++;
            mStepScreen.setText ("Попыток " + StepCount.toString());
            if (str1.equals(str2)) {

                btn[btnOtv.indexOf(str1)].setEnabled(false);
                imBtn[varOtv.indexOf(str2)].setEnabled(false);

                imBtn[varOtv.indexOf(str2)].setVisibility(View.INVISIBLE);
                btn[btnOtv.indexOf(str1)].setVisibility(View.INVISIBLE);

                answerCount++;
                mAnswerScreen.setText ("Правильно " + answerCount.toString());

                str1 = null;
                str2 = null;
            } else {
                imBtn[varOtv.indexOf(str2)].setBackground(incorrect);
                btn[btnOtv.indexOf(str1)].setBackground(incorrect);

                imBtn[varOtv.indexOf(str2)].startAnimation(shakeAnimation);
                btn[btnOtv.indexOf(str1)].startAnimation(shakeAnimation);
                str1 = null;
                str2 = null;
            }
        }

        if ((btn[0].isEnabled() == false) &&(btn[1].isEnabled() == false) && (btn[2].isEnabled() == false)) {
            resQuizPara();
        }
    }

    public void ShowGameOver() {

        if (!(answerCount==0) && !(StepCount==0))
            writeDB();

        String time = mTimeScreen.getText().toString();

        // Диалоговое окно
        AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());

        // Заголовок и текст
        alertbox.setTitle("Поздравляем!");
        String TextToast = "Игра закончена" +
                "\nХодов: " + StepCount.toString() +
                "\nПравильно: " + answerCount.toString() +
                "\nВремя: " + time;
        alertbox.setMessage(TextToast);

        // Добавляем кнопку
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // закрываем текущюю Activity
                getActivity().finish();
            }
        });
        // показываем окно
        alertbox.show();
    }

    public void writeDB(){

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String time = mTimeScreen.getText().toString();
        String step = String.valueOf(StepCount);
        String answer = String.valueOf(answerCount);
        String procent = String.valueOf(answerCount * 100 / StepCount)+"%";

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Log.d(LOG_TAG, "--- Insert in Test2: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("time", time);
        cv.put("step", step);
        cv.put("answer", answer);
        cv.put("procent", procent);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("Test2", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        dbHelper.close();
    }

    public void shakeRow(){
        Integer[] ints = new Integer[6];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = i;
        }
        List<Integer> lst = Arrays.asList(ints);
        Collections.shuffle(lst);
        ints = lst.toArray(ints);

        tableRows[0].removeAllViews();
        tableRows[1].removeAllViews();
        tableRows[2].removeAllViews();
        tableRows[3].removeAllViews();
        tableRows[4].removeAllViews();
        tableRows[5].removeAllViews();


        tableRows[ints[0]].addView(imBtn[0]);
        tableRows[ints[1]].addView(imBtn[1]);
        tableRows[ints[2]].addView(imBtn[2]);
        tableRows[ints[3]].addView(btn[0]);
        tableRows[ints[4]].addView(btn[1]);
        tableRows[ints[5]].addView(btn[2]);
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
