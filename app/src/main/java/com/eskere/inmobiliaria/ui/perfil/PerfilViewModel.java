package com.eskere.inmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Propietario;
import com.eskere.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> mPropietario;
    private MutableLiveData<Boolean> mEdicion;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Propietario> getPropietario() {
        if (mPropietario == null) {
            mPropietario = new MutableLiveData<>();
        }
        return mPropietario;
    }

    public LiveData<Boolean> getEdicion() {
        if (mEdicion == null) {
            mEdicion = new MutableLiveData<>();
            mEdicion.setValue(false);
        }
        return mEdicion;
    }

    public void obtenerPerfil() {
        String token = ApiClient.usarToken(context);
        if (token != null) {
            Call<Propietario> call = ApiClient.getApiInmobiliaria().getPropietario(token);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        mPropietario.setValue(response.body());
                    } else {
                        Toast.makeText(context, "Error al obtener perfil", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void toggleEdicion() {
        if (mEdicion.getValue() != null) {
            mEdicion.setValue(!mEdicion.getValue());
        }
    }

    public void guardarPerfil(Propietario propietario) {
        String token = ApiClient.usarToken(context);
        if (token != null) {
            Call<Propietario> call = ApiClient.getApiInmobiliaria().actualizarPerfil(token, propietario);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            mPropietario.setValue(response.body());
                        } else {
                            obtenerPerfil(); 
                        }
                        mEdicion.setValue(false);
                        Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(context, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}