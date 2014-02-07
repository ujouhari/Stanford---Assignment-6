/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {

	private static final int START_X=0;
	private static final int START_Y=0;
	private static final int SPACE=5;
	private static final int NCOLORS=4;
	private ArrayList <NameSurferEntry> displayEntries = new ArrayList<NameSurferEntry>();;

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		displayEntries.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		displayEntries.add(entry);
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawGrid();
		plotDecades();
		getEntries();
	}

	/**Gets the entries to be drawn on the graph*/
	private void getEntries() {

		if(displayEntries.size() > 0){
			for (int i = 0; i<displayEntries.size(); i++){
				NameSurferEntry entries = displayEntries.get(i);
				drawRankGraph(entries,i);
			}
		}
	}


	/** Draws the grid lines in the graph*/
	private void drawGrid(){

		drawHorizontalLines();
		drawVerticalLines();
	}

	/**Plots the decades on the graph*/
	private void plotDecades() {

		for(int i=0;i<NDECADES;i++){
			int year = START_DECADE;
			year += 10*i;
			String decade = Integer.toString(year);
			double x = i * (getWidth()/NDECADES) + SPACE;
			double y = getHeight() - GRAPH_MARGIN_SIZE/4;
			GLabel decadeLabel = new GLabel (decade, x, y);
			add(decadeLabel);	
		}
	}

	/**Draws the horizontal lines in the graph*/
	private void drawHorizontalLines(){

		double x1 = START_X;
		double x2 = getWidth();
		double y1 = GRAPH_MARGIN_SIZE;
		double y2 = y1;

		drawLine(x1,y1,x2,y2);

		double newY1 = getHeight() - y1;
		double newY2 = newY1;

		drawLine(x1,newY1,x2,newY2);
	}

	/**Draws the vertical lines in the graph*/
	private void drawVerticalLines() {
		for(int i=0;i<NDECADES;i++){

			double x1 = i* (getWidth()/NDECADES);
			double x2 = x1;
			double y1 = START_Y;
			double y2 = getHeight();

			drawLine(x1,y1,x2,y2);
		}
	}

	/**Defines a common method to draw lines on the graphics window.*/
	private void drawLine(double x1, double y1, double x2, double y2) {
		GLine line = new GLine(x1,y1,x2,y2);
		add(line);
	}

	/**Draws the ranks along with the name labels on the graph */
	private void drawRankGraph(NameSurferEntry entry, int entryNumber) {

		plotRank(entry, entryNumber);
		plotName(entry, entryNumber);
	}

	/**Defines a method to plot the name on the graph*/
	private void plotName(NameSurferEntry entry, int entryNumber) {
		
		for(int i = 0; i<NDECADES; i++) {
			String name = entry.getName();
			int rank = entry.getRank(i);
			String rankString = Integer.toString(rank);
			String label = name + " " + rankString;
			
			double x = i * (getWidth()/NDECADES) + SPACE;
			double y = 0;
			
			if(rank != 0) {
				y = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * rank/MAX_RANK - SPACE;
			}
			else{
				label = name + " *";
				y = getHeight() - GRAPH_MARGIN_SIZE - SPACE;
			}
			GLabel nameLabel = new GLabel(label, x, y);
			int numb = entryNumber % NCOLORS;
			Color color = NewColor (numb);
			nameLabel.setColor(color);
			add(nameLabel);
		}
	}

	/**Defines a method to plot the rank on the graph*/
	private void plotRank(NameSurferEntry entry, int entryNumber) {
		
		for(int i = 0; i < NDECADES - 1; i++) {
			int rankPlot1 = entry.getRank(i);
			int rankPlot2 = entry.getRank(i+1);
			
			double x1 = i * (getWidth()/NDECADES);
			double x2 = (i+1) * (getWidth()/NDECADES);
			double y1 = 0;
			double y2 = 0;
			
			if(rankPlot1 != 0 && rankPlot2 != 0) {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * rankPlot1/MAX_RANK;
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * rankPlot2/MAX_RANK;
			}
			else if(rankPlot1 == 0 && rankPlot2 == 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			else if (rankPlot1 == 0){
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * rankPlot2/MAX_RANK;
			}
			else if(rankPlot2 == 0) {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE*2) * rankPlot1/MAX_RANK;
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}

			GLine line = new GLine(x1,y1,x2,y2);
			int numb = entryNumber % NCOLORS;
			Color color = NewColor (numb);
			line.setColor(color);
			add(line);
		}
	}
	/**Defines a common method to set the color of the name whose graph is being drawn.*/
	private Color NewColor(int numb) {

		switch(numb){
		case 0: return Color.BLACK;
		case 1: return Color.RED;
		case 3: return Color.BLUE;
		}
		return Color.MAGENTA;
	}





	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
