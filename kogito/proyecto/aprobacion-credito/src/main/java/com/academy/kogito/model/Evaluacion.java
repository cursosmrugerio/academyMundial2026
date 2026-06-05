package com.academy.kogito.model;

import java.io.Serializable;

/**
 * Modelo de dominio que representa el RESULTADO de la evaluación de crédito (datos de SALIDA).
 *
 * <p>Es la variable "evaluacion" del proceso BPMN {@code aprobacionCredito} y se llena
 * con el resultado de la decisión {@code evaluacion} del DMN {@code scoringCredito}.</p>
 *
 * <p>El campo {@link #decision} solo puede tomar uno de estos valores:
 * {@code "APROBADO"}, {@code "RECHAZADO"} o {@code "REVISION"}, y es la base del
 * gateway exclusivo del proceso.</p>
 */
public class Evaluacion implements Serializable {

    /** Identificador de versión para la serialización. */
    private static final long serialVersionUID = 1L;

    /** Puntaje calculado por el scoring (a mayor score, mejor perfil crediticio). */
    private int score;

    /** Decisión final: uno de {"APROBADO", "RECHAZADO", "REVISION"}. */
    private String decision;

    /** Explicación legible del porqué de la decisión. */
    private String motivo;

    /**
     * Constructor vacío.
     * <p>Requerido por Kogito/Jackson para mapear el resultado del DMN y para
     * reconstruir la variable de salida del proceso.</p>
     */
    public Evaluacion() {
        // Constructor por defecto necesario para frameworks (Kogito, Jackson, etc.).
    }

    // ------------------------------------------------------------------
    // Getters y setters
    // ------------------------------------------------------------------

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * Representación textual de la evaluación, útil para logs y depuración.
     */
    @Override
    public String toString() {
        return "Evaluacion{" +
                "score=" + score +
                ", decision='" + decision + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
