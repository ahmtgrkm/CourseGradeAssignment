/**
 * @Author Ahmet Gorkem Durum
 * @Since 15.01.2021
 */

import java.util.Scanner;
import java.io.*;

public class CourseGrade_20190808046 {


    public static int countCategory(String filename) throws IOException {

        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filename));
        try {
            lineNumberReader.skip(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int lines = lineNumberReader.getLineNumber();
        lineNumberReader.close();
        return lines;


    }

    public static void getCategory(String[] category, int[] quantity, int[] weight, String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner input = new Scanner(file);
        int i = 0;
        while (input.hasNext()) {
            String categoryN = input.next();
            category[i] = categoryN;
            int quantityN = input.nextInt();
            quantity[i] = quantityN;
            int weightN = input.nextInt();
            weight[i] = weightN;
            i++;
        }
    }

    public static void writeGrades(String[] students, double[] grade, String basefile) throws IOException {
        PrintWriter output = new PrintWriter(basefile + "_StudentGrades.txt");
        PrintWriter logout = new PrintWriter(basefile + "_log.txt");
        for (int i = 0; i < grade.length; i++) {
            if (grade[i] < 0) {
                logout.println("ERROR: Student " + students[i] + " - cannot calculate"
                        + " due to invalid grade entered");
            } else {
                output.println(students[i] + " " + grade[i] + " " + gradeLetter(grade[i]) + " "
                        + gpaPoints(grade[i]) + " " + status(grade[i]));
            }
        }
        logout.close();
        output.close();
    }

    public static void validQuantity(int[] quantity, File file) throws IOException {
        boolean quantitystatus;
        PrintWriter output = new PrintWriter(file);

        for (int i = 0; i < quantity.length; i++) {
            if (quantity[i] < 0) {
                output.println("ERROR: Course details - invalid quantity - negative quantity");
                output.close();
                System.exit(1);
            } else {
                quantitystatus = true;
            }
        }
    }

    public static void validWeight(int[] weight, File file) throws IOException {
        PrintWriter logout = new PrintWriter(file);
        int vW1 = 0;
        int totalWeight = 0;
        for (int i = 0; i < weight.length; i++) {
            totalWeight += weight[i];
        }

        for (int i = 0; i < weight.length; i++) {
            if (weight[i] < 0) {
                logout.println("ERROR: Course details - invalid weight - negative weight");
                logout.close();
                System.exit(1);
            } else if (totalWeight > 100) {
                logout.println("ERROR: Course details - invalid weight - does not sum to 100");
                logout.close();
                System.exit(1);
            } else if (totalWeight < 100) {
                logout.println("ERROR: Course details - invalid weight - does not sum to 100");
                logout.close();
                System.exit(1);
            } else vW1 = 0;
        }

    }

    public static String gradeLetter(double grade) {
        String gL;
        if (grade >= 88) {
            gL = "AA";
        } else if (grade >= 81) {
            gL = "BA";
        } else if (grade >= 74) {
            gL = "BB";
        } else if (grade >= 67) {
            gL = "CB";
        } else if (grade >= 60) {
            gL = "CC";
        } else if (grade >= 53) {
            gL = "DC";
        } else if (grade >= 46) {
            gL = "DD";
        } else if (grade >= 35) {
            gL = "FD";
        } else {
            gL = "FF";
        }
        return gL;
    }

    public static String status(double grade) {
        String result;
        if (grade >= 60 && grade <= 100) {
            result = "Passed";
        } else if (grade >= 46 && grade <= 59) {
            result = "Conditionally Passed";
        } else {
            result = "Failed";
        }
        return result;
    }

    public static double gpaPoints(double grade) {
        double gP;
        if (grade >= 88) {
            gP = 4.0;
        } else if (grade >= 81) {
            gP = 3.5;
        } else if (grade >= 74) {
            gP = 3.0;
        } else if (grade >= 67) {
            gP = 2.5;
        } else if (grade >= 60) {
            gP = 2.0;
        } else if (grade >= 53) {
            gP = 1.5;
        } else if (grade >= 46) {
            gP = 1.0;
        } else if (grade >= 35) {
            gP = 0.5;
        } else {
            gP = 0.0;
        }
        return gP;


    }

    public static void main(String[] args) throws Exception {
        File category = new File(args[0] + "_CourseDetails.txt");
        File notes = new File(args[0] + "_StudentScores.txt");
        File write = new File(args[0] + "_StudentGrades.txt");
        File log = new File(args[0] + "_log.txt");
        PrintWriter logout = new PrintWriter(log);
        int first = countCategory(args[0] + "_CourseDetails.txt");
        Scanner inNotes = new Scanner(notes);
        Scanner inCategory = new Scanner(category);
        String[] name = new String[first];
        int[] weight = new int[first];
        int[] quantity = new int[first];
        int noteQ = 0;
        getCategory(name, quantity, weight, args[0] + "_CourseDetails.txt");
        validQuantity(quantity, log);
        validWeight(weight, log);
        int students = countCategory(args[0] + "_StudentScores.txt") + 1;
        double minorAverages = 0;
        double[] majorAverages = new double[students];
        double sum = 0;
        String[] student = new String[students];

        for (int i = 0; i < students; i++) {
            student[i] = inNotes.next();
            double major = 0;
            for (int su = 0; su < first; su++) {
                double[] notesArr = new double[quantity[su]];
                sum = 0;
                minorAverages = 0;
                for (int a = 0; a < quantity[su]; a++) {
                    notesArr[a] = inNotes.nextDouble();
                    sum += notesArr[a];
                    if (notesArr[a] < 0) {
                        majorAverages[i] = -1;
                    }
                }
                minorAverages = sum / quantity[su] * weight[su] / 100;
                major += minorAverages;
            }

            if (majorAverages[i] == 0) {
                majorAverages[i] = major;
            }

            writeGrades(student, majorAverages, args[0]);


        }

    }
}





