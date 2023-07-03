package server;

import realization.client.log.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Server {

    private static final Logger LOGGER = Logger.getInstance();
    private ServerSocket serverSocket;
    private Socket socket;
    private final List<Connection> connections = Collection.synchronizedList(new ArrayList<>());

    public void listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.log("Старт сервера");
            System.out.println("Старт сервера");

            while (true){
                socket = serverSocket.accept();
                final Connection connection = new Connection(socket);
                connections.add(connection);
                connection.start();
            }
        } catch (IOException e) {
            LOGGER.log("Ошибка на сервере" + Server.class.getName());
            e.printStackTrace();
        } finally {
            LOGGER.log("Закрытие потоков у сервера" + Server.class.getName());
        }
    }

    private class Connection extends Thread {

        private BufferedReader in;
        private PrintWriter out;

        public Connection(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                LOGGER.log("ОШибка в соединении" + Connection.class.getName());
            }
        }
    }
}
