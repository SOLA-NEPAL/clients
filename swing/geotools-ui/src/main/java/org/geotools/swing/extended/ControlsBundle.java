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
 * ControlsBundle.java
 *
 * Created on Apr 18, 2011, 5:41:25 PM
 */
package org.geotools.swing.extended;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import org.geotools.swing.control.JCoordsStatusBarItem;
import org.geotools.swing.control.JMapStatusBar;
import org.geotools.swing.control.JRendererStatusBarItem;
import org.geotools.swing.control.extended.Toc;
import org.geotools.swing.extended.exception.InitializeMapException;
import org.geotools.swing.extended.util.Messaging;
import org.geotools.swing.mapaction.extended.FullExtent;
import org.geotools.swing.mapaction.extended.ZoomOutAction;
import org.geotools.swing.tool.extended.ExtendedPan;
import org.geotools.swing.tool.extended.ExtendedZoominTool;

/**
 * This is a bundle of controls that form a single control with:<br/> a map
 * control, <br/> toolbar for adding tools and commands for the map, <br/> a
 * statusbar, <br/> a table of layers <br/> This is used to integrate in other
 * forms or controls easily. The control is layouted in design mode.
 *
 * @author Elton Manoku
 */
public class ControlsBundle extends javax.swing.JPanel {

    private Map map;
    private JTabbedPane leftPanel;
    private Toc toc;

    /**
     * Creates new ControlsBundle
     */
    public ControlsBundle() {
        initComponents();
        pnlStatusBarContainer.setBackground(new Color(224, 224, 224));
    }

    /**
     * It sets up the control with a table of contents/ layers.
     *
     * @param srid The srid
     * @param wktOfReferenceSystem the WKT definition of Reference system if not
     * found in the srid.properties resource file. If found there there is not
     * need to specify.
     * @param withToc
     * @throws InitializeMapException
     */
    public void Setup(int srid, String wktOfReferenceSystem, boolean withToc)
            throws InitializeMapException {
        initialize(srid, wktOfReferenceSystem);
        //Set default tool
        getMap().getToolItemByName(ExtendedPan.NAME).setSelected(true);
        setupLeftPanel();
    }

    /**
     * Internal method to initialize the control. It handles also the layouting.
     *
     * @param srid
     * @param wktOfReferenceSystem
     * @throws InitializeMapException
     */
    private void initialize(int srid, String wktOfReferenceSystem) throws InitializeMapException {
        if (wktOfReferenceSystem == null) {
            map = new Map(srid);
        } else {
            map = new Map(srid, wktOfReferenceSystem);
        }
        pnlMapContent.setLayout(new BorderLayout());
        pnlMapContent.add(map, BorderLayout.CENTER);

        setupToolbar();
        setupStatusBar();
    }

    /**
     * It adds a left panel to the bundle. In the left panel can be added
     * different kinds of panels. <br/> Table of Contents is added to this
     * panel. It links also the TOC with the Map.
     */
    protected void setupLeftPanel() {
        leftPanel = new JTabbedPane();
        pnlToc.setLayout(new BorderLayout());
        pnlToc.add(leftPanel, BorderLayout.CENTER);
        toc = new Toc();
        toc.setMap(map);
        map.setToc(toc);
        addInLeftPanel(Messaging.getInstance().getMessageText(
                Messaging.Ids.LEFT_PANEL_TAB_LAYERS_TITLE.toString()), toc);
    }

    /**
     * It adds in the left panel a tab with the panel provided.
     *
     * @param title The title to appear in the ne tab
     * @param panel The panel to add
     */
    protected void addInLeftPanel(String title, JPanel panel) {
        leftPanel.insertTab(title, null, panel, null, leftPanel.getTabCount());
    }

    /**
     * It starts up the statusbar. It adds the basic navigation commands and
     * tools.
     */
    private void setupToolbar() {
        getMap().addMapAction(new FullExtent(getMap()), mapToolbar, true);
        getMap().addMapAction(new ZoomOutAction(getMap()), mapToolbar, true);
        getMap().addTool(new ExtendedZoominTool(), mapToolbar, true);
        getMap().addTool(new ExtendedPan(), mapToolbar, true);
    }

    /** Disables/enables toolbar components. */
    public void enableToolbar(boolean isEnabled){
        getMap().getMapActionByName("fullextent").setEnabled(isEnabled);
        getMap().getMapActionByName("zoomout").setEnabled(isEnabled);
        getMap().getMapActionByName("zoomin").setEnabled(isEnabled);
        getMap().getMapActionByName(ExtendedPan.NAME).setEnabled(isEnabled);
    }
    
