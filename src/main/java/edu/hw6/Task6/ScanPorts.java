package edu.hw6.Task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ScanPorts {

    private ScanPorts() {

    }

    final private static String OPEN = "OPEN";
    final private static String CLOSE = "CLOSE";
    final private static String UDP = "UDP";
    final private static String TCP = "TCP";
    private final static int START_PORT = 0;
    private final static int END_PORT = 49151;
    private final static int TIME_OUT = 1000;


    Map<Integer, String> allPorts;

    @SuppressWarnings("MagicNumber")
    private static final Map<Integer, String> FAMOUS_PORT = new HashMap<>() { {
        put(135, "EPMAP");
        put(137, "Служба имен NetBIOS");
        put(138, "Служба датаграмм NetBIOS");
        put(139, "Служба сеансов NetBIOS");
        put(445, "Microsoft-DS Active Directory");
        put(843, "Adobe Flash");
        put(1900, "Simple Service Discovery Protocol (SSDP)");
        put(3702, "Динамическое обнаружение веб-служб");
        put(5353, "Многоадресный DNS");
        put(5355, "Link-Local Multicast Name Resolution (LLMNR)");
        put(17500, "Dropbox");
        put(27017, "MongoDB");
    }};

    public static void scanPorts() {
        for (int currentPort = START_PORT; currentPort <= END_PORT; currentPort++) {
            openTCP(currentPort);
            openUDP(currentPort);
        }
    }

    public static void openTCP(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            if (FAMOUS_PORT.containsKey(port)) {
                printInfoAboutFamousPort(TCP, OPEN, port);
            }
        } catch (IOException e) {
            if (FAMOUS_PORT.containsKey(port)) {
                printInfoAboutFamousPort(TCP, CLOSE, port);
            }
        }
    }

    public static void openUDP(int port) {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            if (FAMOUS_PORT.containsKey(port)) {
                printInfoAboutFamousPort(UDP, OPEN, port);
            }
        } catch (IOException e) {
            if (FAMOUS_PORT.containsKey(port)) {
                printInfoAboutFamousPort(UDP, OPEN, port);
            }
        }
    }

    @SuppressWarnings("RegexpSinglelineJava")
    public static void printInfoAboutFamousPort(String protocol, String state, int port) {
        System.out.println(protocol + " " + port + " " + state + " " + FAMOUS_PORT.get(port));
    }
}
