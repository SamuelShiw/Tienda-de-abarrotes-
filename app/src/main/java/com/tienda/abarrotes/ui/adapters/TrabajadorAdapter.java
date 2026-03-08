package com.tienda.abarrotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class TrabajadorAdapter extends RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder> {

    public interface OnTrabajadorActionListener {
        void onEditar(Trabajador trabajador);
        void onDesactivar(Trabajador trabajador);
        void onVerQr(Trabajador trabajador);
    }

    private final List<Trabajador> lista = new ArrayList<>();
    private final OnTrabajadorActionListener listener;

    public TrabajadorAdapter(OnTrabajadorActionListener listener) {
        this.listener = listener;
    }

    public void setLista(List<Trabajador> trabajadores) {
        lista.clear();
        if (trabajadores != null) {
            lista.addAll(trabajadores);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrabajadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trabajador, parent, false);
        return new TrabajadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrabajadorViewHolder holder, int position) {
        Trabajador trabajador = lista.get(position);

        holder.tvNombre.setText(trabajador.getNombreCompleto().trim());
        holder.tvDni.setText("DNI: " + trabajador.getDni());
        holder.tvUsuario.setText("Usuario: " + trabajador.getUsuario());
        holder.tvRol.setText("Rol: " + trabajador.getRol());
        holder.tvTelefono.setText("Teléfono: " + trabajador.getTelefono());
        holder.tvEstado.setText(trabajador.getEstado());

        if (AppConstants.ESTADO_ACTIVO.equalsIgnoreCase(trabajador.getEstado())) {
            holder.tvEstado.setBackgroundResource(R.drawable.bg_chip_status_active);
        } else {
            holder.tvEstado.setBackgroundResource(R.drawable.bg_chip_status_inactive);
        }

        holder.btnEditar.setOnClickListener(v -> listener.onEditar(trabajador));
        holder.btnDesactivar.setOnClickListener(v -> listener.onDesactivar(trabajador));
        holder.btnQr.setOnClickListener(v -> listener.onVerQr(trabajador));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class TrabajadorViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvDni;
        TextView tvUsuario;
        TextView tvRol;
        TextView tvTelefono;
        TextView tvEstado;
        ImageButton btnEditar;
        ImageButton btnDesactivar;
        ImageButton btnQr;

        public TrabajadorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItemNombreTrabajador);
            tvDni = itemView.findViewById(R.id.tvItemDniTrabajador);
            tvUsuario = itemView.findViewById(R.id.tvItemUsuarioTrabajador);
            tvRol = itemView.findViewById(R.id.tvItemRolTrabajador);
            tvTelefono = itemView.findViewById(R.id.tvItemTelefonoTrabajador);
            tvEstado = itemView.findViewById(R.id.tvItemEstadoTrabajador);
            btnEditar = itemView.findViewById(R.id.btnEditarTrabajador);
            btnDesactivar = itemView.findViewById(R.id.btnDesactivarTrabajador);
            btnQr = itemView.findViewById(R.id.btnQrTrabajador);
        }
    }
}