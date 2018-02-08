package pt.ipp.estg.sportcenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import java.util.List;

public class Preferences extends PreferenceActivity {
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);
        Preference myPref = findPreference("pref_logout");
        final Preference music = findPreference("pref_music");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showAlertDialog();
                return true;
            }
        });
        music.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (isServiceRunning(getApplicationContext(), BackgroundSoundService.class)) {
                    stopService(new Intent(getApplicationContext(), BackgroundSoundService.class));
                    music.setSummary("Ligar música");
                } else {
                    startService(new Intent(getApplicationContext(), BackgroundSoundService.class));
                    music.setSummary("Desligar música");
                }
                return true;
            }
        });
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= 21)
            builder = new AlertDialog.Builder(Preferences.this, android.R.style.Theme_Material_Dialog_Alert);
        else
            builder = new AlertDialog.Builder(Preferences.this, R.style.AlertDialog);
        builder.setTitle("Logout");
        builder.setMessage("Tem a certeza que pretende terminar a sessão?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("email");
                editor.remove("IsLoggedIn");
                editor.remove("name");
                editor.commit();
                startActivity(new Intent(getApplicationContext(), login.LoginActivity.class));
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}