import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ControlCliente extends Thread {
	private int id;
	private Socket s;
	private boolean conectado;
	private ComunicacionServidor jefe;

	public ControlCliente(Socket ref, ComunicacionServidor jefe, int clientId) {
		this.id=clientId;
		this.s = ref;
		this.jefe = jefe;
		conectado=true;
		System.out.println(this);
	}

	@Override
	public void run() {
		System.out.println("Nuevo hilo para el cliente "+id+" esperando mensajes");
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
		DataInputStream entrada = null;
		try {
			entrada = new DataInputStream(s.getInputStream());
			int val = entrada.readInt();
			System.out.println("recibido: " + val +" del cliente "+id);
		} catch (SocketException e) {			
			System.err.println("Se perdió la conexión con el cliente");
			try {
				entrada.close();
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			s = null;
			conectado = false;
			jefe.avisar("mesa vacia", this);			
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}

	public void enviar(String mensaje) {
		DataOutputStream salida = null;
		try {
			salida = new DataOutputStream(s.getOutputStream());
			salida.writeUTF(mensaje);
			System.out.println("Se envió el mensaje: " + mensaje);
		} catch (SocketException e) {			
			System.err.println("Se perdió la conexión con el cliente");
			try {
				salida.close();
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			s = null;
			conectado = false;
			jefe.avisar("mesa vacia", this);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
