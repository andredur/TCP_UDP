import java.io.*;
public class TCPServer {
    public static void main(String[] args) throws IOException {
        new TCPServerThread().start();
}
