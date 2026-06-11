package com.curso.v2;

enum DiaSemana {
	LUNES("Sin Visitantes"){
		@Override
		double getCostoBoleto() {
			return 0;
		}
	}, 
	MARTES("Visitantes Bajo"){
		@Override
		double getCostoBoleto() {
			return 10;
		}
	}, 
	MIERCOLES("Visitantes Bajo"){
		@Override
		double getCostoBoleto() {
			return 10;
		}
	}, 
	JUEVES("Visitantes Media"){
		@Override
		double getCostoBoleto() {
			return 20;
		}
	}, 
	VIERNES("Visitantes Media"){
		@Override
		double getCostoBoleto() {
			return 20;
		}
	}, 
	SABADO("Visitantes Alta"){
		@Override
		double getCostoBoleto() {
			return 50;
		}
	}, 
	DOMINGO("Visitantes Alta"){
		@Override
		double getCostoBoleto() {
			return 50;
		}
	};
	
	private String cantidadVisitantes;
	
	DiaSemana(String cantidadVisitantes){
		this.cantidadVisitantes = cantidadVisitantes;
	}
	
	abstract double getCostoBoleto();

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
			System.out.println(ds.getCostoBoleto());
			System.out.println("------------");	
		}
		

	}

}
