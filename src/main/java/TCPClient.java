import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        String hostname = "192.168.1.1";

        if (args.length != 1) {
            System.out.println("Usage: java TCPClient <filename>");
            return;
        }

        String filename = args[0];

        /*// get a datagram socket
        DatagramSocket socket = new DatagramSocket();

        // send request
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();*/

        Socket socket = new Socket(hostname, 15123);
        System.out.println("Setting up connection...");
        InputStream is = socket.getInputStream();
        try {
            new TCPClient().receiveFiles(is, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OutputStream os = socket.getOutputStream();
        socket.close();
        System.out.println("File received?...");
    }

    void sendFiles(OutputStream os, String path) throws Exception {
        File myFile = new File(path);
        byte[] bytearray = new byte[(int) myFile.length() + 1];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(bytearray, 0, bytearray.length);
        System.out.println("Sending file...");
        os.write(bytearray, 0, bytearray.length);
        os.flush();
    }

    void receiveFiles(InputStream is, String name) throws Exception {
        int filesize = 6022386;
        int bytesRead;
        int current = 0;
        byte[] bytearray = new byte[filesize];

        FileOutputStream fos = new FileOutputStream(name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(bytearray, 0, bytearray.length);
        current = bytesRead;

        do {
            bytesRead = is.read(bytearray, current, (bytearray.length - current));
            if (bytesRead >= 0)
                current += bytesRead;
        } while (bytesRead > -1);

        bos.write(bytearray, 0, current);
        bos.flush();
        bos.close();
    }
}
