package com.himanshu.quicksell.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.himanshu.quicksell.MainActivity;
import com.himanshu.quicksell.Model.User_Model;
import com.himanshu.quicksell.NetworkManager.NetworkChangeReceiver;
import com.himanshu.quicksell.R;

import java.util.ArrayList;
import java.util.List;

public class SignUpPage extends AppCompatActivity {

    static EditText username, password;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;
    static Button login_btn;
    static TextView SignUp;
    private FirebaseAuth mAuth;
    List<DocumentSnapshot> document_id = new ArrayList<DocumentSnapshot>();
    DocumentReference documentReference;
    User_Model user_model;
    CollectionReference collectionReference;
    FirebaseFirestore db;

    public static void dialogg(boolean value) {

        if (value) {
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.VISIBLE);
            SignUp.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Back Online !!!");
            tv_check_connection.setBackgroundColor(Color.rgb(88, 214, 141));
            tv_check_connection.setTextColor(Color.WHITE);
            Handler handler = new Handler();
            Runnable delayrunnable = new Runnable() {
                @Override
                public void run() {
                    tv_check_connection.setVisibility(View.GONE);
                }
            };
            handler.postDelayed(delayrunnable, 2500);
        } else {
            username.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            login_btn.setVisibility(View.GONE);
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.rgb(236, 112, 99));
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        init_Views();

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        user_model = new User_Model(document_id);
    }

    public void init_Views() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.btn_login);
        SignUp = findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();
        tv_check_connection = findViewById(R.id.network);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void Login_Button(View view) {
        String user_name = username.getText().toString().trim();
        String pass_word = password.getText().toString().trim();
        if (user_name.isEmpty()) {
            username.setError("Email is required..");
            username.requestFocus();
            return;
        }

        if (pass_word.isEmpty()) {
            password.setError("Password is required..");
            password.requestFocus();
            return;
        }

        if (pass_word.length() <= 6) {
            password.setError("Password should be greater than 6 words");
            password.requestFocus();
            return;
        }

        if (isValidEmail(user_name) && pass_word.length() > 6) {
            Toast.makeText(getApplicationContext(), "Sending Request", Toast.LENGTH_SHORT).show();
            Login_With_Firebase(user_name, pass_word);
            return;
        } else {
            username.setError("Enter a valid email..");
            username.requestFocus();
            return;
        }


    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void Login_With_Firebase(final String user_name, String pass_word) {

        mAuth.createUserWithEmailAndPassword(user_name, pass_word)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mAuth.getCurrentUser();
                            documentReference = collectionReference.document(user.getUid());
                            documentReference.set(user_model)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            updateUI(user);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Something Went Wrong..",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            username.setError("User with this email already exist.");
                            username.setFocusable(true);
                        } else {
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(), "Nothing Happens", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

}
