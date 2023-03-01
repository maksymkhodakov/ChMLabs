package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

class LagrangePolynomial {
    public static void interpolate(List<Double> x, List<Double> y) {
        int n = x.size();
        double[] c = new double[n];
        double[] p = new double[n];

        for (int i = 0; i < n; i++) {
            double product = 1.0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= (x.get(i) - x.get(j));
                }
            }
            c[i] = y.get(i) / product;
        }

        for (int i = 0; i < n; i++) {
            p[i] = c[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    p[i] *= (x.get(0) - x.get(j));
                }
            }
        }

        System.out.print("L(x) = ");
        boolean firstTerm = true;
        for (int i = 0; i < n; i++) {
            if (c[i] != 0) {
                if (!firstTerm) {
                    if (c[i] > 0) {
                        System.out.print(" + ");
                    } else {
                        System.out.print(" - ");
                    }
                }
                if (Math.abs(c[i]) != 1 || i == 0) {
                    System.out.print(Math.abs(c[i]));
                }
                if (i == 0) {
                    System.out.print("x^" + (n - 1));
                } else {
                    System.out.print("x");
                    if (i < n - 1) {
                        System.out.print("^" + (n - i - 1));
                    }
                }
                firstTerm = false;
            }
        }
    }
}



public class Interpolation {

    private static List<Double> chebishovRoots(double a, double b, int n) {
        final List<Double> roots = new ArrayList<>();
        int i = 0;
        for (int k = 0; k <= n; k++) {
            double val = ((a + b)/2) + ((b - a) / 2)*(Math.cos(((2*k + 1)*PI) / (2*(n + 1))));
            roots.add(val);
            System.out.println("x"+i+": " + val);
            i++;
        }
        return roots;
    }

    private static List<Double> f(List<Double> chebishovXi) {
        List<Double> f = new ArrayList<>();
        int i = 0;
        for (var Xi : chebishovXi) {
            double value = Math.pow(Xi, 4) - 5.74 * Math.pow(Xi, 3) + 8.18 * Xi - 3.48 * cos(Xi);
            f.add(value);
            System.out.println("f(x" + i + "): " + value);
            i++;
        }
        return f;
    }

    public static void main(String[] args) {
        final var chebishovXi = chebishovRoots(-2.0, 0.0, 9);
        System.out.println();
        final var FiBasedOnChebishovXi = f(chebishovXi);
        LagrangePolynomial.interpolate(chebishovXi, FiBasedOnChebishovXi);
        System.out.println();
        System.out.println();
        System.out.println();
        LagrangePolynomial.interpolate(FiBasedOnChebishovXi, chebishovXi);
    }
}
