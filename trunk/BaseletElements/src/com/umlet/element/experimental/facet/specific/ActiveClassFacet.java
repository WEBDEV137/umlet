package com.umlet.element.experimental.facet.specific;

import java.util.Arrays;
import java.util.List;

import com.baselet.diagram.draw.BaseDrawHandler;
import com.baselet.diagram.draw.geom.XValues;
import com.baselet.gui.AutocompletionText;
import com.umlet.element.experimental.PropertiesConfig;
import com.umlet.element.experimental.facet.AbstractGlobalFacet;

public class ActiveClassFacet extends AbstractGlobalFacet {

	public static ActiveClassFacet INSTANCE = new ActiveClassFacet();
	private ActiveClassFacet() {}

	private static final String KEY = "{active}";

	private static final int SPACING = 6;

	@Override
	public boolean checkStart(String line, PropertiesConfig propConfig) {
		return line.equals(KEY);
	}

	@Override
	public void handleLine(String line, BaseDrawHandler drawer, PropertiesConfig propConfig) {
		propConfig.addToHorizontalBuffer(SPACING);
		XValues xLimits = propConfig.getXLimits(propConfig.getyPos());
		drawer.drawLine(xLimits.getLeft(), 0, xLimits.getLeft(), propConfig.getGridElementSize().getHeight());
		drawer.drawLine(xLimits.getRight(), 0, xLimits.getRight(), propConfig.getGridElementSize().getHeight());
	}

	@Override
	public List<AutocompletionText> getAutocompletionStrings() {
		return Arrays.asList(new AutocompletionText(KEY, "make class active (double left/right border)"));
	}
	
	@Override
	public Priority getPriority() {
		return Priority.HIGH; // because it changes the xlimits
	}

}
