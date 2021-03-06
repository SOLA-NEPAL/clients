/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geotools.swing.extended;

import com.vividsolutions.jts.geom.Geometry;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import org.geotools.map.event.MapLayerEvent;
import org.geotools.map.event.MapLayerListEvent;
import org.geotools.swing.control.extended.ExtendedToolItem;
import org.geotools.swing.control.extended.Toc;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.referencing.NamedIdentifier;
import org.geotools.swing.tool.CursorTool;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.geotools.geometry.jts.Geometries;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.Layer;
import org.geotools.swing.JMapPane;
import org.geotools.swing.event.MapMouseAdapter;
import org.geotools.swing.event.MapMouseEvent;
import org.geotools.swing.event.MapPaneAdapter;
import org.geotools.swing.event.MapPaneEvent;
import org.geotools.swing.extended.util.Messaging;
import org.geotools.map.extended.layer.ExtendedLayer;
import org.geotools.map.extended.layer.ExtendedLayerEditor;
import org.geotools.map.extended.layer.ExtendedLayerGraphics;
import org.geotools.map.extended.layer.ExtendedLayerShapefile;
import org.geotools.map.extended.layer.ExtendedLayerWMS;
import org.geotools.renderer.lite.RendererUtilities;
import org.geotools.swing.extended.exception.InitializeLayerException;
import org.geotools.swing.extended.exception.InitializeMapException;
import org.geotools.swing.extended.exception.MapScaleException;
import org.geotools.swing.mapaction.extended.ExtendedAction;
import org.geotools.swing.tool.extended.ExtendedTool;
import org.opengis.referencing.operation.TransformException;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.cadastre.DatasetBean;

/**
 * This is an extension of the swing control {
 *
 * @see org.geotools.swing.JMapPane}. Added functionality includes: <ul> <li>
 * Initialization of the control only by a SRID. It uses the StreamingRenderer
 * for rendering and DefaultMapContext for the map context </li> <li> Helper
 * methods to add different kind of layers</li> <li> Adding OnMouseWheel Zoom
 * in/out handling</li> <li> Adding panning with by holding the middle button of
 * the mouse pressed</li> <li> Get current pixel resolution for the map
 * control</li> <li> Helper methods for transforming points map from/to
 * screen</li> <li> Defining a custom full extent that is not dependent in the
 * full extent of all layers found in the map</li> </ul>
 * @author Elton Manoku
 *
 */
public class Map extends JMapPane {

    public static final String DATASET_CHANGED_PROPERTY = "dataset";
    private static String SRID_RESOURCE_LOCATION = "resources/srid.properties";
    private static String SELECTION_SLD_FILE = "selection.xml";
    private static String SELECTION_LAYER_NAME = "selection";
    private static Properties sridResource = null;
    private java.awt.Point panePos = null;
    private boolean panning = false;
    private boolean isRendering = false;
    private MapMouseAdapter mapMouseAdapter;
    private MapPaneAdapter mapPaneListener;
    private ReferencedEnvelope fullExtentEnvelope;
    private LinkedHashMap<String, ExtendedLayer> extendedLayers =
            new LinkedHashMap<String, ExtendedLayer>();
    private Integer srid = null;
    private ButtonGroup toolsGroup = new ButtonGroup();
    private double pixelResolution = 0;
    private Toc toc;
    private CursorTool activeTool = null;
    private DatasetBean dataset;
    //for zoom previous action storing draw extents.
    private List<ReferencedEnvelope> draw_Envelops = new ArrayList<ReferencedEnvelope>();
    private JTextField txtScale;

    public JTextField getTxtScale() {
        return txtScale;
    }

    public void setTxtScale(JTextField txtScale) {
        this.txtScale = txtScale;
    }

    /**
     * This constructor is used only for the graphical designer. Use the other
     * constructor for initializing the map control
     */
    public Map() throws InitializeMapException {
        super();
        this.initializeReferenceSystemResource();
        this.setBackground(Color.WHITE);
        this.createListeners();
    }

    /**
     * It initializes the map control given an SRID.
     *
     * @param srid The SRID
     * @throws InitializeMapException
     */
    public Map(int srid) throws InitializeMapException {
        this();
        this.initialize(srid);
    }

