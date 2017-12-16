import com.google.gson.Gson;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RemoteSQLDataAdapter implements IDataAccess {

    private PrintWriter outStream;
    private BufferedReader inStream;
    private Socket socket;
    private Gson gson = new Gson();

    public RemoteSQLDataAdapter(String hostName, int portNumber) {
        try {
            socket = new Socket(hostName, portNumber);
            outStream = new PrintWriter(socket.getOutputStream(), true);
            inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Product loadProduct(int id) {
        try {
            ClientRequest request = new ClientRequest("GET Product", String.valueOf(id));
            outStream.println(gson.toJson(request));
            String serverAnswer = inStream.readLine();
            return gson.fromJson(serverAnswer, Product.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public boolean saveProduct(Product product) {
        try {
            ClientRequest request = new ClientRequest("PUT Product", gson.toJson(product));
            outStream.println(gson.toJson(request));

            String serverAnswer = inStream.readLine();
            if (serverAnswer.equals("ERROR"))
                return false;
            else
                return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
