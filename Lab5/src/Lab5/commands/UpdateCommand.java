package Lab5.commands;

import java.util.Date;

import Lab5.data.*;
import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.IncorrectInputInScriptException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.exceptions.GroupNotFoundException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;
import Lab5.utility.GroupAsker;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private GroupAsker groupAsker;

    public UpdateCommand(CollectionManager collectionManager, GroupAsker groupAsker) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Integer id = Integer.valueOf(argument);
            StudyGroup oldStudy = collectionManager.getById(id);
            if (oldStudy == null) throw new GroupNotFoundException();

            String name = oldStudy.getName();
            Coordinates coordinates = oldStudy.getCoordinates();
            Date creationDate = oldStudy.getCreationDate();
            Long studentsCount = oldStudy.getStudentsCount();
            int transferredStudentsCount = oldStudy.getTransferredStudents();
            FormOfEducation formOfEducation = oldStudy.getFormOfEducation();
            Semester semester = oldStudy.getSemesterEnum();
            Person groupAdmin = oldStudy.getGroupAdmin();

            collectionManager.removeToCollection(oldStudy);

            if (groupAsker.askQuestion("Хотите изменить имя группы?")) name = groupAsker.askName();
            if (groupAsker.askQuestion("Хотите изменить координаты группы?")) coordinates = groupAsker.askCoordinates();
            if (groupAsker.askQuestion("Хотите изменить число студентов?")) studentsCount = groupAsker.askStundentsCount();
            if (groupAsker.askQuestion("Хотите изменить число переведённых студентов?")) transferredStudentsCount = groupAsker.askTransferredStundentsCount();
            if (groupAsker.askQuestion("Хотите изменить форму обучения?")) formOfEducation = groupAsker.askFormOfEducation();
            if (groupAsker.askQuestion("Хотите изменить семестр группы?")) semester = groupAsker.askSemester();
            if (groupAsker.askQuestion("Хотите изменить админ группы?")) groupAdmin = groupAsker.askGroupAdmin();

            collectionManager.addToCollection(new StudyGroup(
                id,
                name,
                coordinates,
                creationDate,
                studentsCount,
                transferredStudentsCount,
                formOfEducation,
                semester,
                groupAdmin
            ));
            Console.println("Солдат успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            Console.printerror("ID должен быть представлен числом!");
        } catch (GroupNotFoundException exception) {
            Console.printerror("Солдата с таким ID в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
