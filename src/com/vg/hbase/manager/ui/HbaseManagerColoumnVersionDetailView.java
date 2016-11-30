/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.comm.manager.HBaseTableManager;
/*     */ import com.vg.hbase.comm.manager.TableRowObject;

/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.border.LineBorder;

/*     */ import org.apache.hadoop.hbase.KeyValue;
/*     */ import org.apache.hadoop.hbase.client.Get;
/*     */ import org.apache.hadoop.hbase.client.HTable;
/*     */ import org.apache.hadoop.hbase.client.Result;
/*     */ import org.apache.hadoop.hbase.util.Bytes;
/*     */ 
/*     */ public class HbaseManagerColoumnVersionDetailView extends JFrame
/*     */ {
/*  37 */   private List<String> coloumnQualifiers = new ArrayList();
/*  38 */   private Map<String, List<KeyValue>> versionColoumns = new HashMap();
/*     */   private static final long serialVersionUID = -1145103491324638597L;
/*     */   private JButton buttonClickGetVersions;
/*     */   private JComboBox comboColQualifierForVersions;
/*     */   private JLabel jLabel1;
/*     */   private JList versionDataList;
/*     */   private JPanel jPanel1;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JPanel versionViewPanel;
/*     */   private JLabel versionViewStatusMessage;
/*     */ 
/*     */   public HbaseManagerColoumnVersionDetailView(String rowKey, String tableName, String familyName)
/*     */   {
/*  47 */     initComponents();
/*  48 */     getVersionDataOfRow(rowKey, tableName, familyName);
/*  49 */     if (this.comboColQualifierForVersions.getSelectedIndex() < 0)
/*     */     {
/*  51 */       this.versionViewPanel.setEnabled(false);
/*  52 */       this.buttonClickGetVersions.setEnabled(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void getVersionDataOfRow(String rowKey, String tableName, String familyName)
/*     */   {
/*  59 */     HTable table = HBaseTableManager.getTable(tableName);
/*  60 */     Get rowGet = new Get(Bytes.toBytes(rowKey));
/*  61 */     rowGet.setMaxVersions();
/*  62 */     Result versionedData = null;
/*     */     try {
/*  64 */       versionedData = table.get(rowGet);
/*  65 */       TableRowObject rowVersionObject = new TableRowObject(versionedData, familyName, true);
/*  66 */       Object[] dataObject = rowVersionObject.getAllRowInfo();
/*     */ 
/*  68 */       this.coloumnQualifiers = ((List)dataObject[0]);
/*  69 */       this.versionColoumns = ((Map)dataObject[1]);
/*     */ 
/*  71 */       Iterator colQs = this.coloumnQualifiers.iterator();
/*  72 */       while (colQs.hasNext())
/*     */       {
/*  74 */         this.comboColQualifierForVersions.addItem(colQs.next());
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  80 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public HbaseManagerColoumnVersionDetailView()
/*     */   {
/*  87 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  98 */     this.versionViewPanel = new JPanel();
/*  99 */     this.jScrollPane1 = new JScrollPane();
/* 100 */     this.versionDataList = new JList(new DefaultListModel());
/* 101 */     this.versionViewStatusMessage = new JLabel();
/* 102 */     this.jPanel1 = new JPanel();
/* 103 */     this.jLabel1 = new JLabel();
/* 104 */     this.buttonClickGetVersions = new JButton();
/* 105 */     this.comboColQualifierForVersions = new JComboBox();
/*     */ 
/* 107 */     setDefaultCloseOperation(3);
/* 108 */     setTitle("Version Detail : ");
/* 109 */     Image image = Toolkit.getDefaultToolkit().getImage("hb.png");
/* 110 */     setIconImage(image);
/* 111 */     this.versionViewPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
/*     */ 
/* 113 */     this.jScrollPane1.setViewportView(this.versionDataList);
/*     */ 
/* 115 */     GroupLayout versionViewPanelLayout = new GroupLayout(this.versionViewPanel);
/* 116 */     this.versionViewPanel.setLayout(versionViewPanelLayout);
/* 117 */     versionViewPanelLayout.setHorizontalGroup(versionViewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(versionViewPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1).addContainerGap()));
/* 118 */     versionViewPanelLayout.setVerticalGroup(versionViewPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(versionViewPanelLayout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 256, 32767).addContainerGap()));
/*     */ 
/* 120 */     this.versionViewStatusMessage.setHorizontalAlignment(0);
/* 121 */     this.versionViewStatusMessage.setText("Status: -Waiting for User Action-");
/*     */ 
/* 123 */     this.jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */ 
/* 125 */     this.jLabel1.setText("Versions for Coloumn: ");
/*     */ 
/* 127 */     this.buttonClickGetVersions.setText("Get All Versions");
/*     */ 
/* 129 */     this.comboColQualifierForVersions.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 132 */         HbaseManagerColoumnVersionDetailView.this.comboColQualifierForVersionsActionPerformed(evt);
/*     */       }
/*     */     });
/* 136 */     this.buttonClickGetVersions.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 139 */         HbaseManagerColoumnVersionDetailView.this.buttonClickGetVersionsActionPerformed(evt);
/*     */       }
/*     */     });
/* 142 */     setDefaultCloseOperation(2);
/* 143 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 144 */     this.jPanel1.setLayout(jPanel1Layout);
/* 145 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1, -2, 162, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.comboColQualifierForVersions, -2, 87, -2).addGap(18, 18, 18).addComponent(this.buttonClickGetVersions).addContainerGap()));
/* 146 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.comboColQualifierForVersions, -2, -1, -2).addComponent(this.buttonClickGetVersions)).addContainerGap()));
/*     */ 
/* 148 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 149 */     getContentPane().setLayout(layout);
/* 150 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.versionViewPanel, -1, -1, 32767).addComponent(this.jPanel1, -2, -1, 32767)).addContainerGap()).addComponent(this.versionViewStatusMessage, -1, -1, 32767));
/* 151 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addComponent(this.jPanel1, -2, -1, -2).addGap(18, 18, 18).addComponent(this.versionViewPanel, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.versionViewStatusMessage).addContainerGap()));
/*     */ 
/* 153 */     pack();
/*     */   }
/*     */ 
/*     */   private void comboColQualifierForVersionsActionPerformed(ActionEvent evt)
/*     */   {
/* 158 */     if (this.comboColQualifierForVersions.getSelectedIndex() > -1)
/*     */     {
/* 160 */       this.versionViewPanel.setEnabled(true);
/* 161 */       this.buttonClickGetVersions.setEnabled(true);
/* 162 */       this.jLabel1.setText("Versions for Coloumn Qualifier: " + this.comboColQualifierForVersions.getSelectedItem());
/* 163 */       this.versionViewStatusMessage.setText("Click Get Versions to view all Versions");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void buttonClickGetVersionsActionPerformed(ActionEvent evt)
/*     */   {
/* 170 */     List keyValues = (List)this.versionColoumns.get(this.comboColQualifierForVersions.getSelectedItem());
/* 171 */     Iterator value = keyValues.iterator();
/*     */ 
/* 173 */     int i = 0;
/*     */ 
/* 176 */     DefaultListModel lmodel = (DefaultListModel)this.versionDataList.getModel();
/* 177 */     lmodel.clear();
/*     */ 
/* 179 */     while (value.hasNext()) {
/* 180 */       i++;
/* 181 */       String entry = "Version(" + i + "): ";
/* 182 */       KeyValue keyval = (KeyValue)value.next();
/* 183 */       entry = entry.concat(Bytes.toString(keyval.getValue())).concat(" : ") + keyval.getTimestamp();
/* 184 */       lmodel.addElement(entry);
/*     */     }
/*     */ 
/* 187 */     this.versionViewStatusMessage.setText("Displaying all Available versions for " + this.comboColQualifierForVersions.getSelectedItem());
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 208 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 209 */         if ("Nimbus".equals(info.getName())) {
/* 210 */           UIManager.setLookAndFeel(info.getClassName());
/* 211 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 216 */       Logger.getLogger(HbaseManagerColoumnVersionDetailView.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 219 */       Logger.getLogger(HbaseManagerColoumnVersionDetailView.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 222 */       Logger.getLogger(HbaseManagerColoumnVersionDetailView.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 225 */       Logger.getLogger(HbaseManagerColoumnVersionDetailView.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 232 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 236 */         new HbaseManagerColoumnVersionDetailView().setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HbaseManagerColoumnVersionDetailView
 * JD-Core Version:    0.6.2
 */