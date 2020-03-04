package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import model.Scheduler;
import model.SelectionPolicy;
import model.Server;
import model.Task;
import view.GUI;

public class SimulationManager implements Runnable {

	private int timeLimit;
	private int maxProcessingTime;
	private int minProcessingTime;
	private int numberServers;
	private int numberTasks;
	private int maxTasksPerServer;
	private SelectionPolicy selPolicy;

	private Scheduler scheduler; // responsible with queue management and client distribution
	private GUI gui; // to display simulation
	private ArrayList<Task> tasks;

	public SimulationManager() {
		this.gui = new GUI();
		this.gui.addSimulationButtonListener(new SimulationListener());
		this.tasks = new ArrayList<Task>();

	}

	public int getNumberTasks() {
		return numberTasks;
	}
	
	public int getNumberServers() {
		return numberServers;
	}

	private void generateRandomTasks(int count) {

		Random r = new Random();
		int min = 1;
		for (int i = 0; i < count; i++) {
			int arrival = r.nextInt(timeLimit - min) + min;
			min = arrival; // + 1;
			int processing = r.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
			Task t = new Task(i + 1, arrival, processing, 0);
			this.tasks.add(t);
			System.out.println("Task " + (int) (i + 1) + " : Arrival:" + tasks.get(i).getArrivalTime() + " Processing:"
					+ tasks.get(i).getProcessingTime());
		}

	}

	public void run() {

		generateRandomTasks(numberTasks);

		int currentTime = 0;
		int tsk = 0;

		int peakHour=0;
		int maxTasks=0;
		
		while (currentTime <= timeLimit) {

			gui.writeResults("\nTime: " + currentTime + "\n");

			Iterator<Task> i = this.tasks.iterator();

			while (i.hasNext()) {
				Task t = i.next();
				if (currentTime == t.getArrivalTime()) {
					this.scheduler.dispatchTask(t);
					tsk++;
					if (tsk == numberTasks) {
						timeLimit += scheduler.getMaxWaitingTime();
					}
				}

			}
			
			int c=0;
			for (Server serv : scheduler.getServers()) {
				gui.writeResults(serv.getString()+"\n");
				c+=serv.getNoTasks();
			}
			if (c>maxTasks) {
				maxTasks=c;
				peakHour=currentTime;
			}
			
			gui.generateSimulation(scheduler.getServers());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			currentTime++;
		}
		
		//print statistics
		
		DecimalFormat df = new DecimalFormat("#.##");      
		//time = Double.valueOf(df.format(time));
		
		double avgTime=0;
		for (Server s: scheduler.getServers()) {
			avgTime+=s.getAvgTime();
		}
		avgTime/=numberTasks;
		
		gui.writeResults("\n\nRESULTS:\n\nAvgWT="+Double.valueOf(df.format(avgTime))+"\nPeakHour="+peakHour+"\n");
		

	}

	class SimulationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			gui.resetResults();
			SimulationManager.this.timeLimit = Integer.valueOf(gui.getTimeLimit());
			SimulationManager.this.maxProcessingTime = Integer.valueOf(gui.getMaxProcessingTime());
			SimulationManager.this.minProcessingTime = Integer.valueOf(gui.getMinProcessingTime());
			SimulationManager.this.numberServers = Integer.valueOf(gui.getNumberServers());
			SimulationManager.this.numberTasks = Integer.valueOf(gui.getNumberTasks());
			SimulationManager.this.maxTasksPerServer = Integer.valueOf(gui.getMaxTasksPerServer());
			SimulationManager.this.scheduler = new Scheduler(numberServers, maxTasksPerServer);

			int sel = gui.getSelectionPolicy();
			if (sel == 0) {
				SimulationManager.this.selPolicy = SelectionPolicy.SHORTEST_TIME;
			} else if (sel == 1) {
				SimulationManager.this.selPolicy = SelectionPolicy.SHORTEST_QUEUE;
			}

			Thread t = new Thread(SimulationManager.this);
			t.start();

		}

	}

	public static void main(String[] args) {

		SimulationManager gen = new SimulationManager();

	}

}
