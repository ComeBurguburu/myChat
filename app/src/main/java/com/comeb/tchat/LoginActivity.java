package com.comeb.tchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by benjaminjornet on 09/10/15.
 */
public class LoginActivity extends AppCompatActivity {
    private static String login="test";
    private static String password="test";
    private final Context context=LoginActivity.this;
    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        LoginActivity.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "LoginActivity{" +
                "password='" + password + '\'' +
                '}';
    }


    private boolean checkUsername(String currentUsername){
        if(currentUsername.equals(getLogin())&&!currentUsername.equals("")) return true;
        else return false;
    }

    private boolean checkPassword(String currentPassword){
        if(currentPassword.equals(getPassword())&&!currentPassword.equals("")) return true;
        else return false;
    }

    private boolean checkLogin(String currentUsername, String currentPassword){
        if((checkUsername(currentUsername))&&(checkPassword(currentPassword))) return true;
        else return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        SharedPreferences prefs = getSharedPreferences("users_credentials", MODE_PRIVATE);


        String username=prefs.getString("username", "");
        String password=prefs.getString("password","");



        final EditText usernameEntered=(EditText)findViewById(R.id.Username_entrance);
        final EditText passwordEntered=(EditText)findViewById(R.id.Password_entrance);
        final TextView error_message_pop_up = (TextView) findViewById(R.id.error_message);
        Button loginButton = (Button)findViewById(R.id.Login_button);
        Button createAccountButton = (Button)findViewById(R.id.create_account_button);
        if(!username.equals("")&&(!password.equals(""))){
            //usernameEntered.setText(username);
            //passwordEntered.setText(password);
            Intent intent = new Intent(context, TchatActivity.class);
            startActivity(intent);
        }

        // If we want to be connected
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String usernameToCheck = usernameEntered.getText().toString();
                String passwordToCheck = passwordEntered.getText().toString();
                if (checkLogin(usernameToCheck, passwordToCheck)) {
                    SharedPreferences prefs = getSharedPreferences("users_credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("username", usernameEntered.getText().toString());
                    editor.putString("password", passwordEntered.getText().toString());
                    editor.commit();

                    Toast.makeText(context,"T'es connecté mon baba!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, TchatActivity.class);
                    startActivity(intent);
                }
                //list.add(new ElemRight("Titi", passwordEntered.getText().toString()));
                error_message_pop_up.setText("Your username or your password isn't correct");
                usernameEntered.setText("");
                passwordEntered.setText("");

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
                if (checkLogin(usernameToCheck, passwordToCheck)) {
                    //list.add(new ElemRight("Titi", passwordEntered.getText().toString()));
                    Toast.makeText(context,"Profile already exists!",Toast.LENGTH_SHORT).show();
                    usernameEntered.setText("");
                    passwordEntered.setText("");
                    //arrayAdapter.notifyDataSetChanged();
                    //ServerAPI.getInstance().sendMessage(TchatActivity.this, "test", "test", message);
                }

                /* TODO !!!!!!!!!!!!!!!!!
                    Create a connexion with the database
                 */

                SharedPreferences prefs = getSharedPreferences("users_credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("username", usernameEntered.getText().toString());
                editor.putString("password", passwordEntered.getText().toString());
                editor.commit();

                Toast.makeText(context,"T'es connecté mon baba!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TchatActivity.class);
                startActivity(intent);
            }
        });

    }

}
