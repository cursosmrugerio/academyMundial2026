package com.academy.kogito.model;

import java.io.Serializable;

/**
 * Modelo de dominio que representa la SOLICITUD de crédito (datos de ENTRADA).
 *
 * <p>Es la variable "solicitud" del proceso BPMN {@code aprobacionCredito} y, a su vez,
 * el Input Data del DMN {@code scoringCredito}. Por eso sus campos deben coincidir
 * EXACTAMENTE con la estructura esperada por la decisión.</p>
 *
 * <p>Implementa {@link Serializable} porque Kogito puede necesitar serializar las
 * variables del proceso (por ejemplo, al persistir el estado de la instancia).</p>
 */
public class SolicitudCredito implements Serializable {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /** Nombre del solicitante (campo opcional en el scoring). */
    private String nombre;

    /** Edad del solicitante en años. El scoring premia el rango 25-65. */
    private int edad;

    /** Ingresos mensuales del solicitante (en moneda local). */
    private double ingresosMensuales;

    /** Monto de crédito solicitado. */
    private double montoSolicitado;

    /** Plazo del crédito expresado en meses. */
    private int plazoMeses;

    /** Indica si el solicitante registra mora (incumplimientos previos). */
    private boolean tieneMora;

    /**
     * Constructor vacío.
     * <p>Requerido por Kogito/Jackson para deserializar el JSON de entrada
     * al invocar el endpoint REST autogenerado y para reconstruir la variable
     * del proceso.</p>
     */
    public SolicitudCredito() {
        // Constructor por defecto necesario para frameworks (Kogito, Jackson, etc.).
    }

    /**
     * Constructor con todos los campos. Útil en pruebas y para construir
     * solicitudes de forma programática.
     *
     * @param nombre            nombre del solicitante
     * @param edad              edad en años
     * @param ingresosMensuales ingresos mensuales
     * @param montoSolicitado   monto de crédito solicitado
     * @param plazoMeses        plazo en meses
     * @param tieneMora         {@code true} si registra mora
     */
    public SolicitudCredito(String nombre, int edad, double ingresosMensuales,
                            double montoSolicitado, int plazoMeses, boolean tieneMora) {
        this.nombre = nombre;
        this.edad = edad;
        this.ingresosMensuales = ingresosMensuales;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
        this.tieneMora = tieneMora;
    }

    // ------------------------------------------------------------------
    // Getters y setters (necesarios para Kogito/DMN y la serialización JSON)
    // ------------------------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getIngresosMensuales() {
        return ingresosMensuales;
    }

    public void setIngresosMensuales(double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }

    public double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public boolean isTieneMora() {
        return tieneMora;
    }

    public void setTieneMora(boolean tieneMora) {
        this.tieneMora = tieneMora;
    }

    /**
     * Representación textual de la solicitud, útil para logs y depuración.
     */
    @Override
    public String toString() {
        return "SolicitudCredito{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", ingresosMensuales=" + ingresosMensuales +
                ", montoSolicitado=" + montoSolicitado +
                ", plazoMeses=" + plazoMeses +
                ", tieneMora=" + tieneMora +
                '}';
    }
}
