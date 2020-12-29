package com.company;

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Calculator
{
    String expr;
    private String[] tokens;
    private int pos;
    /*
    1 * 2 - 3 + 4 / 5 + 6 - 7 * 8

    1 + 2 - 3 + 4 - 5 + 6 - 7 + 8
    3 - 3 + 4 - 5 + 6 - 7 + 8
    0 + 4 - 5 + 6 - 7 + 8
    4 - 5 + 6 -7 + 8
    -1 + 6 - 7 + 8
    5 - 7 + 8
    6


     */

    public static void main(String[] args) throws IOException {

        String expr;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        expr = reader.readLine();
        //String expr = "1 * 2 - 3 + 4 / 5 + 6 - 7 * 8";
        Calculator calculator = new Calculator(expr);
        calculator.calculate();

        //System.out.println(/*expr + " = " + */"Ответ = " + calculator.calculate());
        //System.out.println("Check " + (1 + 2.0 * ( 3 - 4 ) + 6 / 7.0));
    }

    public Calculator(String expr) {
        this.tokens = expr.split(" ");
        this.pos = 0;

    }

    public double calculate() {
        double first = multiply();

        while (pos < tokens.length) {
            String operator = tokens[pos];
            if (!operator.equals("+") && !operator.equals("-")) {
                break;
            } else {
                pos++;
            }

            double second = multiply();
            if (operator.equals("+")) {
                first += second;
            } else {
                first -= second;
            }
        }

        if (pos == tokens.length){
            double radians1 = Math.toRadians(first);

            System.out.format("Синус %.1f = %.2f%n",
                    first, Math.sin(radians1));
            System.out.format("Косинус %.1f = %.2f%n",
                    first, Math.cos(radians1));
            System.out.format("Тангенс %.1f = %.2f%n",
                    first, Math.tan(radians1));

            System.out.printf("Значение e = %.2f%n", Math.E);
            System.out.printf("Экспонента(%.2f) = %.2f%n", first, Math.exp(first));
            System.out.printf("Натуральный логорифм = %.2f%n", Math.log(first));
            System.out.printf("Десятичный логорифм = %.2f%n", Math.log10(first));
            System.out.printf("Ответ = %.2f%n", first);

            HashMap<Integer,String> cache = new HashMap<Integer,String>();
            cache.put((int) first, " ");
            System.out.println(first + " ");

//            try(FileWriter writer = new FileWriter("notes3.txt", false))
//            {
//                writer.write("Ответ " + String.valueOf(first));
//                writer.append('\n');
//                writer.write("Синус " + String.valueOf(Math.sin(first)));
//                writer.append('\n');
//                writer.write("Косинус " + String.valueOf(Math.cos(first)));
//                writer.append('\n');
//                writer.write("Тангенс " + String.valueOf(Math.tan(first)));
//                writer.append('\n');
//                writer.write("Экспонента " + String.valueOf(Math.exp(first)));
//                writer.append('\n');
//                writer.write("Натуральный логорифм  " + String.valueOf(Math.log(first)));
//                writer.append('\n');
//                writer.write("Десятичный логорифм " + String.valueOf(Math.log10(first)));
//                // запись по символам
//                writer.append('\n');
//                writer.append("__________");
//
//                writer.flush();
//            }
//            catch(IOException ex){
//
//                System.out.println(ex.getMessage());
//            }
        }
        return first;
    }

    public double multiply() {
        double first = factor();

        while (pos < tokens.length) {
            String operator = tokens[pos];
            if (!operator.equals("*") && !operator.equals("/")) {
                break;
            } else {
                pos++;
            }

            double second = factor();
            if (operator.equals("*")) {
                first *= second;
            } else {
                first /= second;
            }
        }
        return first;
    }

    public double factor() {
        String next = tokens[pos];
        double result;
        if (next.equals("(")){
            pos++;
            result = calculate();

            String closingBracket;
            if (pos < tokens.length){
                closingBracket = tokens[pos];
            }else {
                throw new IllegalArgumentException("Error");
            }
            if (closingBracket.equals(")")){
                pos++;
                return result;
            }
            throw new IllegalArgumentException("')' expected but " + closingBracket + " found");
        }
        pos++;
        return Double.parseDouble(next);
    }
}