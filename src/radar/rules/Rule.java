package radar.rules;

import radar.models.Observation;
import radar.models.Violation;

public interface Rule {
    Violation evaluate(Observation obs);
    String getRuleName();
}