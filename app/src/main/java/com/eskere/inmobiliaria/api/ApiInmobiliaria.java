package com.eskere.inmobiliaria.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInmobiliaria {

    @FormUrlEncoded
    @POST("api/Propietarios/login")
    Call<String> login(@Field("Usuario") String usuario, @Field("Clave") String clave);
}