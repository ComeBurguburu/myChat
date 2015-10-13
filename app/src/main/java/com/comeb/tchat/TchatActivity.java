package com.comeb.tchat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.comeb.adapter.SimpleRecyclerAdapter;
import com.comeb.com.comeb.async.ServerAPI;
import com.comeb.model.Elem;
import com.comeb.model.ElemLeft;

import java.util.ArrayList;
import java.util.List;

public class TchatActivity extends AppCompatActivity {

    private static DummyFragment []frag;
    private int counter=0;
    private static String login="test2";
    private static String  password="test2";

    public static String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static SimpleRecyclerAdapter getAdapter(int frag_id) {
        return frag[frag_id].getAdapter();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_animation);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tabanim_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabanim_tabs);
        tabLayout.setupWithViewPager(viewPager);

        ArrayList list = new ArrayList();
        list.add(new ElemLeft(getLogin(), getPassword()));

     //   initialise_timer();
        //ServerAPI.getInstance().testCredentialsString(TchatActivity.this,getLogin(),getPassword());
       // ServerAPI.getInstance().testMessageString(TchatActivity.this,getLogin(),getPassword());
        ServerAPI.getInstance().getAllMessage(getContext());

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

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //foobar();
               // Toast.makeText(TchatActivity.this, String.valueOf(counter++), Toast.LENGTH_SHORT).show();
                ServerAPI.getInstance().getAllMessage(getContext());
                handler.postDelayed(this, 10 * 1000);

            }
        };
        handler.postDelayed(runnable, 0);

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

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Context getContext() {
        return TchatActivity.this;
    }

    public static RecyclerView getRecyclerView(int i) {
       return frag[i].getRecyclerView();

    }


    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class DummyFragment extends Fragment {
        int color;
        private SimpleRecyclerAdapter adapter;
        private RecyclerView recyclerView;

        public RecyclerView getRecyclerView(){
            return recyclerView;
        }

        public DummyFragment() {
        }

        public SimpleRecyclerAdapter getAdapter() {
            return this.adapter;
        }

        @SuppressLint("ValidFragment")
        public DummyFragment(int color) {
            this.color = color;

        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dummy_fragment, container, false);

            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dummyfrag_bg);
            frameLayout.setBackgroundColor(color);
            final EditText ed = (EditText) view.findViewById(R.id.edit);
            ImageView v = (ImageView) view.findViewById(R.id.send);
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String message = ed.getText().toString();
                    if (!message.equals("")) {
                        //list.add(new ElemRight("Titi", ed.getText().toString()));
                        ed.setText("");
                        //  arrayAdapter.notifyDataSetChanged();
                        ServerAPI.getInstance().sendMessage(getContext(), message);
                    }
                }
            });
            recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);
          //  recyclerView.setHasFixedSize(true);
            if (adapter == null) {
                adapter = new SimpleRecyclerAdapter(new ArrayList<Elem>());
            }
            recyclerView.setAdapter(adapter);
            return view;
        }
    }
}