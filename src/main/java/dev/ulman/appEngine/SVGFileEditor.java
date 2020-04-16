package dev.ulman.appEngine;

import java.io.*;
import java.util.List;

public class SVGFileEditor implements IFileEditor {

    private boolean saveOriginalFile = true;

    @Override
    public void setSaveOriginalFile(boolean saveOriginalFile) {
        this.saveOriginalFile = saveOriginalFile;
    }

    @Override
    public void clearFile(File SVGFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SVGFile));
            String headerLines = getHeaderString(reader);
            reader.close();

            File fileToWrite = null;

            if (saveOriginalFile) fileToWrite = creteCopyOfFile(SVGFile);
            else fileToWrite = SVGFile;
            String textToWrite = headerLines;
            writeToFile(fileToWrite, textToWrite);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File creteCopyOfFile (File SVGFile){
        String originalPath = SVGFile.getAbsolutePath();
        int extIndex = originalPath.length() - 4;
        String newPath = originalPath.substring(0, extIndex) + "_cleared" + originalPath.substring(extIndex);
        return new File(newPath);
    }

    private void writeToFile(File SVGFile, String textToWrite) throws IOException {
        FileWriter writer = new FileWriter(SVGFile,false);
        writer.write(textToWrite);
        writer.close();
    }

    private String getHeaderString(BufferedReader reader) throws IOException {
        StringBuilder linesToOut = new StringBuilder();
        String line = "";
        boolean isHeader = false, isPath = false;;
        int tabLength = 0;
        while (line != null) {
            line.toLowerCase();
            if (line.contains("<svg")) isHeader = true;
            if (isHeader && line.endsWith(">")) {
                isHeader = false;
                linesToOut.append(">\n");
            }

            if (isHeader && (
                    line.contains("<svg") || line.contains("xmlns=") || line.contains("viewBox"))) {
                linesToOut.append(line.trim()).append(" ");
            }
            if (line.contains("<path")) {
                isPath = true;
                tabLength = line.indexOf("<path");
                linesToOut.append(line).append("\n");
            }
            if (isPath && line.endsWith("/>")) isPath = false;
            if (isPath &&  line.contains("d=")) linesToOut.append(line).append("\n");

            line = reader.readLine();
        }
        for (int i = 0; i < tabLength; i++) {
            linesToOut.append(" ");
        }
        linesToOut.append("/>\n");
        linesToOut.append("</svg>");
        return linesToOut.toString();
    }

    @Override
    public void clearListOfFiles(List<File> listOfFiles) {
        listOfFiles.stream()
                .forEach(file -> clearFile(file));
    }
}
