package com.comeb.tchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comeb.async.ServerAPI;
import com.comeb.model.MyCredentials;

/**
 * Created by benjaminjornet on 09/10/15.
 */
public class LoginActivity extends AppCompatActivity implements SyncListener2{

    private static TextView error_message_pop_up;
    private static EditText usernameEntered;
    private static EditText passwordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        MyCredentials.setContext(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String username=MyCredentials.getLogin();
        String password=MyCredentials.getPassword();

        usernameEntered=(EditText)findViewById(R.id.Username_entrance);
        passwordEntered=(EditText)findViewById(R.id.Password_entrance);
        error_message_pop_up = (TextView) findViewById(R.id.error_message);
        
        Button loginButton = (Button)findViewById(R.id.Login_button);
        Button createAccountButton = (Button)findViewById(R.id.create_account_button);

        if(MyCredentials.isSomeOneLogged()){
           switchToTchat(LoginActivity.this,username,password);
        }

        // If we want to be connected
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameToCheck = usernameEntered.getText().toString();
                String passwordToCheck = passwordEntered.getText().toString();
                ServerAPI.getInstance().testCredentials(LoginActivity.this, usernameToCheck, passwordToCheck);
            }
        });

        // If we want to create an account
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameToCheck = usernameEntered.getText().toString();
                String passwordToCheck = passwordEntered.getText().toString();
                registerUser(LoginActivity.this,usernameToCheck,passwordToCheck);
            }
        });

    }

    private void registerUser(SyncListener2 context,String user,String password) {
        ServerAPI.getInstance().register(context, user, password);
    }
    public void switchToTchat(Context context,String login,String password){

       MyCredentials.setLogin(login);
       MyCredentials.setPassword(password);

        Intent intent = new Intent(context, TchatActivity.class);
        context.startActivity(intent);
    }

    public void error(String message,boolean userWrong,boolean passwordWrong) {
        error_message_pop_up.setText(message);
        if(userWrong) {
            usernameEntered.setText("");
        }
        if(passwordWrong) {
            passwordEntered.setText("");
        }
    }



    public void onSuccess(String username, String password){
        // call the method switchToChat
        switchToTchat(LoginActivity.this, username, password);
    }

    public void onFailure(String errorMessage){
       error(errorMessage,false,false);
    }

}
