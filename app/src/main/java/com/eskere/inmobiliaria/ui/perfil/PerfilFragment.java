package com.eskere.inmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.databinding.FragmentPerfilBinding;

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
            }
        });

        perfilViewModel.getEstadoEdicion().observe(getViewLifecycleOwner(), habilitado -> {
            binding.etDni.setEnabled(habilitado);
            binding.etNombre.setEnabled(habilitado);
            binding.etApellido.setEnabled(habilitado);
            binding.etEmail.setEnabled(habilitado);
            binding.etTelefono.setEnabled(habilitado);
        });

        perfilViewModel.getAlphaCampos().observe(getViewLifecycleOwner(), alpha -> {
            binding.etDni.setAlpha(alpha);
            binding.etNombre.setAlpha(alpha);
            binding.etApellido.setAlpha(alpha);
            binding.etEmail.setAlpha(alpha);
            binding.etTelefono.setAlpha(alpha);
            binding.etId.setAlpha(0.5f);
        });

        perfilViewModel.getTextoBoton().observe(getViewLifecycleOwner(), texto -> {
            binding.btnEditarGuardar.setText(texto);
        });

        binding.btnCambiarClave.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_nav_perfil_to_nav_cambio_clave);
        });


        binding.btnEditarGuardar.setOnClickListener(v -> {

            String id = binding.etId.getText().toString();
            String nombre = binding.etNombre.getText().toString();
            String apellido = binding.etApellido.getText().toString();
            String dni = binding.etDni.getText().toString();
            String telefono = binding.etTelefono.getText().toString();
            String email = binding.etEmail.getText().toString();

            perfilViewModel.accionBotonEditarGuardar(id, nombre, apellido, dni, telefono, email);
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