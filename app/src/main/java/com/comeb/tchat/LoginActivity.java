package com.comeb.tchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.comeb.async.ServerAPI;

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
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("users_credentials", MODE_PRIVATE);

        final String username=prefs.getString("login", "");
        String password=prefs.getString("password","");

        usernameEntered=(EditText)findViewById(R.id.Username_entrance);
        passwordEntered=(EditText)findViewById(R.id.Password_entrance);
        error_message_pop_up = (TextView) findViewById(R.id.error_message);
        
        Button loginButton = (Button)findViewById(R.id.Login_button);
        Button createAccountButton = (Button)findViewById(R.id.create_account_button);

        if(!username.equals("")&&(!password.equals(""))){
           switchToTchat(LoginActivity.this,username,password);
        }

        // If we want to be connected
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameToCheck = usernameEntered.getText().toString();
                String passwordToCheck = passwordEntered.getText().toString();
                ServerAPI.getInstance().testCredentials(LoginActivity.this, usernameToCheck, passwordToCheck);

                //arrayAdapter.notifyDataSetChanged();
                //ServerAPI.getInstance().sendMessage(TchatActivity.this, "test", "test", message);
            }
        });

        // If we want to create an account
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameToCheck = usernameEntered.getText().toString();
                String passwordToCheck = passwordEntered.getText().toString();

                //TODO
                /* TODO !!!!!!!!!!!!!!!!!
                    Create a connexion with the database
                 */

                switchToTchat(LoginActivity.this,usernameToCheck,passwordToCheck);


            }
        });

    }
    public static void switchToTchat(Context context,String login,String password){
        SharedPreferences prefs = context.getSharedPreferences("users_credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("login", login);
        editor.putString("password", password);
        editor.commit();

        Toast.makeText(context,"T'es connecté mon baba!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, TchatActivity.class);
        context.startActivity(intent);
    }

    public static void error(String message) {
        
        error_message_pop_up.setText(message);
        usernameEntered.setText("");
        passwordEntered.setText("");
    }

    public void onSuccess(String username, String password){
        // call the method switchToChat
        switchToTchat(LoginActivity.this, username, password);
    }

    public void onFailure(String errorMessage){
        //Just a toast to notify the user
        Toast.makeText(LoginActivity.this,"Connexion failed " + errorMessage,Toast.LENGTH_SHORT).show();
    }

}
