package com.eskere.inmobiliaria.ui.cambioclave;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambioClaveViewModel extends AndroidViewModel {

    private MutableLiveData<String> mensajeExito;
    private MutableLiveData<String> mensajeError;

    public CambioClaveViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMensajeExito() {
        if (mensajeExito == null) {
            mensajeExito = new MutableLiveData<>();
        }
        return mensajeExito;
    }

    public LiveData<String> getMensajeError() {
        if (mensajeError == null) {
            mensajeError = new MutableLiveData<>();
        }
        return mensajeError;
    }

    public void cambiarClave(String currentPassword, String newPassword) {
        if (mensajeError == null) mensajeError = new MutableLiveData<>();
        if (mensajeExito == null) mensajeExito = new MutableLiveData<>();

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            mensajeError.setValue("Ambos campos son obligatorios");
            return;
        }

        String token = ApiClient.usarToken(getApplication());
        if (token == null) {
            mensajeError.setValue("No se encontró el token de sesión");
            return;
        }

        Call<Void> call = ApiClient.getApiInmobiliaria().cambiarClave(token, currentPassword, newPassword);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    mensajeExito.setValue("Contraseña actualizada exitosamente");
                } else {
                    mensajeError.setValue("La contraseña actual es incorrecta");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mensajeError.setValue("Fallo de red: " + t.getMessage());
            }
        });
    }
}
