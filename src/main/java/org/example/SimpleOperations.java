package org.example;

public class SimpleOperations {
    public static final double EPSILON = 0.0001;

    private static double phiFunction(double x) {
        return Math.sqrt(0.5*(Math.pow(x, 3) + x + 1));
    }

    private static double function(double x) {
        return Math.pow(x, 3) - 2*Math.pow(x, 2) + x + 1;
    }

    public static void main(String[] args) {
        double x0 = -0.25;
        double x1;
        double x0dummy;

        System.out.println("Знайти корень рівняння x^3 - 2x^2 + x + 1 = 0 " +
                "на інтервалі [-0.5;0] при eps=" + EPSILON);
        System.out.println();

        int iterationNumber = 0;
        do {
            x1 = phiFunction(x0);
            x0dummy = x0;
            x0 = x1;
            iterationNumber++;
            System.out.println("Номер ітерації: " + iterationNumber + "\t X: " + x1 + " |X("+iterationNumber+") - X("+(iterationNumber-1)+")|: " + Math.abs(x1 - x0dummy));
        } while (Math.abs(x1 - x0dummy) > EPSILON);

        System.out.println();
        System.out.println("Результат обчислення:");
        System.out.println("x = " + x1 + " f(x) = " + function(x1));
    }
}
