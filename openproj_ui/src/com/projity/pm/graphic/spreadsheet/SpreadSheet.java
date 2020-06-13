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
package com.projity.pm.graphic.spreadsheet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.Closure;

import com.projity.datatype.Hyperlink;
import com.projity.dialog.ResourceAdditionDialog;
import com.projity.field.Field;
import com.projity.graphic.configuration.ActionList;
import com.projity.graphic.configuration.CellStyle;
import com.projity.graphic.configuration.GraphicConfiguration;
import com.projity.grouping.core.Node;
import com.projity.grouping.core.NodeBridge;
import com.projity.grouping.core.NodeFactory;
import com.projity.help.HelpUtil;
import com.projity.job.Job;
import com.projity.job.JobRunnable;
import com.projity.menu.MenuActionConstants;
import com.projity.options.GeneralOption;
import com.projity.pm.graphic.frames.GraphicManager;
import com.projity.pm.graphic.model.cache.GraphicNode;
import com.projity.pm.graphic.model.cache.NodeModelCache;
import com.projity.pm.graphic.spreadsheet.common.CommonSpreadSheet;
import com.projity.pm.graphic.spreadsheet.common.CommonSpreadSheetAction;
import com.projity.pm.graphic.spreadsheet.common.CommonSpreadSheetModel;
import com.projity.pm.graphic.spreadsheet.common.transfer.NodeListTransferHandler;
import com.projity.pm.graphic.spreadsheet.editor.SimpleComboBoxEditor;
import com.projity.pm.graphic.spreadsheet.renderer.NameCellComponent;
import com.projity.pm.graphic.spreadsheet.selection.SpreadSheetListSelectionModel;
import com.projity.pm.graphic.spreadsheet.selection.SpreadSheetSelectionModel;
import com.projity.pm.graphic.spreadsheet.selection.event.HeaderMouseListener;
import com.projity.pm.resource.ResourceImpl;
import com.projity.pm.resource.ResourcePool;
import com.projity.pm.task.Project;
import com.projity.server.data.EnterpriseResourceData;
import com.projity.server.data.Serializer;
import com.projity.session.Session;
import com.projity.session.SessionFactory;
import com.projity.strings.Messages;
import com.projity.util.Alert;
import com.projity.util.BrowserControl;

/**
 * 
 */
public class SpreadSheet extends CommonSpreadSheet implements Cloneable {
	private static final long serialVersionUID = 5958334223191182318L;
	protected SpreadSheetPopupMenu popup=null;


	public SpreadSheet() {
		super();
		NodeListTransferHandler.registerWith(this);

	}

	protected void finalize() {
		System.out.println("SpreadSheet.finalize()" + this);
	}
	public void cleanUp() {
		if (getModel() instanceof CommonSpreadSheetModel)
			((CommonSpreadSheetModel) getModel()).getCache().removeNodeModelListener(this);
		super.cleanUp();
	}
	public void setCache(NodeModelCache cache, ArrayList fieldArray, CellStyle cellStyle, ActionList actionList) {
		// if (getCache()!=null) getCache().close();
		if (getCache() != null)
			getCache().getReference().close(); // deepClose
		
		SpreadSheetColumnModel colModel;
		TableColumnModel oldColModel=getColumnModel();
		if (oldColModel!=null&&oldColModel instanceof SpreadSheetColumnModel){
			if (((SpreadSheetColumnModel)oldColModel).getFieldArray()==fieldArray)
				colModel=(SpreadSheetColumnModel)oldColModel;
			else colModel= new SpreadSheetColumnModel(fieldArray);
		}else colModel= new SpreadSheetColumnModel(fieldArray);
		setModel(new SpreadSheetModel(cache, colModel, cellStyle, actionList), (colModel==oldColModel)?null:colModel);

	}

	public TableCellEditor getCellEditor(int row, int column) {
		SpreadSheetModel model=(SpreadSheetModel) getModel();
		Field field = model.getFieldInColumn(column + 1);
		GraphicNode node=model.getNode(row);
		if (field != null && (field.isDynamicOptions() || field.hasFilter())) {
			return new SimpleComboBoxEditor(new DefaultComboBoxModel(field.getOptions(node.getNode().getImpl())));
		} else {
			return super.getCellEditor(row, column);
		}
	}

