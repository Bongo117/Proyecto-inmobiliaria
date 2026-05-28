package com.eskere.inmobiliaria.ui.pagos;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Pago;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> pagosMutable = new MutableLiveData<>();

    private MutableLiveData<String> errorMutable = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getPagosMutable() {
        return pagosMutable;
    }

    public LiveData<String> getErrorMutable() {
        return errorMutable;
    }

    public void recuperarDatos(Bundle bundle) {
        if (bundle != null) {
            int idContrato = bundle.getInt("idContrato", -1);
            if (idContrato != -1) {
                obtenerPagos(idContrato);
            }
        }
    }

    private void obtenerPagos(int idContrato) {

        String token = ApiClient.usarToken(getApplication());

        ApiInmobiliaria api = ApiClient.getApiInmobiliaria();
        Call<List<Pago>> call = api.obtenerPagosPorContrato(token, idContrato);

        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pagosMutable.setValue(response.body());
                } else {
                    errorMutable.setValue("Error al obtener pagos.");
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                errorMutable.setValue("Fallo de red: " + t.getMessage());
            }
        });
    }
}
