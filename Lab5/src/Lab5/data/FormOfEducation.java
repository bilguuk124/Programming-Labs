package Lab5.data;

/**
 * Enumeration with form of education constants
 */
public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    /**
     * Generates a list of enum string values.
     * @return String with all enum values split by a comma
     */
    public static String nameList(){
        String nameList = " \n Form of education: \n";

        for(FormOfEducation formOfEducation : values()){
            nameList += formOfEducation.name()+", ";
        }
        nameList=nameList.substring(0,nameList.length()-2);
        nameList+=" \n ";

        return nameList;
    }
}


