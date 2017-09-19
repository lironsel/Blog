package com.moveosoftware.blog.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moveosoftware.blog.R;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mUser;
    private Button loginButton;
    private Button createActButton;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        loginButton = (Button) findViewById(R.id.loginbuttonEt);
        createActButton = (Button) findViewById(R.id.loginCreateAccount);
        emailField = (EditText) findViewById(R.id.loginEmaiEt);
        passwordField = (EditText) findViewById(R.id.loginPasswordEt);


        //on change in auth
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            public void onAuthStateChanged(FirebaseAuth firebaseAuth){
        mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(MainActivity.this,"signed in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, PostListActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"not signed in", Toast.LENGTH_LONG).show();
                }
            }
        };


        // on click login- get the details ang log in
        loginButton.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailField.getText().toString())
                        && !TextUtils.isEmpty(passwordField.getText().toString())) {

                    String email = emailField.getText().toString();
                    String pwd = passwordField.getText().toString();

                    login(email, pwd);




                }else {

                }
            }
        });
    }

    private void login(String email, String pwd) {

        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //the user has been signed in
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Yay!! We're in!
                            Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_LONG)
                                    .show();

                            startActivity(new Intent(MainActivity.this, PostListActivity.class));
                            finish();
                        }else {
                            // Not it!
                            Toast.makeText(MainActivity.this, "noooo", Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });

    }

    @Override
    //choose action when clicked on menu
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.action_signout){
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    //creating the menu
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    protected void onStop(){
        super.onStop();
       if (mAuthListener!=null)
           mAuth.removeAuthStateListener(mAuthListener);
    }
}
