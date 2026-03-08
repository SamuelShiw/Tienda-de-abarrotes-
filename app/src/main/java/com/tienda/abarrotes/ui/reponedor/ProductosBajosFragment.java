package com.tienda.abarrotes.ui.reponedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Producto;
import com.tienda.abarrotes.ui.adapters.ProductoAdapter;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;

import java.util.List;

public class ProductosBajosFragment extends Fragment {

    private RecyclerView rvProductosBajos;
    private TextView tvEmptyProductosBajos;

    private InventarioViewModel inventarioViewModel;
    private ProductoAdapter adapter;

    private final ActivityResultLauncher<Intent> launcherReposicion =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> cargarProductosBajos()
            );

    public ProductosBajosFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_productos_bajos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inventarioViewModel = new InventarioViewModel(requireContext());

        rvProductosBajos = view.findViewById(R.id.rvProductosBajos);
        tvEmptyProductosBajos = view.findViewById(R.id.tvEmptyProductosBajos);

        setupRecycler();
        cargarProductosBajos();
    }

    private void setupRecycler() {
        adapter = new ProductoAdapter(new ProductoAdapter.OnProductoActionListener() {
            @Override
            public void onDetalle(Producto producto) {
                abrirReposicion(producto);
            }

            @Override
            public void onEditar(Producto producto) {
                abrirReposicion(producto);
            }

            @Override
            public void onDesactivar(Producto producto) {
                abrirReposicion(producto);
            }
        });

        rvProductosBajos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvProductosBajos.setAdapter(adapter);
    }

    private void abrirReposicion(Producto producto) {
        if (producto == null) {
            Toast.makeText(requireContext(), "Producto inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(requireContext(), ReposicionActivity.class);
        intent.putExtra("id_producto", producto.getIdProducto());
        launcherReposicion.launch(intent);
    }

    private void cargarProductosBajos() {
        List<Producto> lista = inventarioViewModel.listarProductosConStockBajo();
        adapter.setLista(lista);

        if (lista == null || lista.isEmpty()) {
            tvEmptyProductosBajos.setVisibility(View.VISIBLE);
            rvProductosBajos.setVisibility(View.GONE);
        } else {
            tvEmptyProductosBajos.setVisibility(View.GONE);
            rvProductosBajos.setVisibility(View.VISIBLE);
        }
    }
}