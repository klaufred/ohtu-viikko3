package ohtu;

public class Course {
    private String name;
    private int week;
    private String term;
    private int year;
    private String fullName;
    private int[] exercises;

    public void setName(String name) {
        this.name = name;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;    
    }  

    public void setExercises(int[] ex) {
        this.exercises = ex;
    }

    public String getTerm() {
        return this.term;
    }
    
    public int[] getExercises() {
        return this.exercises;
    }

    public String getName() {
        return this.name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public int getWeek(){
        return this.week;
    }

    public int getYear() {
        return this.year;
    }

    public int total(){
        int total = 0;
        for (int x = 0; x < week; x++) {
            total += this.exercises[x];
        }
        return total;
    }

    @Override
    public String toString() {
        return this.fullName + " " + this.term + " " + this.year + "\n";
    }
}