package com.skarapedulbuk.mysimplenotes.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.skarapedulbuk.mysimplenotes.R;
import com.skarapedulbuk.mysimplenotes.ui.options.SettingsFragment;

public class MainActivity extends AppCompatActivity implements Drawer {
    DrawerLayout drawerLayout;
    GoogleSignInClient client;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            if (task.isSuccessful()) {
                GoogleSignInAccount account = task.getResult();
                String text = getString(R.string.success_auth) + account.getEmail();
                Snackbar.make(findViewById(R.id.main_container), text, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.exit, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                signOut();
                            }
                        })
                        .show();
            }
        }
    });

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if ((account) != null) {
            String text = getString(R.string.earlier_auth) + account.getEmail();
            Snackbar.make(findViewById(R.id.main_container), text, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.exit, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signOut();
                        }
                    })
                    .show();
        }

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, googleSignInOptions);

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
            if (item.getItemId() == R.id.action_auth) {
           //     Toast.makeText(this, "запуск auth в диалоге", Toast.LENGTH_SHORT).show();
                signInLauncher.launch(client.getSignInIntent());
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

    private void signOut() {
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), getString(R.string.account_exit), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
