package com.comeb.model;

/**
 * Created by benjaminjornet on 13/10/15.
 */

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.comeb.tchat.R;

/*
public class AndroidSQLiteActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
/*
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Come", "9100000000"));
        db.addContact(new Contact("Benjamin", "9199999999"));
        db.addContact(new Contact("Gerard", "9522222222"));
        db.addContact(new Contact("Najat", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
}
*/

public class AndroidSQLiteActivity extends Activity {
    private String PRENOM = "prenom.txt";
    private String userName = "Apollidore";
    private File mFile = null;

    private Button mWrite = null;
    private Button mRead = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        DatabaseHandler db = new DatabaseHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Come", "9100000000"));
        db.addContact(new Contact("Benjamin", "9199999999"));
        db.addContact(new Contact("Gerard", "9522222222"));
        db.addContact(new Contact("Najat", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        // On crée un fichier qui correspond à l'emplacement extérieur
        mFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/ " + getPackageName() + "/files/" + PRENOM);

        mWrite = (Button) findViewById(R.id.create_account_button);
        mWrite.setOnClickListener(new View.OnClickListener() {

            public void onClick(View pView) {
                try {
                    // Flux interne
                    FileOutputStream output = openFileOutput(PRENOM, MODE_PRIVATE);

                    // On écrit dans le flux interne
                    output.write(userName.getBytes());

                    if(output != null)
                        output.close();

                    // Si le fichier est lisible et qu'on peut écrire dedans
                    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                            && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
                        // On crée un nouveau fichier. Si le fichier existe déjà, il ne sera pas créé
                        mFile.createNewFile();
                        output = new FileOutputStream(mFile);
                        output.write(userName.getBytes());
                        if(output != null)
                            output.close();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mRead = (Button) findViewById(R.id.Login_button);
        mRead.setOnClickListener(new View.OnClickListener() {

            public void onClick(View pView) {
                try {
                    FileInputStream input = openFileInput(PRENOM);
                    int value;
                    // On utilise un StringBuffer pour construire la chaîne au fur et à mesure
                    StringBuffer lu = new StringBuffer();
                    // On lit les caractères les uns après les autres
                    while((value = input.read()) != -1) {
                        // On écrit dans le fichier le caractère lu
                        lu.append((char)value);
                    }
                    Toast.makeText(AndroidSQLiteActivity.this, "Interne : " + lu.toString(), Toast.LENGTH_SHORT).show();
                    if(input != null)
                        input.close();

                    if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        lu = new StringBuffer();
                        input = new FileInputStream(mFile);
                        while((value = input.read()) != -1)
                            lu.append((char)value);

                        Toast.makeText(AndroidSQLiteActivity.this, "Externe : " + lu.toString(), Toast.LENGTH_SHORT).show();
                        if(input != null)
                            input.close();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
