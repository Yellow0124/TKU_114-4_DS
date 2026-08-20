abstract class Transport {
    private final String routeName;

    Transport(String routeName) {
        this.routeName = routeName == null || routeName.isBlank() ? "Unknown" : routeName.trim();
    }

    String getRouteName() {
        return routeName;
    }

    abstract int calculateFare(int distance);
}

class Bus extends Transport {
    private final int baseFare;

    Bus(String routeName, int baseFare) {
        super(routeName);
        this.baseFare = Math.max(0, baseFare);
    }

    @Override
    int calculateFare(int distance) {
        if (distance <= 0)
            return 0;
        int sections = (int) Math.ceil(distance / 10.0);
        return baseFare * Math.max(1, sections);
    }
}

class Taxi extends Transport {
    private final int startFare;
    private final int perKm;

    Taxi(String routeName, int startFare, int perKm) {
        super(routeName);
        this.startFare = Math.max(0, startFare);
        this.perKm = Math.max(0, perKm);
    }

    @Override
    int calculateFare(int distance) {
        if (distance <= 0)
            return 0;
        if (distance <= 2)
            return startFare;
        return startFare + (distance - 2) * perKm;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
                new Bus("Red 26", 15),
                new Bus("Blue 38", 20),
                new Taxi("Metro Taxi A", 85, 25),
                new Taxi("Metro Taxi B", 100, 30)
        };

        int testDistance = 15;

        for (Transport transport : transports) {
            System.out.println(transport.getRouteName() + " distance=" + testDistance
                    + "km fare=" + transport.calculateFare(testDistance));
        }
    }
}