package com.curso.v0;

interface ComportamientoVolar{
	void ejecutaVuelo();
}

abstract class Ave{ //ABSTRACCION
	private ComportamientoVolar cv; //HAS-A ABSTRACTO

	void volar() {
		cv.ejecutaVuelo(); //DELEGACION
	}

	public void setCv(ComportamientoVolar cv) { //ENCAPSULACION 
		this.cv = cv;
	}
}

class Aguila extends Ave{}
class Pato extends Ave{}
class Pinguino extends Ave{}

public class Principal {

	public static void main(String[] args) {
		
		ComportamientoVolar cv1 = 
				() -> System.out.println("Si Volar");
		ComportamientoVolar cv2 = 
				() -> System.out.println("No Volar");
		ComportamientoVolar cv3 = 
				() -> System.out.println("Aleatorio Volar");
				
		Pato pato = new Pato();
		pato.setCv(cv2);
		pato.volar(); 
		
		pato.setCv(cv1);
		pato.volar(); 
		
		pato.setCv(cv3);
		pato.volar(); 

	}

}
