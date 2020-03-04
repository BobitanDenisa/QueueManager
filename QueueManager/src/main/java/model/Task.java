package model;

public class Task {
	
	private int id;
	private int arrivalTime;
	private int processingTime;
	private int finishTime;
	
	public Task() {
		
	}
	
	public Task(int id, int arr, int pr, int fin) {
		this.id=id;
		this.arrivalTime=arr;
		this.processingTime=pr;
		this.finishTime=fin;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}

	public int getFinishTime() {
		return finishTime;
	}
	
	public int getID() {
		return this.id;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}
	
	public String getString() {
		return "		Client #"+this.id+": A.T="+this.arrivalTime+", P.T="+this.processingTime;
	}

}
