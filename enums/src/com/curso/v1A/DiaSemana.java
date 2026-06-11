package com.curso.v1A;

public final class DiaSemana {

    public static final DiaSemana LUNES =
            new DiaSemana("LUNES", 0, "Sin Visitantes");

    public static final DiaSemana MARTES =
            new DiaSemana("MARTES", 1, "Visitantes Bajo");

    public static final DiaSemana MIERCOLES =
            new DiaSemana("MIERCOLES", 2, "Visitantes Bajo");

    public static final DiaSemana JUEVES =
            new DiaSemana("JUEVES", 3, "Visitantes Media");

    public static final DiaSemana VIERNES =
            new DiaSemana("VIERNES", 4, "Visitantes Media");

    public static final DiaSemana SABADO =
            new DiaSemana("SABADO", 5, "Visitantes Alta");

    public static final DiaSemana DOMINGO =
            new DiaSemana("DOMINGO", 6, "Visitantes Alta");


    private final String nombre;
    private final int ordinal;
    private String cantidadVisitantes;

    private DiaSemana(String nombre, int ordinal, String cantidadVisitantes) {
        this.nombre = nombre;
        this.ordinal = ordinal;
        this.cantidadVisitantes = cantidadVisitantes;
    }

    public String getCantidadVisitantes() {
        return cantidadVisitantes;
    }

    public void setCantidadVisitantes(String cantidadVisitantes) {
        this.cantidadVisitantes = cantidadVisitantes;
    }

    public String name() {
        return nombre;
    }

    public int ordinal() {
        return ordinal;
    }

    public String toString() {
        return nombre;
    }

    public static DiaSemana[] values() {
        return new DiaSemana[] {
            LUNES,
            MARTES,
            MIERCOLES,
            JUEVES,
            VIERNES,
            SABADO,
            DOMINGO
        };
    }

    public static DiaSemana valueOf(String nombre) {
        DiaSemana[] dias = values();

        for (int i = 0; i < dias.length; i++) {
            if (dias[i].name().equals(nombre)) {
                return dias[i];
            }
        }

        throw new IllegalArgumentException("No existe el día: " + nombre);
    }
}