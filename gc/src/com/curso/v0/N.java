package com.curso.v0;

class M {
}

class N {
	private M m = new M(); //HAS-A

	public void makeItNull(M pM) {
		pM = null;
	}

	public void makeThisNull() {
		makeItNull(m);
	}

	public static void main(String[] args) {
		N n = new N(); 
		n.makeThisNull();
	}
}