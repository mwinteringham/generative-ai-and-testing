import dev.langchain4j.model.openai.OpenAiChatModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.similarity.CosineDistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompletedRAGDemo {

    /**
     * This method loads all the files from a specified folder inside the resources directory.
     * It reads the contents of each file as a string and stores it in a list.
     * 
     * @param folderPath The folder path within the resources directory.
     * @return A list of strings, where each string contains the contents of a file.
     * @throws IOException If an I/O error occurs during file reading.
     */
    public static List<String> loadFilesFromResources(String folderPath) throws IOException {
        List<String> fileContents = new ArrayList<>();
        ClassLoader classLoader = CompletedRAGDemo.class.getClassLoader();
        
        // Locate the folder within the resources directory
        File folder = new File(classLoader.getResource(folderPath).getFile());

        // Iterate over all files in the folder and read their contents
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                // Read each file into a string and add it to the list
                String fileContent = FileUtils.readFileToString(file, "UTF-8");
                fileContents.add(fileContent);
            }
        }

        return fileContents;
    }

    /**
     * This method takes a list of strings and a query string, then finds the closest match 
     * from the list using the Cosine Similarity algorithm. The smallest cosine distance 
     * indicates the closest match.
     * 
     * @param list The list of strings to search through.
     * @param query The string to find the closest match for.
     * @return The string from the list that is the closest match to the query.
     */
    public static String findClosestMatch(List<String> list, String query) {
        String closestMatch = null;
        double minDistance = Double.MAX_VALUE;  // Initialize with the maximum possible distance
        CosineDistance cosineDistance = new CosineDistance();

        // Compare the query against each item in the list
        for (String item : list) {
            // Compute the cosine distance between the current item and the query
            double distance = cosineDistance.apply(item, query);
            // Update the closest match if the current item has a smaller distance
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = item;
            }
        }

        return closestMatch;
    }

    public static void main(String[] args) throws Exception {
        // Prompt the user for input
        System.out.println("What would you like help with?");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();  // Capture user input

        // Load files from the "data" folder within the resources directory
        List<String> corpus = loadFilesFromResources("data");

        // Define a prompt template for generating risks based on the user story and input
        String prompt = """
            You are an expert software tester that makes recommendations for testing ideas and risks. You answer with suggested risks to test for based off of the provided user story delimited by three hashes and user input that is delimited by three back ticks.

            Compile a list of suggested risks based off of the user story provided to test for based on the user story and the user input. Cite which part of the user story the risk is based off of. Check that the risk matches the part of the user story before outputting.
            
            ###
            {relevant_document}
            ###
            ```
            {user_input}
            ```
            """;

        // Find the closest matching document in the corpus based on the user input
        String closestMatch = findClosestMatch(corpus, userInput);

        // Replace placeholders in the prompt template with the closest document and user input
        prompt = prompt.replace("{relevant_document}", closestMatch)
                .replace("{user_input}", userInput);

        // Output the constructed prompt to the console
        System.out.println("Created prompt");
        System.out.println(prompt);

        // Initialize the OpenAiChatModel with an API key for GPT model interaction
        OpenAiChatModel model = OpenAiChatModel.withApiKey("enter-api-key");

        // Generate a response from the model based on the constructed prompt
        String response = model.generate(prompt);

        // Display the model's response in the console
        System.out.println("Response received:");
        System.out.println(response);
    }
}
