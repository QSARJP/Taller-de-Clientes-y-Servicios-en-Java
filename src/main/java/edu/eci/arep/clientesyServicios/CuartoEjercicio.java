package edu.eci.arep.clientesyServicios;



import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CuartoEjercicio {

    DatagramSocket socket;

    public CuartoEjercicio() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            Logger.getLogger(CuartoEjercicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];
        int cont = 0;
        while (true) {
            cont = 0;
            while (cont < 5) {
                try {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);

                    String dString = new Date().toString();
                    buf = dString.getBytes();
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                    cont++;
                } catch (IOException ex) {
                    Logger.getLogger(CuartoEjercicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CuartoEjercicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        CuartoEjercicio ct = new CuartoEjercicio();
        ct.startServer();
    }
}