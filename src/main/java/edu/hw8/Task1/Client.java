package edu.hw8.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

@SuppressWarnings("UncommentedMain")
public class Client extends Thread {

    private static BufferedReader bufferedServerReader;
    private static BufferedWriter bufferedWriter;
    private static  BufferedReader bufferedConsoleReader;
    private static Socket socket;

    private final static int PORT = 8000;

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", PORT);
            bufferedConsoleReader = new BufferedReader(new InputStreamReader(System.in));
            bufferedServerReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request;

            request = bufferedConsoleReader.readLine();

            bufferedWriter.write(request + "\n");
            bufferedWriter.flush();
            String answer = bufferedServerReader.readLine();
            System.out.println(answer);

        } catch (Exception e) {

        } finally {
            try {
                socket.close();
                bufferedConsoleReader.close();
                bufferedServerReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
