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


class Spline {
    private final List<Double> x;
    private final List<Double> y;
    private final int n;
    private double[] h;
    private double[] b;
    private double[] u;
    private double[] v;
    private double[] z;

    public Spline(List<Double> x, List<Double> y) {
        this.x = x;
        this.y = y;
        this.n = x.size();
        this.h = new double[n - 1];
        this.b = new double[n - 1];
        this.u = new double[n - 1];
        this.v = new double[n - 1];
        this.z = new double[n];
        init();
    }

    private void init() {
        for (int i = 0; i < n - 1; i++) {
            h[i] = x.get(i + 1) - x.get(i);
            b[i] = (y.get(i + 1) - y.get(i)) / h[i];
        }
        u[1] = 2 * (h[0] + h[1]);
        v[1] = 6 * (b[1] - b[0]);
        for (int i = 2; i < n - 1; i++) {
            u[i] = 2 * (h[i - 1] + h[i]) - (h[i - 1] * h[i - 1]) / u[i - 1];
            v[i] = 6 * (b[i] - b[i - 1]) - (h[i - 1] * v[i - 1]) / u[i - 1];
        }
        z[n - 1] = 0;
        for (int i = n - 2; i > 0; i--) {
            z[i] = (v[i] - h[i] * z[i + 1]) / u[i];
        }
        z[0] = 0;
    }

    public void print() {
        System.out.println("Cubic Spline:");
        for (int i = 0; i < n - 1; i++) {
            System.out.printf("Interval [%f, %f]: S(x) = ", x.get(i), x.get(i+1));
            double a = y.get(i);
            double b = (y.get(i + 1) - y.get(i)) / h[i] - h[i] * (z[i + 1] + 2 * z[i]) / 6;
            double c = z[i] / 2;
            double d = (z[i + 1] - z[i]) / (6 * h[i]);
            if (a != 0) {
                System.out.printf("%.2f ", a);
            }
            if (b != 0) {
                if (b > 0) {
                    if (a != 0) {
                        System.out.print("+ ");
                    }
                    System.out.printf("%.2fx ", Math.abs(b));
                } else {
                    System.out.printf("- %.2fx ", Math.abs(b));
                }
            }
            if (c != 0) {
                if (c > 0) {
                    if (a != 0 || b != 0) {
                        System.out.print("+ ");
                    }
                    System.out.printf("%.2fx^2 ", Math.abs(c));
                } else {
                    System.out.printf("- %.2fx^2 ", Math.abs(c));
                }
            }
            if (d != 0) {
                if (d > 0) {
                    if (a != 0 || b != 0 || c != 0) {
                        System.out.print("+ ");
                    }
                    System.out.printf("%.2fx^3", Math.abs(d));
                } else {
                    System.out.printf("- %.2fx^3", Math.abs(d));
                }
            }
            System.out.println();
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
        System.out.println();
        System.out.println();
        final var spline = new Spline(chebishovXi, FiBasedOnChebishovXi);
        spline.print();
    }
}