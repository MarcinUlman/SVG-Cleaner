package dev.ulman.userInterface;

import dev.ulman.appEngine.SVGFileEditor;
import dev.ulman.appEngine.FileFinder;
import dev.ulman.appEngine.IFileEditor;
import dev.ulman.appEngine.IFileFinder;
import dev.ulman.exceptions.FileNotSupportExceptions;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class CLI {

    private Options options;
    private CommandLineParser parser;
    private HelpFormatter helpFormatter;
    private IFileFinder fileFinder;
    private IFileEditor fileEditor;

    public CLI() {
        this.options = new Options();
        this.parser = new DefaultParser();
        this.helpFormatter = new HelpFormatter();
        this.fileFinder = new FileFinder();
        this.fileEditor = new SVGFileEditor();

        // help
        options.addOption("h", "help", false, "Show help");
        // single file
        Option singleFile = Option.builder("f")
                .longOpt("file")
                .numberOfArgs(1)
                .argName("clear single svg file")
                .desc("Clear single SVG file.")
                .build();
        options.addOption(singleFile);
        Option addProduct = Option.builder("d")
                .longOpt("directory")
                .numberOfArgs(1)
                .optionalArg(true)
                .argName("none> <files directory")
                .desc("Clear all file in JAR location or in <files directory>")
                .build();
        options.addOption(addProduct);
    }

    public void argsParser(String[] args) {
        try {
            CommandLine commandLine = this.parser.parse(this.options, args);
            if (commandLine.hasOption("help")) {
                helpFormatter.printHelp("java -jar svgC.jar [option]\nOptions:", options);
            } else if (commandLine.hasOption("file")) {
                String[] pathToFile = commandLine.getOptionValues("file");
                fileEditor.clearFile(fileFinder.findFileByPath(pathToFile[0]));
            } else if (commandLine.hasOption("directory")) {
                String[] pathToDirectory = commandLine.getOptionValues("directory");
                List<File> listOfFiles;
                if (pathToDirectory == null) listOfFiles = fileFinder.findFilesByDirectory();
                else listOfFiles = fileFinder.findFilesByDirectory(pathToDirectory[0]);
                fileEditor.clearListOfFiles(listOfFiles);
            }
        } catch(MissingArgumentException e){
            System.err.println("Sorry but you miss an argument. Try --help");
        } catch(ParseException e){
            e.printStackTrace();
        } catch (FileNotSupportExceptions fileNotSupportExceptions) {
            fileNotSupportExceptions.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}