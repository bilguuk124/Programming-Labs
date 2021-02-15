package Lab5.commands;

import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;

/**
 * Command 'sum_of_transferred_students'. Prints the sum of transfer students of all groups.
 */
public class SumOfTransferredStudents extends AbstractCommand {
    private CollectionManager collectionManager;

    public SumOfTransferredStudents(CollectionManager collectionManager) {
        super("sum_of_transferred_students", "вывести сумму переведённых студентов для всех элементов коллекции");
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
            double sumOfTransferStudents = collectionManager.getSumOfTransferStudents();
            if (sumOfTransferStudents == 0) throw new CollectionIsEmptyException();
            Console.println("Сумма переведённых студентов: " + sumOfTransferStudents);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return false;
    }
}
