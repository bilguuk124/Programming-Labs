package Lab5.commands;

import Lab5.data.*;
import Lab5.data.Semester;
import Lab5.exceptions.CollectionIsEmptyException;
import Lab5.exceptions.WrongAmountOfElementsException;
import Lab5.utility.CollectionManager;
import Lab5.utility.Console;

/**
 * Command 'filter_by_education_form'. Filters the collection by form of education type.
 */
public class FilterByFormOfEducationCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public FilterByFormOfEducationCommand(CollectionManager collectionManager) {
        super("filter_by_education_form <form>", "вывести элементы, значение поля educationForm которых равно заданному");
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
            FormOfEducation formOfEducation = FormOfEducation.valueOf(argument.toUpperCase());
            String filteredInfo = collectionManager.FormOfEducationFilteredInfo(formOfEducation);
            if (!filteredInfo.isEmpty()) {
                Console.println(filteredInfo);
                return true;
            } else Console.println("В коллекции нет групп с выбранным формой обучения!");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (IllegalArgumentException exception) {
            Console.printerror("Форм обучения нет в списке!");
            Console.println("Список форм обучения - " + Semester.nameList());
        }
        return false;
    }
}
