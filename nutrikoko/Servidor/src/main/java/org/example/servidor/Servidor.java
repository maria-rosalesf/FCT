package org.example.servidor;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.example.threads.LoginThread;

public class Servidor implements Runnable {

    private static final String FICHERO_CONF = "src\\main\\java\\org\\example\\servidor\\servidor.properties";

    private int puerto;
    private boolean stop;
    private ServerSocket serverSocket;
    private List<Socket> clientes;
    private ExecutorService pool;

    public Servidor() {
        try {
            stop = false;
            Properties conf = new Properties();
            conf.load(new FileInputStream(FICHERO_CONF));
            puerto = Integer.parseInt(conf.getProperty("PUERTO"));
            clientes = new ArrayList<>();
            // Crear un pool de hilos con un número fijo de hilos
            pool = Executors.newFixedThreadPool(10); // Por ejemplo, 10 hilos
        } catch (IOException e) {
            System.out.println("Error al leer las propiedad del fichero \"servidor.properties\"");
        }
    }

    @Override
    public void run() {
        iniciar();
    }

    private void iniciar() {
        try (ServerSocket sSocket = new ServerSocket(puerto)) {
            serverSocket = sSocket;

            while (!stop) {
                Socket cliente = serverSocket.accept();
                // Ejecutar la lógica del cliente en un hilo del pool
                pool.execute(() -> {
                    LoginThread hiloLogin = new LoginThread(cliente, new Protocolo());
                    hiloLogin.start();

                    if (hiloLogin.loginExitoso()) {
                        clientes.add(cliente);
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("No se ha podido escuchar en el puerto " + puerto);
        }
    }

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            Thread hiloServidor = new Thread(servidor);
            hiloServidor.start();

            Scanner sc = new Scanner(System.in);
            System.out.println("Pulse s para parar el servidor");
            String respuesta = sc.nextLine();
            while (!respuesta.equalsIgnoreCase("s")) {
                respuesta = sc.nextLine();
            }

            // Detener el pool de hilos antes de salir
            servidor.pool.shutdown();
            servidor.stop = true;
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}