	public void setFieldArray(ArrayList fieldArray) {
		((SpreadSheetColumnModel) getColumnModel()).setFieldArray(fieldArray);
		createDefaultColumnsFromModel(fieldArray);
		resizeAndRepaintHeader();

	}
	
	public void resizeAndRepaintHeader() {
		JTableHeader header = getTableHeader();
		SpreadSheetColumnModel tm = ((SpreadSheetColumnModel) getColumnModel());
		int colWidth = tm.getColWidth();// tm.getTotalColumnWidth(); //Hack,
										// colWidth isn't enough why?
		header.setPreferredSize(new Dimension(colWidth, header.getPreferredSize().height));
		header.resizeAndRepaint();
		
	}

	public void createDefaultColumnsFromModel(ArrayList fieldArray) {
			// Remove any current columns
			TableColumnModel cm = getColumnModel();
			while (cm.getColumnCount() > 0) {
				cm.removeColumn(cm.getColumn(0));
			}

			// Create new columns from the data model info
			int colCount=fieldArray.size();
			for (int i = 0; i < colCount; i++) {
				TableColumn newColumn = new TableColumn(i);
				addColumn(newColumn);
			}
			
//		TableModel m = getModel();
//		if (m != null) {
//			// Remove any current columns
//			TableColumnModel cm = getColumnModel();
//			while (cm.getColumnCount() > 0) {
//				cm.removeColumn(cm.getColumn(0));
//			}
//
//			// Create new columns from the data model info
//			for (int i = 0; i < m.getColumnCount(); i++) {
//				TableColumn newColumn = new TableColumn(i);
//				addColumn(newColumn);
//			}
//		}
	}

