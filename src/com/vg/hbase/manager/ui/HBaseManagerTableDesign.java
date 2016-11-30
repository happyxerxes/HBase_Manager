/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.operations.base.HBaseConfigurationManager;

/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextField;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;

/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.hadoop.hbase.HColumnDescriptor;
/*     */ import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
/*     */ import org.apache.hadoop.hbase.client.HBaseAdmin;
/*     */ import org.apache.hadoop.hbase.util.Bytes;
/*     */ 
/*     */ public class HBaseManagerTableDesign extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 366535590568661722L;
/*     */   private static DefaultListModel listModel;
/*  42 */   HashMap<String, String> versions = new HashMap();
/*     */   private JButton clickAddColFamily;
/*     */   private JButton clickCancel;
/*     */   private JButton clickCreateTable;
/*     */   private JButton clickRemoveColFamily;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JPanel jPanel2;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JList listColFamilynames;
/*     */   private JTextField valueNewColFamilyName;
/*     */   private JTextField valueNewTableName;
/*     */   private JTextField valueVersions;
/*     */ 
/*     */   public HBaseManagerTableDesign(Frame parent, boolean modal)
/*     */   {
/*  46 */     super(parent, modal);
/*  47 */     initComponents();
/*  48 */     listModel = (DefaultListModel)this.listColFamilynames.getModel();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  60 */     this.jPanel2 = new JPanel();
/*  61 */     this.jLabel1 = new JLabel();
/*  62 */     this.valueNewColFamilyName = new JTextField();
/*  63 */     this.jLabel2 = new JLabel();
/*  64 */     this.valueNewTableName = new JTextField();
/*  65 */     this.jScrollPane1 = new JScrollPane();
/*  66 */     this.listColFamilynames = new JList(new DefaultListModel());
/*  67 */     this.clickAddColFamily = new JButton();
/*  68 */     this.jLabel3 = new JLabel();
/*  69 */     this.clickCreateTable = new JButton();
/*  70 */     this.clickCancel = new JButton();
/*  71 */     this.clickRemoveColFamily = new JButton();
/*  72 */     this.valueVersions = new JTextField();
/*  73 */     this.jLabel4 = new JLabel();
/*     */ 
/*  75 */     setDefaultCloseOperation(2);
/*  76 */     setTitle("HBase Manager: New Table");
/*  77 */     setAlwaysOnTop(true);
/*  78 */     Image image = Toolkit.getDefaultToolkit().getImage("hb.png");
/*  79 */     setIconImage(image);
/*     */ 
/*  81 */     this.jPanel2.setBorder(BorderFactory.createBevelBorder(0));
/*     */ 
/*  83 */     this.jLabel1.setText("New Table Name : ");
/*     */ 
/*  85 */     this.jLabel2.setText("Coloumn Families :");
/*     */ 
/*  87 */     this.jScrollPane1.setViewportView(this.listColFamilynames);
/*     */ 
/*  89 */     this.clickAddColFamily.setText("Add Coloumn Family");
/*  90 */     this.clickAddColFamily.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  93 */         HBaseManagerTableDesign.this.clickAddColFamilyActionPerformed(evt);
/*     */       }
/*     */     });
/*  97 */     this.jLabel3.setText("New Coloumn Family:");
/*     */ 
/*  99 */     this.clickCreateTable.setText("Create Table");
/* 100 */     this.clickCreateTable.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 103 */         HBaseManagerTableDesign.this.clickCreateTableActionPerformed(evt);
/*     */       }
/*     */     });
/* 107 */     this.clickCancel.setText("Cancel");
/* 108 */     this.clickCancel.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 111 */         HBaseManagerTableDesign.this.clickCancelActionPerformed(evt);
/*     */       }
/*     */     });
/* 115 */     this.clickRemoveColFamily.setText("Remove CF");
/* 116 */     this.clickRemoveColFamily.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 119 */         HBaseManagerTableDesign.this.clickRemoveColFamilyActionPerformed(evt);
/*     */       }
/*     */     });
/* 123 */     this.jLabel4.setText("Max versions");
/*     */ 
/* 125 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 126 */     this.jPanel2.setLayout(jPanel2Layout);
/* 127 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(28, 28, 28).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel4)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.valueNewTableName, -2, 207, -2).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.valueNewColFamilyName, -2, 115, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickAddColFamily)).addComponent(this.valueVersions, -2, 53, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jScrollPane1, -2, 113, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickRemoveColFamily, -2, 136, -2))).addContainerGap(30, 32767)).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.clickCreateTable).addGap(28, 28, 28).addComponent(this.clickCancel).addGap(104, 104, 104)));
/* 128 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(25, 25, 25).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.valueNewTableName, -2, -1, -2)).addGap(29, 29, 29).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.valueNewColFamilyName, -2, -1, -2).addComponent(this.clickAddColFamily).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.valueVersions, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, 32767).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jScrollPane1, -2, -1, -2)).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.clickRemoveColFamily))).addGap(39, 39, 39).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.clickCreateTable).addComponent(this.clickCancel)).addGap(29, 29, 29)));
/*     */ 
/* 130 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 131 */     getContentPane().setLayout(layout);
/* 132 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel2, -2, -1, -2).addContainerGap(-1, 32767)));
/* 133 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(this.jPanel2, -2, -1, -2).addContainerGap()));
/*     */ 
/* 135 */     pack();
/*     */   }
/*     */ 
/*     */   private void clickAddColFamilyActionPerformed(ActionEvent evt)
/*     */   {
/* 140 */     String splchars = "`~!@#$%^&*()_+=,./<>?;':\"\\ \n";
/* 141 */     char[] splCharArray = splchars.toCharArray();
/* 142 */     String colFamilyName = this.valueNewColFamilyName.getText();
/* 143 */     int maxVersion = Integer.parseInt(this.valueVersions.getText());
/* 144 */     if (maxVersion > 0) {
/* 145 */       this.versions.put(colFamilyName, String.valueOf(maxVersion));
/*     */     }
/*     */     else
/*     */     {
/* 149 */       this.versions.put(colFamilyName, "1");
/*     */     }
/*     */ 
/* 152 */     if ((StringUtils.isEmpty(colFamilyName)) || (StringUtils.containsAny(colFamilyName, splCharArray))) {
/* 153 */       JOptionPane.showMessageDialog(this, "Provide a valid Coloumn Family Name");
/*     */     }
/*     */     else
/* 156 */       listModel.addElement(colFamilyName);
/*     */   }
/*     */ 
/*     */   private void clickCreateTableActionPerformed(ActionEvent evt)
/*     */   {
/* 162 */    // HBaseAdmin admin = HBaseConfigurationManager.getHbaseAdmin();
			  Admin admin = HBaseConfigurationManager.getHbaseAdmin();
/* 163 */   //  HTableDescriptor tableDesc = new HTableDescriptor();
			  HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(valueNewTableName.getText()));
