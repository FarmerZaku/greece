package mod.greece;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

public class Agent {
	private static Instrumentation instrumentation;

	public static void agentmain(String agentArgs, Instrumentation inst) {
		Agent.instrumentation = inst;
	}

	public static void redefineClasses(ClassDefinition ...defs) throws Exception {
		Agent.instrumentation.redefineClasses(defs);
	}
}
