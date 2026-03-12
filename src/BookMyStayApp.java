import java.util.*;
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() { return type; }
    public double getPrice() { return price; }
    public String getAmenities() { return amenities; }

    @Override
    public String toString() {
        return "Type: " + type + " | Price: $" + price + " | Amenities: " + amenities;
    }
}

class Inventory {
    private Map<String, Integer> roomCounts = new HashMap<>();
    private Map<String, Room> roomDetails = new HashMap<>();

    public void addRoomType(Room room, int count) {
        roomDetails.put(room.getType(), room);
        roomCounts.put(room.getType(), count);
    }

    public Map<String, Integer> getAvailableCounts() {
        return Collections.unmodifiableMap(roomCounts);
    }

    public Room getRoomDetails(String type) {
        return roomDetails.get(type);
    }
}

class SearchService {
    private Inventory inventory;

    public SearchService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {
        System.out.println("--- Search Results: Available Rooms ---");
        Map<String, Integer> counts = inventory.getAvailableCounts();
        boolean found = false;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 0) {
                Room room = inventory.getRoomDetails(entry.getKey());
                System.out.println(room + " | Available: " + entry.getValue());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms currently available.");
        }
    }
}

public class BookMyStay{
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addRoomType(new Room("Single", 100.0, "Wifi, TV"), 5);
        inventory.addRoomType(new Room("Double", 150.0, "Wifi, TV, AC"), 2);
        inventory.addRoomType(new Room("Suite", 300.0, "Wifi, TV, AC, Mini Bar"), 0);

        SearchService searchService = new SearchService(inventory);
        searchService.searchAvailableRooms();
    }
}
