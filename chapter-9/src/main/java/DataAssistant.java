// Importing necessary classes from the OpenAI and Langchain4j libraries.
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;

import java.sql.SQLException;
import java.util.Scanner;

public class DataAssistant {

    // Defining an interface that declares a method for sending prompts to the AI service.
    interface DataAssistantService {
        String sendPrompt(String userPrompt);
    }

    public static void main(String[] args) throws SQLException {

        // Creating an instance of the OpenAiChatModel with a builder pattern.
        // Set the API key (replace "ENTER_API_KEY" with a valid one) and specify the model name.
        OpenAiChatModel model = OpenAiChatModel
                .builder()
                .apiKey("ENTER_API_KEY") // API key required for authenticating with OpenAI's API
                .modelName(OpenAiChatModelName.GPT_3_5_TURBO) // Using GPT-3.5-turbo model for chat
                .build();

        // Building the AI service for the DataAssistant using Langchain4j.
        // This links the created model with the service that will interact with it.
        DataAssistantService dataAssistantChat = AiServices.builder(DataAssistantService.class)
                .chatLanguageModel(model) // Assigning the OpenAi model to the service
                .tools(new DataAssistantTools()) // Adding any tools that can be used (must implement this class)
                .build();

        // Starting an infinite loop to keep the program running for user interaction.
        while(true) {
            // Setting up a scanner to capture user input from the console.
            Scanner scanner = new Scanner(System.in);
            System.out.println("What do you need?"); // Prompting the user for input

            // Reading the user input as a string (the query/prompt).
            String query = scanner.nextLine();

            // Sending the prompt to the AI service and getting the response.
            String response = dataAssistantChat.sendPrompt(query);

            // Outputting the AI's response to the console.
            System.out.println(response);
        }
    }

}
