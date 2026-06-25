package com.curso.v0;

public class ServicePago {
	
	//El objeto de la interface es el que va a actuar como MOCK
	private ServiceCalculoInteres sri; //HAS A

	//@Autowired
	public ServicePago(ServiceCalculoInteres sri) {
		this.sri = sri;
	}
	
	double calcularInteres(double cantidad) {
		//DELEGACION
		return sri.calculoInteresesMoratorios(cantidad);
	}
	

}
