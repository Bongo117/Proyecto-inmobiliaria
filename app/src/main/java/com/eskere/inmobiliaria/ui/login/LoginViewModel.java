package com.eskere.inmobiliaria.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.eskere.inmobiliaria.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> tokenMutable;
    private MutableLiveData<String> errorMutable;

    public LiveData<String> getTokenMutable() {
        if (tokenMutable == null) {
            tokenMutable = new MutableLiveData<>();
        }
        return tokenMutable;
    }

    public LiveData<String> getErrorMutable() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }


    public void autenticar(String usuario, String clave) {

        if (usuario.isEmpty() || clave.isEmpty()) {
            getErrorMutable();
            errorMutable.setValue("Por favor, complete todos los campos");
            return;
        }

        Call<String> call = ApiClient.getApiInmobiliaria().login(usuario, clave);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getTokenMutable();
                    tokenMutable.setValue(response.body());
                } else {
                    getErrorMutable();
                    errorMutable.setValue("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getErrorMutable();
                errorMutable.setValue("Error de red: " + t.getMessage());
            }
        });
    }
}