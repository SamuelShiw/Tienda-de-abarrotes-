package com.tienda.abarrotes.ui.reponedor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.ui.adapters.MovimientoStockAdapter;
import com.tienda.abarrotes.viewmodel.InventarioViewModel;

public class HistorialReposicionActivity extends AppCompatActivity {

    private RecyclerView rvHistorialReposicion;
    private MovimientoStockAdapter adapter;
    private InventarioViewModel inventarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reposicion);

        inventarioViewModel = new InventarioViewModel(this);

        rvHistorialReposicion = findViewById(R.id.rvHistorialReposicion);

        adapter = new MovimientoStockAdapter();
        rvHistorialReposicion.setLayoutManager(new LinearLayoutManager(this));
        rvHistorialReposicion.setAdapter(adapter);

        adapter.setLista(inventarioViewModel.listarMovimientos());
    }
}