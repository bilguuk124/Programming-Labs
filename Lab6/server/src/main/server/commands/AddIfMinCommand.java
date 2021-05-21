package server.commands;

import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
import java.time.Instant;

import Lab5.common.data.*;
import Lab5.common.exceptions.WrongAmountOfElementsException;
import Lab5.common.interactions.GroupRaw;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;


    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min ", "{element}","добавить новый элемент, если его значение меньше, чем у наименьшего");
        this.collectionManager = collectionManager;

    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object commandObjectArgument) {
        try {
            if (!argument.isEmpty() || commandObjectArgument == null) throw new WrongAmountOfElementsException();
            GroupRaw groupRaw = (GroupRaw) commandObjectArgument;
            StudyGroup groupToAdd = new StudyGroup(
                   collectionManager.generateNextId(),
                   groupRaw.getName(),
                   groupRaw.getCoordinates(),
                   java.util.Date.from(Instant.now()),
                   groupRaw.getStudentsCount(),
                   groupRaw.getTransferredStudents(),
                   groupRaw.getFormOfEducation(),
                   groupRaw.getSemester(),
                   groupRaw.getGroupAdmin()
            );
            if (collectionManager.collectionSize() == 0 || groupToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(groupToAdd);
                ResponseOutputer.appendln("Группа успешно добавлен!");
                return true;
            } else ResponseOutputer.appenderror("Значение группы больше, чем значение наименьшего из групп!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appenderror("Исдользование: " + getName() + "'");
        } catch (ClassCastException exception) {}
        return false;
    }
}
