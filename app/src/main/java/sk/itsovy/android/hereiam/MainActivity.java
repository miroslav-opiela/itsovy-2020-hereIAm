package sk.itsovy.android.hereiam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import sk.itsovy.android.hereiam.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private UsersViewModel viewModel;

    private Handler periodicHandler = new Handler();
    // delay 20 sekund
    private int delay = 20 * 1000;
    private Runnable periodicRefresh = new Runnable() {
        @Override
        public void run() {
            viewModel.refresh();
            periodicHandler.postDelayed(periodicRefresh, delay);
        }
    };


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

        ViewModelProvider provider = new ViewModelProvider(this);
        viewModel = provider.get(UsersViewModel.class);
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                binding.textViewUsers.setText(users.toString());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        periodicHandler.postDelayed(periodicRefresh, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        periodicHandler.removeCallbacks(periodicRefresh);
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
        viewModel.refresh();
    }
}