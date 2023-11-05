import message.MessageReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static config.SocketConfig.*;

public class EntryPoint {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        String consoleInputMessage;

        BufferedReader reader;
        BufferedReader consoleInput;
        BufferedWriter writer;

        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();

            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // client consoleInput
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("방에 입장하였습니다. 나가기: quit");

            Thread messageReadThread = new Thread(new MessageReader(reader));
            messageReadThread.start();

            while (true) {
                try {
                    consoleInputMessage = consoleInput.readLine();
                    if (consoleInputMessage.equals("quit")) {
                        break;
                    }

                    writer.write(consoleInputMessage + "\r\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) serverSocket.close();
            if (clientSocket != null) clientSocket.close();
        }
    }
}


//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//import static config.SocketConfig.*;
//
//public class ServerEntryPoint {
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//        Socket clientSocket = null;
//
//        String response, consoleInputMessage;
//
//
//        BufferedReader reader;
//        BufferedReader consoleInput;
//        BufferedWriter writer;
//
//        try {
//            serverSocket = new ServerSocket(port);
//            clientSocket = serverSocket.accept();
//
//            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
//
//            // client consoleInput
//            consoleInput = new BufferedReader(new InputStreamReader(System.in));
//
//            System.out.println("방에 입장하였습니다. 나가기: quit");
//
//            while ((response = reader.readLine()) != null) {
//                System.out.println(response);
//            }
//
//            clientSocket.close();
//        } catch (IOException e) {
//            System.out.println(e);
//        } finally {
//            if (serverSocket != null) serverSocket.close();
//            if (clientSocket != null) clientSocket.close();
//        }
//    }
//}

