package dev.ulman.appEngine;

import dev.ulman.exceptions.FileNotSupportExceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder implements IFileFinder {

    public File findFileByPath(String pathToFile) throws FileNotSupportExceptions, FileNotFoundException {
        if (isSVGFile(pathToFile)){
            File file = new File(pathToFile);
            if (!file.exists()) throw new FileNotFoundException("This file not exist");
            return file;
        } else {
            throw new FileNotSupportExceptions();
        }
    }

    public List<File> findFilesByDirectory() throws FileNotFoundException {
        File folder = new File("");
        try {
            folder = new File(System.getProperty("user.dir"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return analyzeFolder(folder.listFiles());
    }

    public List<File> findFilesByDirectory(String pathToFileFolder) throws FileNotFoundException {
        File folder = new File(pathToFileFolder);
        if (!folder.exists()) throw new FileNotFoundException("This directory does not exist");
        return analyzeFolder(folder.listFiles());
    }

    private List<File> analyzeFolder(File[] fileList) throws FileNotFoundException {
        if (fileList == null || fileList.length == 0) {
            throw new FileNotFoundException("No files in folder.");
        }

        List<File> filesList = Arrays.asList(fileList).stream()
                .filter(item -> !item.isDirectory())
                .filter(item -> isSVGFile(item.getAbsolutePath()))
                .collect(Collectors.toList());
        if (filesList.size() == 0) throw new FileNotFoundException("No SVG files in folder.");
        return filesList;
    }

    private boolean isSVGFile(String pathToFile) {
        try {
            String fileExtension = pathToFile.substring(pathToFile.length() - 3).toLowerCase();
            return fileExtension.equals("svg");
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}