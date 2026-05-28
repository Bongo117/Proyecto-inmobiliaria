package com.eskere.inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Contrato;

public class ContratoDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> contratoMutable;

    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContratoMutable() {
        if (contratoMutable == null) {
            contratoMutable = new MutableLiveData<>();
        }
        return contratoMutable;
    }

    public void recuperarDatos(Bundle bundle) {
        if (bundle != null) {
            Contrato contrato = (Contrato) bundle.getSerializable("contrato");
            if (contrato != null) {
                contratoMutable.setValue(contrato);
            }
        }
    }
}
