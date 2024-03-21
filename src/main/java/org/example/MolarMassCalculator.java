package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MolarMassCalculator {
    private JSONObject molarMasses;

    public MolarMassCalculator() {
        JSONParser parser = new JSONParser();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/molar_masses.json");
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(inputStream);
                Object obj = parser.parse(reader);
                molarMasses = (JSONObject) obj;
            } else {
                System.out.println("Json не найден");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public double calculateMolarMass(String formula) {
        double molarMass = 0;
        int i = 0;
        while (i < formula.length()) {
            char c = formula.charAt(i);
            StringBuilder element = new StringBuilder();
            element.append(c);
            i++;
            while (i < formula.length() && Character.isLowerCase(formula.charAt(i))) {
                element.append(formula.charAt(i));
                i++;
            }
            int count = 1;
            if (i < formula.length() && Character.isDigit(formula.charAt(i))) {
                StringBuilder countStr = new StringBuilder();
                while (i < formula.length() && Character.isDigit(formula.charAt(i))) {
                    countStr.append(formula.charAt(i));
                    i++;
                }
                count = Integer.parseInt(countStr.toString());
            }
            molarMass += getMolarMass(element.toString()) * count;
        }
        return molarMass;
    }

    private double getMolarMass(String element) {
        if (molarMasses.containsKey(element)) {
            return (double) molarMasses.get(element);
        } else {
            System.out.println("Неизвестный элемент " + element);
            return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        MolarMassCalculator calculator = new MolarMassCalculator();
        while(true){
            System.out.println("Введите молекулу:");
            String formula = scanner.nextLine();
            double molarMass = calculator.calculateMolarMass(formula);
            System.out.println("Молярная масса " + formula + " = " + molarMass + " g/mol");
        }
    }
}
