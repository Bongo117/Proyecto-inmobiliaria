package com.eskere.inmobiliaria.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eskere.inmobiliaria.databinding.FragmentInicioBinding;

import org.maplibre.android.MapLibre;
import org.maplibre.android.WellKnownTileServer;

public class InicioFragment extends Fragment {

    private FragmentInicioBinding binding;
    private InicioFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // iniciamos maplibre antes de que se infle la vista
        MapLibre.getInstance(requireContext(), "7EqwWSH3O7mskdKhF8h3", WellKnownTileServer.MapTiler);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(InicioFragmentViewModel.class);

        viewModel.getMapaMutable().observe(getViewLifecycleOwner(), new Observer<InicioFragmentViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioFragmentViewModel.MapaActual mapaActual) {
                binding.mapa.getMapAsync(mapaActual);
            }
        });

        viewModel.cargarMapa();

        return binding.getRoot();
    }
    // Delegamos los métodos a la vista del mapa para liberar recursos
    @Override
    public void onStart() {
        super.onStart();
        if (binding != null) binding.mapa.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) binding.mapa.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (binding != null) binding.mapa.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (binding != null) binding.mapa.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (binding != null) binding.mapa.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.mapa.onDestroy();
            binding = null;
        }
    }
}