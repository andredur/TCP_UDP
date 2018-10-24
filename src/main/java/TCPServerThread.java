import java.net.*;
import java.util.*;
import java.io.*;

public class TCPServerThread extends Thread {
    protected ServerSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public TCPServerThread() throws IOException {
        this("TCPServerThread");
    }

    public TCPServerThread(String name) throws IOException {
        super(name);
        socket = new ServerSocket(4445);

        try {
            in = new BufferedReader(new FileReader("test.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open  file. Serving time instead.");
        }
    }

    public void run() throws  {
        while(true){
            Socket sock = null;
            try {
                sock = socket.accept();
                OutputStream os = sock.getOutputStream();
                new TCPServerThread().send(os,);
                InputStream is = sock.getInputStream();
                //new TCPServerThread().receiveFile(is,"test.txt");
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        /*while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextQuote();

                buf = dString.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                moreQuotes = false;
            }
        }*/
        socket.close();
    }
    public void receiveFile(InputStream is,String name) throws Exception{
        int filesize = 6022386;
        int bytesRead;
        int current = 0;
        byte[] byteArray = new byte[filesize];

        FileOutputStream fos = new FileOutputStream(name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(byteArray, 0, byteArray.length);
        current = bytesRead;

        do {
            bytesRead = is.read(byteArray, current,
                    (byteArray.length - current));
            if (bytesRead >= 0)
                current += bytesRead;
        } while (bytesRead > -1);

        bos.write(byteArray, 0, current);
        bos.flush();
        bos.close();
    }
    public void send(OutputStream os, String filename) throws Exception {
        // sendfile
        String filePath = "C:\\Users\\andre\\Documents\\GitHub\\TCP_UDP\\target\\files".concat(filename);
        File myFile = new File(filePath);
        byte[] byteArray = new byte[(int) myFile.length() + 1];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(byteArray, 0, byteArray.length);
        os.write(byteArray, 0, byteArray.length);
        os.flush();
    }