import java.util.*;

class Service {
    private String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class AddOnServiceManager {
    private Map<String, List<Service>> reservationAddOns = new HashMap<>();

    public void addServiceToReservation(String reservationId, Service service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service.getName() + " to Reservation: " + reservationId);
    }

    public double calculateTotalAddOnCost(String reservationId) {
        List<Service> services = reservationAddOns.getOrDefault(reservationId, Collections.emptyList());
        double total = 0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    public void showAddOns(String reservationId) {
        List<Service> services = reservationAddOns.get(reservationId);
        System.out.println("Add-ons for " + reservationId + ": " + (services != null ? services : "None"));
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        Service wifi = new Service("Premium Wifi", 15.0);
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 50.0);

        String resId1 = "RES-101";
        String resId2 = "RES-102";

        manager.addServiceToReservation(resId1, wifi);
        manager.addServiceToReservation(resId1, breakfast);
        manager.addServiceToReservation(resId2, spa);

        System.out.println("\n--- Add-On Summary ---");
        manager.showAddOns(resId1);
        System.out.println("Total Add-on Cost for " + resId1 + ": $" + manager.calculateTotalAddOnCost(resId1));

        manager.showAddOns(resId2);
        System.out.println("Total Add-on Cost for " + resId2 + ": $" + manager.calculateTotalAddOnCost(resId2));
    }
}
