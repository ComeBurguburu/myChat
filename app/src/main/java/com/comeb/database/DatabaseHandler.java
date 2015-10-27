package com.comeb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.comeb.model.Message;
import com.comeb.model.MessageLeft;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by benjaminjornet on 13/10/15.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static DatabaseHandler singleton;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "messagesManager";

    // Contacts table name
    private static final String TABLE_MESSAGES = "messages";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MESSAGE = "message";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE); // TO CHANGE !!!!!!!!!
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new message
    public void addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
       // values.put(KEY_ID, message.getId()); // Message ID
        values.put(KEY_NAME, message.getPseudo()); // Message Name
        values.put(KEY_MESSAGE, message.getMessage()); // Message

        // Inserting Row
        db.insert(TABLE_MESSAGES,null,values);
      /*  String myQuery = "INSERT INTO " + TABLE_MESSAGES + " (" +
                KEY_NAME + ", " + KEY_MESSAGE + ") VALUES (\"" +
                message.getPseudo() + "\", \"" +
                message.getMessage() + "\")";
        db.execSQL(myQuery);*/
        db.close(); // Closing database connection
    }
    public void addMessages(ArrayList<Message> messages){
        if(messages==null || messages.isEmpty()){
            return;
        }
        Iterator<Message> it = messages.iterator();
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_MESSAGES,null,null);
        while(it.hasNext()){
            addMessage(it.next());
        }
    }

    // Getting single message
    public Message getMessage(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGES + " WHERE " + KEY_ID + " = " + id, null);

        if (cursor.getCount() == 0) {
            cursor.close();
            System.out.println("No result");
            return null;
        }
        cursor.moveToNext();


        try {
            System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(KEY_MESSAGE)));
        } catch (Exception e) {
            System.out.println("column  not found");
        }
        //Message message = new Message( cursor.getInt(Colo), cursor.getString(1), cursor.getString(2));
        //return message;
        return null;
    }

    // Getting All Contacts
    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messageList = new ArrayList<Message>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Message message = new MessageLeft(cursor.getInt(0), cursor.getString(1), cursor.getString(2), "");
            // Adding message to list
            messageList.add(message);
        }


        return messageList;
    }

    // Updating single message
    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, message.getPseudo());
        values.put(KEY_MESSAGE, message.getMessage());

        // updating row
        return db.update(TABLE_MESSAGES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(message.getId())});
    }

    // Deleting single message
    public void deleteMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, KEY_ID + " = ?",
                new String[]{String.valueOf(message.getId())});
        db.close();
    }


    // Getting messages Count
    public int getMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}