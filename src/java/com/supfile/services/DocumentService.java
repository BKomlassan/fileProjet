/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supfile.services;

import com.supfile.controller.UtilisateurController;
import com.supfile.controller.Library;
import com.supfile.entity.Document;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import java.io.FileOutputStream;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author BenyiJeanMarc
 */
@ManagedBean(name = "documentService")
@ApplicationScoped
@SessionScoped
public class DocumentService implements Serializable {

    TreeNode root,documents1, documents2, documents3, documents4;
    String dirPath1, dirPath2, dirPath3, dirPath4;
    private DataModel<Document> filesModel;
    private String fileName;
    @ManagedProperty("#{utilisateurController}")
    private UtilisateurController utilisateurController;
    private Document documentSelect;

    public TreeNode createDocuments(String login) {

         root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);

        dirPath1 = "C:\\" + login;
        File dir1 = new File(dirPath1);
        String[] files1 = dir1.list();
        List<String> list = new ArrayList<>();
        String extension_t1, extension_t2, extension_t3, extension_t4;
        int i = 0;
        if (files1.length != 0) {
            for (String aFile1 : files1) {
                extension_t1 = extensionFichier(aFile1);
                if (extension_t1.equals("Dossier")) {
                    documents1 = new DefaultTreeNode(new Document(aFile1, "-", "Folder"), root);

                    dirPath2 = "C:\\" + login + "\\" + aFile1;
                    File dir2 = new File(dirPath2);
                    String[] files2 = dir2.list();
                    if (files2.length != 0) {
                        for (String aFile2 : files2) {
                            extension_t2 = extensionFichier(aFile2);
                            if (extension_t2.equals("Dossier")) {
                                documents2 = new DefaultTreeNode(new Document(aFile2, "-", "Folder"), documents1);

                                dirPath3 = "C:\\" + login + "\\" + aFile1 + "\\" + aFile2;
                                File dir3 = new File(dirPath3);
                                String[] file3 = dir3.list();
                                if (file3.length != 0) {
                                    for (String aFile3 : file3) {
                                        extension_t3 = extensionFichier(aFile3);
                                        if (extension_t3.equals("Dossier")) {
                                            documents3 = new DefaultTreeNode(new Document(aFile3, "-", "Folder"), documents2);

                                            dirPath4 = "C:\\" + login + "\\" + aFile1 + "\\" + aFile2 + "\\" + aFile3;
                                            File dir4 = new File(dirPath4);
                                            String[] file4 = dir4.list();
                                            if (file4.length != 0) {
                                                for (String aFile4 : file4) {
                                                    extension_t4 = extensionFichier(aFile4);
                                                    if (extension_t4.equals("Dossier")) {
                                                        documents4 = new DefaultTreeNode(new Document(aFile4, "-", "Folder"), documents3);
                                                    } else {
                                                        getContent(dirPath4);
                                                    }
                                                }
                                            }

                                        } else {
                                            getContent(dirPath3);
                                        }

                                    }

                                }
                            } else {
                                getContent(dirPath2);
                            }
                        }

                    }
                } else {
                    getContent(dirPath1);
                }

            }
        }
        return root;
    }

    public DataModel<Document> getContent(String chemin) {
        TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);
        String a = chemin;
        if (a == null) {
            chemin = "C : \\" + utilisateurController.getUtilisateurConnecte().getLogin();
        }
        double taille_t;
        ArrayList<Document> documents = new ArrayList<Document>();
        File dir = new File(chemin);
        String[] files = dir.list();
        String extension_t;
        if (files.length != 0) {
            for (String file : files) {
                extension_t = extensionFichier(file);
                taille_t = tailleFichier(chemin + "\\" + file);
                if ((extension_t.equals(".exe")) || (extension_t.equals(".pdf")) || (extension_t.equals(".zip")) || (extension_t.equals(".txt")) || (extension_t.equals(".mp4")) || (extension_t.equals(".xml")) || (extension_t.equals(".doc")) || (extension_t.equals(".docx")) || (extension_t.equals(".xls")) || (extension_t.equals(".png")) || (extension_t.equals(".jpg")) || (extension_t.equals(".mp3")) || (extension_t.equals(".avi")) || (extension_t.equals(".jar"))) {

                    Document dcm = new Document(file, Double.toString(taille_t).substring(0, 3) + "  Mo", extensionFichier(file).substring(1, 4));
                    documents.add(dcm);
                }
            }
        }
        filesModel = new ListDataModel<>(documents);
        return filesModel;
    }
    
 public Document fileSelected()
 {
     
     DataModel<Document> var = filesModel;
     if (var.getRowCount()!=0)
     {
         for(Document d : var)
         {
             d = var.getRowData();
             documentSelect = d;
         }
     }
     
     System.out.print(documentSelect.getName());
     return documentSelect;
     
 }
 
    public void onRowSelect(SelectEvent event) {
         fileSelected();
        FacesMessage msg = new FacesMessage("Car Selected", ((Document) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    

    public void createFile(String path) {

        try {
            File dir = new File(path);
            String[] files = dir.list();
            List<String> list = new ArrayList<>();

        } catch (Exception e) {

        }
    }

    public void renameFolder(String path, String newName) {
        File s = new File(path);
        if (s.isDirectory()) {
            s.renameTo(s);
            System.out.print(path);
            System.out.print(s);
        } 
    }
    
    public  void renameFile(String path, String newName)
    {	
    	
		File oldfile =new File(path);
		File newfile =new File(newName+""+extensionFichier(path));
		
		if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful");
		}else{
			System.out.println("Rename failed");
		}
    	
    }
    
       
    

    public void createDirectoryByLogin(String Path, String NameFolder) {
        Path path_dossier = Paths.get(Path + "\\" + NameFolder);
        try {
            Files.createDirectories(path_dossier);
            System.out.print(path_dossier);
        } catch (Exception e) {

        }
    }

    public String extensionFichier(String nom_fichier) {

        String extension = "Type de fichier inconnu";
        if (nom_fichier.lastIndexOf(".") > 0) {
            String ext = nom_fichier.substring(nom_fichier.lastIndexOf("."));
            extension = ext;
        } else {
            extension = "Dossier";
        }
        return extension;
    }

    public double tailleFichier(String path) {
        File file = new File(path);
        double taille = 0;

        if (file.exists()) {

            double bytes = file.length();
            double bits = bytes * 8;
            double kilobytes = bytes / 1024;
            double megabytes = kilobytes / 1024;
            double gigabytes = megabytes / 1024;
            double terabytes = gigabytes / 1024;

            taille = megabytes;
        } else {
            System.out.println("Fichier innexistant");
        }
        return taille;

    }

    public TreeNode getDocuments2() {
        return documents2;
    }

    public void setDocuments2(TreeNode documents2) {
        this.documents2 = documents2;
    }

    public TreeNode getDocuments3() {
        return documents3;
    }

    public void setDocuments3(TreeNode documents3) {
        this.documents3 = documents3;
    }

    public String getDirPath2() {
        return dirPath2;
    }

    public void setDirPath2(String dirPath2) {
        this.dirPath2 = dirPath2;
    }

    public String getDirPath3() {
        return dirPath3;
    }

    public void setDirPath3(String dirPath3) {
        this.dirPath3 = dirPath3;
    }

    public String getDirPath4() {
        return dirPath4;
    }

    public void setDirPath4(String dirPath4) {
        this.dirPath4 = dirPath4;
    }

    public TreeNode getDocuments1() {
        return documents1;
    }

    public void setDocuments1(TreeNode documents1) {
        this.documents1 = documents1;
    }

    public String getDirPath() {
        return dirPath2;
    }

    public void setDirPath(String dirPath) {
        this.dirPath2 = dirPath;
    }

    public String getDirPath1() {
        return dirPath1;
    }

    public void setDirPath1(String dirPath1) {
        this.dirPath1 = dirPath1;
    }

    public DataModel<Document> getFilesModel() {
        return filesModel;
    }

    public void setFilesModel(DataModel<Document> filesModel) {
        this.filesModel = filesModel;
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

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getDocuments4() {
        return documents4;
    }

    public void setDocuments4(TreeNode documents4) {
        this.documents4 = documents4;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Document getDocumentSelect() {
        return documentSelect;
    }

    public void setDocumentSelect(Document documentSelect) {
        this.documentSelect = documentSelect;
    }

    
}
