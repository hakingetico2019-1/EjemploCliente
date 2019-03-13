/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente1;

import java.net.*;
import java.io.*;

public class Cliente1 {

    public static void main(String[] args) throws IOException {
        Socket socketCliente = null;
        BufferedReader entrada = null;
        PrintWriter salida = null;
        // Creamos un socket en el lado cliente, enlazado con un
        // servidor que está en la misma máquina que el cliente
        // y que escucha en el puerto 4444
        try {
            socketCliente = new Socket("localhost", 4444);// Obtenemos el canal de entrada
            directorio();
            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream())); // Obtenemos el canal de salida
            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
        } catch (IOException e) {
            System.err.println("No puede establer canales de E/S para la conexión");
            System.exit(-1);
        }
        BufferedReader stdIn
                = new BufferedReader(new InputStreamReader(System.in));

        String linea;

        // El programa cliente no analiza los mensajes enviados por el
        // usario, simplemente los reenvía al servidor hasta que este
        // se despide con "Adios"
        try {
            while (true) {// Leo la entrada del usuario
                linea = stdIn.readLine(); // La envia al servidor
                salida.println(linea); // Envía a la salida estándar la respuesta del servidor
                linea = entrada.readLine();
                System.out.println("Respuesta servidor: " + linea);// Si es "Adios" es que finaliza la comunicación
                if (linea.equals("Adios")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } // Libera recursos
        salida.close();
        entrada.close();
        stdIn.close();
        socketCliente.close();
    }

    public static void directorio() {
      try {

            // -- Linux --
            // Run a shell command
            // Process process = Runtime.getRuntime().exec("ls /home/mkyong/");
            // Run a shell script
            // Process process = Runtime.getRuntime().exec("path/to/hello.sh");
            // -- Windows --
            // Run a command
            Process process = Runtime.getRuntime().exec("cmd /c dir C:\\Users");
            //Run a bat file
            //Process process = Runtime.getRuntime().exec("cmd /c ", null, new File("C:\\Users"));

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                //System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
