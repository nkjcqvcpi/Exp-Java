import java.util.Scanner;

public class grade {
    public static void main(String[] args){
        Scanner reader = new Scanner(System.in);
        float score = reader.nextFloat();
        char level = 'E';
        if (score >= 90) level = 'A';
        else if (score >= 80) level = 'B';
        else if (score >= 70) level = 'C';
        else if (score >= 60) level = 'D';
        System.out.println(level);
    }
}
