package com.eskere.inmobiliaria.ui.inmuebles;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmueblesMutable = new MutableLiveData<>();

    private MutableLiveData<String> errorMutable = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesMutable() {
        return inmueblesMutable;
    }

    public LiveData<String> getErrorMutable() {
        return errorMutable;
    }

    public void obtenerInmuebles() {

        String token = ApiClient.usarToken(getApplication());

        ApiInmobiliaria api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> call = api.getInmuebles(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    inmueblesMutable.setValue(response.body());
                } else {
                    errorMutable.setValue("Error al obtener inmuebles.");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                errorMutable.setValue("Fallo de red: " + t.getMessage());
            }
        });
    }
}