package server.utility;

import Lab5.common.data.*;
import Lab5.common.utility.Outputer;
import sun.reflect.generics.tree.Tree;


import java.time.LocalDateTime;
import java.util.*;

/**
 * Operates the collection.
 */
public class CollectionManager {
    private CachedLinkedHashSet<StudyGroup> studyGroupsCollection = new CachedLinkedHashSet<StudyGroup>();
    private Iterator<StudyGroup> itr = studyGroupsCollection.iterator();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private CollectionFileManager fileManager;
    private StudyGroup curr=null;


    public CollectionManager(CollectionFileManager fileManager) {
        this.lastInitTime = null;
        this.lastInitTime = null;
        this.fileManager = fileManager;
                  loadCollection();
    }

    /**
     *
      * @return The collection itself.
     */

    public LinkedHashSet<StudyGroup> getStudyGroupsCollection(){
        return studyGroupsCollection;
    }

    public TreeSet<StudyGroup> getSortedSet(){
        TreeSet<StudyGroup> sortedList = new TreeSet<>(new MyCoordinateComparator());
        sortedList.addAll(studyGroupsCollection);
        return sortedList;
    }

    /**
     *
     * @return last initialization time or null if there wasn't initialization.
     */

    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }

    /**
     *
     * @return last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }

    /**
     *
     * @return Name of the collection's type.
     */
    public String collectionType(){
        return studyGroupsCollection.getClass().getName();
    }

    /**
     *
     * @return Size of the collection.
     */
    public int collectionSize(){
        return studyGroupsCollection.size();
    }

    /**
     *
     * @return first element of the collection or null if collection is empty.
     */
    public StudyGroup getFirst() {
        if(studyGroupsCollection.isEmpty()) return null;
        if(itr.hasNext()){
            return itr.next();
        } else return null;
    }

    /**
     *
     * @param id of the group.
     * @return A group by his ID or null if group isn't found.
     */
    public StudyGroup getById(Integer id){
        for(StudyGroup group : studyGroupsCollection) {
            if(group.getId().equals(id)) return group;
        }
        return null;
    }

    /**
     *
     * @param groupToFind A group who's value will ve found.
     * @return A group by it's value or null if group does not exist.
     */
    public StudyGroup getByValue(StudyGroup groupToFind){
        for(StudyGroup group : studyGroupsCollection){
            if(group.equals(groupToFind)) return group;
        }
        return null;
    }



    /**
     *
     * @param semester A string to find group with same semesterEnum.
     * @return A group found by it's semester or null if group is not found.
     */
    public StudyGroup getBySemester(String semester){
        for(StudyGroup group : studyGroupsCollection){
            if(group.getSemesterEnum().toString().equals(semester.toUpperCase(Locale.ROOT))) return group;
        }
        return null;
    }

    /**
     *
     * @return Sum of all transfer students or 0 if collection is empty.
     */
    public int getSumOfTransferStudents(){
        int sumOfTransferStudents = 0;
        for(StudyGroup group : studyGroupsCollection){
            sumOfTransferStudents += group.getTransferredStudents();
        }
        return sumOfTransferStudents;
    }

    /**
     * Method that prints value of transferred_students in descending order.
     */
    public void print_field_descending_transferred_students(){
        TreeSet<StudyGroup> copy =  new TreeSet<StudyGroup>(Collections.reverseOrder(StudyGroup::compareToTransferredStudents));
        ArrayList<Integer> arrayList = new ArrayList<>();
        copy.addAll(studyGroupsCollection);

        for(StudyGroup studyGroup : copy){
            arrayList.add(studyGroup.getTransferredStudents());
        }
        Outputer.println(arrayList.toString().trim());

    }

    /**
     *
     * @param formOfEducationToFilter form of education to filter by.
     * @return Information about group with certain form of education,
     * or empty string, if there's no group with such form of education.
     */

    public String FormOfEducationFilteredInfo(FormOfEducation formOfEducationToFilter){
        String info = "";
        for(StudyGroup group: studyGroupsCollection){
            if(group.getFormOfEducation().equals(formOfEducationToFilter)){
                info += group + "\n\n";
            }
        }
        return info.trim();
    }

    /**
     * Adds a new group to collection.
     * @param group A group to add.
     */
    public void addToCollection(StudyGroup group){
        studyGroupsCollection.add(group);
    }

    /**
     * Removes a new marine to collection
     * @param group A group to remove
     */
    public void removeToCollection(StudyGroup group){
        studyGroupsCollection.remove(group);
    }

    /**
     * Remove groups greater that the selected one
     * @param groupToCompare A  group to compare with.
     */
    public void removeGreater(StudyGroup groupToCompare){
        studyGroupsCollection.removeIf(group -> group.compareTo(groupToCompare)>0);
    }

    /**
     * Removes one group with selected semesterEnum.
     * @param group A group to remove
     */
    public void removeAnyBySemesterEnum(StudyGroup group){
        studyGroupsCollection.remove(group);
    }

    /**
     * Check if user's input string is valid Semester.
     * @param stringToCompare User input.
     * @return true if user input was valid, or false if user input was wrong.
     */
    public boolean semesterContains(String stringToCompare){
        for(Semester semester : Semester.values()){
            if(semester.name().equals(stringToCompare.trim())) return true;
        }
        return false;
    }

    /**
     * Clears the collection
     */
    public void clearCollection(){
        studyGroupsCollection.clear();
    }

    /**
     * Generates the next ID. It will the last one + 1.
     * @return Next ID.
     */
    public Integer generateNextId(){
        if(studyGroupsCollection.isEmpty()) return 1;
        return studyGroupsCollection.getLast().getId()+1;
    }

    /**
     * Saves the collection to file
     */
    public void saveCollection(){
        fileManager.writeCollection(studyGroupsCollection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection(){
        studyGroupsCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

     @Override
     public String toString(){
         if(studyGroupsCollection.isEmpty()) return "Коллекция пуста!";
         StudyGroup last = null;
         while(itr.hasNext()){
             last = itr.next();
         }
         String info = "";
         for(StudyGroup group : getSortedSet()){
             info+= group;
                 if ( group != last) info += "\n\n";
         }
         return info;
     }

}
