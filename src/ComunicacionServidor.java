import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ComunicacionServidor extends Thread {

	private ServerSocket ss;
	private boolean conectado;
	private ArrayList<ControlCliente> clientes;

	public ComunicacionServidor() {
		try {
			ss = new ServerSocket(5000);
			conectado = true;
			clientes = new ArrayList<ControlCliente>();
			System.out.println("Servidor atendiendo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (conectado) {
			try {
				Socket st = ss.accept();
				ControlCliente ct = new ControlCliente(st, this, clientes.size());
				ct.start();
				clientes.add(ct);	
				System.out.println("tenemos: " + clientes.size() + " clientes");
				if(clientes.size()==4){
					for(int i = 0 ; i < clientes.size() ; i++){
						clientes.get(i).enviar("Happy Hour!!");
					}	
				}
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void avisar(String aviso, ControlCliente controlCliente) {
		if(aviso.equals("mesa vacia")){
			clientes.remove(controlCliente);
		}
		System.out.println("tenemos: " + clientes.size() + " clientes");
	}


}
