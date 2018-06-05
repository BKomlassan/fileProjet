/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.controller;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import com.supfile.entity.Document;
import com.supfile.services.DocumentService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author BenyiJeanMarc
 */
@ManagedBean(name = "library")
@SessionScoped
public class Library implements Serializable {

    private TreeNode root;
    private String folderName;
    private String folderNewName;
    private TreeNode selectedNode;
    private String lieu;
    String destination;

    private List<Document> document;
    private Document selectedDocument;
    private List<Document> selectedDocuments;

    @ManagedProperty("#{documentService}")
    private DocumentService service;

    @ManagedProperty("#{utilisateurController}")
    private UtilisateurController utilisateurController;

    @PostConstruct
    public void init() {
        lieu = "\\";
        root = service.createDocuments(utilisateurController.getUtilisateurConnecte().getLogin());
    }

    public void createFolder() {
        service.createDirectoryByLogin(lieu, folderName);
        lieu = "\\";
        root = service.createDocuments(utilisateurController.getUtilisateurConnecte().getLogin());

    }

    public void renameFolder() {
        service.renameFolder(lieu, folderNewName);
    }

    public void upload(FileUploadEvent event) {
        destination = lieu + "\\";
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        System.out.print(destination);

        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            System.out.print(fileName);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFolder () {
        File del = new File(lieu);
        try {
            recursifDelete(del);
        } catch (IOException e) {
        // TODO Auto-generated catch block 
            e.printStackTrace();
        }
    }

    public static void recursifDelete(File path) throws IOException {

        if (!path.exists()) {
            throw new IOException(
                    "File not found '" + path.getAbsolutePath() + "'");
        }
        if (path.isDirectory()) {
            File[] children = path.listFiles();
            for (int i = 0; children != null && i < children.length; i++) {
                recursifDelete(children[i]);
            }
            if (!path.delete()) {
                throw new IOException(
                        "No delete path '" + path.getAbsolutePath() + "'");
            }
        } else if (!path.delete()) {
            throw new IOException(
                    "No delete file '" + path.getAbsolutePath() + "'");
        }
    }

    public void refresh() {
        RequestContext.getCurrentInstance().update("form");
    }

    public void deleteNode() {
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);
        selectedNode = null;
    }

    private TreeNode[] getPathToRoot(TreeNode aNode) {

        ArrayList<String> list = new ArrayList<String>();
        String path1, path_contenu;
        path1 = "C:\\" + utilisateurController.getUtilisateurConnecte().getLogin();
        TreeNode[] retNodes;
        ArrayList<TreeNode> temp = new ArrayList<TreeNode>();

        /* Check for null, in case someone passed in a null node, or
         they passed in an element that isn't rooted at root. */
        while (aNode != null) {
            temp.add(aNode);
            aNode = aNode.getParent();
        }

        int num = temp.size();
        retNodes = new TreeNode[num];
        for (int i = num - 1; i >= 0; i--) {
            retNodes[num - 1 - i] = temp.get(i);

        }

        if (Arrays.toString(retNodes) != null) {
            for (int i = 1; i < retNodes.length; i++) {
                path_contenu = path1 + "\\" + retNodes[i];
                path1 = path_contenu;
                lieu = path1;

            }
        }
        return retNodes;
    }

    public void onNodeExpand(NodeExpandEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onNodeCollapse(NodeCollapseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String onNodeSelect(NodeSelectEvent event) {
        getPathToRoot(event.getTreeNode());
        service.getContent(lieu);

        return lieu;

    }

    public void onNodeUnselect(NodeUnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((Document) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public UtilisateurController getUtilisateurController() {
        return utilisateurController;
    }

    public void setUtilisateurController(UtilisateurController utilisateurController) {
        this.utilisateurController = utilisateurController;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setService(DocumentService service) {
        this.service = service;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public DocumentService getService() {
        return service;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getFolderNewName() {
        return folderNewName;
    }

    public void setFolderNewName(String folderNewName) {
        this.folderNewName = folderNewName;
    }

    public List<Document> getDocument() {
        return document;
    }

    public void setDocument(List<Document> document) {
        this.document = document;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public List<Document> getSelectedDocuments() {
        return selectedDocuments;
    }

    public void setSelectedDocuments(List<Document> selectedDocuments) {
        this.selectedDocuments = selectedDocuments;
    }

}
