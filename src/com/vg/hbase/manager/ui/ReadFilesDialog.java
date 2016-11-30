/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ public class ReadFilesDialog extends JDialog
/*     */ {
/*     */   BackupRestoreFileUtil util;
/*     */   final ReadFilesDialog dialog;
/*     */   private JLabel jLabel1;
/*     */   private JPanel jPanel1;
/*     */   private JButton buttonStart;
/*     */ 
/*     */   public ReadFilesDialog(Frame parent, boolean modal, BackupRestoreFileUtil util)
/*     */   {
/*  32 */     super(parent, modal);
/*  33 */     initComponents();
/*  34 */     this.util = util;
/*  35 */     this.dialog = this;
/*     */ 
/*  37 */     setVisible(true);
/*     */   }
/*     */ 
/*     */   public void scanBackupFiles(BackupRestoreFileUtil util)
/*     */   {
/*  43 */     List hbObjects = util.restoreFromFiles(this);
/*  44 */     HbaseDataBackupRestoreDialog.setRestoredTableData(hbObjects, this.dialog);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  58 */     this.jPanel1 = new JPanel();
/*  59 */     this.jLabel1 = new JLabel();
/*  60 */     this.buttonStart = new JButton();
/*  61 */     this.buttonStart.setText("Start");
/*  62 */     this.buttonStart.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  65 */         ReadFilesDialog.this.buttonStartActionPerformed(evt);
/*  66 */         ReadFilesDialog.this.scanBackupFiles(ReadFilesDialog.this.util);
/*  67 */         ReadFilesDialog.this.dialog.setVisible(false);
/*     */       }
/*     */     });
/*  75 */     setTitle("Backup Notification");
/*     */ 
/*  77 */     this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  78 */     this.jPanel1.setToolTipText("Backup Progress");
/*     */ 
/*  80 */     this.jLabel1.setFont(new Font("SimHei", 0, 24));
/*  81 */     this.jLabel1.setText("Scan Backup Files");
/*     */ 
/*  83 */     this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  84 */     this.jPanel1.setToolTipText("Backup Progress");
/*     */ 
/*  86 */     this.jLabel1.setFont(new Font("SimHei", 0, 24));
/*  87 */     this.jLabel1.setText("Scanning Backup Files...");
/*     */ 
/*  89 */     this.buttonStart.setText("Start");
/*     */ 
/*  91 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  92 */     this.jPanel1.setLayout(jPanel1Layout);
/*  93 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 491, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buttonStart, -2, 113, -2).addContainerGap(73, 32767)));
/*  94 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(35, 35, 35).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.buttonStart)).addContainerGap(51, 32767)));
/*     */ 
/*  96 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  97 */     getContentPane().setLayout(layout);
/*  98 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(26, 32767)));
/*  99 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
/*     */ 
/* 101 */     pack();
/*     */   }
/*     */ 
/*     */   private void buttonStartActionPerformed(ActionEvent evt)
/*     */   {
/* 107 */     this.jLabel1.setText("Scanning Backup Files... Please Wait");
/* 108 */     this.buttonStart.setVisible(false);
/*     */ 
/* 110 */     this.dialog.setDefaultCloseOperation(0);
/* 111 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e)
/*     */       {
/* 116 */         JOptionPane.showMessageDialog(ReadFilesDialog.this.dialog, "Busy! Please Wait", "Oops!", 0);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 140 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 141 */         if ("Nimbus".equals(info.getName())) {
/* 142 */           UIManager.setLookAndFeel(info.getClassName());
/* 143 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 148 */       Logger.getLogger(ReadFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 151 */       Logger.getLogger(ReadFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 154 */       Logger.getLogger(ReadFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 157 */       Logger.getLogger(ReadFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 164 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 168 */         ReadFilesDialog dialog = new ReadFilesDialog(new JFrame(), true, new BackupRestoreFileUtil(new File("F:/javax/create.file")));
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.ReadFilesDialog
 * JD-Core Version:    0.6.2
 */