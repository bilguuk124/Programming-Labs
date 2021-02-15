package Lab5.commands;

import java.security.acl.Group;
import java.time.Instant;
import java.time.LocalDateTime;

import Lab5.data.StudyGroup;
import Lab5.exceptions.IncorrectInputInScriptException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;
import Lab5.utility.GroupAsker;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private GroupAsker groupAsker;

    public AddIfMinCommand(CollectionManager collectionManager, GroupAsker groupAsker) {
        super("add_if_min {element}", "добавить новый элемент, если его значение меньше, чем у наименьшего");
        this.collectionManager = collectionManager;
        this.groupAsker = groupAsker;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            StudyGroup groupToAdd = new StudyGroup(
                   collectionManager.generateNextId(),
                   groupAsker.askName(),
                   groupAsker.askCoordinates(),
                   java.util.Date.from(Instant.now()),
                   groupAsker.askStundentsCount(),
                   groupAsker.askTransferredStundentsCount(),
                   groupAsker.askFormOfEducation(),
                   groupAsker.askSemester(),
                   groupAsker.askGroupAdmin()
            );
            if (collectionManager.collectionSize() == 0 || groupToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(groupToAdd);
                Console.println("Группа успешно добавлен!");
                return true;
            } else Console.printerror("Значение группы больше, чем значение наименьшего из групп!");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Исдользование: " + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
