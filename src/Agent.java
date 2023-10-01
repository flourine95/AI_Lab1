public class Agent {
    private AgentProgram program;
    private int score;

    public Agent() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Agent(AgentProgram aProgram) {
        this.program = aProgram;
        this.score = score;
    }

    public Action execute(Percept p) {
        if (program != null) {
            return program.execute(p);
        }
        return NoOpAction.NO_OP;
    }
}
