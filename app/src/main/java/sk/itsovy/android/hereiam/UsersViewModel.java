package sk.itsovy.android.hereiam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<List<User>> users;

    // volanie ktore vrati live data
    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
            refresh();
        }
        return users;
    }

    // vynuti aktualizaciu live data -> volanie REST API GET
    public void refresh() {
        Call<List<User>> call = RestDao.API.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> list = response.body();
                    // aktualizuje live data
                    users.postValue(list);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}
