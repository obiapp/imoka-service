/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.views;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.imoka.domain.ProjectContent;
import org.imoka.entities.Machines;
import org.imoka.jsf.util.JsfUtil;
import org.imoka.services.S7Handler;
import org.imoka.sessions.MachinesFacade;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.TreeNodeEvent;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewProject")
@SessionScoped
public class ViewProject {

    /**
     * Persistence of machines
     */
    @EJB
    private MachinesFacade machinesFacade;
    
    @Inject S7Handler s7Handler;

    private TreeNode root;
    private TreeNode projectNode;
    private TreeNode machineNode;
    private TreeNode datasNode;
    private TreeNode viewsNode;
    private TreeNode accessNode;

    private TreeNode selectedNode;
    private Integer counterClick = 0;
    private Long elapseTime = 0l;

    private final String L_MACHINES_LIST = "app/machines/List.xhtml";
    private final String L_MACHINES_PLC = "app/machines/PLC.xhtml";
    private final String L_PROJECT_INDEX = "index.xhtml";

    /**
     * Creates a new instance of ViewProject
     */
    public ViewProject() {
    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    // =========================================================================
    /**
     * Initialise view project
     */
    @PostConstruct
    public void init() {
        // Init counterClick
        counterClick = 0;
        elapseTime = 0l;

        // Init tree
        root = new DefaultTreeNode(ProjectContent.Type.NONE.toString(),
                new ProjectContent("Root", ProjectContent.Type.NONE, null), null);

        projectNode = new DefaultTreeNode(ProjectContent.Type.PROJECT.toString(),
                new ProjectContent("Project 1 - ?name?", ProjectContent.Type.PROJECT, null), root);

        machineNode = new DefaultTreeNode(ProjectContent.Type.MACHINES.toString(),
                new ProjectContent("Machines", ProjectContent.Type.MACHINES, null), projectNode);
        loadMachines();
        datasNode = new DefaultTreeNode(ProjectContent.Type.DATAS.toString(), "Données", projectNode);
        viewsNode = new DefaultTreeNode(ProjectContent.Type.VIEWS.toString(), "Views", projectNode);
        accessNode = new DefaultTreeNode(ProjectContent.Type.ACCESS.toString(), "Accès", projectNode);

    }

    /**
     * <p>
     * Load all machine from database to the project
     * </p>
     */
    private void loadMachines() {
        List<Machines> lstMachines = machinesFacade.findAll();
        lstMachines.stream().forEach((machine) -> {
            DefaultTreeNode defaultTreeNode = new DefaultTreeNode(
                    machine.getType().getType().toString(),
                    new ProjectContent(machine.getMachine(), ProjectContent.Type.PLC, machine),
                    machineNode);
        });
    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    // =========================================================================
    public void handleSelectTreeNode(NodeSelectEvent event) throws IOException {
        ProjectContent content = (ProjectContent) event.getTreeNode().getData();
        switch (content.getType()) {
            case PROJECT:
                // Initialise elapseTime
                if (counterClick == 0) {
                    elapseTime = System.currentTimeMillis();
                    counterClick++;
                } else if (System.currentTimeMillis() - elapseTime < 1500) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    String ctxPath = ctx.getExternalContext().getRequestContextPath();
                    String servletPath = ctx.getExternalContext().getRequestServletPath();
                    ctx.getExternalContext().redirect(
                            ctxPath + servletPath + "/"
                            + L_PROJECT_INDEX);

                    // Finish double click
                    counterClick = 0;
                    elapseTime = 0l;
                } else {
                    counterClick = 0;
                    elapseTime = 0l;
                }
                break;
            case MACHINES:
                // Initialise elapseTime
                if (counterClick == 0) {
                    elapseTime = System.currentTimeMillis();
                    counterClick++;
                } else if (System.currentTimeMillis() - elapseTime < 1500) {
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    String ctxPath = ctx.getExternalContext().getRequestContextPath();
                    String servletPath = ctx.getExternalContext().getRequestServletPath();
                    ctx.getExternalContext().redirect(
                            ctxPath + servletPath + "/"
                            + L_MACHINES_LIST);

                    // Finish double click
                    counterClick = 0;
                    elapseTime = 0l;
                } else {
                    counterClick = 0;
                    elapseTime = 0l;
                }
                break;
            case PLC:
                // Initialise elapseTime
                if (counterClick == 0) {
                    elapseTime = System.currentTimeMillis();
                    counterClick++;
                } else if (System.currentTimeMillis() - elapseTime < 1500) {
                    s7Handler.setSelected((Machines)content.getValue());
                    s7Handler.handlePLCConnection();
                    
                    FacesContext ctx = FacesContext.getCurrentInstance();
                    String ctxPath = ctx.getExternalContext().getRequestContextPath();
                    String servletPath = ctx.getExternalContext().getRequestServletPath();
                    ctx.getExternalContext().redirect(
                            ctxPath + servletPath + "/"
                            + L_MACHINES_PLC);

                    // Finish double click
                    counterClick = 0;
                    elapseTime = 0l;
                } else {
                    counterClick = 0;
                    elapseTime = 0l;
                }
                break;
            default:
                break;
        }
    }

    public void handleUnSelectTreeNode(NodeSelectEvent event) {

    }

    // =========================================================================
    // =========================================================================
    // =========================================================================
    // =========================================================================
    public TreeNode getRoot() {
        return root;
    }

    /**
     * <p>
     * Specify the root node of the project view
     * </p>
     *
     * @param root
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * <p>
     * Identify the actual node selected
     * </p>
     *
     * @param selectedNode current node selected
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

}