	private void makeCustomTableHeader(TableColumnModel columnModel) {
		JTableHeader h =new JTableHeader(columnModel) {

			public String getToolTipText(MouseEvent e) {
				if (isHasColumnHeaderPopup()) {
					int col = columnAtPoint(e.getPoint());
					Field f = ((SpreadSheetModel) getModel()).getFieldInNonTranslatedColumn(col + 1);
					if (f != null)
						return "<html>" + f.getName() + 
							"<br>" + Messages.getString("Text.rightClickToInsertRemoveColumns") + "</html>";
				}
				return super.getToolTipText(e);
			}
			
		};
		setTableHeader(h);
		
	}
	public SpreadSheetPopupMenu getPopup(){
		if (popup==null){
			popup = hasRowPopup() ? new SpreadSheetPopupMenu(this) : null;
		}
		return popup;
	}
	
	
	public void setModel(SpreadSheetModel spreadSheetModel, SpreadSheetColumnModel spreadSheetColumnModel) {
		makeCustomTableHeader(spreadSheetColumnModel);
		TableModel oldModel = getModel();
		setModel(spreadSheetModel);
		
		if (spreadSheetColumnModel!=null){
			//System.out.println("creating new ColModel");
			setColumnModel(spreadSheetColumnModel);
	
			selection = new SpreadSheetSelectionModel(this);
			selection.setRowSelection(new SpreadSheetListSelectionModel(selection, true));
			selection.setColumnSelection(new SpreadSheetListSelectionModel(selection, false));
			setSelectionModel(selection.getRowSelection());
			createDefaultColumnsFromModel(spreadSheetModel.getFieldArray()); //Consume memory
			getColumnModel().setSelectionModel(selection.getColumnSelection());
		}
		
		registerEditors(); //Consume memory
		initRowHeader(spreadSheetModel);
		initModel();
		initListeners();

		GraphicConfiguration config = GraphicConfiguration.getInstance();
		//fix for substance
		setTableHeader(createDefaultTableHeader());
		JTableHeader header = getTableHeader();
		header.setPreferredSize(new Dimension((int) header.getPreferredSize().getWidth(), config.getColumnHeaderHeight()));
		header.addMouseListener(new HeaderMouseListener(this));

		

		addMouseListener(new MouseAdapter() {
//			Cursor oldCursor = null;
//			public void mouseEntered(MouseEvent e) {
//				Point p = e.getPoint();
//				int col = columnAtPoint(p);
//				Field field = ((SpreadSheetModel) getModel()).getFieldInNonTranslatedColumn(col + 1);
//				System.out.println("mouse entered field " + field);
//				if (field != null && field.isHyperlink()) {
//					oldCursor = getCursor();
//					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//					System.out.println("setting new cursor to " + Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) + " old is " + oldCursor);
//				} else 
//					super.mouseEntered(e);
//
//			}
//
//			public void mouseExited(MouseEvent e) {
//				Point p = e.getPoint();
//				int col = columnAtPoint(p);
//				Field field = ((SpreadSheetModel) getModel()).getFieldInNonTranslatedColumn(col + 1);
//				System.out.println("mouse exited field " + field);
//				if (field != null && field.isHyperlink()) {
//					setCursor(oldCursor);
//					System.out.println("setting old cursor to " + oldCursor);
//					e.consume();
//				} else 
//					super.mouseEntered(e);
//			}

			public void mousePressed(MouseEvent e) { // changed to mousePressed instead of mouseClicked() for snappier handling 17/5/04 hk
				Point p = e.getPoint();
				int row = rowAtPoint(p);
				int col = columnAtPoint(p);
				SpreadSheetPopupMenu popup=getPopup();
				if (SwingUtilities.isLeftMouseButton(e)) {
					SpreadSheetColumnModel columnModel = (SpreadSheetColumnModel) getColumnModel();
					Field field = ((SpreadSheetModel) getModel()).getFieldInNonTranslatedColumn(col + 1);
					SpreadSheetModel model = (SpreadSheetModel) getModel();
					if (field.isNameField()) {
						// if (col == columnModel.getNameIndex()) {
						GraphicNode node = model.getNode(row);
						if (isOnIcon(e)) {
							if (model.getCellProperties(node).isCompositeIcon()) {
								finishCurrentOperations();
								selection.getRowSelection().clearSelection();
								boolean change = true;
								if (!node.isFetched()) // for subprojects
									change = node.fetch();
								if (change)
									model.changeCollapsedState(row);
								e.consume(); // prevent dbl click treatment below

								// because editor may have already been
								// installed we
								// have to update its collapsed state
								// updateNameCellEditor(node);

								// editCellAt(row,model.findGraphicNodeRow(node));
							}
						}
					} else if (field != null && field.isHyperlink()) {
						Hyperlink link = (Hyperlink) model.getValueAt(row, col+1);
						if (link != null) {
							BrowserControl.displayURL(link.getAddress());
							e.consume(); // prevent dbl click treatment below
						}
						
					}
					if (!e.isConsumed()) {
						if (e.getClickCount() == 2)  // if above code didn't treat and is dbl click
							doDoubleClick(row,col);
						else
							doClick(row,col);
					}
								
					
				} else if (popup != null && SwingUtilities.isRightMouseButton(e)) { // e.isPopupTrigger() can be used too
//					selection.getRowSelection().clearSelection();
//					selection.getRowSelection().addSelectionInterval(row, row);
					popup.setRow(row);
					popup.setCol(col);
					popup.show(SpreadSheet.this, e.getX(), e.getY());
				}
			}
		});

		if (oldModel != spreadSheetModel && oldModel instanceof CommonSpreadSheetModel)
			((CommonSpreadSheetModel) getModel()).getCache().removeNodeModelListener(this);
		spreadSheetModel.getCache().addNodeModelListener(this);

//		getColumnModel().addColumnModelListener(new TableColumnModelListener(){
//			public void columnAdded(TableColumnModelEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			public void columnMarginChanged(ChangeEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			public void columnMoved(TableColumnModelEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			public void columnRemoved(TableColumnModelEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			public void columnSelectionChanged(ListSelectionEvent e) {
//				System.out.println(((e.getValueIsAdjusting())?"lse=":"LSE=")+e.getFirstIndex()+", "+e.getLastIndex());
//				SpreadSheet.this.revalidate();
//				//SpreadSheet.this.paintImmediately(0, 0, getWidth(), GraphicConfiguration.getInstance().getColumnHeaderHeight());
//			}
//		});

	}
	
//    public void columnSelectionChanged(ListSelectionEvent e) {
//		System.out.println("JTable: "+((e.getValueIsAdjusting())?"lse=":"LSE=")+e.getFirstIndex()+", "+e.getLastIndex());
//    	super.columnSelectionChanged(e);
//    }
    

