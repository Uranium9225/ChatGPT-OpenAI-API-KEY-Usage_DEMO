package controls;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ChatGPTAPIExample {

	public static String chatGPT(String prompt) {
		String url = "https://api.openai.com/v1/chat/completions";
		String apiKey = "YOUR-API-KEY-GOES-HERE";
		String model = "gpt-3.5-turbo";

		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "Bearer " + apiKey);
			connection.setRequestProperty("Content-Type", "application/json");

			// The request body
			String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt
					+ "\"}]}";
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();

			// Response from ChatGPT
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			StringBuffer response = new StringBuffer();

			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();

			// calls the method to extract the message.
			return extractMessageFromJSONResponse(response.toString());

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String extractMessageFromJSONResponse(String response) {
		int start = response.indexOf("content") + 11;

		int end = response.indexOf("\"", start);

		return response.substring(start, end);

	}

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		String prompt;

		System.out.println("Ask :\n\n");
		System.out.println(chatGPT(prompt = scan.next()));
	}
}