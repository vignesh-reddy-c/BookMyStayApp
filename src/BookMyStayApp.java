import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }

    @Override
    public String toString() {
        return String.format("Guest: %-10s | Room Type: %-8s | Room ID: %s", guestName, roomType, roomId);
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void recordBooking(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllBookings() {
        return new ArrayList<>(history);
    }
}

class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void generateSummaryReport() {
        List<Reservation> records = bookingHistory.getAllBookings();
        System.out.println("--- Hotel Booking Summary Report ---");

        if (records.isEmpty()) {
            System.out.println("No bookings recorded.");
            return;
        }

        for (Reservation res : records) {
            System.out.println(res);
        }

        System.out.println("------------------------------------");
        System.out.println("Total Bookings Confirmed: " + records.size());
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        history.recordBooking(new Reservation("Alice", "Single", "S101"));
        history.recordBooking(new Reservation("Bob", "Double", "D102"));
        history.recordBooking(new Reservation("Charlie", "Single", "S103"));
        reportService.generateSummaryReport();
    }
}
