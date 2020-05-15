package ru.rublt.checkitout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import ru.rublt.checkitout.Models.User;

public class MainActivity extends AppCompatActivity {

    //----/--------------------------------------Auth-google
    private static final String TAG = "AndroidClied";
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    //-===================================-----Auth-google

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    Button emailSignInButton, emailCreateAccountButton, ButtonGoogle;

    RelativeLayout root;
//--------------------------------------------
   // private static final int MY_REQUEST_CODE = 7117; //Any number you want
   // List<AuthUI.IdpConfig> providers;  // AuthList
    //Button btn_sign_out;
//------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        //--------/------------------------------------Auth-google


        googleSignInButton = findViewById(R.id.btn_in_Google);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 100);
            }
        });
//-======================================================-Auth-google
        emailSignInButton = findViewById(R.id.emailSignInButton);
        emailCreateAccountButton = findViewById(R.id.emailCreateAccountButton);
        //ButtonGoogle = findViewById(R.id.btn_in_Google);

        root = findViewById(R.id.main_layoutt); // Присвоили id Layouty MainAcnivity для реализации функции в кнопке Добавить при проверке пустой строки Email
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        //signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View v) {
       //         //реализует нажатие кнопки
      //          showSignInGoogle();
      //      }
      //  });

        emailCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //реализует нажатие кнопки
                showRegisterWindow();
            }
        });

        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //реализует нажатие кнопки
                showSignInWindow();
            }
        });

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    // реализуем функцию нажатия кнопки btnSignIn
    private void showSignInWindow() {
//Allert создает всплывающиен окна
        AlertDialog.Builder dilog = new AlertDialog.Builder(this); //Отображает всплывающие окна
        dilog.setTitle("Войти");
        dilog.setMessage("Введите данные для входа");
        // Загружаем шаблон
        LayoutInflater inflater = LayoutInflater.from(this); // созддаалли  оббьеектт  дляя получченнияя  шаблоннчиика
        View sing_in_window = inflater.inflate(R.layout.sing_in_window, null);
        dilog.setView(sing_in_window);

        final MaterialEditText email = sing_in_window.findViewById(R.id.emailFild); //final вызывает из вне в кнопке Добавить и означает что является Константой
        final MaterialEditText pass = sing_in_window.findViewById(R.id.passFild);

        // создаем кнопки отмены всплывающего окна и регистрации
        dilog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss(); // dismsiss - скрывает окно
            }
        });

        //пишем вторую кноппку, функцию обрабатывающую информацию и регистрируем в базу
        dilog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }

                if(pass.getText().toString().length() <5) {
                    Snackbar.make(root, "Введите ваш пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }
                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Если авторизация  прошла  успешно, переккидываем на другую странице Activiti
                                startActivity(new Intent(MainActivity.this, CheckActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Ошибка авторизацции. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();            //Если не  успешноо авторизовали пользователя, e.getMessege - Сама ошиббка
                    }
                });
            }
        }); // Конструкция проверяет пустую строку

        // чтобы показать наш диалог мы напишем ниже следующую функцию
        dilog.show();
    }


    private void showRegisterWindow() {
        //Allert создает всплывающиен окна
        AlertDialog.Builder dilog = new AlertDialog.Builder(this);
        dilog.setTitle("Зарегистрироваться");
        dilog.setMessage("Введите все данные для регистрации");
        // Загружаем шаблон
        LayoutInflater inflater = LayoutInflater.from(this);
        View registor_window = inflater.inflate(R.layout.registor_window, null);
        dilog.setView(registor_window);

        final MaterialEditText email = registor_window.findViewById(R.id.emailFild); //final вызывает из вне в кнопке Добавить и означает что является Константой
        final MaterialEditText pass = registor_window.findViewById(R.id.passFild);
        final MaterialEditText name = registor_window.findViewById(R.id.nameFild);
        final MaterialEditText phone = registor_window.findViewById(R.id.phonelFild);

        // создаем кнопки отмены всплывающего окна и регистрации
        dilog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss(); // dismsiss - скрывает окно
            }
        });

        //пишем функцию обрабатывающую информацию и регистрируем в базу
        dilog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }
                // Далее прописываем для других полей ошибки pass, name, phone
                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }
                if(TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "Введите ваш телефон", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }
                if(pass.getText().toString().length() <=5) {
                    Snackbar.make(root, "Введите ваш пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show(); // Позволяет выводить различные ошибки в сплывающих окнах LENGTH_SHORT показывает 3 секунды всплывающее окно
                    return; // Для того чтобы если вызывается ошибка мы сразу выходили из этой функции
                }

                // Дальше идет регистрация пользователя после ошибок если они возникали
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // onSucccess добовляет только точно зарегистрированного пользователя он добавляет не только емаил и пароль но также и имя и телефон, чирбы эти данные сохранить мы создадим новый клас будем помещать в него все необходимые данные создаем новую папке Models (New Package там новый класс User) рядом с Mainactivity.java
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPass(pass.getText().toString());
                        user.setPhone(phone.getText().toString());
                        // Дальше передаем в базу данных

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(root, "Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                    }
                                }); // Таблицу users добавили нового пользователя user, а child это ключ для его идентификации
                    }
                });

            }
        }); // Конструкция проверяет пустую строку

        // чтобы показать наш диалог мы напишем ниже следующую функцию
        dilog.show();

    }
    //--------------/---------------Auth-google
    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

    //-=============================================--Auth-google
    //-----------/----------------------------------Auth-google
    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, CheckActivity.class);
        intent.putExtra(CheckActivity.GOOGLE_ACCOUNTT, googleSignInAccount);

        startActivity(intent);
        finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 100:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);


                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    //=============================================-Auth-google

}
