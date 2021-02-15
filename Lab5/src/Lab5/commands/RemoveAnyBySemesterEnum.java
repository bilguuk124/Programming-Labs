package Lab5.commands;

import Lab5.data.Semester;
import Lab5.data.StudyGroup;
import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.GroupNotFoundException;
import Lab5.exceptions.SemesterNotFoundException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;

import java.util.Locale;

/**
 * Command 'remove_any_by_semester'. Removes one group if it's semester is equal to user input.
 */
public class RemoveAnyBySemesterEnum extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveAnyBySemesterEnum(CollectionManager collectionManager) {
        super("remove_by_semester", "удалить из коллекции один элемент, значение поля semesterEnum которого эквивалентно заданному");
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
            String semester = argument.trim().toUpperCase(Locale.ROOT);
            if(!collectionManager.semesterContains(semester)) throw new SemesterNotFoundException();
            StudyGroup groupToRemove = collectionManager.getBySemester(semester);
            if (groupToRemove == null) throw new GroupNotFoundException();
            collectionManager.removeAnyBySemesterEnum(groupToRemove);
            Console.println("Успешно удален");
            return true;
        } catch (SemesterNotFoundException e) {
            Console.printerror("Такого Семестра нет");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: " + getName() + "<семестр>");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (GroupNotFoundException exception) {
            Console.printerror("Группы с таким семестром в коллекции нет!");
        }
        return false;
    }
}
