/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ public class ConvertColoumnsDialog extends JDialog
/*     */ {
/*     */   private DefaultListModel listSourceModel;
/*     */   private DefaultListModel listTargetModel;
/*     */   public static String[] coloumnList;
/*  25 */   private HashMap<String, String> actionMap = new HashMap();
/*     */   HBaseManagerViewTable parentObject;
/*     */   private ButtonGroup buttonGroup1;
/*     */   private JButton clickConvert;
/*     */   private JButton jButton2;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JScrollPane jScrollPane2;
/*     */   private JList listSource;
/*     */   private JList listTarget;
/*     */   private JRadioButton radioBoolean;
/*     */   private JRadioButton radioBytes;
/*     */   private JRadioButton radioDate;
/*     */   private JRadioButton radioDouble;
/*     */   private JRadioButton radioInteger;
/*     */   private JRadioButton radioLong;
/*     */   private JRadioButton radioShort;
/*     */   private JRadioButton radioTimeStamp;
/*     */ 
/*     */   public ConvertColoumnsDialog(HBaseManagerViewTable parent, boolean modal)
/*     */   {
/*  29 */     super(parent, modal);
/*  30 */     this.parentObject = parent;
/*  31 */     initComponents();
/*     */ 
/*  33 */     this.buttonGroup1.add(this.radioDate);
/*  34 */     this.buttonGroup1.add(this.radioBoolean);
/*  35 */     this.buttonGroup1.add(this.radioDouble);
/*  36 */     this.buttonGroup1.add(this.radioInteger);
/*  37 */     this.buttonGroup1.add(this.radioLong);
/*  38 */     this.buttonGroup1.add(this.radioShort);
/*  39 */     this.buttonGroup1.add(this.radioTimeStamp);
/*  40 */     this.buttonGroup1.add(this.radioBytes);
/*  41 */     this.listSourceModel = ((DefaultListModel)this.listSource.getModel());
/*  42 */     this.listTargetModel = ((DefaultListModel)this.listTarget.getModel());
/*  43 */     coloumnList = parent.getCurrentTableColoumns();
/*     */ 
/*  45 */     populateSourceList();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  58 */     this.buttonGroup1 = new ButtonGroup();
/*  59 */     this.jScrollPane1 = new JScrollPane();
/*  60 */     this.listTarget = new JList();
/*  61 */     this.radioInteger = new JRadioButton();
/*  62 */     this.radioLong = new JRadioButton();
/*  63 */     this.radioDate = new JRadioButton();
/*  64 */     this.radioBoolean = new JRadioButton();
/*  65 */     this.radioDouble = new JRadioButton();
/*  66 */     this.radioShort = new JRadioButton();
/*  67 */     this.jScrollPane2 = new JScrollPane();
/*  68 */     this.listSource = new JList(new DefaultListModel());
/*  69 */     this.clickConvert = new JButton();
/*  70 */     this.jButton2 = new JButton();
/*  71 */     this.radioTimeStamp = new JRadioButton();
/*  72 */     this.radioBytes = new JRadioButton();
/*     */ 
/*  74 */     setDefaultCloseOperation(2);
/*  75 */     setTitle("Convert Coloumn Data");
/*  76 */     setAlwaysOnTop(true);
/*  77 */     setResizable(false);
/*  78 */     Image image = Toolkit.getDefaultToolkit().getImage("hb.png");
/*  79 */     setIconImage(image);
/*  80 */     this.listTarget.setModel(new DefaultListModel());
/*  81 */     this.listTarget.addKeyListener(new KeyAdapter() {
/*     */       public void keyPressed(KeyEvent evt) {
/*  83 */         ConvertColoumnsDialog.this.listTargetKeyPressed(evt);
/*     */       }
/*     */     });
/*  86 */     this.jScrollPane1.setViewportView(this.listTarget);
/*     */ 
/*  88 */     this.radioInteger.setText("Integer");
/*  89 */     this.radioInteger.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  91 */         ConvertColoumnsDialog.this.radioIntegerActionPerformed(evt);
/*     */       }
/*     */     });
/*  95 */     this.radioLong.setText("Long");
/*  96 */     this.radioLong.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  98 */         ConvertColoumnsDialog.this.radioLongActionPerformed(evt);
/*     */       }
/*     */     });
/* 102 */     this.radioDate.setText("Date | Time");
/* 103 */     this.radioDate.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 105 */         ConvertColoumnsDialog.this.radioDateActionPerformed(evt);
/*     */       }
/*     */     });
/* 109 */     this.radioBoolean.setText("Boolean");
/* 110 */     this.radioBoolean.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 112 */         ConvertColoumnsDialog.this.radioBooleanActionPerformed(evt);
/*     */       }
/*     */     });
/* 116 */     this.radioDouble.setText("Double");
/* 117 */     this.radioDouble.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 119 */         ConvertColoumnsDialog.this.radioDoubleActionPerformed(evt);
/*     */       }
/*     */     });
/* 123 */     this.radioShort.setText("Short");
/* 124 */     this.radioShort.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 126 */         ConvertColoumnsDialog.this.radioShortActionPerformed(evt);
/*     */       }
/*     */     });
/* 130 */     this.listSource.setModel(new DefaultListModel());
/* 131 */     this.jScrollPane2.setViewportView(this.listSource);
/*     */ 
/* 133 */     this.clickConvert.setText("Convert");
/* 134 */     this.clickConvert.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 136 */         ConvertColoumnsDialog.this.clickConvertActionPerformed(evt);
/*     */       }
/*     */     });
/* 140 */     this.jButton2.setText("Cancel");
/* 141 */     this.jButton2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 143 */         ConvertColoumnsDialog.this.jButton2ActionPerformed(evt);
/*     */       }
/*     */     });
/* 147 */     this.radioTimeStamp.setText("Time Stamp");
/* 148 */     this.radioTimeStamp.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 150 */         ConvertColoumnsDialog.this.radioTimeStampActionPerformed(evt);
/*     */       }
/*     */     });
/* 154 */     this.radioBytes.setText("Bytes");
/* 155 */     this.radioBytes.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 157 */         ConvertColoumnsDialog.this.radioBytesActionPerformed(evt);
/*     */       }
/*     */     });
/* 161 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 162 */     getContentPane().setLayout(layout);
/* 163 */     layout.setHorizontalGroup(layout
/* 164 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 165 */       .addGroup(
/* 166 */       layout.createSequentialGroup()
/* 167 */       .addGroup(
/* 168 */       layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 169 */       .addGroup(
/* 170 */       layout.createSequentialGroup()
/* 171 */       .addGap(152, 152, 152)
/* 172 */       .addGroup(
/* 173 */       layout.createParallelGroup(
/* 174 */       GroupLayout.Alignment.LEADING)
/* 175 */       .addComponent(this.radioShort)
/* 176 */       .addComponent(this.radioLong)
/* 177 */       .addComponent(this.radioDate)
/* 178 */       .addComponent(this.radioInteger)
/* 179 */       .addComponent(this.radioTimeStamp)
/* 180 */       .addComponent(this.radioDouble)
/* 181 */       .addComponent(this.radioBoolean)
/* 182 */       .addComponent(this.radioBytes))
/* 183 */       .addPreferredGap(
/* 184 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 185 */       .addComponent(this.jScrollPane1, 
/* 186 */       -2, 124, 
/* 187 */       -2))
/* 188 */       .addGroup(
/* 189 */       layout.createSequentialGroup()
/* 190 */       .addGap(126, 126, 126)
/* 191 */       .addComponent(this.clickConvert)
/* 192 */       .addPreferredGap(
/* 193 */       LayoutStyle.ComponentPlacement.RELATED)
/* 194 */       .addComponent(this.jButton2)))
/* 195 */       .addContainerGap(42, 32767))
/* 196 */       .addGroup(
/* 197 */       layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
/* 198 */       layout.createSequentialGroup()
/* 199 */       .addGap(20, 20, 20)
/* 200 */       .addComponent(this.jScrollPane2, -2, 124, 
/* 201 */       -2)
/* 202 */       .addContainerGap(261, 32767))));
/* 203 */     layout.setVerticalGroup(layout
/* 204 */       .createParallelGroup(GroupLayout.Alignment.LEADING)
/* 205 */       .addGroup(
/* 206 */       layout.createSequentialGroup()
/* 207 */       .addGroup(
/* 208 */       layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/* 209 */       .addGroup(
/* 210 */       layout.createSequentialGroup()
/* 211 */       .addGap(22, 22, 22)
/* 212 */       .addComponent(this.jScrollPane1, 
/* 213 */       -2, 228, 
/* 214 */       -2))
/* 215 */       .addGroup(
/* 216 */       layout.createSequentialGroup()
/* 217 */       .addGap(37, 37, 37)
/* 218 */       .addComponent(this.radioDate)
/* 219 */       .addPreferredGap(
/* 220 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 221 */       .addComponent(this.radioTimeStamp)
/* 222 */       .addPreferredGap(
/* 223 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 224 */       .addComponent(this.radioInteger)
/* 225 */       .addPreferredGap(
/* 226 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 227 */       .addComponent(this.radioLong)
/* 228 */       .addPreferredGap(
/* 229 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 230 */       .addComponent(this.radioShort)
/* 231 */       .addPreferredGap(
/* 232 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 233 */       .addComponent(this.radioBoolean)
/* 234 */       .addPreferredGap(
/* 235 */       LayoutStyle.ComponentPlacement.UNRELATED)
/* 236 */       .addComponent(this.radioDouble)
/* 237 */       .addPreferredGap(
/* 238 */       LayoutStyle.ComponentPlacement.RELATED)
/* 239 */       .addComponent(this.radioBytes)))
/* 240 */       .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, 
/* 241 */       32767)
/* 242 */       .addGroup(
/* 243 */       layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/* 244 */       .addComponent(this.clickConvert).addComponent(this.jButton2)).addContainerGap())
/* 245 */       .addGroup(
/* 246 */       layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
/* 247 */       layout.createSequentialGroup()
/* 248 */       .addGap(21, 21, 21)
/* 249 */       .addComponent(this.jScrollPane2, -2, 228, 
/* 250 */       -2)
/* 251 */       .addContainerGap(68, 32767))));
/*     */ 
/* 253 */     pack();
/*     */   }
/*     */ 
/*     */   private void radioDateActionPerformed(ActionEvent evt)
/*     */   {
/* 258 */     setTargetList("DATE");
/*     */   }
/*     */ 
/*     */   private void radioShortActionPerformed(ActionEvent evt)
/*     */   {
/* 263 */     setTargetList("SHORT");
/*     */   }
/*     */ 
/*     */   private void radioDoubleActionPerformed(ActionEvent evt) {
/* 267 */     setTargetList("DOUBLE");
/*     */   }
/*     */ 
/*     */   private void jButton2ActionPerformed(ActionEvent evt) {
/* 271 */     dispose();
/*     */   }
/*     */ 
/*     */   private void radioIntegerActionPerformed(ActionEvent evt) {
/* 275 */     setTargetList("INTEGER");
/*     */   }
/*     */ 
/*     */   private void radioLongActionPerformed(ActionEvent evt) {
/* 279 */     setTargetList("LONG");
/*     */   }
/*     */ 
/*     */   private void radioBooleanActionPerformed(ActionEvent evt) {
/* 283 */     setTargetList("BOOLEAN");
/*     */   }
/*     */ 
/*     */   private void listTargetKeyPressed(KeyEvent evt) {
/* 287 */     if (evt.getKeyCode() == 127)
/* 288 */       removeItemsFromSelected();
/*     */   }
/*     */ 
/*     */   private void clickConvertActionPerformed(ActionEvent evt)
/*     */   {
/* 294 */     this.parentObject.convertColoumnstoUserData(this.actionMap);
/*     */   }
/*     */ 
/*     */   private void radioTimeStampActionPerformed(ActionEvent evt) {
/* 298 */     setTargetList("TIMESTAMP");
/*     */   }
/*     */ 
/*     */   private void radioBytesActionPerformed(ActionEvent evt) {
/* 302 */     setTargetList("BYTES");
/*     */   }
/*     */ 
/*     */   private void setTargetList(String type)
/*     */   {
/* 307 */     Object[] selitems = this.listSource.getSelectedValues();
/* 308 */     int numSelected = selitems.length;
/* 309 */     System.out.println("Items to Convert :" + numSelected);
/* 310 */     String item = null;
/*     */ 
/* 312 */     for (int i = 0; i < numSelected; i++) {
/* 313 */       item = (String)selitems[i];
/*     */ 
/* 315 */       this.actionMap.put(item, type);
/* 316 */       System.out.println("Putting @: " + item);
/*     */ 
/* 318 */       if (!this.listTargetModel.contains(item))
/*     */       {
/* 320 */         this.listTargetModel.addElement(item);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 342 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 343 */         if ("Nimbus".equals(info.getName())) {
/* 344 */           UIManager.setLookAndFeel(info.getClassName());
/* 345 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 350 */       Logger.getLogger(ConvertColoumnsDialog.class.getName()).log(
/* 351 */         Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 354 */       Logger.getLogger(ConvertColoumnsDialog.class.getName()).log(
/* 355 */         Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 358 */       Logger.getLogger(ConvertColoumnsDialog.class.getName()).log(
/* 359 */         Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 362 */       Logger.getLogger(ConvertColoumnsDialog.class.getName()).log(
/* 363 */         Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 370 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 373 */         ConvertColoumnsDialog dialog = new ConvertColoumnsDialog(new HBaseManagerViewTable(), true);
/* 374 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 378 */             System.exit(0);
/*     */           }
/*     */         });
/* 381 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void populateSourceList()
/*     */   {
/* 406 */     if (coloumnList.length > 0) {
/* 407 */       for (int i = 1; i < coloumnList.length; i++)
/* 408 */         this.listSourceModel.addElement(coloumnList[i]);
/*     */     }
/*     */     else
/* 411 */       dispose();
/*     */   }
/*     */ 
/*     */   private void removeItemsFromSelected()
/*     */   {
/* 418 */     int[] sellist = this.listTarget.getSelectedIndices();
/*     */ 
/* 420 */     System.out.print("Items to remove  : " + sellist.length);
/*     */ 
/* 422 */     String val = null;
/*     */ 
/* 424 */     for (int i = 0; i < sellist.length; i++) {
/* 425 */       val = (String)this.listTarget.getSelectedValue();
/* 426 */       System.out.println("Removing @: " + val);
/* 427 */       this.actionMap.remove(val);
/* 428 */       this.listTargetModel.remove(this.listTarget.getSelectedIndex());
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.ConvertColoumnsDialog
 * JD-Core Version:    0.6.2
 */