package com.tienda.abarrotes.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.tienda.abarrotes.data.repository.CajaRepository;
import com.tienda.abarrotes.model.Caja;

public class CajaViewModel {

    private final CajaRepository repository;

    public CajaViewModel(Context context) {
        this.repository = new CajaRepository(context);
    }

    public String validarMonto(String monto) {
        if (TextUtils.isEmpty(monto)) {
            return "Ingresa un monto";
        }

        try {
            double montoDouble = Double.parseDouble(monto.trim());
            if (montoDouble < 0) {
                return "El monto no puede ser negativo";
            }
        } catch (Exception e) {
            return "Ingresa un monto válido";
        }

        return null;
    }

    public String abrirCaja(int idCajero, String montoInicial) {
        return repository.abrirCaja(idCajero, Double.parseDouble(montoInicial.trim()));
    }

    public String cerrarCaja(int idCajero, String montoFinal) {
        return repository.cerrarCaja(idCajero, Double.parseDouble(montoFinal.trim()));
    }

    public Caja obtenerCajaAbiertaPorCajero(int idCajero) {
        return repository.obtenerCajaAbiertaPorCajero(idCajero);
    }

    public Caja obtenerUltimaCajaPorCajero(int idCajero) {
        return repository.obtenerUltimaCajaPorCajero(idCajero);
    }
}