/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.comm.manager.HBaseTableManager;
/*     */ import com.vg.hbase.operations.base.HBaseConfigurationManager;
/*     */ import com.vg.hbase.operations.base.HbaseManagerStatic;

/*     */ import java.awt.Component;
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
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
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
/*     */ public class HBaseManagerAdminPanel extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -1797775811490932987L;
/*     */   private static DefaultListModel listModel;
/*     */   private static HTableDescriptor tableDesc;
/*     */   Component[] cmplist;
/*     */  // private HBaseAdmin hbaseAdmin;
			private Admin hbaseAdmin;
/*     */   private JButton clickAddColFamily;
/*     */   private JButton clickAlter;
/*     */   private JButton clickAlterTable;
/*     */   private JButton clickDrop;
/*     */   private JButton clickRemoveColFamily;
/*     */   private JButton clickTruncate;
/*     */   private JComboBox comboTableList;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JLabel jLabel5;
/*     */   private JLabel jLabel6;
/*     */   private JPanel jPanel1;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JLabel labelTableName;
/*     */   private JList listColFamilynames;
/*     */   private JPanel panelAlterTableControl;
/*     */   private JTextField valueNewColFamilyName;
/*     */ 
/*     */   public HBaseManagerAdminPanel(Frame parent, boolean modal)
/*     */   {
/*  52 */     super(parent, modal);
/*  53 */     initComponents();
/*  54 */     listModel = (DefaultListModel)this.listColFamilynames.getModel();
/*  55 */     this.cmplist = this.panelAlterTableControl.getComponents();
/*     */ 
/*  57 */     disablePane(false);
/*     */ 
/*  59 */     HBaseTableManager.resetConnection();
/*  60 */     this.hbaseAdmin = HBaseConfigurationManager.getHbaseAdmin();
/*  61 */     addTableList();
/*     */   }
/*     */ 
/*     */   private void addTableList()
/*     */   {
/*  68 */     String[] tblList = HBaseTableManager.getAllTableNames();
/*  69 */     for (String tblListItem : tblList)
/*  70 */       this.comboTableList.addItem(tblListItem);
/*     */   }
/*     */ 
/*     */   private void AddColoumnList(String tblName)
/*     */   {
/*  77 */     setTitle(tblName);
/*     */ 
/*  79 */     String[] familyList = HBaseTableManager.getColFamilies(tblName);
/*  80 */     String[] familyVersions = HBaseTableManager.getColFamilyVersions(tblName);
/*     */ 
/*  82 */     int i = 0;
/*     */ 
/*  84 */     for (String tblListItem : familyList)
/*  85 */       listModel.addElement(tblListItem + ": " + familyVersions[(i++)]);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean arg0)
/*     */   {
/* 103 */     super.setVisible(arg0);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 108 */     this.jPanel1 = new JPanel();
/* 109 */     this.jLabel1 = new JLabel();
/* 110 */     this.jLabel2 = new JLabel();
/* 111 */     this.comboTableList = new JComboBox();
/* 112 */     this.jLabel3 = new JLabel();
/* 113 */     this.clickAlter = new JButton();
/* 114 */     this.clickDrop = new JButton();
/* 115 */     this.clickTruncate = new JButton();
/* 116 */     this.panelAlterTableControl = new JPanel();
/* 117 */     this.jLabel4 = new JLabel();
/* 118 */     this.labelTableName = new JLabel();
/* 119 */     this.jLabel5 = new JLabel();
/* 120 */     this.clickAddColFamily = new JButton();
/* 121 */     this.clickRemoveColFamily = new JButton();
/* 122 */     this.valueNewColFamilyName = new JTextField();
/* 123 */     this.jLabel6 = new JLabel();
/* 124 */     this.jScrollPane1 = new JScrollPane();
/* 125 */     this.listColFamilynames = new JList(new DefaultListModel());
/* 126 */     this.clickAlterTable = new JButton();
/* 127 */     Image image = Toolkit.getDefaultToolkit().getImage("hb.png");
/* 128 */     setIconImage(image);
/* 129 */     setDefaultCloseOperation(2);
/* 130 */     setTitle("HBase Manager - Administrator Panel : Hbase: @" + HbaseManagerStatic.getCurrentConnectionAlias());
/*     */ 
/* 132 */     this.jLabel1.setText("Admin Actions");
/*     */ 
/* 134 */     this.jLabel2.setText("Tables");
/*     */ 
/* 136 */     this.comboTableList.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 139 */         HBaseManagerAdminPanel.this.comboTableListActionPerformed(evt);
/*     */       }
/*     */     });
/* 143 */     this.jLabel3.setText("Actions:");
/*     */ 
/* 145 */     this.clickAlter.setText("Alter");
/* 146 */     this.clickAlter.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 149 */         HBaseManagerAdminPanel.this.clickAlterActionPerformed(evt);
/*     */       }
/*     */     });
/* 153 */     this.clickDrop.setText("Drop");
/* 154 */     this.clickDrop.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 157 */         HBaseManagerAdminPanel.this.clickDropActionPerformed(evt);
/*     */       }
/*     */     });
/* 161 */     this.clickTruncate.setText("Truncate");
/* 162 */     this.clickTruncate.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 165 */         HBaseManagerAdminPanel.this.clickTruncateActionPerformed(evt);
/*     */       }
/*     */     });
/* 169 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 170 */     this.jPanel1.setLayout(jPanel1Layout);
/* 171 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1)).addGroup(jPanel1Layout.createSequentialGroup().addGap(33, 33, 33).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.comboTableList, -2, 138, -2).addGap(38, 38, 38).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickAlter).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickDrop).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickTruncate))).addContainerGap(21, 32767)));
/* 172 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(22, 22, 22).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.comboTableList, -2, -1, -2).addComponent(this.jLabel3).addComponent(this.clickAlter).addComponent(this.clickDrop).addComponent(this.clickTruncate)).addContainerGap(39, 32767)));
/*     */ 
/* 174 */     this.panelAlterTableControl.setBorder(BorderFactory.createBevelBorder(0));
/*     */ 
/* 176 */     this.jLabel4.setText("Table :");
/*     */ 
/* 178 */     this.labelTableName.setText("<TableName>");
/*     */ 
/* 180 */     this.jLabel5.setText("New Coloumn Family:");
/*     */ 
/* 182 */     this.clickAddColFamily.setText("Add Coloumn Family");
/* 183 */     this.clickAddColFamily.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 186 */         HBaseManagerAdminPanel.this.clickAddColFamilyActionPerformed(evt);
/*     */       }
/*     */     });
/* 190 */     this.clickRemoveColFamily.setText("Remove Coloumn Family");
/* 191 */     this.clickRemoveColFamily.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 194 */         HBaseManagerAdminPanel.this.clickRemoveColFamilyActionPerformed(evt);
/*     */       }
/*     */     });
/* 198 */     this.jLabel6.setText("Coloumn Families :");
/*     */ 
/* 200 */     this.jScrollPane1.setViewportView(this.listColFamilynames);
/*     */ 
/* 202 */     this.clickAlterTable.setText("Alter Table");
/* 203 */     this.clickAlterTable.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 206 */         HBaseManagerAdminPanel.this.clickAlterTableActionPerformed(evt);
/*     */       }
/*     */     });
/* 210 */     GroupLayout panelAlterTableControlLayout = new GroupLayout(this.panelAlterTableControl);
/* 211 */     this.panelAlterTableControl.setLayout(panelAlterTableControlLayout);
/* 212 */     panelAlterTableControlLayout.setHorizontalGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelAlterTableControlLayout.createSequentialGroup().addContainerGap().addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelAlterTableControlLayout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.labelTableName)).addGroup(panelAlterTableControlLayout.createSequentialGroup().addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addComponent(this.jLabel6)).addGap(23, 23, 23).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.valueNewColFamilyName, -2, 115, -2).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.clickAlterTable).addComponent(this.jScrollPane1, -2, 113, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.clickAddColFamily).addComponent(this.clickRemoveColFamily)))).addContainerGap(-1, 32767)));
/* 213 */     panelAlterTableControlLayout.setVerticalGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelAlterTableControlLayout.createSequentialGroup().addGap(24, 24, 24).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.labelTableName)).addGap(18, 18, 18).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.valueNewColFamilyName, -2, -1, -2).addComponent(this.clickAddColFamily).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(panelAlterTableControlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(panelAlterTableControlLayout.createSequentialGroup().addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.clickRemoveColFamily)).addComponent(this.jScrollPane1, -2, -1, -2)).addGap(37, 37, 37).addComponent(this.clickAlterTable).addContainerGap(40, 32767)));
/*     */ 
/* 215 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 216 */     getContentPane().setLayout(layout);
/* 217 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.panelAlterTableControl, -1, -1, 32767).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addGap(0, 15, 32767))).addContainerGap()));
/* 218 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addGap(18, 18, 18).addComponent(this.panelAlterTableControl, -2, -1, -2).addContainerGap(38, 32767)));
/*     */ 
/* 220 */     pack();
/*     */   }
/*     */ 
/*     */   private void clickAddColFamilyActionPerformed(ActionEvent evt)
/*     */   {
/* 225 */     String splchars = "`~!@#$%^&*()_+=,./<>?;':\"\\ \n";
/* 226 */     char[] splCharArray = splchars.toCharArray();
/*     */ 
/* 228 */     String[] colFamily = this.valueNewColFamilyName.getText().split(":");
/* 229 */     String colFamilyName = colFamily[0];
/* 230 */     if ((StringUtils.isEmpty(colFamilyName)) || (StringUtils.containsAny(colFamilyName, splCharArray))) {
/* 231 */       JOptionPane.showMessageDialog(this, "Provide a valid Coloumn Family Name");
/*     */     }
/*     */     else {
/* 234 */       listModel.addElement(colFamilyName);
/*     */ 
/* 236 */       HColumnDescriptor colDesc = new HColumnDescriptor(colFamilyName);
/*     */       try {
/* 238 */         if (colFamily.length > 1)
/* 239 */           colDesc.setMaxVersions(Integer.parseInt(colFamily[1]));
/*     */       }
/*     */       catch (Exception e) {
/* 242 */         colDesc.setMaxVersions(1);
/*     */       }
/* 244 */       tableDesc.addFamily(colDesc);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clickRemoveColFamilyActionPerformed(ActionEvent evt)
/*     */   {
/* 251 */     String familyname = (String)this.listColFamilynames.getSelectedValue();
/* 252 */     String family = familyname.split(":")[0];
/* 253 */     System.out.println("Removing " + family);
/* 254 */     listModel.removeElement(familyname);
/* 255 */     tableDesc.removeFamily(Bytes.toBytes(family));
/*     */   }
/*     */ 
/*     */   private void clickAlterActionPerformed(ActionEvent evt)
/*     */   {
/* 260 */     this.labelTableName.setText((String)this.comboTableList.getSelectedItem());
/*     */ 
/* 262 */     listModel.removeAllElements();
/* 263 */     byte[] tableName = Bytes.toBytes((String)this.comboTableList.getSelectedItem());
/*     */ 
/* 265 */     AddColoumnList((String)this.comboTableList.getSelectedItem());
/*     */     try
/*     */     {
/* 268 */      // tableDesc = this.hbaseAdmin.getTableDescriptor(tableName);
				tableDesc = this.hbaseAdmin.getTableDescriptor(TableName.valueOf((String)this.comboTableList.getSelectedItem()));
/* 269 */       JOptionPane.showMessageDialog(this, "Altered Successfully");
/*     */     }
/*     */     catch (IOException ex) {
/* 272 */       JOptionPane.showMessageDialog(this, "Altering table Failed", "Error", 0);
/* 273 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/* 275 */     addTableList();
/* 276 */     disablePane(true);
/*     */   }
/*     */ 
/*     */   private void clickDropActionPerformed(ActionEvent evt)
/*     */   {
/* 282 */     String tableName = null;
/*     */     try {
/* 284 */       int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to Drop '" + this.comboTableList.getSelectedItem() + "'", "Warning!", 0);
/* 285 */       if (opt == 0) {
/* 286 */         tableName = (String)this.comboTableList.getSelectedItem();
/* 287 */         listModel.removeElement(tableName);
/* 288 */         this.hbaseAdmin.disableTable(TableName.valueOf(tableName));
/* 289 */         this.hbaseAdmin.deleteTable(TableName.valueOf(tableName));
/* 290 */         JOptionPane.showMessageDialog(this, "Table: " + tableName + " dropped");
/* 291 */         addTableList();
/*     */       }
/*     */     }
/*     */     catch (IOException ex) {
/* 295 */       JOptionPane.showMessageDialog(this, "Table: " + tableName + " drop failed", "Error", 0);
/* 296 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 303 */     HBaseManagerViewTable.addTableList();
/* 304 */     super.dispose();
/*     */   }
/*     */ 
/*     */   private void clickTruncateActionPerformed(ActionEvent evt)
/*     */   {
/*     */     try
/*     */     {
/* 311 */       int opt = JOptionPane.showConfirmDialog(this, "This Action will delete all Data of '" + this.comboTableList.getSelectedItem() + "'. Are you Sure?", "Warning!", 0);
/* 312 */       if (opt == 0) {
/* 313 */         byte[] tableName = Bytes.toBytes((String)this.comboTableList.getSelectedItem());
/* 314 */         HTableDescriptor currentDescriptor = this.hbaseAdmin.getTableDescriptor(TableName.valueOf(tableName));
/*     */ 
/* 316 */         this.hbaseAdmin.disableTable(TableName.valueOf(tableName));
/* 317 */         this.hbaseAdmin.deleteTable(TableName.valueOf(tableName));
/* 318 */         this.hbaseAdmin.createTable(currentDescriptor);
/*     */ 
/* 320 */         JOptionPane.showMessageDialog(this, "Table Truncated Successfully");
/*     */       }
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/* 325 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clickAlterTableActionPerformed(ActionEvent evt)
/*     */   {
/* 332 */     byte[] table = Bytes.toBytes((String)this.comboTableList.getSelectedItem());
/*     */     try {
/* 334 */       this.hbaseAdmin.disableTable(TableName.valueOf(table));
/* 335 */       this.hbaseAdmin.modifyTable(TableName.valueOf(table), tableDesc);
/* 336 */       this.hbaseAdmin.enableTable(TableName.valueOf(table));
/* 337 */       JOptionPane.showMessageDialog(this, "Table Modified Successfully");
/* 338 */       listModel.removeAllElements();
/* 339 */       disablePane(false);
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/* 343 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void comboTableListActionPerformed(ActionEvent evt)
/*     */   {
/* 350 */     disablePane(false);
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 371 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 372 */         if ("Nimbus".equals(info.getName())) {
/* 373 */           UIManager.setLookAndFeel(info.getClassName());
/* 374 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 379 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 382 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 385 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 388 */       Logger.getLogger(HBaseManagerAdminPanel.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 395 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 399 */         HBaseManagerAdminPanel dialog = new HBaseManagerAdminPanel(new JFrame(), true);
/* 400 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 405 */             System.exit(0);
/*     */           }
/*     */         });
/* 408 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void disablePane(boolean b)
/*     */   {
/* 438 */     for (int i = 0; i < this.cmplist.length; i++)
/* 439 */       this.cmplist[i].setEnabled(b);
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HBaseManagerAdminPanel
 * JD-Core Version:    0.6.2
 */