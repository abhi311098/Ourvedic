package com.abhi.ourvedic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash_Screen1 extends AppCompatActivity {

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen1);
        int SPLASH_DISPLAY_LENGTH = 1500;
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if (user!=null) {
                    startActivity(new Intent(Splash_Screen1.this,MainActivity.class));
                } else {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(Splash_Screen1.this, gso);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();
                    mGoogleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SharedPreferences sharedPreferences = getSharedPreferences("impkey", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();
                            Toast.makeText(Splash_Screen1.this, "done", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Splash_Screen1.this, LoginPage.class);
                            startActivity(i);
                            finish();
                        }
                    });
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}