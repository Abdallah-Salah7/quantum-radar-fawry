package radar.models;

public class Violation {
    private String description;
    private int fineAmount;
    private String ruleName;

    public Violation(String description, int fineAmount, String ruleName) {
        this.description = description;
        this.fineAmount = fineAmount;
        this.ruleName = ruleName;
    }

    public String getDescription() { return description; }
    public int getFineAmount() { return fineAmount; }
    public String getRuleName() { return ruleName; }
}