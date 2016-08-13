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

Attribution Information: Attribution Copyright Notice: Copyright (c) 2006, 2007
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
package com.projity.pm.graphic.frames;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.apache.batik.util.gui.resource.ActionMap;
import org.apache.batik.util.gui.resource.MissingListenerException;
import org.apache.commons.collections.Closure;
import org.pushingpixels.flamingo.api.common.AbstractCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonComponent;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;

import apple.dts.samplecode.osxadapter.OSXAdapter;

import com.projectlibre.ui.ribbon.CustomRibbonBandGenerator;
import com.projectlibre.ui.ribbon.ProjectLibreRibbonUI;
import com.projity.configuration.Configuration;
import com.projity.configuration.FieldDictionary;
import com.projity.contrib.ClassLoaderUtils;
import com.projity.dialog.AboutDialog;
import com.projity.dialog.AbstractDialog;
import com.projity.dialog.BaselineDialog;
import com.projity.dialog.FindDialog;
import com.projity.dialog.HelpDialog;
import com.projity.dialog.OpenProjectDialog;
import com.projity.dialog.PODOnlyFeature;
import com.projity.dialog.ProjectDialog;
import com.projity.dialog.ProjectInformationDialog;
import com.projity.dialog.RenameProjectDialog;
import com.projity.dialog.ResourceInformationDialog;
import com.projity.dialog.ResourceMappingDialog;
import com.projity.dialog.TaskInformationDialog;
import com.projity.dialog.TipOfTheDay;
import com.projity.dialog.WelcomeDialog;
import com.projity.dialog.assignment.AssignmentDialog;
import com.projity.dialog.options.CalendarDialogBox;
import com.projity.document.Document;
import com.projity.document.ObjectEvent;
import com.projity.exchange.ResourceMappingForm;
import com.projity.field.Field;
import com.projity.graphic.configuration.SpreadSheetFieldArray;
import com.projity.grouping.core.Node;
import com.projity.grouping.core.VoidNodeImpl;
import com.projity.grouping.core.model.NodeModel;
import com.projity.grouping.core.transform.ViewTransformer;
import com.projity.grouping.core.transform.filtering.NodeFilter;
import com.projity.grouping.core.transform.filtering.ResourceInTeamFilter;
import com.projity.job.Job;
import com.projity.job.JobQueue;
import com.projity.job.JobRunnable;
import com.projity.job.Mutex;
import com.projity.menu.MenuActionConstants;
import com.projity.menu.MenuActionsMap;
import com.projity.menu.MenuManager;
import com.projity.options.CalendarOption;
import com.projity.pm.assignment.Assignment;
import com.projity.pm.graphic.IconManager;
import com.projity.pm.graphic.TabbedNavigation;
import com.projity.pm.graphic.frames.workspace.DefaultFrameManager;
import com.projity.pm.graphic.frames.workspace.FrameHolder;
import com.projity.pm.graphic.frames.workspace.FrameManager;
import com.projity.pm.graphic.frames.workspace.NamedFrame;
import com.projity.pm.graphic.frames.workspace.NamedFrameEvent;
import com.projity.pm.graphic.frames.workspace.NamedFrameListener;
import com.projity.pm.graphic.laf.LafManager;
import com.projity.pm.graphic.spreadsheet.SpreadSheet;
import com.projity.pm.graphic.spreadsheet.common.CommonSpreadSheet;
import com.projity.pm.graphic.spreadsheet.selection.event.SelectionNodeEvent;
import com.projity.pm.graphic.spreadsheet.selection.event.SelectionNodeListener;
import com.projity.pm.graphic.views.BaseView;
import com.projity.pm.graphic.views.ProjectsDialog;
import com.projity.pm.graphic.views.Searchable;
import com.projity.pm.resource.Resource;
import com.projity.pm.resource.ResourcePool;
import com.projity.pm.task.Project;
import com.projity.pm.task.ProjectFactory;
import com.projity.pm.task.SubProj;
import com.projity.pm.task.Task;
import com.projity.pm.time.HasStartAndEnd;
import com.projity.preference.ConfigurationFile;
import com.projity.preference.GlobalPreferences;
import com.projity.print.GraphPageable;
import com.projity.print.PrintDocumentFactory;
import com.projity.server.data.DocumentData;
import com.projity.session.CreateOptions;
import com.projity.session.LoadOptions;
import com.projity.session.LocalSession;
import com.projity.session.SaveOptions;
import com.projity.session.Session;
import com.projity.session.SessionFactory;
import com.projity.strings.Messages;
import com.projity.toolbar.FilterToolBarManager;
import com.projity.toolbar.TransformComboBox;
import com.projity.undo.CommandInfo;
import com.projity.undo.UndoController;
import com.projity.util.Alert;
import com.projity.util.BrowserControl;
import com.projity.util.ClassUtils;
import com.projity.util.Environment;
import com.projity.workspace.SavableToWorkspace;
import com.projity.workspace.WorkspaceSetting;


/**
 *
 */
public class GraphicManager implements  FrameHolder, NamedFrameListener, WindowStateListener,  SelectionNodeListener, ObjectEvent.Listener, ActionMap, MenuActionConstants, SavableToWorkspace {
	private static final boolean BINARY_WORKSPACE = true;
	private static GraphicManager lastGraphicManager = null; // used when displaying a popup but the frame isn't known
    private DocumentFrame currentFrame = null;
    private List frameList=new ArrayList();
    private HashMap<Project,NamedFrame> frameMap = new HashMap<Project,NamedFrame>();
//    private JFileChooser fileChooser = null;

	private NamedFrame viewBarFrame;
	private FrameManager frameManager;

	private MenuManager menuManager;
	MenuActionsMap actionsMap = null;
	//private String[] projectUrl;
	private static String server = null;

    private AssignmentDialog assignResourcesDialog = null;
    private FindDialog findDialog = null;
	private ProjectInformationDialog projectInformationDialog = null;
	private TaskInformationDialog taskInformationDialog = null;
	private ResourceInformationDialog resourceInformationDialog = null;
    private AboutDialog aboutDialog = null;
    private HelpDialog helpDialog = null;
    private BaselineDialog baselineDialog = null;
    private ResourceMappingDialog resourceMappingDialog=null;
	ProjectFactory projectFactory = null;
	protected Container container;
	protected Frame frame;
	TabbedNavigation topTabs = null;

	private static Object lastWorkspace = null; // static required - used for copying current workspace to new instance
	static LinkedList graphicManagers = new LinkedList();
    private static LafManager lafManager;
	public static boolean badLAF = false;
	private StartupFactory startupFactory = null;
	protected JobQueue jobQueue=null;

	protected GlobalPreferences preferences=null;
	private FilterToolBarManager filterToolBarManager;
	private JMenu projectListMenu = null;

	private ArrayList<CommandInfo> history=new ArrayList<CommandInfo>();

	/** determines the parent graphic manager for a component
	 *
	 * @param component
	 * @return
	 */
	public static GraphicManager getInstance(Component component){
		Component c = component;
		for (c = component; c != null; c = c.getParent()) {
			if (c instanceof FrameHolder)
				return ((FrameHolder)c).getGraphicManager();
			else if (c.getName() != null && c.getName().endsWith("BootstrapApplet") && c.getClass().getName().endsWith("BootstrapApplet")){
				System.out.println("applet: "+c.getClass().getName());
				try {
					FrameHolder holder=(FrameHolder)Class.forName("com.projity.bootstrap.BootstrapApplet.class").getMethod("getObject", null).invoke(c, null);
					return holder.getGraphicManager();
				} catch (Exception e) {
					return null;
				}
			}
		}
		return lastGraphicManager; // if none found, use last used one
	}
	public static GraphicManager getInstance(){
//System.out.println("Graphic manager getInstance = " + lastGraphicManager.hashCode());
		return lastGraphicManager;
	}

	public static Frame getFrameInstance(){
		return lastGraphicManager.getFrame();
	}

	public static DocumentFrame getDocumentFrameInstance(){
		return lastGraphicManager==null?null:lastGraphicManager.getCurrentFrame();
	}

	void setMeAsLastGraphicManager() { // makes this the current graphic manager for job queue and dialogs
		lastGraphicManager = this;
		if (jobQueue != null)
			SessionFactory.getInstance().setJobQueue(getJobQueue());

	}



	public static LinkedList getGraphicManagers() {
		return graphicManagers;
	}

	/**
	 * @param projectUrl TODO
	 * @param server TODO
	 * @throws java.awt.HeadlessException
	 */
	public GraphicManager(/*String[] projectUrl,*/ String server,Container container) throws HeadlessException {
		graphicManagers.add(this);
		lastGraphicManager = this;
		container.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
//				System.out.println("GainFocus " + GraphicManager.this.hashCode());
				setMeAsLastGraphicManager();
			}

			public void focusLost(FocusEvent e) {
//				System.out.println("LostFocus " + GraphicManager.this.hashCode());
			}});

		projectFactory = ProjectFactory.getInstance();
		projectFactory.getPortfolio().addObjectListener(this);

		//this.projectUrl = projectUrl;
		GraphicManager.server = server;
		this.container=container;
		if (container instanceof Frame)
			frame=(Frame)container;
		else if (container instanceof JApplet)
			frame = JOptionPane.getFrameForComponent(container);
		if (container instanceof FrameHolder)
			((FrameHolder)container).setGraphicManager(this);
