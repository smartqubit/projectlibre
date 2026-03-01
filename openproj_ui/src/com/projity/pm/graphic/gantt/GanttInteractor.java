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

Attribution Information: Attribution Copyright Notice: Copyright � 2006, 2007
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
package com.projity.pm.graphic.gantt;

import java.awt.Cursor;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.projity.association.InvalidAssociationException;
import com.projity.functor.IntervalConsumer;
import com.projity.pm.dependency.DependencyService;
import com.projity.pm.dependency.DependencyType;
import com.projity.pm.dependency.HasDependencies;
import com.projity.pm.graphic.graph.GraphInteractor;
import com.projity.pm.graphic.graph.GraphUI;
import com.projity.pm.graphic.model.cache.GraphicDependency;
import com.projity.pm.graphic.model.cache.GraphicNode;
import com.projity.pm.graphic.timescale.CoordinatesConverter;
import com.projity.pm.scheduling.Schedule;
import com.projity.pm.scheduling.ScheduleInterval;
import com.projity.pm.scheduling.ScheduleService;
import com.projity.util.Alert;
import com.projity.util.ClassUtils;

/**
 *
 */
public class GanttInteractor extends GraphInteractor{
	private static final long serialVersionUID = -555882007216388246L;
	protected static final int BAR_MOVE_START=4;
	protected static final int BAR_MOVE_END=5;
	protected static final int PROGRESS_BAR_MOVE=6;
	protected static final int SPLIT=7;

	protected ScheduleInterval selectedInterval;
	protected int selectedIntervalNumber;
	protected double t;
	/**
	 *
	 */
	public GanttInteractor(GraphUI ui) {
		super(ui);
		popup=new GanttPopupMenu(this);
	}


    private class NodeSelectionIntervalConsumer implements IntervalConsumer{
    	private boolean consumed=false;
    	private GraphicNode node;
    	private double deltaResize0;
    	private double deltaResize1;
    	private double deltaOutside;
		private double completedDeltaT0;
		private double completedDeltaT1;
    	private ScheduleInterval completedInterval;
		private double t;
		private CoordinatesConverter coord;


    	public NodeSelectionIntervalConsumer init(double x,GraphicNode node){
    		this.t=getCoord().toTime(x);
    		consumed=false;
    		selectedInterval=null;
    		completedInterval=null;
    		selectedIntervalNumber=0;
//    		GraphicNode node=(GraphicNode)selected;
//    		long completedT=node.getCompleted();
    		coord=getCoord();
    		completedDeltaT0=coord.toDuration(config.getSelectionProgress0());
    		completedDeltaT1=coord.toDuration(config.getSelectionProgress1());
    		deltaResize0=coord.toDuration(config.getSelectionResize0());
    		deltaResize1=coord.toDuration(config.getSelectionResize1());
    		deltaOutside=coord.toDuration(config.getSelectionSquare());
    		this.node=node;
    		return this;
    	}
    	public void consumeInterval(ScheduleInterval interval){
    		if (consumed) return; //Consumer need to consume all the intervals
    		if (ClassUtils.isObjectReadOnly(((GraphicNode)selected).getNode().getImpl())) // pre
    			return;
			if (coord!=null){
				long completedT=((GraphicNode)selected).getCompleted();
	    		if (completedT>=interval.getStart()&&completedT<=interval.getEnd()){
	    			completedInterval=new ScheduleInterval(interval.getStart(),completedT);
	    			if (interval.getEnd()>interval.getStart()) completedInterval=coord.adaptSmallBarTimeInterval(completedInterval,node,config);
	    		}
				interval=coord.adaptSmallBarTimeInterval(interval, node,config);

			}
    		if (t<interval.getStart()&&t>=interval.getStart()-deltaOutside){
    			if (selectedIsNonSummaryNode()) state=BAR_MOVE_START;
    		}else if (t>interval.getEnd()&&t<=interval.getEnd()+deltaOutside){
    			if (selectedIsNonSummaryNode()) state=BAR_MOVE_END;
    		}else if (t<interval.getStart()||t>interval.getEnd()){
    			selectedIntervalNumber++;
    			return;
       		}else if (completedInterval!=null&&t>=completedInterval.getEnd()-completedDeltaT0&&t<=completedInterval.getEnd()+completedDeltaT1&&selectedZone!=null&&selectedZone.getZoneId()==GanttUI.PROGRESS_BAR_ZONE_ID){
       			if (selectedIsNonSummaryNode()) state=PROGRESS_BAR_MOVE;
    		}else if (t<=interval.getStart()+deltaResize0){
    			if (selectedIsNonSummaryNode()) state=BAR_MOVE_START;
    		}else if (t>=interval.getEnd()-deltaResize1){
    			if (selectedIsNonSummaryNode()) state=BAR_MOVE_END;
    		} else state= BAR_MOVE;
    		selectedInterval=interval;
			consumed=true;
     	}
    }
    private NodeSelectionIntervalConsumer nodeSelectionIntervalConsumer=new NodeSelectionIntervalConsumer();

