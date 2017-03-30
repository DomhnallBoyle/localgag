package com.example.domhnallboyle.localgag.Screens.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.domhnallboyle.localgag.Engine.Database.FunctionType;
import com.example.domhnallboyle.localgag.Engine.Database.Functions;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class MainScreenActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private EditText searchBar;
    private TextView slider_name, slider_bio;
    private ImageView slider_image;
    private CircularProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressView = (CircularProgressView)findViewById(R.id.progress_view_fragment);
        progressView.setVisibility(View.INVISIBLE);
        progressView.stopAnimation();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        slider_name = (TextView)header.findViewById(R.id.slide_menu_name);
        slider_bio = (TextView)header.findViewById(R.id.slide_menu_bio);
        slider_image = (ImageView)header.findViewById(R.id.slide_menu_image);
        slider_name.setText(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Name", "Please Edit Your Profile!"));
        slider_bio.setText(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Bio", "Please Edit Your Bio!"));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.wall:
                        new Functions(getApplicationContext(), getSupportFragmentManager(), FunctionType.GET_POSTS, progressView)
                                .execute(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Username", ""));
                        break;
                    case R.id.profile:
                        new Functions(getApplicationContext(), getSupportFragmentManager(), FunctionType.GET_PROFILE, progressView)
                                .execute(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Username", ""));
                        break;
                    case R.id.messages:
                        Intent messaging = new Intent(MainScreenActivity.this, MessagingActivity.class);
                        MainScreenActivity.this.startActivity(messaging);
                        break;
                    case R.id.settings:
                        break;
                    case R.id.about:
                        break;
                    case R.id.logout:
                        DeviceStorage.setValueSharedPreferences(getApplicationContext(), "Session", 0);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                        break;
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        searchBar = (EditText)findViewById(R.id.search_bar);

        new Functions(getApplicationContext(), getSupportFragmentManager(), FunctionType.GET_POSTS, this.progressView)
                .execute(DeviceStorage.getStringSharedPreferences(getApplicationContext(), "Username", ""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.create_status:
                Intent write_status = new Intent(MainScreenActivity.this, WriteStatusActivity.class);
                MainScreenActivity.this.startActivity(write_status);
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
