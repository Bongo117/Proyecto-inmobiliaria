package com.eskere.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.eskere.inmobiliaria.databinding.FragmentPerfilBinding;
import com.eskere.inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel perfilViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        perfilViewModel.getPropietario().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {
                binding.etId.setText(String.valueOf(propietario.getIdPropietario()));
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());
                
                binding.tvNombreTop.setText(propietario.getNombre());
                binding.tvEmailTop.setText(propietario.getEmail());
            }
        });

        perfilViewModel.getEdicion().observe(getViewLifecycleOwner(), edicion -> {
            binding.etDni.setEnabled(edicion);
            binding.etNombre.setEnabled(edicion);
            binding.etApellido.setEnabled(edicion);
            binding.etEmail.setEnabled(edicion);
            binding.etTelefono.setEnabled(edicion);

            float alpha = edicion ? 1.0f : 0.5f;
            binding.etDni.setAlpha(alpha);
            binding.etNombre.setAlpha(alpha);
            binding.etApellido.setAlpha(alpha);
            binding.etEmail.setAlpha(alpha);
            binding.etTelefono.setAlpha(alpha);
            binding.etId.setAlpha(0.5f);

            if (edicion) {
                binding.btnEditarGuardar.setText("Guardar perfil");
            } else {
                binding.btnEditarGuardar.setText("Editar perfil");
            }
        });

        binding.btnCambiarClave.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(v).navigate(com.eskere.inmobiliaria.R.id.action_nav_perfil_to_nav_cambio_clave);
        });

        binding.btnEditarGuardar.setOnClickListener(v -> {
            Boolean edicion = perfilViewModel.getEdicion().getValue();
            if (edicion != null && edicion) {
                Propietario p = new Propietario(
                        Integer.parseInt(binding.etId.getText().toString()),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString(),
                        null
                );
                perfilViewModel.guardarPerfil(p);
            } else {
                perfilViewModel.toggleEdicion();
            }
        });

        perfilViewModel.obtenerPerfil();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}