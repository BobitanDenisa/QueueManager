package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import model.Server;
import model.Task;

public class GUI extends JFrame {

	private JLabel serv;
	private JComboBox<Integer> numberServers; // max nb of servers available in the supermarket at a moment
	private JLabel task;
	private JComboBox<Integer> numberTasks; // max nb of clients in the supermarket at a moment
	private JLabel maxp;
	private JComboBox<Integer> maxProcessingTime; // max processing time of a task
	private JLabel minp;
	private JComboBox<Integer> minProcessingTime; // min processing time of a task
	private JLabel tlim;
	private JComboBox<Integer> timeLimit; // max processing time of a server
	private JLabel tsel;
	private JRadioButton timeSelectionPolicy; // selection policy according to time spent in queue
	private JLabel qsel;
	private JRadioButton queueSelectionPolicy; // selection policy according to length of queue
	private JLabel maxtps;
	private JComboBox<Integer> maxTasksPerServer; // maximum number of tasks allowed per server
	private JButton startSimulation;
	private JTextArea results;
	private JPanel pan2;
	private JPanel queues;
	
	private ArrayList<JLabel> q;

	private Integer[] generateNumbers(int start, int end) {
		Integer[] a = new Integer[end - start + 1];
		int index = -1;
		for (int i = start; i <= end; i++) {
			index++;
			a[index] = i;
		}
		return a;
	}

	public GUI() {

		q=new ArrayList<JLabel>();
		JLabel l1=new JLabel("fafd");
		JLabel l2=new JLabel("gbfgb");
		q.add(l1);
		q.add(l2);
		
		this.setTitle("Queue simulator");
		this.setLayout(new BorderLayout(30, 30));
		
		this.pan2=new JPanel(new FlowLayout());
		
		this.queues=new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
		queues.setPreferredSize(new Dimension(1000, 800));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));

		serv = new JLabel("Number of servers:");
		Integer[] itemsS = generateNumbers(1, 10);
		numberServers = new JComboBox<Integer>(itemsS);

		task = new JLabel("Number of tasks:");
		Integer[] itemsT = generateNumbers(1, 10);
		numberTasks = new JComboBox<Integer>(itemsT);

		maxp = new JLabel("Maximum processing time:");
		Integer[] itemsPT = generateNumbers(10, 30);
		maxProcessingTime = new JComboBox<Integer>(itemsPT);

		minp = new JLabel("Minimum processing time:");
		Integer[] itemspt = generateNumbers(2, 5);
		minProcessingTime = new JComboBox<Integer>(itemspt);

		tlim = new JLabel("Time limit:");
		Integer[] itemsTL = generateNumbers(10, 20);
		timeLimit = new JComboBox<Integer>(itemsTL);

		timeSelectionPolicy = new JRadioButton("Shortest time selection policy");
		timeSelectionPolicy.setMnemonic(KeyEvent.VK_T);
		timeSelectionPolicy.setActionCommand("Shortest time selection policy");
		timeSelectionPolicy.setSelected(true);

		queueSelectionPolicy = new JRadioButton("Shortest queue selection policy");
		queueSelectionPolicy.setMnemonic(KeyEvent.VK_Q);
		queueSelectionPolicy.setActionCommand("Shortest queue selection policy");
		queueSelectionPolicy.setSelected(false);

		ButtonGroup selectionPolicy = new ButtonGroup();
		selectionPolicy.add(timeSelectionPolicy);
		selectionPolicy.add(queueSelectionPolicy);

		maxtps = new JLabel("Maximum tasks per server:");
		Integer[] itemsTPS = generateNumbers(5, 10);
		maxTasksPerServer = new JComboBox<Integer>(itemsTPS);

		startSimulation = new JButton("Start Simulation");
		startSimulation.setPreferredSize(new Dimension(100, 40));

		pan.add(serv);
		pan.add(numberServers);
		pan.add(task);
		pan.add(numberTasks);
		pan.add(maxp);
		pan.add(maxProcessingTime);
		pan.add(minp);
		pan.add(minProcessingTime);
		pan.add(tlim);
		pan.add(timeLimit);
		pan.add(maxtps);
		pan.add(maxTasksPerServer);
		pan.add(timeSelectionPolicy);
		pan.add(queueSelectionPolicy);

		JLabel x1 = new JLabel();
		x1.setPreferredSize(new Dimension(750, 700));
		JLabel x2 = new JLabel();
		x2.setPreferredSize(new Dimension(750, 700));
		JLabel x3 = new JLabel();
		x3.setPreferredSize(new Dimension(850, 850));

		results = new JTextArea();
		results.setOpaque(true);
		results.setBackground(Color.white);

		JScrollPane scroll = new JScrollPane(results);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(500, 800));

		pan2.add(scroll, FlowLayout.LEFT);
		pan2.add(queues);

		this.add(pan, BorderLayout.PAGE_START);
		this.add(startSimulation, BorderLayout.CENTER);
		this.add(x1, BorderLayout.LINE_START);
		this.add(x2, BorderLayout.LINE_END);
		this.add(pan2, BorderLayout.PAGE_END);

		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	public String getNumberServers() {
		return numberServers.getSelectedItem().toString();
	}

	public String getNumberTasks() {
		return numberTasks.getSelectedItem().toString();
	}

	public String getMaxProcessingTime() {
		return maxProcessingTime.getSelectedItem().toString();
	}

	public String getMinProcessingTime() {
		return minProcessingTime.getSelectedItem().toString();
	}

	public String getTimeLimit() {
		return timeLimit.getSelectedItem().toString();
	}

	public int getSelectionPolicy() {
		if (timeSelectionPolicy.isSelected()) {
			return 0;
		} else if (queueSelectionPolicy.isSelected()) {
			return 1;
		} else {
			return -1;
		}
	}

	public void writeResults(String s) {
		results.append(s);
	}
	
	public void resetResults() {
		results.setText("");
	}

	public String getMaxTasksPerServer() {
		return maxTasksPerServer.getSelectedItem().toString();
	}

	public void addSimulationButtonListener(ActionListener a) {
		startSimulation.addActionListener(a);
	}

	public void generateSimulation(List<Server> s) {
		
		pan2.remove(queues);
		queues.removeAll();
		
		for (Server serv:s) {
			JLabel q=new JLabel("Queue #"+(s.indexOf(serv)+1)+": "+serv.getTasksString());
			queues.add(q);
			q.removeAll();
		}
		pan2.add(queues);
		this.setVisible(true);
		
	}

}
