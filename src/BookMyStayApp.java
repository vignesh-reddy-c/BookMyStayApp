import java.util.HashMap;
import java.util.Map;
class RoomInventory {
    private HashMap<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }
    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Available: " + entry.getValue());
        }
    }
}
public class BookMyStayApp{
    public static void main(String[] args) {
        System.out.println("Book My Stay - Hotel Booking System v3.1\n");
        RoomInventory inventory = new RoomInventory();
        System.out.println("Current Room Inventory:");
        inventory.displayInventory();
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single Room", 8);
        System.out.println("\nUpdated Room Inventory:");
        inventory.displayInventory();
    }
}