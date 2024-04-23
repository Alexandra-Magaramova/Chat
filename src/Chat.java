import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Chat {
     ArrayList<Client> clients = new ArrayList<>();
    // создаем серверный сокет на порту 1234
    ServerSocket server;

    public Chat() throws IOException {
        server = new ServerSocket(1234);
    }

    public void run(){
        while(true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента из сети
                Socket socket = server.accept();
                System.out.println("Client connected!" + socket);
                clients.add(new Client(socket, this));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void send(String message){
        for (Client client : clients){
            client.receive(message);
        }
}

    public static void main(String[] args) throws IOException {
        new Chat().run();
     }
}