    /**
     * It initializes the map control given an SRID and WKT Presentation of the
     * reference system. This constructor is used if for the given srid the WKT
     * is not in the resource srid.properties.
     *
     * @param srid The SRID
     * @param wktOfReferenceSystem the WKT definition of Reference system if not
     * found in the srid.properties resource file. If found there there is not
     * need to specify.
     * @throws InitializeMapException
     */
    public Map(int srid, String wktOfReferenceSystem) throws InitializeMapException {
        this();
        if (!sridResource.contains(Integer.toString(srid))) {
            sridResource.setProperty(Integer.toString(srid), wktOfReferenceSystem);
        }
        this.initialize(srid);
    }

    private void initializeReferenceSystemResource() throws InitializeMapException {
        try {
            System.setProperty("org.geotools.referencing.forceXY", "true");
            //Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
            if (sridResource == null) {
                sridResource = new Properties();
                String resourceLocation = String.format("/%s/%s",
                        this.getClass().getPackage().getName().replace('.', '/'),
                        SRID_RESOURCE_LOCATION);
                sridResource.load(this.getClass().getResourceAsStream(resourceLocation));
            }
        } catch (IOException ex) {
            throw new InitializeMapException("Coordinative system resource not found.", ex);
        }
    }

    /**
     * Internal initializer of the control. It starts up the map context,
     * renderer and sets the Reference System based in the SRID.
     *
     * @param srid
     * @throws InitializeMapException
     */
    private void initialize(int srid) throws InitializeMapException {
        MapContent content = new MapContent();
        try {
            content.getViewport().setCoordinateReferenceSystem(
                    this.generateCoordinateReferenceSystem(srid));
        } catch (FactoryException ex) {
            throw new InitializeMapException(
                    Messaging.Ids.MAPCONTROL_MAPCONTEXT_WITHOUT_SRID_ERROR.toString(), ex);
        }
        if (content.getCoordinateReferenceSystem() == null) {
            throw new InitializeMapException(
                    Messaging.Ids.MAPCONTROL_MAPCONTEXT_WITHOUT_SRID_ERROR.toString(), null);
        }
        this.srid = srid;
        this.setMapContent(content);
    }

    /**
     * Overrides the method to call the event of onSelectionChanged of tools.
     *
     * @param tool The tool to set active for the map
     */
    @Override
    public void setCursorTool(CursorTool tool) {
        if (this.activeTool != null && this.activeTool instanceof ExtendedTool) {
            ((ExtendedTool) this.activeTool).onSelectionChanged(false);
        }
        super.setCursorTool(tool);
        this.activeTool = tool;
        if (this.activeTool != null && this.activeTool instanceof ExtendedTool) {
            ((ExtendedTool) this.activeTool).onSelectionChanged(true);
        }
        this.setFocusable(true);
    }

    /**
     * It generates a CoordinateReferenceSystem based in srid.
     *
     * @param srid
     * @return The coordinative system corresponding to the srid
     * @throws FactoryException
     */
    private CoordinateReferenceSystem generateCoordinateReferenceSystem(int srid)
            throws FactoryException {
        String wktOfCrs = sridResource.getProperty(Integer.toString(srid));
        return CRS.parseWKT(wktOfCrs);
    }

    /**
     * Initialize the mouse and map bounds listeners. The map wheel listener are
     * initialized separately because the ones provided by mapMouseAdapter gives
     * a null location
     */
    private void createListeners() {

        this.addMouseWheelListener(new java.awt.event.MouseWheelListener() {

            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                handleMouseWheelEvent(evt);
            }
        });

        this.mapMouseAdapter = new MapMouseAdapter() {

            @Override
            public void onMouseDragged(MapMouseEvent mme) {
                handleMouseDragged(mme);
            }

            @Override
            public void onMousePressed(MapMouseEvent mme) {
                handleMousePressed(mme);
            }

            @Override
            public void onMouseReleased(MapMouseEvent mme) {
                handleMouseReleased(mme);
            }
        };

