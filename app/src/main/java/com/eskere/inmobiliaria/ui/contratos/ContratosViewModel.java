package com.eskere.inmobiliaria.ui.contratos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Contrato;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contrato>> contratosMutable = new MutableLiveData<>();
    private MutableLiveData<String> errorMutable = new MutableLiveData<>();

    private List<Contrato> listaCompleta = new ArrayList<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Contrato>> getContratosMutable() {
        return contratosMutable;
    }

    public LiveData<String> getErrorMutable() {
        return errorMutable;
    }

    public void obtenerContratos() {
        String token = ApiClient.usarToken(getApplication());
        ApiInmobiliaria api = ApiClient.getApiInmobiliaria();
        Call<List<Contrato>> call = api.getContratos(token);

        call.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCompleta = response.body(); // Guardamos la original
                    contratosMutable.setValue(listaCompleta);
                } else {
                    errorMutable.setValue("Error al obtener contratos.");
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                errorMutable.setValue("Fallo de red: " + t.getMessage());
            }
        });
    }

    public void buscar(String texto) {
        if (texto.isEmpty()) {
            contratosMutable.setValue(listaCompleta);
            return;
        }

        List<Contrato> filtrados = new ArrayList<>();
        for (Contrato c : listaCompleta) {
            if (c.getDireccionAMostrar().toLowerCase().contains(texto.toLowerCase()) || 
                c.getNombreInquilinoCompleto().toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(c);
            }
        }
        contratosMutable.setValue(filtrados);
    }
}