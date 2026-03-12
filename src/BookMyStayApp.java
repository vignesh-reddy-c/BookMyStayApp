import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    private Set<String> allocatedRoomIds = new HashSet<>();

    public void addInventory(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public String allocateRoom(String type) {
        int roomNumber = 100 + allocatedRoomIds.size() + 1;
        String roomId = type.substring(0, 1).toUpperCase() + roomNumber;

        allocatedRoomIds.add(roomId);
        inventory.put(type, inventory.get(type) - 1);
        return roomId;
    }

    public int getCount(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class BookingService {
    private Queue<Reservation> requestQueue = new LinkedList<>();
    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(Reservation res) {
        requestQueue.add(res);
    }

    public void processAllocations() {
        System.out.println("--- Processing Room Allocations ---");
        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.poll();
            if (inventoryService.isAvailable(res.getRoomType())) {
                String roomId = inventoryService.allocateRoom(res.getRoomType());
                System.out.println("CONFIRMED: " + res.getGuestName() + " assigned Room " + roomId);
            } else {
                System.out.println("FAILED: No availability for " + res.getGuestName() + " (" + res.getRoomType() + ")");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        inventory.addInventory("Single", 2);
        inventory.addInventory("Double", 1);

        BookingService bookingService = new BookingService(inventory);
        bookingService.addRequest(new Reservation("Alice", "Single"));
        bookingService.addRequest(new Reservation("Bob", "Double"));
        bookingService.addRequest(new Reservation("Charlie", "Single"));
        bookingService.addRequest(new Reservation("David", "Single")); // Should fail

        bookingService.processAllocations();

        System.out.println("\nFinal Inventory for Single: " + inventory.getCount("Single"));
    }
}