//		else if (container instanceof BootstrapApplet){
		else{
			try {
				FrameHolder holder=(FrameHolder)Class.forName("com.projity.bootstrap.BootstrapApplet").getMethod("getObject", null).invoke(container, null);
				holder.setGraphicManager(this);
			} catch (Exception e) {
			}
		}
		registerForMacOSXEvents();
	}
	public GraphicManager(Container container) {
		this(/*null,*/ server,container);
	}

	protected void finalize() throws Throwable {
//		System.out.println("~~~~~~~~~~~~~~~~ GraphicManager.finalize()");
		super.finalize();
	}

	public void cleanUp() {

//		On quitting, a sleep interrupted exception (below) is thrown by Substance. Without changing the source
//		java.lang.InterruptedException: sleep interrupted
//		at java.lang.Thread.sleep(Native Method)
//		at org.jvnet.substance.utils.FadeTracker$FadeTrackerThread.run(FadeTracker.java:210)
//		I have submitted a bug report: https://substance.dev.java.net/issues/show_bug.cgi?id=155 with a proposed fix

		projectFactory.getPortfolio().removeObjectListener(this);
		((DefaultFrameManager)frameManager).cleanUp();
		graphicManagers.remove(this);
		if (graphicManagers.isEmpty())
			getLafManager().clean();

		if (jobQueue != null)
			jobQueue.cancel();
		jobQueue = null;
	}

	public LafManager getLafManager(){
		if (lafManager==null){
			try {
				String lafName=Messages.getMetaString("LafManager");
				lafManager=(LafManager)Class.forName(lafName).getConstructor(new Class[]{GraphicManager.class}).newInstance(new Object[]{this});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//lafManager=new LafManager(this);
		}
		return lafManager;
	}



	private String fileName = "../projity_exchange/testdata/New Product.mpp"; //$NON-NLS-1$
	private ViewAction resourceAction;

//    private JFileChooser getFileChooser() {
//    	if (fileChooser == null)
//    		fileChooser = new JFileChooser();
//    	return fileChooser;
//    }


    private static String getTabIdForProject(Project project) {
    	if (project == null)
    		return null;
    	return "" + project.getUniqueId(); //see later //$NON-NLS-1$
    }

    void setTabNameAndTitle(DocumentFrame frame, Project project) {
    	frame.setTabNameAndTitle(project);
    }


    public void switchToProject(long projectId) {
    	Project project = ProjectFactory.getInstance().findFromId(projectId);
    	if (project == null)
    		return;
    	DocumentFrame f = (DocumentFrame) frameMap.get(project);
    	if (f == null)
    		return;
    	setCurrentFrame(f);

    }
	protected void setCurrentFrame(DocumentFrame frame){
		if (frame instanceof DocumentFrame) {
			if (currentFrame != null && projectListMenu != null&&!Environment.isPlugin()) {
				currentFrame.getMenuItem().setSelected(false);
			}

			if (currentFrame != null&&!Environment.isPlugin())
				currentFrame.refreshViewButtons(false); // disable buttons for old view

			currentFrame = (DocumentFrame)frame;
			if (projectListMenu != null&&!Environment.isPlugin()) {
				currentFrame.getMenuItem().setSelected(true);
			}
			if (topTabs != null&&!Environment.isPlugin()) {
				topTabs.setCurrentFrame(currentFrame);
			}
			DocumentSelectedEvent.fire(this,currentFrame);
			if (projectInformationDialog != null)
				projectInformationDialog.documentSelected(new DocumentSelectedEvent(this,currentFrame));
			if (taskInformationDialog != null)
				taskInformationDialog.documentSelected(new DocumentSelectedEvent(this,currentFrame));
			if (resourceInformationDialog != null)
				resourceInformationDialog.documentSelected(new DocumentSelectedEvent(this,currentFrame));

			setTitle(false);
			if (currentFrame != null)
				currentFrame.refreshViewButtons(true);

			getFrameManager().activateFrame(currentFrame); // need to force activation in case being activated by closing another
			if(!Environment.isPlugin()){
				setEnabledDocumentMenuActions(currentFrame!=null);
				setButtonState(null,currentFrame.getProject());
			}
			if (currentFrame != null && currentFrame.getProject() != null) {
				if (!Environment.isPlugin()) currentFrame.getFilterToolBarManager().transformBasedOnValue();
				CalendarOption calendarOption = currentFrame.getProject().getCalendarOption();

				if (calendarOption != null) {
					CalendarOption.setInstance(calendarOption);
				}
			} else {
				CalendarOption.setInstance(CalendarOption.getDefaultInstance());
			}
		}
	}

	void setTitle(boolean isSaving) {
		DocumentFrame dframe = getCurrentFrame();
		String title=Messages.getContextString("Text.ApplicationTitle"); //$NON-NLS-1$
		if (dframe != null && dframe.getProject() != null) {
			if (Environment.getStandAlone()) title=dframe.getProject().getTitle();
			else title += " - " + dframe.getProject().getName(); //$NON-NLS-1$
			if (!isSaving && dframe.getProject().needsSaving())
				title += " *"; // modified; //$NON-NLS-1$
		}
		Frame f=getFrame();
		if (frame!=null) frame.setTitle(title);

	}
    /**
	 * Adds a new document frame and shows it
	 * @param project
	 * @return
	 */

	public DocumentFrame addProjectFrame(final Project project) {
		String tabId = getTabIdForProject(project);
		if (project == null) // in case of out of memory error
			return null;
		final DocumentFrame frame = new DocumentFrame(this,project,tabId);
		if (frame == null) // in case of out memory error
			return null;
		getFrameManager().addFrame(frame);
//		DocumentFrame newDocumentFrame = (DocumentFrame)getFrameManager().getFrame(tabId);

		setTabNameAndTitle(frame,project);
		frame.setShowTitleBar(false);
		getFrameManager().showFrame(frame); // show the frame
		frame.addNamedFrameListener(this); // main frame listens to changes in selection

		project.addProjectListener(frame);


		if (projectListMenu != null) {
			JRadioButtonMenuItem mi = new JRadioButtonMenuItem(new SelectDocumentAction(frame));
			mi.setSelected(true);
			frame.setMenuItem(mi);
			projectListMenu.add(mi);
		}
		setCurrentFrame(frame);

		frameList.add(frame);
		frameMap.put(project, frame);

		// clear filter/grouping/sort for newly opened or created project
		if (!Environment.isPlugin()) SwingUtilities.invokeLater( new Runnable() {
			public void run() {
			 	frame.getFilterToolBarManager().clear();
			}});
		getMenuManager().setActionEnabled(ACTION_OPEN_PROJECT,frame==null || !frame.isEditingResourcePool()); //resource pool can not be opened at same time as another proj
		return frame;
	}

	private void closeProjectFrame(Project project) {
		String tabId = getTabIdForProject(project);
		DocumentFrame frame = (DocumentFrame) frameMap.get(project);
		if (frame!=null){ //TODO why is it sometimes null? Well, in the case of opening a subproject it can be

			if (currentFrame == frame){
		    	frame.setVisible(false);
				JMenuItem mi = frame.getMenuItem();
				if (mi != null && projectListMenu != null)
					projectListMenu.remove(mi);

			    if (frameList.size()<=1) {
			    	frame.refreshViewButtons(false); // disable old buttons
			    	currentFrame=null;//TODO open a new one instead
			    	setTitle(false);
			        setEnabledDocumentMenuActions(false);
			    } else{
			        DocumentFrame current;
			        int index=0;
			        for (Iterator i=frameList.iterator();i.hasNext();index++){
			            current=(DocumentFrame)i.next();
			            if (tabId.equals(getTabIdForProject(current.getProject())))
			                break;
			        }
					setCurrentFrame((DocumentFrame)frameList.get((index==0)?1:index-1));//TODO use previous instead
			    }
			}
			project.removeProjectListener(frame); // hk uncommented this for applet. don't know why it was commented
			frame.removeNamedFrameListener(this); // main frame listens to changes in selection

			getFrameManager().removeFrame(frame);
			frame.onClose();
			frameList.remove(frame);
			frameMap.remove(project);


		}
		setAllButResourceDisabled(false);
		getMenuManager().setActionEnabled(ACTION_OPEN_PROJECT,true); // no matter what, you can open a project after closing, since if you closed resource pool you can open after
	}
	public String doRenameProjectDialog(String name,Set projectNames,boolean saveAs) {
		finishAnyOperations();
		RenameProjectDialog renameProjectDialog = RenameProjectDialog.getInstance(getFrame(),null);
		renameProjectDialog.getForm().setName(name);
		renameProjectDialog.getForm().setProjectNames(projectNames);
		renameProjectDialog.getForm().setSaveAs(saveAs);
		if (renameProjectDialog.doModal())
			return renameProjectDialog.getForm().getName();
		return null;
	}

	public void doWelcomeDialog() {
		//claur, for test purpose to preload a project
		String preloadProject=ConfigurationFile.getProperty("preLoadProject"); 
		if (preloadProject!=null){
			loadLocalDocument(preloadProject,false);
			return;
		}

		WelcomeDialog instance = WelcomeDialog.getInstance(getFrame(),getMenuManager());
		if (instance.doModal()) {
			waitInitialization();
			if (instance.getForm().isCreateProject())
				doNewProjectDialog();
			else if (instance.getForm().isOpenProject()){
				if(Environment.getStandAlone()) openLocalProject();
				else doOpenProjectDialog();
			}else if (instance.getForm().isManageResources()) {
				loadMasterProject();
			}
		}
	}

	public boolean doNewProjectDialog() {
		ProjectDialog.Form form=doNewProjectDialog1();
		if (form==null) return false;
		else return doNewProjectDialog2(form);
	}
	public boolean doNewProjectNoDialog(HashMap opts) {
		ProjectDialog.Form form=doNewProjectNoDialog1();
		if (form==null) return false;
		if (opts!=null){
			Closure updateViewClosure=(Closure)opts.get("updateViewClosure");
			if (updateViewClosure!=null) updateViewClosure.execute(form);
		}
		return doNewProjectDialog2(form);
	}
	public ProjectDialog.Form doNewProjectDialog1() {
		addHistory("doNewProjectDialog");
		finishAnyOperations();
		ProjectDialog projectDialog = ProjectDialog.getInstance(getFrame(),null);
		projectDialog.getForm().setManager(Environment.getUser().getName());
		if (!projectDialog.doModal())
			return null; // if cancelled
		return projectDialog.getForm();
	}
	protected static int project_suffix_count=1;
//	protected static ProjectDialog.Form lastNewProjectForm;
//	public ProjectDialog.Form getLastNewProjectForm() {
//		return lastNewProjectForm;
//	}
	public ProjectDialog.Form doNewProjectNoDialog1() {
		System.out.println("doNewProjectNoDialog1 begin");
		addHistory("doNewProjectNoDialog");
		finishAnyOperations();
		ProjectDialog.Form form=new ProjectDialog.Form();
		form.setName("Project"+(project_suffix_count++));
//		lastNewProjectForm=form;
		System.out.println("doNewProjectNoDialog1 end");
		return form;
	}
	public boolean doNewProjectDialog2(ProjectDialog.Form form) {
		showWaitCursor(true);
		ResourcePool resourcePool=form.getResourcePool();
		boolean local=form.isLocal();
		if (resourcePool!=null) resourcePool.setLocal(local);
		CreateOptions opt=new CreateOptions();
		opt.setResourcePool(form.getResourcePool());
		opt.setLocal(local);
		opt.setName(form.getName());
		opt.setAddResources(!local);
		Project project = projectFactory.createProject(opt);
		try {
			//createProject above might make a new resource pool, so make sur it is used when copying properties
			//projectDialog.getForm().setResourcePool(project.getResourcePool());

			project.setManager(form.getManager());
			project.setName(form.getName());
			project.setNotes(form.getNotes());
			project.setForward(form.isForward());
			project.setGroup(form.getGroup());
			project.setDivision(form.getDivision());
			project.setProjectType(form.getProjectType());
			project.setProjectStatus(form.getProjectStatus());
			project.setExpenseType(form.getExpenseType());

			if (!form.isLocal()){
				project.setAccessControlPolicy(form.getAccessControlType());
				project.resetRoles(form.getAccessControlType()==0);
			}


			if (form.isLocal())
				project.setLocal(true);
			else project.setTemporaryLocal(true);
			if (form.isForward())
				project.setStartDate(form.getStartDate());
			else
				project.setFinishDate(form.getStartDate());
			// copy any extra fields to the project
			project.getExtraFields().putAll(form.getExtra().getExtraFields());

//			PropertyUtils.copyProperties(project, projectDialog.getForm());
		} catch (Exception propertyException) {
			propertyException.printStackTrace();
		}
		showWaitCursor(false);

		return true;
	}

	boolean doingOpenDialog = false;
	private void doOpenProjectDialog() {
		if (doingOpenDialog)
			return;
		doingOpenDialog = true;
		finishAnyOperations();

		final ArrayList descriptors=new ArrayList();

    	Session session=SessionFactory.getInstance().getSession(false);
		Job job=(Job)SessionFactory.callNoEx(session,"getLoadProjectDescriptorsJob",new Class[]{boolean.class,java.util.List.class,boolean.class},new Object[]{true,descriptors,!Environment.isAdministrator()});
    	job.addSwingRunnable(new JobRunnable("Local: loadDocument"){ //$NON-NLS-1$
    		public Object run() throws Exception{
			   		    final Closure setter=new Closure(){
		    		        public void execute(Object obj){

		    		        }
		    		    };
		    		    final Closure getter=new Closure(){
		    		        public void execute(Object obj){
		    		        	final Object[] r=(Object[])obj;
		    		        	if (r!=null){
		    		        		DocumentData data=(DocumentData)r[0];
		    		        		boolean openAs=(Boolean)r[1];
		    		        		loadDocument(data.getUniqueId(),false,openAs);
		    		        	}

		    		        }
		    		    };
		    		    try {
		    		    	boolean allowMaster = getCurrentFrame() == null && Environment.isAdministrator();
		    		    	OpenProjectDialog.getInstance(getFrame(),descriptors,Messages.getString("Text.openProject"),allowMaster, true, null).execute(setter,getter); //$NON-NLS-1$
		    		    } finally {
				    		doingOpenDialog = false;
		    		    }

	    		    	return null;
    		}
		});
		session.schedule(job);
	}
	private void doInsertProjectDialog() {
		if (doingOpenDialog)
			return;
		doingOpenDialog = true;

		finishAnyOperations();

		final Project project;
		project= getCurrentFrame().getProject();

//		List nodes=getCurrentFrame().getSelectedNodes();
//		if (nodes==null||nodes.size()==0) return;
//		Node node=(Node)nodes.get(0);
//		if (!node.isInSubproject()) project= getCurrentFrame().getProject();
//		else{
//			while (!(node==null) && !(node.getImpl().getClass().getName().equals("com.projity.pm.task.Subproject"))){
//				node=(Node)node.getParent();
//			}
//			if (node==null) return; //shouldn't happen
//			try {
//				project=(Project)node.getImpl().getClass().getMethod("getSubproject", null).invoke(node.getImpl(), null);
//			} catch (Exception e) {
//				return;
//			}
//		}

		final ArrayList descriptors=new ArrayList();

    	Session session=SessionFactory.getInstance().getSession(false);
		Job job=(Job)SessionFactory.callNoEx(session,"getLoadProjectDescriptorsJob",new Class[]{boolean.class,java.util.List.class,boolean.class},new Object[]{true,descriptors,true});
    	job.addSwingRunnable(new JobRunnable("Local: add"){ //$NON-NLS-1$
    		public Object run() throws Exception{
	    	    Closure setter=new Closure(){
	    	        public void execute(Object obj){

	    	        }
	    	    };
	    	    Closure getter=new Closure(){
	    	        public void execute(Object obj){
	    		        final Object[] r=(Object[])obj;
	    		        if (r!=null){
   		        			final DocumentData data=(DocumentData)r[0];
	    	        		if (data.isMaster())
	    	        			return;
	    	        		insertSubproject(project, data.getUniqueId(), true);
//	    	        		Project openedAlready = ProjectFactory.getInstance().findFromId(data.getUniqueId());
//
//							if (!project.canInsertProject(data.getUniqueId())) {
//								Alert.error("The selected project is already a subproject in this consolidated project.");
//								return;
//							}
//							if (openedAlready != null && openedAlready.isOpenedAsSubproject()) {
//								Alert.error("The selected project is already opened as a subproject in another consolidated project.");
//								return;
//							}
//							Subproject subprojectTask = new Subproject(project,data.getUniqueId());
//							Node subprojectNode = getCurrentFrame().addNodeForImpl(subprojectTask,NodeModel.EVENT);
//							ProjectFactory.getInstance().openSubproject(project, subprojectNode, true);
	    	        	}
	    	        }
	    	    };

	    		try {
	    		    OpenProjectDialog dlg = OpenProjectDialog.getInstance(getFrame(),descriptors,Messages.getString("Text.insertProject"),false, false, project); //$NON-NLS-1$
	    		    dlg.execute(setter,getter);
	    		} catch (Exception e) {
	    			Alert.error(Messages.getString("Message.serverUnreachable"),getContainer()); //$NON-NLS-1$
	    			//TODO need more precise exception
	    			e.printStackTrace();
	    		} finally {
		    		doingOpenDialog = false;
	    		}
	    		return null;
    		}
		});
		session.schedule(job);
	}



	public void insertSubproject(final Project project, final long subprojectUniqueId,final boolean undo) {
		addHistory("insertSubproject", new Object[]{project.getName(),project.getUniqueId(),subprojectUniqueId});
		Project openedAlready = ProjectFactory.getInstance().findFromId(subprojectUniqueId);

		if (!project.getSubprojectHandler().canInsertProject(subprojectUniqueId)) {
			Alert.error(Messages.getString("GraphicManager.SelectedProjectAlreadySubproject")); //$NON-NLS-1$
			return;
		}
		if (openedAlready != null && openedAlready.isOpenedAsSubproject()) {
			Alert.error(Messages.getString("GraphicManager.SelectedProjectAlreadyOpenedAsSubproject")); //$NON-NLS-1$
			return;
		}
		SubProj subprojectTask = project.getSubprojectHandler().createSubProj(subprojectUniqueId);
		Node subprojectNode = getCurrentFrame().addNodeForImpl(subprojectTask,NodeModel.EVENT);
		ProjectFactory.getInstance().openSubproject(project, subprojectNode, true);

		//Undo
		if (undo){
			UndoController undoContoller=project.getUndoController();
			if (undoContoller.getEditSupport()!=null){
				undoContoller.clear();
				//undoContoller.getEditSupport().postEdit(new CreateSubprojectEdit(project,subprojectNode,subprojectUniqueId));
			}
		}

	}



	protected class CreateSubprojectEdit extends AbstractUndoableEdit{
		protected Project project;
		protected final Node subprojectNode;
		protected long subprojectUniqueId;


		public CreateSubprojectEdit(Project project, final Node subprojectNode, long subprojectUniqueId) {
			super();
			this.project = project;
			this.subprojectNode = subprojectNode;
			this.subprojectUniqueId = subprojectUniqueId;
		}
		public void redo() throws CannotRedoException {
			super.redo();
			insertSubproject(project, subprojectUniqueId, false);
		}
		public void undo() throws CannotUndoException {
			super.undo();
			project.getTaskOutline().remove(subprojectNode,NodeModel.EVENT);

//			UndoController undoContoller=project.getUndoController();
//			if (undoContoller.getEditSupport()!=null){
//				undoContoller.clear();
//			}
		}
	}



	private void doProjectInformationDialog() {
		if (!getCurrentFrame().isActive())
			return;

		finishAnyOperations();

		if (projectInformationDialog == null) {
			projectInformationDialog = ProjectInformationDialog.getInstance(getFrame(),getCurrentFrame().getProject());
			projectInformationDialog.pack();
			projectInformationDialog.setModal(false);
		} else {
			projectInformationDialog.setObject(getCurrentFrame().getProject());
		}
		projectInformationDialog.setLocationRelativeTo(getCurrentFrame());//to center on screen
		projectInformationDialog.setVisible(true);

	}

	public void doInformationDialog(boolean notes) {

		if (!isDocumentActive())
			return;

		finishAnyOperations();
	    List nodes=getCurrentFrame().getSelectedNodes(false);
	    if (nodes == null)
	    	return;
		if (nodes.size() > 1) {
			Alert.warn(Messages.getString("Message.onlySelectOneElement"),getContainer()); //$NON-NLS-1$
			return;
		}
		final Node node=(Node)nodes.get(0);
		Object impl=node.getImpl();
		if (impl instanceof Task||(impl instanceof Assignment&&taskType)){
			Task task=(Task)((impl instanceof Assignment)?(((Assignment)impl).getTask()):impl);
			if (taskInformationDialog == null) {
				taskInformationDialog = TaskInformationDialog.getInstance(getFrame(),task, notes);
				taskInformationDialog.pack();
				taskInformationDialog.setModal(false);
			} else {
				taskInformationDialog.setObject(task);
				taskInformationDialog.updateAll();
			}
			taskInformationDialog.setLocationRelativeTo(getCurrentFrame());//to center on screen
			if (notes)
				taskInformationDialog.showNotes();
			else if (impl instanceof Assignment)
				taskInformationDialog.showResources();

			taskInformationDialog.setVisible(true);
		} else if (impl instanceof Resource||(impl instanceof Assignment&&resourceType)) {
			Resource resource=(Resource)((impl instanceof Assignment)?(((Assignment)impl).getResource()):impl);;
			if (resourceInformationDialog == null) {
				resourceInformationDialog = ResourceInformationDialog.getInstance(getFrame(),resource);
				resourceInformationDialog.pack();
				resourceInformationDialog.setModal(false);
			} else {
				resourceInformationDialog.setObject(resource);
				resourceInformationDialog.updateAll();
			}
			resourceInformationDialog.setLocationRelativeTo(getCurrentFrame());//to center on screen
			if (notes)
				resourceInformationDialog.showNotes();
			resourceInformationDialog.setVisible(true);

		} else if (impl instanceof Project) {
			doProjectInformationDialog();
		}


	}


	public Action getAction(String key) throws MissingListenerException {
		if (actionsMap == null)
			addHandlers();

		Action action = actionsMap.getConcreteAction(key);
		if (action == null)
			throw new MissingListenerException("no listener for mainFrame", getClass().getName(),key); //$NON-NLS-1$

		return action;
	}

	public String getStringFromAction(Action action) throws MissingListenerException {
		return actionsMap.getStringFromAction(action);
	}

    public Action getRawAction(String s){
        return actionsMap.getActionFromMenuId(s);
    }


	public void addHandlers() {
		actionsMap = new MenuActionsMap(this,menuManager);
		actionsMap.addHandler(ACTION_NEW_PROJECT, new NewProjectAction());
		actionsMap.addHandler(ACTION_OPEN_PROJECT, new OpenProjectAction());
		actionsMap.addHandler(ACTION_INSERT_PROJECT, new InsertProjectAction());
		actionsMap.addHandler(ACTION_EXIT, new ExitAction());
		actionsMap.addHandler(ACTION_IMPORT_MSPROJECT, new ImportMSProjectAction());
		actionsMap.addHandler(ACTION_EXPORT_MSPROJECT, new ExportMSProjectAction());
		actionsMap.addHandler(ACTION_ABOUT_PROJITY, new AboutAction());
		actionsMap.addHandler(ACTION_OPENPROJ, new OpenProjAction());
		actionsMap.addHandler(ACTION_PROJECTLIBRE, new ProjectLibreAction());
		actionsMap.addHandler(ACTION_PROJITY_DOCUMENTATION, new HelpAction());
		actionsMap.addHandler(ACTION_TIP_OF_THE_DAY, new TipOfTheDayAction());
		actionsMap.addHandler(ACTION_PROJECT_INFORMATION, new ProjectInformationAction());
		actionsMap.addHandler(ACTION_PROJECTS_DIALOG, new ProjectsDialogAction());
		actionsMap.addHandler(ACTION_TEAM_FILTER, new TeamFilterAction());
		actionsMap.addHandler(ACTION_DOCUMENTS, new DocumentsAction());
		actionsMap.addHandler(ACTION_INFORMATION, new InformationAction());
		actionsMap.addHandler(ACTION_NOTES, new NotesAction());
		actionsMap.addHandler(ACTION_ASSIGN_RESOURCES, new AssignResourcesAction());

		actionsMap.addHandler(ACTION_FIND, new FindAction());
		actionsMap.addHandler(ACTION_GOTO, new GoToAction());
		actionsMap.addHandler(ACTION_INSERT_TASK, new InsertTaskAction());
		actionsMap.addHandler(ACTION_INSERT_RESOURCE, new InsertTaskAction()); // will do resource
		actionsMap.addHandler(ACTION_SAVE_PROJECT, new SaveProjectAction());
		actionsMap.addHandler(ACTION_SAVE_PROJECT_AS, new SaveProjectAsAction());
		actionsMap.addHandler(ACTION_PRINT, new PrintAction());
		actionsMap.addHandler(ACTION_PRINT_PREVIEW, new PrintPreviewAction());
		actionsMap.addHandler(ACTION_PDF, new PDFAction());
		actionsMap.addHandler(ACTION_CLOSE_PROJECT, new CloseProjectAction());
		actionsMap.addHandler(ACTION_UNDO, new UndoAction());
		actionsMap.addHandler(ACTION_REDO, new RedoAction());
//		actionsMap.addHandler(ACTION_ENTERPRISE_RESOURCES, new EnterpriseResourcesAction());
		actionsMap.addHandler(ACTION_CHANGE_WORKING_TIME, new ChangeWorkingTimeAction());
		actionsMap.addHandler(ACTION_LEVEL_RESOURCES, new LevelResourcesAction());
		actionsMap.addHandler(ACTION_DELEGATE_TASKS, new DelegateTasksAction());
		actionsMap.addHandler(ACTION_UPDATE_TASKS, new UpdateTasksAction());
		actionsMap.addHandler(ACTION_UPDATE_PROJECT, new UpdateProjectAction());
		actionsMap.addHandler(ACTION_BAR, new BarAction());
		actionsMap.addHandler(ACTION_INSERT_RECURRING, new RecurringTaskAction());
		actionsMap.addHandler(ACTION_SORT, new SortAction());
		actionsMap.addHandler(ACTION_GROUP, new GroupAction());
		actionsMap.addHandler(ACTION_CALENDAR_OPTIONS, new CalendarOptionsAction());
		actionsMap.addHandler(ACTION_SAVE_BASELINE, new SaveBaselineAction());
		actionsMap.addHandler(ACTION_CLEAR_BASELINE, new ClearBaselineAction());
		actionsMap.addHandler(ACTION_LINK, new LinkAction());
		actionsMap.addHandler(ACTION_UNLINK, new UnlinkAction());
		actionsMap.addHandler(ACTION_ZOOM_IN, new ZoomInAction());
		actionsMap.addHandler(ACTION_ZOOM_OUT, new ZoomOutAction());
		actionsMap.addHandler(ACTION_SCROLL_TO_TASK, new ScrollToTaskAction());
		actionsMap.addHandler(ACTION_INDENT, new IndentAction());
		actionsMap.addHandler(ACTION_OUTDENT, new OutdentAction());
		actionsMap.addHandler(ACTION_COLLAPSE, new CollapseAction());
		actionsMap.addHandler(ACTION_EXPAND, new ExpandAction());


		actionsMap.addHandler(ACTION_CUT, new CutAction());
		actionsMap.addHandler(ACTION_COPY, new CopyAction());
		actionsMap.addHandler(ACTION_PASTE, new PasteAction());
		actionsMap.addHandler(ACTION_DELETE, new DeleteAction());

		actionsMap.addHandler(ACTION_GANTT, new ViewAction(ACTION_GANTT));
		actionsMap.addHandler(ACTION_TRACKING_GANTT, new ViewAction(ACTION_TRACKING_GANTT));
		actionsMap.addHandler(ACTION_TASK_USAGE_DETAIL, new ViewAction(ACTION_TASK_USAGE_DETAIL));
		actionsMap.addHandler(ACTION_RESOURCE_USAGE_DETAIL, new ViewAction(ACTION_RESOURCE_USAGE_DETAIL));
		actionsMap.addHandler(ACTION_NETWORK, new ViewAction(ACTION_NETWORK));
		actionsMap.addHandler(ACTION_WBS, new ViewAction(ACTION_WBS));
		actionsMap.addHandler(ACTION_RBS, new ViewAction(ACTION_RBS));
		actionsMap.addHandler(ACTION_REPORT, new ViewAction(ACTION_REPORT));
		actionsMap.addHandler(ACTION_PROJECTS, new ViewAction(ACTION_PROJECTS));
		actionsMap.addHandler(ACTION_RESOURCES, resourceAction = new ViewAction(ACTION_RESOURCES));
		actionsMap.addHandler(ACTION_HISTOGRAM, new ViewAction(ACTION_HISTOGRAM));
		actionsMap.addHandler(ACTION_CHARTS, new ViewAction(ACTION_CHARTS));
		actionsMap.addHandler(ACTION_TASK_USAGE, new ViewAction(ACTION_TASK_USAGE));
		actionsMap.addHandler(ACTION_RESOURCE_USAGE, new ViewAction(ACTION_RESOURCE_USAGE));
		actionsMap.addHandler(ACTION_NO_SUB_WINDOW, new ViewAction(ACTION_NO_SUB_WINDOW));

		actionsMap.addHandler(ACTION_CHOOSE_FILTER, new TransformAction());
		actionsMap.addHandler(ACTION_CHOOSE_SORT, new TransformAction());
		actionsMap.addHandler(ACTION_CHOOSE_GROUP, new TransformAction());

		actionsMap.addHandler(ACTION_PALETTE, new PaletteAction());
		actionsMap.addHandler(ACTION_LOOK_AND_FEEL, new LookAndFeelAction());
		actionsMap.addHandler(ACTION_FULL_SCREEN, new FullScreenAction());
		actionsMap.addHandler(ACTION_REFRESH, new RefreshAction());


	}

	public class NewProjectAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doNewProjectDialog();
		}
		protected boolean allowed(boolean enable){
			DocumentFrame dframe = getCurrentFrame();
			return dframe == null || !dframe.isEditingResourcePool();
		}
	}

	public class OpenProjectAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {

			setMeAsLastGraphicManager();
			if (Environment.getStandAlone()) openLocalProject();
			else doOpenProjectDialog();
		}
		protected boolean allowed(boolean enable){
			DocumentFrame dframe = getCurrentFrame();
			return dframe == null || !dframe.isEditingResourcePool();
		}
		protected boolean needsDocument() {
			return  !allowed(true); // force it to be called iff the resource pool is open
		}


	}

	public class InsertProjectAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doInsertProjectDialog();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class ExitAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
		    closeApplication();
		}
	}

	public class ImportMSProjectAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			openLocalProject();		}
	}

	public class ExportMSProjectAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			saveLocalProject(true);		}
	}

	public class AboutAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			showAboutDialog();		}
	}
	public class OpenProjAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			BrowserControl.displayURL("http://www.projity.com/");
		}
	}

	public class ProjectLibreAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			BrowserControl.displayURL("http://www.projectlibre.com/");
		}
	}

	public class HelpAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			showHelpDialog();		}
	}

	public class TipOfTheDayAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			TipOfTheDay.showDialog(getFrame(),true);
			}
	}
	public class ProjectInformationAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doProjectInformationDialog();
		}
	}

	public class ProjectsDialogAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			ProjectsDialog.show(GraphicManager.this);
		}
	}

	public class TeamFilterAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			GlobalPreferences preferences=getPreferences();;
			//Field field = Configuration.getFieldFromId("Field.showProjectResourcesOnly");
			boolean teamOnly=!preferences.isShowProjectResourcesOnly();
			//field.setValue(preferences,this,teamOnly);
			preferences.setShowProjectResourcesOnly(teamOnly);
			ArrayList buttons=getMenuManager().getToolBarFactory().getButtonsFromId("TeamFilter"); //$NON-NLS-1$
			if (buttons!=null&&buttons.size()==1){
				JButton b=(JButton)buttons.get(0);
				if (Environment.isNewLook())
					b.setIcon(IconManager.getIcon(teamOnly?"menu24.showTeamResources":"menu24.showAllResources")); //$NON-NLS-1$ //$NON-NLS-2$
				else
					b.setIcon(IconManager.getIcon(teamOnly?"menu.showTeamResourcesSmall":"menu.showAllResourcesSmall")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			menuManager.setActionSelected(ACTION_TEAM_FILTER,teamOnly);


		}
	}
	public class DocumentsAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (!isDocumentActive())
				return;
			invokeFieldAction(ACTION_DOCUMENTS,getCurrentFrame().getProject());
		}
	}

	private boolean isEnabledFieldAction(String action, Object obj) {
		Field f = FieldDictionary.getInstance().getActionField(ACTION_DOCUMENTS);
		return (obj != null && f != null && f.getValue(obj,null) != null);

	}
	private void invokeFieldAction(String action, Object obj) {
		Field f = FieldDictionary.getInstance().getActionField(ACTION_DOCUMENTS);
		if (f != null)
			f.invokeAction(obj);

	}
	public class CalendarOptionsAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doCalendarOptionsDialog();
		}
	}


	public class InformationAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doInformationDialog(false);
		}
	}
	public class NotesAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			doInformationDialog(true);
		}
	}

	public class AssignResourcesAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			showAssignmentDialog(getCurrentFrame());
		}
	}

	public class SelectDocumentAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		DocumentFrame frame;
		public SelectDocumentAction(DocumentFrame frame) {
			this.frame = frame;
		}
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			GraphicManager.this.setCurrentFrame(frame);
		}
		@Override
		public Object getValue(String key) {
			if (key == Action.NAME)
				return frame.getProject().getName();
			return super.getValue(key);
		}
	}

	// Document actions
	public class FindAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				doFind(getCurrentFrame().getTopSpreadSheet(),null);
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return (currentFrame.getActiveSpreadSheet() != null);
		}
	}

	public class GoToAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				doFind(getCurrentFrame().getTopSpreadSheet(),Configuration.getFieldFromId("Field.id"));
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return (currentFrame.getActiveSpreadSheet() != null);
		}
	}

	public class RecalculateAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().getProject().recalculate();
		}
	}

	public class InsertTaskAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().addNodeForImpl(null);
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class SaveProjectAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (Environment.getStandAlone()) saveLocalProject(false);
			else{
				if (isDocumentActive()) {
					final DocumentFrame frame=getCurrentFrame();
					final Project project = frame.getProject();
					SaveOptions opt=new SaveOptions();
					opt.setPostSaving(new Closure(){
						public void execute(Object arg0) {
							refreshSaveStatus(true);
						}
					});
					opt.setPreSaving(getSavingClosure());
					addHistory("saveProject", new Object[]{project.getName(),project.getUniqueId()});
					projectFactory.saveProject(project,opt);
				}
			}

		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			NamedFrame frame=getCurrentFrame();
			if (frame==null) return false;
			Project project=getCurrentFrame().getProject();
			if (project==null) return false;
			return Environment.isOpenProj() || (!project.isLocal()&&project.needsSaving());
		}
	}

	public class SaveProjectAsAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (Environment.getStandAlone()) saveLocalProject(true);
			else{
				if (isDocumentActive()) {
					final DocumentFrame frame=getCurrentFrame();
					final Project project = frame.getProject();
					SaveOptions opt=new SaveOptions();
					opt.setPostSaving(new Closure(){
						public void execute(Object arg0) {
							frame.setId(project.getUniqueId()+""); //$NON-NLS-1$
							refreshSaveStatus(true);
						}
					});
					opt.setSaveAs(true);
					opt.setPreSaving(getSavingClosure());
					projectFactory.saveProject(project,opt);
				}
			}
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			NamedFrame frame=getCurrentFrame();
			if (frame==null) return false;
			Project project=getCurrentFrame().getProject();
			if (project==null) return false;
			if (project.isMaster() && !Environment.getStandAlone() && !Environment.isOpenProj())
				return false;

			return (project.isSavable());
