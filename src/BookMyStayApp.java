import java.util.*;

class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

class HotelInventory {
    Map<String, Integer> inventory = new HashMap<>();
    Map<String, Stack<String>> availableRooms = new HashMap<>();

    void addRoom(String type, String roomId) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        availableRooms.putIfAbsent(type, new Stack<>());
        availableRooms.get(type).push(roomId);
    }

    String allocateRoom(String type) {
        if (!availableRooms.containsKey(type) || availableRooms.get(type).isEmpty()) return null;
        inventory.put(type, inventory.get(type) - 1);
        return availableRooms.get(type).pop();
    }

    void releaseRoom(String type, String roomId) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        availableRooms.putIfAbsent(type, new Stack<>());
        availableRooms.get(type).push(roomId);
    }

    int getAvailableCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingService {
    Map<String, Booking> bookings = new HashMap<>();
    HotelInventory inventory;
    Stack<String> rollbackStack = new Stack<>();

    BookingService(HotelInventory inventory) {
        this.inventory = inventory;
    }

    void createBooking(String bookingId, String roomType) {
        String roomId = inventory.allocateRoom(roomType);
        if (roomId == null) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }
        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);
        System.out.println("Booking confirmed: " + bookingId + " Room: " + roomId);
    }
    void cancelBooking(String bookingId) {
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Invalid booking ID");
            return;
        }
        Booking booking = bookings.get(bookingId);
        if (booking.isCancelled) {
            System.out.println("Booking already cancelled");
            return;
        }
        rollbackStack.push(booking.roomId);
        inventory.releaseRoom(booking.roomType, booking.roomId);
        booking.isCancelled = true;
        System.out.println("Booking cancelled: " + bookingId);
    }
    void showInventory(String type) {
        System.out.println("Available " + type + " rooms: " + inventory.getAvailableCount(type));
    }
}
public class BookMyStayApp{
    public static void main(String[] args) {
        HotelInventory inventory = new HotelInventory();
        inventory.addRoom("Deluxe", "D1");
        inventory.addRoom("Deluxe", "D2");
        inventory.addRoom("Standard", "S1");
        BookingService service = new BookingService(inventory);
        service.createBooking("B1", "Deluxe");
        service.createBooking("B2", "Deluxe");
        service.showInventory("Deluxe");
        service.cancelBooking("B1");
        service.showInventory("Deluxe");
        service.cancelBooking("B1");
        service.cancelBooking("B3");
    }
}