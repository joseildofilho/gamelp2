package com.game.Connection;

import com.game.bullet.Bullets;
import com.game.game.Ship;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.Scanner;

/**
 * Created by root on 10/04/17.
 */
public class Connection {
    // ip referente ao servidor de lobby
    private static final String IP_SERVE = "localhost";
    private int port = 2000;
    private Socket socket;

    // itens referentes a conexão com o outro player
    private DatagramSocket datagramSocket;
    private String ipEnemy;
    private byte[] bufferOut = new byte[4096], bufferIn = new byte[8];
    private DatagramPacket dpOut, dpIn;
    private String result;

    /**
     * Protocolo:
     * posicao 1,4 é o numero da posição x, posicao 5,8 é o numero da posição y
     * posição 0,7 valor tudo 9 = confirmada conexão
     */
    public Connection(String name) throws IOException, InterruptedException {
        socket = new Socket(IP_SERVE, this.port);
        datagramSocket = new DatagramSocket(0);
        dpIn = new DatagramPacket(bufferIn, bufferIn.length);
        Scanner scan = new Scanner(socket.getInputStream());

        //Envia para o servidor avisando a porta em que o datagram estara aberto
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println(datagramSocket.getLocalPort());
        ps.flush();

        //espera do servidor a resposta para conectar com o inimigo
        String ip = scan.nextLine();
        System.out.println("eu " + name + " Recebi o IP:" + ip);
        String port = scan.nextLine();
        System.out.println("Recebi a Porta:" + port);

        //tenta enviar o confirma para o inimigo
        //falta implementar o envio do conneceted
        bufferOut = "91239456".getBytes();
        dpOut = new DatagramPacket(bufferOut, bufferOut.length, InetAddress.getByName(ip.substring(1, ip.indexOf(":"))), Integer.parseInt(port));
        datagramSocket.send(dpOut);

        //espera o confirmar do inimigo
        //todo falta implmentar a confirmação da conexão
        datagramSocket.receive(dpIn);
        String s = new String(dpIn.getData());
        System.out.println(Integer.parseInt(s.substring(1, 4)));

        System.out.println(Integer.parseInt(s.substring(5, 8)));
        setResult(new String(dpIn.getData()));

    }

    public synchronized void updateShip(Ship ship) {
        try {
            datagramSocket.receive(dpIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = new String(dpIn.getData());
        if (s.startsWith("1"))
            Bullets.shoot(Integer.parseInt(s.substring(1, 4)), Integer.parseInt(s.substring(5, 8)), false);
        //é plausivel de erro visto que havera perca de informação com relação conversão dos float para int
        //todo o calculo para ajuste das naves em ambas as telas é feito aqui, claramente uma gambiarra, favor refatorar no futuro
        ship.setPosition(600 - ship.getWidth() - Integer.parseInt(s.substring(1, 4)), 700 - ship.getHeight() - Integer.parseInt(s.substring(5, 8)));


    }

    public synchronized void sendDataShip(Ship ship) {
        //todo jogar essa tralha para dentro da classe ship
        String result = (9000 + ((int) ship.getX())) + "" + (9000 + ((int) ship.getY()));
        dpOut.setData(result.getBytes());
        try {
            datagramSocket.send(dpOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendDataShoot(Ship ship) {
        String result = (1000 + ((int) ship.getX())) + "" + (9000 + ((int) ship.getY()));
        dpOut.setData(result.getBytes());
        try {
            datagramSocket.send(dpOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
