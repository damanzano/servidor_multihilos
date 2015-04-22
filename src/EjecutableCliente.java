
public class EjecutableCliente {
	
	static ComunicacionCliente com;
	
	public static void main(String[] args) {
		com = new ComunicacionCliente();
		com.start();
	}
}
