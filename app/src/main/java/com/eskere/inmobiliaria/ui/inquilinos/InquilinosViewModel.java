package com.eskere.inmobiliaria.ui.inquilinos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmueblesAlquiladosMutable;
    private MutableLiveData<String> errorMutable;

    private List<Inmueble> listaCompleta = new ArrayList<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesAlquiladosMutable() {
        if (inmueblesAlquiladosMutable == null) {
            inmueblesAlquiladosMutable = new MutableLiveData<>();
        }
        return inmueblesAlquiladosMutable;
    }

    public LiveData<String> getErrorMutable() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void cargarInmueblesAlquilados() {
        String token = ApiClient.usarToken(getApplication());
        ApiInmobiliaria api = ApiClient.getApiInmobiliaria();

        Call<List<Inmueble>> call = api.obtenerInmueblesAlquilados(token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCompleta = response.body(); // Guardamos la original
                    inmueblesAlquiladosMutable.setValue(listaCompleta);
                } else {
                    errorMutable.setValue("No se encontraron inmuebles alquilados.");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                errorMutable.setValue("Fallo de red: " + t.getMessage());
            }
        });
    }
    public void buscar(String texto) {
        if (texto.isEmpty()) {
            inmueblesAlquiladosMutable.setValue(listaCompleta);
            return;
        }

        String busqueda = texto.toLowerCase();
        List<Inmueble> filtrados = new ArrayList<>();
        for (Inmueble i : listaCompleta) {
            // metodo formateado para evitar nulos
            if (i.getDireccionFormateada().toLowerCase().contains(busqueda)) {
                filtrados.add(i);
            }
        }
        inmueblesAlquiladosMutable.setValue(filtrados);
    }}