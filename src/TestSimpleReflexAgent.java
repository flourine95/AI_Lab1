
public class TestSimpleReflexAgent {
	public static void main(String[] args) {

		Environment env = new Environment(false);
		Agent agent = new Agent(new AgentProgram());
		env.addAgent(agent, Environment.LOCATION_A);

		env.stepUntilDone();
	}
}
