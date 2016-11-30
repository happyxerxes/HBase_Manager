/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ public class WritingFilesDialog extends JDialog
/*     */ {
/*     */   private JLabel jLabel1;
/*     */   private JPanel jPanel1;
/*     */ 
/*     */   public WritingFilesDialog(Frame parent, boolean modal)
/*     */   {
/*  22 */     super(parent, modal);
/*  23 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  36 */     this.jPanel1 = new JPanel();
/*  37 */     this.jLabel1 = new JLabel();
/*     */ 
/*  39 */     setDefaultCloseOperation(2);
/*  40 */     setTitle("Backup Notification");
/*     */ 
/*  42 */     this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  43 */     this.jPanel1.setToolTipText("Backup Progress");
/*     */ 
/*  45 */     this.jLabel1.setFont(new Font("SimHei", 0, 24));
/*  46 */     this.jLabel1.setText("Table Backup in Progress... Please Wait");
/*     */ 
/*  48 */     final WritingFilesDialog dialog = this;
/*  49 */     dialog.setDefaultCloseOperation(0);
/*  50 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e)
/*     */       {
/*  55 */         JOptionPane.showMessageDialog(dialog, "Busy! Please Wait", "Oops!", 0);
/*     */       }
/*     */     });
/*  59 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  60 */     this.jPanel1.setLayout(jPanel1Layout);
/*  61 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 491, -2).addContainerGap(-1, 32767)));
/*  62 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(35, 35, 35).addComponent(this.jLabel1).addContainerGap(51, 32767)));
/*     */ 
/*  64 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  65 */     getContentPane().setLayout(layout);
/*  66 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
/*  67 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
/*     */ 
/*  69 */     pack();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/*  90 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/*  91 */         if ("Nimbus".equals(info.getName())) {
/*  92 */           UIManager.setLookAndFeel(info.getClassName());
/*  93 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/*  98 */       Logger.getLogger(WritingFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 101 */       Logger.getLogger(WritingFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 104 */       Logger.getLogger(WritingFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 107 */       Logger.getLogger(WritingFilesDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 114 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 118 */         WritingFilesDialog dialog = new WritingFilesDialog(new JFrame(), true);
/*     */ 
/* 120 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.WritingFilesDialog
 * JD-Core Version:    0.6.2
 */