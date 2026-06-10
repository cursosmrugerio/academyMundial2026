package com.curso.v6;

//INMUTABLE
public record Empleado (String nombre,
		int edad,
		String[] prestaciones,
		StringBuilder curp){
}