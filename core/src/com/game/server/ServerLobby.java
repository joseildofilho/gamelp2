package com.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 10/04/17.
 */
public class ServerLobby {
    private ExecutorService threads;
    private List<Socket> notPlaying;
    private List<Room> playing;
    ServerSocket ss;
    private final int POOL_SIZE = 10, PORT = 2000;

    private ServerLobby() {
        //não precisa ser instancido por outra classe

        threads = Executors.newFixedThreadPool(POOL_SIZE);
        notPlaying = new Vector<Socket>();
        playing = new Vector<Room>();

        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        this.startLoop();
    }

    private void startLoop() {
        try {
            //melhorar a cara desse bloco
            while (true) {
                //todo falta se preocupar com a entrada de clientes que já entraram uma vez para não causar bug a reentrada do desgraçado
                System.out.println("esperando clientes");
                Socket s1 = ss.accept();
                System.out.println("um cliente foi recebido");
                Socket s2 = ss.accept();
                System.out.println("um cliente foi recebido");
                System.out.println("iniciando Room");
                threads.execute(new Room(s1,s2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //inicia o servidor
        new ServerLobby().start();
    }

}
