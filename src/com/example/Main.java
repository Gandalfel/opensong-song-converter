package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            convertCreateFile("C:\\Users\\quack\\Desktop\\Songs\\będę cię wielbił", "C:\\Users\\quack\\Desktop\\Songs_destination\\będę cię wielbił");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertCreateFile(String target, String destination) throws IOException {
        StringBuilder wholeText = new StringBuilder();
        String lines = "";

        try {
            File myObj = new File(target);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                wholeText.append(data).append("\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        lines = wholeText.substring(wholeText.indexOf("<lyrics>")+8, wholeText.indexOf("</lyrics>"));
        System.out.println(lines);

        String[] linesX = lines.split("\\r?\\n");
        List<String> withMarker = Arrays.asList(linesX);
        List<String> withoutMarker = new ArrayList();
        List<String> textFinal = new ArrayList();

        for (String line : withMarker) {
            if (!line.contains("[")) {
                withoutMarker.add(line);
            }
        }

        int verseCount = 1;
        int linesCount = 0;
        textFinal.add("[v1]"); verseCount++;
        for (String line : withoutMarker) {
            if (linesCount < 2) {
                textFinal.add(line);
                linesCount++;
            } else if (linesCount == 2) {
                textFinal.add("[v" + verseCount + "]");
                textFinal.add(line);
                verseCount++;
                linesCount = 1;
            }
        }

        StringBuilder finalTextStringSB = new StringBuilder();
        for (String line : textFinal) {
            finalTextStringSB.append(line+="\n");
        }
        String finalTextString = String.valueOf(finalTextStringSB);

        System.out.println(finalTextString);

        wholeText.replace(wholeText.indexOf("<lyrics>")+8, wholeText.indexOf("</lyrics>"), finalTextString);
        System.out.println(wholeText);

        FileWriter finishFile = new FileWriter(destination);
        finishFile.write(wholeText.toString());
        finishFile.flush();
        finishFile.close();
    }
}
