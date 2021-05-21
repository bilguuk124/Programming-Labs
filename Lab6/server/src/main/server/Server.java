package server;

import Lab5.common.interactions.Request;
import Lab5.common.interactions.Response;
import Lab5.common.interactions.ResponseCode;
import Lab5.common.utility.Outputer;

import server.utility.RequestHandler;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;


/**
 * Runs the server.
 */
public class Server {
    private int port;
    private DatagramChannel datagramChannel;
    private SocketAddress address;
    private Selector selector;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(16384);
    private SocketAddress target;
    private Scanner scanner = new Scanner(System.in);

    {
        try {
            target = new InetSocketAddress(InetAddress.getLocalHost(),8090);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private RequestHandler requestHandler;


    public Server(int port, RequestHandler requestHandler) {
        this.port = port;
        this.requestHandler = requestHandler;
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try {
            App.logger.info("Запуск сервера...");
            datagramChannel = DatagramChannel.open();
            address = new InetSocketAddress(InetAddress.getLocalHost(), port);
            datagramChannel.bind(address);
            datagramChannel.configureBlocking(false);
            selector = Selector.open();
            datagramChannel.register(selector, SelectionKey.OP_READ);
            App.logger.info("Сервер успешно запущен.");
            Runnable userInput = () -> {
                try {
                    while (true) {
                        String[] userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                        userCommand[1] = userCommand[1].trim();
                        if (!userCommand[0].equals("save")) {
                            Outputer.println("Сервер не может сам принимать такую команду! ");
                            return;
                        }

                        Response response = executeRequest(new Request(userCommand[0], userCommand[1]));
                        Outputer.println(response.getResponseBody());
                    }
                } catch (Exception e) {
                    Outputer.println("Запрощено выход программы через CTRL+D");
                    App.logger.info("Запрощено выход программы через CTRL+D");
                    try {
                        stop();
                    } catch (IOException exception) {
                        Outputer.println("Произошла ошибка при попытке завершить соединение с клиентом!");
                        App.logger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
                    }
                }
            };
            Thread thread = new Thread(userInput);
            thread.start();
            boolean processingStatus = true;
            while(processingStatus){
                    processingStatus = processingClientRequest();
            }
            stop();

        } catch (IOException exception) {
            Outputer.println("Произошла ошибка при попытке завершить соединение с клиентом!");
            App.logger.error("Произошла ошибка при попытке завершить соединение с клиентом!");
        }


    }
    private void stop() throws IOException {
        App.logger.info("Завершение работы сервера...");
        String[] saveResponse = new String[]{"save",""};
        Response response = executeRequest(new Request(saveResponse[0],saveResponse[1]));
        Outputer.println(response.getResponseBody());
        App.logger.info("Коллекция сохранена!");
        datagramChannel.close();
        App.logger.info("Работа сервера успешно завершен");
        System.exit(0);
    }

      private void makeByteBufferToResponse(Response response) throws IOException{
        byteBuffer.put(serialize(response));
        byteBuffer.flip();
    }

    private byte[] serialize(Response response) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
    private Request deserialize() throws IOException, ClassNotFoundException{
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        byteBuffer.clear();
        return request;
    }
    private Response executeRequest(Request request){
        return requestHandler.handle(request);
    }

    private void sendResponse(Response response) throws IOException{
        makeByteBufferToResponse(response);
        DatagramChannel channel = null;
        while(channel == null){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for(SelectionKey key : selectionKeys){
                if(key.isWritable()){
                    channel = (DatagramChannel) key.channel();
                    channel.send(byteBuffer,target);
                    channel.register(selector, SelectionKey.OP_READ);
                    break;
                }
            }
        }
        byteBuffer.clear();
    }
    private Request receive() throws IOException, ClassNotFoundException {
        DatagramChannel channel = null;
        while(channel == null){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for(SelectionKey key : selectionKeys){
                if(key.isReadable()){
                    channel = (DatagramChannel) key.channel();
                    channel.receive(byteBuffer);
                    byteBuffer.flip();
                    channel.register(selector, SelectionKey.OP_WRITE);
                    break;
                }
            }
        } return deserialize();
    }
    private boolean processingClientRequest(){
        Request request = null;
        Response response = null;
        try{
            do{
                request = receive();
                App.logger.info("Получено команда '" + request.getCommandName() + "'");
                response = executeRequest(request);
                sendResponse(response);
                App.logger.info("Команда '" + request.getCommandName() + "' выполнена и отправлена");
            } while (response.getResponseCode() != ResponseCode.SERVER_EXIT);
            return false;
        }catch (ClassNotFoundException exception){
            Outputer.printerror("Произошла ошибка при чтении полученных данных!");
            App.logger.error("Произошла ошибка при чтении полученных данных!");
        }
        catch (IOException e) {
            Outputer.println("Клиент успешно отключен от сервера");
            App.logger.info("Клиент успешно отключен от сервера");
        } return true;
    }
}