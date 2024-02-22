import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonlExporter {

    public static void exportToJsonL(List<JsonlEntry> entries, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write each JsonLEntry to the JSONL file
            for (JsonlEntry entry : entries) {
                // Start a new JSON object for each entry
                writer.write("{");

                // Write instruction field
                writer.write("\"instruction\": \"" + escape(entry.getInstruction()) + "\",");

                // Write output field
                writer.write("\"output\": \"" + escape(entry.getOutput()) + "\"");

                // End the JSON object
                writer.write("}\n");
            }
        }
    }

    // Helper method to escape special characters in JSON strings
    private static String escape(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
