package com.comeb.tchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.comeb.adapter.SimpleRecyclerAdapter;
import com.comeb.async.ServerAPI;
import com.comeb.database.DatabaseHandler;
import com.comeb.model.Message;
import com.comeb.model.MyCredentials;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
        MyCredentials.setContext(TchatActivity.this);

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
        Snackbar.make(findViewById(R.id.tabanim_maincontent), getString(R.string.connected_as, MyCredentials.getLogin()), Snackbar.LENGTH_SHORT).show();

    }

    public void initialise_timer() {
        //get all messages each 10 seconds;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doRefresh();
                handler.postDelayed(this, 5 * 1000);
            }
        };
        handler.postDelayed(runnable, 0);

    }

    private void doRefresh() {
        ServerAPI.getInstance().getAllMessage(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        Context context = TchatActivity.this;
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        frag = new DummyFragment[3];
        frag[0] = new DummyFragment(ContextCompat.getColor(context, R.color.accent_material_light));
        frag[1] = new TchatFragment(ContextCompat.getColor(context, R.color.ripple_material_light));
        frag[2] = new AboutFragment(ContextCompat.getColor(context, R.color.button_material_dark));
        adapter.addFrag(frag[0], context.getString(R.string.contacts));
        adapter.addFrag(frag[1], context.getString(R.string.messages));
        adapter.addFrag(frag[2], context.getString(R.string.about));
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
            case R.id.disconnect:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        MyCredentials.setLogin("");
        MyCredentials.setPassword("");

        Context context = TchatActivity.this;

        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        finish();
    }


    public static RecyclerView getRecyclerView(int i) {
        return frag[i].getRecyclerView();

    }

    @Override
    public void onSuccess(ArrayList<Message> messages) {
        getAdapter(0).setList(ServerAPI.uniqUser(messages));
        getAdapter(0).notifyDataSetChanged();
        getAdapter(1).setList(messages);
        getAdapter(1).notifyDataSetChanged();
        getRecyclerView(0).scrollToPosition(getAdapter(0).getItemCount());
        getRecyclerView(1).scrollToPosition(getAdapter(1).getItemCount());
        DatabaseHandler dao = DatabaseHandler.getInstance(this);
        dao.addMessages(messages);
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, getString(R.string.no_connexion), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RESULT_LOAD_IMAGE = 1;
        ArrayList<String> encoded = new ArrayList<String>();

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
          /*  String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            // while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            */
            ImageView imageView = (ImageView) frag[1].getView().findViewById(R.id.preview);
            InputStream is = null;

            try {
                is = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();


                //  Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                imageView.setImageBitmap(bitmap);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                encoded.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
                // }
                //   cursor.close();
                setEncoded(encoded);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void setEncoded(ArrayList<String> encoded) {
        getFragment(1).setEncoded(encoded);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServerAPI.stopAllAsync();
    }
}