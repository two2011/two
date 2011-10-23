package pl.edu.agh.two.mud.client;

import pl.edu.agh.two.mud.client.configuration.*;
import pl.edu.agh.two.mud.common.*;

public class Client {

	SomeRMIInterface someRMIInterface;

	public static void main(String[] args) {
		Gui gui = (Gui) ApplicationContext.getBean("gui");
		
		gui.show();
	}
}
