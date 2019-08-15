package com.himanshu.quicksell.SignUp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.himanshu.quicksell.MainActivity;
import com.himanshu.quicksell.NetworkManager.NetworkChangeReceiver;
import com.himanshu.quicksell.R;
import com.himanshu.quicksell.SignUp.SignUpPage;

public class LoginActivity extends AppCompatActivity {


    static EditText username, password;
    private BroadcastReceiver mNetworkReceiver;
    static TextView tv_check_connection;
    static Button login_btn;
    static TextView forgetPassword, googleLogin, SignUp;
    private FirebaseAuth mAuth;
    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient googleSignInClient;

    public static void dialog(boolean value) {

        if (value) {
            username.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.VISIBLE);
            forgetPassword.setVisibility(View.VISIBLE);
            googleLogin.setVisibility(View.VISIBLE);
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
            forgetPassword.setVisibility(View.GONE);
            googleLogin.setVisibility(View.GONE);
            SignUp.setVisibility(View.GONE);
            tv_check_connection.setVisibility(View.VISIBLE);
            tv_check_connection.setText("Could not Connect to internet");
            tv_check_connection.setBackgroundColor(Color.rgb(236, 112, 99));
            tv_check_connection.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init_Views();

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();
    }

    public void init_Views() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.btn_login);
        forgetPassword = findViewById(R.id.forgot_password);
        googleLogin = findViewById(R.id.facebook_login);
        SignUp = findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();
        tv_check_connection = findViewById(R.id.network);


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(i);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void SignInGoogle(View view) {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i, GOOGLE_SIGN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthwithGoogle(account);
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthwithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);


                } else {
                    Toast.makeText(getApplicationContext(), "Something Went Wrong!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        } else {
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
            Toast.makeText(getApplicationContext(), "Sending Login Request", Toast.LENGTH_SHORT).show();
            Login_With_Firebase(user_name, pass_word);
            return;
        } else {
            username.setError("Enter a valid email..");
            username.requestFocus();
            return;
        }


    }

    private void Login_With_Firebase(String user_name, String pass_word) {

        mAuth.signInWithEmailAndPassword(user_name, pass_word)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }

                        // ...
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}
