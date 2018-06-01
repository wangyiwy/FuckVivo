package me.wangyi.fuckvivo;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
    private static final String CONFIG_NAME = "system_config";

    public static final String KEY_PASSWORD = "key_password";

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }
}
