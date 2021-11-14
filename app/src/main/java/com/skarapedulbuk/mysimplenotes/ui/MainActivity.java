package com.skarapedulbuk.mysimplenotes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

public class MainActivity extends AppCompatActivity implements Drawer {
    DrawerLayout drawerLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setBackgroundColor(R.color.purple_700);
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.action_settings) {
                Toast.makeText(this, "запуск SettinsFragment в диалоге", Toast.LENGTH_SHORT).show();
                SettingsFragment.newInstance()
                        .show(getSupportFragmentManager(), SettingsFragment.TAG);
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Нажата системная кнопка назад", Toast.LENGTH_SHORT).show();
        showExitDialog(this);
    }

    private void showExitDialog(Context context) {
        new AlertDialog.Builder(this)
                .setTitle("Выход")
                .setMessage("Вы действительно хотите выйти?")
                .setIcon(R.drawable.ic_baseline_anchor_24)
                .setCancelable(false)
                .setPositiveButton("Да", (dialog, which) -> {
                    Toast.makeText(context, "Выход подтвержден", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Нет", (dialog, which) ->
                        Toast.makeText(context, "Отмена", Toast.LENGTH_SHORT).show())
                .show();
    }
}
