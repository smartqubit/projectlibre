/*
The contents of this file are subject to the Common Public Attribution License
Version 1.0 (the "License"); you may not use this file except in compliance with
the License. You may obtain a copy of the License at
http://www.projity.com/license . The License is based on the Mozilla Public
License Version 1.1 but Sections 14 and 15 have been added to cover use of
software over a computer network and provide for limited attribution for the
Original Developer. In addition, Exhibit A has been modified to be consistent
with Exhibit B.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
specific language governing rights and limitations under the License. The
Original Code is OpenProj. The Original Developer is the Initial Developer and
is Projity, Inc. All portions of the code written by Projity are Copyright (c)
2006, 2007. All Rights Reserved. Contributors Projity, Inc.

Alternatively, the contents of this file may be used under the terms of the
Projity End-User License Agreeement (the Projity License), in which case the
provisions of the Projity License are applicable instead of those above. If you
wish to allow use of your version of this file only under the terms of the
Projity License and not to allow others to use your version of this file under
the CPAL, indicate your decision by deleting the provisions above and replace
them with the notice and other provisions required by the Projity  License. If
you do not delete the provisions above, a recipient may use your version of this
file under either the CPAL or the Projity License.

[NOTE: The text of this license may differ slightly from the text of the notices
in Exhibits A and B of the license at http://www.projity.com/license. You should
use the latest text at http://www.projity.com/license for your modifications.
You may not remove this license text from the source files.]

Attribution Information: Attribution Copyright Notice: Copyright © 2006, 2007
Projity, Inc. Attribution Phrase (not exceeding 10 words): Powered by OpenProj,
an open source solution from Projity. Attribution URL: http://www.projity.com
Graphic Image as provided in the Covered Code as file:  openproj_logo.png with
alternatives listed on http://www.projity.com/logo

Display of Attribution Information is required in Larger Works which are defined
in the CPAL as a work which combines Covered Code or portions thereof with code
not governed by the terms of the CPAL. However, in addition to the other notice
obligations, all copies of the Covered Code in Executable and Source Code form
distributed must, as a form of attribution of the original author, include on
each user interface screen the "OpenProj" logo visible to all users.  The
OpenProj logo should be located horizontally aligned with the menu bar and left
justified on the top left of the screen adjacent to the File menu.  The logo
must be at least 100 x 25 pixels.  When users click on the "OpenProj" logo it
must direct them back to http://www.projity.com.
*/
package com.projity.pm.graphic.graph;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.projity.dialog.DependencyDialog;
import com.projity.graphic.configuration.GraphicConfiguration;
import com.projity.pm.graphic.IconManager;
import com.projity.pm.graphic.model.cache.GraphicDependency;
import com.projity.pm.graphic.model.cache.GraphicNode;

/**
 *
 */
public abstract class GraphInteractor implements MouseListener, MouseMotionListener, MouseWheelListener, Serializable{
	protected static final int NOTHING_SELECTED=0;
	protected static final int LINK_CREATION=1;
	protected static final int LINK_SELECTION=2;
	protected static final int BAR_MOVE=3;
	protected GraphUI ui;
	protected int state;
	protected Object selected=null;
	protected GraphZone selectedZone=null;
	protected boolean selection;
	protected double x0,y0;
	protected GraphicConfiguration config;
	protected GraphPopupMenu popup=null;
	protected DependencyDialog dependencyPropertiesDialog;
	/**
	 *
	 */
	public GraphInteractor(GraphUI ui) {
		this.ui=ui;
		config=GraphicConfiguration.getInstance();
		state=NOTHING_SELECTED;
		selection=true;
		init();
	}

	protected void init(){
    	ui.getGraph().addMouseListener(this);
    	ui.getGraph().addMouseMotionListener(this);
    	
    	defaultCursor=getGraph().getCursor();
    }

    protected void reset(){
     	lastShadowX=-1;
     	lastShadowY=-1;
    	lastLinkShadowX=-1;
    	lastLinkShadowY=-1;
       	sourceNode=null;
    	destinationNode=null;
    	selection=true;
    }

    protected boolean selectedIsNonSummaryNode(){
    	return ((selected instanceof GraphicNode)&&!((GraphicNode)selected).isSummary());
    }
    public Graph getGraph(){
    	return ui.getGraph();
    }


	public void showDependencyPropertiesDialog(GraphicDependency dependency) {
    	if (dependencyPropertiesDialog == null) {
    		Frame parent=JOptionPane.getFrameForComponent(getGraph());
    		dependencyPropertiesDialog = new DependencyDialog(parent,dependency.getDependency());
    	}
    	boolean didAction = DependencyDialog.doDialog(dependencyPropertiesDialog,dependency.getDependency());
    }


