package fr.jlc.polytech.scheduler.core.timeline;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class TimelineView extends Group implements Serializable, Cloneable {
	
	private double timeWeight;
	private double eventsHeight;
	
	public TimelineView() {
		initTimeWeight();
		initEventsHeight();
	}
	
	public void update(@NotNull Timeline timeline) {
		if (timeline == null)
			throw new NullPointerException();
		
		if (timeline.getEvents() == null)
			throw new NullPointerException();
		
		Canvas canvas = new Canvas(640, 480);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		for (int i = 0, maxi = timeline.getEvents().size(); i < maxi; i++) {
			if (timeline.getEvents().get(i) == null)
				throw new NullPointerException();
			
			double x;
			double y = getEventsHeight() * i;
			
			gc.setFill(Color.rgb(0, 0, 0));
			gc.fillText(Integer.toString(i) + ":", 0, y);
			
			for (int j = 0, maxj = timeline.getEvents().get(i).size(); j < maxj; j++) {
				Event<?> currentEvent = timeline.getEvents().get(i).get(j);
				
				if (currentEvent == null)
					throw new NullPointerException();
				
				x = getTimeWeight() * currentEvent.getStart() + 100;
				
				gc.setFill(ColorGenerator.generateRandomBrightColor());
				gc.fillRect(x, y, getTimeWeight() * (double) currentEvent.getDuration(), getEventsHeight());
			}
		}
		
		this.getChildren().clear();
		this.getChildren().add(canvas);
	}
	
	/* GETTERS & SETTERS */
	
	public double getTimeWeight() {
		return timeWeight;
	}
	
	public void setTimeWeight(double timeWeight) {
		if (timeWeight > 0.0)
			this.timeWeight = timeWeight;
	}
	
	public void initTimeWeight() {
		this.timeWeight = 10.0;
	}
	
	public double getEventsHeight() {
		return eventsHeight;
	}
	
	public void setEventsHeight(double eventsHeight) {
		if (timeWeight > 0.0)
			this.eventsHeight = eventsHeight;
	}
	
	public void initEventsHeight() {
		this.eventsHeight = 20.0;
	}
}
