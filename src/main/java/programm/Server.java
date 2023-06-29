package programm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        System.out.println("[SERVER] - is connected");
    }

    public void startServer(){
        try{
            System.out.println("[SERVER] - is started");
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("[SERVER] - new Client Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeServer(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(1234));
        server.startServer();
    }
}
