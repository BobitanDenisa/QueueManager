package model;

import java.util.List;

import view.GUI;

public class ConcreteStrategyQueue implements Strategy {

	public int addTask(List<Server> s, Task t) {
		int minTasks = 50;
		int posServer = -1;
		for (Server serv : s) {
			int tn = serv.getNoTasks(); // tasks number
			if (tn < Scheduler.maxTasksPerServer) {
				if (tn < minTasks) {
					minTasks = tn;
					posServer = s.indexOf(serv);
				}
			}
		}
		if (posServer != -1) {
			s.get(posServer).addTask(t);
			return 0;
		} else {
			// open new server if exists
			return 1;
		}
	}

}
