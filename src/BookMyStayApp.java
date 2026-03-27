import java.util.*;
import java.util.concurrent.*;

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
    private final Map<String, Integer> inventory = new ConcurrentHashMap<>();
    private final Map<String, Stack<String>> availableRooms = new ConcurrentHashMap<>();

    public synchronized void addRoom(String type, String roomId) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        availableRooms.putIfAbsent(type, new Stack<>());
        availableRooms.get(type).push(roomId);
    }

    public synchronized String allocateRoom(String type) {
        if (!availableRooms.containsKey(type) || availableRooms.get(type).isEmpty()) {
            return null;
        }
        inventory.put(type, inventory.get(type) - 1);
        return availableRooms.get(type).pop();
    }

    public synchronized void releaseRoom(String type, String roomId) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        availableRooms.putIfAbsent(type, new Stack<>());
        availableRooms.get(type).push(roomId);
    }

    public synchronized int getAvailableCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingService {
    private final Map<String, Booking> bookings = new ConcurrentHashMap<>();
    private final HotelInventory inventory;

    BookingService(HotelInventory inventory) {
        this.inventory = inventory;
    }

    public void createBooking(String bookingId, String roomType) {
        String roomId = inventory.allocateRoom(roomType);
        if (roomId == null) {
            System.out.println("No rooms available for type: " + roomType + " (Booking: " + bookingId + ")");
            return;
        }
        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);
        System.out.println("Booking confirmed: " + bookingId + " Room: " + roomId);
    }

    public void showInventory(String type) {
        System.out.println("Available " + type + " rooms: " + inventory.getAvailableCount(type));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) throws InterruptedException {
        HotelInventory inventory = new HotelInventory();
        inventory.addRoom("Deluxe", "D1");
        inventory.addRoom("Deluxe", "D2");

        BookingService service = new BookingService(inventory);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        Runnable task1 = () -> service.createBooking("B1", "Deluxe");
        Runnable task2 = () -> service.createBooking("B2", "Deluxe");
        Runnable task3 = () -> service.createBooking("B3", "Deluxe");

        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        service.showInventory("Deluxe");
    }
}
