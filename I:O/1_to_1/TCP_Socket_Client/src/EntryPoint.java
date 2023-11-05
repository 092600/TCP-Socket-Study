import message.MessageReader;

import java.io.*;
import java.net.Socket;
import static config.SocketConfig.*;

public class EntryPoint {
//    private static Thread messageReaderThread;

    public static void main(String[] args) throws IOException {
        Socket socket = null;

        String consoleInputMessage;
        InputStream is;
        OutputStream os;
        BufferedReader reader, consoleInput;
        BufferedWriter writer;

        try {
            socket = new Socket(host, port);

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("방에 입장하였습니다. 나가기: quit");

            Thread messageReadThread = new Thread(new MessageReader(reader));
            messageReadThread.start();

            // Message Sender
            while (true) {
                consoleInputMessage = consoleInput.readLine();
                if (consoleInputMessage.equals("quit")) {
                    if (reader != null){
                        reader.close();
                    }
                    break;
                }

                writer.write(consoleInputMessage + "\r\n");
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) socket.close();
        }
    }
}


//import java.io.*;
//import java.net.Socket;
//import static config.SocketConfig.*;
//
//public class ClientEntryPoint {
//    public static void main(String[] args) throws IOException {
//        Socket socket = null;
//
//        //
//        String consoleInputMessage;
//        InputStream is;
//        OutputStream os;
//        BufferedReader reader, consoleInput;
//        BufferedWriter writer;
//
//
//        try {
//            socket = new Socket(host, port);
//
//            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//            // client consoleInput
//            consoleInput = new BufferedReader(new InputStreamReader(System.in));
//
//            System.out.println("방에 입장하였습니다. 나가기: quit");
//
//            while (((consoleInputMessage = consoleInput.readLine()) != null)) {
//                if (consoleInputMessage.equals("quit")) break;
//
//                writer.write(consoleInputMessage+"\r\n");
//                writer.flush();
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        } finally {
//            if (socket != null) socket.close();
//        }
//    }
//}
