/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import com.vg.hbase.comm.manager.HBaseTableManager;
/*     */ import com.vg.hbase.operations.base.HbaseManagerStatic;

/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;

/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HBaseManagerNewConnection extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 7185802611190755780L;
/*  40 */   private static boolean LISTEN_NOW = false;
/*  41 */   private static boolean SAVE_NEEDED = false;
/*  42 */   private static String DIRTY_ITEM = null;
/*  43 */   private final String connectionFile = "connectionList.xconf";
/*     */ 
/*  45 */   HashMap<String, String[]> connectionMap = new HashMap();
/*     */   private JButton clickCancel;
/*     */   private JButton clickConnectHbase;
/*     */   private JButton clickDeleteConnection;
/*     */   private JButton clickNewConnection;
/*     */   private JButton clickSaveConnection;
/*     */   private JComboBox comboConnectionList;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JLabel jLabel3;
/*     */   private JLabel jLabel4;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JLabel labelErrorConnection;
/*     */   private JTextField valueClientPort;
/*     */   private JTextField valueHbaseMaster;
/*     */   private JTextField valueQuorum;
/*     */ 
/*     */   public HBaseManagerNewConnection(Frame parent, boolean modal)
/*     */   {
/*  54 */     super(parent, modal);
/*  55 */     initComponents();
/*  56 */     this.clickSaveConnection.setVisible(false);
/*  57 */     readAllConnections();
/*  58 */     SAVE_NEEDED = false;
/*  59 */     this.labelErrorConnection.setVisible(false);
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  71 */     this.jPanel1 = new JPanel();
/*  72 */     this.clickNewConnection = new JButton();
/*  73 */     this.clickSaveConnection = new JButton();
/*  74 */     this.clickDeleteConnection = new JButton();
/*  75 */     this.jLabel1 = new JLabel();
/*  76 */     this.comboConnectionList = new JComboBox();
/*  77 */     this.jPanel2 = new JPanel();
/*  78 */     this.jLabel2 = new JLabel();
/*  79 */     this.jLabel3 = new JLabel();
/*  80 */     this.jLabel4 = new JLabel();
/*  81 */     this.valueQuorum = new JTextField();
/*  82 */     this.valueClientPort = new JTextField();
/*  83 */     this.valueHbaseMaster = new JTextField();
/*  84 */     this.labelErrorConnection = new JLabel();
/*  85 */     this.jPanel3 = new JPanel();
/*  86 */     this.clickConnectHbase = new JButton();
/*  87 */     this.clickCancel = new JButton();
/*     */ 
/*  89 */     setDefaultCloseOperation(2);
/*  90 */     setTitle("HBase Manager - Connections");
/*     */     try
/*     */     {
/*  93 */       BufferedImage image = ImageIO.read(new File("hb.gif"));
/*  94 */       setIconImage(image);
/*     */     }
/*     */     catch (IOException e1)
/*     */     {
/*  99 */       e1.printStackTrace();
/*     */     }
/* 101 */     this.clickNewConnection.setText("New..");
/* 102 */     this.clickNewConnection.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 105 */         HBaseManagerNewConnection.this.clickNewConnectionActionPerformed(evt);
/*     */       }
/*     */     });
/* 109 */     this.clickSaveConnection.setText("Save");
/* 110 */     this.clickSaveConnection.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 113 */         HBaseManagerNewConnection.this.clickSaveConnectionActionPerformed(evt);
/*     */       }
/*     */     });
/* 117 */     this.clickDeleteConnection.setText("Delete");
/* 118 */     this.clickDeleteConnection.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 121 */         HBaseManagerNewConnection.this.clickDeleteConnectionActionPerformed(evt);
/*     */       }
/*     */     });
/* 125 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 126 */     this.jPanel1.setLayout(jPanel1Layout);
/* 127 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.clickNewConnection, -2, 74, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickSaveConnection, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.clickDeleteConnection, -1, 129, 32767)));
/* 128 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.clickNewConnection).addComponent(this.clickSaveConnection).addComponent(this.clickDeleteConnection)).addContainerGap(-1, 32767)));
/*     */ 
/* 130 */     this.jLabel1.setText("Saved Connections :");
/*     */ 
/* 132 */     this.comboConnectionList.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 135 */         HBaseManagerNewConnection.this.comboConnectionListActionPerformed(evt);
/*     */       }
/*     */     });
/* 139 */     this.jPanel2.setBorder(BorderFactory.createEtchedBorder());
/*     */ 
/* 141 */     this.jLabel2.setText("hbase.zookeeper.quorum :");
/*     */ 
/* 143 */     this.jLabel3.setText("hbase.zookeeper.property.clientPort:");
/*     */ 
/* 145 */     this.jLabel4.setText("hbase.master:");
/*     */ 
/* 147 */     this.valueQuorum.getDocument().addDocumentListener(new DocumentListener()
/*     */     {
/*     */       public void changedUpdate(DocumentEvent e) {
/* 150 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent e)
/*     */       {
/* 155 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void insertUpdate(DocumentEvent e)
/*     */       {
/* 160 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */     });
/* 163 */     this.valueQuorum.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 166 */         HBaseManagerNewConnection.this.valueQuorumActionPerformed(evt);
/*     */       }
/*     */     });
/* 170 */     this.valueClientPort.getDocument().addDocumentListener(new DocumentListener()
/*     */     {
/*     */       public void changedUpdate(DocumentEvent e) {
/* 173 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent e)
/*     */       {
/* 178 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void insertUpdate(DocumentEvent e)
/*     */       {
/* 183 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */     });
/* 186 */     this.valueClientPort.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 189 */         HBaseManagerNewConnection.this.valueClientPortActionPerformed(evt);
/*     */       }
/*     */     });
/* 193 */     this.valueHbaseMaster.getDocument().addDocumentListener(new DocumentListener()
/*     */     {
/*     */       public void changedUpdate(DocumentEvent e) {
/* 196 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void removeUpdate(DocumentEvent e)
/*     */       {
/* 201 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */ 
/*     */       public void insertUpdate(DocumentEvent e)
/*     */       {
/* 206 */         HBaseManagerNewConnection.this.textBoxValueChanged();
/*     */       }
/*     */     });
/* 209 */     this.valueHbaseMaster.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 212 */         HBaseManagerNewConnection.this.valueHbaseMasterActionPerformed(evt);
/*     */       }
/*     */     });
/* 216 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 217 */     this.jPanel2.setLayout(jPanel2Layout);
/* 218 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4).addComponent(this.jLabel3).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.valueQuorum).addComponent(this.valueClientPort).addComponent(this.valueHbaseMaster, GroupLayout.Alignment.TRAILING, -1, 169, 32767)).addContainerGap()));
/* 219 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(54, 54, 54).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.valueQuorum, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.valueClientPort, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.valueHbaseMaster, -2, -1, -2)).addContainerGap(61, 32767)));
/*     */ 
/* 221 */     this.labelErrorConnection.setForeground(new Color(255, 51, 51));
/* 222 */     this.labelErrorConnection.setHorizontalAlignment(0);
/* 223 */     this.labelErrorConnection.setText("The Connection attempt Failed! Check your Connection Parameters");
/*     */ 
/* 225 */     this.clickConnectHbase.setText("Connect");
/* 226 */     this.clickConnectHbase.setHorizontalTextPosition(0);
/* 227 */     this.clickConnectHbase.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 230 */         HBaseManagerNewConnection.this.clickConnectHbaseActionPerformed(evt);
/*     */       }
/*     */     });
/* 234 */     this.clickCancel.setText("Cancel");
/* 235 */     this.clickCancel.setHorizontalTextPosition(0);
/* 236 */     this.clickCancel.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 239 */         HBaseManagerNewConnection.this.clickCancelActionPerformed(evt);
/*     */       }
/*     */     });
/* 243 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 244 */     this.jPanel3.setLayout(jPanel3Layout);
/* 245 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap(119, 32767).addComponent(this.clickConnectHbase).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.clickCancel).addGap(134, 134, 134)));
/* 246 */     jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.clickConnectHbase).addComponent(this.clickCancel, -1, -1, 32767)).addContainerGap()));
/*     */ 
/* 248 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 249 */     getContentPane().setLayout(layout);
/* 250 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(40, 40, 40).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel1, -2, -1, -2).addGroup(layout.createSequentialGroup().addGap(16, 16, 16).addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.comboConnectionList, -2, 170, -2)))).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel2, -2, -1, -2))).addGap(0, 22, 32767)).addComponent(this.labelErrorConnection, -1, -1, 32767).addComponent(this.jPanel3, -1, -1, 32767));
/* 251 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel1, -2, -1, -2).addGap(33, 33, 33).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.comboConnectionList, -2, -1, -2)).addGap(18, 18, 18).addComponent(this.jPanel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.labelErrorConnection).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jPanel3, -2, -1, 32767).addGap(14, 14, 14)));
/*     */ 
/* 253 */     pack();
/*     */   }
/*     */ 
/*     */   private void clickNewConnectionActionPerformed(ActionEvent evt)
/*     */   {
/* 258 */     String newConnectionName = JOptionPane.showInputDialog(this, "Name New Connection:", "HBase Manager: New Connection", 3);
/* 259 */     String[] emptyConfig = new String[3];
/* 260 */     emptyConfig[0] = "0.0.0.0";
/* 261 */     emptyConfig[1] = "000";
/* 262 */     emptyConfig[2] = "0.0.0.0";
/* 263 */     this.connectionMap.put(newConnectionName, emptyConfig);
/* 264 */     this.comboConnectionList.addItem(newConnectionName);
/* 265 */     this.comboConnectionList.setSelectedItem(newConnectionName);
/* 266 */     clearInputs();
/* 267 */     this.clickSaveConnection.setVisible(false);
/*     */   }
/*     */ 
/*     */   private void comboConnectionListActionPerformed(ActionEvent evt)
/*     */   {
/* 275 */     checkSaveNeeded();
/* 276 */     String currentConnection = (String)this.comboConnectionList.getSelectedItem();
/* 277 */     String[] connDetails = new String[3];
/* 278 */     connDetails = (String[])this.connectionMap.get(currentConnection);
/* 279 */     this.valueClientPort.setText(connDetails[1]);
/* 280 */     this.valueQuorum.setText(connDetails[0]);
/* 281 */     this.valueHbaseMaster.setText(connDetails[2]);
/* 282 */     SAVE_NEEDED = false;
/*     */ 
/* 285 */     this.clickSaveConnection.setVisible(false);
/*     */   }
/*     */ 
/*     */   private void clickConnectHbaseActionPerformed(ActionEvent evt)
/*     */   {
/* 294 */     this.labelErrorConnection.setVisible(false);
/* 295 */     checkSaveNeeded();
/*     */ 
/* 297 */     HBaseTableManager.shutdownAliveConnection();
/* 298 */     String[] conDetails = (String[])this.connectionMap.get(this.comboConnectionList.getSelectedItem());
/* 299 */     HbaseManagerStatic.setHBASE_ZOOKEEPER_QUORUM(conDetails[0]);
/* 300 */     HbaseManagerStatic.setHBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT(conDetails[1]);
/* 301 */     HbaseManagerStatic.setHBASE_MASTER(conDetails[2]);
/* 302 */     HbaseManagerStatic.setCurrentConnectionAlias((String)this.comboConnectionList.getSelectedItem());
/*     */ 
/* 304 */     HBaseTableManager.resetConnection();
/*     */ 
/* 306 */     this.clickConnectHbase.setEnabled(false);
/*     */ 
/* 309 */     if (HbaseManagerStatic.SERVER_ERROR) {
/* 310 */       this.clickConnectHbase.setEnabled(true);
/*     */ 
/* 312 */       this.labelErrorConnection.setVisible(true);
/* 313 */       HbaseManagerStatic.SERVER_ERROR = false;
/* 314 */       HbaseManagerStatic.SERVER_NOT_CONNECTED = true;
/*     */ 
/* 316 */       setVisible(true);
/*     */     }
/*     */     else {
/* 319 */       HbaseManagerStatic.SERVER_NOT_CONNECTED = false;
/* 320 */       this.clickConnectHbase.setEnabled(true);
/* 321 */       HBaseManagerViewTable.enableControls();
/* 322 */       setVisible(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clickCancelActionPerformed(ActionEvent evt)
/*     */   {
/* 330 */     checkSaveNeeded();
/* 331 */     HbaseManagerStatic.SERVER_NOT_CONNECTED = true;
/* 332 */     dispose();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/* 338 */     HbaseManagerStatic.SERVER_NOT_CONNECTED = true;
/* 339 */     HBaseManagerViewTable.disableControls();
/* 340 */     super.dispose();
/*     */   }
/*     */ 
/*     */   private void clickSaveConnectionActionPerformed(ActionEvent evt)
/*     */   {
/* 345 */     String[] conEdited = new String[3];
/* 346 */     String ConnectionName = (String)this.comboConnectionList.getSelectedItem();
/* 347 */     conEdited[1] = this.valueClientPort.getText();
/* 348 */     conEdited[2] = this.valueHbaseMaster.getText();
/* 349 */     conEdited[0] = this.valueQuorum.getText();
/* 350 */     this.connectionMap.put(ConnectionName, conEdited);
/* 351 */     this.clickSaveConnection.setVisible(false);
/* 352 */     SAVE_NEEDED = false;
/*     */ 
/* 354 */     storeConnections();
/*     */   }
/*     */ 
/*     */   private void clickDeleteConnectionActionPerformed(ActionEvent evt)
/*     */   {
/* 360 */     String ConnectionName = (String)this.comboConnectionList.getSelectedItem();
/* 361 */     this.connectionMap.remove(ConnectionName);
/* 362 */     this.comboConnectionList.remove(this.comboConnectionList.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   private void valueQuorumActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void valueClientPortActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void valueHbaseMasterActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 399 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 400 */         if ("Nimbus".equals(info.getName())) {
/* 401 */           UIManager.setLookAndFeel(info.getClassName());
/* 402 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 407 */       Logger.getLogger(HBaseManagerNewConnection.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 410 */       Logger.getLogger(HBaseManagerNewConnection.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 413 */       Logger.getLogger(HBaseManagerNewConnection.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 416 */       Logger.getLogger(HBaseManagerNewConnection.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 423 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 427 */         HBaseManagerNewConnection dialog = new HBaseManagerNewConnection(new JFrame(), true);
/* 428 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 433 */             System.exit(0);
/*     */           }
/*     */         });
/* 436 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void textBoxValueChanged()
/*     */   {
/* 463 */     this.clickSaveConnection.setVisible(true);
/*     */ 
/* 465 */     DIRTY_ITEM = (String)this.comboConnectionList.getSelectedItem();
/*     */ 
/* 467 */     SAVE_NEEDED = true;
/*     */   }
/*     */ 
/*     */   private void addConnectionToList()
/*     */   {
/* 474 */     LISTEN_NOW = true;
/*     */ 
/* 476 */     String[] keySet = new String[this.connectionMap.size()];
/* 477 */     this.connectionMap.keySet().toArray(keySet);
/*     */ 
/* 479 */     System.out.print("Adding:" + keySet.length);
/*     */ 
/* 481 */     for (int i = 0; i < keySet.length; i++) {
/* 482 */       System.out.print("Adding:" + keySet[i]);
/* 483 */       this.comboConnectionList.addItem(keySet[i]);
/*     */     }
/*     */ 
/* 487 */     this.clickSaveConnection.setVisible(false);
/*     */   }
/*     */ 
/*     */   private void clearInputs()
/*     */   {
/* 493 */     this.valueClientPort.setText("000");
/* 494 */     this.valueHbaseMaster.setText("0.0.0.0");
/* 495 */     this.valueQuorum.setText("0.0.0.0");
/*     */   }
/*     */ 
/*     */   private void checkSaveNeeded()
/*     */   {
/* 500 */     if (SAVE_NEEDED) {
/* 501 */       this.comboConnectionList.setSelectedItem(DIRTY_ITEM);
/* 502 */       DIRTY_ITEM = "";
/* 503 */       int result = JOptionPane.showConfirmDialog(this, "The new connection is not Saved, Do you want to save it?", "Save Connection", 0);
/* 504 */       if (result == 0) {
/* 505 */         clickSaveConnectionActionPerformed(null);
/* 506 */         SAVE_NEEDED = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String serializedConnInfo(String[] params)
/*     */   {
/* 516 */     String newString = "";
/* 517 */     for (String param : params) {
/* 518 */       newString = newString.concat(param).concat("`");
/*     */     }
/* 520 */     return newString;
/*     */   }
/*     */ 
/*     */   private void storeConnections()
/*     */   {
/* 525 */     String allConnectionData = "";
/* 526 */     Iterator iterator = this.connectionMap.keySet().iterator();
/* 527 */     while (iterator.hasNext()) {
/* 528 */       String connectionName = (String)iterator.next();
/* 529 */       String connectionData = serializedConnInfo((String[])this.connectionMap.get(connectionName));
/* 530 */       allConnectionData = allConnectionData.concat(connectionName.concat("#").concat(connectionData)).concat("\n");
/*     */     }
/*     */     try
/*     */     {
/* 534 */       FileUtils.writeByteArrayToFile(new File("connectionList.xconf"), allConnectionData.getBytes());
/*     */     }
/*     */     catch (IOException e) {
/* 537 */       this.labelErrorConnection.setForeground(Color.RED);
/* 538 */       this.labelErrorConnection.setText("Saving Connection failed");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readAllConnections()
/*     */   {
/* 547 */     this.connectionMap.clear();
/* 548 */     this.comboConnectionList.removeAllItems();
/*     */ 
/* 550 */     String allConnectionData = "";
/*     */     try {
/* 552 */       byte[] connectionData = FileUtils.readFileToByteArray(new File("connectionList.xconf"));
/* 553 */       if (connectionData != null)
/* 554 */         allConnectionData = new String(connectionData);
/*     */     }
/*     */     catch (IOException e) {
/* 557 */       System.err.println("Could not load input file");
/*     */     }
/*     */ 
/* 560 */     if (StringUtils.isNotEmpty(allConnectionData))
/*     */     {
/* 562 */       String[] connectionsList = allConnectionData.split("\n");
/* 563 */       for (String connection : connectionsList)
/*     */       {
/* 565 */         String[] connectionParams = { "", "", "" };
/*     */ 
/* 567 */         String[] connData = connection.split("#");
/* 568 */         String[] connParams = connData[1].split("`");
/*     */ 
/* 570 */         int i = 0;
/* 571 */         for (String x : connParams) {
/* 572 */           connectionParams[i] = x;
/* 573 */           i++;
/*     */         }
/*     */ 
/* 576 */         this.connectionMap.put(connData[0], connectionParams);
/* 577 */         this.comboConnectionList.addItem(connData[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HBaseManagerNewConnection
 * JD-Core Version:    0.6.2
 */