package com.abhi.ourvedic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginPage extends AppCompatActivity {

    ProgressDialog progressDialog;
    private static final String TAG = "loginpage";
    private EditText temail, tpassword;
    private GoogleSignInClient mgoogleSignInClient;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String EMAIL = "email";
    private TextView forgetText;
    TextView login_as_admin;
    TextView facebook_sign_in_button;
    private CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        progressDialog = new ProgressDialog(LoginPage.this);
        RelativeLayout signInButton = findViewById(R.id.sign_in_button);
        temail = findViewById(R.id.loginemailteacher);
        tpassword = findViewById(R.id.loginpasswordteacher);
        forgetText = findViewById(R.id.forgetpassword);
        login_as_admin = findViewById(R.id.login_as_admin);
        login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginPage.this, Admin_Panel_Login.class);
                startActivity(i);
            }
        });

        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, ForgetPassword.class));
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog_view);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mgoogleSignInClient = GoogleSignIn.getClient(this, gso);


        callbackManager = CallbackManager.Factory.create();

        facebook_sign_in_button = findViewById(R.id.facebook_sign_in_button);
        facebook_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }


    public void registrationbutton(View view) {
        Intent i = new Intent(LoginPage.this, RegistrationPage.class);
        startActivity(i);
    }

    public void loginbuttonteacher(View view) {
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final String email = temail.getText().toString().trim();
        String password = tpassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "not valid email address", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                SharedPreferences sh = getSharedPreferences("email", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sh.edit();
                                edit.putString("email", email);
                                edit.commit();
                                Log.d(TAG, "signInWithEmail:success");
                                gotonext();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                progressDialog.dismiss();
                                Toast.makeText(LoginPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }
    }

    private void gotonext() {
        progressDialog.dismiss();
        Intent i = new Intent(LoginPage.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void signIn() {
        Intent signInIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data); //for facebook login
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            progressDialog.dismiss();
                            gotonext();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                        // ...
                    }
                });

    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken != null) {
                Intent i2 = new Intent(LoginPage.this, MainActivity.class);
                startActivity(i2);
            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    public void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                //for abhishek
                    /*
                try {

                    first_name = object.getString("first_name");
                    last_name = object.getString("last_name");
                    email = object.getString("email");
                    id = object.getString("id");
                    String img_url = "https://graph.facebook.com/"+ id + "/picture?type=normal";


                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("name", first_name + " " + last_name);
                    i.putExtra("email", email);
                    i.putExtra("img_url", img_url);
                    Log.v("name", first_name + last_name);
                    Log.v("email", email);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
