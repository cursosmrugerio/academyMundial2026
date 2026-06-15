package com.sim.v1;

public class Principal1 {

	public static void main(String[] args) {
		int[] values = { 0, 1, 2, 3, 4 };
		new Principal1().processArray(values);
	}

	public void processArray(int[] values) {
		int sum = 0;
		int i = 0;
		try {
			while (values[i] < 100) {
				sum = sum + values[i];
				i++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		System.out.println("sum = " + sum);
	}

}
