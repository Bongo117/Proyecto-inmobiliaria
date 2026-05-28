package com.eskere.inmobiliaria.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eskere.inmobiliaria.databinding.ItemPagoBinding;
import com.eskere.inmobiliaria.modelo.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {

    private List<Pago> listaPagos;
    private Context context;

    public PagosAdapter(List<Pago> listaPagos, Context context) {
        this.listaPagos = listaPagos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPagoBinding binding = ItemPagoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pago = listaPagos.get(position);

        holder.binding.tvCodigoPago.setText("Pago #" + pago.getIdPago());
        holder.binding.tvNroPago.setText("Nro: " + pago.getNroPago());
        holder.binding.tvIdContratoPago.setText("Contrato: " + pago.getIdAlquiler());
        holder.binding.tvImportePago.setText("$ " + pago.getImporte());
        holder.binding.tvFechaPago.setText("Fecha: " + pago.getFecha());
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemPagoBinding binding;

        public ViewHolder(@NonNull ItemPagoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