/*     */ 
/* 165 */   //  tableDesc.setName(Bytes.toBytes(this.valueNewTableName.getText()));
/*     */ 
/* 169 */     for (int i = 0; i < listModel.getSize(); i++) {
/* 170 */       String colFamName = (String)listModel.getElementAt(i);
/* 171 */       int version = Integer.parseInt((String)this.versions.get(colFamName));
/* 172 */       HColumnDescriptor familyDesc = new HColumnDescriptor(colFamName);
/* 173 */       familyDesc.setMaxVersions(version);
/*     */ 
/* 175 */       System.out.println("Adding Family: " + colFamName);
/* 176 */       tableDesc.addFamily(familyDesc);
/*     */     }
/*     */     try
/*     */     {
/* 180 */       admin.createTable(tableDesc);
/* 181 */       JOptionPane.showMessageDialog(this, "Table Created and Enabled", "Information", 1);
/*     */     }
/*     */     catch (IOException ex) {
/* 184 */       JOptionPane.showMessageDialog(this, "Table Creation Failed", "Error", 0);
/* 185 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clickCancelActionPerformed(ActionEvent evt)
/*     */   {
/* 192 */     dispose();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 199 */     super.dispose();
/*     */   }
/*     */ 
/*     */   private void clickRemoveColFamilyActionPerformed(ActionEvent evt)
/*     */   {
/* 204 */     this.versions.remove((String)this.listColFamilynames.getSelectedValue());
/* 205 */     listModel.remove(this.listColFamilynames.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 226 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 227 */         if ("Nimbus".equals(info.getName())) {
/* 228 */           UIManager.setLookAndFeel(info.getClassName());
/* 229 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 234 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 237 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 240 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 243 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 250 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 254 */         HBaseManagerTableDesign dialog = new HBaseManagerTableDesign(new JFrame(), true);
/* 255 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 260 */             System.exit(0);
/*     */           }
/*     */         });
/* 263 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HBaseManagerTableDesign
 * JD-Core Version:    0.6.2
 */