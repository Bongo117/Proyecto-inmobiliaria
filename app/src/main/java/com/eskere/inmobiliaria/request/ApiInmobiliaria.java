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

public interface ApiInmobiliaria {

    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);
    @GET("api/Propietarios")
    Call<Propietario> getPropietario(@Header("Authorization") String token);
    @GET("api/Inmuebles")
    Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);
}