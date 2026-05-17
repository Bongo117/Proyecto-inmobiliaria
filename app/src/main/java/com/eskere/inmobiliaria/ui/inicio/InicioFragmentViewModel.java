package com.eskere.inmobiliaria.ui.inicio;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.maplibre.android.annotations.MarkerOptions;
import org.maplibre.android.camera.CameraPosition;
import org.maplibre.android.geometry.LatLng;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.maps.OnMapReadyCallback;
import org.maplibre.android.maps.Style;

public class InicioFragmentViewModel extends ViewModel {


    private final MutableLiveData<MapaActual> mapaMutable = new MutableLiveData<>();

    public LiveData<MapaActual> getMapaMutable() {
        return mapaMutable;
    }

    public void cargarMapa() {
        mapaMutable.setValue(new MapaActual());
    }

    // Clase interna que implementa el Callback
    public static class MapaActual implements OnMapReadyCallback {
        @Override
        public void onMapReady(@NonNull MapLibreMap mapLibreMap) {

            String styleUrl = "https://api.maptiler.com/maps/streets-v4/style.json?key=7EqwWSH3O7mskdKhF8h3";

            mapLibreMap.setStyle(styleUrl, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    LatLng inmoLocation = new LatLng(-33.183730028155274, -66.31247810790867);

                    mapLibreMap.addMarker(new MarkerOptions()  //esto hay que revisarlo con versiones mas nuevas de maplibre mas adelante
                            .position(inmoLocation)
                            .title("Inmobiliaria La Punta"));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(inmoLocation)
                            .zoom(16.0)
                            .build();

                    mapLibreMap.setCameraPosition(cameraPosition);
                }
            });
        }
    }
}