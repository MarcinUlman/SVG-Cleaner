package dev.ulman.appEngine;

import java.io.File;
import java.util.List;

public interface IFileEditor {

    void setSaveOriginalFile(boolean saveOriginalFile);

    void clearFile(File file);

    void clearListOfFiles(List<File> listOfFiles);

}
