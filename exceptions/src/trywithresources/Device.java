package trywithresources;

import java.io.IOException;

public class Device implements AutoCloseable {
	String header = null;

	public Device(String name) throws IOException {
		header = name;
		if ("D2".equals(name))
			throw new IOException("Unknown");
		System.out.println(header + " Opened");
	}

	public String read() throws IOException {
		return "";
	}

	public void close(){
		System.out.println("Closing device " + header);
		throw new RuntimeException("RTE while closing " + header);
	}

	public static void main(String[] args) throws Exception {
		try (Device d1 = new Device("D1"); 
			) {
			throw new Exception("test");
		}
	}

}

// D1 Opened
// Closing device D1
// Throws Exception (Desborde el programa) y se guarda RuntimeException



