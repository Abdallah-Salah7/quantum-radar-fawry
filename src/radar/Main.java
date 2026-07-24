package radar;

import radar.models.CarType;
import radar.models.Observation;
import radar.rules.PrivateSpeedRule;
import radar.rules.SeatbeltRule;
import radar.rules.TruckSpeedRule;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        QuRadar radar = new QuRadar();

        radar.addRule(new SeatbeltRule());
        radar.addRule(new PrivateSpeedRule());
        radar.addRule(new TruckSpeedRule());

        Observation obs1 = new Observation("ABC1234", "2023-10-15", CarType.PRIVATE, 94, false);
        Observation obs2 = new Observation("TRK999", "2023-10-15", CarType.TRUCK, 65, true);
        Observation obs3 = new Observation("XYZ777", "2023-10-15", CarType.PRIVATE, 75, true);

        radar.process(obs1);
        radar.process(obs2);
        radar.process(obs3);

        System.out.println("=== All Fines (Plate Number : Total Amount) ===");
        for (Map.Entry<String, Integer> entry : radar.getAllPossibleFines().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " EGP");
        }
        System.out.println();

        System.out.println("=== Violated Rules Statistics ===");
        for (Map.Entry<String, Integer> entry : radar.getViolatedRulesStats().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " violations");
        }
    }
}