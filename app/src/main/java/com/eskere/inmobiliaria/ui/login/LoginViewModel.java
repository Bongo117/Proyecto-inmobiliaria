package com.eskere.inmobiliaria.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> tokenMutable = new MutableLiveData<>();
    private MutableLiveData<String> errorMutable = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getTokenMutable() {
        return tokenMutable;
    }

    public LiveData<String> getErrorMutable() {
        return errorMutable;
    }

    public void autenticar(String usuario, String clave) {

        if (usuario.isEmpty() || clave.isEmpty()) {
            errorMutable.setValue("Por favor, complete todos los campos");
            return;
        }

        Call<String> call = ApiClient.getApiInmobiliaria().login(usuario, clave);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    // ACA SE GUARDA EL TOKEN
                    ApiClient.crearToken(getApplication(), response.body());

                    // Recién después de guardarlo le avisamos a la vista
                    tokenMutable.setValue(response.body());

                } else {
                    errorMutable.setValue("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                errorMutable.setValue("Error de red: " + t.getMessage());
            }
        });
    }
}