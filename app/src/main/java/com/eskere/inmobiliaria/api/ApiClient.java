package com.eskere.inmobiliaria.api;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static final String URL_BASE = "https://capacitacion.alwaysdata.net/";

    private static ApiInmobiliaria apiInmobiliaria;
    public static ApiInmobiliaria getApiInmobiliaria() {
        if (apiInmobiliaria == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            apiInmobiliaria = retrofit.create(ApiInmobiliaria.class);
        }
        return apiInmobiliaria;
    }
}