package client;
import Lab5.common.interactions.ResponseCode;
import  client.utility.UserHandler;
import Lab5.common.interactions.Response;
import Lab5.common.interactions.Request;
import Lab5.common.utility.Outputer;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private DatagramSocket socket;
    private Scanner scanner = new Scanner(System.in);



    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler){
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
    }
    public void run(){
        try{
            socket = new DatagramSocket(8090);
            socket.setSoTimeout(5 * 1000);
            Request requestToServer = null;
            Response serverResponse = null;
            do{
                requestToServer = serverResponse!= null ? userHandler.handle(serverResponse.getResponseCode()) : userHandler.handle(null);
                if (requestToServer.isEmpty()) continue;
                if (requestToServer.getCommandName().equals("exit")) break;
                sendRequest(requestToServer);
                Outputer.println("I've sent the package");
                serverResponse = receive();
                Outputer.println("I've received a package");
                Outputer.println(serverResponse.getResponseBody());
            } while(!requestToServer.getCommandName().equals("exit"));
            Outputer.println("Завершение работы клиента...");
            System.exit(0);
        } catch (SocketTimeoutException e){
            Outputer.printerror("Сервер временно не работает.");
            Outputer.println("Хотите попробовать ещё раз? (y/n)");
            String tryAgain = scanner.nextLine();
            if(tryAgain.equals("y")) {
                Outputer.println("Попробую ещё раз!");
                socket.close();
                socket.disconnect();
                run();
            }
            else if(tryAgain.equals("n")){
                Outputer.println("Завершаю работу клиента!");
                socket.close();
                socket.disconnect();
                System.exit(0);
            }
        } catch (IOException | ClassNotFoundException exception) {
            Outputer.printerror("Произошла ошибка при работе с сервером!");
            exception.printStackTrace();
            System.exit(0);
        }
    }

    private Response deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Response response = (Response) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }
    private void sendRequest(Request request) throws IOException{
        byte[] sendBuffer = serialize(request);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer,sendBuffer.length, InetAddress.getLocalHost(),port);
        socket.send(sendPacket);
    }
    private byte[] serialize(Request request) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
    private Response receive() throws IOException, ClassNotFoundException, SocketTimeoutException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        return deserialize(getPacket,getBuffer);
    }
}