	public void doDoubleClick(int row, int col) {
		GraphicManager.getInstance(this).doInformationDialog(false);
	}
	public void doClick(int row, int col) {
		// override to treat cell clicks
	}
	/*
	 * public SpreadSheetPopupMenu getPopup() { return popup; } public void
	 * setPopup(SpreadSheetPopupMenu popup) { this.popup = popup; }
	 */

	public boolean isOnIcon(MouseEvent e) {
		Point p = e.getPoint();
		int row = rowAtPoint(p);
		int col = columnAtPoint(p);
		Rectangle bounds = getCellRect(row, col, false);
		SpreadSheetModel model = (SpreadSheetModel) getModel();
		GraphicNode node = model.getNode(row);
		return NameCellComponent.isOnIcon(new Point((int) (p.getX() - bounds.getX()), (int) (p.getY() - bounds.getY())), bounds.getSize(), model
				.getCache().getLevel(node));
	}

	public boolean isOnText(MouseEvent e) {
		Point p = e.getPoint();
		int row = rowAtPoint(p);
		int col = columnAtPoint(p);
		Rectangle bounds = getCellRect(row, col, false);
		SpreadSheetModel model = (SpreadSheetModel) getModel();
		GraphicNode node = model.getNode(row);
		return NameCellComponent.isOnText(new Point((int) (p.getX() - bounds.getX()), (int) (p.getY() - bounds.getY())), bounds.getSize(), model
				.getCache().getLevel(node));
	}

	public void updateNameCellEditor(GraphicNode node) {
		SpreadSheetColumnModel columnModel = (SpreadSheetColumnModel) getColumnModel();

		// if (isEditing() && getEditingColumn() == columnModel.getNameIndex()
		// && editorComp != null) {

		if (isEditing() && editorComp != null && ((SpreadSheetModel) getModel()).getFieldInColumn(getEditingColumn() + 1).isNameField()) {
			NameCellComponent c = (NameCellComponent) editorComp;
			SpreadSheetModel model = (SpreadSheetModel) getModel();
			// GraphicNode node = model.getNode(row);
			if (model.getCellProperties(node).isCompositeIcon())
				c.setCollapsed(node.isCollapsed());
		}
	}