//			return true;//!project.isLocal()&&!project.isMaster();
		}
	}

	public class PrintAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				print();
		}
	}
	public class PrintPreviewAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()) {
				Component c = (Component)arg0.getSource();
				Cursor cur = c.getCursor();
				c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				printPreview();
				c.setCursor(cur);

			}
			}
	}
	public class PDFAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
//claur - PDF enabled
//			if (Environment.isOpenProj()) {
//				PODOnlyFeature.doDialog(getFrame());
//				return;
//			}
			setMeAsLastGraphicManager();
			if (isDocumentActive()) {
				Component c = (Component)arg0.getSource();
				Cursor cur = c.getCursor();
				c.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				savePDF();
				c.setCursor(cur);
			}
		}
	}

	public class CloseProjectAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				closeProject(getCurrentFrame().getProject());
		}
	}

	public class UndoAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()){
				doUndoRedo(true);
			}
		}
	}
	public class RedoAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()){
				doUndoRedo(false);
			}
		}
	}


	public void doUndoRedo(boolean isUndo){
		DocumentFrame frame=getCurrentFrame();
		UndoController undoController=getUndoController();
		Object[] args=null;
		if (undoController!=null){
			if (isUndo){
	            String name=undoController.getUndoName();
	            if (name!=null) args=new Object[]{true,name};
			}else{
	            String name=undoController.getRedoName();
	            if (name!=null) args=new Object[]{false,name};
			}
		}
		if (args==null) args=new Object[]{isUndo};
		addHistory("doUndoRedo",args);
		frame.doUndoRedo(isUndo);

	}

