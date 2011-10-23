package pl.edu.agh.two.mud.server;

import pl.edu.agh.two.mud.common.*;
import pl.edu.agh.two.mud.server.configuration.*;

public class Server {

	SomeRMIInterface someRMIInterface;

	public static void main(String[] args) {
		Server server = (Server) ApplicationContext.getBean("server");
		System.out.println(server);
	}
}
