/*     */ package com.vg.hbase.manager.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Frame;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Calendar;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.DefaultComboBoxModel;
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
import javax.swing.LayoutStyle;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UIManager.LookAndFeelInfo;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ 
/*     */ public class DateFilterDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 4634311595551780501L;
/*     */   private JButton clickOnClose;
/*     */   private JButton clickSetDateFilter;
/*     */   private JComboBox dateFieldDf;
/*     */   private JComboBox dateFieldDt;
/*     */   private JLabel jLabel1;
/*     */   private JLabel jLabel2;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JComboBox monthFieldDf;
/*     */   private JComboBox monthFieldDt;
/*     */   private JComboBox yearFieldDf;
/*     */   private JComboBox yearFieldDt;
/*     */ 
/*     */   public DateFilterDialog(Frame parent, boolean modal)
/*     */   {
/*  28 */     super(parent, modal);
/*  29 */     initComponents();
/*  30 */     fillYearFields();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/*  41 */     this.jPanel1 = new JPanel();
/*  42 */     this.jPanel2 = new JPanel();
/*  43 */     this.jLabel1 = new JLabel();
/*  44 */     this.dateFieldDf = new JComboBox();
/*  45 */     this.yearFieldDf = new JComboBox();
/*  46 */     this.monthFieldDf = new JComboBox();
/*  47 */     this.jPanel3 = new JPanel();
/*  48 */     this.jLabel2 = new JLabel();
/*  49 */     this.yearFieldDt = new JComboBox();
/*  50 */     this.monthFieldDt = new JComboBox();
/*  51 */     this.dateFieldDt = new JComboBox();
/*  52 */     this.clickOnClose = new JButton();
/*  53 */     this.clickSetDateFilter = new JButton();
/*     */ 
/*  55 */     setTitle("Select a Date Range");
/*  56 */     setAlwaysOnTop(true);
/*  57 */     setBackground(Color.white);
/*  58 */     setName("dateFilterDialog");
/*  59 */     setResizable(false);
/*  60 */     getContentPane().setLayout(new GridLayout());
/*     */ 
/*  62 */     this.jPanel1.setBorder(BorderFactory.createBevelBorder(0, null, Color.gray, null, null));
/*     */ 
/*  64 */     this.jPanel2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */ 
/*  66 */     this.jLabel1.setHorizontalAlignment(0);
/*  67 */     this.jLabel1.setText("Date From");
/*     */ 
/*  69 */     this.dateFieldDf.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
/*  70 */     this.dateFieldDf.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  73 */         DateFilterDialog.this.dateFieldDfActionPerformed(evt);
/*     */       }
/*     */     });
/*  77 */     this.yearFieldDf.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  80 */         DateFilterDialog.this.yearFieldDfActionPerformed(evt);
/*     */       }
/*     */     });
/*  84 */     this.monthFieldDf.setModel(new DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
/*  85 */     this.monthFieldDf.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/*  88 */         DateFilterDialog.this.monthFieldDfActionPerformed(evt);
/*     */       }
/*     */     });
/*  92 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  93 */     this.jPanel2.setLayout(jPanel2Layout);
/*  94 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, -1, 32767).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(this.dateFieldDf, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.monthFieldDf, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.yearFieldDf, 0, 72, 32767).addContainerGap()));
/*  95 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.dateFieldDf, -2, -1, -2).addComponent(this.yearFieldDf, -2, -1, -2).addComponent(this.monthFieldDf, -2, -1, -2)).addGap(0, 16, 32767)));
/*     */ 
/*  97 */     this.jPanel3.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*     */ 
/*  99 */     this.jLabel2.setHorizontalAlignment(0);
/* 100 */     this.jLabel2.setText("Date To");
/*     */ 
/* 102 */     this.yearFieldDt.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 105 */         DateFilterDialog.this.yearFieldDtActionPerformed(evt);
/*     */       }
/*     */     });
/* 109 */     this.monthFieldDt.setModel(new DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
/* 110 */     this.monthFieldDt.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 113 */         DateFilterDialog.this.monthFieldDtActionPerformed(evt);
/*     */       }
/*     */     });
/* 117 */     this.dateFieldDt.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
/* 118 */     this.dateFieldDt.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 121 */         DateFilterDialog.this.dateFieldDtActionPerformed(evt);
/*     */       }
/*     */     });
/* 125 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 126 */     this.jPanel3.setLayout(jPanel3Layout);
/* 127 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -1, 177, 32767).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.dateFieldDt, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.monthFieldDt, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.yearFieldDt, 0, 72, 32767).addGap(1, 1, 1))));
/* 128 */     jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(this.jLabel2).addGap(0, 0, 32767)).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addGap(20, 20, 20).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.dateFieldDt, -2, -1, -2).addComponent(this.yearFieldDt, -2, -1, -2).addComponent(this.monthFieldDt, -2, -1, -2)).addContainerGap(21, 32767))));
/*     */ 
/* 130 */     this.clickOnClose.setText("Cancel");
/* 131 */     this.clickOnClose.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 134 */         DateFilterDialog.this.clickOnCloseActionPerformed(evt);
/*     */       }
/*     */     });
/* 138 */     this.clickSetDateFilter.setText("Set Date Filter");
/* 139 */     this.clickSetDateFilter.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 142 */         DateFilterDialog.this.clickSetDateFilterActionPerformed(evt);
/*     */       }
/*     */     });
/* 146 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 147 */     this.jPanel1.setLayout(jPanel1Layout);
/* 148 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(12, 12, 12).addComponent(this.jPanel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jPanel3, -1, -1, 32767).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(this.clickSetDateFilter).addGap(18, 18, 18).addComponent(this.clickOnClose, -2, 99, -2).addGap(92, 92, 92)));
/* 149 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel2, -1, -1, 32767).addComponent(this.jPanel3, -1, -1, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.clickOnClose).addComponent(this.clickSetDateFilter)).addContainerGap(21, 32767)));
/*     */ 
/* 151 */     getContentPane().add(this.jPanel1);
/*     */ 
/* 153 */     pack();
/*     */   }
/*     */ 
/*     */   private void dateFieldDfActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void yearFieldDfActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void monthFieldDfActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void yearFieldDtActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void monthFieldDtActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void dateFieldDtActionPerformed(ActionEvent evt)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void clickOnCloseActionPerformed(ActionEvent evt)
/*     */   {
/* 188 */     dispose();
/*     */   }
/*     */ 
/*     */   private void clickSetDateFilterActionPerformed(ActionEvent evt)
/*     */   {
/* 193 */     Calendar calFrom = Calendar.getInstance();
/* 194 */     Calendar calTo = Calendar.getInstance();
/*     */ 
/* 196 */     calFrom.clear();
/* 197 */     calTo.clear();
/*     */     try
/*     */     {
/* 204 */       int year = ((Integer)this.yearFieldDf.getSelectedItem()).intValue();
/* 205 */       calFrom.set(year, this.monthFieldDf.getSelectedIndex(), this.dateFieldDf.getSelectedIndex() + 1, 0, 0, 0);
/*     */ 
/* 207 */       year = ((Integer)this.yearFieldDt.getSelectedItem()).intValue();
/* 208 */       calTo.set(year, this.monthFieldDt.getSelectedIndex(), this.dateFieldDt.getSelectedIndex() + 1, 23, 59, 59);
/*     */ 
/* 210 */       long fromTime = calFrom.getTimeInMillis();
/* 211 */       long toTime = calTo.getTimeInMillis();
/*     */ 
/* 213 */       HBaseManagerViewTable.setFilterDateFrom(fromTime);
/* 214 */       HBaseManagerViewTable.setFilterDateTo(toTime);
/*     */ 
/* 216 */       JOptionPane.showMessageDialog(this, "Date Filter Active", "Warning", 2);
/* 217 */       dispose();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 221 */       JOptionPane.showMessageDialog(this, "Invalid Dates", "Error!!", 0);
/* 222 */       resetDates();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */     try
/*     */     {
/* 245 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 246 */         if ("Nimbus".equals(info.getName())) {
/* 247 */           UIManager.setLookAndFeel(info.getClassName());
/* 248 */           break;
/*     */         }
/*     */     }
/*     */     catch (ClassNotFoundException ex)
/*     */     {
/* 253 */       Logger.getLogger(DateFilterDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (InstantiationException ex) {
/* 256 */       Logger.getLogger(DateFilterDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (IllegalAccessException ex) {
/* 259 */       Logger.getLogger(DateFilterDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException ex) {
/* 262 */       Logger.getLogger(DateFilterDialog.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */ 
/* 269 */     EventQueue.invokeLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 273 */         DateFilterDialog dialog = new DateFilterDialog(new JFrame(), true);
/* 274 */         dialog.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 279 */             System.exit(0);
/*     */           }
/*     */         });
/* 282 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void fillYearFields()
/*     */   {
/* 306 */     for (int i = 1990; i <= 2100; i++) {
/* 307 */       this.yearFieldDf.addItem(Integer.valueOf(i));
/* 308 */       this.yearFieldDt.addItem(Integer.valueOf(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void resetDates()
/*     */   {
/* 314 */     this.dateFieldDf.setSelectedIndex(0);
/* 315 */     this.dateFieldDt.setSelectedIndex(0);
/*     */ 
/* 317 */     this.monthFieldDf.setSelectedIndex(0);
/* 318 */     this.monthFieldDt.setSelectedIndex(0);
/*     */ 
/* 320 */     this.yearFieldDf.setSelectedIndex(0);
/* 321 */     this.yearFieldDt.setSelectedIndex(0);
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.DateFilterDialog
 * JD-Core Version:    0.6.2
 */