package com.eskere.inmobiliaria.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Contrato;
import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.modelo.Inquilino;
import com.eskere.inmobiliaria.request.ApiClient;
import com.eskere.inmobiliaria.request.ApiInmobiliaria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> inquilinoMutable;
    private MutableLiveData<String> errorMutable;

    public DetalleInquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilinoMutable() {
        if (inquilinoMutable == null) {
            inquilinoMutable = new MutableLiveData<>();
        }
        return inquilinoMutable;
    }

    public LiveData<String> getErrorMutable() {
        if (errorMutable == null) {
            errorMutable = new MutableLiveData<>();
        }
        return errorMutable;
    }

    public void cargarInquilinoDesdeBundle(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            if (inmueble != null) {
                String token = ApiClient.usarToken(getApplication());
                ApiInmobiliaria api = ApiClient.getApiInmobiliaria();

                Call<Contrato> call = api.obtenerContratoPorInmueble(token, inmueble.getIdInmueble());
                call.enqueue(new Callback<Contrato>() {
                    @Override
                    public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            inquilinoMutable.setValue(response.body().getInquilino());
                        } else {
                            errorMutable.setValue("No se pudo obtener el inquilino");
                        }
                    }

                    @Override
                    public void onFailure(Call<Contrato> call, Throwable t) {
                        errorMutable.setValue("Error de red: " + t.getMessage());
                    }
                });
            }
        }
    }
}