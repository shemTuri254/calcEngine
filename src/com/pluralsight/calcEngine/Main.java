package com.pluralsight.calcEngine;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        declarations of the arrays : these are parallel arrays whereby each value corresponds with each other
    double[] leftVals = {100.0d, 25.0d, 225.0d, 11.0d};
    double[] rightVals = {50.0d, 92.0d, 17.0d, 3.0d};
    char[] opCodes = {'d', 'a', 's', 'm'};
    double[] results = new double[opCodes.length];

    if(args.length == 0) {
        for (int i = 0; i < opCodes.length; i++) {
            results[i] = execute(opCodes[i], leftVals[i], rightVals[i]);

        }
        for (double currentResult : results)
            System.out.println(currentResult);
    }else if (args.length == 1 && args[0].equals("interactive"))
        executeInteractively();
    else if (args.length == 3)
        handleCommandLine(args);
    else
        System.out.println("Please provide an operation code and 2 numeric values");
    }

    static void executeInteractively(){
//        method that enables input of data by the user using the Scanner(system.In)
        System.out.println("Enter an operation and two numbers or date and the number of days to be added or subtracted *when->addition*now->subtraction:");
        Scanner scanner = new Scanner(System.in);
        String userInput  = scanner.nextLine();
        String[] parts = userInput.split(" ");
//        this enables calculations to be performed
        performOperation(parts);
    }

    private static void performOperation(String[] parts) {
//        this method enables calculations to be performed
        char opCode = opCodeFromString(parts[0]);
//        this conditional loop enables calculation of dates where by if the opCode is 'w' the handle when method is called
          if (opCode == 'n')
                handleNow(parts);
       else if (opCode =='w')
            handleWhen(parts);
        else  {
//            this else enable the calculation of input word data
            double leftVal = valueFromWord(parts[1]);
            double rightVal = valueFromWord(parts[2]);
            double result = execute(opCode, leftVal, rightVal);
            displayResult(opCode, leftVal, rightVal, result);
        }

    }

    private static void handleNow(String[] parts) {
        LocalDate startDate = LocalDate.parse(parts[1]);
        long daysToMinus = (long) valueFromWord(parts[2]);
        LocalDate newDate = startDate.minusDays(daysToMinus);
        String output = String.format("%s minus %d days is %s", startDate, daysToMinus, newDate);
        System.out.println(output);
    }

    private static void handleWhen(String[] parts) {
//        method enables the calculation of date
            LocalDate startDate = LocalDate.parse(parts[1]);
            long daysToAdd = (long) valueFromWord(parts[2]);
            LocalDate newDate = startDate.plusDays(daysToAdd);
            String output = String.format("%s plus %d days is %s", startDate, daysToAdd, newDate);
            System.out.println(output);
    }

    private static void displayResult(char opCode, double leftVal, double rightVal, double result) {
            char symbol = symbolFromOpCode(opCode);
//            method used to display results of calculations

            /*
            StringBuilder builder = new StringBuilder(20);
            builder.append(leftVal);
            builder.append(" ");
            builder.append(symbol);
            builder.append(" ");
            builder.append(rightVal);
            builder.append(" = ");
            builder.append(result);
            String output = builder.toString();
            */

            String output = String.format("%.3f %c %.3f = %.3f", leftVal, symbol, rightVal, result);
            System.out.println(output);
    }

    private static void handleCommandLine(String[] args) {
        char opCode = args[0].charAt(0);
        double leftVal = Double.parseDouble(args[1]);
        double rightVal = Double.parseDouble(args[2]);
        double result = execute(opCode, leftVal, rightVal);
        System.out.println(result);

    }

    private static char symbolFromOpCode(char opCode){
//        method that contains parallel arrays that match the opCodes to their appropriate mathematical symbols
        char[] opCodes = {'a', 's', 'm', 'd'};
        char[] symbols = {'+', '-', '*', '/'};
        char symbol = ' ';
//        for loop that checks the opCode for calculations to occur through substitues with the appropriate symbols
        for(int index = 0; index < opCodes.length; index++){
            if(opCode == opCodes[index]){
                symbol = symbols[index];
                break;
            }
        }
        return symbol;
    }
//method allows execution of the opCode
    static double execute(char opCode, double leftVal, double rightVal){
//        execution enable by a switch statement
        double result;
      switch (opCode) {

          case 'a':
              result = leftVal + rightVal;
              break;
          case 's':
              result = leftVal - rightVal;
              break;
          case 'm':
              result = leftVal * rightVal;
              break;
          case 'd':
              result = rightVal != 0 ? leftVal / rightVal : 0.0d;
              break;

          default:
              System.out.println("invalid opCode: " + opCode);
              result = 0.0d;
              break;
      }
      return result;
  }

  static char opCodeFromString(String operationName){
        char opCode =  operationName.charAt(0);
        return opCode;
  }

  static double valueFromWord(String word){
        String[] numberWords = {
                "zero", "one", "two", "three", "four",
                "five", "six", "seven", "eight", "nine"
        };
        double value = -1d;
        for(int index = 0; index < numberWords.length; index++){
            if(word.equals(numberWords[index])) {
                value  = index;
                break;
            }
        }
        if(value == -1d)
            value = Double.parseDouble(word);
        return value;
  }
}