    /**
     * It starts up the Statusbar.
     */
    private void setupStatusBar() {
        pnlStatusbar.setLayout(new BorderLayout());
        JMapStatusBar statusBar = new JMapStatusBar();
        statusBar.addItem(new JRendererStatusBarItem(map), false, true);
        statusBar.addItem(new JCoordsStatusBarItem(map));
        pnlDatasetName.setMapPane(map);
        pnlStatusbar.add(statusBar, BorderLayout.CENTER);
    }

    /**
     * Gets the map control
     *
     * @return
     */
    public Map getMap() {
        return map;
    }

    /**
     * Get the toolbar
     *
     * @return
     */
    public JToolBar getToolbar() {
        return mapToolbar;
    }

    /**
     * Gets the Table of Contents
     *
     * @return
     */
    public Toc getToc() {
        return toc;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMap = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlToc = new javax.swing.JPanel();
        pnlMapContent = new javax.swing.JPanel();
        mapToolbar = new javax.swing.JToolBar();
        pnlStatusBarContainer = new javax.swing.JPanel();
        pnlDatasetName = new org.geotools.swing.control.DatasetNameItem();
        pnlStatusbar = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setName("Form"); // NOI18N

        pnlMap.setName("pnlMap"); // NOI18N

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setLastDividerLocation(250);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/geotools/swing/extended/Bundle"); // NOI18N
        jSplitPane1.setName(bundle.getString("ControlsBundle.jSplitPane1.name")); // NOI18N

        pnlToc.setName(bundle.getString("ControlsBundle.pnlToc.name")); // NOI18N

        javax.swing.GroupLayout pnlTocLayout = new javax.swing.GroupLayout(pnlToc);
        pnlToc.setLayout(pnlTocLayout);
        pnlTocLayout.setHorizontalGroup(
            pnlTocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );
        pnlTocLayout.setVerticalGroup(
            pnlTocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(pnlToc);

        pnlMapContent.setName(bundle.getString("ControlsBundle.pnlMapContent.name")); // NOI18N

        javax.swing.GroupLayout pnlMapContentLayout = new javax.swing.GroupLayout(pnlMapContent);
        pnlMapContent.setLayout(pnlMapContentLayout);
        pnlMapContentLayout.setHorizontalGroup(
            pnlMapContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );
        pnlMapContentLayout.setVerticalGroup(
            pnlMapContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(pnlMapContent);

        javax.swing.GroupLayout pnlMapLayout = new javax.swing.GroupLayout(pnlMap);
        pnlMap.setLayout(pnlMapLayout);
        pnlMapLayout.setHorizontalGroup(
            pnlMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        pnlMapLayout.setVerticalGroup(
            pnlMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        mapToolbar.setFloatable(false);
        mapToolbar.setRollover(true);
        mapToolbar.setName("mapToolbar"); // NOI18N

        pnlStatusBarContainer.setName(bundle.getString("ControlsBundle.pnlStatusBarContainer.name")); // NOI18N

        pnlDatasetName.setName(bundle.getString("ControlsBundle.pnlDatasetName.name")); // NOI18N

        pnlStatusbar.setName("pnlStatusbar"); // NOI18N

        javax.swing.GroupLayout pnlStatusbarLayout = new javax.swing.GroupLayout(pnlStatusbar);
        pnlStatusbar.setLayout(pnlStatusbarLayout);
        pnlStatusbarLayout.setHorizontalGroup(
            pnlStatusbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlStatusbarLayout.setVerticalGroup(
            pnlStatusbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlStatusBarContainerLayout = new javax.swing.GroupLayout(pnlStatusBarContainer);
        pnlStatusBarContainer.setLayout(pnlStatusBarContainerLayout);
        pnlStatusBarContainerLayout.setHorizontalGroup(
            pnlStatusBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusBarContainerLayout.createSequentialGroup()
                .addComponent(pnlStatusbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDatasetName, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlStatusBarContainerLayout.setVerticalGroup(
            pnlStatusBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStatusBarContainerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlStatusBarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlStatusbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDatasetName, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mapToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlMap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlStatusBarContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mapToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStatusBarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar mapToolbar;
    private org.geotools.swing.control.DatasetNameItem pnlDatasetName;
    private javax.swing.JPanel pnlMap;
    private javax.swing.JPanel pnlMapContent;
    private javax.swing.JPanel pnlStatusBarContainer;
    private javax.swing.JPanel pnlStatusbar;
    private javax.swing.JPanel pnlToc;
    // End of variables declaration//GEN-END:variables
}
