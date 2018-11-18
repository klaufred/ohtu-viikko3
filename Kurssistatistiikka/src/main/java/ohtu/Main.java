package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        JsonParser parser = new JsonParser();

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";
        String infoUrl = "https://studies.cs.helsinki.fi/courses/courseinfo";

        String statistic1 = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String statistic2 = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        
        String ohtuResponse = Request.Get(statistic1).execute().returnContent().asString();
        JsonObject ohtu = parser.parse(ohtuResponse).getAsJsonObject();

        String railsResponse = Request.Get(statistic2).execute().returnContent().asString();
        JsonObject rails = parser.parse(railsResponse).getAsJsonObject();

        String bodyText = Request.Get(url).execute().returnContent().asString();
        String infoText = Request.Get(infoUrl).execute().returnContent().asString();

        System.out.println("json-muotoinen data:");
        System.out.println( bodyText );

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        Course[] courses = mapper.fromJson(infoText, Course[].class);



        for(Course course : courses) {
            for (Submission submission: subs) {
                if (submission.getCourse().equals(course.getName())){
                    submission.setCourseObject(course);
                }
            }
        }   
        
        System.out.println("Opiskelijanumero on " + studentNr);
        for (Course course : courses) {

            int teht = 0;
            int hours = 0;
            int totalEx = 0;

            System.out.println(course);

            for (Submission submission: subs) {
                if (submission.getCourse().equals(course.getName())) {
                    System.out.println(submission);

                    teht += submission.getExercisesSum();
                    hours += submission.getHours();
                    totalEx += course.getExercises()[submission.getWeek()];
                }
            }
            System.out.println("Yhteensä " + teht + "/" + totalEx + " tehtävää ja " + hours + " tuntia");

            if (course.getName().equals("ohtu2018")) {
                System.out.println(printInfo(ohtu,course.getWeek()));
            } else if (course.getName().equals("rails2018")) {
                 System.out.println(printInfo(rails,course.getWeek()));
            }
        }
    }

    //Helpottamaan tulostusta
    private static String printInfo(JsonObject data, int week) {
        int hours = 0;
        int subs = 0;
        int teht = 0;
        
        for (Integer x = 1; x <= week; x++) {
            JsonObject job = data.getAsJsonObject(x.toString());
            hours += job.get("hour_total").getAsInt();
            subs += job.get("students").getAsInt();
            teht += job.get("exercise_total").getAsInt();
        }
        return "Kursilla yhteensä: " +
                + subs + " palautusta, palautettuja tehtäviä "
                + teht + "kpl,"
                + "aikaa käytetty yhteensä " +  hours + " tuntia";
    }
}