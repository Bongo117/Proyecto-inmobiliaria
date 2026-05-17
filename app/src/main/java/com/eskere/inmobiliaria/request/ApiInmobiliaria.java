package com.eskere.inmobiliaria.request;

import com.eskere.inmobiliaria.modelo.Inmueble;
import com.eskere.inmobiliaria.modelo.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInmobiliaria {

    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);
    @GET("api/Propietarios")
    Call<Propietario> getPropietario(@Header("Authorization") String token);
    @GET("api/Inmuebles")
    Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);
    @PUT("api/Propietarios/actualizar")
    Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @retrofit2.http.Body Propietario propietario);

    @FormUrlEncoded
    @PUT("api/Propietarios/changePassword")
    Call<Void> cambiarClave(@Header("Authorization") String token, @Field("currentPassword") String currentPassword, @Field("newPassword") String newPassword);
}