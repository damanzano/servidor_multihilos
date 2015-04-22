
public class EjecutableServidor {
	
	static ComunicacionServidor com;
	
	public static void main(String[] args) {
		com = new ComunicacionServidor();
		com.start();
	}
}
