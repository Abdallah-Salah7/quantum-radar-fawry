package radar;

import radar.models.Fine;
import radar.models.Observation;
import radar.models.Violation;
import radar.rules.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuRadar {
    private List<Rule> rules;
    private Map<String, Integer> finesLedger;
    private Map<String, Integer> violatedRulesStats;

    public QuRadar() {
        rules = new ArrayList<>();
        finesLedger = new HashMap<>();
        violatedRulesStats = new HashMap<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void process(Observation obs) {
        Fine fine = new Fine(obs.getPlateNumber());

        for (Rule rule : rules) {
            Violation violation = rule.evaluate(obs);
            if (violation != null) {
                fine.addViolation(violation);

                String ruleName = rule.getRuleName();
                violatedRulesStats.put(ruleName, violatedRulesStats.getOrDefault(ruleName, 0) + 1);
            }
        }

        if (!fine.getViolations().isEmpty()) {
            fine.printReceipt();
            finesLedger.put(fine.getPlateNumber(),
                    finesLedger.getOrDefault(fine.getPlateNumber(), 0) + fine.getTotalAmount());
        }
    }

    public Map<String, Integer> getAllPossibleFines() {
        return finesLedger;
    }

    public Map<String, Integer> getViolatedRulesStats() {
        return violatedRulesStats;
    }
}