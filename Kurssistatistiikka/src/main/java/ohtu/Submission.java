package ohtu;

public class Submission {
    private int week;
    private int hours;
    private String course;
    private int[] exercises;
    private Course courseObject;
    
    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return this.hours;
    }

    public String getCourse() {
        return this.course;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }

    public String getExercises() {
        String exercisesAsString = "";
        if (this.exercises!=null)  {
            for (int x = 0; x < exercises.length; x++) {
                exercisesAsString += exercises[x] + ",";
            }
        }
        return exercisesAsString;
    }

    public int getExercisesSum() {
        int exercisesSum = 0;
        if (this.exercises != null) {
            for (int x = 0; x < exercises.length; x++) {
                exercisesSum++;
            }
        }
        return exercisesSum;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public int getWeek() {
        return this.week;
    }
    
    public void setWeek(int week) {
        this.week = week;
    }

    public void setCourseObject(Course course) {
        this.courseObject = course;
    }

    public Course getCourseObject() {
        return this.courseObject;
    }

    @Override
    public String toString() {
        int[] allExOFCourse = this.courseObject.getExercises();
    	String info = "viikko " + this.week + "\n" + 
        " tehtäviä yhteensä " + this.getExercisesSum() + "/" + allExOFCourse[week] + " aikaa kului " + this.hours + " tehdyt tehtävät : ";
        if (this.exercises != null) {
            for (int teht : this.exercises) {
                info += teht + " "; 
            }
        }
        return info;
    }
    
}