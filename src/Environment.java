import java.util.List;
import java.util.Random;

public class Environment {
    public static final Action MOVE_LEFT = new DynamicAction("LEFT");
    public static final Action MOVE_RIGHT = new DynamicAction("RIGHT");
    public static final Action MOVE_UP = new DynamicAction("UP");
    public static final Action MOVE_DOWN = new DynamicAction("DOWN");
    public static final Action SUCK_DIRT = new DynamicAction("SUCK");
    public static final String LOCATION_A = "A";
    public static final String LOCATION_B = "B";
    public static final String LOCATION_C = "C";
    public static final String LOCATION_D = "D";
    public static final int SCORE_SUCK = 500;
    public static final int SCORE_FREEZE = -100;
    public static final int SCORE_MOVE = -10;
    private static final List<LocationState> VALUES = List.of(LocationState.values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public enum LocationState {
        CLEAN, DIRTY
    }

    private EnvironmentState envState;
    private boolean isDone = false;// all squares are CLEAN
    private Agent agent = null;

    public Environment(LocationState locAState, LocationState locBState, LocationState locCState, LocationState locDState) {
        envState = new EnvironmentState(locAState, locBState, locCState, locDState);
    }

    // mặc định sẽ là random các khu vực có bẩn hoặc sạch
    public Environment() {
        envState = new EnvironmentState(
                VALUES.get(RANDOM.nextInt(SIZE)),
                VALUES.get(RANDOM.nextInt(SIZE)),
                VALUES.get(RANDOM.nextInt(SIZE)),
                VALUES.get(RANDOM.nextInt(SIZE))
        );
    }

    public Environment(boolean isAllClean) {
        envState = isAllClean ?
                new EnvironmentState(LocationState.CLEAN, LocationState.CLEAN, LocationState.CLEAN, LocationState.CLEAN) :
                new EnvironmentState(LocationState.DIRTY, LocationState.DIRTY, LocationState.DIRTY, LocationState.DIRTY);
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
        boolean isMove = true;
        // TODO
        switch (this.envState.getAgentLocation()) {
            case LOCATION_A -> {
                if (action.equals(MOVE_RIGHT)) {
                    this.envState.setAgentLocation(LOCATION_B);
                } else if (action.equals(MOVE_DOWN)) {
                    this.envState.setAgentLocation(LOCATION_C);
                } else {
                    isMove = false;
                }
            }
            case LOCATION_B -> {
                if (action.equals(MOVE_LEFT)) {
                    this.envState.setAgentLocation(LOCATION_A);
                } else if (action.equals(MOVE_DOWN)) {
                    this.envState.setAgentLocation(LOCATION_D);
                } else {
                    isMove = false;
                }
            }
            case LOCATION_C -> {
                if (action.equals(MOVE_RIGHT)) {
                    this.envState.setAgentLocation(LOCATION_D);
                } else if (action.equals(MOVE_UP)) {
                    this.envState.setAgentLocation(LOCATION_A);
                } else {
                    isMove = false;
                }
            }
            case LOCATION_D -> {
                if (action.equals(MOVE_LEFT)) {
                    this.envState.setAgentLocation(LOCATION_C);
                } else if (action.equals(MOVE_UP)) {
                    this.envState.setAgentLocation(LOCATION_B);
                } else {
                    isMove = false;
                }
            }
        }
        if (action.equals(SUCK_DIRT)) {
            this.envState.setLocationState(this.envState.getAgentLocation(), LocationState.CLEAN);
            this.agent.addScore(SCORE_SUCK);
        } else {
            this.agent.addScore(isMove ? SCORE_MOVE : SCORE_FREEZE);
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
        System.out.println("Agent Loc.: " + agentLocation + "\tAction: " + anAction + "\tScore: " + agent.getScore());
        if ((es.getLocationState(LOCATION_A) == LocationState.CLEAN)
            && (es.getLocationState(LOCATION_B) == LocationState.CLEAN)
            && (es.getLocationState(LOCATION_C) == LocationState.CLEAN)
            && (es.getLocationState(LOCATION_D) == LocationState.CLEAN))
            isDone = true;// if both squares are clean, then agent do not need to do any action
//        es.display();
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
