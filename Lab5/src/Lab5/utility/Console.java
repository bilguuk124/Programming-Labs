package Lab5.utility;

import Lab5.Main;
import Lab5.exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Operates command input
 */
public class Console {
    private CommandManager commandManager;
    private Scanner userScanner;
    private GroupAsker groupAsker;
    private List<String> scriptStack = new ArrayList<>();

    public Console(CommandManager commandManager,Scanner userScanner, GroupAsker groupAsker){
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.groupAsker = groupAsker;
    }

    /**
     * User input reader
     */
    public void interactiveMode(){
        String[] userCommand = {"",""};
        int commandStatus;
        try{
            do{
                Console.print(Main.PS1);
                userCommand = (userScanner.nextLine().trim() + " ").split(" ",2);
                userCommand[1] = userCommand[1].trim();
                commandManager.addToHistory(userCommand[0]);
                commandStatus = launchCommand(userCommand);
            }while(commandStatus != 2);
        }catch (NoSuchElementException e){
            Console.printerror("Пользовательский ввод не обнаружен");
        }catch (IllegalStateException e){
            Console.printerror("Непредвиденная ошибка");
        }
    }

    /**
     * Script reader.
     * @param argument It's argument
     * @return Exit code.
     */
    public int scriptMode(String argument){
        String[] userCommand = {"",""};
        int commandStatus;
        scriptStack.add(argument);
        try(Scanner scriptScanner = new Scanner(new File(argument))){
            if(!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner  tmpScanner = groupAsker.getUserScanner();
            groupAsker.setUserScanner(scriptScanner);
            groupAsker.setFileMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                Console.println(Main.PS1 + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")){
                    for(String script:scriptStack){
                        if(userCommand[1].equals(script))throw new ScriptRecursionException();
                    }
                }
                commandStatus = launchCommand(userCommand);
            } while(commandStatus == 0 && scriptScanner.hasNextLine());
            groupAsker.setUserScanner(tmpScanner);
            groupAsker.setUserMode();
            if(commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                Console.println("Проверьте скрипт на корректность введенных данных! ");
            return commandStatus;
        }catch(FileNotFoundException e){
            Console.printerror("Файл со скиптом не найден");
        }catch(NoSuchElementException e){
            Console.printerror("Файл со скриптом пуст");
        }catch(ScriptRecursionException e){
            Console.printerror("Скрипты не могут вызаться рекурсивно");
        }catch(IllegalStateException e){
            Console.printerror("Непредвиденная ошибка");
            System.exit(0);
        }finally{
            scriptStack.remove(scriptStack.size()-1);
        }
        return 1;
    }

    /**
     * Launches the command
     * @param userCommand Command to launch
     * @return Exit code.
     */
    private int launchCommand(String[] userCommand) {
        switch (userCommand[0]) {
            case "":
                break;
            case "help":
                if (!commandManager.help(userCommand[1])) return 1;
                break;
            case "info":
                if (!commandManager.info(userCommand[1])) return 1;
                break;
            case "show":
                if (!commandManager.show(userCommand[1])) return 1;
                break;
            case "add":
                if (!commandManager.add(userCommand[1])) return 1;
                break;
            case "update":
                if (!commandManager.update(userCommand[1])) return 1;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(userCommand[1])) return 1;
                break;
            case "clear":
                if (!commandManager.clear(userCommand[1])) return 1;
                break;
            case "save":
                if (!commandManager.save(userCommand[1])) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(userCommand[1])) return 1;
                else return scriptMode(userCommand[1]);
            case "add_if_min":
                if (!commandManager.addIfMin(userCommand[1])) return 1;
                break;
            case "remove_greater":
                if (!commandManager.removeGreater(userCommand[1])) return 1;
                break;
            case "remove_by_semester":
                if(!commandManager.removeAnyBySemesterEnum(userCommand[1])) return 1;
                break;
            case "history":
                if (!commandManager.history(userCommand[1])) return 1;
                break;
            case "sum_of_transferred_students":
                if (!commandManager.sumOfTransferredStudents(userCommand[1])) return 1;
                break;
            case "filter_by_form_of_education_type":
                if (!commandManager.filterByFormOfEducation(userCommand[1])) return 1;
                break;
            case "print_descending_transferred":
                if(!commandManager.printFieldDescendingTransferredStudents(userCommand[1])) return 1;
                break;
            case "exit":
                if (!commandManager.exit(userCommand[1])) return 1;
                else return 2;
            default:
                if (!commandManager.noSuchCommand(userCommand[0])) return 1;
        }
        return 0;
    }


    /**
     * Print toOut.toString() (\n,error,)to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
            System.out.print(toOut);
        }
        public static void println(Object toOut) {
            System.out.println(toOut);
        }
        public static void printerror(Object toOut) {
            System.out.println("error: " + toOut);
        }
        public static void printtable(Object element1, Object element2) {
            System.out.printf("%-37s%-1s%n", element1, element2);
        }
    @Override
    public String toString(){
        return "Console (класс для обработки ввода комманд)";
    }
}
