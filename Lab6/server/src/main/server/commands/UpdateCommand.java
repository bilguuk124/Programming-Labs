package server.commands;

import server.utility.CollectionManager;
import java.util.Date;

import Lab5.common.data.*;
import Lab5.common.exceptions.CollectionIsEmptyException;
import Lab5.common.exceptions.GroupNotFoundException;
import Lab5.common.exceptions.WrongAmountOfElementsException;
import Lab5.common.interactions.GroupRaw;
import server.utility.ResponseOutputer;


/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update ","<ID> {element}", "обновить значение элемента коллекции по ID");
        this.collectionManager = collectionManager;
            }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object commandObjectArgument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Integer id = Integer.valueOf(argument);
            StudyGroup oldStudy = collectionManager.getById(id);
            if (oldStudy == null) throw new GroupNotFoundException();

            GroupRaw groupRaw = (GroupRaw) commandObjectArgument;
            String name = groupRaw.getName() == null ? oldStudy.getName() : groupRaw.getName();
            Coordinates coordinates = groupRaw.getCoordinates() == null? oldStudy.getCoordinates() : groupRaw.getCoordinates();
            Date creationDate = oldStudy.getCreationDate();
            Long studentsCount = groupRaw.getStudentsCount() == -1 ? oldStudy.getStudentsCount() : groupRaw.getStudentsCount();
            int transferredStudentsCount = groupRaw.getTransferredStudents() == -1 ? oldStudy.getTransferredStudents() : groupRaw.getTransferredStudents();
            FormOfEducation formOfEducation = groupRaw.getFormOfEducation() == null ? oldStudy.getFormOfEducation() : groupRaw.getFormOfEducation();
            Semester semester = groupRaw.getSemester() == null ? oldStudy.getSemesterEnum() : groupRaw.getSemester();
            Person groupAdmin = groupRaw.getGroupAdmin() == null ? oldStudy.getGroupAdmin() : groupRaw.getGroupAdmin();

            collectionManager.removeToCollection(oldStudy);
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
            ResponseOutputer.appendln("Группа успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appenderror("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен числом!");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("Групп с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен.");
        }
        return false;
    }
}
