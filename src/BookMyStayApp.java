import java.util.*;

class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public void addRoomType(String type, int count) {
        rooms.put(type, count);
    }

    public void validateAndDecrement(String type) throws BookingException {
        if (!rooms.containsKey(type)) {
            throw new BookingException("Error: Room type '" + type + "' does not exist.");
        }
        int count = rooms.get(type);
        if (count <= 0) {
            throw new BookingException("Error: No availability for room type '" + type + "'.");
        }
        rooms.put(type, count - 1);
    }

    public int getCount(String type) {
        return rooms.getOrDefault(type, 0);
    }
}

class BookingValidator {
    public static void validateRequest(String guestName, String roomType) throws BookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new BookingException("Error: Guest name cannot be empty.");
        }
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new BookingException("Error: Room type must be specified.");
        }
    }
}

public class BookMyStayApp {
    public static void processBooking(Inventory inventory, String name, String type) {
        try {
            System.out.println("\nProcessing booking for: " + name + " (" + type + ")");
            BookingValidator.validateRequest(name, type);
            inventory.validateAndDecrement(type);
            System.out.println("SUCCESS: Booking confirmed for " + name);
        } catch (BookingException e) {
            System.err.println("VALIDATION FAILED: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addRoomType("Single", 1);

        processBooking(inventory, "Alice", "Single");

        processBooking(inventory, "Bob", "Single");

        processBooking(inventory, "Charlie", "Penthouse");

        processBooking(inventory, "", "Single");
    }
}