	protected void initListeners() {
		addKeyListener(new KeyAdapter() { // TODO need to fix focus problems elsewhere for this to always work
			public void keyPressed(KeyEvent e) {
				int row = getSelectedRow();
				if (row < 0)
					return;
				CommonSpreadSheetModel model = (CommonSpreadSheetModel) getModel();
				if (e.getKeyCode() == KeyEvent.VK_INSERT)
					executeAction(MenuActionConstants.ACTION_NEW);
				else if (e.getKeyCode() == KeyEvent.VK_DELETE)
					executeAction(MenuActionConstants.ACTION_DELETE);
				else if (e.getKeyCode() == KeyEvent.VK_F3)
					GraphicManager.getInstance().doFind(SpreadSheet.this,null);
				else if (e.getKeyCode() == KeyEvent.VK_F && e.getModifiers()== KeyEvent.CTRL_MASK)
					GraphicManager.getInstance().doFind(SpreadSheet.this,null);
				// ALT + Right = Indent right
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && 
						(e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0)
				{
				   executeAction(MenuActionConstants.ACTION_INDENT);
				}
				// ALT + Left = Indent left
				else if (e.getKeyCode() == KeyEvent.VK_LEFT && 
				        (e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0)
				{
				   executeAction(MenuActionConstants.ACTION_OUTDENT);
				}
				// CTRL + L = Link Selected items
				else if (e.getKeyCode() == KeyEvent.VK_L && 
				        (e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0)
				{
				   executeAction(MenuActionConstants.ACTION_LINK);
				}


			}
		});

	}

	// Actions on selected nodes
	public List getSelectedGraphicNodes() {
		return rowsToGraphicNodes(getSelectedRows());
	}

	public List rowsToGraphicNodes(int[] rows) {
		if (rows == null || rows.length == 0)
			return new LinkedList();
		NodeModelCache cache = ((SpreadSheetModel) getModel()).getCache();
		return cache.getElementsAt(rows);
	}
	
	
	
	
	


	// gui actions
	public void executeAction(String actionId) {
		CommonSpreadSheetAction action = getAction(actionId);
		if (action == null) {
			System.out.println("No action for " + actionId);
			return;
		}
		action.setSpreadSheet(this);
		action.execute();
	}

	// init actions
	public CommonSpreadSheetAction prepareAction(String actionId) {
		CommonSpreadSheetAction action = getAction(actionId);
		action.setSpreadSheet(this);
		return action;
	}
	private String[] actionList=null;
	public String[] getActionList(){
		if (actionList==null) actionList=((SpreadSheetModel)getModel()).getActionList();
		return actionList;
	}
	private Map actionMap=null;
	public CommonSpreadSheetAction getAction(String actionId) {
		if (actionMap==null){
			actionMap=new HashMap();
			addActions(getActionList());
		}
		return (CommonSpreadSheetAction) actionMap.get(actionId);
	}
	private void addAction(String action,String spreadSheetActionId,CommonSpreadSheetAction spreadSheetAction){
		if (spreadSheetActionId.equals(action)){
			actionMap.put(spreadSheetActionId,spreadSheetAction);
		}
	}
	private void addActions(String[] actions){
//		System.out.println("SpreadSheet "+spreadSheetCategory+", "+hashCode()+" addActions("+dumpActions(actions)+")");
		NodeListTransferHandler handler=null;
		if (getTransferHandler() instanceof NodeListTransferHandler) handler=(NodeListTransferHandler)getTransferHandler();
		if (actions!=null)
			for (int i=0;i<actions.length;i++){
				String action=actions[i];
				addAction(action,MenuActionConstants.ACTION_INDENT,indentAction);
				addAction(action,MenuActionConstants.ACTION_OUTDENT,outdentAction);
				addAction(action,MenuActionConstants.ACTION_NEW,newAction);
				addAction(action,MenuActionConstants.ACTION_DELETE,deleteAction);
				if (handler!=null){
					addAction(action,MenuActionConstants.ACTION_COPY,handler.getNodeListCopyAction());
					addAction(action,MenuActionConstants.ACTION_CUT,handler.getNodeListCutAction());
					addAction(action,MenuActionConstants.ACTION_PASTE,handler.getNodeListPasteAction());
				}
				addAction(action,MenuActionConstants.ACTION_EXPAND,expandAction);
				addAction(action,MenuActionConstants.ACTION_COLLAPSE,collapseAction);
				
			}
	}
	public void clearActions(){
		actionMap=null;
		actionList=null;
		popup=null;
		((CommonSpreadSheetModel)getModel()).clearActions();
	}
//	private static String dumpActions(String[] actions){
//		if (actions==null) return null;
//		StringBuffer sb=new StringBuffer();
//		for (int i=0;i<actions.length;i++){
//			sb.append(actions[i]).append(',');
//		}
//		return sb.toString();
//	}
	public void setActions(String[] actions){
		//replace default actions
		actionList=actions;
		if (actionMap==null) actionMap=new HashMap();
		else actionMap.clear();
		addActions(actions);
	}
	public void setActions(String actions){
		addActions(CommonSpreadSheetModel.convertActions(actions));
	}

	
//	public static final String INDENT = "Action.Indent";
//	public static final String OUTDENT = "Action.Outdent";
//	public static final String NEW = "Action.New";
//	public static final String DELETE = "Action.Delete";
//	public static final String CUT = "Action.Cut";
//	public static final String COPY = "Action.Copy";
//	public static final String PASTE = "Action.Paste";
	
//	public static final int INDENT = 0;
//
//	public static final int OUTDENT = 1;
//
//	public static final int NEW = 2;
//
//	public static final int DELETE = 3;
//
//	public static final int CUT = 4;
//
//
//	public static final int COPY = 5;
//
//	public static final int PASTE = 6;

	public static abstract class SpreadSheetAction extends AbstractAction implements Closure,CommonSpreadSheetAction {
		protected SpreadSheet spreadSheet;

		protected int[] rows;

		public SpreadSheetAction(String id,SpreadSheet spreadSheet) {
			super(Messages.getString(id));
			this.spreadSheet=spreadSheet;
		}

		public void actionPerformed(ActionEvent e) {
			execute();
		}

		public void executeFirst() {
			rows = spreadSheet.finishCurrentOperations();
		}

		public void execute(Object o) {
			executeFirst();
			execute();
		}

		public abstract void execute();

		public CommonSpreadSheet getSpreadSheet() {
			return spreadSheet;
		}

		public void setSpreadSheet(CommonSpreadSheet spreadSheet) {
			this.spreadSheet = (SpreadSheet)spreadSheet;
		}

		public NodeModelCache getCache() {
			return ((SpreadSheetModel) spreadSheet.getModel()).getCache();
		}

		public List getSelected() {
			return spreadSheet.rowsToGraphicNodes((rows == null) ? spreadSheet.getSelectedRows() : rows);
		}
	}


	protected SpreadSheetAction indentAction = new SpreadSheetAction("Spreadsheet.Action.indent",this) {
		public void execute() {
			finishCurrentOperations();
			getCache().indentNodes(getSelected());
		}
	};

	protected SpreadSheetAction outdentAction = new SpreadSheetAction("Spreadsheet.Action.outdent",this) {
		public void execute() {
			finishCurrentOperations();
			getCache().outdentNodes(getSelected());
		}
	};

	protected SpreadSheetAction newAction=new SpreadSheetAction("Spreadsheet.Action.new",this){
		public void execute(){
			List nodes=getSelected();
			if (nodes==null||nodes.size()==0) {
				int row = getCurrentRow();
				if (row == -1)
					return;
				getCache().newNode((GraphicNode)getCache().getElementAt(row));
			} else {
				getCache().newNode((GraphicNode)nodes.get(nodes.size()-1));
			}
		}
	};
	
	//will be used later
	protected SpreadSheetAction newResourceAction=new SpreadSheetAction("Spreadsheet.Action.new",this){
		public void execute(){
			List nodes=getSelected();
				final ResourcePool resourcePool=(ResourcePool)getCache().getModel().getDataFactory();
				Project project=(Project)resourcePool.getProjects().get(0);
					if (nodes==null||nodes.size()==0) return;
					final ArrayList descriptors=new ArrayList();
					Session session=SessionFactory.getInstance().getSession(false);
					Job job=(Job)SessionFactory.callNoEx(session,"getLoadProjectDescriptorsJob",new Class[]{boolean.class,java.util.List.class,boolean.class},new Object[]{true,descriptors,true});
					job.addSwingRunnable(new JobRunnable("Local: addNodes"){
						public Object run() throws Exception{
							final Closure setter=new Closure(){
								public void execute(Object obj){
								}
							};
							final Closure getter=new Closure(){
								public void execute(Object obj){
									ResourceAdditionDialog.Form form=(ResourceAdditionDialog.Form)obj;
									List nodes=new ArrayList();
									for (Iterator i=form.getSelectedResources().iterator();i.hasNext();){
										try {
											nodes.add(NodeFactory.getInstance().createNode(Serializer.deserializeResourceAndAddToPool((EnterpriseResourceData)i.next(),resourcePool,null)));
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (ClassNotFoundException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									getCache().addNodes(((GraphicNode)getSelected().get(0)).getNode(),nodes);
									getCache().update();
								}
							};
							ResourceAdditionDialog.Form form=new ResourceAdditionDialog.Form();
							try{
								List resources=(List)SessionFactory.call(SessionFactory.getInstance().getSession(false),"retrieveResourceDescriptors",null,null);
								HashMap resourceMap=new HashMap();
								for (Iterator i=resources.iterator();i.hasNext();){
									EnterpriseResourceData data=(EnterpriseResourceData)i.next();
									resourceMap.put(new Long(data.getUniqueId()),data);
								}
								List currentResources=resourcePool.getResourceList();
								for (Iterator i=currentResources.iterator();i.hasNext();){
									ResourceImpl resource=(ResourceImpl)i.next();
									Long key=new Long(resource.getUniqueId());
									if (resourceMap.containsKey(key)) resourceMap.remove(key);
								}
								form.getSelectedResources().addAll(resourceMap.values());
							}catch(Exception e){}
							
							ResourceAdditionDialog.getInstance((JFrame)SwingUtilities.getRoot(SpreadSheet.this),form).execute(setter,getter);
							return null;
						}
					});
					session.schedule(job);
				}
	};

	protected SpreadSheetAction deleteAction = new SpreadSheetAction("Spreadsheet.Action.delete",this) {
		private static final long serialVersionUID = 1561847977122331970L;

		public void execute() {
			finishCurrentOperations();
			List l = getSelectedDeletableRows();
			if (l.isEmpty())
				return;
			if (!GeneralOption.getInstance().isConfirmDeletes() || Alert.okCancel(Messages.getString("Message.confirmDeleteRows"))) {
				getCache().deleteNodes(l);
			}
		}
	};

	protected SpreadSheetAction cutAction = new SpreadSheetAction("Spreadsheet.Action.cut",this) {
		private static final long serialVersionUID = -7928292866527615772L;

		public void execute() {
			finishCurrentOperations();
			execute(getSelectedRows());
		}

		public void execute(Object object) {
			if (object != null && object instanceof List) {
				finishCurrentOperations();
				List nodes = getSelectedCuttableRows((List) object);
				if (nodes.isEmpty())
					return;
				executeFirst();
				getCache().cutNodes(nodes);
			}
		}
	};

	protected SpreadSheetAction copyAction = new SpreadSheetAction("Spreadsheet.Action.copy",this) {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7593036949653490043L;

		public void execute() {
			execute(getSelectedNodes());
		}

		public void execute(Object object) {
			if (object != null && object instanceof List) {
				finishCurrentOperations();
				List nodes = (List) object;
				executeFirst();
				getCache().copyNodes(nodes);
			}
		}
	};

	protected SpreadSheetAction pasteAction = new SpreadSheetAction("Spreadsheet.Action.paste",this) {
		private static final long serialVersionUID = 5904764895696983803L;

		public void execute() {
			execute(getSelectedNodes());
		}

		public void execute(Object object) {
			if (object != null && object instanceof List) {
				finishCurrentOperations();
				List selectedNodes = getSelectedNodes();
				Node parent = null;
				int position = 0;
				if (selectedNodes.size() > 0) {
					Node node = (Node) selectedNodes.get(0);
					parent = (Node) node.getParent();
					position = ((NodeBridge) parent).getIndex(node);
				}
				List nodes = (List) object;
				executeFirst();
				spreadSheet.clearSelection();
				getCache().pasteNodes(parent, nodes, position);
//				if (nodes.size() > 0) {
//					int row = ((SpreadSheetModel) spreadSheet.getModel()).findGraphicNodeRow(spreadSheet.getCache().getGraphicNode(nodes.get(0)));
//					changeSelection(row, 0, false, false);
//					if (nodes.size() > 1)
//						changeSelection(row + nodes.size() - 1, getColumnCount(), false, true);
//				}
			}
		}
	};
	
	protected SpreadSheetAction expandAction = new SpreadSheetAction("Spreadsheet.Action.expand",this) {
		public void execute() {
			finishCurrentOperations();
			getCache().expandNodes(getSelected(),true);
		}
	};
	protected SpreadSheetAction collapseAction = new SpreadSheetAction("Spreadsheet.Action.collapse",this) {
		public void execute() {
			finishCurrentOperations();
			getCache().expandNodes(getSelected(),false);
		}
	};
	
	
	public boolean isReadOnly() {
		return ((SpreadSheetModel)getModel()).isReadOnly();
	}

	public void setReadOnly(boolean readOnly) {
		((SpreadSheetModel)getModel()).setReadOnly(readOnly);
	}


//	private static final int[] DEFAULT_POPUP_OPTIONS = new int[] {INDENT,OUTDENT,NEW,DELETE,CUT,COPY,PASTE};
//	private int[] popupActions = DEFAULT_POPUP_OPTIONS;
//	public final void setPopupActions(int[] popupActions) {
//		this.popupActions = popupActions;
//	}
	

//	public boolean supportsAction(int option) {
//		if (popupActions == null)
//			return false;
//		for (int i = 0; i<popupActions.length; i++) {
//			if (popupActions[i] == option)
//				return true;
//		}
//		return false;
//	}
//	public boolean hasRowPopup() {
//		return popupActions != null && popupActions.length > 0;
//	}
	public boolean hasRowPopup() {
		getAction(null);
		return actionMap != null && actionMap.size() > 0;
	}

	public SpreadSheetAction getCopyAction() {
		return copyAction;
	}

	public SpreadSheetAction getCutAction() {
		return cutAction;
	}

	public SpreadSheetAction getPasteAction() {
		return pasteAction;
	}

}