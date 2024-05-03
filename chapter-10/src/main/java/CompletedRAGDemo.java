import client.GPTClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.similarity.CosineDistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompletedRAGDemo {

    // Load files from the resources folder into a list of strings
    public static List<String> loadFilesFromResources(String folderPath) throws IOException {
        List<String> fileContents = new ArrayList<>();
        ClassLoader classLoader = CompletedRAGDemo.class.getClassLoader();
        File folder = new File(classLoader.getResource(folderPath).getFile());

        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                String fileContent = FileUtils.readFileToString(file, "UTF-8");
                fileContents.add(fileContent);
            }
        }

        return fileContents;
    }

    // Find the closest match in the list using cosine distance
    public static String findClosestMatch(List<String> list, String query) {
        String closestMatch = null;
        double minDistance = Double.MAX_VALUE;
        CosineDistance cosineDistance = new CosineDistance();

        for (String item : list) {
            double distance = cosineDistance.apply(item, query);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = item;
            }
        }

        return closestMatch;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("What would you like help with?");
        Scanner in = new Scanner(System.in);
        String userInput = in.nextLine();

        // Load files from the resources folder
        List<String> corpus = loadFilesFromResources("data");

        // Define user story and input
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

        // Find the closest match to the user input in the loaded files
        String closestMatch = findClosestMatch(corpus, userInput);

        // Replace placeholders in the user story
        prompt = prompt.replace("{relevant_document}", closestMatch)
                .replace("{user_input}", userInput);

        System.out.println("Created prompt");
        System.out.println(prompt);

        // Perform GPT prompt using the constructed prompt
        GPTClient gptClient = new GPTClient(System.getenv("OPEN_AI_KEY"));
        String response = gptClient.prompt(prompt);
        System.out.println("Response received:");
        System.out.println(response);
    }
}
