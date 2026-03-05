import java.util.HashMap;
import java.util.Map;
abstract class Room {
    String type;
    double price;
    Room(String type, double price) {
        this.type = type;
        this.price = price;
    }
    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price per Night: " + price);
    }
}
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 2000);
    }
}
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 3500);
    }
}
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 6000);
    }
}
class RoomInventory {
    private HashMap<String, Integer> inventory;
    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0);
    }
    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}
class RoomSearchService {
    private RoomInventory inventory;
    private HashMap<String, Room> rooms;
    RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
        rooms = new HashMap<>();
        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());
    }
    void searchAvailableRooms() {
        for (String type : rooms.keySet()) {
            int available = inventory.getAvailability(type);
            if (available > 0) {
                rooms.get(type).displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay - Hotel Booking System v4.0\n");
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService(inventory);
        System.out.println("Available Rooms:\n");
        searchService.searchAvailableRooms();
    }
}