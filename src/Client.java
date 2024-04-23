import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private Scanner in;
    private Scanner inputUser;
    private PrintStream out;
    private Chat server;
    private String nickname;
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;

    public Client(Socket socket, Chat server){
        this.socket = socket;
        this.server = server;
        try {
            inputUser = new Scanner(System.in);
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);
            this.pressNickname();
            new Thread(this).start();
        }catch (IOException e) {
            System.err.println("Socket failed");
        }
      }

    private void pressNickname() {
        System.out.println("Press your nick: ");
        nickname = inputUser.nextLine();
        out.println("Hello " + nickname + "\n");
    }

    void receive(String message){
        out.println(message);
    }

    public void run() {
        try {
            time = new Date();
            dt1 = new SimpleDateFormat("HH:mm:ss");
            dtime = dt1.format(time);
            // читаем из сети и пишем в сеть
            out.println("Welcome to Chat!");
            String input = in.nextLine();

            while (!input.equals("bye")) {
                String word = "(" + dtime + ") " + nickname + ": " + input;
                server.send(word);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

