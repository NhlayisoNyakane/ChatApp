

package rmquickchat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

public class Message {

    public enum Status {
        SENT,
        DISCARDED,
        STORED
    }

    private static final SecureRandom RANDOM = new SecureRandom();

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    private Status status;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.status = Status.DISCARDED;
    }

    // Generate a unique 10-digit message ID
    private String generateMessageID() {
        long number = 1_000_000_000L
                + RANDOM.nextLong(9_000_000_000L);

        return String.valueOf(number);
    }

    // Create the message hash
    private String createMessageHash() {
        String text = messageText.trim();

        String[] words = text.isEmpty()
                ? new String[]{"EMPTY"}
                : text.split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

  return (messageID.substring(0, 2)
        + ":" + messageNumber
        + ":" + firstWord
        + lastWord).toUpperCase();
    }

    // Display message options using the console
    // This method works with your current ChatApp.
public String sendMessageViaConsole() {
    Scanner scanner = new Scanner(System.in);
    return sendMessageViaConsole(scanner);
}
  

    // Display message options using an existing Scanner

public String sendMessageViaConsole(Scanner scanner) {

    while (true) {

        System.out.println();
        System.out.println("Choose an option:");
        System.out.println("1. Send Message");
        System.out.println("2. Discard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter your choice: ");

        String choice;

        try {
            choice = scanner.nextLine().trim();
        } catch (NullPointerException e) {
            return "Error: Scanner was not initialized.";
        }

        switch (choice) {

            case "1":
                status = Status.SENT;
                return "Message successfully sent.";

            case "2":
                status = Status.DISCARDED;
                return "Press 0 to delete the message.";

            case "3":
                status = Status.STORED;
                return "Message successfully stored.";

            default:
                System.out.println(
                        "Invalid option. Please choose 1, 2, or 3."
                );
        }
    }
}

   

    // Getters
    public Status getStatus() {
        return status;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    // Display message details
    public String printMessageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText
                + "\nStatus: " + status;
    }

    // Count successfully sent messages
    public static int returnTotalMessages(List<Message> messages) {
        int total = 0;

        for (Message message : messages) {
            if (message.getStatus() == Status.SENT) {
                total++;
            }
        }

        return total;
    }

    // Save messages to a JSON file
    public static void storeMessagesToJson(
            List<Message> messages, String fileName) throws IOException {

        Path path = Paths.get(fileName);
        Path parent = path.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        StringBuilder json = new StringBuilder("[");

        // Preserve existing JSON messages
        if (Files.exists(path)) {
            String existing = Files.readString(path).trim();

            if (existing.startsWith("[")
                    && existing.endsWith("]")) {

                String contents = existing.substring(
                        1, existing.length() - 1
                ).trim();

                if (!contents.isEmpty()) {
                    json.append(contents).append(",");
                }
            }
        }

        // Add new messages
        boolean first = json.length() == 1;

        for (Message message : messages) {

            if (!first) {
                json.append(",");
            }

            json.append("{")
                    .append("\"messageID\":\"")
                    .append(escapeJson(message.getMessageID()))
                    .append("\",")
                    .append("\"messageNumber\":")
                    .append(message.getMessageNumber())
                    .append(",")
                    .append("\"messageHash\":\"")
                    .append(escapeJson(message.getMessageHash()))
                    .append("\",")
                    .append("\"recipient\":\"")
                    .append(escapeJson(message.getRecipient()))
                    .append("\",")
                    .append("\"message\":\"")
                    .append(escapeJson(message.getMessageText()))
                    .append("\",")
                    .append("\"status\":\"")
                    .append(message.getStatus())
                    .append("\"")
                    .append("}");

            first = false;
        }

        json.append("]");

        Files.writeString(path, json.toString());
    }

    // Escape special characters in JSON
    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
  
    }
    
    
    
    // Validate message length
    public String validateMessageLength() {
        int length = messageText.length();

        if (length <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (length - 250)
                + "; please reduce the size.";
    }

    // Validate recipient cellphone number
    public String validateRecipient() {
        if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does "
                + "not contain an international code. Please correct "
                + "the number and try again.";
    }

    // Generate a predictable hash for a supplied test ID
    public Message(String messageID, int messageNumber,
                   String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.status = Status.DISCARDED;
    }

}

