package message;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader implements Runnable {
    private BufferedReader reader;

    public MessageReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String response;
            while ((response = this.reader.readLine()) != null && this.reader.ready()) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
