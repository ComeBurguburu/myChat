package com.comeb.tchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.comeb.adapter.SimpleRecyclerAdapter;
import com.comeb.async.ServerAPI;
import com.comeb.database.DatabaseHandler;
import com.comeb.model.Message;
import com.comeb.model.MyCredentials;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class TchatActivity extends AppCompatActivity implements SyncListener {

    private static DummyFragment[] frag;

    public DummyFragment getFragment(int index) {
        return index < frag.length ? frag[index] : null;
    }

    public static SimpleRecyclerAdapter getAdapter(int frag_id) {
        return frag[frag_id].getAdapter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_animation);
        SharedPreferences prefs = getSharedPreferences("users_credentials", MODE_PRIVATE);
        MyCredentials.setLogin(prefs.getString("login", "void"));
        MyCredentials.setPassword(prefs.getString("password", "void"));

        Context context = this;

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        initialise_timer();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Snackbar.make(findViewById(R.id.tabanim_maincontent), "Ready", Snackbar.LENGTH_SHORT).show();

    }

    public void initialise_timer() {
        //get all messages each 10 seconds;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doRefresh();
                handler.postDelayed(this, 10 * 1000);
            }
        };
        handler.postDelayed(runnable, 0);

    }

    private void doRefresh() {
        // Toast.makeText(TchatActivity.this, String.valueOf(counter++), Toast.LENGTH_SHORT).show();
        ServerAPI.getInstance().getAllMessage(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        frag = new DummyFragment[3];
        frag[0] = new DummyFragment(getResources().getColor(R.color.accent_material_light));
        frag[1] = new DummyFragment(getResources().getColor(R.color.ripple_material_light));
        frag[2] = new DummyFragment(getResources().getColor(R.color.button_material_dark));
        adapter.addFrag(frag[0], "CONTACTS");
        adapter.addFrag(frag[1], "MESSAGE");
        adapter.addFrag(frag[2], "NOTES");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab_switch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_switch:
                Intent intent = new Intent(TchatActivity.this, com.comeb.tchat.ListActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public Context getContext() {
        return TchatActivity.this;
    }*/

    public static RecyclerView getRecyclerView(int i) {
        return frag[i].getRecyclerView();

    }

    @Override
    public void onSuccess(ArrayList<Message> messages) {
        getAdapter(0).setList(ServerAPI.uniqUser(messages));
        getAdapter(0).notifyDataSetChanged();
        getAdapter(1).setList(messages);
        getAdapter(1).notifyDataSetChanged();
        getRecyclerView(0).scrollToPosition(TchatActivity.getAdapter(0).getItemCount());
        getRecyclerView(1).scrollToPosition(TchatActivity.getAdapter(1).getItemCount());
        DatabaseHandler dao=DatabaseHandler.getInstance(this);
        dao.addMessages(messages);
    }

    @Override
    public void onFailure() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RESULT_LOAD_IMAGE = 1;

        ArrayList<String> encoded = new ArrayList<String>();

        System.out.println("Request code:" + requestCode);
        System.out.println("result code:" + resultCode);
        System.out.println("is data" + (null != data));

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            System.out.println("bien reçu");

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            // while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            ImageView imageView = (ImageView) frag[1].getView().findViewById(R.id.preview);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            imageView.setImageBitmap(bitmap);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            encoded.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
            // }
            cursor.close();
            setEncoded(encoded, 1);
        }
    }

    private void setEncoded(ArrayList<String> encoded, int i) {
        getFragment(i).setEncoded(encoded);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServerAPI.stopAllAsync();
    }

}