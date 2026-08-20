abstract class Device {
    private final String deviceId;

    Device(String deviceId) {
        this.deviceId = (deviceId == null || deviceId.isBlank()) ? "DEV-000" : deviceId.trim();
    }

    String getDeviceId() {
        return deviceId;
    }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    Laptop(String deviceId) {
        super(deviceId);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Laptop [" + getDeviceId() + "]: Memory & CPU OK");
    }
}

class Printer extends Device {
    Printer(String deviceId) {
        super(deviceId);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Printer [" + getDeviceId() + "]: Ink levels checked");
    }

    void cleanPrintHead() {
        System.out.println(" -> Printer [" + getDeviceId() + "]: Print head cleaned");
    }
}

class Router extends Device {
    Router(String deviceId) {
        super(deviceId);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Router [" + getDeviceId() + "]: Network connectivity OK");
    }
}

public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = {
                new Laptop("LAP-101"),
                new Printer("PRN-201"),
                new Router("RTR-301"),
                new Printer("PRN-202")
        };

        for (Device device : devices) {
            device.runDiagnostic();

            if (device instanceof Printer printer) {
                printer.cleanPrintHead();
            }
        }
    }
}