# Quantum Radar — Fawry Internship Challenge

A rule-based traffic radar system built in Java, designed with clean OOP principles to detect traffic violations, issue fines, and remain fully extensible without modifying existing code.

## Overview

`QuRadar` simulates the software side of a traffic radar. It receives observation data (as if forwarded from physical radar hardware), evaluates each observation against a configurable set of rules, and issues fines for any violations found.

The core design goal was **extensibility**: new rules can be added at any time without touching the `QuRadar` class itself, satisfying the Open/Closed Principle.

**AI model used:** None. This is a deterministic, rule-based system — no machine learning or AI is involved in violation detection.

## Features

- Accepts observation data: plate number, date, car type, speed, and seatbelt status
- Evaluates observations against a pluggable set of rules
- Generates zero or more violations per observation
- Issues a formatted fine receipt when violations occur
- Tracks total fines per plate number (`getAllPossibleFines`)
- Tracks how many times each rule has been violated (`getViolatedRulesStats`)
- New rules can be plugged in via `addRule()` with no changes to `QuRadar`

## Project Structure

```
QuantumRadar/
└── src/
    └── radar/
        ├── models/
        │   ├── CarType.java        # Enum: PRIVATE, TRUCK, BUS
        │   ├── Observation.java    # Raw data sent from the radar
        │   ├── Violation.java      # A single rule violation + fee
        │   └── Fine.java           # Aggregates violations for one car, prints receipt
        ├── rules/
        │   ├── Rule.java              # Interface all rules implement
        │   ├── SeatbeltRule.java      # Seatbelt must be fastened
        │   ├── PrivateSpeedRule.java  # Private car speed limit: 80
        │   └── TruckSpeedRule.java    # Truck speed limit: 60
        ├── QuRadar.java            # Core engine
        └── Main.java               # Demo entry point
```

## Design

### Strategy Pattern (`Rule` interface)

Each traffic rule is its own class implementing the `Rule` interface:

```java
public interface Rule {
    Violation evaluate(Observation obs);
    String getRuleName();
}
```

`QuRadar` holds a list of `Rule` objects and evaluates every observation against all of them, with no knowledge of what any individual rule checks. This means:

- Adding a new rule (e.g. `BusSpeedRule`) only requires creating a new class that implements `Rule` and registering it with `radar.addRule(new BusSpeedRule())`.
- `QuRadar.java` itself never needs to change — new behavior is added, not inserted into existing logic.

### Why an interface instead of an abstract class?

Rules share no common state or partial implementation — each one is a self-contained, independent check. An interface keeps them fully decoupled from each other and from `QuRadar`, and leaves room for a rule class to extend something else in the future if needed.

### Separation of concerns

- **`models/`** — plain data holders (`Observation`, `Violation`) and one class with light behavior (`Fine`, which aggregates violations and formats the receipt).
- **`rules/`** — all violation-detection logic, isolated from the engine.
- **`QuRadar.java`** — orchestration only: runs observations through rules, tracks fines and stats. Contains no rule-specific logic.

## Rules Implemented

| Rule | Condition | Fine |
|---|---|---|
| Seatbelt Rule | Seatbelt not fastened | 100 EGP |
| Private Speed Rule | Private car speed > 80 | 300 EGP |
| Truck Speed Rule | Truck speed > 60 | 400 EGP |

## Sample Output

```
Traffic fine for car ABC1234
Total amount: 400 EGP
Violations:
- Seatbelt not fastned : 100 EGP
- speed of 94 exceeded max allowed 80 : 300 EGP
-------------------------------------------------
=== All Fines (Plate Number : Total Amount) ===
ABC1234 : 400 EGP
=== Violated Rules Statistics ===
Seatbelt Rule : 1 violations
Private Speed Rule : 1 violations
```

## How to Run

**Requirements:** JDK 8 or later.

From the project root:

```bash
# Compile
javac -d out $(find src -name "*.java")

# Run
java -cp out radar.Main
```

`Main.java` demonstrates the system with a few sample observations, prints any fine receipts generated, then prints the full fines summary and rule-violation statistics.

## Extending the System

To add a new rule (e.g. a bus speed limit):

1. Create a new class in `radar/rules/` implementing `Rule`:

```java
public class BusSpeedRule implements Rule {
    @Override
    public Violation evaluate(Observation obs) {
        if (obs.getCarType() == CarType.BUS && obs.getSpeed() > 70) {
            return new Violation(
                "speed of " + obs.getSpeed() + " exceeded max allowed 70",
                350,
                "Bus Speed Rule"
            );
        }
        return null;
    }

    @Override
    public String getRuleName() {
        return "Bus Speed Rule";
    }
}
```

2. Register it in `Main.java`:

```java
radar.addRule(new BusSpeedRule());
```

No other file needs to change.

## Author

Abdallah Salah — Fawry Quantum Internship Challenge submission.