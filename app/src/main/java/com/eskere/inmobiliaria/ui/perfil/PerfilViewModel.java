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

    private MutableLiveData<Boolean> estadoEdicionMutable;
    private MutableLiveData<String> textoBotonMutable;
    private MutableLiveData<Float> alphaCamposMutable;

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

    public LiveData<Boolean> getEstadoEdicion() {
        if (estadoEdicionMutable == null) {
            estadoEdicionMutable = new MutableLiveData<>();
            estadoEdicionMutable.setValue(false);
        }
        return estadoEdicionMutable;
    }

    public LiveData<String> getTextoBoton() {
        if (textoBotonMutable == null) {
            textoBotonMutable = new MutableLiveData<>();
            textoBotonMutable.setValue("Editar");
        }
        return textoBotonMutable;
    }

    public LiveData<Float> getAlphaCampos() {
        if (alphaCamposMutable == null) {
            alphaCamposMutable = new MutableLiveData<>();
            alphaCamposMutable.setValue(0.5f);
        }
        return alphaCamposMutable;
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

    public void accionBotonEditarGuardar(String id, String nombre, String apellido, String dni, String telefono, String email) {
        Boolean enEdicion = estadoEdicionMutable.getValue();

        if (enEdicion != null && enEdicion) {

            try {
                Propietario p = new Propietario(
                        Integer.parseInt(id),
                        nombre,
                        apellido,
                        dni,
                        telefono,
                        email,
                        null
                );
                guardarPerfil(p);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Error con el ID del usuario.", Toast.LENGTH_SHORT).show();
            }
        } else {
            activarModoEdicion(true);
        }
    }
    private void activarModoEdicion(boolean activar) {
        estadoEdicionMutable.setValue(activar);
        textoBotonMutable.setValue(activar ? "Guardar" : "Editar");
        alphaCamposMutable.setValue(activar ? 1.0f : 0.5f);
    }

    private void guardarPerfil(Propietario propietario) {
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

                        activarModoEdicion(false);
                        Toast.makeText(context, "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show();
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