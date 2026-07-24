package radar.rules;

import radar.models.Observation;
import radar.models.Violation;

public class SeatbeltRule implements Rule {
    @Override
    public Violation evaluate(Observation obs) {
        if (!obs.isSeatbeltFastened()) {
            return new Violation("Seatbelt not fastned", 100, "Seatbelt Rule");
        }
        return null;
    }

    @Override
    public String getRuleName() {
        return "Seatbelt Rule";
    }
}