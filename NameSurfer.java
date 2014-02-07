/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	private JLabel name;
	private JTextField pName;
	private JButton bGraph;
	private JButton bClear;
	private NameSurferDataBase database;
	private NameSurferGraph graph;


	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {

		name = new JLabel ("Name:");
		add(name,NORTH);

		pName = new JTextField (10);
		add(pName, NORTH);
		pName.addActionListener(this);

		bGraph = new JButton ("Graph");
		add(bGraph,NORTH);

		bClear = new JButton ("Clear");
		add(bClear,NORTH);

		graph = new NameSurferGraph();
		add(graph);

		addActionListeners();

		database = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, defines the actions for the buttons "Graph" and "Clear"
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Graph")){
			String nameEntered = pName.getText();
			NameSurferEntry nameRanks = database.findEntry(nameEntered);
			if (nameRanks!=null){
				graph.addEntry(nameRanks);
				graph.update();
			}	
		}

		else if(e.getActionCommand().equals("Clear")){
			graph.clear();
			graph.update(); 
		}
	}
}
