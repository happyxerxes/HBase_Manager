/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.comm.manager.HBaseRow;
/*     */ import com.vg.hbase.comm.manager.HBaseTableData;
/*     */ import com.vg.hbase.comm.manager.HBaseTableManager;
/*     */ import com.vg.hbase.comm.manager.HBaseTableStructure;
/*     */ import com.vg.hbase.comm.manager.HbaseTableObject;
/*     */ import com.vg.hbase.operations.base.HBaseConfigurationManager;

/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;

/*     */ import org.apache.commons.lang.exception.ExceptionUtils;
/*     */ import org.apache.hadoop.hbase.HTableDescriptor;
/*     */ import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.client.Admin;
/*     */ import org.apache.hadoop.hbase.client.HBaseAdmin;
/*     */ import org.apache.hadoop.hbase.client.HTable;
/*     */ import org.apache.hadoop.hbase.client.Result;
/*     */ import org.apache.hadoop.hbase.client.ResultScanner;
/*     */ import org.apache.hadoop.hbase.util.Bytes;
/*     */ 
/*     */ public class HbaseDataBackupRestoreDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 5959525532772616338L;
/*     */   DefaultListModel listBackupSelectedModel;
/*     */   DefaultListModel listBackupAllTablesModel;
/*     */   DefaultListModel listRestoreSelectedModel;
/*     */   DefaultListModel listRestoreupAllTablesModel;
/*  52 */   HbaseTableObject hbTable = new HbaseTableObject();
/*  53 */   HBaseTableData tableData = new HBaseTableData();
/*  54 */   HBaseTableStructure tableStructure = new HBaseTableStructure();
/*  55 */   HashMap<String, List<HbaseTableObject>> tableObjects = new HashMap();
/*     */   private static List<HbaseTableObject> restoredTableData;
/*  57 */   static ReadFilesDialog readFilesDialog = null;
/*     */ 
/*  59 */   private HBaseTableManager hbaseTableManager = HBaseTableManager.getInstance();
/*     */   private JButton buttonAddAlltoSelectedBox;
/*     */   private JButton buttonExit;
/*     */   private JButton buttonRemoveAllFromSelectedBox;
/*     */   private JButton buttonBackupToFile;
/*     */   private JButton buttonMigrate;
/*     */   private JButton buttonCancel;
/*     */   private JButton buttonBrowseFile;
/*     */   private JButton buttonAddAlltoSelectedBox2;
/*     */   private JButton buttonRemoveAllFromSelectedBox2;
/*     */   private JButton buttonRestore;
/*     */   private JLabel labelFileSelect;
/*     */   private JLabel labelFileOperationStatus;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JScrollPane jScrollPane2;
/*     */   private JScrollPane jScrollPane3;
/*     */   private JScrollPane jScrollPane4;
/*     */   private JList listAllTables;
/*     */   private JList listRestoreAllTables;
/*     */   private JList listRestoreSelectedTables;
/*     */   private JList listSelectedTables;
/*     */   private static Long totalRows;
/*     */ 
/*     */   public HbaseDataBackupRestoreDialog(Frame parent, boolean modal)
/*     */   {
/*  63 */     super(parent, modal);
/*  64 */     initComponents();
/*  65 */     setTitle("Backup/ Restore your Data");
/*  66 */     this.listBackupAllTablesModel = ((DefaultListModel)this.listAllTables.getModel());
/*  67 */     this.listBackupSelectedModel = ((DefaultListModel)this.listSelectedTables.getModel());
/*  68 */     this.listRestoreupAllTablesModel = ((DefaultListModel)this.listRestoreAllTables.getModel());
/*  69 */     this.listRestoreSelectedModel = ((DefaultListModel)this.listRestoreSelectedTables.getModel());
/*  70 */     populateAvailableTables();
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean arg0)
/*     */   {
/*  82 */     this.listRestoreSelectedModel.removeAllElements();
/*  83 */     this.listBackupAllTablesModel.removeAllElements();
/*  84 */     this.listRestoreupAllTablesModel.removeAllElements();
/*  85 */     this.listBackupSelectedModel.removeAllElements();
/*  86 */     getLabelFileOperationStatus().setText("Status: Idle");
/*  87 */     if (arg0) {
/*  88 */       populateAvailableTables();
/*     */     }
/*  90 */     super.setVisible(arg0);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  95 */     this.jPanel1 = new JPanel();
/*  96 */     this.jScrollPane1 = new JScrollPane();
/*  97 */     this.listAllTables = new JList(new DefaultListModel());
/*  98 */     this.jScrollPane2 = new JScrollPane();
/*  99 */     this.listSelectedTables = new JList(new DefaultListModel());
/* 100 */     this.buttonAddAlltoSelectedBox = new JButton();
/* 101 */     this.buttonRemoveAllFromSelectedBox = new JButton();
/* 102 */     this.buttonBackupToFile = new JButton();
/* 103 */     this.buttonMigrate = new JButton();
/* 104 */     this.buttonCancel = new JButton();
/* 105 */     this.jPanel2 = new JPanel();
/* 106 */     this.labelFileSelect = new JLabel();
/* 107 */     setLabelFileOperationStatus(new JLabel());
/* 108 */     this.buttonBrowseFile = new JButton();
/* 109 */     this.jScrollPane3 = new JScrollPane();
/* 110 */     this.listRestoreAllTables = new JList(new DefaultListModel());
/* 111 */     this.jScrollPane4 = new JScrollPane();
/* 112 */     this.listRestoreSelectedTables = new JList(new DefaultListModel());
/* 113 */     this.buttonAddAlltoSelectedBox2 = new JButton();
/* 114 */     this.buttonRemoveAllFromSelectedBox2 = new JButton();
/* 115 */     this.buttonRestore = new JButton();
/* 116 */     this.buttonExit = new JButton();
/* 117 */     Image image = Toolkit.getDefaultToolkit().getImage("hb.png");
/* 118 */     setIconImage(image);
/* 119 */     setDefaultCloseOperation(2);
/*     */ 
/* 121 */     this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */ 
/* 123 */     this.listAllTables.setModel(new DefaultListModel());
/*     */ 
/* 126 */     this.jScrollPane1.setViewportView(this.listAllTables);
/* 127 */     setDefaultStatus();
/* 128 */     this.jScrollPane2.setViewportView(this.listSelectedTables);
/*     */ 
/* 130 */     this.buttonAddAlltoSelectedBox.setText(">>");
/* 131 */     this.buttonAddAlltoSelectedBox.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 134 */         HbaseDataBackupRestoreDialog.this.buttonAddAlltoSelectedBoxClicked(evt);
/*     */       }
/*     */     });
/* 138 */     this.buttonRemoveAllFromSelectedBox.setText("<<");
/* 139 */     this.buttonRemoveAllFromSelectedBox.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 142 */         HbaseDataBackupRestoreDialog.this.buttonRemoveAllFromSelectedBoxClicked(evt);
/*     */       }
/*     */     });
/* 146 */     this.buttonBackupToFile.setText("Backup to File");
/* 147 */     this.buttonBackupToFile.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 150 */         HbaseDataBackupRestoreDialog.this.backupToFileButtonClicked(evt);
/*     */       }
/*     */     });
/* 154 */     this.buttonMigrate.setText("Migrate");
/*     */ 
/* 156 */     this.buttonCancel.setText("Cancel");
/* 157 */     this.buttonCancel.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 160 */         HbaseDataBackupRestoreDialog.this.buttonCancelClicked(evt);
/*     */       }
/*     */     });
/* 164 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 165 */     this.jPanel1.setLayout(jPanel1Layout);
/* 166 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(74, 74, 74).addComponent(this.jScrollPane1, -2, 120, -2).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.buttonAddAlltoSelectedBox, -2, 76, -2).addComponent(this.buttonRemoveAllFromSelectedBox, -2, 76, -2)).addGap(18, 18, 18).addComponent(this.jScrollPane2, -2, 120, -2).addGap(53, 53, 53).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.buttonBackupToFile, -1, -1, 32767).addComponent(this.buttonMigrate, -1, -1, 32767).addComponent(this.buttonCancel, -2, 99, -2)).addContainerGap(57, 32767)));
/* 167 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(37, 37, 37).addComponent(this.buttonAddAlltoSelectedBox).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.buttonRemoveAllFromSelectedBox).addGap(96, 96, 96)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(19, 19, 19).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jScrollPane2, -2, 230, -2).addComponent(this.jScrollPane1, -2, 230, -2))).addGroup(jPanel1Layout.createSequentialGroup().addGap(57, 57, 57).addComponent(this.buttonBackupToFile).addGap(26, 26, 26).addComponent(this.buttonMigrate).addGap(30, 30, 30).addComponent(this.buttonCancel))).addContainerGap(39, 32767)));
/*     */ 
/* 169 */     this.jPanel2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */ 
/* 171 */     this.labelFileSelect.setText("Select Backup File :");
/*     */ 
/* 173 */     this.buttonBrowseFile.setText("...");
/* 174 */     this.buttonBrowseFile.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 177 */         HbaseDataBackupRestoreDialog.this.buttonBrowseFileClicked(evt);
/*     */       }
/*     */     });
/* 181 */     this.listRestoreAllTables.setModel(new DefaultListModel());
/* 182 */     this.jScrollPane3.setViewportView(this.listRestoreAllTables);
/*     */ 
/* 184 */     this.jScrollPane4.setViewportView(this.listRestoreSelectedTables);
/*     */ 
/* 186 */     this.buttonAddAlltoSelectedBox2.setText(">>");
/* 187 */     this.buttonAddAlltoSelectedBox2.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 190 */         HbaseDataBackupRestoreDialog.this.buttonAddAlltoSelectedBox2(evt);
/*     */       }
/*     */     });
/* 194 */     this.buttonRemoveAllFromSelectedBox2.setText("<<");
/* 195 */     this.buttonRemoveAllFromSelectedBox2.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 198 */         HbaseDataBackupRestoreDialog.this.buttonRemoveAllFromSelectedBox2(evt);
/*     */       }
/*     */     });
/* 202 */     this.buttonRestore.setText("Restore");
/* 203 */     this.buttonRestore.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 206 */         HbaseDataBackupRestoreDialog.this.buttonRestoreClicked(evt);
/*     */       }
/*     */     });
/* 210 */     this.buttonExit.setText("Exit");
/* 211 */     this.buttonExit.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 214 */         HbaseDataBackupRestoreDialog.this.buttonExitClicked(evt);
/*     */       }
/*     */     });
/* 218 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 219 */     this.jPanel2.setLayout(jPanel2Layout);
/* 220 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(27, 27, 27).addComponent(this.labelFileSelect).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.buttonBrowseFile).addGap(40, 40, 40).addComponent(getLabelFileOperationStatus())).addGroup(jPanel2Layout.createSequentialGroup().addGap(102, 102, 102).addComponent(this.jScrollPane3, -2, 138, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.buttonAddAlltoSelectedBox2, -2, 65, -2).addComponent(this.buttonRemoveAllFromSelectedBox2, -2, 65, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane4, -2, 129, -2).addGap(51, 51, 51).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.buttonRestore, -1, -1, 32767).addComponent(this.buttonExit, -1, -1, 32767)))).addContainerGap(80, 32767)));
/* 221 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(24, 24, 24).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.labelFileSelect).addComponent(this.buttonBrowseFile).addComponent(getLabelFileOperationStatus())).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane3).addComponent(this.jScrollPane4)).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addGap(73, 73, 73).addComponent(this.buttonAddAlltoSelectedBox2).addGap(18, 18, 18).addComponent(this.buttonRestore).addGap(18, 18, 18).addComponent(this.buttonExit).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, 32767).addComponent(this.buttonRemoveAllFromSelectedBox2).addGap(32, 32, 32)));
/*     */ 
/* 223 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 224 */     getContentPane().setLayout(layout);
/* 225 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel1, -1, -1, 32767).addComponent(this.jPanel2, -1, -1, 32767)).addContainerGap(-1, 32767)));
/* 226 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel2, -2, -1, -2).addContainerGap(-1, 32767)));
/*     */ 
/* 228 */     pack();
/*     */   }
/*     */ 
/*     */   public void setDefaultStatus()
/*     */   {
/* 233 */     getLabelFileOperationStatus().setText("Status: Waiting for User action");
/*     */   }
/*     */ 
/*     */   private void backupToFileButtonClicked(ActionEvent evt)
/*     */   {
/* 243 */     JFileChooser fc = new JFileChooser();
/* 244 */     File backupFile = null;
/*     */ 
/* 246 */     fc.showSaveDialog(this);
/*     */ 
/* 248 */     backupFile = fc.getSelectedFile();
/*     */     try
/*     */     {
/* 251 */       getLabelFileOperationStatus().setText("Backup in progress");
/* 252 */       for (int i = 0; i < this.listSelectedTables.getModel().getSize(); i++) {
/* 253 */         String tblName = (String)this.listSelectedTables.getModel().getElementAt(i);
/* 254 */         this.listSelectedTables.setSelectedValue(tblName, true);
/* 255 */         Logger.getLogger(HbaseDataBackupRestoreDialog.class.getName()).log(Level.INFO, "Table name: " + tblName);
/* 256 */         HBaseTableStructure tableStructure = new HBaseTableStructure();
/* 257 */         tableStructure.createWriteTableStructure(tblName);
/*     */ 
/* 259 */         byte[][] colFamilys = tableStructure.getAllColoumnFamilies();
/*     */ 
/* 261 */         ResultScanner resultScan = HBaseTableManager.getAllDataInRangeOfFamily("0", "zz", colFamilys, tblName);
/*     */ 
/* 263 */         List tableRows = getDataObjectList(resultScan, colFamilys);
/*     */ 
/* 265 */         Iterator iterator = tableRows.iterator();
/*     */ 
/* 267 */         List tableObject = new ArrayList();
/* 268 */         while (iterator.hasNext())
/*     */         {
/* 270 */           List rowList = (List)iterator.next();
/* 271 */           HbaseTableObject hbTable = new HbaseTableObject();
/* 272 */           HBaseTableData tableData = new HBaseTableData();
/*     */ 
/* 274 */           tableData.setHbaseTableData(rowList);
/* 275 */           hbTable.setTableData(tableData);
/* 276 */           hbTable.setTableStructure(tableStructure);
/* 277 */           hbTable.setLastObject(false);
/* 278 */           hbTable.setLinkedFileAvailable(true);
/*     */ 
/* 280 */           if (!iterator.hasNext()) {
/* 281 */             hbTable.setLastObject(true);
/*     */           }
/* 283 */           tableObject.add(hbTable);
/*     */         }
/*     */ 
/* 287 */         File targetFile = new File(backupFile.getAbsolutePath().concat("_").concat(tblName));
/*     */ 
/* 289 */         BackupRestoreFileUtil fileUtil = new BackupRestoreFileUtil(targetFile);
/* 290 */         long rows = (tableObject.size() - 1) * 20001 + ((HbaseTableObject)tableObject.get(tableObject.size() - 1)).getTableData().getHbaseTableData().size();
/* 291 */         getLabelFileOperationStatus().setText("Backup Done: ~ " + rows + " backed-up");
/* 292 */         fileUtil.backupToFiles(tableObject);
/*     */ 
/* 295 */         this.listBackupSelectedModel.remove(0);
/*     */       }
/*     */ 
/* 299 */       JOptionPane.showMessageDialog(this, "Backup Complete. \n Backup File : " + fc.getSelectedFile().getName());
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 303 */       JOptionPane.showMessageDialog(this, "Backup Failed.", "Error", 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void buttonAddAlltoSelectedBoxClicked(ActionEvent evt)
/*     */   {
/* 311 */     Object[] sellist = this.listAllTables.getSelectedValues();
/* 312 */     for (int i = 0; i < sellist.length; i++)
/* 313 */       this.listBackupSelectedModel.addElement((String)sellist[i]);
/*     */   }
/*     */ 
/*     */   private void buttonRemoveAllFromSelectedBoxClicked(ActionEvent evt)
/*     */   {
/* 319 */     int[] sellist = this.listSelectedTables.getSelectedIndices();
/* 320 */     for (int i = 0; i < sellist.length; i++)
/* 321 */       this.listBackupSelectedModel.remove(this.listSelectedTables.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   private void buttonBrowseFileClicked(ActionEvent evt)
/*     */   {
/* 329 */     String tableName = null;
/* 330 */     JFileChooser fc = new JFileChooser();
/*     */ 
/* 332 */     fc.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 335 */         HbaseDataBackupRestoreDialog.this.getLabelFileOperationStatus().setText("Scanning backup files...Please wait");
/*     */ 
/* 337 */         HbaseDataBackupRestoreDialog.this.getLabelFileOperationStatus().setVisible(true);
/*     */       }
/*     */     });
/* 342 */     fc.showOpenDialog(new JLabel("Select the Backup File"));
/* 343 */     File restoreFile = fc.getSelectedFile();
/*     */     try
/*     */     {
/* 347 */       BackupRestoreFileUtil fileUtil = new BackupRestoreFileUtil(restoreFile);
/*     */ 
/* 352 */       List restoredTableData = fileUtil.restoreFromFiles(this);
/*     */ 
/* 354 */       if (restoredTableData.size() > 0)
/*     */       {
/* 356 */         tableName = ((HbaseTableObject)restoredTableData.get(0)).getTableStructure().getHTableName();
/* 357 */         this.listRestoreupAllTablesModel.addElement(tableName);
/* 358 */         this.tableObjects.put(tableName, restoredTableData);
/* 359 */         long rows = (restoredTableData.size() - 1) * 20001 + ((HbaseTableObject)restoredTableData.get(restoredTableData.size() - 1)).getTableData().getHbaseTableData().size();
/* 360 */         getLabelFileOperationStatus().setText("Status: Completed Reading Files (~" + rows + "rows available)");
/*     */       }
/*     */       else {
/* 363 */         JOptionPane.showMessageDialog(this, "Nothing to restore", "Warning", 2);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 368 */       JOptionPane.showMessageDialog(this, "Failed to find valid rows", "Error", 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void buttonRestoreClicked(ActionEvent evt)
/*     */   {
/* 376 */     String tableName = null;
/* 377 */     getLabelFileOperationStatus().setText("Restoring in progress");
/* 378 */     for (int i = 0; i < this.listRestoreSelectedTables.getModel().getSize(); i++) {
/*     */       try
/*     */       {
/* 381 */         tableName = (String)this.listRestoreSelectedTables.getModel().getElementAt(i);
/* 382 */         this.listRestoreSelectedTables.setSelectedValue(tableName, true);
/* 383 */         List objectlist = (List)this.tableObjects.get(tableName);
/* 384 */         Iterator iterator = objectlist.iterator();
/* 385 */         boolean createdTable = false;
/* 386 */         totalRows = Long.valueOf(0L);
/* 387 */         while (iterator.hasNext()) {
/* 388 */           this.hbTable = ((HbaseTableObject)iterator.next());
/*     */ 
/* 390 */           this.tableData = this.hbTable.getTableData();
/* 391 */           this.tableStructure = this.hbTable.getTableStructure();
/*     */ 
/* 393 */           HTableDescriptor descriptor = this.tableStructure.createReadTableStructure();
/* 394 */           if (!createdTable) {
/* 395 */             createTable(descriptor, createdTable);
/* 396 */             createdTable = true;
/*     */           }
/* 398 */           addDataToTable(this.tableData, HBaseTableManager.getTable(this.tableStructure.getHTableName()));
/*     */         }
/* 400 */         JOptionPane.showMessageDialog(this, "Table : " + this.listRestoreSelectedTables.getSelectedValue() + " Restored");
/* 401 */         this.listRestoreSelectedTables.setSelectedValue(tableName, false);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 406 */         System.out.print("Restore of " + tableName + " Failed" + ExceptionUtils.getFullStackTrace(e));
/* 407 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 411 */     JOptionPane.showMessageDialog(this, "All tables restored successfully");
/* 412 */     restoredTableData = null;
/*     */   }
/*     */ 
/*     */   private void buttonAddAlltoSelectedBox2(ActionEvent evt)
/*     */   {
/* 418 */     Object[] sellist = this.listRestoreAllTables.getSelectedValues();
/* 419 */     for (int i = 0; i < sellist.length; i++)
/* 420 */       this.listRestoreSelectedModel.addElement((String)sellist[i]);
/*     */   }
/*     */ 
/*     */   private void buttonRemoveAllFromSelectedBox2(ActionEvent evt)
/*     */   {
/* 426 */     int[] sellist = this.listRestoreSelectedTables.getSelectedIndices();
/* 427 */     for (int i = 0; i < sellist.length; i++)
/* 428 */       this.listRestoreSelectedModel.remove(this.listRestoreSelectedTables.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   private void buttonExitClicked(ActionEvent evt)
/*     */   {
/* 434 */     dispose();
/*     */   }
/*     */ 
/*     */   private void buttonCancelClicked(ActionEvent evt)
/*     */   {
/* 439 */     dispose();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 460 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 461 */         if ("Nimbus".equals(info.getName())) {
/* 462 */           UIManager.setLookAndFeel(info.getClassName());
/* 463 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 468 */       Logger.getLogger(HbaseDataBackupRestoreDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 471 */       Logger.getLogger(HbaseDataBackupRestoreDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 474 */       Logger.getLogger(HbaseDataBackupRestoreDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 477 */       Logger.getLogger(HbaseDataBackupRestoreDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 484 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 488 */         HbaseDataBackupRestoreDialog dialog = new HbaseDataBackupRestoreDialog(new JFrame(), true);
/* 489 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 494 */             System.exit(0);
/*     */           }
/*     */         });
/* 497 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void createTable(HTableDescriptor tableDesc, boolean ignoreTableExists)
/*     */   {
/* 531 */     Admin admin = HBaseConfigurationManager.getHbaseAdmin();
/*     */     try
/*     */     {
/* 535 */       admin.createTable(tableDesc);
/* 536 */       JOptionPane.showMessageDialog(this, "Table Created and Enabled Successfully");
/*     */     }
/*     */     catch (TableExistsException e) {
/* 539 */       if (!ignoreTableExists) {
/* 540 */         int sel = JOptionPane.showConfirmDialog(this, "Table Already Exist, Add Data ???", "Warning!", 0, 2);
/*     */ 
/* 542 */         if (sel == 1) {
/* 543 */           dispose();
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/* 549 */       JOptionPane.showMessageDialog(this, "Table Creation Failed", "Error", 0);
/* 550 */       Logger.getLogger(HBaseManagerTableDesign.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addDataToTable(HBaseTableData tableData, HTable table)
/*     */   {
/*     */     try
/*     */     {
/* 559 */       List tableDataRows = tableData.getHbaseTableData();
/*     */ 
/* 561 */       Iterator rowIterator = tableDataRows.iterator();
/*     */ 
/* 563 */       while (rowIterator.hasNext()) {
/* 564 */         HBaseRow tableDataFromRows = (HBaseRow)rowIterator.next();
/* 565 */         getLabelFileOperationStatus().setText("Status: Written " + totalRows + " rows");
/* 566 */         totalRows = Long.valueOf(totalRows.longValue() + 1L);
/* 567 */         tableDataFromRows.putRow(table);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 573 */       System.out.println("Exception in adding data to : " + Bytes.toString(table.getTableName()));
/* 574 */       System.out.println("Stacktrace: " + ExceptionUtils.getFullStackTrace(e));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void populateAvailableTables()
/*     */   {
/* 582 */     this.listBackupAllTablesModel.removeAllElements();
/* 583 */     String[] tables = HBaseTableManager.getAllTableNames();
/* 584 */     for (int i = 0; i < tables.length; i++)
/* 585 */       this.listBackupAllTablesModel.addElement(tables[i]);
/*     */   }
/*     */ 
/*     */   private List<List<HBaseRow>> getDataObjectList(ResultScanner resultScan, byte[][] families)
/*     */   {
/* 591 */     List rows = new ArrayList();
/*     */ 
/* 593 */     List allrows = new ArrayList();
/*     */ 
/* 595 */     int count = 0;
/*     */ 
/* 597 */     Long totalRow = Long.valueOf(0L);
/* 598 */     for (Result result : resultScan)
/*     */     {
/* 600 */       count++;
/* 601 */       getLabelFileOperationStatus().setText("Status: Written " + totalRow + " rows");
/* 602 */       totalRow = Long.valueOf(totalRow.longValue() + 1L);
/* 603 */       HBaseRow row = new HBaseRow(result, families);
/* 604 */       rows.add(row);
/* 605 */       if (count > 20000) {
/* 606 */         System.out.println("Copied next " + count + " rows. Starting on next file");
/* 607 */         count = 0;
/* 608 */         allrows.add(rows);
/* 609 */         rows = new ArrayList();
/* 610 */         rows.clear();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 615 */     if (count <= 20000) {
/* 616 */       System.out.println("Copied next " + count + " rows.Finished");
/* 617 */       count = 0;
/* 618 */       allrows.add(rows);
/*     */     }
/* 620 */     return allrows;
/*     */   }
/*     */ 
/*     */   public JLabel getLabelFileOperationStatus()
/*     */   {
/* 625 */     return this.labelFileOperationStatus;
/*     */   }
/*     */ 
/*     */   public void setLabelFileOperationStatus(JLabel labelFileOperationStatus)
/*     */   {
/* 630 */     this.labelFileOperationStatus = labelFileOperationStatus;
/*     */   }
/*     */ 
/*     */   public static List<HbaseTableObject> getRestoredTableData()
/*     */   {
/* 635 */     return restoredTableData;
/*     */   }
/*     */ 
/*     */   public static void setRestoredTableData(List<HbaseTableObject> restoredTableData, ReadFilesDialog dialog)
/*     */   {
/* 640 */     restoredTableData = restoredTableData;
/* 641 */     dialog.setVisible(false);
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HbaseDataBackupRestoreDialog
 * JD-Core Version:    0.6.2
 */