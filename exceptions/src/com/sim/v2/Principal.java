package com.sim.v2;

import java.io.FileNotFoundException;
import java.io.IOException;

class Animal {
//	void volar() throws IOException{
//	}
	
	void comer() {}
}

class Pato extends Animal {
	
	//void comer() throws Exception {}
	
//  3 ESCENARIO CORRECTOS
//	@Override
//	void volar(){
//	}
	
//	@Override
//	void volar() throws FileNotFoundException{
//	}
	
//	@Override
//	void volar() throws IOException{
//	}
	
	
//  NO PUEDE DEFINIR UNA SUPER CLASE
//	@Override
//	void volar() throws Exception{
//	}
}

public class Principal {

}
