package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.abs;


@Getter
@Setter
@AllArgsConstructor
class JacobiVector {
    private double x1;
    private double x2;
    private double x3;
    private double x4;

    public static JacobiVector createNextIterationVector(JacobiVector x0) {
        double val1 = 0.142*(-2*x0.x2 - x0.x3 - 3*x0.x4 + 1);
        double val2 = 0.125*(-2*x0.x1 - 2*x0.x3 - x0.x4 + 1.5);
        double val3 = 0.166*(-x0.x1 - 2*x0.x2 - x0.x4 + 2.5);
        double val4 = 0.166*(-3*x0.x1 - x0.x2 - x0.x3 + 1);
        return new JacobiVector(val1, val2, val3, val4);
    }

    public static JacobiVector subtraction(JacobiVector x1, JacobiVector x0) {
        return new JacobiVector(
                x1.x1 - x0.x1,
                x1.x2 - x0.x2,
                x1.x3 - x0.x3,
                x1.x4 - x0.x4
        );
    }

    public static double norm(JacobiVector subtracted) {
        return Math.max(Math.max(abs(subtracted.x1), abs(subtracted.x2)),
                Math.max(abs(subtracted.x3), abs(subtracted.x4)));
    }
}

public class Jacobi {
    public static final double EPSILON = 0.0001;

    public static void main(String[] args) {
        System.out.println("""
        Знайти наближено розв'язок системи методом Якобі поданій у матричному вигляді
        7 2 1 3 | 1
        2 8 2 1 | 1.5
        1 2 6 1 | 2.5
        3 1 1 6 | 1
        
        з точністю eps = """ + EPSILON);
        System.out.println();

        JacobiVector x0 = new JacobiVector(0, 0, 0, 0);
        JacobiVector x1;
        JacobiVector x0dummy;
        JacobiVector subtracted;
        int iterationNumber = 0;
        do {
            x1 = JacobiVector.createNextIterationVector(x0);
            x0dummy = x0;
            x0 = x1;
            subtracted = JacobiVector.subtraction(x1, x0dummy);
            iterationNumber++;
            System.out.println("Номер ітерації: " + iterationNumber + " X: x1: "
                    + x1.getX1() + " x2: " + x1.getX2() + " x3: " + x1.getX3() + " x4: " + x1.getX4()
                    + " |X("+iterationNumber+") - X("+(iterationNumber-1)+")|: " + JacobiVector.norm(subtracted));
        } while (JacobiVector.norm(subtracted) > EPSILON);

        System.out.println();
        System.out.println("Результат обчислення:");
        System.out.println("x1 = " + x1.getX1());
        System.out.println("x2 = " + x1.getX2());
        System.out.println("x3 = " + x1.getX3());
        System.out.println("x4 = " + x1.getX4());
    }
}
