package com.curso.v2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

class Empleado {
    private String nombre;
    private int edad;
    private double sueldo;

    public Empleado(String nombre, int edad, double sueldo) {
        this.nombre = nombre;
        this.edad = edad;
        this.sueldo = sueldo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void aumentarSueldo(double porcentaje) {
        this.sueldo = this.sueldo + (this.sueldo * porcentaje / 100);
    }

    public boolean esMayorDeEdad() {
        return this.edad >= 18;
    }

    public String getNombreEnMayusculas() {
        return this.nombre.toUpperCase();
    }

    @Override
    public int hashCode() {
        return Objects.hash(edad, nombre, sueldo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Empleado other = (Empleado) obj;

        return edad == other.edad
                && Objects.equals(nombre, other.nombre)
                && Double.doubleToLongBits(sueldo) == Double.doubleToLongBits(other.sueldo);
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + nombre + ", edad=" + edad + ", sueldo=" + sueldo + "]";
    }
}

class ComparadorEmpleadoPorNombre implements Comparator<Empleado> {

    @Override
    public int compare(Empleado empleado1, Empleado empleado2) {
        return empleado1.getNombre().compareTo(empleado2.getNombre());
    }
}

class ServicioEmpleado {

    public void aumentarSueldoAMayoresDeEdad(List<Empleado> empleados, double porcentaje) {
        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);

            if (empleado.esMayorDeEdad()) {
                empleado.aumentarSueldo(porcentaje);
            }
        }
    }

    public void ordenarPorNombre(List<Empleado> empleados) {
        Collections.sort(empleados, new ComparadorEmpleadoPorNombre());
    }

    public void mostrarNombresEnMayusculas(List<Empleado> empleados) {
        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);
            System.out.println(empleado.getNombreEnMayusculas());
        }
    }

    public double calcularSumaDeSalarios(List<Empleado> empleados) {
        double suma = 0;

        for (int i = 0; i < empleados.size(); i++) {
            Empleado empleado = empleados.get(i);
            suma = suma + empleado.getSueldo();
        }

        return suma;
    }

    public void mostrarEmpleados(List<Empleado> empleados) {
        for (int i = 0; i < empleados.size(); i++) {
            System.out.println(empleados.get(i));
        }
    }
}

public class PrincipalPOO {

    public static void main(String[] args) {

        List<Empleado> lista = Arrays.asList(
                new Empleado("Patrobas", 30, 50.50),
                new Empleado("Filologo", 20, 60.20),
                new Empleado("Epeneto", 25, 35.50)
        );

        ServicioEmpleado servicioEmpleado = new ServicioEmpleado();

        // MAYORES DE EDAD
        // ADD 15% SUELDO
        servicioEmpleado.aumentarSueldoAMayoresDeEdad(lista, 15);

        // ORDENALOS POR NOMBRE
        servicioEmpleado.ordenarPorNombre(lista);

        // MUESTRAME QUIENES SON CON SU NOMBRE EN MAYUSCULAS
        System.out.println("Nombres en mayusculas:");
        servicioEmpleado.mostrarNombresEnMayusculas(lista);

        // SUMA DE SUS SALARIOS
        double sumaSalarios = servicioEmpleado.calcularSumaDeSalarios(lista);

        System.out.println();
        System.out.println("Empleados ordenados por nombre:");
        servicioEmpleado.mostrarEmpleados(lista);

        System.out.println();
        System.out.println("Suma de salarios: " + sumaSalarios);
    }
}