//	public class EnterpriseResourcesAction extends MenuActionsMap.DocumentMenuAction {
//		public void actionPerformed(ActionEvent arg0) {
//			if (isDocumentActive())
//				getCurrentFrame().doEnterpriseResourcesDialog();
//		}
//	}

	public class ChangeWorkingTimeAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			JDialog dlg = AbstractDialog.containedInDialog(arg0.getSource());
			boolean restrict = dlg != null;
			if (isDocumentActive())
				getCurrentFrame().doChangeWorkingTimeDialog(restrict);
		}
	}

	public class LevelResourcesAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doLevelResourcesDialog();
		}
	}
	public class DelegateTasksAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doDelegateTasksDialog();
		}
	}

	public class UpdateTasksAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doUpdateTasksDialog();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}
	public class UpdateProjectAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doUpdateProjectDialog();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class DefineCodeAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doDefineCodeDialog();
		}
	}

	public class BarAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doBarDialog();
		}
	}
	public class RecurringTaskAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doRecurringTaskDialog();
		}
	}
	public class SortAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doSortDialog();
		}
	}
	public class GroupAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doGroupDialog();
		}
	}
	public class SaveBaselineAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doBaselineDialog(true);
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class ClearBaselineAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doBaselineDialog(false);
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}
	public class LinkAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doLinkTasks();
		}
	}
	public class UnlinkAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doUnlinkTasks();
		}
	}
	public class ZoomInAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doZoomIn();
			setZoomButtons();

		}
	}
	public class ZoomOutAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doZoomOut();
			setZoomButtons();
		}
	}
	public class ScrollToTaskAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doScrollToTask();
		}
	}
	public class ExpandAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doExpand();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}
	public class CollapseAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doCollapse();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class IndentAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doIndent();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}
	public class OutdentAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doOutdent();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}
	public class CutAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()){
				addHistory("doCut");
				getCurrentFrame().doCut();
			}
		}
	}
	public class CopyAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()){
				addHistory("doCopy");
				getCurrentFrame().doCopy();
			}
		}
	}
	public class PasteAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive()){
				addHistory("doPaste");
				getCurrentFrame().doPaste();
			}
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class DeleteAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			if (isDocumentActive())
				getCurrentFrame().doDelete();
		}
		protected boolean allowed(boolean enable) {
			if (enable==false) return true;
			return isDocumentWritable();
		}
	}

	public class ViewAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		private String viewName;
		public ViewAction(String viewName) {
			this.viewName = viewName;
		}
		public void actionPerformed(ActionEvent e) {
			setMeAsLastGraphicManager();
			if (getCurrentFrame() == null)
				return;
			setColorTheme(viewName);
			getCurrentFrame().activateView(viewName);
			setButtonState(null,currentFrame.getProject()); // disable buttons because no selection when first activated

		}
		public final String getViewName() {
			return viewName;
		}

	}

	public class TransformAction extends MenuActionsMap.DocumentMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {
			setMeAsLastGraphicManager();
			if (!isDocumentActive())
				return;
	        CommonSpreadSheet spreadSheet=getCurrentFrame().getTopSpreadSheet();
	        if (spreadSheet!=null){
	            if (spreadSheet.isEditing())
	            	spreadSheet.getCellEditor().stopCellEditing();//.cancelCellEditing();
	            spreadSheet.clearSelection();
	        }
	        TransformComboBox combo = (TransformComboBox) e.getSource();
	        combo.transformBasedOnValue();
		}
	}


	public void closeApplication(){
		addHistory("closeApplication");
//		if (Environment.getStandAlone()) {
//			Frame frame=getFrame();
//			if (frame!=null)
//				frame.dispose();
//			System.exit(0);
//			return;
//		}

		(new Thread(){
			public void run(){
				JobRunnable exitRunnable=new JobRunnable("Local: closeProjects"){
					public Object run() throws Exception{
						Frame frame=getFrame();
						if (frame!=null) frame.dispose();
						System.exit(0);
		    	    	return null; //return not used anyway
					}
				};


				Job job=projectFactory.getPortfolio().getRemoveAllProjectsJob(exitRunnable,true,null);
				SessionFactory.getInstance().getLocalSession().schedule(job);

			}
		}).start();
	}

	public void initLayout() {
		getFrameManager().getWorkspace().setLayout(new BorderLayout());
	}
	public void initProject(){
		//projects loaded in doStartupAction
//        if (projectUrl == null && !GeneralOption.getInstance().isStartWithBlankProject()) {
//			//System.out.println("not opening anything");
//		} else if (projectUrl == null   || projectUrl.length==0 || projectUrl[0].startsWith("http")) { //same as in Main //$NON-NLS-1$
//			System.out.println("loading local project:" +projectUrl); //$NON-NLS-1$
//			boolean ok = loadLocalDocument(projectUrl[0],true); //if null then it will create a new project. WebStart will send a file name
//			if (!ok)
//				return;
//		}
////		else {
////			loadDownloadedDocument(); //not used anymore
////		}
		if (currentFrame != null)
			currentFrame.activateView(ACTION_GANTT);

	}

	void setEnabledDocumentMenuActions(boolean enable) {
		if (Environment.isPlugin()) return;
       actionsMap.setEnabledDocumentMenuActions(enable);
        if (getCurrentFrame() != null) {
        	getCurrentFrame().getFilterToolBarManager().setEnabled(enable);
        }
        if (topTabs != null)
        	topTabs.setTrackingEnabled(enable && isDocumentWritable());
	}

	protected Document loadMasterProject() {
		return loadDocument(Session.MASTER,false,false);
	}
