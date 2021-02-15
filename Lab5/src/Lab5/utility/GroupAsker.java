package Lab5.utility;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Lab5.*;
import Lab5.data.Coordinates;
import Lab5.data.FormOfEducation;
import Lab5.data.Person;
import Lab5.data.Semester;
import Lab5.exceptions.IncorrectInputInScriptException;
import Lab5.exceptions.MustBeNotEmptyException;
import Lab5.exceptions.NotInDeclaredLimitsException;

/**
 * Asks user a group's value.
 */
public class GroupAsker {
    private final int MAX_Y = 262;
    private final long MIN_StudentsCount = 1;
    private final long MAX_StudentsCount = 40;

    private Scanner userScanner;
    private boolean fileMode;

    public GroupAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     * @param userScanner Scanner to set
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     *
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets group asker mode to 'File mode'
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets group asker mode to 'User Mode'
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the group's name.
     * @return Group's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                System.out.println("Введите имя: ");
                System.out.println(Main.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) System.out.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException e) {
                System.err.println("Имя не распознано");
            } catch (MustBeNotEmptyException e) {
                System.err.println("имя не может быть пустым");
            } catch (IllegalStateException e) {
                System.err.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks user the X-coordinate
     * @return the X-coordinate
     * @throws IncorrectInputInScriptException if script was wrong
     */
    public int askX() throws IncorrectInputInScriptException {
        String strX;
        int x;
        while (true) {
            try {
                Console.println("Введите координату х:");
                Console.print(Main.PS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException e) {
                Console.printerror("Координата Х не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printerror("Коодината Х должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException e) {
                Console.printerror("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks user Y-coordinates
     * @return Y-coordinate
     * @throws IncorrectInputInScriptException the script was wrong
     */
    public float askY() throws IncorrectInputInScriptException {
        String strY;
        float y;
        while (true) {
            try {
                Console.println("Введите координату Y < " + (MAX_Y + 1) + ":");
                Console.print(Main.PS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                if (y > MAX_Y) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Координата Y не может превышать " + MAX_Y + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Combines the X-Y coordinate
     * @return Group Coordinates XY
     * @throws IncorrectInputInScriptException if script was wrong
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        int x;
        float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks user the student count of the group
     * @return Student count of the group
     * @throws IncorrectInputInScriptException If script was bas
     */
    public Long askStundentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount;
        Long studentsCount;
        while (true) {
            try {
                Console.println("Введите число студентов: ");
                Console.print(Main.PS2);
                strStudentsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strStudentsCount);
                studentsCount = Long.parseLong(strStudentsCount);
                if (studentsCount < MIN_StudentsCount || studentsCount > MAX_StudentsCount)
                    throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printerror("Не распознаю");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException e) {
                Console.printerror("Число студентов должно быть больше нуля и меньше 40 ");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printerror("Число студентов должна быть число");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException e) {
                Console.printerror("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user the groups transfer students
     * @return Transfer student count
     * @throws IncorrectInputInScriptException if script was bad
     */
    public int askTransferredStundentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount;
        int transferredStudentsCount;
        while (true) {
            try {
                Console.println("Введите число переведённых студентов: ");
                Console.print(Main.PS2);
                strStudentsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strStudentsCount);
                transferredStudentsCount = Integer.parseInt(strStudentsCount);
                if (transferredStudentsCount < MIN_StudentsCount) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                Console.printerror("Не распознаю");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException e) {
                Console.printerror("Число студентов должно быть больше нуля ");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException e) {
                Console.printerror("Число студентов должна быть число");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException e) {
                Console.printerror("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return transferredStudentsCount;
    }

    /**
     * Asks a user in which semester the group is in
     * @return Group's semester.
     * @throws IncorrectInputInScriptException If script was bad.
     */
    public Semester askSemester() throws IncorrectInputInScriptException {
        String strSemester;
        Semester semester;
        while (true) {
            try {
                Console.println("Список семестрa - " + Semester.nameList());
                Console.println("Введите семестр: ");
                Console.print(Main.PS2);
                strSemester = userScanner.nextLine().trim();
                if (fileMode) Console.println(strSemester);
                semester = Semester.valueOf(strSemester.toUpperCase());
                break;
            } catch (NoSuchElementException e) {
                Console.printerror("Семестр не распознана");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException e) {
                Console.printerror("Семестра нет в списке");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException e) {
                Console.printerror("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return semester;
    }

    /**
     * Asks user the groups form of education.
     * @return Group's form of eduction.
     * @throws IncorrectInputInScriptException if script was bad
     */
    public FormOfEducation askFormOfEducation() throws IncorrectInputInScriptException {
        String strFormOfEducation;
        FormOfEducation formOfEducation;
        while (true) {
            try {
                Console.println("Список форма обучения - " + FormOfEducation.nameList());
                Console.println("Введите форму обучения:");
                Console.print(Main.PS2);
                strFormOfEducation = userScanner.nextLine().trim();
                if (fileMode) Console.println(strFormOfEducation);
                formOfEducation = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Оружие не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Оружия нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return formOfEducation;
    }

    /**
     * Asks user the group admin's name
     * @return Group admin's name.
     * @throws IncorrectInputInScriptException If the script was bad.
     */
    public String askGroupAdminsName() throws IncorrectInputInScriptException {
        String groupAdminsName;
        while (true) {
            try {
                Console.println("Введите имя админа:");
                Console.print(Main.PS2);
                groupAdminsName = userScanner.nextLine().trim();
                if (fileMode) Console.println(groupAdminsName);
                if (groupAdminsName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя админа не распознано");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя админа не может быть пустым");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }

        }
        return groupAdminsName;
    }

    /**
     * Asks user group admin's birthday
     * @return Admin's date of birth
     * @throws IncorrectInputInScriptException If script was bad
     */
    public LocalDate askBirthday() throws IncorrectInputInScriptException {
        String strBirthday;
        LocalDate birthday;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            try{
                Console.println("Введите дату рождения админа (дд/мм/гггг): ");
                Console.print(Main.PS2);
                strBirthday = userScanner.nextLine().trim();
                if(fileMode) Console.println(strBirthday);
                birthday = LocalDate.parse(strBirthday,formatter);
                if(strBirthday.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (DateTimeParseException e){

                Console.printerror("Формат даты неверно ");

            } catch (NoSuchElementException e) {
                System.err.println("Дату не распознаю");
            } catch (MustBeNotEmptyException e) {
                System.err.println("Дата не может быть пустым");
            } catch (IllegalStateException e) {
                System.err.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return birthday;
    }

    /**
     * Asks user admin's Passport Id
     * @return Admin's Passport Id
     * @throws IncorrectInputInScriptException If script was bad.
     */
    public String askPassportId() throws IncorrectInputInScriptException {
        String passportId;
        while (true) {
            try {
                Console.println("Введите № паспорта:");
                Console.print(Main.PS2);
                passportId = userScanner.nextLine().trim();
                if (fileMode) Console.println(passportId);
                if (passportId.equals("")) throw new MustBeNotEmptyException();
                if (passportId.length() < 5) throw new NotInDeclaredLimitsException();
                break;
            } catch (NotInDeclaredLimitsException e) {
                Console.printerror("№ паспорта должен иметь больше 5 символов");
            } catch (NoSuchElementException e) {
                Console.printerror("№ паспорта не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException e) {
                Console.printerror("№ паспорта не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException e) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
            return passportId;
    }

    /**
     * Combines information about admin
     * @return Admin
     * @throws IncorrectInputInScriptException if script  was bad
     */
    public Person askGroupAdmin() throws IncorrectInputInScriptException{
        String name;
        LocalDate birthday;
        String passportId;
        name = askGroupAdminsName();
        birthday = askBirthday();
        passportId = askPassportId();
        return new Person(name,birthday,passportId);
}

    /**
     * Asks user a question
     * @param question A question
     * @return (type/false)
     * @throws IncorrectInputInScriptException if script was bad
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(Main.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }




    @Override
    public String toString(){
        return "GroupAsker (вспомогательный класс для запросов пользователю)";
    }

}