    //CURSORS
    protected Cursor defaultCursor;
	protected Cursor progressCursor;
	protected Cursor getProgressCursor(){
        if (progressCursor==null){
            try{
            	progressCursor=Toolkit.getDefaultToolkit().createCustomCursor(
              		IconManager.getImage("gantt.progress.cursor"),
					new Point(15, 5),
					"ProgressCursor");
            }catch (Exception e) {
              progressCursor=new Cursor(Cursor.HAND_CURSOR);
            }
        }
      return progressCursor;
	}
	protected Cursor linkCursor;
	protected Cursor getLinkCursor(){
        if (linkCursor==null){
            try{
            	linkCursor=Toolkit.getDefaultToolkit().createCustomCursor(
              		IconManager.getImage("gantt.link.cursor"),
					new Point(7,3),
					"linkCursor");
            }catch (Exception e) {
            	linkCursor=new Cursor(Cursor.HAND_CURSOR);
            }
        }
        return linkCursor;
	}

	protected Cursor splitCursor;
	protected Cursor getSplitCursor(){
        if (splitCursor==null){
            try{
            	splitCursor=Toolkit.getDefaultToolkit().createCustomCursor(
              		IconManager.getImage("gantt.split.cursor"),
					new Point(10,4),
					"splitCursor");
            }catch (Exception e) {
                splitCursor=new Cursor(Cursor.HAND_CURSOR);
            }
        }
        return splitCursor;
	}


    public Cursor selectCursor(){
    	Cursor cursor=defaultCursor;
    	switch (state) {
		case BAR_MOVE:
			cursor=new Cursor(Cursor.MOVE_CURSOR);
			break;
		case LINK_CREATION:
			cursor=getLinkCursor();
			break;
		case LINK_SELECTION:
			cursor=new Cursor(Cursor.CROSSHAIR_CURSOR);
			break;
		}
    	getGraph().setCursor(cursor);
    	return  cursor;
    }

    //Drawings
    protected void findState(double x,double y){
		state=NOTHING_SELECTED;
		if (selected==null) return;
		if (selected instanceof GraphicNode){
			computeNodeSelection(x,y);
		}else if (selected instanceof GraphicDependency){
			state=LINK_SELECTION;
		}
    }

    protected Graphics2D initGraphics(){
    	Graph graph=getGraph();
    	Graphics2D g=(Graphics2D)graph.getGraphics();
		g.setColor(graph.getForeground());
		g.setXORMode(graph.getBackground().darker());
		return g;
    }

    protected double lastShadowX=-1;
    protected double lastShadowY=-1;
    protected void drawBarShadow(double x,double y, boolean alternate){
    	if (x==-1) return;
		Graphics2D g=initGraphics();

		Shape bounds=getBarShadowBounds(x,y);
		if (bounds==null) return;
		g.setStroke(new BasicStroke(3));
		g.draw(bounds);
		if (alternate){
			lastShadowX=(lastShadowX==-1)?x:-1;
			lastShadowY=(lastShadowY==-1)?y:-1;
		}
    }



    protected GraphicNode sourceNode=null;
    protected GraphicNode destinationNode=null;
    protected void drawLinkSelectionBarShadow(GraphicNode node){
    	if (node==null) return;
    	Graphics2D g=initGraphics();
		Rectangle2D selectionRectangle=getLinkSelectionShadowBounds(node);
		if (selectionRectangle==null) return;
		g.setStroke(new BasicStroke(3));
		g.draw(selectionRectangle);
    }



    protected double lastLinkShadowX=-1;
    protected double lastLinkShadowY=-1;
    protected double x0link,y0link;
    private void drawLinkShadow(double x,double y,boolean alternate){
    	if (x==-1||y==-1) return;
		Graphics2D g=initGraphics();

		Line2D line=new Line2D.Double(x0link,y0link,x,y);
		g.setStroke(new BasicStroke(2));
		g.draw(line);

		if (alternate){
			lastLinkShadowX=(lastLinkShadowX==-1)?x:-1;
			lastLinkShadowY=(lastLinkShadowY==-1)?y:-1;
		}
    }


    //Mouse
    public void mouseClicked(MouseEvent e){}
    public void mouseWheelMoved(MouseWheelEvent e){}


    public void mousePressed(MouseEvent e){
    	if (isReadOnly()) return;
    	if (SwingUtilities.isRightMouseButton(e)){
    		if (popup!=null) popup.show(getGraph(),e.getX(),e.getY());
    	}else{
	    	if (selected==null) return;
	    	if (isMove()){
	    		selection=false;
	    		x0=e.getX();
	    		y0=e.getY();
	    		drawBarShadow(x0,y0,true);
	    	}else if (isDirectAction()){
	    		executeAction(e.getX(),e.getY());
	    		state=NOTHING_SELECTED;
	    		//select(e.getX(),e.getY()); //TODO commented to avoid second action when spliting
	    	}
    	}
    }

