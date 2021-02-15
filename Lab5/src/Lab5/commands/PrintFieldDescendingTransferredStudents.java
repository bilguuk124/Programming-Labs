package Lab5.commands;

import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;

/**
 * Command print_descending_transferred. Prints values of transferred students in descending order.
 */
public class PrintFieldDescendingTransferredStudents extends AbstractCommand{
    private CollectionManager collectionManager;

    public PrintFieldDescendingTransferredStudents(CollectionManager collectionManager) {
        super("print_descending_transferred", "вывести значения поля transferredStudents всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            collectionManager.print_field_descending_transferred_students();
            if (collectionManager.getSumOfTransferStudents() == 0) throw new CollectionIsEmptyException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return false;
    }
}
