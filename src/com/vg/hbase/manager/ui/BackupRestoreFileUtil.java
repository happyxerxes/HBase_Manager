/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.comm.manager.HbaseTableObject;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.commons.lang.exception.ExceptionUtils;
/*     */ 
/*     */ public class BackupRestoreFileUtil
/*     */ {
/*     */   private File selectedFile;
/*  24 */   private int filecount = 0;
/*     */   private String filePath;
/*     */   public static boolean runningNow;
/*     */ 
/*     */   public BackupRestoreFileUtil(File backupFile)
/*     */   {
/*  31 */     this.selectedFile = backupFile;
/*  32 */     this.filePath = this.selectedFile.getAbsolutePath();
/*     */   }
/*     */ 
/*     */   private File getNextFile(boolean verifyExistence)
/*     */   {
/*  38 */     if (this.filecount > 0)
/*     */     {
/*  40 */       this.selectedFile = new File(this.filePath.concat("_").concat(String.valueOf(this.filecount)));
/*  41 */       this.filecount += 1;
/*     */     }
/*     */     else
/*     */     {
/*  46 */       this.filecount += 1;
/*     */     }
/*  48 */     if (verifyExistence) {
/*  49 */       if (this.selectedFile.isFile()) {
/*  50 */         return this.selectedFile;
/*     */       }
/*  52 */       return null;
/*     */     }
/*     */ 
/*  55 */     return this.selectedFile;
/*     */   }
/*     */ 
/*     */   public void backupToFiles(List<HbaseTableObject> hTables)
/*     */     throws IOException
/*     */   {
/*  61 */     runningNow = true;
/*  62 */     Iterator iterator = hTables.iterator();
/*  63 */     boolean markedHeader = false;
/*  64 */     while (iterator.hasNext()) {
/*     */       try
/*     */       {
/*  67 */         HbaseTableObject next = (HbaseTableObject)iterator.next();
/*  68 */         if (!markedHeader) {
/*  69 */           next.setTotalLinkedFiles(hTables.size());
/*     */         }
/*  71 */         File nextFile = getNextFile(false);
/*  72 */         ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(nextFile));
/*  73 */         System.out.println("Writing: " + this.selectedFile.getName());
/*     */ 
/*  75 */         if (!iterator.hasNext()) {
/*  76 */           System.out.println("Marking link end at " + this.selectedFile);
/*  77 */           next.setLinkedFileAvailable(false);
/*     */         }
/*  79 */         output.writeObject(next);
/*  80 */         output.close();
/*     */       }
/*     */       catch (Throwable e) {
/*  83 */         System.out.println("Exception while backup:  files may not be written properly. " + ExceptionUtils.getFullStackTrace(e));
/*     */       }
/*     */     }
/*     */ 
/*  87 */     runningNow = false;
/*     */   }
/*     */ 
/*     */   public List<HbaseTableObject> restoreFromFiles(JDialog dialog)
/*     */   {
/*  93 */     List tableObjects = new ArrayList();
/*  94 */     runningNow = true;
/*     */     try
/*     */     {
/*  99 */       File nextFile = getNextFile(true);
/* 100 */       ObjectInputStream instream = new ObjectInputStream(new FileInputStream(nextFile));
/*     */ 
/* 102 */       HbaseTableObject object = (HbaseTableObject)instream.readObject();
/* 103 */       int totalFiles = object.getTotalLinkedFiles();
/* 104 */       int i = 1;
/* 105 */       System.out.println("Total: Files" + totalFiles);
/* 106 */       System.out.println("Scanning backup files..." + i + " of " + totalFiles);
/* 107 */       System.out.println("Reading " + this.selectedFile.getName());
/*     */ 
/* 109 */       tableObjects.add(object);
/* 110 */       instream.close();
/*     */ 
/* 112 */       while (object.isLinkedFileAvailable()) {
/* 113 */         i++;
/* 114 */         System.out.println("Scanning backup files..." + i + " of " + totalFiles);
/* 115 */         nextFile = getNextFile(true);
/* 116 */         if (nextFile == null) {
/* 117 */           JOptionPane.showMessageDialog(dialog, "All the Backup files not available. \n This restore will work only partial", "Error", 0);
/* 118 */           break;
/*     */         }
/*     */ 
/* 121 */         instream = new ObjectInputStream(new FileInputStream(nextFile));
/* 122 */         System.out.println("Reading " + this.selectedFile.getName());
/* 123 */         object = (HbaseTableObject)instream.readObject();
/* 124 */         instream.close();
/* 125 */         System.out.println("Continue reading: " + object.isLinkedFileAvailable());
/* 126 */         tableObjects.add(object);
/*     */       }
/*     */ 
/* 130 */       runningNow = false;
/* 131 */       return tableObjects;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 135 */       System.out.println("Exception while restoring. Restoring failed" + ExceptionUtils.getFullStackTrace(e));
/*     */ 
/* 137 */       runningNow = false;
/*     */     }
/*     */ 
/* 140 */     return null;
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.BackupRestoreFileUtil
 * JD-Core Version:    0.6.2
 */