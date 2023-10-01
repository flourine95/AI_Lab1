
public class AgentProgram {

    public Action execute(Percept p) {// location, status
        //TODO
        if (p.getLocationState().equals(Environment.LocationState.DIRTY)) {
            return Environment.SUCK_DIRT;
        } else {
            return randomAction();
        }

    }
    public Action randomAction() {
        int randomNumber = (int) (Math.random() * 4);
        return switch (randomNumber) {
            case 0 -> Environment.MOVE_LEFT;
            case 1 -> Environment.MOVE_RIGHT;
            case 2 -> Environment.MOVE_UP;
            case 3 -> Environment.MOVE_DOWN;
            default -> Environment.SUCK_DIRT;
        };
    }
}