//	protected void loadDownloadedDocument(){
//		//showWaitCursor(true);
//
//		projectFactory.openDownloadedProject();
//		//showWaitCursor(false);
//	}
	public Document loadDocument(long id,boolean sync,boolean openAs){
		return loadDocument(id, sync, openAs, null);
	}
	protected Document loadDocument(long id,boolean sync,boolean openAs,Closure endSwingClosure){
		addHistory("loadDocument", new Object[]{id,sync,openAs,endSwingClosure==null});
		//showWaitCursor(true);
		if (id==-1L)
			return null;
		ProjectFactory factory = projectFactory;
		factory.setServer(server);
		LoadOptions opt=new LoadOptions();
		opt.setId(id);
		opt.setSync(sync);
		opt.setOpenAs(openAs);
		opt.setEndSwingClosure(endSwingClosure);

		Document result = factory.openProject(opt);
		//showWaitCursor(false);
		return result;
	}
protected boolean loadLocalDocument(String fileName,boolean merge){ //uses server to merge
	addHistory("loadLocalDocument",new Object[]{fileName,merge});
		//showWaitCursor(true);
		Project project;
		if (fileName==null) {
			//System.out.println("creating empty project");
			project = projectFactory.createProject();

		} else {
			LoadOptions opt=new LoadOptions();
			opt.setFileName(fileName);
			opt.setLocal(true);
			opt.setSync(false);

			if (merge) opt.setResourceMapping(new ResourceMappingForm(){
				public boolean execute(){
					if (getImportedResources().size() == 0) // don't show dialog if no resources were imported
						return true;
					if (resourceMappingDialog == null) {
						resourceMappingDialog = ResourceMappingDialog.getInstance(this);
						resourceMappingDialog.pack();
						resourceMappingDialog.setModal(true);
					} else resourceMappingDialog.setForm(this);
					resourceMappingDialog.bind(true);
					resourceMappingDialog.setLocationRelativeTo(getCurrentFrame());//to center on screen
					resourceMappingDialog.setVisible(true);
					return resourceMappingDialog.getDialogResult()==JOptionPane.OK_OPTION;
				}
			});

			if (fileName.endsWith(".pod")){ //$NON-NLS-1$
				opt.setImporter(Environment.getStandAlone()?LocalSession.LOCAL_PROJECT_IMPORTER:LocalSession.SERVER_LOCAL_PROJECT_IMPORTER);
			}else opt.setImporter(LocalSession.MICROSOFT_PROJECT_IMPORTER);
			project=projectFactory.openProject(opt);

		}
		//showWaitCursor(false);
		return project != null;
	}
	protected void saveLocalDocument(String fileName,final boolean saveAs){
		addHistory("saveLocalDocument",new Object[]{fileName,saveAs});
		//showWaitCursor(true);
		SaveOptions opt=new SaveOptions();
		opt.setLocal(true);
		final Project project=getCurrentFrame().getProject();
		if (project.getFileName()!=fileName){
			final DocumentFrame frame=getCurrentFrame();
			if (saveAs) opt.setSaveAs(true);
			opt.setPostSaving(new Closure(){
				public void execute(Object arg0) {
					if (saveAs) frame.setId(project.getUniqueId()+""); //$NON-NLS-1$
					refreshSaveStatus(true);
				}
			});
		}
		if (fileName.endsWith(".pod")){ //$NON-NLS-1$
			opt.setFileName(fileName);
			opt.setImporter(LocalSession.LOCAL_PROJECT_IMPORTER);
		}
		else{
			opt.setFileName(fileName/*+((fileName.endsWith(".xml"))?"":".xml")*/);
			opt.setImporter(LocalSession.MICROSOFT_PROJECT_IMPORTER);
//			if (Environment.isOpenProj()) {
//				if (!Alert.okCancel(Messages.getString("Warn.saveXML")))
//					return;
//			}
			//claur

		}
		opt.setPreSaving(getSavingClosure());
		projectFactory.saveProject(getCurrentFrame().getProject(),opt);
		//showWaitCursor(false);
	}

	private Closure getSavingClosure() {
		return null;
//		return new Closure() {
//
//			public void execute(Object arg0) {
//				Project proj = (Project)arg0;
//				SpreadSheetFieldArray fieldArray = (SpreadSheetFieldArray) getCurrentFrame().getGanttView().getSpreadSheet().getFieldArray();
//				proj.getDocumentWorkspace().setSetting("fieldArray", fieldArray);
//			}
//
//		};
//
	}

	private Closure getLoadClosure() {
		return null;
//		return new Closure() {
//
//			public void execute(Object arg0) {
//				Project proj = (Project)arg0;
//				SpreadSheetFieldArray fieldArray = (SpreadSheetFieldArray) proj.getDocumentWorkspace().getSetting("fieldArray");
//				if (fieldArray != null)
//					getCurrentFrame().getGanttView().getSpreadSheet().setFieldArray(fieldArray);
//			}
//
//		};

	}
	protected void saveLocalDocument(Project project,String fileName){
		//showWaitCursor(true);
		SaveOptions opt=new SaveOptions();
		opt.setFileName(fileName);
		opt.setLocal(true);
		opt.setPreSaving(getSavingClosure());

	    projectFactory.saveProject(project,opt);
		//showWaitCursor(false);
	}

	protected void closeProject(Project project){
		projectFactory.removeProject(project,true,true,true);
	}

	public void openLocalProject(){
		String fileName=SessionFactory.getInstance().getLocalSession().chooseFileName(false,null);
		if (fileName!=null) loadLocalDocument(fileName,!Environment.getStandAlone());
	}

	public void saveLocalProject(boolean saveAs){
		String fileName=null;
		Project project=getCurrentFrame().getProject();
		if (!saveAs){
			fileName=project.getFileName();
		}
		if (fileName==null) fileName=SessionFactory.getInstance().getLocalSession().chooseFileName(true,project.getGuessedFileName());
		if (fileName!=null) saveLocalDocument(fileName,saveAs);
	}


    public void showAboutDialog() {
    	if (aboutDialog == null) {
    		aboutDialog = AboutDialog.getInstance(getFrame());
    		aboutDialog.pack();
    		aboutDialog.setModal(true);
    	}
    	aboutDialog.setLocationRelativeTo(getFrame());//to center on screen
    	aboutDialog.setVisible(true);
    }

    public void showHelpDialog(/*DocumentFrame documentFrame*/) {
    	if (helpDialog == null) {
    		helpDialog = HelpDialog.getInstance(getFrame());
    		helpDialog.pack();
    		helpDialog.setModal(true);
    	}
    	helpDialog.setLocationRelativeTo(getFrame());//to center on screen
    	helpDialog.setVisible(true);
    }


