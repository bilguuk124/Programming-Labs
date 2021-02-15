package Lab5.commands;

import java.time.Instant;
import java.time.LocalDateTime;

import Lab5.data.StudyGroup;
import Lab5.exceptions.GroupNotFoundException;
import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.IncorrectInputInScriptException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;
import Lab5.utility.GroupAsker;


public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private GroupAsker groupAsker;

    public RemoveGreaterCommand(CollectionManager collectionManager, GroupAsker groupAsker) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
        this.groupAsker = groupAsker;
    }


    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
                StudyGroup marineToFind = new StudyGroup(
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
            StudyGroup marineFromCollection = collectionManager.getByValue(marineToFind);
            if (marineFromCollection == null) throw new GroupNotFoundException();
            collectionManager.removeGreater(marineFromCollection);
            Console.println("Группа успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование:  " + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (GroupNotFoundException exception) {
            Console.printerror("Группы с такими характеристиками в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
