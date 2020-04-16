package dev.ulman.appEngine;

import dev.ulman.exceptions.FileNotSupportExceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder implements IFileFinder {

    public File findFileByPath(String pathToFile) throws FileNotSupportExceptions {
        if (isSVGFile(pathToFile)){
            return new File(pathToFile);
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
        return analyzeFolder(folder.listFiles());
    }

    private List<File> analyzeFolder(File[] fileList) throws FileNotFoundException {
        if (fileList == null || fileList.length == 0) {
            throw new FileNotFoundException("No files in folder.");
        }

        List<File> filesList = Arrays.asList(fileList).stream()
                .filter(item -> !item.isDirectory())
                .filter(item -> isSVGFile(item.getAbsolutePath()) == true)
                .collect(Collectors.toList());
        if (filesList.size() == 0) throw new FileNotFoundException("No SVG files in folder.");
        return filesList;
    }

    private boolean isSVGFile(String pathToFile) {
        String fileExtension = pathToFile.substring(pathToFile.length() - 3).toLowerCase();
        return fileExtension.equals("svg");
    }
}
