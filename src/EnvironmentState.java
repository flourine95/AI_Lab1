import java.util.HashMap;
import java.util.Map;

public class EnvironmentState {
    private Map<String, Environment.LocationState> state = new HashMap<>();
    private String agentLocation = null;//

    public EnvironmentState(Environment.LocationState locAState, Environment.LocationState locBState,
                            Environment.LocationState locCState, Environment.LocationState locDState) {
        this.state.put(Environment.LOCATION_A, locAState);
        this.state.put(Environment.LOCATION_B, locBState);
        this.state.put(Environment.LOCATION_C, locCState);
        this.state.put(Environment.LOCATION_D, locDState);
    }

    public void setAgentLocation(String location) {
        this.agentLocation = location;
    }

    public String getAgentLocation() {
        return this.agentLocation;
    }

    public Environment.LocationState getLocationState(String location) {
        return this.state.get(location);
    }

    public void setLocationState(String location, Environment.LocationState locationState) {
        this.state.put(location, locationState);
    }

    public void display() {
        System.out.println("Environment state: \n\t" + mapToString(this.state));
    }

    public static <K, V> String mapToString(Map<K, V> map) {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue());
            if (index % 2 == 0) {
                sb.append("\n\t");
            } else {
                sb.append(", ");
            }
            index++;
        }
        return sb.toString();
    }

}