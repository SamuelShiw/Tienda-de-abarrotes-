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

public class ListaProductosFragment extends Fragment {

    private RecyclerView rvListaProductosReponedor;
    private TextView tvEmptyListaProductosReponedor;

    private InventarioViewModel inventarioViewModel;
    private ProductoAdapter adapter;

    private final ActivityResultLauncher<Intent> launcherReposicion =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> cargarProductos()
            );

    public ListaProductosFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_productos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inventarioViewModel = new InventarioViewModel(requireContext());

        rvListaProductosReponedor = view.findViewById(R.id.rvListaProductosReponedor);
        tvEmptyListaProductosReponedor = view.findViewById(R.id.tvEmptyListaProductosReponedor);

        setupRecycler();
        cargarProductos();
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

        rvListaProductosReponedor.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvListaProductosReponedor.setAdapter(adapter);
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

    private void cargarProductos() {
        List<Producto> lista = inventarioViewModel.listarInventario();
        adapter.setLista(lista);

        if (lista == null || lista.isEmpty()) {
            tvEmptyListaProductosReponedor.setVisibility(View.VISIBLE);
            rvListaProductosReponedor.setVisibility(View.GONE);
        } else {
            tvEmptyListaProductosReponedor.setVisibility(View.GONE);
            rvListaProductosReponedor.setVisibility(View.VISIBLE);
        }
    }
}