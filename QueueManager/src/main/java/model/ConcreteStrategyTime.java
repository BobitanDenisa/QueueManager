package model;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {

	public int addTask(List<Server> s, Task t) {
		int minWaitingTime = 200;
		int posServer = -1;
		for (Server serv : s) {
			if (serv.getNoTasks() < Scheduler.maxTasksPerServer) {
				int wt = serv.getWaitingTime().intValue(); // waiting time
				if (wt < minWaitingTime) {
					minWaitingTime = wt;
					posServer = s.indexOf(serv);
				}
			}
		}
		if (posServer != -1) {
			s.get(posServer).addTask(t);
			System.out.println("task "+t.getID()+" added to queue "+(posServer+1)+"\n");
			return 0;
		} else {
			// open new server if exists
			return 1;
		}
	}

}
