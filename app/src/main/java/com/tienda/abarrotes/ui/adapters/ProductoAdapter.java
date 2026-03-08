package com.tienda.abarrotes.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    public interface OnProductoActionListener {
        void onDetalle(Producto producto);
        void onEditar(Producto producto);
        void onDesactivar(Producto producto);
    }

    private final List<Producto> lista = new ArrayList<>();
    private final OnProductoActionListener listener;

    public ProductoAdapter(OnProductoActionListener listener) {
        this.listener = listener;
    }

    public void setLista(List<Producto> productos) {
        lista.clear();
        if (productos != null) {
            lista.addAll(productos);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = lista.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvCodigo.setText(producto.getCodigoProducto());
        holder.tvCategoria.setText("Categoría: " + producto.getCategoria());
        holder.tvPrecio.setText(String.format(Locale.getDefault(), "S/ %.2f", producto.getPrecioVenta()));
        holder.tvStocks.setText("Almacén: " + producto.getStockAlmacen() + " | Tienda: " + producto.getStockTienda());
        holder.tvEstado.setText(producto.getEstado());

        if (AppConstants.ESTADO_ACTIVO.equalsIgnoreCase(producto.getEstado())) {
            holder.tvEstado.setBackgroundResource(R.drawable.bg_chip_status_active);
        } else {
            holder.tvEstado.setBackgroundResource(R.drawable.bg_chip_status_inactive);
        }

        holder.btnDetalle.setOnClickListener(v -> listener.onDetalle(producto));
        holder.btnEditar.setOnClickListener(v -> listener.onEditar(producto));
        holder.btnDesactivar.setOnClickListener(v -> listener.onDesactivar(producto));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCodigo, tvCategoria, tvPrecio, tvStocks, tvEstado;
        ImageButton btnDetalle, btnEditar, btnDesactivar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvItemNombreProducto);
            tvCodigo = itemView.findViewById(R.id.tvItemCodigoProducto);
            tvCategoria = itemView.findViewById(R.id.tvItemCategoriaProducto);
            tvPrecio = itemView.findViewById(R.id.tvItemPrecioProducto);
            tvStocks = itemView.findViewById(R.id.tvItemStocksProducto);
            tvEstado = itemView.findViewById(R.id.tvItemEstadoProducto);
            btnDetalle = itemView.findViewById(R.id.btnDetalleProducto);
            btnEditar = itemView.findViewById(R.id.btnEditarProducto);
            btnDesactivar = itemView.findViewById(R.id.btnDesactivarProducto);
        }
    }
}