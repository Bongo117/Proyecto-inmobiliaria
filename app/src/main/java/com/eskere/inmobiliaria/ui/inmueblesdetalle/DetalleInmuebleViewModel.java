package com.eskere.inmobiliaria.ui.inmueblesdetalle;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> inmuebleMutable;
    private MutableLiveData<String> mensajeMutable;

    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getInmuebleMutable() {
        if (inmuebleMutable == null) {
            inmuebleMutable = new MutableLiveData<>();
        }
        return inmuebleMutable;
    }

    public LiveData<String> getMensajeMutable() {
        if (mensajeMutable == null) {
            mensajeMutable = new MutableLiveData<>();
        }
        return mensajeMutable;
    }

    public void recuperarDatos(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) {
                inmuebleMutable.setValue(inmueble);
            }
        }
    }

    public void actualizarEstado(boolean nuevoEstado) {

        Inmueble inmuebleActual = inmuebleMutable.getValue();

        if (inmuebleActual != null) {

            inmuebleActual.setDisponible(nuevoEstado);

            String token = ApiClient.usarToken(getApplication());
            ApiInmobiliaria api = ApiClient.getApiInmobiliaria();

            Call<Inmueble> call = api.actualizarInmueble(token, inmuebleActual);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        mensajeMutable.setValue("¡Disponibilidad actualizada en la web!");
                    } else {
                        mensajeMutable.setValue("Error al actualizar la disponibilidad.");
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    mensajeMutable.setValue("Fallo de red: " + t.getMessage());
                }
            });
        }
    }
}