    public void mouseReleased(MouseEvent e){
    	if (isReadOnly()) return;
    	if (!SwingUtilities.isLeftMouseButton(e)) return;
    	if (selected==null||state==NOTHING_SELECTED) return;
		if (isRepaintOnRelease()) getGraph().repaint();

    	double x1=e.getX();
    	double y1=e.getY();
    	executeAction(x1,y1);
    	reset();
    	findState(x1,y1);
    }

    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    private void scrollToVisible(int x,int y){
    	int scrollingDistance=100;
    	getGraph().scrollRectToVisible(new Rectangle(x-scrollingDistance,y-scrollingDistance,scrollingDistance*2,scrollingDistance*2));
    }
    protected boolean allowLinkSelectionToMove(){
    	return false;
    }
    protected int beforeLinkState=0;
    public void mouseDragged(MouseEvent e){
    	if (isReadOnly()) return;
    	if (!SwingUtilities.isLeftMouseButton(e)) return;
    	if (selected instanceof GraphicNode){
    		GraphicNode node=(GraphicNode)selected;
    		boolean sw=switchOnLinkCreation(e.getX(),e.getY());
    		if (state!=LINK_CREATION&&sw){
    			drawBarShadow(lastShadowX,lastLinkShadowY,true);
    			beforeLinkState=state;
    			state=LINK_CREATION;
    			selectCursor();

    			sourceNode=(GraphicNode)selected;
    			drawLinkSelectionBarShadow(sourceNode);

    			setLinkOrigin();
    		}
    		else if (state==LINK_CREATION&&!sw&&allowLinkSelectionToMove()){
    			drawLinkShadow(lastLinkShadowX,lastLinkShadowY,true);
    			drawBarShadow(lastShadowX,lastLinkShadowY,true);
    			state=beforeLinkState;
    			selectCursor();

    			sourceNode=null;

    		}
    	}
    	if (state==LINK_CREATION){
    		GraphZone zone=ui.getNodeAt(e.getX(),e.getY());
			GraphicNode newDestinationNode=zone==null?null:(GraphicNode)zone.getObject();
			drawLinkSelectionBarShadow(destinationNode);
    		drawLinkShadow(lastLinkShadowX,lastLinkShadowY,true);
    		scrollToVisible(e.getX(),e.getY());
			drawLinkShadow(e.getX(),e.getY(),true);
			if (newDestinationNode!=null && newDestinationNode.isLinkable() && sourceNode != newDestinationNode){
				destinationNode=newDestinationNode;
				drawLinkSelectionBarShadow(destinationNode);
			}else destinationNode=null;
    	}else if (isMove()){
    		drawBarShadow(lastShadowX,lastShadowY,true);
    		scrollToVisible(e.getX(),e.getY());
    		drawBarShadow(e.getX(),e.getY(),true);
    	}
    }
//    public void mouseDragged(MouseEvent e){
//    	if (isReadOnly()) return;
//    	if (!SwingUtilities.isLeftMouseButton(e)) return;
//    	if (state!=LINK_CREATION&&selected instanceof GraphicNode){
//    		GraphicNode node=(GraphicNode)selected;
//    		if (switchOnLinkCreation(e.getX(),e.getY())){
//    			drawBarShadow(lastShadowX,lastLinkShadowY,true);
//    			state=LINK_CREATION;
//    			selectCursor();
//
//    			sourceNode=(GraphicNode)selected;
//    			drawLinkSelectionBarShadow(sourceNode);
//
//    			setLinkOrigin();
//    		}
//    	}
//    	if (state==LINK_CREATION){
//			GraphicNode newDestinationNode=ui.getNodeAt(e.getX(),e.getY());
//			drawLinkSelectionBarShadow(destinationNode);
//    		drawLinkShadow(lastLinkShadowX,lastLinkShadowY,true);
//    		scrollToVisible(e.getX(),e.getY());
//			drawLinkShadow(e.getX(),e.getY(),true);
//			if (newDestinationNode!=null && newDestinationNode.isLinkable() && sourceNode != newDestinationNode){
//				destinationNode=newDestinationNode;
//				drawLinkSelectionBarShadow(destinationNode);
//			}else destinationNode=null;
//    	}else if (isMove()){
//    		drawBarShadow(lastShadowX,lastShadowY,true);
//    		scrollToVisible(e.getX(),e.getY());
//    		drawBarShadow(e.getX(),e.getY(),true);
//    	}
//    }

    public void mouseMoved(MouseEvent e){
    	if (isReadOnly()) return;
    	select(e.getX(),e.getY());
    }




    public boolean isReadOnly(){
    	return getGraph().getModel().isReadOnly();
    }








    protected abstract void computeNodeSelection(double x,double y);
    protected abstract Shape getBarShadowBounds(double x,double y);
    protected abstract Rectangle2D getLinkSelectionShadowBounds(GraphicNode node);


    protected abstract void setLinkOrigin();
    protected abstract boolean switchOnLinkCreation(double x, double y);

    public abstract boolean executeAction(double x,double y);




    protected void select(int x,int y){
    	if (selection){
       		selectedZone=ui.getObjectAt(x,y);
    		if (selectedZone!=null) selected=selectedZone.getObject();
	    	if (selected==null){
	    		state=NOTHING_SELECTED;
	    	}else{
	    		 findState(x,y);
	    	}
	    	selectCursor();
    	}
    }

    protected boolean isMove(){
    	return state==BAR_MOVE;
    }
    protected boolean isDirectAction(){
    	return state==LINK_SELECTION;
    }
    protected boolean isRepaintOnRelease(){
    	return state==BAR_MOVE||state==LINK_CREATION;
    }




}
