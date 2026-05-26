package com.eskere.inmobiliaria.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LogoutViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> logoutExitosoMutable;

    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getLogoutExitosoMutable() {
        if (logoutExitosoMutable == null) {
            logoutExitosoMutable = new MutableLiveData<>();
        }
        return logoutExitosoMutable;
    }

    public void cerrarSesion() {

        SharedPreferences sp = getApplication().getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();

        logoutExitosoMutable.setValue(true);
    }
}