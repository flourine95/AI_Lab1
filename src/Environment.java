
public class Environment {
    public static final Action MOVE_LEFT = new DynamicAction("LEFT");
    public static final Action MOVE_RIGHT = new DynamicAction("RIGHT");
    public static final Action SUCK_DIRT = new DynamicAction("SUCK");
    public static final String LOCATION_A = "A";
    public static final String LOCATION_B = "B";

    public enum LocationState {
        CLEAN, DIRTY
    }

    private EnvironmentState envState;
    private boolean isDone = false;// all squares are CLEAN
    private Agent agent = null;

    public Environment(LocationState locAState, LocationState locBState) {
        envState = new EnvironmentState(locAState, locBState);
    }

    // add an agent into the enviroment
    public void addAgent(Agent agent, String location) {
        // TODO
        this.agent = agent;
        this.envState.setAgentLocation(location);
    }

    public EnvironmentState getCurrentState() {
        return this.envState;
    }

    // Update enviroment state when agent do an action
    public EnvironmentState executeAction(Action action) {
        // TODO
        if (action.equals(MOVE_LEFT)) {
            this.envState.setAgentLocation(LOCATION_A);
        } else if (action.equals(MOVE_RIGHT)) {
            this.envState.setAgentLocation(LOCATION_B);
        } else {
            this.envState.setLocationState(this.envState.getAgentLocation(), LocationState.CLEAN);
        }
        return envState;
    }

    // get percept<AgentLocation, LocationState> at the current location where agent
    // is in.
    public Percept getPerceptSeenBy() {
        // TODO

        return new Percept(this.envState.getAgentLocation(), this.envState.getLocationState(this.envState.getAgentLocation()));
    }

    public void step() {
        envState.display();
        String agentLocation = this.envState.getAgentLocation();
        Action anAction = agent.execute(getPerceptSeenBy());
        EnvironmentState es = executeAction(anAction);

        System.out.println("Agent Loc.: " + agentLocation + "\tAction: " + anAction);

        if ((es.getLocationState(LOCATION_A) == LocationState.CLEAN)
            && (es.getLocationState(LOCATION_B) == LocationState.CLEAN))
            isDone = true;// if both squares are clean, then agent do not need to do any action
        es.display();
    }

    public void step(int n) {
        for (int i = 0; i < n; i++) {
            step();
            System.out.println("-------------------------");
        }
    }

    public void stepUntilDone() {
        int i = 0;

        while (!isDone) {
            System.out.println("step: " + i++);
            step();
        }
    }
}
