package com.sim.v1;

public class Principal0 {

	public static void main(String[] args) {
		int[] values = {0,1,2,3,4};
		new Principal0().processArray(values);
	}
	
	public void processArray(int[] values){
		int sum = 0; //0,1,3,6,10
        int i = 0; //0,1,2,3,4,5*
	
		while(i < values.length && values[i]<100){
			sum = sum +values[i];
            i++;
		}
		
		System.out.println("sum = "+sum); //10
	}

}
