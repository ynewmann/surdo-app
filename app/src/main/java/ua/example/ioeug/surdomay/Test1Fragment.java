package ua.example.ioeug.surdomay;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import ua.example.ioeug.surdomay.DB.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.Telephony.Mms.Part.FILENAME;
import static android.view.KeyEvent.KEYCODE_HOME;

/**
 * Created by Asus on 07.05.2017.
 */

public class Test1Fragment extends Fragment {

    final String LOG_TAG = "myLogs";

    final String FILENAMES = "fileStep";
    final String FILENAMEA = "fileAnswer";
    final String FILENAMED = "fileDiv";

    private ArrayList<String> massAlfabet = new ArrayList<>();
    private ArrayList<String> arrayAlphabet = new ArrayList<>();
    private String pravda;
    private String textBtn;
    private ArrayList<String> otveti = new ArrayList<>();
    private Animation shakeAnimation = null;

    private Drawable incorrect = null;
    private Drawable buttonshape = null;

    private TextView answerTextView;
    private Button[] button = new Button[4];
    private ImageView imViewTest1;
    final String[] text1 = new String[4];

    private TextView mStepScreen;
    private TextView mQuestScreen;
    private TextView mAnswerScreen;
    private Chronometer mTimeScreen;

    private Integer StepCount; // кол-во ходов
    private Integer answerCount; // кол-во правельныъ ответов
    private Integer questCount; // кол-во правельныъ ответов

    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Collections.addAll(arrayAlphabet, getResources().getStringArray(R.array.alfabet_big));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test1, container, false);

        imViewTest1 = (ImageView) view.findViewById(R.id.imViewTest1);

        button[0] = (Button) view.findViewById(R.id.button1);
        button[1] = (Button) view.findViewById(R.id.button2);
        button[2] = (Button) view.findViewById(R.id.button3);
        button[3] = (Button) view.findViewById(R.id.button4);

        answerTextView = (TextView) view.findViewById(R.id.answerTextView);

        Resources res = getResources();
        incorrect = res.getDrawable(R.drawable.incorrect);
        buttonshape = res.getDrawable(R.drawable.buttonshape);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        shakeAnimation.setRepeatCount(3);

        mTimeScreen = (Chronometer) view.findViewById(R.id.chronometer);
        mStepScreen = (TextView)view.findViewById(R.id.stepView);
        mAnswerScreen = (TextView)view.findViewById(R.id.answerView);
        mQuestScreen = (TextView)view.findViewById(R.id.questionNumberTextView);

        dbHelper = new DBHelper(getActivity());

        StepCount = 0;
        mStepScreen.setText ("Попыток " + StepCount.toString());

        answerCount = 0;
        mAnswerScreen.setText ("Правильно " + answerCount.toString());
        questCount = 1;
        mQuestScreen.setText ("Вопрос № " + questCount.toString());

        mTimeScreen.start();

        return view;
    }

    public void resetQuiz() {

        Random randomLetter = new Random();

        answerTextView.setText("");

        massAlfabet.clear();

        Collections.addAll(massAlfabet, getResources().getStringArray(R.array.alfabet_big));

        if (arrayAlphabet.size() == 0){

            ShowGameFinish();

            Collections.addAll(arrayAlphabet, getResources().getStringArray(R.array.alfabet_big));
            StepCount = 0;
            mStepScreen.setText ("Попыток " + StepCount.toString());

            answerCount = 0;
            mAnswerScreen.setText ("Правильно " + answerCount.toString());
            questCount = 1;
            mQuestScreen.setText ("Вопрос № " + answerCount.toString());
        }

        pravda = arrayAlphabet.get(randomLetter.nextInt(arrayAlphabet.size()));
        massAlfabet.remove(pravda);
        arrayAlphabet.remove(pravda);
        imViewTest1.setImageBitmap(getBitmapFromAssets("alfabet/" + pravda + ".png"));

        otveti.add(pravda);
        otveti.add(massAlfabet.get(randomLetter.nextInt(massAlfabet.size())));
        massAlfabet.remove(otveti.get(1));
        otveti.add(massAlfabet.get(randomLetter.nextInt(massAlfabet.size())));
        massAlfabet.remove(otveti.get(2));
        otveti.add(massAlfabet.get(randomLetter.nextInt(massAlfabet.size())));
        massAlfabet.remove(otveti.get(3));

        for (int i = 0; i < button.length; i++) {
            button[i].setText(otveti.get(randomLetter.nextInt(otveti.size())));
            otveti.remove(button[i].getText());
        }

        button[0].setEnabled(true);
        button[1].setEnabled(true);
        button[2].setEnabled(true);
        button[3].setEnabled(true);

        button[0].setBackground(buttonshape);
        button[1].setBackground(buttonshape);
        button[2].setBackground(buttonshape);
        button[3].setBackground(buttonshape);

        button[0].setOnClickListener(trueBtn);
        button[1].setOnClickListener(trueBtn);
        button[2].setOnClickListener(trueBtn);
        button[3].setOnClickListener(trueBtn);

        getCheckFuck(pravda, String.valueOf(button[0].getText()));
        getCheckFuck(pravda, String.valueOf(button[1].getText()));
        getCheckFuck(pravda, String.valueOf(button[2].getText()));
        getCheckFuck(pravda, String.valueOf(button[3].getText()));
    }

    View.OnClickListener trueBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StepCount++;
            mStepScreen.setText ("Попыток " + StepCount.toString());
            switch (v.getId()) {
                case R.id.button1:
                    text1[0] = String.valueOf(button[0].getText());
                    if (getCheckFuck(pravda, text1[0])) {
                        answerTextView.setText("Верно!");
                        answerCount++;
                        questCount++;
                        mAnswerScreen.setText ("Правильно " + answerCount.toString());
                        mQuestScreen.setText ("Вопрос № " + questCount.toString());
                        otveti.clear();
                        resetQuiz();
                    } else {
                        button[0].setBackground(incorrect);
                        button[0].startAnimation(shakeAnimation);
                        answerTextView.setText("Неверно!");
                        button[0].setEnabled(false);
                    }
                    break;
                case R.id.button2:
                    text1[1] = String.valueOf(button[1].getText());
                    if (getCheckFuck(pravda, text1[1])) {
                        answerTextView.setText("Верно!");
                        otveti.clear();
                        questCount++;
                        answerCount++;
                        mAnswerScreen.setText ("Правильно " + answerCount.toString());
                        mQuestScreen.setText ("Вопрос № " + questCount.toString());
                        otveti.clear();
                        resetQuiz();
                    } else {
                        button[1].setBackground(incorrect);
                        button[1].startAnimation(shakeAnimation);
                        answerTextView.setText("Неверно!");
                        button[1].setEnabled(false);
                    }
                    break;
                case R.id.button3:
                    text1[2] = String.valueOf(button[2].getText());
                    if (getCheckFuck(pravda, text1[2])) {
                        answerTextView.setText("Верно!");
                        answerCount++;
                        questCount++;
                        mAnswerScreen.setText ("Правильно " + answerCount.toString());
                        mQuestScreen.setText ("Вопрос № " + questCount.toString());
                        otveti.clear();
                        resetQuiz();
                    } else {
                        button[2].setBackground(incorrect);
                        button[2].startAnimation(shakeAnimation);
                        answerTextView.setText("Неверно!");
                        button[2].setEnabled(false);
                    }
                    break;
                case R.id.button4:
                    text1[3] = String.valueOf(button[3].getText());
                    if (getCheckFuck(pravda, text1[3])) {
                        answerTextView.setText("Верно!");
                        answerCount++;
                        questCount++;
                        mAnswerScreen.setText ("Правильно " + answerCount.toString());
                        mQuestScreen.setText ("Вопрос № " + questCount.toString());
                        otveti.clear();
                        resetQuiz();
                    } else {
                        button[3].setBackground(incorrect);
                        button[3].startAnimation(shakeAnimation);
                        answerTextView.setText("Неверно!");
                        button[3].setEnabled(false);
                    }
                    break;
            }
        }
    };

    public void ShowGameFinish() {

        String time = mTimeScreen.getText().toString();

        if (!(answerCount==0) && !(StepCount==0))
        writeDB();

        mTimeScreen.stop();

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
                resetQuiz();
                mTimeScreen.setBase(SystemClock.elapsedRealtime());
                mTimeScreen.start();
            }
        });

        // показываем окно
        alertbox.show();
    }

    public void ShowGameOver() {

        String time = mTimeScreen.getText().toString();

        if (!(answerCount==0) && !(StepCount==0))
        writeDB();

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

        Log.d(LOG_TAG, "--- Insert in Test1: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("time", time);
        cv.put("step", step);
        cv.put("answer", answer);
        cv.put("procent", procent);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("Test1", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        dbHelper.close();
    }

    public boolean getCheckFuck(String trueAnswer, String textButton) {

        this.pravda = trueAnswer;
        this.textBtn = textButton;
        if (trueAnswer.equals(textButton))
            return true;
        else return false;
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = getActivity().getAssets();
        InputStream is = null;
        try {

            is = assetManager.open(String.valueOf(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

}
