package server.commands;

import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
import java.time.Instant;

import Lab5.common.data.StudyGroup;
import Lab5.common.exceptions.WrongAmountOfElementsException;
import Lab5.common.interactions.GroupRaw;



/**
 * Command 'add'. Adds a new element to collection.
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add ", "{element}","добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;

    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object commandObjectArgument) {
        try {
            if (!argument.isEmpty() || commandObjectArgument == null ) throw new WrongAmountOfElementsException();
            GroupRaw groupRaw = (GroupRaw) commandObjectArgument;
            collectionManager.addToCollection(new StudyGroup(
                    collectionManager.generateNextId(),
                groupRaw.getName(),
                groupRaw.getCoordinates(),
                java.util.Date.from(Instant.now()),
                groupRaw.getStudentsCount(),
                groupRaw.getTransferredStudents(),
                groupRaw.getFormOfEducation(),
                groupRaw.getSemester(),
                groupRaw.getGroupAdmin()
            ));
            ResponseOutputer.appendln("Группа успешно добавлен!");
            return true;
        } catch (WrongAmountOfElementsException e ) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException e){
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
