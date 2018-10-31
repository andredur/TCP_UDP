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
            in = new BufferedReader(new FileReader("tcp"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open  file. Serving time instead.");
        }
    }

    public void run() {
        while (true) {
            Socket sock = null;
            try {
                sock = socket.accept();
                OutputStream os = sock.getOutputStream();
                new TCPServerThread().send(os, "tcp");
                InputStream is = sock.getInputStream();
                //new TCPServerThread().receiveFile(is,"test.txt");
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void receiveFile(InputStream is, String name) throws Exception {
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
        String filePath = "E:\\".concat(filename);
        File myFile = new File(filePath);
        byte[] byteArray = new byte[(int) myFile.length() + 1];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(byteArray, 0, byteArray.length);
        os.write(byteArray, 0, byteArray.length);
        os.flush();
    }
}