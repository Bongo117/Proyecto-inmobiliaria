package com.eskere.inmobiliaria.ui.contratos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.databinding.ItemContratoBinding;
import com.eskere.inmobiliaria.modelo.Contrato;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {

    private List<Contrato> listaContratos;
    private Context context;

    public ContratoAdapter(List<Contrato> listaContratos, Context context) {
        this.listaContratos = listaContratos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContratoBinding binding = ItemContratoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contrato contrato = listaContratos.get(position);

        holder.binding.tvDireccionContrato.setText(contrato.getInmueble() != null ? contrato.getInmueble().getDireccion() : "Sin dirección");
        holder.binding.tvInquilinoContrato.setText(contrato.getInquilino() != null ? contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido() : "Sin inquilino");
        holder.binding.tvMontoContrato.setText("$ " + contrato.getMonto());
        holder.binding.tvFechaInicioContrato.setText("Inicio: " + contrato.getFechaInicio());

        holder.binding.btnVerContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contrato);
                Navigation.findNavController(v).navigate(R.id.action_nav_contratos_to_nav_detalle_contrato, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContratos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemContratoBinding binding;

        public ViewHolder(@NonNull ItemContratoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
