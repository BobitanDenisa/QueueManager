package model;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	private List<Server> servers;
	private int maxNoServers;
	public static int maxTasksPerServer;
	private Strategy strategy = new ConcreteStrategyTime();

	public Scheduler(int maxNoServers, int maxTasksPerServer) {
		this.maxNoServers = maxNoServers;
		this.maxTasksPerServer = maxTasksPerServer;
		this.servers = new ArrayList<Server>();
		for (int i = 1; i <= maxNoServers; i++) {
			Server s = new Server(i);
			this.servers.add(s);
			Thread t = new Thread(s);
			t.start();
		}
	}

	public void changeStrategy(SelectionPolicy policy) {
		// apply strategy pattern to instantiate the strategy with the concrete strategy
		// corresponding to policy
		if (policy == SelectionPolicy.SHORTEST_TIME) {
			strategy = new ConcreteStrategyTime();
		}
		if (policy == SelectionPolicy.SHORTEST_QUEUE) {
			strategy = new ConcreteStrategyQueue();
		}
	}

	public int getMaxWaitingTime() {
		int max = 0;
		for (Server s : this.servers) {
			if (max < s.getWaitingTime().intValue()) {
				max = s.getWaitingTime().intValue();
			}
		}
		return max;
	}

	public void dispatchTask(Task t) {
		int x = strategy.addTask(servers, t);
//		while (x==-1 && this.servers.size()<maxNoServers) { // add another server
//			this.servers.add(new Server(this.servers.get(this.servers.size()-1).getID()+1));
//			x=strategy.addTask(servers, t);
//		}
		
	}

	public List<Server> getServers() {
		return servers;
	}

}
