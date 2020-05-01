package com.als;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private String[] configContent = new String[4];
    Scanner scanner;

    public FileHandler(int configSize) {
        this.configContent = new String[configSize];
    }

    public FileHandler() {
    }

    public void SaveConfig(int numBugs, int numBirds, int numVeg, int numObs, String fileName) {
        fileName = fileName + ".csv";

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.append(Integer.toString(numBugs));
            writer.append(',');
            writer.append(Integer.toString(numBirds));
            writer.append(',');
            writer.append(Integer.toString(numVeg));
            writer.append(',');
            writer.append(Integer.toString(numObs));
            writer.flush();
            writer.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public String[] GetConfig(String fileName) throws FileNotFoundException {
        fileName = fileName + ".csv";
        this.scanner = new Scanner(new File(fileName));
        this.scanner.useDelimiter(",");

        for(int var2 = 0; this.scanner.hasNext(); this.configContent[var2++] = this.scanner.next()) {
        }

        this.scanner.close();
        return this.configContent;
    }
}

