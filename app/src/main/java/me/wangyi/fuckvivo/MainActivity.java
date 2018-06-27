package me.wangyi.fuckvivo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.et_password);
        startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String password = PreferencesUtils.getString(getApplicationContext(),
                PreferencesUtils.KEY_PASSWORD, "");
        if (!TextUtils.isEmpty(password)) {
            mEditText.setText(password);
            mEditText.setSelection(password.length());
        }
    }

    public void savePassword(View view) {
        String password = mEditText.getText().toString().trim();
        PreferencesUtils.saveString(this, PreferencesUtils.KEY_PASSWORD, password);
        Toast.makeText(this, R.string.toast_save_success, Toast.LENGTH_SHORT).show();
    }

    public void openSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
