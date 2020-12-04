package sk.itsovy.android.hereiam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import sk.itsovy.android.hereiam.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textViewUsers.setText("NO USERS");
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        binding.buttonRefresh.setOnClickListener(view -> refresh());
    }

    private void login() {
        String login = "Filip";
        Executor e = Executors.newSingleThreadExecutor();
        e.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Call<Void> call = RestDao.API.sendLogin(login);
                    call.execute();
                } catch (IOException exception) {
                    Log.e(getClass().getName(), "Unable to send login.", exception);
                }
            }
        });

    }

    private void refresh() {
    }
}