    protected void computeNodeSelection(double x,double y){
		//would have prefered an iterator
    	GraphicNode node=(GraphicNode)selected;
		node.consumeIntervals(nodeSelectionIntervalConsumer.init(x,node));
    }


    protected Shape getBarShadowBounds(double x,double y){
		double deltaX=x-x0;
		CoordinatesConverter coord=getCoord();
		GraphicNode node=(GraphicNode)selected;
		Rectangle2D bounds;
		double xStart=coord.toX((selectedIntervalNumber==0&&state==BAR_MOVE)?node.getStart():selectedInterval.getStart());
		if (state==PROGRESS_BAR_MOVE){
			double completedX=coord.toX(node.getCompleted());//CoordinatesConverter.adaptSmallBarEndX(xStart,coord.toX(node.getCompleted()),config);
			bounds=new Rectangle2D.Double(xStart,((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset()+(node.getGanttShapeHeight()-config.getGanttProgressBarHeight())/2,completedX-xStart+deltaX,config.getGanttProgressBarHeight());
		}else{
			double xEnd=(selectedIntervalNumber==0&&state==BAR_MOVE)?CoordinatesConverter.adaptSmallBarEndX(coord.toX(node.getStart()),coord.toX(node.getEnd()),node,config):coord.toX(selectedInterval.getEnd());
			double w=xEnd-xStart;
			switch (state) {
			case BAR_MOVE:
				bounds=new Rectangle2D.Double(xStart+deltaX,((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset(),w,node.getGanttShapeHeight());
				break;
			case BAR_MOVE_START:
				bounds=new Rectangle2D.Double(xStart+deltaX,((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset(),w-deltaX,node.getGanttShapeHeight());
				break;
			case BAR_MOVE_END:
				bounds=new Rectangle2D.Double(xStart,((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset(),w+deltaX,node.getGanttShapeHeight());
				break;
			default:
				return null;
			}
		}
		return bounds;
    }
    protected Rectangle2D getLinkSelectionShadowBounds(GraphicNode node){
		CoordinatesConverter coord=getCoord();
		double xStart=coord.toX(node.getStart());
		double xEnd=coord.toX(node.getEnd());
		xEnd=CoordinatesConverter.adaptSmallBarEndX(xStart,xEnd,node,config);
		Rectangle2D selectionRectangle=new Rectangle2D.Double(xStart,((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset(),xEnd-xStart,node.getGanttShapeHeight());
		return selectionRectangle;
    }


    public CoordinatesConverter getCoord(){
    	return ((GanttUI)ui).getCoord();
    }

    protected void setLinkOrigin(){
    	GraphicNode node=(GraphicNode)selected;
		CoordinatesConverter coord=getCoord();
		double xStart=coord.toX((selectedIntervalNumber==0)?node.getStart():selectedInterval.getStart());
		double xEnd=selectedIntervalNumber==0?CoordinatesConverter.adaptSmallBarEndX(coord.toX(node.getStart()),coord.toX(node.getEnd()),node,config):coord.toX(selectedInterval.getEnd());
		x0link=(xStart+xEnd)/2;
		y0link=((GanttUI)ui).getBarY(node.getRow())+node.getGanttShapeOffset()+node.getGanttShapeHeight()/2;

    }

    protected boolean allowLinkSelectionToMove(){
    	return beforeLinkState==BAR_MOVE||beforeLinkState==BAR_MOVE_START||beforeLinkState==BAR_MOVE_END;
    }

    protected boolean switchOnLinkCreation(double x, double y){
    	if (state==PROGRESS_BAR_MOVE) return false;
		GraphicNode node=(GraphicNode)selected;
		Object impl = node.getNode().getImpl();
		return impl instanceof HasDependencies &&
				((int)y)/((Gantt)getGraph()).getRowHeight()!=node.getRow() ;
    }

    public Cursor selectCursor(){
    	Cursor cursor=null;
    	switch (state) {
		case BAR_MOVE_START:
			cursor=new Cursor(Cursor.W_RESIZE_CURSOR);
			break;
		case BAR_MOVE_END:
			cursor=new Cursor(Cursor.E_RESIZE_CURSOR);
			break;
		case PROGRESS_BAR_MOVE:
			cursor=getProgressCursor();
			break;
		case SPLIT:
			cursor=getSplitCursor();
			break;
		}
    	if (cursor==null) super.selectCursor();
    	else getGraph().setCursor(cursor);
    	return cursor;
    }

    public boolean executeAction(double x,double y){
    	if (x==x0||selected==null) return false;
    	if (state==BAR_MOVE||state==BAR_MOVE_START||state==BAR_MOVE_END||state==PROGRESS_BAR_MOVE||state==SPLIT){
    		if (!(selected instanceof GraphicNode)) return false;
    		sourceNode=(GraphicNode)selected;
    	}
    	long t=(long)getCoord().toTime(x);
    	long dt=(long)getCoord().toDuration(x-x0);
    	switch (state) {
		case BAR_MOVE:
			ScheduleService.getInstance().setInterval(this,(Schedule)sourceNode.getNode().getImpl(),selectedInterval.getStart()+dt,selectedInterval.getEnd()+dt,selectedInterval,ui.getGraph().getProject().getUndoController().getEditSupport());
			return true;
		case BAR_MOVE_START:
			ScheduleService.getInstance().setInterval(this,(Schedule)sourceNode.getNode().getImpl(),selectedInterval.getStart()+dt,selectedInterval.getEnd(),selectedInterval,ui.getGraph().getProject().getUndoController().getEditSupport());
			return true;
		case BAR_MOVE_END:
			ScheduleService.getInstance().setInterval(this,(Schedule)sourceNode.getNode().getImpl(),selectedInterval.getStart(),selectedInterval.getEnd()+dt,selectedInterval,ui.getGraph().getProject().getUndoController().getEditSupport());
			return true;
		case PROGRESS_BAR_MOVE:
			ScheduleService.getInstance().setCompleted(this,(Schedule)sourceNode.getNode().getImpl(),t,ui.getGraph().getProject().getUndoController().getEditSupport());
			return true;
		case LINK_CREATION:
			try {
					if (sourceNode!=null&&destinationNode!=null&&
							sourceNode.getNode().getImpl() instanceof HasDependencies &&
							destinationNode.getNode().getImpl() instanceof HasDependencies && !ClassUtils.isObjectReadOnly(destinationNode.getNode().getImpl())){
						DependencyService.getInstance().newDependency((HasDependencies)sourceNode.getNode().getImpl(),(HasDependencies)destinationNode.getNode().getImpl(),DependencyType.FS,0,this);
					}
				} catch (InvalidAssociationException e) {
					// Define the choices for the user
					Object[] options = {"OK", "Unlink Tasks"};
					
					// Show a dialog with a choice instead of just a message
					int choice = javax.swing.JOptionPane.showOptionDialog(
						null, 
						e.getMessage() + "\n\nWould you like to unlink these tasks to resolve the error?", 
						"Scheduling Error",
						javax.swing.JOptionPane.YES_NO_OPTION, 
						javax.swing.JOptionPane.WARNING_MESSAGE, 
						null, 
						options, 
						options[0]
					);

					// If the user clicks "Unlink Tasks" (which is option index 1)
					if (choice == 1) {
						// Logic to remove the attempted link
						System.out.println("User opted to unlink tasks.");
						// Note: ProjectLibre usually handles the revert automatically when the exception is thrown,
						// but providing this choice makes the UI much clearer for the user.
					}
				}
				return true;
		case LINK_SELECTION:
			showDependencyPropertiesDialog((GraphicDependency)selected);
			return true;
		case SPLIT:
			ScheduleService.getInstance().split(this,(Schedule)sourceNode.getNode().getImpl(),t,t,ui.getGraph().getProject().getUndoController().getEditSupport());
			return true;
		}
    	return false;
    }

    public void setSplitMode(){
    	state=SPLIT;
    	selectCursor();
    }





    protected void select(int x,int y){
    	if (selection){
    		selectedZone=ui.getObjectAt(x,y);
    		selected=selectedZone==null?null:selectedZone.getObject();
    		if (state==SPLIT) return;
	    	if (selected==null ){
	    		state=NOTHING_SELECTED;
	    	}else{
	    		 findState(x,y);
	    	}
	    	selectCursor();
    	}
    }

    protected boolean isMove(){
    	return state==BAR_MOVE||state==BAR_MOVE_END||state==BAR_MOVE_START||state==PROGRESS_BAR_MOVE;
    }
    protected boolean isDirectAction(){
    	return state==SPLIT||super.isDirectAction();
    }
    protected boolean isRepaintOnRelease(){
    	return state==BAR_MOVE||state==BAR_MOVE_END||state==BAR_MOVE_START||state==PROGRESS_BAR_MOVE||state==LINK_CREATION;
    }

}