/**
 * Show or focus the assignment dialog.  If showing, initilize to project
 * @param project
 */
    public void showAssignmentDialog(DocumentFrame documentFrame) {
		if (currentFrame==null||!getCurrentFrame().isActive())
			return;

    	if (assignResourcesDialog == null) {
    		assignResourcesDialog = new AssignmentDialog(documentFrame);
    		assignResourcesDialog.pack();
    		assignResourcesDialog.setModal(false);
    	}
    	assignResourcesDialog.setLocationRelativeTo(documentFrame);//to center on screen
        assignResourcesDialog.setVisible(true);
    }


	void doCalendarOptionsDialog() {
		finishAnyOperations();
		CalendarDialogBox.getInstance(getFrame(), null).doModal();
	}



	void print(){
		GraphPageable document=PrintDocumentFactory.getInstance().createDocument(getCurrentFrame(),true,false);
		if (document!=null) document.print();
	}


	void printPreview(){
		GraphPageable document=PrintDocumentFactory.getInstance().createDocument(getCurrentFrame(),false,false);
		if (document!=null) document.preview();
	}

	void savePDF() {
		GraphPageable document=PrintDocumentFactory.getInstance().createDocument(getCurrentFrame(),false,false);
		try {
			Class generator=ClassLoaderUtils.forName("org.projectlibre.export.ImageExport"); //claur
			generator.getMethod("export", new Class[]{GraphPageable.class,Component.class}).invoke(null,new Object[]{document,getContainer()});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DocumentFrame getCurrentFrame() {
		return currentFrame;
	}

	public Frame getFrame(){
		return frame;
	}

	public Container getContainer() {
		return container;
	}

	public JobQueue getJobQueue(){
		if (jobQueue==null){
			jobQueue=new JobQueue("GraphicManager",false); //$NON-NLS-1$
		}
		return jobQueue;
	}


	public boolean isDocumentActive() {
		return currentFrame != null && currentFrame.isActive();
	}
	public boolean isDocumentWritable() {
		return currentFrame != null && currentFrame.isActive() && !currentFrame.getProject().isReadOnly();
	}


	public void namedFrameActivated(NamedFrameEvent evt) {
//		System.out.println("Frame activated");
		NamedFrame frame = evt.getNamedFrame();
		if (frame instanceof DocumentFrame){
			DocumentFrame df=(DocumentFrame)frame;
			setCurrentFrame(df);

		}
	}
	public void namedFrameShown(NamedFrameEvent arg0) {
	}
	public void namedFrameTabShown(NamedFrameEvent evt) {
		NamedFrame frame = evt.getNamedFrame();
		if (frame instanceof DocumentFrame){
			DocumentFrame df=(DocumentFrame)frame;
			setCurrentFrame(df);

		}
	}
	public void windowActivated(WindowEvent arg0) {
	}
	public void windowClosed(WindowEvent evt) {
		if (evt.getWindow() == assignResourcesDialog)
			assignResourcesDialog = null;
	}

	public void windowClosing(WindowEvent arg0) {
	}

	public void windowDeactivated(WindowEvent arg0) {
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowStateChanged(WindowEvent arg0) {
	}

	protected boolean resourceType=false;
	protected boolean taskType=false;
	public void setTaskInformation(boolean taskType,boolean resourceType){
		this.taskType=taskType;
		this.resourceType=resourceType;
//		JButton button = null;
//		String infoText = "Task Information";
//		String notesText = "Task Notes";
//		String insertText = getMenuManager().getString(ACTION_INSERT_TASK + ButtonFactory.TOOLTIP_SUFFIX);
//		if (resourceType&&!taskType){
//			infoText = "Resource Information";
//			notesText = "Resource Notes";
//			insertText = "Insert Resource";
//		}
//		getMenuManager().setText(ACTION_INFORMATION,infoText);
//		getMenuManager().setText(ACTION_NOTES,notesText);
//		getMenuManager().setText(ACTION_INSERT_TASK,insertText);
	}


	public void setConnected(boolean connected){
		getMenuManager().setActionEnabled(ACTION_IMPORT_MSPROJECT,connected);
		getMenuManager().setActionEnabled(ACTION_OPEN_PROJECT,connected);
		getMenuManager().setActionEnabled(ACTION_NEW_PROJECT,connected);
		if (connected) refreshSaveStatus(true);

	}

	Set getActionSet(){
		Set actions=null;
		DocumentFrame df=getCurrentFrame();
		if (df!=null){
			SpreadSheet sp=df.getActiveSpreadSheet();
			actions=new HashSet();
			if (sp!=null){
				String[] a=sp.getActionList();
				if (a!=null){
					for (int i=0;i<a.length;i++) actions.add(a[i]);
				}
			}
		}
		return actions;
	}

	void setButtonState(Object currentImpl, Project project) {
		Set actions=getActionSet();
		boolean infoEnabled = currentImpl != null && (currentImpl instanceof Assignment||currentImpl instanceof Task||currentImpl instanceof Resource);
		boolean notVoid = currentImpl != null && !(currentImpl instanceof VoidNodeImpl);

		boolean readOnly = !isDocumentWritable();
		getMenuManager().setActionEnabled(ACTION_INFORMATION,infoEnabled);
		getMenuManager().setActionEnabled(ACTION_NOTES,infoEnabled);
		getMenuManager().setActionEnabled(ACTION_INSERT_TASK, !readOnly && (taskType || resourceType)&&(actions==null||actions.contains(ACTION_INSERT_TASK)));
		getMenuManager().setActionEnabled(ACTION_INSERT_RESOURCE, !readOnly && (taskType || resourceType)&&(actions==null||actions.contains(ACTION_INSERT_TASK)));
		getMenuManager().setActionEnabled(ACTION_CUT,!readOnly &&notVoid&&(actions==null||actions.contains(ACTION_CUT)));
		getMenuManager().setActionEnabled(ACTION_COPY,notVoid&&(actions==null||actions.contains(ACTION_COPY)));
		getMenuManager().setActionEnabled(ACTION_PASTE,!readOnly && (actions==null||actions.contains(ACTION_PASTE)));
		getMenuManager().setActionEnabled(ACTION_DELETE,!readOnly && (actions==null||actions.contains(ACTION_DELETE)));
		//TODO set state of paste button
		boolean isTask = currentImpl != null && currentImpl instanceof Task;
		boolean isResource = currentImpl != null && currentImpl instanceof Resource;
		boolean isHasStartAndEnd = currentImpl != null && currentImpl instanceof HasStartAndEnd;
		boolean writable = (currentImpl != null && !ClassUtils.isObjectReadOnly(currentImpl));
		getMenuManager().setActionEnabled(ACTION_INDENT,!readOnly &&(isTask || isResource)&&(actions==null||actions.contains(ACTION_INDENT)));
		getMenuManager().setActionEnabled(ACTION_OUTDENT,!readOnly &&(isTask || isResource)&&(actions==null||actions.contains(ACTION_OUTDENT)));
		getMenuManager().setActionEnabled(ACTION_LINK,isTask);
		getMenuManager().setActionEnabled(ACTION_UNLINK,isTask);
		getMenuManager().setActionEnabled(ACTION_ASSIGN_RESOURCES,isTask && writable);
		getMenuManager().setActionEnabled(ACTION_DELEGATE_TASKS,isTask && writable);
		getMenuManager().setActionEnabled(ACTION_UPDATE_TASKS,!readOnly && isTask);


		boolean insertProject = getCurrentFrame().isCurrentRowInMainProject();


//			taskType && (!notVoid || currentImpl == null || ((Task)currentImpl).getOwningProject() == null || ((Task)currentImpl).getOwningProject() == project);
		getMenuManager().setActionEnabled(ACTION_INSERT_PROJECT,!readOnly &&insertProject);

		BaseView view=null;
		DocumentFrame frame=getCurrentFrame();
		if (frame!=null){
			view=(BaseView)frame.getMainView().getTopComponent();
		}
		getMenuManager().setActionEnabled(ACTION_SCROLL_TO_TASK,isHasStartAndEnd&&view.canScrollToTask());

		if (currentFrame != null) {
			currentFrame.refreshUndoButtons();
			//refreshSaveStatus(false);
		}
		boolean printable = currentFrame!= null && currentFrame.isPrintable();
		getMenuManager().setActionEnabled(ACTION_PRINT,printable);
		getMenuManager().setActionEnabled(ACTION_PRINT_PREVIEW,printable);

		setZoomButtons();

		Field f = FieldDictionary.getInstance().getActionField(ACTION_DOCUMENTS);
		getMenuManager().setActionVisible(ACTION_DOCUMENTS,currentFrame != null && f != null);
		getMenuManager().setActionEnabled(ACTION_DOCUMENTS,currentFrame != null && isEnabledFieldAction(ACTION_DOCUMENTS,  currentFrame.getProject()));


	}

	public void setZoomButtons() {
		getMenuManager().setActionEnabled(ACTION_ZOOM_IN,currentFrame != null && currentFrame.canZoomIn());
		getMenuManager().setActionEnabled(ACTION_ZOOM_OUT,currentFrame != null && currentFrame.canZoomOut());

	}
	/**
	 * React to selection changed events and forward them on to any bottom window
	 */
	protected Node lastNode=null;
	public void selectionChanged(SelectionNodeEvent e) {
		if (assignResourcesDialog != null)
			assignResourcesDialog.selectionChanged(e);

		Node currentNode=e.getCurrentNode();
		Object currentImpl=currentNode.getImpl();
		setButtonState(currentImpl,currentFrame.getProject());
		// if on resource view, hide task info and vice versa.  Otherwise just show it
		if (lastNode!=null&&taskInformationDialog!=null&&(lastNode.getImpl() instanceof Task||lastNode.getImpl() instanceof Assignment)&&currentNode.getImpl() instanceof Resource){
			taskInformationDialog.setVisible(false);
			doInformationDialog(false);
		} else if (lastNode!=null&&resourceInformationDialog!=null&&lastNode.getImpl() instanceof Resource&&(currentNode.getImpl() instanceof Task||currentNode.getImpl() instanceof Assignment)){
			resourceInformationDialog.setVisible(false);
			doInformationDialog(false);
		}else{
			if (taskInformationDialog != null)
				taskInformationDialog.selectionChanged(e);
			if (resourceInformationDialog != null)
				resourceInformationDialog.selectionChanged(e);
		}
		lastNode=currentNode;
	}

	void refreshSaveStatus(boolean isSaving) {
		getMenuManager().setActionEnabled(ACTION_SAVE_PROJECT,currentFrame != null && !isSaving && currentFrame.getProject().needsSaving());
		setTitle(isSaving);

		FrameManager dm=getFrameManager();
		if (dm!=null) dm.update(); //update project combo
	}

	/* (non-Javadoc)
	 * @see com.projity.document.ObjectEvent.Listener#objectChanged(com.projity.document.ObjectEvent)
	 */
	public void objectChanged(ObjectEvent objectEvent) {

		if (objectEvent.getObject() instanceof Project) {
			Project project = (Project)objectEvent.getObject();
			if (objectEvent.isCreate()) {
				if (project.isOpenedAsSubproject())
					closeProjectFrame(project); // because it's now in a project
				else {
					DocumentFrame f = addProjectFrame(project);
				}

			} else if (objectEvent.isDelete()) {
				closeProjectFrame(project);
			}
			if (projectInformationDialog != null)
				projectInformationDialog.objectChanged(objectEvent);
			if (taskInformationDialog != null)
				taskInformationDialog.objectChanged(objectEvent);
			if (resourceInformationDialog != null)
				resourceInformationDialog.objectChanged(objectEvent);

		}
	}


	/**
	 * @return Returns the menuManager.
	 */
	public MenuManager getMenuManager() {
		if (menuManager == null) {
			menuManager = MenuManager.getInstance(this);
			addHandlers();
		}

		return menuManager;
	}

	public void finishAnyOperations() {
		if (getCurrentFrame() != null)
			getCurrentFrame().finishAnyOperations();
	}
	public void showWaitCursor(boolean show) {
		Frame frame=getFrame();
		if (frame==null) return;
		if (show)
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			frame.setCursor(Cursor.getDefaultCursor());
	}


	public final ProjectFactory getProjectFactory() {
		return projectFactory;
	}


    public String getTopViewId() {
    	if (getCurrentFrame() == null)
    		return ACTION_GANTT;
    	else
    		return getCurrentFrame().getTopViewId();
    }


    public boolean isApplet() {
    	return container instanceof Applet;
    }
    
    public void setRibbon(JRibbonFrame frame, MenuManager menuManger){
    	
    	
		final JPanel filtersPanel=new JPanel(new GridLayout(3, 1));
		filterToolBarManager = FilterToolBarManager.create(getMenuManager());
		filterToolBarManager.addButtonsInRibbonBand(filtersPanel);
		
    	CustomRibbonBandGenerator customBandsGenerator=new CustomRibbonBandGenerator() {
			
			@Override
			public JComponent createRibbonComponent(String ribbonBandName) {
				if ("FiltersRibbonBand".equals(ribbonBandName)){
					return filtersPanel;
				}
				else return null;
			}
		};
    	
		Collection<RibbonTask> ribbonTasks= menuManger.getRibbon(MenuManager.STANDARD_RIBBON, customBandsGenerator);
		JRibbon ribbon=frame.getRibbon();

		for (RibbonTask ribbonTask : ribbonTasks){
			ribbon.addTask(ribbonTask);
		}
		
		
		RibbonApplicationMenu applicationMenu=new RibbonApplicationMenu();
		
		
		ribbon.setApplicationMenu(applicationMenu);
		
		Collection<AbstractCommandButton> taskBars=menuManger.getTaskBar(MenuManager.STANDARD_RIBBON);
		for (AbstractCommandButton button : taskBars)
			ribbon.addTaskbarComponent(button);
		
		ribbon.configureHelp(IconManager.getRibbonIcon("ribbon.help",26,26), new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelpDialog();
				
			}
		});
		
		JLabel openprojLogo=ribbon.getOpenprojLogo();
		openprojLogo.setIcon(IconManager.getIcon("logo.OpenProj"));
		openprojLogo.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
            	BrowserControl.displayURL("http://www.projity.com/");
            }
         });
		
		JPanel projectViews=ribbon.getProjectViews();
		projectViews.setBorder(new EmptyBorder(0,0,0,0));		
		getMenuManager().initComponent(MenuManager.RIBBON_VIEW_BAR,projectViews);
		
		JPanel fileSelector=ribbon.getFileSelector();
		fileSelector.setLayout(new BorderLayout());
		fileSelector.setBackground(ProjectLibreRibbonUI.RIBBON_MENU_COLOR);
		JComponent filesComponent=((DefaultFrameManager)getFrameManager()).getProjectComboPanel();
		filesComponent.setBackground(ProjectLibreRibbonUI.RIBBON_MENU_COLOR);
		fileSelector.add(filesComponent,BorderLayout.EAST);
		projectViews.setBorder(new EmptyBorder(0,0,0,0));		
		


    	
    }
    
    
