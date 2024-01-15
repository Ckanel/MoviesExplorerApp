package com.example.myapplication.Network;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.SecondActivity;

import java.util.List;

public abstract class NetworkCheck extends AppCompatActivity {
    // this guide was used to help with this check https://developer.android.com/develop/connectivity/network-ops/managing
    // but some things on the guide are deprecated and old so i had to change some things
    private boolean isOnline = true;

    private BroadcastReceiver connectionChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (checkNetworkConnection()) {
                // Network is available
                if (!isOnline) {
                    // Get the current activity
                    ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
                    List<ActivityManager.AppTask> taskList = activityManager.getAppTasks();
                    String currentActivityName = taskList.get(0).getTaskInfo().topActivity.getClassName();
                    if (currentActivityName.equals("com.example.myapplication.Activities.MainActivity")) {
                        // If MainActivity is on the screen, refresh it
                        Intent mainActivityIntent = new Intent(context, MainActivity.class);
                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainActivityIntent);
                    } else {
                        // If another activity is on the screen, refresh that
                        Intent secondActivityIntent = new Intent(context, SecondActivity.class);
                        secondActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        secondActivityIntent.putExtra("id", getIntent().getIntExtra("id", 0));
                        startActivity(secondActivityIntent);
                    }
                    isOnline = true;
                }

            } else {
                // Network is unavailable
                displayNetworkAlert();
                isOnline = false;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister BroadcastReceiver when app is in background.
        unregisterReceiver(connectionChangeReceiver);
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public void displayNetworkAlert() {
        if (!checkNetworkConnection()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

}
