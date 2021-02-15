package Lab5.commands;

import Lab5.data.StudyGroup;
import Lab5.exceptions.GroupNotFoundException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;

/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            int id = Integer.valueOf(argument);
            StudyGroup groupToRemove = collectionManager.getById(id);
            if (groupToRemove == null) throw new GroupNotFoundException();
            collectionManager.removeToCollection(groupToRemove);
            Console.println("Группа успешно удален!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            Console.printerror("ID должен быть представлен числом!");
        } catch (GroupNotFoundException exception) {
            Console.printerror("Группы с таким ID в коллекции нет!");
        }
        return false;
    }
}
