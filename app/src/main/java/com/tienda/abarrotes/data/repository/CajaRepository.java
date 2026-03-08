package com.tienda.abarrotes.data.repository;

import android.content.Context;

import com.tienda.abarrotes.data.dao.CajaDao;
import com.tienda.abarrotes.model.Caja;
import com.tienda.abarrotes.utils.AppConstants;
import com.tienda.abarrotes.utils.DateTimeUtils;

public class CajaRepository {

    private final CajaDao cajaDao;

    public CajaRepository(Context context) {
        this.cajaDao = new CajaDao(context);
    }

    public Caja obtenerCajaAbiertaPorCajero(int idCajero) {
        return cajaDao.obtenerCajaAbiertaPorCajero(idCajero);
    }

    public Caja obtenerUltimaCajaPorCajero(int idCajero) {
        return cajaDao.obtenerUltimaCajaPorCajero(idCajero);
    }

    public String abrirCaja(int idCajero, double montoInicial) {
        Caja cajaAbierta = cajaDao.obtenerCajaAbiertaPorCajero(idCajero);
        if (cajaAbierta != null) {
            return "Ya tienes una caja abierta";
        }

        Caja caja = new Caja();
        caja.setIdCajero(idCajero);
        caja.setFechaApertura(DateTimeUtils.getCurrentDate());
        caja.setHoraApertura(DateTimeUtils.getCurrentTime());
        caja.setMontoInicial(montoInicial);
        caja.setEstado(AppConstants.CAJA_ABIERTA);
        caja.setFechaCierre(null);
        caja.setHoraCierre(null);
        caja.setMontoFinal(0);
        caja.setTotalVentas(0);

        long resultado = cajaDao.abrirCaja(caja);
        return resultado > 0 ? null : "No se pudo abrir la caja";
    }

    public String cerrarCaja(int idCajero, double montoFinal) {
        Caja cajaAbierta = cajaDao.obtenerCajaAbiertaPorCajero(idCajero);

        if (cajaAbierta == null) {
            return "No tienes una caja abierta";
        }

        boolean cerrado = cajaDao.cerrarCaja(
                cajaAbierta.getIdCaja(),
                DateTimeUtils.getCurrentDate(),
                DateTimeUtils.getCurrentTime(),
                montoFinal,
                cajaAbierta.getTotalVentas()
        );

        return cerrado ? null : "No se pudo cerrar la caja";
    }
}