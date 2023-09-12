package com.masai;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class demo {
    public static void main(String[] args) {
        String inputFilePath = "CSv.csv";
        String outputFilePath = "CSv.csv";

        try {
            List<List<String>> inputData = readCSV(inputFilePath);
            List<List<String>> outputData = processCSV(inputData);
            writeCSV(outputData, outputFilePath);
            System.out.println("CSV processed.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

  
    private static List<List<String>> readCSV(String filePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
        }
        return data;
    }

    private static List<List<String>> processCSV(List<List<String>> inputData) {
        List<List<String>> outputData = new ArrayList<>();
        for (List<String> row : inputData) {
            List<String> newRow = new ArrayList<>();
            for (String cell : row) {
                if (cell.startsWith("=")) {
                    String formula = cell.substring(1); 
                    double result = evaluateFormula(formula);
                    newRow.add(String.valueOf(result));
                } else {
                    newRow.add(cell); 
                }
            }
            outputData.add(newRow);
        }
        return outputData;
    }

    private static double evaluateFormula(String formula) {
       
        String[] tokens = formula.split("\\+");
        double sum = 0;
        for (String token : tokens) {
            sum += Double.parseDouble(token);
        }
        return sum;
    }

 
    private static void writeCSV(List<List<String>> data, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (List<String> row : data) {
                String line = String.join(",", row);
                writer.write(line + "\n");
            }
        }
    }
}