//    public void addProjectTab(String projectName){
//    	if (!(container instanceof JRibbonFrame))
//    		return;
//    	JRibbonFrame frame=(JRibbonFrame)container;
//    	JRibbon ribbon=frame.getRibbon();
//    	
//    	ribbon.getFileSelector().addTab("projectName", new JLabel());
//    }
//
//    public void removeProjectTab(String projectName){
//    	if (!(container instanceof JRibbonFrame))
//    		return;
//    	JRibbonFrame frame=(JRibbonFrame)container;
//    	JRibbon ribbon=frame.getRibbon();
//    	
//    	ribbon.getFileSelector().removeTabAt(index)("projectName", new JLabel());
//    }
//
//    public void selectProjectTab(String projectName){
//    	if (!(container instanceof JRibbonFrame))
//    		return;
//    	JRibbonFrame frame=(JRibbonFrame)container;
//    	JRibbon ribbon=frame.getRibbon();
//    	
//    	ribbon.getFileSelector().addTab("projectName", new JLabel());
//    }

    
    
    public void setToolBarAndMenus(final Container contentPane) {
    	JToolBar toolBar;
    	if (Environment.isRibbonUI()){
			if (Environment.isNeedToRestart()) {
				contentPane.add(new JLabel(Messages.getString("Error.restart")),BorderLayout.CENTER);
				return;
			}


			setRibbon((JRibbonFrame)container,getMenuManager());

			
			
			
//			JToolBar viewToolBar = getMenuManager().getToolBar(MenuManager.VIEW_TOOL_BAR_WITH_NO_SUB_VIEW_OPTION);
//			topTabs = new TabbedNavigation();
//			JComponent tabs = topTabs.createContentPanel(getMenuManager(),viewToolBar,0,JTabbedPane.TOP,true);
//			tabs.setAlignmentX(0.0f); // so it is left justified
//
//
//		    Box top = new Box(BoxLayout.Y_AXIS);
//		    JComponent bottom;
//			top.add(tabs);
//			bottom = new TabbedNavigation().createContentPanel(getMenuManager(),viewToolBar,1,JTabbedPane.BOTTOM,false);
//			contentPane.add(top, BorderLayout.BEFORE_FIRST_LINE);
//			contentPane.add(bottom,BorderLayout.AFTER_LAST_LINE);
//			if (Environment.isNewLaf())
//				contentPane.setBackground(Color.WHITE);

//			if (Environment.isMac()){
//				//System.setProperty("apple.laf.useScreenMenuBar","true");
//				//System.setProperty("com.apple.mrj.application.apple.menu.about.name", Messages.getMetaString("Text.ShortTitle"));
//				JMenuBar menu = getMenuManager().getMenu(Environment.getStandAlone()?MenuManager.MAC_STANDARD_MENU:MenuManager.SERVER_STANDARD_MENU);
//				//((JComponent)menu).setBorder(BorderFactory.createEmptyBorder());
//
//				((JFrame)container).setJMenuBar(menu);
//				projectListMenu = (JMenu) menu.getComponent(5);
//			}

    	} else if (Environment.isNewLook()) {
			if (Environment.isNeedToRestart()) {
				contentPane.add(new JLabel(Messages.getString("Error.restart")),BorderLayout.CENTER);
				return;
			}

			toolBar = getMenuManager().getToolBar(MenuManager.BIG_TOOL_BAR);
			if (!getLafManager().isToolbarOpaque())
				toolBar.setOpaque(false);
			if (!isApplet())
				getMenuManager().setActionVisible(ACTION_FULL_SCREEN, false);

			if (Environment.isExternal()) // external users only see project team
				getMenuManager().setActionVisible(ACTION_TEAM_FILTER, false);

			toolBar.addSeparator(new Dimension(20, 20));
			toolBar.add(new Box.Filler(new Dimension(0,0),new Dimension(0,0),new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE)));
			toolBar.add(((DefaultFrameManager)getFrameManager()).getProjectComboPanel());
			toolBar.add(Box.createRigidArea(new Dimension(20,20)));
			if (Environment.isNewLaf())
				toolBar.setBackground(Color.WHITE);
			toolBar.setFloatable(false);
			toolBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    Box top;
		    JComponent bottom;

	        top = new Box(BoxLayout.Y_AXIS);
	        toolBar.setAlignmentX(0.0f); // so it is left justified
			top.add(toolBar);

			JToolBar viewToolBar = getMenuManager().getToolBar(MenuManager.VIEW_TOOL_BAR_WITH_NO_SUB_VIEW_OPTION);
			topTabs = new TabbedNavigation();
			JComponent tabs = topTabs.createContentPanel(getMenuManager(),viewToolBar,0,JTabbedPane.TOP,true);
			tabs.setAlignmentX(0.0f); // so it is left justified


			top.add(tabs);
			bottom = new TabbedNavigation().createContentPanel(getMenuManager(),viewToolBar,1,JTabbedPane.BOTTOM,false);
			contentPane.add(top, BorderLayout.BEFORE_FIRST_LINE);
			contentPane.add(bottom,BorderLayout.AFTER_LAST_LINE);
			if (Environment.isNewLaf())
				contentPane.setBackground(Color.WHITE);

			if (Environment.isMac()){
				//System.setProperty("apple.laf.useScreenMenuBar","true");
				//System.setProperty("com.apple.mrj.application.apple.menu.about.name", Messages.getMetaString("Text.ShortTitle"));
				JMenuBar menu = getMenuManager().getMenu(Environment.getStandAlone()?MenuManager.MAC_STANDARD_MENU:MenuManager.SERVER_STANDARD_MENU);
				//((JComponent)menu).setBorder(BorderFactory.createEmptyBorder());

				((JFrame)container).setJMenuBar(menu);
				projectListMenu = (JMenu) menu.getComponent(5);
			}



		} else {

			toolBar = getMenuManager().getToolBar(Environment.isMac()?MenuManager.MAC_STANDARD_TOOL_BAR:MenuManager.STANDARD_TOOL_BAR);
			filterToolBarManager = FilterToolBarManager.create(getMenuManager());
			filterToolBarManager.addButtons(toolBar);
			contentPane.add(toolBar, BorderLayout.BEFORE_FIRST_LINE);
			JToolBar viewToolBar = getMenuManager().getToolBar(MenuManager.VIEW_TOOL_BAR);
			viewToolBar.setOrientation(JToolBar.VERTICAL);
			viewToolBar.setRollover(true);
			contentPane.add(viewToolBar, BorderLayout.WEST);

			JMenuBar menu = getMenuManager().getMenu(Environment.getStandAlone()?(Environment.isMac()?MenuManager.MAC_STANDARD_MENU:MenuManager.STANDARD_MENU):MenuManager.SERVER_STANDARD_MENU);

			if (!Environment.isMac()){
				((JComponent)menu).setBorder(BorderFactory.createEmptyBorder());
				JMenuItem logo = (JMenuItem) menu.getComponent(0);
				logo.setBorder(BorderFactory.createEmptyBorder());
				logo.setMaximumSize(new Dimension(124, 52));
				logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
				((JFrame)container).setJMenuBar(menu);
				projectListMenu = (JMenu) menu.getComponent(Environment.isMac()?5:6);
		}

		//accelerators
		    addCtrlAccel(KeyEvent.VK_G, ACTION_GOTO, null);
		    addCtrlAccel(KeyEvent.VK_L, ACTION_LINK, null);
		    addCtrlAccel(KeyEvent.VK_F, ACTION_FIND, null);
		    addCtrlAccel(KeyEvent.VK_Z, ACTION_UNDO, null);			//- Sanhita
		    addCtrlAccel(KeyEvent.VK_Y, ACTION_REDO, null);
		    addCtrlAccel(KeyEvent.VK_N, ACTION_NEW_PROJECT, null);
		    addCtrlAccel(KeyEvent.VK_O, ACTION_OPEN_PROJECT, null);
		    addCtrlAccel(KeyEvent.VK_S, ACTION_SAVE_PROJECT, null);
		    addCtrlAccel(KeyEvent.VK_P, ACTION_PRINT, null);			//-Sanhita
		    addCtrlAccel(KeyEvent.VK_I, ACTION_INSERT_TASK, null);

		    // Indentation
		    addCtrlAccel(KeyEvent.VK_PERIOD, ACTION_INDENT, null);
		    addCtrlAccel(KeyEvent.VK_COMMA, ACTION_OUTDENT, null);
		    addCtrlAccel(KeyEvent.VK_LEFT, ACTION_OUTDENT, null);
		    addCtrlAccel(KeyEvent.VK_RIGHT, ACTION_INDENT, null);

		    // Expand / Collapse 
		    addCtrlAccel(KeyEvent.VK_PLUS, ACTION_EXPAND, new ExpandAction());
		    addCtrlAccel(KeyEvent.VK_ADD, ACTION_EXPAND, new ExpandAction());
		    addCtrlAccel(KeyEvent.VK_EQUALS, ACTION_EXPAND, new ExpandAction());
		    addCtrlAccel(KeyEvent.VK_MINUS, ACTION_COLLAPSE, new CollapseAction());
		    addCtrlAccel(KeyEvent.VK_SUBTRACT, ACTION_COLLAPSE, new CollapseAction());
		    
			// To force a recalculation. This normally shouldn't be needed.
		    addCtrlAccel(KeyEvent.VK_R, ACTION_RECALCULATE, new RecalculateAction());
    }

    private void addCtrlAccel(int vk, String actionConstant, Action action) {
		RootPaneContainer root = (RootPaneContainer)container;
		InputMap inputMap = root.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		KeyStroke key = KeyStroke.getKeyStroke(vk, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()); //claur use getMenuShortcutKeyMask so it work on Mac too.
		inputMap.put(key, actionConstant);
		if (action == null)
			action = menuManager.getActionFromId(actionConstant);
		root.getRootPane().getActionMap().put(actionConstant, action);
	}
    private LookAndFeel getPlaf() {
    	return getLafManager().getPlaf();
    }

    public void invalidate() {
    	container.invalidate();
    	((RootPaneContainer)container).getContentPane().invalidate();
    	((RootPaneContainer)container).getContentPane().repaint();
    }

    public void initLookAndFeel() {
   		getLafManager().initLookAndFeel();

    }


	private HashMap colorThemes = null;
	public HashMap getColorThemes() {
		if (colorThemes == null) {
			colorThemes = new HashMap();
			colorThemes.put(ACTION_GANTT,"Bloody Moon"); //$NON-NLS-1$
			colorThemes.put(ACTION_TRACKING_GANTT,"Mahogany"); //$NON-NLS-1$
			colorThemes.put(ACTION_NETWORK,"Emerald Grass"); //$NON-NLS-1$
			colorThemes.put(ACTION_RESOURCES,"Blue Yonder"); //$NON-NLS-1$
			colorThemes.put(ACTION_PROJECTS,"Emerald Grass"); //$NON-NLS-1$
			colorThemes.put(ACTION_WBS,"Sepia"); //$NON-NLS-1$
			colorThemes.put(ACTION_RBS,"Steel Blue"); //$NON-NLS-1$
			colorThemes.put(ACTION_REPORT,"Aqua"); //$NON-NLS-1$
			colorThemes.put(ACTION_TASK_USAGE_DETAIL,"Brown Velvet"); //$NON-NLS-1$
			colorThemes.put(ACTION_RESOURCE_USAGE_DETAIL,"Earth Fresco"); //$NON-NLS-1$
		}
		return colorThemes;
	}

	public void setPaletteText(String themeName){
		getMenuManager().setText(ACTION_PALETTE,themeName);
	}

	void setColorTheme(String viewName){
		getLafManager().setColorTheme(viewName);
	}

	public class PaletteAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();

			getLafManager().changePalette();

		}
		protected boolean allowed(boolean enable){
			LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
			return getLafManager().isChangePaletteAllowed(lookAndFeel);
		}

	}

	public class LookAndFeelAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();

		}
	}

	public class FullScreenAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			encodeWorkspace(); // so new window takes this one's preferences
			// see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5029025
			try {
				Class cl=Class.forName("netscape.javascript.JSObject");
				Object win=cl.getMethod("getWindow", new Class[]{Applet.class}).invoke(null, new Object[]{container});
		        //JSObject win = JSObject.getWindow((Applet) container);
		        cl.getMethod("call", new Class[]{String.class,(new Object[]{}).getClass()}).invoke(win, new Object[]{"fullScreen",null} );
				//win.call("fullScreen", null);		  	     // Call f() in HTML page //$NON-NLS-1$

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class RefreshAction extends MenuActionsMap.GlobalMenuAction {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent arg0) {
			setMeAsLastGraphicManager();
			getStartupFactory().restart(GraphicManager.this);
		}
	}

