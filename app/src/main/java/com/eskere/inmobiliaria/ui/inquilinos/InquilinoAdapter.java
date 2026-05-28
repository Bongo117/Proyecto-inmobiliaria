package com.eskere.inmobiliaria.ui.inquilinos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eskere.inmobiliaria.R;
import com.eskere.inmobiliaria.databinding.ItemInquilinoBinding;
import com.eskere.inmobiliaria.modelo.Inmueble;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.ViewHolder> {

    private List<Inmueble> listaInmuebles;
    private Context context;

    public InquilinoAdapter(List<Inmueble> listaInmuebles, Context context) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInquilinoBinding binding = ItemInquilinoBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble inmueble = listaInmuebles.get(position);

        holder.binding.tvDireccionInquilino.setText(inmueble.getDireccion());

        Glide.with(context)
                .load(inmueble.getUrlImagenOriginal())
                .into(holder.binding.ivFotoInquilino);

        holder.binding.btnVerInquilino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmueble);
                Navigation.findNavController(v).navigate(R.id.action_nav_inquilinos_to_nav_detalle_inquilino, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemInquilinoBinding binding;
        public ViewHolder(@NonNull ItemInquilinoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}