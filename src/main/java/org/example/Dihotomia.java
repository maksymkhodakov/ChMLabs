package org.example;

public class Dihotomia {
    public static final double EPSILON = 0.0001;
    private static double function(double x) {
        return Math.pow(x, 3) - 2*Math.pow(x, 2) + x + 1;
    }
    public static void main(String[] args) {
        double x1;
        double x2;
        double x = 0;
        double middleValue = 0;

        System.out.println("Знайти корень рівняння x^3 - 2x^2 + x + 1 = 0 " +
                "на інтервалі [-0.5;0] при eps=" + EPSILON);
        System.out.println();

        x1 = -0.5;
        x2 = 0;
        int iterationNumber = 0;

        while (Math.abs(x1 - x2) > EPSILON) {
            middleValue = (x1+x2)/2;
            if (function(x1) * function(middleValue) <= 0) {
                x2 = middleValue;
            }  else  {
                x1 = middleValue;
                x = (x1 + x2) / 2;
            }
            iterationNumber++;
            System.out.println("Номер ітерації: " + iterationNumber + "\t X: " + x + " |X("+iterationNumber+") - X("+(iterationNumber-1)+")|: " + Math.abs(x2 - x1));
        }

        System.out.println();
        System.out.println("Результат обчислення:");
        System.out.println("x = " + x + " f(x) = " + function(x));
    }
}