/**
 * Decode the current workspace (currently using XML though could be binary)
 * @return workspace object decoded from lastWorkspace static
 */
	private Workspace decodeWorkspaceXML() {
		ByteArrayInputStream stream = new ByteArrayInputStream(((String)lastWorkspace).getBytes());
		XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(stream));
		Workspace workspace = (Workspace) decoder.readObject();
		decoder.close();
		return workspace;
	}
	private Workspace decodeWorkspaceBinary() {
        ByteArrayInputStream bin=new ByteArrayInputStream((byte[]) lastWorkspace);
        ObjectInputStream in;
		try {
			in = new ObjectInputStream(bin);
	        return (Workspace) in.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Workspace decodeWorkspace() {
		if (lastWorkspace == null)
			return null;
		return BINARY_WORKSPACE ? decodeWorkspaceBinary() : decodeWorkspaceXML();
	}

/**
 * Encode the current workspace and store it off in lastWorkspace.
 * Currently I use an XML format for easier debugging. It could be serialized as binary as well since
 * all objects in the graph implement Serializable
 *
 */
	private void encodeWorkspaceXML() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(stream));
		encoder.writeObject(createWorkspace(SavableToWorkspace.VIEW));
		encoder.close();
		lastWorkspace = stream.toString();
//		System.out.println(lastWorkspace);
	}
	private void encodeWorkspaceBinary() {
        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(bout);
	        out.writeObject(createWorkspace(SavableToWorkspace.VIEW));
	        out.close();
	        bout.close();
	    	lastWorkspace = bout.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void encodeWorkspace() {
		if (BINARY_WORKSPACE)
			encodeWorkspaceBinary();
		else
			encodeWorkspaceXML();
	}


	public void restoreWorkspace(WorkspaceSetting w, int context) {
		Workspace ws = (Workspace) w;
		colorThemes = ws.colorThemes;
		getFrameManager().restoreWorkspace(ws.frames, context);
	}

	public WorkspaceSetting createWorkspace(int context) {
		Workspace ws = new Workspace();
		ws.colorThemes = getColorThemes();
		ws.frames = getFrameManager().createWorkspace(context);
		//TODO The active states of BarStyles (and other styles) are currently static. This is ok for applets, but not a general restore workspace feature
		return ws;
	}

	public static class Workspace implements WorkspaceSetting   {
		private static final long serialVersionUID = -6606344141026658401L;
		private HashMap colorThemes;
		WorkspaceSetting frames;
		public HashMap getColorThemes() {
			return colorThemes;
		}
		public void setColorThemes(HashMap colorThemes) {
			this.colorThemes = colorThemes;
		}
		public WorkspaceSetting getFrames() {
			return frames;
		}
		public void setFrames(WorkspaceSetting frames) {
			this.frames = frames;
		}
	}

	public static final Object getLastWorkspace() {
		return lastWorkspace;
	}


	public GraphicManager getGraphicManager() {
		return this;
	}


	public void setGraphicManager(GraphicManager manager) {
		// TODO Auto-generated method stub

	}

	public FrameManager getFrameManager() {
		return frameManager;
	}

	public void setFrameManager(FrameManager frameManager) {
		this.frameManager = frameManager;
	}

	public void initView() {
		Container c=container;
		if (container!=null && container instanceof RootPaneContainer){
			c=((RootPaneContainer)container).getContentPane();
		}
        if (!Environment.isRibbonUI()) c.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        c.add(panel, "Center"); //$NON-NLS-1$
        setFrameManager(new DefaultFrameManager(container, panel,this));

		initLayout();

		if (!Environment.isPlugin()) setToolBarAndMenus(c);

        setEnabledDocumentMenuActions(false);
    	Workspace workspace = decodeWorkspace();
        if (workspace != null) {
        	restoreWorkspace(workspace, SavableToWorkspace.VIEW);

        } else
        	initProject();
//        container.invalidate();
 	}

	public BaselineDialog getBaselineDialog() {
		return baselineDialog;
	}

	public void setBaselineDialog(BaselineDialog baselineDialog) {
		this.baselineDialog = baselineDialog;
	}
	public StartupFactory getStartupFactory() {
		return startupFactory;
	}
	public void setStartupFactory(StartupFactory startupFactory) {
		this.startupFactory = startupFactory;
	}

	public boolean isEditingMasterProject() {
		Project currentProject=currentFrame.getProject();
		if (currentProject == null)
			return false;
		return currentProject.isMaster() && !currentProject.isReadOnly();

	}

	public GlobalPreferences getPreferences(){
		if (preferences==null) {
			preferences=new GlobalPreferences();
			if (Environment.isExternal())
				preferences.setShowProjectResourcesOnly(true);
		}
		return preferences;
	}


	//for AssignmentDialog
	private ResourceInTeamFilter assignmentDialogTransformerInitializationClosure;
	public Closure setAssignmentDialogTransformerInitializationClosure(){
		return new Closure(){
			public void execute(Object arg) {
				ViewTransformer transformer=(ViewTransformer)arg;
		        NodeFilter hiddenFilter=transformer.getHiddenFilter();
		        if (hiddenFilter!=null&& hiddenFilter instanceof ResourceInTeamFilter){
		        	assignmentDialogTransformerInitializationClosure=(ResourceInTeamFilter)hiddenFilter;
		        	assignmentDialogTransformerInitializationClosure.setFilterTeam(getGraphicManager().getPreferences().isShowProjectResourcesOnly());
		        }else assignmentDialogTransformerInitializationClosure=null;
			}
		};
	}
	public ResourceInTeamFilter getAssignmentDialogTransformerInitializationClosure() {
		return assignmentDialogTransformerInitializationClosure;
	}
	public FilterToolBarManager getFilterToolBarManager() {
		return filterToolBarManager;
	}

	boolean initialized=false;
	private Mutex initializing=new Mutex();
	public void beginInitialization(){
		showWaitCursor(true);
		initializing.lock();
	}
	public void finishInitialization(){
		container.setVisible(true);
		initialized=true;
		initializing.unlock();
		showWaitCursor(false);
	}
	public void waitInitialization(){
		initializing.waitUntilUnlocked();
	}

	/**
	 * Methods that are called using reflection to save workspace stuff into project
	 * @return
	 */
	public static SpreadSheetFieldArray getCurrentFieldArray() {
		return (SpreadSheetFieldArray) getDocumentFrameInstance().getGanttView().getSpreadSheet().getFieldArrayWithWidths(getDocumentFrameInstance().getGanttColumns());
	}
	public static void setCurrentFieldArray(Object fieldArray) {
		getDocumentFrameInstance().getGanttView().getSpreadSheet().setFieldArrayWithWidths((SpreadSheetFieldArray)fieldArray);
	}

	public static UndoController getUndoController(){
		DocumentFrame frame=GraphicManager.getDocumentFrameInstance();
		if (frame==null) return null;
		return frame.getUndoController();
	}
	public void setAllButResourceDisabled(boolean disable) {
		if (topTabs!=null) topTabs.setAllButResourceDisabled(disable);
	}
	public void doFind(Searchable searchable, Field field) {
		if (currentFrame==null||!getCurrentFrame().isActive())
			return;
		if (searchable == null)
			return;
		currentFrame.doFind(searchable, field);

	}


    public void registerForMacOSXEvents() {
        if (Environment.isMac()) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quitApplication", (Class[])null));
                OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("showAboutDialog", (Class[])null));
                //OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("preferences", (Class[])null));
                if (Environment.getStandAlone()) OSXAdapter.setFileHandler(this, getClass().getDeclaredMethod("openFile", new Class[] { String.class }));
            } catch (Exception e) {
                System.err.println("Error while loading the OSXAdapter:");
                e.printStackTrace();
            }
        }
    }

    protected String lastFileName;
    public void openFile(String fileName){
    	lastFileName=fileName;
    	if (fileName!=null&&initialized) loadLocalDocument(fileName,!Environment.getStandAlone());
    }

	public String getLastFileName() {
		return lastFileName;
	}

	public boolean quitApplication() throws Exception{
		final boolean[] lock=new boolean[]{false};

		JobRunnable exitRunnable=new JobRunnable("Local: closeProjects"){
			public Object run() throws Exception{
				synchronized (lock) {
					lock[0]=true;
					lock.notifyAll();
				}
    	    	return null;
			}
		};
		final boolean[] closeStatus=new boolean[]{false};
		final Job job=projectFactory.getPortfolio().getRemoveAllProjectsJob(exitRunnable,false,closeStatus);
		SessionFactory.getInstance().getLocalSession().schedule(job);

		synchronized(lock){
			while (!lock[0]){
				try{
						lock.wait();
					}catch (InterruptedException e) {}
			}
		}
		if (closeStatus[0]){
			Frame frame=getFrame();
			if (frame!=null) frame.dispose();
			//System.exit(0);
			return true;
		}else return false;
	}


	public static Project getProject() {
		if (lastGraphicManager == null)
			return null;
		if (lastGraphicManager.currentFrame==null)
			return null;
		return lastGraphicManager.currentFrame.getProject();
	}

	public void addHistory(String command,Object[] args){
		history.add(new CommandInfo(command,args));
	}
	public void addHistory(String command){
		history.add(new CommandInfo(command,null));
	}
	public static List<CommandInfo> getHistory() {
		if (lastGraphicManager == null)
			return null;
		return lastGraphicManager.history;
	}

}