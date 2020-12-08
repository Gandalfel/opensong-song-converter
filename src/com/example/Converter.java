package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Converter {

    public void convert(String root, String destination) throws IOException {
        List<String> fileNames = gatherFileNames(root);

        //for (String file : fileNames) {
            String text = extractLyrics(readFile(root+"\\"+fileNames.get(1)));
            System.out.println("---INPUT---");
            System.out.println(text);
            System.out.println("---OUTPUT---");

            String[] linesX = text.split("\\r?\\n");
            List<String> lines = Arrays.asList(linesX);
            ArrayList<String> linesConverted = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains("[")) {
                    linesConverted.add(line);
                }
            }
        System.out.println(linesConverted);

        int i = 0;
        int verse = 1;
        StringBuilder finalText = new StringBuilder();
        for (String line : linesConverted) {
            if (i==2) {
                finalText.append("[v").append(verse).append("]").append("\\n");
                finalText.append(line).append("\\n");
                i++;
                continue;
            } else {
                finalText.append(line).append("\\n");
                i++;
            }
        }

        System.out.println("---FINAL OUTPUT---");
        PrintWriter writer = new PrintWriter(new FileWriter(destination + "s"));
        writer.write(String.valueOf(finalText));
        writer.close();

            //break;
        //}

    }

    private String extractLyrics(String content) {
        content = content.substring(content.indexOf("<lyrics>")+8, content.indexOf("</lyrics>"));
        return content;
    }

    private String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    private List<String> gatherFileNames(String path) {
        String[] fileNames;
        File file = new File(path);
        fileNames = file.list();

        return Arrays.asList(fileNames);
    }
}
