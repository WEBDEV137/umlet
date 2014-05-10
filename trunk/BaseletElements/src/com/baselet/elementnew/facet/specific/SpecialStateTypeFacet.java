package com.baselet.elementnew.facet.specific;

import com.baselet.diagram.draw.DrawHandler;
import com.baselet.diagram.draw.geom.Dimension;
import com.baselet.diagram.draw.geom.PointDouble;
import com.baselet.diagram.draw.geom.XValues;
import com.baselet.diagram.draw.helper.ColorOwn;
import com.baselet.diagram.draw.helper.ColorOwn.Transparency;
import com.baselet.elementnew.PropertiesParserState;
import com.baselet.elementnew.facet.KeyValueFacet;

public class SpecialStateTypeFacet extends KeyValueFacet {

	public static SpecialStateTypeFacet INSTANCE = new SpecialStateTypeFacet();

	private SpecialStateTypeFacet() {}

	private enum StateTypeEnum {
		INITIAL, FINAL, FLOW_FINAL, TERMINATION, DECISION
	}

	@Override
	public KeyValue getKeyValue() {
		return new KeyValue("type",
				new ValueInfo(StateTypeEnum.INITIAL, "an initial state"),
				new ValueInfo(StateTypeEnum.FINAL, "a final state for the activity"),
				new ValueInfo(StateTypeEnum.FLOW_FINAL, "a final state for a flow"),
				new ValueInfo(StateTypeEnum.TERMINATION, "a termination state"),
				new ValueInfo(StateTypeEnum.DECISION, "a decision"));
	}

	@Override
	public void handleValue(final String value, final DrawHandler drawer, final PropertiesParserState state) {
		StateTypeEnum type = StateTypeEnum.valueOf(value.toUpperCase());
		Dimension s = state.getGridElementSize();
		final double w = s.getWidth();
		final double h = s.getHeight();
		if (type == StateTypeEnum.INITIAL) {
			drawBlackEllipse(drawer, w, h, 0);
		}
		else if (type == StateTypeEnum.FINAL) {
			drawer.drawEllipse(0, 0, w, h);
			ColorOwn oldFg = drawer.getStyle().getFgColor();
			drawer.setForegroundColor(ColorOwn.TRANSPARENT); // don't use foregroundcolor for the inner circle, because otherwise in Swing it would look very ugly
			drawBlackEllipse(drawer, w - 1, h - 1, Math.max(w - 1, h - 1) / 5.5);
			drawer.setForegroundColor(oldFg);
		}
		else if (type == StateTypeEnum.FLOW_FINAL) {
			drawer.drawEllipse(0, 0, w, h);
			double yPos = h / 6;
			double lowerY = h - yPos;
			XValues upperXVal = XValues.createForEllipse(yPos, h, w);
			XValues lowerXVal = XValues.createForEllipse(lowerY, h, w);
			drawer.drawLine(upperXVal.getLeft(), yPos, lowerXVal.getRight() - 1, lowerY);
			drawer.drawLine(lowerXVal.getLeft(), lowerY, upperXVal.getRight() - 1, yPos);
		}
		else if (type == StateTypeEnum.TERMINATION) {
			drawer.drawLine(0, 0, s.getWidth(), s.getHeight());
			drawer.drawLine(s.getWidth(), 0, 0, s.getHeight());
		}
		else if (type == StateTypeEnum.DECISION) {
			drawDecision(drawer, w, h);
		}
		state.setFacetResponse(SpecialStateTypeFacet.class, true);
	}

	public static void drawDecision(final DrawHandler drawer, final double w, final double h) {
		drawer.drawLines(new PointDouble(w / 2, 1), new PointDouble(w - 1, h / 2), new PointDouble(w / 2, h - 1), new PointDouble(1, h / 2), new PointDouble(w / 2, 1));
	}

	private void drawBlackEllipse(final DrawHandler drawer, double width, double height, double radius) {
		ColorOwn oldBg = drawer.getStyle().getBgColor();
		if (drawer.getStyle().getBgColor() == ColorOwn.DEFAULT_BACKGROUND) {
			drawer.setBackgroundColor(ColorOwn.BLACK.transparency(Transparency.FOREGROUND));
		}
		else {
			drawer.setBackgroundColor(drawer.getStyle().getBgColor().transparency(Transparency.FOREGROUND));
		}
		drawer.drawEllipse(radius, radius, width - radius * 2, height - radius * 2);
		drawer.setBackgroundColor(oldBg);
	}

}