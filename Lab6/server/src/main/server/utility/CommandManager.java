package server.utility;
import server.commands.Command;
import java.util.ArrayList;
import java.util.List;

import Lab5.common.exceptions.HistoryIsEmptyException;
import Lab5.common.utility.Outputer;

/**
 * Operates the command
 */
public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 8;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = new ArrayList<>();
    private Command helpCommand;
    private Command infoCommand;
    private Command showCommand;
    private Command addCommand;
    private Command updateCommand;
    private Command removeByIdCommand;
    private Command clearCommand;
    private Command saveCommand;
    private Command exitCommand;
    private Command executeScriptCommand;
    private Command addIfMinCommand;
    private Command removeGreaterCommand;
    private Command historyCommand;
    private Command sumOfTransferredStudents;
    private Command filterByFormOfEducationCommand;
    private Command printFieldDescendingTransferredStudents;
    private Command removeAnyBySemesterEnum;
    private Command serverExitCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand,
                          Command removeByIdCommand, Command clearCommand, Command saveCommand, Command exitCommand, Command executeScriptCommand,
                          Command addIfMinCommand, Command removeGreaterCommand, Command historyCommand, Command sumOfTransferredStudensCommand,
                          Command filterByFormOfEducationCommand, Command printFieldDescendingTransferredStudents,
                          Command removeAnyBySemesterEnum, Command serverExitCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.saveCommand = saveCommand;
        this.exitCommand = exitCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.historyCommand = historyCommand;
        this.sumOfTransferredStudents = sumOfTransferredStudensCommand;
        this.filterByFormOfEducationCommand = filterByFormOfEducationCommand;
        this.printFieldDescendingTransferredStudents = printFieldDescendingTransferredStudents;
        this.removeAnyBySemesterEnum = removeAnyBySemesterEnum;
        this.serverExitCommand = serverExitCommand;

        commands.add(helpCommand);
        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(saveCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(addIfMinCommand);
        commands.add(removeGreaterCommand);
        commands.add(historyCommand);
        commands.add(sumOfTransferredStudensCommand);
        commands.add(filterByFormOfEducationCommand);
        commands.add(printFieldDescendingTransferredStudents);
        commands.add(removeAnyBySemesterEnum);
        commands.add(serverExitCommand);
    }

    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore) throws NullPointerException {

        for (Command command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints that command is not found.
     * @param command Command, which is not found.
     * @return Command exit status.
     */
    public boolean noSuchCommand(String command) {
        ResponseOutputer.appendln("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
        return false;
    }

    /**
     * Prints info about the all commands.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean help(String argument, Object commandObjectArgument) {
        if (helpCommand.execute(argument,commandObjectArgument)) {
            for (Command command : commands) {
                ResponseOutputer.appendtable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean info(String argument, Object commandObjectArgument) {
        return infoCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean show(String argument, Object commandObjectArgument) {
        return showCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean add(String argument, Object commandObjectArgument) {
        return addCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean update(String argument, Object commandObjectArgument) {
        return updateCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean removeById(String argument, Object commandObjectArgument) {
        return removeByIdCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeAnyBySemesterEnum(String argument, Object commandObjectArgument){
        return removeAnyBySemesterEnum.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean clear(String argument, Object commandObjectArgument) {
        return clearCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean save(String argument, Object commandObjectArgument) {
        return saveCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument, Object commandObjectArgument) {
        return exitCommand.execute(argument, commandObjectArgument);
    }




    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean executeScript(String argument, Object commandObjectArgument) {
        return executeScriptCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean addIfMin(String argument, Object commandObjectArgument) {
        return addIfMinCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean removeGreater(String argument, Object commandObjectArgument) {
        return removeGreaterCommand.execute(argument, commandObjectArgument);
    }

    /**
     * Prints the history of used commands.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean history(String argument, Object commandObjectArgument) {
        if (historyCommand.execute(argument, commandObjectArgument)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.appendln("Последние использованные команды:");
                for (int i=0; i<commandHistory.length; i++) {
                    if (commandHistory[i] != null) ResponseOutputer.appendln(" " + commandHistory[i]);
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.appendln("Ни одной команды еще не было использовано!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean sumOfTransferredStudents(String argument, Object commandObjectArgument) {
        return sumOfTransferredStudents.execute(argument, commandObjectArgument);
    }
    public boolean printFieldDescendingTransferredStudents(String argument, Object commandObjectArgument){
        return printFieldDescendingTransferredStudents.execute(argument, commandObjectArgument);
    }


    /**
     * Executes needed command.
     * @param argument Its argument.
     * @param commandObjectArgument
     * @return Command exit status.
     */
    public boolean filterByFormOfEducation(String argument, Object commandObjectArgument) {
        return filterByFormOfEducationCommand.execute(argument, commandObjectArgument);
    }

    public boolean serverExit(String stringArgument, Object objectArgument) {return serverExitCommand.execute(stringArgument,objectArgument);}

    @Override
    public String toString() {
        return "CommandManager (вспомогательный класс для работы с командами)";
    }
}

