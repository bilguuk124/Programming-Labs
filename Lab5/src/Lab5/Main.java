package Lab5;
import Lab5.commands.*;
import Lab5.utility.CollectionManager;
import Lab5.utility.CommandManager;
import Lab5.utility.Console;
import Lab5.utility.FileManager;
import Lab5.utility.GroupAsker;
import java.util.Scanner;

/**
 * Main application class. Creates all instances and runs the program.
 * @author Purevsuren Bilguun
 */

public class Main {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    public static void main(String[] args){
        try (Scanner userScanner = new Scanner(System.in)) {
            final String envVariable = "LAB.json";

            GroupAsker groupAsker = new GroupAsker(userScanner);
            FileManager fileManager = new FileManager(envVariable);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager, groupAsker),
                    new UpdateCommand(collectionManager, groupAsker),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ExitCommand(),
                    new ExecuteScriptCommand(),
                    new AddIfMinCommand(collectionManager, groupAsker),
                    new RemoveGreaterCommand(collectionManager, groupAsker),
                    new HistoryCommand(),
                    new SumOfTransferredStudents(collectionManager),
                    new FilterByFormOfEducationCommand(collectionManager),
                    new PrintFieldDescendingTransferredStudents(collectionManager),
                    new RemoveAnyBySemesterEnum(collectionManager)
            );
            Console console = new Console(commandManager, userScanner, groupAsker);

            console.interactiveMode();
        }
    }


}