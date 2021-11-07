package com.skarapedulbuk.mysimplenotes.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsStorage {
    public static final String ARG_BASE_CHECKBOX = "ARG_BASE_CHECKBOX";
    public static final String ARG_ADDITIONAL_CHECKBOX = "ARG_ADDITIONAL_CHECKBOX";

    private final SharedPreferences sharedPreferences;

    public SettingsStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }

    public Bundle getSettings() {

        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_BASE_CHECKBOX, sharedPreferences.getBoolean(ARG_BASE_CHECKBOX, false));
        bundle.putBoolean(ARG_ADDITIONAL_CHECKBOX, sharedPreferences.getBoolean(ARG_ADDITIONAL_CHECKBOX, false));

        return bundle;
    }

    public void setSettings(Bundle bundle) {

        Boolean isBaseCheckboxed = bundle.getBoolean(ARG_BASE_CHECKBOX);
        Boolean isAdditionalCheckboxed = bundle.getBoolean(ARG_ADDITIONAL_CHECKBOX);

        sharedPreferences.edit()
                .putBoolean(ARG_BASE_CHECKBOX, isBaseCheckboxed)
                .putBoolean(ARG_ADDITIONAL_CHECKBOX, isAdditionalCheckboxed)
                .apply();
    }
}
