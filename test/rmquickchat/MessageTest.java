
package rmquickchat;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Scanner;
public class MessageTest {

    @Test
    public void testMessageIDCreated() {
        Message message = new Message(
                0,
                "+27838968976",
                "Hi tonight"
        );

        assertNotNull(message.getMessageID());
        assertTrue(
                message.getMessageID().matches("\\d{10}")
        );
    }
    
@Test
public void testMessageLengthWithinLimit() {
    Message message = new Message(0, "+27838968976", "Hi tonight");

    assertEquals("Message ready to send.",
            message.validateMessageLength());
}

@Test
public void testMessageLengthExceedsLimit() {
    String longText = "A".repeat(260);
    Message message = new Message(0, "+27838968976", longText);

    assertEquals(
            "Message exceeds 250 characters by 10; please reduce the size.",
            message.validateMessageLength()
    );
}

@Test
public void testValidRecipientNumber() {
    Message message = new Message(0, "+27838968976", "Hi tonight");

    assertEquals("Cell phone number successfully captured.",
            message.validateRecipient());
}

@Test
public void testInvalidRecipientNumber() {
    Message message = new Message(0, "0838968976", "Hi tonight");

    assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            message.validateRecipient()
    );
}

@Test
public void testMessageHash() {
    Message message = new Message("0012345678", 0,
            "+27838968976", "Hi tonight");

    assertEquals("00:0:HITONIGHT", message.getMessageHash());
}

@Test
public void testSendMessageOption() {
    Message message = new Message(0, "+27838968976", "Hi tonight");
    Scanner scanner = new Scanner("1\n");

    assertEquals("Message successfully sent.",
            message.sendMessageViaConsole(scanner));
}

@Test
public void testDiscardMessageOption() {
    Message message = new Message(0, "+27838968976", "Hi tonight");
    Scanner scanner = new Scanner("2\n");

    assertEquals("Press 0 to delete the message.",
            message.sendMessageViaConsole(scanner));
}

@Test
public void testStoreMessageOption() {
    Message message = new Message(0, "+27838968976", "Hi tonight");
    Scanner scanner = new Scanner("3\n");

    assertEquals("Message successfully stored.",
            message.sendMessageViaConsole(scanner));
}

}
