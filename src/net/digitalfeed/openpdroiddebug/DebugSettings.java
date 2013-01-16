package net.digitalfeed.openpdroiddebug;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import android.os.Bundle;
import android.privacy.IPrivacySettingsManager;
import android.privacy.PrivacySettings;
import android.privacy.PrivacySettingsManager;
import android.privacy.PrivacySettingsManagerService;
import android.privacy.surrogate.PrivacyAccountManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Config;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DebugSettings extends Activity {

    TextView outputText;

    CheckBox cacheEnabled;
    CheckBox notificationsEnabled;
    CheckBox databaseOpenCloseEnabled;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_pdroid_tester);
        outputText = (TextView)findViewById(R.id.output_display);

        cacheEnabled = (CheckBox)findViewById(R.id.cache_enabled);
        notificationsEnabled = (CheckBox)findViewById(R.id.notifications_enabled);
        databaseOpenCloseEnabled = (CheckBox)findViewById(R.id.database_openclose_enabled);

        PrivacySettingsManager prvMgr = (PrivacySettingsManager)context.getSystemService("privacy");
        cacheEnabled.setChecked(prvMgr.getDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_USE_CACHE));
        notificationsEnabled.setChecked(prvMgr.getDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_SEND_NOTIFICATIONS));
        databaseOpenCloseEnabled.setChecked(prvMgr.getDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_OPEN_AND_CLOSE_DB));

        cacheEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrivacySettingsManager prvMgr = (PrivacySettingsManager)context.getSystemService("privacy");
                if (prvMgr != null) {
                    prvMgr.setDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_USE_CACHE, isChecked);
                    outputText.setText("Cache state updated");
                } else {
                    outputText.setText("Service was null");
                }
            }
        });

        notificationsEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrivacySettingsManager prvMgr = (PrivacySettingsManager)context.getSystemService("privacy");
                if (prvMgr != null) {
                    prvMgr.setDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_SEND_NOTIFICATIONS, isChecked);
                    outputText.setText("Notifications state updated");
                } else {
                    outputText.setText("Service was null");
                }
            }
        });

        databaseOpenCloseEnabled.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrivacySettingsManager prvMgr = (PrivacySettingsManager)context.getSystemService("privacy");
                if (prvMgr != null) {
                    prvMgr.setDebugFlagBool(PrivacySettingsManagerService.DEBUG_FLAG_OPEN_AND_CLOSE_DB, isChecked);
                    outputText.setText("Database status updated");
                } else {
                    outputText.setText("Service was null");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_pdroid_tester, menu);
        return true;
    }
}
