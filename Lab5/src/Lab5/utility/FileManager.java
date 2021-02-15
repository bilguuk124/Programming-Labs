package Lab5.utility;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;
import Lab5.data.StudyGroup;
import sun.reflect.generics.tree.Tree;

/**
 * Operates file for saving/loading collection
 */
public class FileManager {
    public Gson gson = new Gson();
    private String envVariable;
    private BufferedOutputStream bfos;
    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    /**
     * Writes collection to file.
     * @param collection Collection to write.
     */
    public void writeCollection(LinkedHashSet<StudyGroup> collection){
        if(envVariable != null){
            try {
                bfos = new BufferedOutputStream(new FileOutputStream(envVariable),1024);
                String a = gson.toJson(collection);
                byte[] saveFile = a.getBytes(StandardCharsets.UTF_8);
                bfos.write(saveFile);
            } catch (IOException e) {
                System.err.println("Загрузочный файл не может быть открыт");
            }
        }
        else Console.printerror("Системная переменная с загрузочным файлом не найдена!");

    }

    /**
     * Reads collection from file.
     * @return Read collection.
     */
    public CachedLinkedHashSet<StudyGroup> readCollection(){
        if(envVariable != null){
            try(Scanner collectionFileScanner = new Scanner(new File(envVariable))){
                CachedLinkedHashSet<StudyGroup> collection;
                Type collectionType = new TypeToken<CachedLinkedHashSet<StudyGroup>>() {}.getType();
                collection = gson.fromJson(collectionFileScanner.nextLine().trim(), collectionType);
                System.out.println("Коллекция успешна загружена");
                return collection;
            } catch (FileNotFoundException e){
                System.err.println("Файл не найдена");
            } catch (NoSuchElementException e){
                System.err.println("Файл пуст");
            } catch (JsonParseException | NullPointerException e){
                System.err.println("Нет  необходимой коллекции");
            } catch (IllegalStateException e){
                System.err.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }else System.err.println("Не найдена");
        return new CachedLinkedHashSet<>();
    }
    @Override
    public String toString(){
        String string = "FileManager ";
        return string;

    }


}
