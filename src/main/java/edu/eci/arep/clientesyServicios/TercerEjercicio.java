package edu.eci.arep.clientesyServicios;


import java.io.*;
import java.net.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * Main Class
 *
 */
public class TercerEjercicio {


    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4567.");
            System.exit(1);
        }
        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Ready to listen ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request = "";
            String inputLine;

            while ((inputLine = in.readLine())!= null) {
                if (inputLine.matches("(GET)+.*"))
                    request = inputLine.split(" ")[1];
                if (!in.ready())
                    break;
            }
            //System.out.println(request);
            if (request == null){
                request = "/error.html";
            }else if (request.equals("/")){
                request = "/index.html";
    
            }
            requests(request, out, clientSocket.getOutputStream());
            

            out.close();
            in.close();
            clientSocket.close();

        }

    }

    private static void requests(String request, PrintWriter out, OutputStream outputStream) throws IOException {
        
        if (request.endsWith(".png")) {
            readImage(out,outputStream,request);

        }else if (request.endsWith("/linkin")) {
            linkin(out);

        } else if (request.endsWith(".html")) {
            readHTML(out, request);
        } else {
            readHTML(out, "/error.html");
        }

    }

        
    
    

    private static void readImage(PrintWriter out, OutputStream outStream, String request) throws IOException {
        File graphicResource= new File("resource/" +request);
        FileInputStream inputImage = new FileInputStream(graphicResource);
        byte[] bytes = new byte[(int) graphicResource.length()];
        inputImage.read(bytes);

        DataOutputStream binaryOut;
        binaryOut = new DataOutputStream(outStream);
        binaryOut.writeBytes("HTTP/1.1 200 OK \r\n");
        binaryOut.writeBytes("Content-Type: image/png\r\n");
        binaryOut.writeBytes("Content-Length: " + bytes.length);
        binaryOut.writeBytes("\r\n\r\n");
        binaryOut.write(bytes);
        binaryOut.close();
    }

    private static void readHTML(PrintWriter out, String request) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader( "resource" + request ));
        out.print("HTTP/1.1 200 OK \r");
        out.print("Content-Type: text/html \r\n");
        out.print("\r\n");
        String line;
        while ((line = bf.readLine()) != null) {
            out.print(line);
        }
    }



    private static int getPort() {

		if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e.on localhost)
    }
	



    private static void linkin(PrintWriter out) {
        out.print("HTTP/1.1 200 OK \r");
        out.print("Content-Type: text/html \r\n");
        out.print("\r\n");
        URL url = null;
        try {
            url = new URL("https://www.linkinpark.com/");
        } catch (IOException ex) {
            
                System.err.println(ex);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                out.println(inputLine + "\r\n");
            }

        } catch (IOException x) {
        }
    }

    private static Object[] extParams(String request) {
        Object[] params = null;
        
        if (request.matches("[/apps/]+[a-z]+[?]+[a-z,=,&,0-9]*")) {
            String[] preParams = request.split("\\?")[1].split("&");
            System.out.println(preParams.length);
            params = new Object[preParams.length];
            for (int i = 0; i < preParams.length; i++) {
                System.out.println(preParams[i]);
                String str = preParams[i].split("=")[1];
                System.out.println(str);
                params[i] = str;
            }
        }
        return params;
    }

    
}
