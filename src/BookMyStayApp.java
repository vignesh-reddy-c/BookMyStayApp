import java.io.*;
import java.util.*;

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    String guestName;
    int roomNumber;

    Booking(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room: " + roomNumber;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;
    List<Booking> bookings;
    Map<Integer, Boolean> inventory;

    SystemState(List<Booking> bookings, Map<Integer, Boolean> inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

public class BookMyStayApp {
    private static final String STORAGE_FILE = "hotel_data.ser";
    private List<Booking> bookings = new ArrayList<>();
    private Map<Integer, Boolean> inventory = new HashMap<>();

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();
        app.run();
    }

    public void run() {
        loadState();

        if (bookings.isEmpty()) {
            System.out.println("No previous state found. Initializing new system...");
            initializeSystem();
            addSampleData();
        } else {
            System.out.println("System state recovered successfully.");
        }

        displayStatus();
        saveState();
        System.out.println("System state saved. Shutting down.");
    }

    private void initializeSystem() {
        for (int i = 101; i <= 105; i++) {
            inventory.put(i, true);
        }
    }

    private void addSampleData() {
        bookings.add(new Booking("Vignesh", 101));
        inventory.put(101, false);
    }

    private void displayStatus() {
        System.out.println("\n--- Current System State ---");
        System.out.println("Inventory: " + inventory);
        System.out.println("Bookings: " + bookings);
        System.out.println("---------------------------\n");
    }

    private void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            SystemState state = new SystemState(bookings, inventory);
            oos.writeObject(state);
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadState() {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORAGE_FILE))) {
            SystemState state = (SystemState) ois.readObject();
            this.bookings = state.bookings;
            this.inventory = state.inventory;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Recovery failed or file corrupted. Starting fresh.");
        }
    }
}
