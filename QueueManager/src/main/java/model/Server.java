package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

	private int id;
	private BlockingQueue<Task> tasks;
	private AtomicInteger waitingTime;
	private double averageTime;
	private int peakTime;

	public Server(int id) {
		this.id = id;
		this.tasks = new ArrayBlockingQueue<Task>(Scheduler.maxTasksPerServer);
		this.waitingTime = new AtomicInteger(0);
		this.averageTime = 0;
		this.peakTime = 0;
	}

	public void run() {
		int processedTasks = 0;
		int totalWaitingTime = 0;

		while (true) {
			Task t = new Task();
			try {
				// take next task from queue
				t = this.tasks.peek();

				if (t != null) {

					// compute average waiting time
					processedTasks++;
					int aux = t.getFinishTime() - t.getArrivalTime();
					totalWaitingTime += aux;
					this.averageTime = totalWaitingTime / processedTasks;

					// compute peak waiting time
					if (aux > this.peakTime) {
						this.peakTime = aux;
					}
					
					// stop the thread for a time equal with the task's processing time
					Thread.sleep(1000 * t.getProcessingTime());
					
					// decrement waiting period
					this.waitingTime.addAndGet(-t.getProcessingTime());
					
					this.tasks.poll();
				}
				

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addTask(Task t) {
		// add task to queue
		this.tasks.add(t);

		t.setFinishTime(t.getArrivalTime() + t.getProcessingTime() + this.waitingTime.intValue());

		// increase waiting time
		//this.waitingTime.addAndGet(t.getProcessingTime());
		this.waitingTime.set(this.waitingTime.get()+t.getProcessingTime());
	}

	public int getNoTasks() {
		return tasks.size();
	}

	public void setTasks(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}

	public AtomicInteger getWaitingTime() {
		return waitingTime;
	}

	public int getID() {
		return this.id;
	}
	
	public double getAvgTime() {
		return this.averageTime;
	}
	
	public String getTasksString() {
		String s=" ";
		for (Task t : this.tasks) {
			if (s.equals(" ")) {
				s=String.valueOf(t.getID());
			}
			else {
				s = s + ", " + t.getID();
			}
		}
		return s;
	}

	public String getString() {
		String s = "	Queue #" + this.id + ": W.T=" + this.waitingTime + ", A.T=" + this.averageTime + ", P.T="
				+ this.peakTime;
		for (Task t : this.tasks) {
			s = s + "\n" + t.getString();
		}
		return s;
	}

}
