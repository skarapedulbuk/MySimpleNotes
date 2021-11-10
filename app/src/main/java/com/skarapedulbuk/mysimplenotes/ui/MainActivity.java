package com.skarapedulbuk.mysimplenotes.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

public class MainActivity extends AppCompatActivity implements Drawer {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_settings) {
                Toast.makeText(this, "запуск SettinsFragment в основном контейнере", Toast.LENGTH_SHORT).show();
                showSettingsNotChild();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            if (item.getItemId() == R.id.action_about) {
                Toast.makeText(this, "запуск MainFragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, new MainFragment())
                        .addToBackStack("About")
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    public void setToolbar(MaterialToolbar toolbar) { // на обычный Toolbar ругается, работает только с явным указанием MaterialToolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0); // непонятно зачем строковые ресурсы
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void showSettingsNotChild() {
       /* getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, new SettingsFragment())
                .addToBackStack("Settings")
                .commit();*/
        if (getSupportFragmentManager().findFragmentByTag("Settings") == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_container, new SettingsFragment(), "Settings")
                    .commit();
            Toast.makeText(this, "Показываю настройки", Toast.LENGTH_SHORT).show();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentByTag("Settings"))
                    .commit();
            Toast.makeText(this, "Скрываю настройки", Toast.LENGTH_SHORT).show();
        }
    }
}
