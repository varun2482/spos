import java.io.*;
import java.util.*;

class Macro {
    String name;
    ArrayList<String> arguments;
    ArrayList<String> definition;

    public Macro(String name, ArrayList<String> arguments, ArrayList<String> definition) {
        this.name = name;
        this.arguments = arguments;
        this.definition = definition;
    }
}

public class TwoPassMacroprocessor {
    static ArrayList<Macro> macroTable = new ArrayList<>();
    static ArrayList<String> MDT = new ArrayList<>();
    static ArrayList<String> MNT = new ArrayList<>();
    static ArrayList<String> intermediateCode = new ArrayList<>();

    public static void main(String[] args) {
        String[] macroDefinition = {
            "MACRO",
            "ADD MACRO &ARG1,&ARG2",
            "MOVER A,&ARG1",
            "ADDM A,&ARG2",
            "MEND",
            "ADD X,Y"
        };

        passOne(macroDefinition);
        System.out.println("MDT:");
        for (String line : MDT) {
            System.out.println(line);
        }

        System.out.println("\nMNT:");
        for (String line : MNT) {
            System.out.println(line);
        }

        System.out.println("\nIntermediate Code:");
        for (String line : intermediateCode) {
            System.out.println(line);
        }
    }

    public static void passOne(String[] macroDefinition) {
        String currentMacro = null;
        ArrayList<String> currentDefinition = null;

        for (String line : macroDefinition) {
            if (line.equals("MACRO")) {
                currentDefinition = new ArrayList<>();
            } else if (line.startsWith("MEND")) {
                Macro macro = new Macro(currentMacro, parseArguments(currentMacro), currentDefinition);
                macroTable.add(macro);
                MNT.add(currentMacro);
                currentMacro = null;
                currentDefinition = null;
            } else if (currentDefinition != null) {
                currentDefinition.add(line);
                MDT.add(line);
            } else {
                currentMacro = line.substring(0, line.indexOf(" "));
                MNT.add(currentMacro);
                currentDefinition = new ArrayList<>();
                MDT.add(line);
            }
        }
    }

    public static ArrayList<String> parseArguments(String macroName) {
        for (Macro macro : macroTable) {
            if (macro.name.equals(macroName)) {
                return macro.arguments;
            }
        }
        return null;
    }
}
