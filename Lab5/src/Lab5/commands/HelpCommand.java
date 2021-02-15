package Lab5.commands;

import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.Console;

/**
 * Command 'help'. It's here just for logical structure.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование:  '" + getName() + "'");
        }
        return false;
    }
}
