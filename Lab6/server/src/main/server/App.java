package server;

import Lab5.common.utility.Outputer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.*;
import server.utility.CollectionFileManager;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.RequestHandler;

import java.io.File;
import java.io.IOException;

public class App {

    public static final int PORT = 1821;
    public static String ENV_VARIABLE = null;
    public static Logger logger = LoggerFactory.getLogger("server");

    public static void main(String[] args) {
        if(args.length != 0 ){
            ENV_VARIABLE = args[0];
            File file = new File(ENV_VARIABLE);
            if(!file.exists()){
                try{
                    if(file.createNewFile()){
                        Outputer.println("Файл успешно создан.");
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else{
            Outputer.println("Вы не ввели файл сохранение. Загружаю файл по умолчению: Default.json");
            ENV_VARIABLE = "Default.json";
        }

        CollectionFileManager collectionFileManager = new CollectionFileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new AddIfMinCommand(collectionManager),
                new RemoveGreaterCommand(collectionManager),
                new HistoryCommand(),
                new SumOfTransferredStudents(collectionManager),
                new RemoveAnyBySemesterEnum(collectionManager),
                new FilterByFormOfEducationCommand(collectionManager),
                new ServerExitCommand(),
                new PrintFieldDescendingTransferredStudents(collectionManager)
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT,requestHandler);
        server.run();









    }
}