        this.mapPaneListener = new MapPaneAdapter() {

            @Override
            public void onRenderingStarted(MapPaneEvent ev) {
                isRendering = true;
            }

            @Override
            public void onRenderingStopped(MapPaneEvent ev) {
                isRendering = false;
                //refreshScalebar();
            }

            @Override
            public void onDisplayAreaChanged(MapPaneEvent ev) {
                handleOnDisplayAreaChanged(ev);
            }
        };
        this.addMouseListener(this.mapMouseAdapter);
        this.addMapPaneListener(this.mapPaneListener);
    }

    /**
     * It zooms in and out as the user uses the mousewheel. The mouse stays
     * pointing in the same map coordinates
     *
     * @param ev
     */
    private void handleMouseWheelEvent(MouseWheelEvent ev) {
        if (this.IsRendering()) {
            return;
        }
        double zoomFactor = 0.5;
        int clicks = ev.getWheelRotation();
        // -ve means wheel moved up, +ve means down
        int sign = (clicks > 0 ? -1 : 1);

        zoomFactor = sign * zoomFactor / 2;

        Point2D mouseMapPointPos = this.getPointInMap(ev.getPoint());

        ReferencedEnvelope env = this.getDisplayArea();

        double width = env.getSpan(0);
        double height = env.getSpan(1);
        double newWidth = width - (width * zoomFactor);
        double newHeight = height - (height * zoomFactor);

        double centerX = env.getMedian(0);
        double centerY = env.getMedian(1);

        double distanceMouseCenterAlongX = mouseMapPointPos.getX() - centerX;
        double distanceMouseCenterAlongY = mouseMapPointPos.getY() - centerY;
        centerX += distanceMouseCenterAlongX * zoomFactor;
        centerY += distanceMouseCenterAlongY * zoomFactor;

        double newMinX = centerX - newWidth / 2;
        double newMinY = centerY - newHeight / 2;
        double newMaxX = centerX + newWidth / 2;
        double newMaxY = centerY + newHeight / 2;
        env = new ReferencedEnvelope(
                newMinX, newMaxX, newMinY, newMaxY, env.getCoordinateReferenceSystem());

        this.setDisplayArea(env);
        this.refresh();
    }

    /**
     * It catches the start of the pan process
     *
     * @param ev
     */
    public void handleMousePressed(MapMouseEvent ev) {
        if (ev.getButton() == java.awt.event.MouseEvent.BUTTON2) {
            panePos = ev.getPoint();
            panning = true;
        }
    }

    /**
     * Respond to a mouse dragged event. Calls {@link org.geotools.swing.JMapPane#moveImage()}
     *
     * @param ev the mouse event
     */
    public void handleMouseDragged(MapMouseEvent ev) {
        if (this.panning) {
            java.awt.Point pos = ev.getPoint();
            if (!pos.equals(this.panePos)) {
                this.moveImage(pos.x - this.panePos.x, pos.y - this.panePos.y);
                this.panePos = pos;
            }
        }
    }

    /**
     * If this button release is the end of a mouse dragged event, requests the
     * map to repaint the display
     *
     * @param ev the mouse event
     */
    public void handleMouseReleased(MapMouseEvent ev) {
        if (this.panning) {
            panning = false;
            this.refresh();
        }
    }

    /**
     * It updates the pixel resolution of the map for each change of the
     * DisplayArea.
     *
     * @param ev
     */
    public void handleOnDisplayAreaChanged(MapPaneEvent ev) {
        ReferencedEnvelope newDisplayArea = this.getDisplayArea();
        double widthInMap = newDisplayArea.getSpan(0);
        double widthInPixels = this.getSize().getWidth();
        this.pixelResolution = widthInMap / widthInPixels;
    }

    /**
     * Gets the current pixel resolution of the map
     *
     * @return
     */
    public double getPixelResolution() {
        return this.pixelResolution;
    }

    /**
     * Get the point in screen coordinates
     *
     * @param mapPoint The point in map coordinates
     * @return
     */
    public Point2D getPointInScreen(Point2D mapPoint) {
        Point2D screenPoint = new Point2D.Double(mapPoint.getX(), mapPoint.getY());
        this.getWorldToScreenTransform().transform(screenPoint, screenPoint);
        return screenPoint;
    }

    /**
     * Get the point in map coordinates
     *
     * @param screenPoint The point in screen coordinates
     * @return
     */
    public Point2D getPointInMap(Point2D screenPoint) {
        Point2D mapPoint = new Point2D.Double(screenPoint.getX(), screenPoint.getY());
        this.getScreenToWorldTransform().transform(mapPoint, mapPoint);
        return mapPoint;
    }

    /**
     * Gets the fact that the map is busy rendering
     *
     * @return the isRendering
     */
    public boolean IsRendering() {
        return isRendering;
    }

    /**
     * It zooms to the full extent.
     */
    public void zoomToFullExtent() {
        this.setDisplayArea(this.getFullExtent());
    }

    /**
     * It sets the full extent of the map control.
     *
     * @param east
     * @param west
     * @param north
     * @param south
     */
    public void setFullExtent(double east, double west, double north, double south) {
        this.fullExtentEnvelope = new ReferencedEnvelope(
                west, east, south, north, this.getMapContent().getCoordinateReferenceSystem());
    }

    /**
     * Gets the full extent. If the full extent is not yet set then the full
     * extent of all layers in the map is used.
     *
     *
     * @return
     */
    public ReferencedEnvelope getFullExtent() {
        if (this.fullExtentEnvelope == null) {
            this.reset();
            this.fullExtentEnvelope = this.getDisplayArea();
        }
        return this.fullExtentEnvelope;
    }

    /**
     * Gets the srid of the map
     *
     * @return
     */
    public Integer getSrid() {
        if (srid == null) {
            Object[] identifiers =
                    this.getMapContent().getCoordinateReferenceSystem().getIdentifiers().toArray();
            srid = Integer.parseInt(((NamedIdentifier) identifiers[0]).getCode());

        }
        return srid;
    }

    /**
     * Gets the list of SolaLayers loaded in the map
     *
     * @return
     */
    public LinkedHashMap<String, ExtendedLayer> getSolaLayers() {
        return this.extendedLayers;
    }

    /**
     * It adds a ExtendedLayer
     *
     * @param solaLayer the ExtendedLayer
     * @return
     */
    public ExtendedLayer addLayer(ExtendedLayer solaLayer) {
        this.getSolaLayers().put(solaLayer.getLayerName(), solaLayer);
        for (Layer layer : solaLayer.getMapLayers()) {
            this.getMapContent().addLayer(layer);
        }
        solaLayer.setMapControl(this);
        if (this.toc != null && solaLayer.isShowInToc()) {
            this.toc.addLayer(solaLayer);
        }
        return solaLayer;
    }

    public DatasetBean getDataset() {
        return dataset;
    }

    public String getDatasetId() {
        if (getDataset() == null) {
            return null;
        }
        return getDataset().getId();
    }

    /** 
     * Updates dataset changes for the map, to be used for subsequent data fetch.
     * @param dataset Dataset object to set
     * @param refresh Indicates whether to refresh the map or not.
     */
    public void setDataset(DatasetBean dataset, boolean refresh) throws InitializeMapException {
        if (dataset != null) {
            this.dataset = dataset;
            this.srid = dataset.getSrid();
            if (!sridResource.contains(Integer.toString(srid))) {
                sridResource.setProperty(Integer.toString(srid), CacheManager.getCrs(srid));
            }
            try {
                getMapContent().getViewport().setCoordinateReferenceSystem(
                        this.generateCoordinateReferenceSystem(srid));
            } catch (FactoryException ex) {
                    throw new InitializeMapException(
                            Messaging.Ids.MAPCONTROL_MAPCONTEXT_WITHOUT_SRID_ERROR.toString(), ex);
            }
            if (getMapContent().getCoordinateReferenceSystem() == null) {
                    throw new InitializeMapException(
                            Messaging.Ids.MAPCONTROL_MAPCONTEXT_WITHOUT_SRID_ERROR.toString(), null);
            }
            if (getMapContent().layers().size() > 0 && refresh) {
                refresh();
            }
        }
        firePropertyChange(DATASET_CHANGED_PROPERTY, false, true);
    }

    /**
     * It adds a layer of type WMS which is actually a WMS Server with a list of
     * layers in it.
     *
     * @param layerName The name
     * @param layerTitle layer title
     * @param URL The WMS Capabilities Location
     * @param layerNames The list of layer names
     * @return
     */
    public ExtendedLayerWMS addLayerWMS(
            String layerName, String layerTitle, String URL, List<String> layerNames) {
        ExtendedLayerWMS layer = null;
        try {
            layer = new ExtendedLayerWMS(layerName, layerTitle, this, URL, layerNames);
            this.getSolaLayers().put(layerName, layer);
        } catch (InitializeLayerException ex) {
            Messaging.getInstance().show(ex.getMessage());
        }
        return layer;
    }

    /**
     * It adds a shapefile layer
     *
     * @param layerName the layer name
     * @param layerTitle layer title
     * @param pathOfShapefile the path of the .shp file
     * @param styleResource the resource name .xml in the location of resources
     * for layer styles. This resource location is added in the path decided in
     * SLD_RESOURCES {
     * @see org.sola.clients.geotools.ui.layers.SolaFeatureLayer}.
     * @return
     */
    public ExtendedLayerShapefile addLayerShapefile(
            String layerName, String layerTitle, String pathOfShapefile, String styleResource) {
        ExtendedLayerShapefile layer = null;
        try {
            layer = new ExtendedLayerShapefile(layerName, pathOfShapefile, styleResource);
            layer.setTitle(layerTitle);
            this.addLayer(layer);
        } catch (InitializeLayerException ex) {
            Messaging.getInstance().show(ex.getMessage());
        }
        return layer;
    }

    /**
     * It adds a graphics layer in the map
     *
     * @param layerName layer name
     * @param layerTitle layer title
     * @param geometryType geometry type for this layer
     * @param styleResource the resource name .xml in the location of resources
     * for layer styles. This resource location is added in the path decided in
     * SLD_RESOURCES {
     * @see org.sola.clients.geotools.ui.layers.SolaFeatureLayer}.
     * @return
     */
    public ExtendedLayerGraphics addLayerGraphics(
            String layerName, String layerTitle, Geometries geometryType, String styleResource) {
        ExtendedLayerGraphics layer = null;
        try {
            layer = new ExtendedLayerGraphics(
                    layerName, geometryType, styleResource);
            layer.setTitle(layerTitle);
            this.addLayer(layer);
        } catch (InitializeLayerException ex) {
            Messaging.getInstance().show(ex.getMessage());
        }
        return layer;
    }

    /**
     * It adds a editor layer in the map.
     *
     * @param layerName layer name
     * @param layerTitle layer title
     * @param geometryType geometry type for this layer
     * @param styleResource the resource name .xml in the location of resources
     * for layer styles. This resource location is added in the path decided in
     * SLD_RESOURCES {
     * @see org.sola.clients.geotools.ui.layers.SolaFeatureLayer}.
     * @return
     */
    public ExtendedLayerEditor addLayerEditor(
            String layerName, String layerTitle, Geometries geometryType,
            String styleResource, String extraFieldsFormat) {
        ExtendedLayerEditor layer = null;
        try {
            layer = new ExtendedLayerEditor(
                    layerName, geometryType, styleResource, extraFieldsFormat);
            layer.setTitle(layerTitle);
            this.addLayer(layer);
        } catch (InitializeLayerException ex) {
            Messaging.getInstance().show(ex.getMessage());
        }
        return layer;
    }

    /**
     * It adds an action of a control (usually a button) in the toolbar.
     *
     * @param action the action to be added
     * @param hasTool if the action activates a map tool
     * @param inToolbar the toolbar where the action will be added
     */
    public void addMapAction(
            AbstractAction action, boolean hasTool, JToolBar inToolbar, boolean enabled) {
        action.setEnabled(enabled);
        AbstractButton btn = null;
        if (hasTool) {
            btn = new ExtendedToolItem(action);
        } else {
            btn = new JButton(action);
        }

        this.toolsGroup.add(btn);
        inToolbar.add(btn);
    }

    public void addMapAction(
            AbstractAction action, boolean hasTool, JToolBar inToolbar,
            boolean enabled, String buttonName) {
        action.setEnabled(enabled);
        AbstractButton btn = null;
        if (hasTool) {
            btn = new ExtendedToolItem(action);
        } else {
            btn = new JButton(action);
        }
        btn.setName(buttonName);
        this.toolsGroup.add(btn);
        inToolbar.add(btn);
    }

    public void addMapTextBoxAction(
            AbstractAction action, JToolBar inToolbar,
            boolean enabled, String textBoxName) {
        //Label box.
        JLabel lbl = new JLabel();
        lbl.setText(textBoxName + ": ");
        lbl.setSize(50, 25);
        lbl.setEnabled(enabled);
        inToolbar.add(lbl);
        //Text box.
        JTextField txt = new JTextField(textBoxName);
        txt.setAction(action);
        txt.setText("");
        txt.setSize(50, 25);
        txt.setEnabled(enabled);
        this.setTxtScale(txt);
        inToolbar.add(txt);
    }

    /**
     * It adds a tool in the map. The tool adds also an action in the toolbar
     * which can activate the tool
     *
     * @param tool the tool
     * @param inToolbar the toolbar
     */
    public void addTool(ExtendedTool tool, JToolBar inToolbar, boolean enabled) {
        this.addMapAction(new ExtendedAction(this, tool), inToolbar, enabled);
    }

    public void addTool(ExtendedTool tool, JToolBar inToolbar, boolean enabled,
            String buttonName) {
        this.addMapAction(new ExtendedAction(this, tool), inToolbar,
                enabled, buttonName);
    }

    /**
     * Add an action of type ExtendedAction in a toolbar.
     *
     * @param action the action
     * @param inToolbar the toolbar
     */
    public void addMapAction(ExtendedAction action, JToolBar inToolbar, boolean enabled) {
        this.addMapAction(action, action.getAttachedTool() != null, inToolbar, enabled);
    }

    public void addMapAction(ExtendedAction action, JToolBar inToolbar, boolean enabled,
            String buttonName) {
        this.addMapAction(action, action.getAttachedTool() != null, inToolbar, enabled, buttonName);
    }

    /**
     * Gets the button in the toolbar associated with the map control.
     *
     * @param actionName The action name associated with the button
     * @return The button if found otherwise null
     */
    public AbstractButton getBaritemByActionName(String actionName) {
        Enumeration mapBtnEnum = this.toolsGroup.getElements();
        while (mapBtnEnum.hasMoreElements()) {
            AbstractButton btn = (AbstractButton) mapBtnEnum.nextElement();
            ExtendedAction btnAction = (ExtendedAction) btn.getAction();
            if (btnAction.getName().equals(actionName)) {
                return btn;
            }
        }
        return null;
    }

    /**
     * Gets the map action which is used in one of the buttons added in the
     * toolbar associated with the map.
     *
     * @param actionName The action name
     * @return The action if found or null
     */
    public ExtendedAction getMapActionByName(String actionName) {
        AbstractButton btn = this.getBaritemByActionName(actionName);
        if (btn == null) {
            return null;
        }
        return (ExtendedAction) btn.getAction();
    }

    /**
     * Gets a toolitem that is found in the toolbar associated with the map.
     *
     * @param name Name of tool
     * @return The toolitem or null
     */
    public ExtendedToolItem getToolItemByName(String name) {
        AbstractButton btn = this.getBaritemByActionName(name);
        ExtendedToolItem toolItem = null;
        if (btn == null) {
            return null;
        } else if (btn instanceof ExtendedToolItem) {
            toolItem = (ExtendedToolItem) btn;
        }
        return toolItem;

    }

    /**
     * Sets a tool as active in the map.
     *
     * @param toolName The name of the tool.
     */
    public void setActiveTool(String toolName) {
        ExtendedToolItem toolItem = this.getToolItemByName(toolName);
        toolItem.setSelected(true);
    }

    /**
     * Sets the Table of Contents.
     *
     * @param toc
     */
    public void setToc(Toc toc) {
        this.toc = toc;
    }

    /**
     * Gets the Table of Contents.
     *
     * @return
     */
    public Toc getToc() {
        return toc;
    }

    /**
     * It refreshes the map.
     */
    public void refresh() {
        record_ZoomEnvelope();
        //redraw map.
        this.drawLayers(false);
    }

    //<editor-fold defaultstate="collapse" desc="By Kabindra">
    /**
     * It refreshes the map, for checking purpose.
     */
    public void refresh(boolean bln_redraw) {
        record_ZoomEnvelope();
        //redraw map.
        this.drawLayers(bln_redraw);
    }

    // <needed for zoom previous--addition>
    //------------------------------------------------
    public void record_ZoomEnvelope() {
        if (draw_Envelops.size() > 25) {
            draw_Envelops.remove(0);
        }
        draw_Envelops.add(this.getDisplayArea());
    }

    //zoom previous action.
    public void zoomPrevious() {
        int n = draw_Envelops.size();
        if (n > 0) {
            //last window used to draw.
            ReferencedEnvelope zoomwindow = draw_Envelops.get(n - 1);
            this.setDisplayArea(zoomwindow);
            this.drawLayers(false);
            //remove used window.
            draw_Envelops.remove(zoomwindow);
        }
    }
    //</editor-fold>

    /**
     * Gets the current scale of the map.
     *
     * @return
     */
    public Double getScale() throws MapScaleException {
        try {
            return RendererUtilities.calculateScale(
                    this.getDisplayArea(), this.getWidth(),
                    this.getHeight(), null);
        } catch (TransformException trnsEx) {
            throw new MapScaleException(trnsEx);
        } catch (FactoryException trnsEx) {
            throw new MapScaleException(trnsEx);
        }
    }

    /**
     * It overrides the default behavior of drawLayers by resetting always the
     * labelcache.
     *
     * @param createNewImage
     */
    @Override
    protected void drawLayers(boolean createNewImage) {
        clearLabelCache.set(true);
        super.drawLayers(createNewImage);
        try {
            if (this.getWidth() == 0 || this.getHeight() == 0) {
                return;
            }
            DecimalFormat df = new DecimalFormat("#0");
            String currentScale = df.format(this.getScale());
            if(txtScale!=null){
                txtScale.setText(currentScale);
            }
        } catch (MapScaleException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * It overrides the default behavior of the JMapPane of geotools by trying
     * to maintain the extent of the map while resizing.
     */
    @Override
    protected void setForNewSize() {
        if (this.getDisplayArea() != null && this.pendingDisplayArea == null) {
            this.pendingDisplayArea = this.getDisplayArea();
        }
        super.setForNewSize();
    }

    /**
     * Overrides the default behavior of layerChanged. The repaint() is
     * commented so when the layer is changed and the map is refreshed the image
     * is not reseted completely.
     */
    @Override
    public void layerChanged(MapLayerListEvent event) {
        paramsLock.writeLock().lock();
        try {
            int reason = event.getMapLayerEvent().getReason();

            if (reason == MapLayerEvent.DATA_CHANGED) {
                setFullExtent();
            }

            if (reason != MapLayerEvent.SELECTION_CHANGED) {
                clearLabelCache.set(true);
                drawLayers(false);
            }

        } finally {
            paramsLock.writeLock().unlock();
        }

        //repaint();
    }

    /**
     * Overrides the default behavior of onImageMoved. The repaint() is
     * commented so while panning the image is not reseted completely.
     */
    @Override
    protected void onImageMoved() {
        if (imageMovedFuture != null && !imageMovedFuture.isDone()) {
            imageMovedFuture.cancel(true);
        }

        imageMovedFuture = paneTaskExecutor.schedule(new Runnable() {

            @Override
            public void run() {
                afterImageMoved();
                clearLabelCache.set(true);
                drawLayers(false);
                //repaint();
            }
        }, paintDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * Initializes the selection layer on the map control. This method should be
     * called after all map layers have been added to ensure the selection layer
     * appears on top of the other SOLA layers.
     */
    public void initializeSelectionLayer() {
        try {
            ExtendedLayerGraphics selectionLayer =
                    (ExtendedLayerGraphics) getSolaLayers().get(SELECTION_LAYER_NAME);
            if (selectionLayer == null) {
                selectionLayer = new ExtendedLayerGraphics(SELECTION_LAYER_NAME,
                        Geometries.GEOMETRY, SELECTION_SLD_FILE);
                selectionLayer.setShowInToc(false);
                this.addLayer(selectionLayer);
            }
        } catch (InitializeLayerException ex) {
            Messaging.getInstance().show(ex.getMessage());
        }
    }

    /**
     * Adds the specified feature to the selection layer.
     *
     * @param id The identifier for the feature
     * @param geom The geometry of the feature.
     */
    public void selectFeature(String id, Geometry geom) {
        ExtendedLayerGraphics selectionLayer =
                (ExtendedLayerGraphics) getSolaLayers().get(SELECTION_LAYER_NAME);
        if (selectionLayer != null) {
            selectionLayer.addFeature(id, geom, null);
        }
    }

    /**
     * Clears all features from the selection layer.
     */
    public void clearSelectedFeatures() {
        ExtendedLayerGraphics selectionLayer =
                (ExtendedLayerGraphics) getSolaLayers().get(SELECTION_LAYER_NAME);
        if (selectionLayer != null
                && selectionLayer.getFeatureCollection().size() > 0) {
            selectionLayer.removeFeatures();
        }
    }
}
