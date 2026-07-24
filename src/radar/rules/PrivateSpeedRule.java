package radar.rules;

import radar.models.CarType;
import radar.models.Observation;
import radar.models.Violation;

public class PrivateSpeedRule implements Rule {
    @Override
    public Violation evaluate(Observation obs) {
        if (obs.getCarType() == CarType.PRIVATE && obs.getSpeed() > 80) {
            String desc = "speed of " + obs.getSpeed() + " exceeded max allowed 80";
            return new Violation(desc, 300, "Private Speed Rule");
        }
        return null;
    }

    @Override
    public String getRuleName() {
        return "Private Speed Rule";
    }
}