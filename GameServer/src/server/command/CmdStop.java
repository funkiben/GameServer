package server.command;
import java.util.Map;

public class CmdStop extends CmdServer {

	public CmdStop() {
		super("stop", "Stops the server");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		server.stop();
		return "Server stopped";
		
	}

}
