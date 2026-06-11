package com.curso.v1;

enum DiaSemana {
	LUNES("Sin Visitantes"), 
	MARTES("Visitantes Bajo"),
	MIERCOLES("Visitantes Bajo"),
	JUEVES("Visitantes Media"),
	VIERNES("Visitantes Media"),
	SABADO("Visitantes Alta"),
	DOMINGO("Visitantes Alta");
	
	private String cantidadVisitantes;
	
	DiaSemana(String cantidadVisitantes){
		this.cantidadVisitantes = cantidadVisitantes;
	}

	public String getCantidadVisitantes() {
		return cantidadVisitantes;
	}

	public void setCantidadVisitantes(String cantidadVisitantes) {
		this.cantidadVisitantes = cantidadVisitantes;
	}
}

public class Principal {

	public static void main(String[] args) {
		
		DiaSemana inicioMundial = DiaSemana.JUEVES;
		System.out.println(inicioMundial);
		System.out.println(inicioMundial.getCantidadVisitantes());
		System.out.println("********");
		
		for (DiaSemana ds : DiaSemana.values()) {
			System.out.println(ds);
			System.out.println(ds.getCantidadVisitantes());
			System.out.println("------------");	
		}
		

	}

}
