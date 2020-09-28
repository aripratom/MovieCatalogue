package com.aripratom.moviecataloguesubmission4localstorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.fragment.FavoriteFragment;
import com.aripratom.moviecataloguesubmission4localstorage.fragment.TVFragment;
import com.aripratom.moviecataloguesubmission4localstorage.preferences.SettingPreferences;
import com.aripratom.moviecataloguesubmission4localstorage.services.DailyReminderService;
import com.aripratom.moviecataloguesubmission4localstorage.services.ReleaseTodayReminderService;

import static com.aripratom.moviecataloguesubmission4localstorage.fragment.MovieFragment.newInstance;

public class MainActivity extends AppCompatActivity {
    private SettingPreferences preferences;
    DailyReminderService dailyReminder;
    ReleaseTodayReminderService releaseTodayTask;


    private boolean gantiFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.movies:
                    gantiFragment(newInstance());
                    return true;
                case R.id.tv:
                    gantiFragment(TVFragment.newInstance());
                    return true;
                case R.id.favorite:
                    gantiFragment(FavoriteFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            gantiFragment(newInstance());
        }

        preferences = new SettingPreferences(this);

        dailyReminder = new DailyReminderService();
        releaseTodayTask = new ReleaseTodayReminderService();

        if (preferences.dailyChecked()) {
            dailyReminder.setDailyReminderNotif(getApplicationContext(),
                    DailyReminderService.TYPE_REPEATING, "07:00", getString(R.string.notif_msg));
        } else {
            dailyReminder.cancelDailyReminder(getApplicationContext(),
                    DailyReminderService.TYPE_REPEATING);
        }

        if (preferences.releaseChecked()) {
            releaseTodayTask.setMovieReleaseNotif(getApplicationContext(),
                    ReleaseTodayReminderService.TYPE_REPEATING, "08:00");
        } else {
            releaseTodayTask.cancelMovieNotif(getApplicationContext(),
                    ReleaseTodayReminderService.TYPE_REPEATING);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
