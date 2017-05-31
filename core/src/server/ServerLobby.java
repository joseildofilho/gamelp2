package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by root on 10/04/17.
 */
public class ServerLobby {
    private final int POOL_SIZE = 10, PORT = 2000;
    ServerSocket ss;
    private ExecutorService threads;
    private BlockingQueue<Socket> notPlaying;
    private List<Room> playing;
    private RankingDAO ranking;
    private Thread acceptLoop, roomMaker;

    private ServerLobby() {
        //não precisa ser instancido por outra classe

        threads = Executors.newFixedThreadPool(POOL_SIZE);
        notPlaying = new LinkedBlockingDeque<>();
        playing = new Vector<Room>();

        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //inicia o servidor
        new ServerLobby().start();
    }

    private void start() {
        this.startLoop();
        startRoomMaker();
    }

    private void startRoomMaker() {
        roomMaker = new Thread(() -> {
            Socket s1 = null, s2 = null;
            while (true) {
                if (notPlaying.isEmpty() && s1 == null) try {
                    s1 = notPlaying.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (notPlaying.isEmpty() && s2 == null) try {
                    s2 = notPlaying.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!s1.isConnected()) s1 = null;
                else if (!s2.isConnected()) s2 = null;
                else {
                    threads.execute(new Room(s1, s2));
                    s1 = null;
                    s2 = null;
                }
            }
        });
        roomMaker.start();
    }

    private void startLoop() {
        acceptLoop = new Thread(() -> {
            try {
                //melhorar a cara desse bloco
                while (true) {
                    notPlaying.add(ss.accept());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        acceptLoop.start();
    }

}
