package com.aripratom.moviecataloguesubmission4localstorage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.aripratom.moviecataloguesubmission4localstorage.R;
import com.aripratom.moviecataloguesubmission4localstorage.preferences.SettingPreferences;
import com.aripratom.moviecataloguesubmission4localstorage.services.DailyReminderService;
import com.aripratom.moviecataloguesubmission4localstorage.services.ReleaseTodayReminderService;

public class SettingActivity extends AppCompatActivity {
    private SettingPreferences preferences;

    Switch daily, release;
    TextView language;

    DailyReminderService dailyReminder;
    ReleaseTodayReminderService releaseTodayTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setTitle(R.string.setting);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = new SettingPreferences(this);

        dailyReminder = new DailyReminderService();
        releaseTodayTask = new ReleaseTodayReminderService();

        daily = findViewById(R.id.switch_daily_reminder);
        release = findViewById(R.id.switch_release_today);
        language = findViewById(R.id.tv_change_language);

        daily.setChecked(preferences.dailyChecked());
        release.setChecked(preferences.releaseChecked());

        daily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = daily.isChecked();
                if(checked){
                    dailyReminder.setDailyReminderNotif(getApplicationContext(),
                            DailyReminderService.TYPE_REPEATING, "07:00", getString(R.string.notif_msg));
                } else {
                    dailyReminder.cancelDailyReminder(getApplicationContext(),
                            DailyReminderService.TYPE_REPEATING);
                }
                preferences.setDailyReminder(checked);
            }
        });

        release.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = release.isChecked();
                if(checked) {
                    releaseTodayTask.setMovieReleaseNotif(getApplicationContext(),
                            ReleaseTodayReminderService.TYPE_REPEATING, "08:00");
                } else {
                    releaseTodayTask.cancelMovieNotif(getApplicationContext(),
                            ReleaseTodayReminderService.TYPE_REPEATING);
                }
                preferences.setReleaseTodayReminder(checked);
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
