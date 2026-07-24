package radar;

import radar.models.Fine;
import radar.models.Observation;
import radar.models.Violation;
import radar.rules.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QuRadar - core radar violation-detection engine.
 *
 * Receives Observation data forwarded from the physical radar hardware
 * (plate number, date, car type, speed, seatbelt status) and evaluates
 * each observation against a pluggable set of Rule implementations
 * (Strategy Pattern - see the radar.rules package).
 *
 * For every rule that is violated, a Violation is generated and grouped
 * into a single Fine for that observation. Totals per plate number and
 * per-rule violation counts are tracked internally.
 *
 * The system is extensible by design: new rules (e.g. a BusSpeedRule)
 * can be added at runtime via addRule() without any modification to
 * this class, satisfying the Open/Closed Principle.
 *
 * AI model used: none. This is a deterministic, rule-based system —
 * no machine learning or AI model is involved in violation detection.
 */
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