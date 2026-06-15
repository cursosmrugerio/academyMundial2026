package com.sim.v1;

public class Principal {

	public static void main(String[] args) {
		int[] values = {0,1,2,3,4};
		new Principal().processArray(values);
	}
	
	public void processArray(int[] values){
		int sum = 0; //0,1,3,6,10
        int i = 0; //0,1,2,3,4,5*
	
		while(values[i]<100){ //ArrayIndexOutOfBoundsException
			sum = sum +values[i];
            i++;
		}
		
		System.out.println("sum = "+sum); //10
	}

}
