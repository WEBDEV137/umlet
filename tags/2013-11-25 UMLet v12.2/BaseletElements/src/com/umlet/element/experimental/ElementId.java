package com.umlet.element.experimental;

import com.umlet.element.experimental.element.generic.Text;
import com.umlet.element.experimental.element.plot.PlotGrid;
import com.umlet.element.experimental.element.uml.Actor;
import com.umlet.element.experimental.element.uml.Class;
import com.umlet.element.experimental.element.uml.Interface;
import com.umlet.element.experimental.element.uml.UseCase;
import com.umlet.element.experimental.element.uml.relation.Relation;

/**
 * these IDs should NEVER be changed, because they are stored in uxf files
 */
public enum ElementId {
	UMLClass, UMLUseCase, Relation, UMLInterface, UMLActor, Text, 
	PlotGrid /*standalone only (at the moment), therefore instantiated in ElementFactory and not here*/;
	
	public NewGridElement createAssociatedGridElement() {
		final NewGridElement returnObj;
		if (this == UMLClass) returnObj = new Class();
		else if (this == UMLUseCase) returnObj = new UseCase();
		else if (this == UMLInterface) returnObj = new Interface();
		else if (this == UMLActor) returnObj = new Actor();
		else if (this == Relation) returnObj = new Relation();
		else if (this == Text) returnObj = new Text();
		else if (this == PlotGrid) returnObj = new PlotGrid();
		else throw new RuntimeException("Unknown class id: " + this);
		return returnObj;
	}
}