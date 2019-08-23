package edu.eci.arep.clientesyServicios;




import java.io.*;
import java.net.*;

/**
 * Main Class
 *
 */
public class SegundoEjercicio {


    public static void main(String[] args) throws Exception {
        File file = new File("src/main/resource/file.html"); 
        FileWriter escribir = new FileWriter(file,true);
        URL google = new URL("https://www.facebook.com");
        try
            (BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()))){
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                escribir.append(inputLine);
            }  
            escribir.close();
        } catch (IOException x) {
            System.err.println(x);
        }
             
        
    }
}
