package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String Pref_File = "LangPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String username = "admin", pass = "hello";
    Intent open_intent;


    public void setLangSettings()
    {
        editor.putString("en_User_hint", "Username");
        editor.putString("en_Passwd_hint", "Password");
        editor.putString("en_login_title", "LOGIN");
        editor.putString("en_reg_button", "SIGN IN");
        editor.putString("en_login_button", "CONTINUE");
        editor.putString("en_change_b", "CHANGE PASSWORD");
        editor.putString("en_del_b", "DELETE");
        editor.putString("en_List_Hint", "Welcome!");
        editor.putString("en_toast", "Welcome!");
        editor.putString("en_toast_wrong","Wrong!");

        editor.putString("ru_User_hint", "Логин");
        editor.putString("ru_Passwd_hint", "Пароль");
        editor.putString("ru_login_title", "АВТОРИЗАЦИЯ");
        editor.putString("ru_login_button", "ПРОДОЛЖИТЬ");
        editor.putString("ru_reg_button", "ЗАРЕГИСТРИРОВАТЬСЯ");
        editor.putString("ru_change_b", "ИЗМЕНИТЬ ПАРОЛЬ");
        editor.putString("ru_del_b", "УДАЛИТЬ");
        editor.putString("ru_List_Hint", "Добро пожаловать!");
        editor.putString("ru_toast", "Добро пожаловать!");
        editor.putString("ru_toast_wrong","Неверно!");

        editor.apply();
    }

    public void setLangEn()
    {
        login.setHint(settings.getString("en_User_hint",""));
        passwd.setHint(settings.getString("en_Passwd_hint",""));
        title.setText(settings.getString("en_login_title",""));
        b_enter.setText(settings.getString("en_login_button",""));
        b_reg.setText(settings.getString("en_reg_button",""));
        toast_text = settings.getString("en_toast","");
        toast_wrong = settings.getString("en_toast_wrong","");
    }

    public void setLangRu()
    {
        login.setHint(settings.getString("ru_User_hint",""));
        passwd.setHint(settings.getString("ru_Passwd_hint",""));
        title.setText(settings.getString("ru_login_title",""));
        b_enter.setText(settings.getString("ru_login_button",""));
        b_reg.setText(settings.getString("ru_reg_button",""));
        toast_text = settings.getString("ru_toast","");
        toast_wrong = settings.getString("ru_toast_wrong","");
    }

    TextView title;
    EditText login;
    EditText passwd;
    String toast_text, toast_wrong;
    Button b_enter, b_reg;
    Button b_lang;
    String lang;

    ArrayList<User> Users = new ArrayList<>();
    DataBaseHandler DBH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBH = new DataBaseHandler(this);
        settings = getSharedPreferences(Pref_File,Context.MODE_PRIVATE);
        editor = settings.edit();

        /*editor.putString("lang","null");
        editor.apply();*/
        lang = settings.getString("lang","null");

        open_intent = new Intent(this, Lists.class);

        //DBH.onUpgrade();

        if(lang.equals("null"))
        {
            setLangSettings();
            editor.putString("lang","ENG");
            editor.apply();
        }

        title = findViewById(R.id.auth);
        login = findViewById(R.id.login);
        passwd = findViewById(R.id.passwd);

        b_enter = findViewById(R.id.b_enter);
        b_reg = findViewById(R.id.b_reg);
        b_lang = findViewById(R.id.lang);

        lang = settings.getString("lang","");
        if(lang.equals("РУС"))
        {
            setLangRu();
        }
        else    //Eng by default
        {
            setLangEn();
        }

        b_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String name = login.getText().toString();
                String pswd = passwd.getText().toString();
                if(name.equals(username) && pass.equals(pswd))
                {
                    open_intent  = new Intent(MainActivity.this, Lists.class);
                    Toast.makeText(MainActivity.this,toast_text,Toast.LENGTH_LONG).show();
                    //open_intent.putExtra("lang_set",lang);
                    startActivity(open_intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this,toast_wrong, Toast.LENGTH_LONG).show();
                }*/

                Boolean exist = false;
                Users = DBH.getAllUsers();
                for(User user: Users)
                {
                    if((user.getUsername().equals(login.getText().toString())) && (user.getPassword().equals(passwd.getText().toString())))
                    {
                        Toast.makeText(MainActivity.this,"Вход выполнен!",Toast.LENGTH_LONG).show();
                        exist = true;
                        startActivity(open_intent);
                        break;
                    }
                    /*else
                    {
                        //DBH.addUser(login.getText().toString(),passwd.getText().toString());
                        //Toast.makeText(MainActivity.this,"Такой пользователь уже существует!",Toast.LENGTH_LONG).show();
                    }*/
                }
                if(!exist)
                {
                    Toast.makeText(MainActivity.this,"Неверный логин или пароль!",Toast.LENGTH_LONG).show();
                }
            }
        });

        b_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang = settings.getString("lang","");
                if(lang.equals("ENG"))
                {
                    editor.putString("lang", "РУС");
                    setLangRu();
                }
                else
                {
                    editor.putString("lang", "ENG");
                    setLangEn();
                }
                editor.apply();
            }
        });

        b_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(login.getText().toString().equals("") || passwd.getText().toString().equals("")))
                {
                    int res = DBH.addUser(login.getText().toString(),passwd.getText().toString());
                    if(res == 0)
                    {
                        Toast.makeText(MainActivity.this,"Регистрация прошла успешно!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Такой пользователь уже существует!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }



    @Override
    protected void onStart(){
        super.onStart();
        Log.v("Вызван метод", "MainActivity.onStart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.v("Вызван метод", "MainActivity.onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v("Вызван метод", "MainActivity.onPause");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("Вызван метод", "MainActivity.onRestart");
    }

    protected void onStop(){
        super.onStop();
        Log.i("Вызван метод", "MainActivity.onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("Вызван метод", "MainActivity.onDestroy");
    }
}