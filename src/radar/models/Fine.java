package radar.models;

import java.util.ArrayList;
import java.util.List;

public class Fine {
    private String plateNumber;
    private int totalAmount;
    private List<Violation> violations;

    public Fine(String plateNumber) {
        this.plateNumber = plateNumber;
        this.totalAmount = 0;
        this.violations = new ArrayList<>();
    }

    public void addViolation(Violation violation) {
        this.violations.add(violation);
        this.totalAmount += violation.getFineAmount();
    }

    public void printReceipt() {
        System.out.println("Traffic fine for car " + plateNumber);
        System.out.println("Total amount: " + totalAmount + " EGP");
        System.out.println("Violations:");
        for (Violation v : violations) {
            System.out.println("- " + v.getDescription() + " : " + v.getFineAmount() + " EGP");
        }
        System.out.println("-------------------------------------------------");
    }

    public String getPlateNumber() { return plateNumber; }
    public int getTotalAmount() { return totalAmount; }
    public List<Violation> getViolations() { return violations; }
}