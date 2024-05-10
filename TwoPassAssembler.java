import java.util.HashMap;

public class TwoPassAssembler {
    // Define opcode table
    static HashMap<String, String> opcodeTable = new HashMap<>();
    static {
        opcodeTable.put("LOAD", "01");
        opcodeTable.put("STORE", "02");
        opcodeTable.put("ADD", "03");
        // Add more opcodes as needed
    }

    // Define assembler directives
    static String[] directives = {"START", "END", "RESW", "RESB", "WORD"};

    // Symbol table to store labels and addresses
    static HashMap<String, Integer> symbolTable = new HashMap<>();

    public static void main(String[] args) {
        String[] assemblyCode = {
            "START 1000",
            "LABEL1 LOAD A",
            "STORE B",
            "ADD C",
            "RESW 2",
            "END"
        };

        firstPass(assemblyCode);
        System.out.println("Symbol Table:");
        System.out.println(symbolTable);
    }

    public static void firstPass(String[] assemblyCode) {
        int locationCounter = 0;

        for (String line : assemblyCode) {
            // Split line into label, opcode, operands
            String[] parts = line.split("\\s+");
            String label = parts.length > 1 ? parts[0] : null;
            String opcode = parts.length > 1 ? parts[1] : parts[0];
            String[] operands = parts.length > 2 ? new String[parts.length - 2] : new String[0];
            if (parts.length > 2) {
                System.arraycopy(parts, 2, operands, 0, parts.length - 2);
            }

            // Process label
            if (label != null) {
                symbolTable.put(label, locationCounter);
            }

            // Increment location counter based on opcode and operands
            if (isDirective(opcode)) {
                switch (opcode) {
                    case "RESW":
                        locationCounter += Integer.parseInt(operands[0]) * 3;
                        break;
                    case "RESB":
                        locationCounter += Integer.parseInt(operands[0]);
                        break;
                    case "WORD":
                        locationCounter += 3;
                        break;
                }
            } else if (opcodeTable.containsKey(opcode)) {
                locationCounter += 3;
            }
        }
    }

    public static boolean isDirective(String opcode) {
        for (String directive : directives) {
            if (directive.equals(opcode)) {
                return true;
            }
        }
        return false;
    }
}
