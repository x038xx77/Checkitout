package ru.rublt.checkitout;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.UidVerifier;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import ru.rublt.checkitout.Models.Check_User;

public class CheckActivity extends AppCompatActivity {


    public static final String GOOGLE_ACCOUNTT = "my_google_account";
    private EditText check_inn, check_compani, check_name, check_phone, check_otziv;

    Button check_btn1, check_btn2, check_btn3, check_btn4, check_btn5, check_talk, btnWeb;
    private DatabaseReference star_database;
    private String Star_key = "Check";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_main);
        init();

    }

    public void init() {

        check_inn = findViewById(R.id.check_inn);
        check_compani = findViewById(R.id.check_compani);
        check_name = findViewById(R.id.check_name);
        check_phone = findViewById(R.id.check_phone);
        check_otziv = findViewById(R.id.check_otziv);
        check_btn1 = findViewById(R.id.check_btn1);
        check_btn2 = findViewById(R.id.check_btn2);
        check_btn3 = findViewById(R.id.check_btn3);
        check_btn4 = findViewById(R.id.check_btn4);
        check_btn5 = findViewById(R.id.check_btn5);
        check_talk = findViewById(R.id.check_talk);
        btnWeb = findViewById(R.id.btnWeb);
        star_database = FirebaseDatabase.getInstance().getReference(Star_key);
    }

    public void onClickstar1(View view) {
        String id = star_database.getKey();
        String inn = check_inn.getText().toString();
        String company = check_compani.getText().toString();
        String name = check_name.getText().toString();
        String phone = check_phone.getText().toString();
        String otziv = check_otziv.getText().toString();
        String btn1 = check_btn1.getText().toString();
        String btn2 = check_btn2.getText().toString();
        String btn3 = check_btn3.getText().toString();
        String btn4 = check_btn4.getText().toString();
        String btn5 = check_btn5.getText().toString();
        Check_User star_user = new Check_User(id, inn, company, name, phone, otziv, btn1, null, null, null, null);
        if (!TextUtils.isEmpty(inn) && !TextUtils.isEmpty(company) && inn.length() == 7 | inn.length() == 8 | inn.length() == 9 | inn.length() == 10 | inn.length() == 11 | inn.length() == 12 | inn.length() == 13 | inn.length() == 14) {
            star_database.child("Star").push().setValue(star_user);
            Toast.makeText(this, "Спасибо за оценку!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Пустое поле или ИНН неправильный", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickstar2(View view) {
        String id = star_database.getKey();
        String inn = check_inn.getText().toString();
        String company = check_compani.getText().toString();
        String name = check_name.getText().toString();
        String phone = check_phone.getText().toString();
        String otziv = check_otziv.getText().toString();
        String btn1 = check_btn1.getText().toString();
        String btn2 = check_btn2.getText().toString();
        String btn3 = check_btn3.getText().toString();
        String btn4 = check_btn4.getText().toString();
        String btn5 = check_btn5.getText().toString();
        Check_User star_user = new Check_User(id, inn, company, name, phone, otziv, null, btn2, null, null, null);
        if (!TextUtils.isEmpty(inn) && !TextUtils.isEmpty(company) && inn.length() == 7 | inn.length() == 8 | inn.length() == 9 | inn.length() == 10 | inn.length() == 11 | inn.length() == 12 | inn.length() == 13 | inn.length() == 14) {
            star_database.child("Star").push().setValue(star_user);
            Toast.makeText(this, "Спасибо за оценку!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Пустое поле или ИНН неправильный", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickstar3(View view) {
        String id = star_database.getKey();
        String inn = check_inn.getText().toString();
        String company = check_compani.getText().toString();
        String name = check_name.getText().toString();
        String phone = check_phone.getText().toString();
        String otziv = check_otziv.getText().toString();
        String btn1 = check_btn1.getText().toString();
        String btn2 = check_btn2.getText().toString();
        String btn3 = check_btn3.getText().toString();
        String btn4 = check_btn4.getText().toString();
        String btn5 = check_btn5.getText().toString();
        Check_User star_user = new Check_User(id, inn, company, name, phone, otziv, null, null, btn3, null, null);
        if (!TextUtils.isEmpty(inn) && !TextUtils.isEmpty(company) && inn.length() == 7 | inn.length() == 8 | inn.length() == 9 | inn.length() == 10 | inn.length() == 11 | inn.length() == 12 | inn.length() == 13 | inn.length() == 14) {
            star_database.child("Star").push().setValue(star_user);
            Toast.makeText(this, "Спасибо за оценку!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Пустое поле или ИНН неправильный", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickstar4(View view) {
        String id = star_database.getKey();
        String inn = check_inn.getText().toString();
        String company = check_compani.getText().toString();
        String name = check_name.getText().toString();
        String phone = check_phone.getText().toString();
        String otziv = check_otziv.getText().toString();
        String btn1 = check_btn1.getText().toString();
        String btn2 = check_btn2.getText().toString();
        String btn3 = check_btn3.getText().toString();
        String btn4 = check_btn4.getText().toString();
        String btn5 = check_btn5.getText().toString();
        Check_User star_user = new Check_User(id, inn, company, name, phone, otziv, null, null, null, btn4, null);
        if (!TextUtils.isEmpty(inn) && !TextUtils.isEmpty(company) && inn.length() == 7 | inn.length() == 8 | inn.length() == 9 | inn.length() == 10 | inn.length() == 11 | inn.length() == 12 | inn.length() == 13 | inn.length() == 14) {
            star_database.child("Star").push().setValue(star_user);
            Toast.makeText(this, "Спасибо за оценку!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Пустое поле или ИНН неправильный, 10 или 12 цыфр", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickstar5(View view) {
        String id = star_database.getKey();
        String inn = check_inn.getText().toString();
        String company = check_compani.getText().toString();
        String name = check_name.getText().toString();
        String phone = check_phone.getText().toString();
        String otziv = check_otziv.getText().toString();
        String btn1 = check_btn1.getText().toString();
        String btn2 = check_btn2.getText().toString();
        String btn3 = check_btn3.getText().toString();
        String btn4 = check_btn4.getText().toString();
        String btn5 = check_btn5.getText().toString();
        Check_User star_user = new Check_User(id, inn, company, name, phone, otziv, null, null, null, null, btn5);
        if (!TextUtils.isEmpty(inn) && !TextUtils.isEmpty(company) && inn.length() == 7 | inn.length() == 8 | inn.length() == 9 | inn.length() == 10 | inn.length() == 11 | inn.length() == 12 | inn.length() == 13 | inn.length() == 14) {
            star_database.child("Star").push().setValue(star_user);
            Toast.makeText(this, "Спасибо за оценку!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Пустое поле или ИНН неправильный", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickstar6(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 1);
    }

    public void onClickstar7(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://rublt.bitrix24.site/chechinn")));
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && data != null) {
                switch (requestCode) {
                    case 1:
                        ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        check_otziv.setText(text.get(0));
                        break;

                }
            }
        }
    }