import java.util.LinkedList;
import java.util.Queue;

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
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Enqueued: " + reservation);
    }

    public void showQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
    }

    public Reservation processNextRequest() {
        return requestQueue.poll();
    }
}

public class BookMyStayApp{
    public static void main(String[] args) {
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Single"));

        bookingQueue.showQueue();

        System.out.println("\nProcessing first request...");
        Reservation next = bookingQueue.processNextRequest();
        System.out.println("Processing: " + next);

        bookingQueue.showQueue();
    }
}
