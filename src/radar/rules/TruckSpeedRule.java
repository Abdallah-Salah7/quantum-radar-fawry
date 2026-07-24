package radar.rules;

import radar.models.CarType;
import radar.models.Observation;
import radar.models.Violation;

public class TruckSpeedRule implements Rule {
    @Override
    public Violation evaluate(Observation obs) {
        if (obs.getCarType() == CarType.TRUCK && obs.getSpeed() > 60) {
            String desc = "speed of " + obs.getSpeed() + " exceeded max allowed 60";
            return new Violation(desc, 400, "Truck Speed Rule");
        }
        return null;
    }

    @Override
    public String getRuleName() {
        return "Truck Speed Rule";
    }
}