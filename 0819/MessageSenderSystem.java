interface MessageSender {
    boolean send(String receiver, String message);

    String getChannelName();

    default boolean isValid(String receiver, String message) {
        return receiver != null && !receiver.isBlank()
                && message != null && !message.isBlank();
    }
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL to " + receiver.trim() + ": " + message.trim());
        return true;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.trim().matches("^09\\d{8}$")) {
            return false;
        }
        System.out.println("SMS to " + receiver.trim() + ": " + message.trim());
        return true;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message)) {
            return false;
        }
        System.out.println("CONSOLE [" + receiver.trim() + "]: " + message.trim());
        return true;
    }

    @Override
    public String getChannelName() {
        return "Console";
    }
}

public class MessageSenderSystem {
    static boolean notify(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            return false;
        }
        return sender.send(receiver, message);
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        System.out.println("sent=" + notify(email, "amy@example.com", "Assignment ready"));
        System.out.println("sent=" + notify(sms, "0912345678", "Code is 4920"));
        System.out.println("sent=" + notify(console, "SystemAdmin", "Server rebooting"));
        System.out.println("sent=" + notify(email, "   ", "Hello"));
        System.out.println("sent=" + notify(sms, "0912345678", "   "));
        System.out.println("sent=" + notify(email, "invalid-email", "Test"));
    }
}