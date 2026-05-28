package com.eskere.inmobiliaria.modelo;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Contrato implements Serializable {
    
    @SerializedName("idContrato")
    private int idContrato;
    
    @SerializedName(value = "fechaInicio", alternate = {"FechaInicio", "fechaDesde", "desde"})
    private String fechaInicio;
    
    @SerializedName(value = "fechaFin", alternate = {"FechaFin", "fechaHasta", "hasta"})
    private String fechaFin;
    
    @SerializedName(value = "monto", alternate = {"Monto", "precio", "importe"})
    private double monto;
    
    @SerializedName("inquilino")
    private Inquilino inquilino;
    
    @SerializedName("inmueble")
    private Inmueble inmueble;
    public Contrato() {
    }
    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}