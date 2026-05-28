package com.eskere.inmobiliaria.ui.inmueblesdetalle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.eskere.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;

public class DetalleInmuebleFragment extends Fragment {

    private FragmentDetalleInmuebleBinding binding;
    private DetalleInmuebleViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        viewModel.getInmuebleMutable().observe(getViewLifecycleOwner(), inmueble -> {
            binding.tvCodigoDetalle.setText(inmueble.getCodigoFormateado());
            binding.tvDireccionDetalle.setText(inmueble.getDireccionFormateada());
            binding.tvUsoDetalle.setText(inmueble.getUsoFormateado());
            binding.tvTipoDetalle.setText(inmueble.getTipoFormateado());
            binding.tvAmbientesDetalle.setText(inmueble.getAmbientesFormateado());
            binding.tvPrecioDetalle.setText(inmueble.getPrecioFormateado());
            binding.cbDisponible.setChecked(inmueble.isDisponible());

            // La vista le pide la URL lista al modelo
            Glide.with(requireContext())
                    .load(inmueble.getUrlImagenOriginal())
                    .into(binding.ivFotoDetalle);
        });

        viewModel.getMensajeMutable().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });

        binding.cbDisponible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean estaTildado = binding.cbDisponible.isChecked();
                viewModel.actualizarEstado(estaTildado);
            }
        });

        viewModel.recuperarDatos(getArguments());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}