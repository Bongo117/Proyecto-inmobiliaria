package com.eskere.inmobiliaria.modelo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Pago implements Serializable {
    @SerializedName("idPago")
    private int idPago;
    
    @SerializedName(value = "numero", alternate = {"nroPago", "numeroPago"})
    private int nroPago;
    
    @SerializedName(value = "idContrato", alternate = {"idAlquiler", "contratoId"})
    private int idAlquiler;
    
    @SerializedName(value = "fechaDePago", alternate = {"fechaPago", "fecha"})
    private String fecha;
    
    @SerializedName(value = "importe", alternate = {"monto"})
    private double importe;

    public Pago() {}
    public String getCodigoPagoFormateado() {
        return "Pago #" + idPago;
    }

    public String getNroPagoFormateado() {
        return "Nro: " + nroPago;
    }

    public String getContratoFormateado() {
        return "Contrato: " + idAlquiler;
    }

    public String getImporteFormateado() {
        return "$ " + importe;
    }

    public String getFechaFormateada() {
        return "Fecha: " + fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }
}