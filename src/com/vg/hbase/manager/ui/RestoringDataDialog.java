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
/*     */ public class RestoringDataDialog extends JDialog
/*     */ {
/*     */   private JLabel jLabel1;
/*     */   private JPanel jPanel1;
/*     */ 
/*     */   public RestoringDataDialog(Frame parent, boolean modal)
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
/*  46 */     this.jLabel1.setText("Restoring Data to HBase.. Be Patient");
/*     */ 
/*  48 */     final RestoringDataDialog dialog = this;
/*  49 */     dialog.setDefaultCloseOperation(0);
/*  50 */     addWindowListener(new WindowAdapter()
/*     */     {
/*     */       public void windowClosing(WindowEvent e)
/*     */       {
/*  55 */         JOptionPane.showMessageDialog(dialog, "Busy! Please Wait", "Oops!", 0);
/*     */       }
/*     */     });
/*  58 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/*  59 */     this.jPanel1.setLayout(jPanel1Layout);
/*  60 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 491, -2).addContainerGap(-1, 32767)));
/*  61 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(35, 35, 35).addComponent(this.jLabel1).addContainerGap(51, 32767)));
/*     */ 
/*  63 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  64 */     getContentPane().setLayout(layout);
/*  65 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
/*  66 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
/*     */ 
/*  68 */     pack();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/*  90 */         if ("Nimbus".equals(info.getName())) {
/*  91 */           UIManager.setLookAndFeel(info.getClassName());
/*  92 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/*  97 */       Logger.getLogger(RestoringDataDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 100 */       Logger.getLogger(RestoringDataDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 103 */       Logger.getLogger(RestoringDataDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 106 */       Logger.getLogger(RestoringDataDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 113 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 117 */         RestoringDataDialog dialog = new RestoringDataDialog(new JFrame(), true);
/*     */ 
/* 119 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.RestoringDataDialog
 * JD-Core Version:    0.6.2
 */