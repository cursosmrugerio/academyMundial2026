package com.curso.v0;

public class Salmon {
	int count; //0 //1 //4

	{
		System.out.print(count + "-");
	}

	{
		count++;
	}

	public Salmon() {
		count = 4;
		System.out.print(2 + "-");
	}

	public static void main(String[] args) {
		System.out.print(7 + "-");
		var s = new Salmon();
		System.out.print(s.count + "-");
	}
}

// 7-0-2-4-
