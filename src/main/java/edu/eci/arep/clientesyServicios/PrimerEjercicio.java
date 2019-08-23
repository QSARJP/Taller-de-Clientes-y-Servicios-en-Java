package edu.eci.arep.clientesyServicios;


import java.net.*;

/**
 * Main Class
 *
 */
public class PrimerEjercicio {

    static URL prueba;
    public static void main(String[] args) throws Exception {
        
		try {
            prueba = new URL("http://www.google.com:8080/test-html?name=prueba#referecias");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        getMetodos();
        
    }


    public static void getMetodos(){
        System.out.println("Protocol = " + prueba.getProtocol());
        System.out.println("Authority = " + prueba.getAuthority());
        System.out.println("Host = "+prueba.getHost());
        System.out.println("Port = "+prueba.getPort());
        System.out.println("Path = "+prueba.getPath());
        System.out.println("Query = "+prueba.getQuery());
        System.out.println("File = "+prueba.getFile());
        System.out.println("Ref = "+prueba.getRef());
    }
}
