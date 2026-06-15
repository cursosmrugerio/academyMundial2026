package com.curso.v1;

import java.io.UnsupportedEncodingException;

public class Principal0 {

	public static void main(String[] args) {
		
		byte[] bytes = {1,2,3};

		try {
			String cadena = new String(bytes,0,1,"a");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
