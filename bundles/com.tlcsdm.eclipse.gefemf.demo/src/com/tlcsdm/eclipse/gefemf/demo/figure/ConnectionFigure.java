/*******************************************************************************
 * Copyright (c) 2025 Tlcsdm. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution,
 * and is available at https://www.eclipse.org/legal/epl-v20.html
 ******************************************************************************/
package com.tlcsdm.eclipse.gefemf.demo.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;

import com.tlcsdm.eclipse.gefemf.demo.model.Connection.ConnectionType;

/**
 * Figure representing a connection between class nodes.
 */
public class ConnectionFigure extends PolylineConnection {

    public ConnectionFigure() {
        setForegroundColor(ColorConstants.black);
        setLineWidth(1);
    }

    public void setConnectionType(ConnectionType type) {
        switch (type) {
            case ASSOCIATION:
                setTargetDecoration(createArrowDecoration());
                break;
            case INHERITANCE:
                setTargetDecoration(createInheritanceDecoration());
                setLineStyle(org.eclipse.draw2d.Graphics.LINE_SOLID);
                break;
            case COMPOSITION:
                setSourceDecoration(createDiamondDecoration(true));
                setTargetDecoration(createArrowDecoration());
                break;
            case AGGREGATION:
                setSourceDecoration(createDiamondDecoration(false));
                setTargetDecoration(createArrowDecoration());
                break;
        }
    }

    private PolygonDecoration createArrowDecoration() {
        PolygonDecoration decoration = new PolygonDecoration();
        decoration.setTemplate(PolygonDecoration.TRIANGLE_TIP);
        decoration.setBackgroundColor(ColorConstants.black);
        decoration.setScale(8, 4);
        return decoration;
    }

    private PolygonDecoration createInheritanceDecoration() {
        PolygonDecoration decoration = new PolygonDecoration();
        PointList points = new PointList();
        points.addPoint(0, 0);
        points.addPoint(-2, -1);
        points.addPoint(-2, 1);
        decoration.setTemplate(points);
        decoration.setBackgroundColor(ColorConstants.white);
        decoration.setScale(10, 8);
        return decoration;
    }

    private PolygonDecoration createDiamondDecoration(boolean filled) {
        PolygonDecoration decoration = new PolygonDecoration();
        PointList points = new PointList();
        points.addPoint(0, 0);
        points.addPoint(-1, 1);
        points.addPoint(-2, 0);
        points.addPoint(-1, -1);
        decoration.setTemplate(points);
        decoration.setBackgroundColor(filled ? ColorConstants.black : ColorConstants.white);
        decoration.setScale(8, 6);
        return decoration;
    }
}
