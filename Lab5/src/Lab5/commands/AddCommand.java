package Lab5.commands;

import java.time.Instant;
import java.time.LocalDateTime;

import Lab5.data.StudyGroup;
import Lab5.exceptions.IncorrectInputInScriptException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;
import Lab5.utility.GroupAsker;

/**
 * Command 'add'. Adds a new element to collection.
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private GroupAsker groupAsker;

    public AddCommand(CollectionManager collectionManager, GroupAsker groupAsker) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            collectionManager.addToCollection(new StudyGroup(
                collectionManager.generateNextId(),
                groupAsker.askName(),
                groupAsker.askCoordinates(),
                java.util.Date.from(Instant.now()),
                groupAsker.askStundentsCount(),
                groupAsker.askTransferredStundentsCount(),
                groupAsker.askFormOfEducation(),
                groupAsker.askSemester(),
                groupAsker.askGroupAdmin()
            ));
            Console.println("Группа успешно добавлен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
