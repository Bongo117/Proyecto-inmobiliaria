package com.eskere.inmobiliaria.ui.inmueblesagregar;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesAgregarViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri;
    private MutableLiveData<String> mensajeMutable;

    public InmueblesAgregarViewModel(@NonNull Application application) {
        super(application);
    }

    public void recibirFoto(ActivityResult resultado) {
        if (resultado.getResultCode() == RESULT_OK) {
            Intent data = resultado.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }

    public LiveData<Uri> getmUri() {
        if (mUri == null) {
            mUri = new MutableLiveData<>();
        }
        return mUri;
    }

    public LiveData<String> getMensajeMutable() {
        if (mensajeMutable == null) {
            mensajeMutable = new MutableLiveData<>();
        }
        return mensajeMutable;
    }

    public void cargarInmueble(String direccion, String uso, String tipo,
                               String ambientes, String superficie, String valor) {
        if (mensajeMutable == null) mensajeMutable = new MutableLiveData<>();

        try {
            if (!direccion.isEmpty() && !uso.isEmpty() && !tipo.isEmpty() && !ambientes.isEmpty()
                    && !superficie.isEmpty() && !valor.isEmpty()) {
                Inmueble i = new Inmueble();
                i.setDireccion(direccion);
                i.setUso(uso);
                i.setTipo(tipo);
                i.setAmbientes(Integer.parseInt(ambientes));
                i.setSuperficie(Integer.parseInt(superficie));
                i.setValor(Double.parseDouble(valor));
                byte[] imagen = transformarImagen();
                i.setDisponible(false);
                if (imagen.length == 0) {
                    mensajeMutable.setValue("Debe ingresar imagen");
                    return;
                }
                String inmuebleJson = new Gson().toJson(i);
                RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
                MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
                String token = ApiClient.usarToken(getApplication());
                Call<Inmueble> call = ApiClient.getApiInmobiliaria().cargarInmueble(token, imagenPart, inmuebleBody);
                call.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        if (response.isSuccessful()) {
                            mensajeMutable.setValue("Inmueble guardado correctamente");
                        } else {
                            mensajeMutable.setValue("Error al cargar inmueble");
                            Log.d("ERROR", "codigo: " + response.code());
                            Log.d("ERROR", "mensaje: " + response.message());
                            Log.d("ERROR", "body: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {
                        mensajeMutable.setValue("Fallo de red: " + t.getMessage());
                    }
                });
            } else {
                mensajeMutable.setValue("Debe llenar todos los campos.");
            }
        } catch (NumberFormatException e) {
            mensajeMutable.setValue("Superficie, ambientes y valor deben ser numéricos");
        }
    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();
            if (uri == null) {
                return new byte[]{};
            }
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            mensajeMutable.setValue("Debe ingresar una foto");
            return new byte[]{};
        }
    }
}
