package com.tienda.abarrotes.ui.admin.trabajadores;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tienda.abarrotes.R;
import com.tienda.abarrotes.model.Trabajador;
import com.tienda.abarrotes.ui.adapters.TrabajadorAdapter;
import com.tienda.abarrotes.ui.common.QRTrabajadorActivity;
import com.tienda.abarrotes.viewmodel.TrabajadorViewModel;

import java.util.List;

public class TrabajadoresFragment extends Fragment {

    private RecyclerView rvTrabajadores;
    private TextView tvEmptyTrabajadores;
    private Button btnNuevoTrabajador;

    private TrabajadorViewModel trabajadorViewModel;
    private TrabajadorAdapter adapter;

    private final ActivityResultLauncher<Intent> launcherRegistrarEditar =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> cargarTrabajadores()
            );

    public TrabajadoresFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trabajadores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trabajadorViewModel = new TrabajadorViewModel(requireContext());

        initViews(view);
        setupRecyclerView();
        configurarEventos();
        cargarTrabajadores();
    }

    private void initViews(View view) {
        rvTrabajadores = view.findViewById(R.id.rvTrabajadores);
        tvEmptyTrabajadores = view.findViewById(R.id.tvEmptyTrabajadores);
        btnNuevoTrabajador = view.findViewById(R.id.btnNuevoTrabajador);
    }

    private void setupRecyclerView() {
        adapter = new TrabajadorAdapter(new TrabajadorAdapter.OnTrabajadorActionListener() {
            @Override
            public void onEditar(Trabajador trabajador) {
                Intent intent = new Intent(requireContext(), EditarTrabajadorActivity.class);
                intent.putExtra("id_trabajador", trabajador.getIdTrabajador());
                launcherRegistrarEditar.launch(intent);
            }

            @Override
            public void onDesactivar(Trabajador trabajador) {
                mostrarDialogoDesactivar(trabajador);
            }

            @Override
            public void onVerQr(Trabajador trabajador) {
                Intent intent = new Intent(requireContext(), QRTrabajadorActivity.class);
                intent.putExtra("id_trabajador", trabajador.getIdTrabajador());
                startActivity(intent);
            }
        });

        rvTrabajadores.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvTrabajadores.setAdapter(adapter);
    }

    private void configurarEventos() {
        btnNuevoTrabajador.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RegistrarTrabajadorActivity.class);
            launcherRegistrarEditar.launch(intent);
        });
    }

    private void cargarTrabajadores() {
        List<Trabajador> lista = trabajadorViewModel.listarTrabajadores();
        adapter.setLista(lista);

        if (lista == null || lista.isEmpty()) {
            tvEmptyTrabajadores.setVisibility(View.VISIBLE);
            rvTrabajadores.setVisibility(View.GONE);
        } else {
            tvEmptyTrabajadores.setVisibility(View.GONE);
            rvTrabajadores.setVisibility(View.VISIBLE);
        }
    }

    private void mostrarDialogoDesactivar(Trabajador trabajador) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Desactivar trabajador")
                .setMessage("¿Deseas desactivar a " + trabajador.getNombreCompleto().trim() + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    boolean ok = trabajadorViewModel.desactivarTrabajador(trabajador.getIdTrabajador());
                    Toast.makeText(requireContext(),
                            ok ? "Trabajador desactivado" : "No se pudo desactivar",
                            Toast.LENGTH_SHORT).show();
                    cargarTrabajadores();
                })
                .setNegativeButton("No", null)
                .show();
    }
}