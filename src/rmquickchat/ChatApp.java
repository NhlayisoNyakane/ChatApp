
package rmquickchat;

/**
 *
 * @author Student
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ChatApp {


   
    public static void main(String[] args) {

        try(Scanner scanner = new Scanner(System.in)) {
            login login = new login();
            
            // WELCOME SCREEN
            System.out.println("==========================================");
            System.out.println("              RMQuickChat");
            System.out.println("==========================================");
            System.out.println("Where Real Messages Make Real Connections.");
            System.out.println();
            
            // REGISTRATION
            boolean registered = false;
            
            while (!registered) {
                
                System.out.println("==========================================");
                System.out.println("             REGISTRATION");
                System.out.println("==========================================");
                
                System.out.print("Enter a username ");
                System.out.println("(must include '_' and be 5 characters or fewer):");
                String username = scanner.nextLine();
                
                System.out.print("Create a password ");
                System.out.println("(at least 8 characters, uppercase, number and symbol):");
                String password = scanner.nextLine();
                
                System.out.print("Enter your cellphone (e.g. +27838968976): ");
                String cellphone = scanner.nextLine();
                
                String regMessage = login.registerUser(
                        username.trim(),
                        password,
                        cellphone.trim()
                );
                
                System.out.println();
                System.out.println(regMessage);
                System.out.println();
                
                if (regMessage.equals("Registration successful.")) {
                    registered = true;
                }
            }
            
            // LOGIN
            boolean loggedIn = false;
            String loggedUsername = null;
            
            while (!loggedIn) {
                
                System.out.println("==========================================");
                System.out.println("                 LOGIN");
                System.out.println("==========================================");
                
                System.out.print("Username: ");
                String username = scanner.nextLine();
                
                System.out.print("Password: ");
                String password = scanner.nextLine();
                
                boolean loginResult =
                        login.loginUser(username.trim(), password);
                
                String loginMessage =
                        login.returnLoginStatus(
                                loginResult,
                                username.trim()
                        );
                
                System.out.println();
                System.out.println(loginMessage);
                System.out.println();
                
                if (loginResult) {
                    loggedIn = true;
                    loggedUsername = username.trim();
                }
            }

            System.out.println("==========================================");
            System.out.println("Welcome to RMQuickChat, " + loggedUsername + "!");
            System.out.println("==========================================");
            System.out.println();
            
            // MESSAGE COUNT
            int messagesToEnter = 0;
            
            while (messagesToEnter <= 0) {
                
                System.out.print(
                        "How many messages would you like to send today? "
                );
                
                String input = scanner.nextLine();
                
                try {
                    
                    messagesToEnter = Integer.parseInt(input.trim());
                    
                    if (messagesToEnter <= 0) {
                        System.out.println(
                                "Please enter a number greater than zero."
                        );
                    }
                    
                } catch (NumberFormatException e) {
                    
                    System.out.println(
                            "Please enter a valid number."
                    );
                }
            }
            
            List<Message> allMessages = new ArrayList<>();
            
            boolean quit = false;
            
            // MAIN MENU
            while (!quit) {
                
                System.out.println();
                System.out.println("==========================================");
                System.out.println("              RMQuickChat MENU");
                System.out.println("==========================================");
                System.out.println("1. Send new messages");
                System.out.println("2. View recent messages");
                System.out.println("3. Exit");
                System.out.println("==========================================");
                
                System.out.print("Choose an option: ");
                String choice = scanner.nextLine();
                
                switch (choice.trim()) {
                    
                    case "1" -> {
                        if (allMessages.size() >= messagesToEnter) {
                            
                            System.out.println(
                                    "You have already sent your "
                                            + messagesToEnter
                                            + " message(s)."
                            );
                            
                            break;
                        }
                        
                        int remaining =
                                messagesToEnter - allMessages.size();
                        
                        for (int i = 0; i < remaining; i++) {
                            
                            System.out.print(
                                    "Enter recipient for message "
                                            + (i + 1)
                                            + " (include +27): "
                            );
                            
                            String recipient = scanner.nextLine();
                            
                            System.out.print(
                                    "Type your message (maximum 250 characters): "
                            );
                            
                            String messageText = scanner.nextLine();
                            
                            // Message length validation
                            if (messageText.length() > 250) {
                                
                                System.out.println(
                                        "Message too long! "
                                                + "Keep it under 250 characters."
                                );
                                
                                i--;
                                continue;
                            }
                            
                            Message message = new Message(
                                    allMessages.size(),
                                    recipient.trim(),
                                    messageText
                            );
                            
                           
                            String result = message.sendMessageViaConsole(scanner);
                            System.out.println();
                            System.out.println(result);
                            
                            // Save stored message
                            if (message.getStatus()
                                    == Message.Status.STORED) {
                                
                                try {
                                    
                                    Message.storeMessagesToJson(
                                            Collections.singletonList(message),
                                            System.getProperty("user.home")
                                                    + "/stored_messages.json"
                                    );
                                    
                                    System.out.println(
                                            "Message saved to: "
                                                    + System.getProperty("user.home")
                                                    + "/stored_messages.json"
                                    );
                                    
                                } catch (IOException e) {
                                    
                                    System.out.println(
                                            "Failed to save message: "
                                                    + e.getMessage()
                                    );
                                }
                            }
                            
                            System.out.println();
                            System.out.println(
                                    message.printMessageDetails()
                            );
                            
                            allMessages.add(message);
                        }

                        int totalSent =
                                Message.returnTotalMessages(allMessages);
                        
                        System.out.println();
                        System.out.println(
                                "Total messages sent: " + totalSent
                        );
                    }
                    
                    case "2" -> {
                        System.out.println();
                        System.out.println("Recent Messages");
                        System.out.println("------------------------------------------");
                        
                        if (allMessages.isEmpty()) {
                            
                            System.out.println(
                                    "No messages have been sent yet."
                            );

                        } else {
                            
                            for (Message message : allMessages) {
                                System.out.println(
                                        message.printMessageDetails()
                                );
                                System.out.println("------------------------------------------");
                            }
                        }
                    }
                    
                    case "3" -> quit = true;
                    
                    default -> System.out.println(
                            "Invalid option. Please choose 1, 2, or 3."
                    );
              
                }
            }
            
            System.out.println();
            System.out.println("==========================================");
            System.out.println("Thank you for using RMQuickChat!");
            System.out.println("Stay connected. Stay real.");
            System.out.println("See you soon!");
            System.out.println("==========================================");

        
        }
    }
}

