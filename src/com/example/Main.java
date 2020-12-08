package com.example;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        StringBuilder wholeText = new StringBuilder();
        String lines = "";

        try {
            File myObj = new File("C:\\Users\\quack\\Desktop\\Songs\\będę cię wielbił");
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

        for (String line : textFinal) {
            System.out.println(line);
        }
    }
}
