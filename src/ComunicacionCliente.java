import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ComunicacionCliente extends Thread {
	private Socket s;
	private boolean conectado;

	public ComunicacionCliente() {
		try {
			s = new Socket(InetAddress.getByName("127.0.0.1"), 5000);
			conectado = true;
			enviar();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void enviar() {
		try {
			DataOutputStream salida = new DataOutputStream(s.getOutputStream());
			int valor = (int) (Math.random() * 255);
			salida.writeInt(valor);
			System.out.println("se envio: " + valor);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (conectado) {
			recibir();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void recibir() {
		DataInputStream entrada;
		try {
			entrada = new DataInputStream(s.getInputStream());
			String mensaje = entrada.readUTF();
			System.out.println("Se recibio: " +mensaje);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
