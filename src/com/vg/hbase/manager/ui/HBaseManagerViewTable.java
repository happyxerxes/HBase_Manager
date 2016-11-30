/*      */ package com.vg.hbase.manager.ui;
/*      */ 
/*      */ import com.toedter.calendar.JCalendar;
/*      */ import com.vg.hbase.comm.manager.HBaseTableManager;
/*      */ import com.vg.hbase.comm.manager.TableRowObject;
/*      */ import com.vg.hbase.operations.base.HbaseManagerStatic;
/*      */ import com.vg.hbase.operations.base.HbaseManagerTableGetter;

/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.InputMethodEvent;
/*      */ import java.awt.event.InputMethodListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;

/*      */ import javax.imageio.ImageIO;
/*      */ import javax.swing.AbstractButton;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonModel;
/*      */ import javax.swing.ComboBoxModel;
/*      */ import javax.swing.DefaultComboBoxModel;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.GroupLayout.Alignment;
/*      */ import javax.swing.GroupLayout.ParallelGroup;
/*      */ import javax.swing.GroupLayout.SequentialGroup;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
/*      */ import javax.swing.LayoutStyle.ComponentPlacement;
/*      */ import javax.swing.ListModel;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.UIManager.LookAndFeelInfo;
/*      */ import javax.swing.UnsupportedLookAndFeelException;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.event.DocumentEvent;
/*      */ import javax.swing.event.DocumentListener;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.table.TableColumn;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.table.TableModel;
/*      */ import javax.swing.text.Document;

/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.hadoop.hbase.client.Result;
/*      */ import org.apache.hadoop.hbase.client.ResultScanner;
/*      */ import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
/*      */ import org.apache.hadoop.hbase.filter.FilterList;
/*      */ import org.apache.hadoop.hbase.filter.FilterList.Operator;
/*      */ import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
/*      */ import org.apache.hadoop.hbase.util.Bytes;
/*      */ 
/*      */ public class HBaseManagerViewTable extends JFrame
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*   65 */   private static boolean mannualRange = false;
/*   66 */   private static boolean PRESSED_SCAN_ONCE = false;
/*   67 */   private boolean PASS_ALL = false;
/*   68 */   private static boolean FILTER_SET = false;
/*   69 */   private static boolean ALLOW_UPDATE_COUNT = true;
/*      */   private static boolean PRESSED_BACKWARD_SEEK;
/*   71 */   private static boolean PAGINATED_ALREADY = false;
/*   72 */   private static boolean dateFilterSet = false;
/*      */   private static Color buttonDefault;
/*   76 */   private static long totalRowsBrought = 0L;
/*   77 */   private long currentRowLimit = 0L;
/*   78 */   private static long CURRENT_SPINNER_COUNT = 100L;
/*      */ 
/*   80 */   private static String dateFilterOnCq = "";
/*      */ 
/*   82 */   private static int paginationCount = 0;
/*      */   private static long filterDateFrom;
/*      */   private static long filterDateTo;
/*   87 */   private static List<String> paginateForwardLimits = new ArrayList();
/*   88 */   private static List<String> paginateBackWardLimits = new ArrayList();
/*   89 */   private List<String> coloumnList = new ArrayList();
/*   90 */   private List<String> currentColoumnList = new ArrayList();
/*      */ 
/*   92 */   private String CURRENT_START_RANGE = "0";
/*   93 */   private String CURRENT_STOP_RANGE = "zzz";
/*   94 */   private Map<String, Integer> coloumnMap = new HashMap();
/*      */   private static HbaseManagerTableGetter hbaseTableGetter;
/*   97 */   public static HashMap<String, String> coloumnTypeList = new HashMap();
/*   98 */   private static HashMap<String, String[]> filterStrings = new HashMap();
/*      */   private HBaseManagerNewConnection newConnectionDialog;
/*      */   private HBaseManagerTableDesign newTableDesign;
/*      */   private HBaseManagerAdminPanel adminBenchDialog;
/*      */   private HbaseDataBackupRestoreDialog dataBackupDialog;
/*      */   private DateFilterDialog dateFilterDialog;
/*      */   private static JButton clickSetDateFilter;
/*      */   private static JButton clickAddFilterToList;
/*      */   private static JButton clickConvert;
/*      */   private static JButton clickVersions;
/*      */   private static JButton clickDelete;
/*      */   private static JButton clickInsert;
/*      */   private static JButton clickNEWCQ;
/*      */   private static JMenuItem jMenuItem1;
/*      */   private static JButton clickPaginateForward;
/*      */   private static JButton clickPaginateBackward;
/*      */   private static JButton clickRefreshTableData;
/*      */   private static JButton clickRemoveFilterFromList;
/*      */   private static JButton clickSave;
/*      */   private static JButton clickScan;
/*      */   private static JComboBox comboCompareOpLsit;
/*      */   private static JComboBox comboFamilyList;
/*      */   private static JComboBox comboFieldList;
/*      */   private static JMenu menuClickBackUp;
/*      */   private static JComboBox comboTableList;
/*      */   private static JPanel controlPane;
/*      */   private static JLabel jLabel1;
/*      */   private static JLabel jLabel10;
/*      */   private static JLabel jLabel2;
/*      */   private static JLabel jLabel3;
/*      */   private static JLabel jLabel4;
/*      */   private static JLabel jLabel5;
/*      */   private static JLabel jLabel6;
/*      */   private static JLabel jLabel7;
/*      */   private static JLabel jLabel8;
/*      */   private static JLabel jLabel9;
/*      */   private static JMenuBar jMenuBar1;
/*      */   private static JSpinner rowLimitSpinner;
/*      */   private static JPanel jPanel1;
/*      */   private static JPanel jPanel3;
/*      */   private static JPanel jPanel4;
/*      */   private static JScrollPane jScrollPane1;
/*      */   private static JScrollPane jScrollPane2;
/*      */   private static JTabbedPane jTabbedPane2;
/*      */   private static JLabel labelLoadingData;
/*      */   private static JList listFiltersSet;
/*      */   private static JMenu menuAdministrator;
/*      */   private static JLabel labelTotalRows;
/*      */   private static JMenu menuConnections;
/*      */   private static JMenuItem menuExit;
/*      */   private static JMenuItem menuNewConnection;
/*      */   private static JMenuItem menuOpenAdmin;
/*      */   private static JMenuItem menuOpenTableDesigner;
/*      */   private static JPanel panelInteranlCustomSet;
/*      */   private static JPanel panelInternalCutomize;
/*      */   private static JPanel panelInternalSetFilters;
/*      */   private static JRadioButton radioAND;
/*      */   private static JRadioButton radioOR;
/*      */   private static JCheckBox toggleCustomScan;
/*      */   private static JTable userTable;
/*      */   private static JTextField valueFilterDataColoumn;
/*      */   private static JTextField valueStartRow;
/*      */   private static JTextField valueStopRow;
/*      */   private JCalendar calendarFrom;
/*      */   private JCalendar calendarTo;
/*      */ 
/*      */   public static boolean isDateFilterSet()
/*      */   {
/*  109 */     return dateFilterSet;
/*      */   }
/*      */ 
/*      */   public static void setDateFilterSet(boolean dateFilterSet)
/*      */   {
/*  114 */     dateFilterSet = dateFilterSet;
/*      */   }
/*      */ 
/*      */   public static String getCurrentFamily()
/*      */   {
/*  119 */     return (String)comboFamilyList.getSelectedItem();
/*      */   }
/*      */ 
/*      */   public HBaseManagerViewTable()
/*      */   {
/*  124 */     HbaseManagerStatic.SERVER_NOT_CONNECTED = true;
/*      */ 
/*  126 */     initComponents();
/*  127 */     labelTotalRows.setText("Waiting for a connection to be established");
/*  128 */     labelLoadingData.setVisible(false);
/*      */ 
/*  139 */     setVisible(true);
/*      */ 
/*  144 */     this.newConnectionDialog = new HBaseManagerNewConnection(this, true);
/*  145 */     this.newConnectionDialog.setLocationRelativeTo(this);
/*  146 */     this.newConnectionDialog.setVisible(true);
/*      */ 
/*  148 */     initUI();
/*      */ 
/*  150 */     rowLimitSpinner.setValue(Integer.valueOf(500));
/*      */ 
/*  152 */     CURRENT_SPINNER_COUNT = ((Integer)rowLimitSpinner.getValue()).intValue();
/*      */ 
/*  154 */     this.adminBenchDialog = new HBaseManagerAdminPanel(this, true);
/*  155 */     this.adminBenchDialog.setLocationRelativeTo(this);
/*      */ 
/*  157 */     this.dataBackupDialog = new HbaseDataBackupRestoreDialog(this, true);
/*  158 */     this.dataBackupDialog.setLocationRelativeTo(this);
/*      */ 
/*  160 */     this.dateFilterDialog = new DateFilterDialog(this, true);
/*  161 */     this.dateFilterDialog.setLocationRelativeTo(this);
/*      */   }
/*      */ 
/*      */   public static void initUI()
/*      */   {
/*  167 */     hbaseTableGetter = new HbaseManagerTableGetter();
/*      */ 
/*  171 */     totalRowsBrought = 0L;
/*  172 */     labelTotalRows.setText("Waiting for a connection to be established");
/*      */ 
/*  174 */     if (HbaseManagerStatic.SERVER_NOT_CONNECTED) {
	
				//syxiong
/*  175 */     //  disableControls();
/*      */     }
/*      */     else {
/*  178 */       labelLoadingData.setVisible(true);
/*  179 */       enableControls();
/*  180 */       addTableList();
/*  181 */       mannualRange = false;
/*  182 */       panelInteranlCustomSet.setVisible(false);
/*  183 */       userTable.setAutoResizeMode(0);
/*  184 */       radioOR.setSelected(true);
/*  185 */       buttonDefault = clickSetDateFilter.getBackground();
/*      */     }
/*  187 */     resetPaginators("Initializing UI");
/*      */   }
/*      */ 
/*      */   public static void addTableList()
/*      */   {
/*  193 */     toggleCustomScan.setSelected(false);
/*  194 */     clearTogglePane();
/*      */     try {
/*  196 */       DefaultComboBoxModel model = (DefaultComboBoxModel)comboTableList.getModel();
/*  197 */       model.removeAllElements();
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */ 
/*  203 */     String[] tblList = HBaseTableManager.getAllTableNames();
/*  204 */     String[] arrayOfString1 = tblList; int j = tblList.length; for (int i = 0; i < j; i++) { String tblListItem = arrayOfString1[i];
/*  205 */       comboTableList.addItem(tblListItem);
/*      */     }
/*  207 */     if (comboTableList.getModel().getSize() > 0) {
/*  208 */       comboTableList.setSelectedIndex(0);
/*  209 */       AddColoumnList((String)comboTableList.getSelectedItem());
/*      */     }
/*      */ 
/*  212 */     labelLoadingData.setVisible(false);
/*      */   }
/*      */ 
/*      */   private static void AddColoumnList(String tblname)
/*      */   {
/*  218 */     String[] familyList = HBaseTableManager.getColFamilies(tblname);
/*      */ 
/*  220 */     comboFamilyList.removeAllItems();
/*      */ 
/*  222 */     for (String tblListItem : familyList)
/*  223 */       comboFamilyList.addItem(tblListItem);
/*      */   }
/*      */ 
/*      */   private String[][] getRowData(int row)
/*      */   {
/*  230 */     List dataList = new ArrayList();
/*  231 */     List colList = new ArrayList();
/*      */ 
/*  235 */     String tablename = (String)comboTableList.getSelectedItem();
/*  236 */     String family = (String)comboFamilyList.getSelectedItem();
/*  237 */     for (int i = 0; i < this.coloumnList.size(); i++) {
/*  238 */       String data = (String)userTable.getValueAt(row, i);
/*  239 */       if (StringUtils.isEmpty(data)) {
/*  240 */         String colname = userTable.getColumnName(i);
/*  241 */         String rowKey = (String)userTable.getValueAt(row, 0);
/*  242 */         HBaseTableManager.deleteTableColoumnQualifier(tablename, rowKey, colname, family);
/*      */       }
/*      */       else
/*      */       {
/*  246 */         dataList.add(data);
/*  247 */         colList.add(userTable.getColumnName(i));
/*      */       }
/*      */     }
/*      */ 
/*  251 */     String[][] tbldata = new String[dataList.size()][2];
/*      */ 
/*  253 */     for (int i = 0; i < dataList.size(); i++)
/*      */     {
/*  255 */       tbldata[i][0] = ((String)dataList.get(i));
/*  256 */       tbldata[i][1] = ((String)colList.get(i));
/*      */     }
/*      */ 
/*  260 */     return tbldata;
/*      */   }
/*      */ 
/*      */   private void deleteHbaseRow(String rowKey)
/*      */   {
/*  266 */     String tableName = (String)comboTableList.getSelectedItem();
/*  267 */     HBaseTableManager.deleteTableRow(Bytes.toBytes(rowKey), tableName);
/*      */   }
/*      */ 
/*      */   public String[] getCurrentTableColoumns()
/*      */   {
/*  273 */     this.currentColoumnList.clear();
/*  274 */     int count = userTable.getColumnCount();
/*  275 */     String[] coloumns = new String[count];
/*      */ 
/*  277 */     for (int i = 0; i < count; i++) {
/*  278 */       coloumns[i] = userTable.getColumnName(i);
/*  279 */       this.currentColoumnList.add(coloumns[i]);
/*      */     }
/*      */ 
/*  282 */     return coloumns;
/*      */   }
/*      */ 
/*      */   private void initComponents()
/*      */   {
/*  294 */     jPanel3 = new JPanel();
/*      */ 
/*  296 */     jPanel1 = new JPanel();
/*  297 */     controlPane = new JPanel();
/*  298 */     jLabel1 = new JLabel();
/*  299 */     panelInteranlCustomSet = new JPanel();
/*  300 */     jTabbedPane2 = new JTabbedPane();
/*  301 */     panelInternalCutomize = new JPanel();
/*  302 */     jLabel3 = new JLabel();
/*  303 */     jLabel4 = new JLabel();
/*  304 */     valueStartRow = new JTextField();
/*      */ 
/*  306 */     valueStopRow = new JTextField();
/*  307 */     panelInternalSetFilters = new JPanel();
/*  308 */     jScrollPane1 = new JScrollPane();
/*  309 */     listFiltersSet = new JList(new DefaultListModel());
/*  310 */     jLabel5 = new JLabel();
/*  311 */     jLabel6 = new JLabel();
/*  312 */     comboFieldList = new JComboBox();
/*  313 */     comboCompareOpLsit = new JComboBox();
/*  314 */     jLabel7 = new JLabel();
/*  315 */     jLabel8 = new JLabel();
/*  316 */     valueFilterDataColoumn = new JTextField();
/*  317 */     clickAddFilterToList = new JButton();
/*  318 */     jLabel9 = new JLabel();
/*  319 */     radioAND = new JRadioButton();
/*  320 */     radioOR = new JRadioButton();
/*  321 */     clickRemoveFilterFromList = new JButton();
/*  322 */     clickSetDateFilter = new JButton();
/*      */ 
/*  324 */     jLabel2 = new JLabel();
/*  325 */     comboFamilyList = new JComboBox();
/*  326 */     comboTableList = new JComboBox();
/*  327 */     labelLoadingData = new JLabel();
/*  328 */     clickScan = new JButton();
/*  329 */     toggleCustomScan = new JCheckBox();
/*  330 */     jPanel4 = new JPanel();
/*  331 */     jScrollPane2 = new JScrollPane();
/*  332 */     userTable = new JTable();
/*  333 */     clickInsert = new JButton();
/*  334 */     clickSave = new JButton();
/*  335 */     clickNEWCQ = new JButton();
/*  336 */     clickDelete = new JButton();
/*  337 */     clickRefreshTableData = new JButton();
/*  338 */     clickPaginateBackward = new JButton();
/*  339 */     clickPaginateForward = new JButton();
/*  340 */     rowLimitSpinner = new JSpinner();
/*  341 */     jLabel10 = new JLabel();
/*  342 */     clickConvert = new JButton();
/*  343 */     clickVersions = new JButton();
/*  344 */     labelTotalRows = new JLabel();
/*  345 */     jMenuBar1 = new JMenuBar();
/*  346 */     menuConnections = new JMenu();
/*  347 */     menuNewConnection = new JMenuItem();
/*  348 */     menuExit = new JMenuItem();
/*  349 */     menuAdministrator = new JMenu();
/*  350 */     menuOpenTableDesigner = new JMenuItem();
/*  351 */     menuOpenAdmin = new JMenuItem();
/*  352 */     menuClickBackUp = new JMenu();
/*  353 */     jMenuItem1 = new JMenuItem();
/*      */ 
/*  355 */     GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
/*  356 */     jPanel3.setLayout(jPanel3Layout);
/*  357 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, 32767));
/*  358 */     jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, 32767));
/*      */ 
/*  360 */     setDefaultCloseOperation(2);
/*  361 */     setTitle("HBase Manager");
/*  362 */     setResizable(false);
/*  363 */     jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  364 */     controlPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  365 */     jLabel1.setText("Select Table : ");
/*  366 */     jTabbedPane2.setBorder(BorderFactory.createBevelBorder(0));
/*  367 */     jLabel3.setText("Start Range:");
/*  368 */     jLabel4.setText("Stop Range:");
/*      */ 
/*  370 */     comboFieldList.setEditable(true);
/*  371 */     comboFieldList.setPrototypeDisplayValue("maxqualifier");
/*      */     try
/*      */     {
/*  374 */       BufferedImage image = ImageIO.read(new File("hb.gif"));
/*  375 */       setIconImage(image);
/*      */     }
/*      */     catch (IOException e1)
/*      */     {
/*  380 */       e1.printStackTrace();
/*      */     }
/*      */ 
/*  383 */     valueStartRow.getDocument().addDocumentListener(new DocumentListener()
/*      */     {
/*      */       public void changedUpdate(DocumentEvent e) {
/*  386 */         HBaseManagerViewTable.resetPaginators("Custom Start row Change");
/*      */       }
/*      */ 
/*      */       public void removeUpdate(DocumentEvent e)
/*      */       {
/*  391 */         HBaseManagerViewTable.resetPaginators("Custom Start row Change");
/*      */       }
/*      */ 
/*      */       public void insertUpdate(DocumentEvent e)
/*      */       {
/*  396 */         HBaseManagerViewTable.resetPaginators("Custom Start row Change");
/*      */       }
/*      */     });
/*  399 */     valueStartRow.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  402 */         HBaseManagerViewTable.this.valueStartRowActionPerformed(evt);
/*      */       }
/*      */     });
/*  405 */     valueStartRow.addInputMethodListener(new InputMethodListener()
/*      */     {
/*      */       public void caretPositionChanged(InputMethodEvent evt)
/*      */       {
/*      */       }
/*      */ 
/*      */       public void inputMethodTextChanged(InputMethodEvent evt) {
/*  412 */         HBaseManagerViewTable.this.valueStartRowInputMethodTextChanged(evt);
/*      */       }
/*      */     });
/*  415 */     valueStopRow.getDocument().addDocumentListener(new DocumentListener()
/*      */     {
/*      */       public void changedUpdate(DocumentEvent e) {
/*  418 */         HBaseManagerViewTable.resetPaginators("Custom Stop row Change");
/*      */       }
/*      */ 
/*      */       public void removeUpdate(DocumentEvent e)
/*      */       {
/*  423 */         HBaseManagerViewTable.resetPaginators("Custom Stop row Change");
/*      */       }
/*      */ 
/*      */       public void insertUpdate(DocumentEvent e)
/*      */       {
/*  428 */         HBaseManagerViewTable.resetPaginators("Custom Stop row Change");
/*      */       }
/*      */     });
/*  431 */     GroupLayout panelInternalCutomizeLayout = new GroupLayout(panelInternalCutomize);
/*  432 */     panelInternalCutomize.setLayout(panelInternalCutomizeLayout);
/*  433 */     panelInternalCutomizeLayout.setHorizontalGroup(panelInternalCutomizeLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalCutomizeLayout.createSequentialGroup().addGap(45, 45, 45).addGroup(panelInternalCutomizeLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(panelInternalCutomizeLayout.createSequentialGroup().addComponent(jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(valueStartRow, -2, 332, -2)).addGroup(panelInternalCutomizeLayout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(valueStopRow, -2, 332, -2))).addContainerGap(209, 32767)));
/*  434 */     panelInternalCutomizeLayout.setVerticalGroup(panelInternalCutomizeLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalCutomizeLayout.createSequentialGroup().addContainerGap().addGroup(panelInternalCutomizeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(valueStartRow, -2, -1, -2)).addGap(44, 44, 44).addGroup(panelInternalCutomizeLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(valueStopRow, -2, -1, -2)).addContainerGap(49, 32767)));
/*      */ 
/*  436 */     jTabbedPane2.addTab("Custom Scan Range", panelInternalCutomize);
/*      */ 
/*  438 */     listFiltersSet.setModel(new DefaultListModel());
/*      */ 
/*  440 */     jScrollPane1.setViewportView(listFiltersSet);
/*      */ 
/*  442 */     jLabel5.setText("Set Value Filters Here");
/*      */ 
/*  444 */     jLabel6.setText("Field :");
/*      */ 
/*  446 */     comboFieldList.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  449 */         HBaseManagerViewTable.this.comboFieldListActionPerformed(evt);
/*      */       }
/*      */     });
/*  453 */     comboCompareOpLsit.setModel(new DefaultComboBoxModel(new String[] { "EQUAL", "GREATER", "LESS", "GREATER OR EQUAL", "LESSER OR EQUAL", "NOT OP", "NOT EQUAL" }));
/*  454 */     comboCompareOpLsit.setAutoscrolls(true);
/*      */ 
/*  456 */     jLabel7.setText("Operation:");
/*      */ 
/*  458 */     jLabel8.setText("Value:");
/*      */ 
/*  460 */     clickAddFilterToList.setText("Add Filter >>");
/*  461 */     clickAddFilterToList.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  464 */         HBaseManagerViewTable.this.clickAddFilterToListActionPerformed(evt);
/*      */       }
/*      */     });
/*  468 */     jLabel9.setHorizontalAlignment(0);
/*  469 */     jLabel9.setText("Filter List");
/*      */ 
/*  471 */     radioAND.setText("AND Filter");
/*  472 */     radioAND.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  475 */         HBaseManagerViewTable.this.radioANDActionPerformed(evt);
/*      */       }
/*      */     });
/*  479 */     radioOR.setText("OR Filter");
/*  480 */     radioOR.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  483 */         HBaseManagerViewTable.this.radioORActionPerformed(evt);
/*      */       }
/*      */     });
/*  487 */     clickRemoveFilterFromList.setText("<<Remove Filter");
/*      */ 
/*  489 */     clickRemoveFilterFromList.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  492 */         HBaseManagerViewTable.this.clickRemoveFilterFromListActionPerformed(evt);
/*      */       }
/*      */     });
/*  495 */     clickSetDateFilter.setText("Set Date Filter");
/*      */ 
/*  497 */     clickSetDateFilter.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  500 */         HBaseManagerViewTable.this.clickSetDateFilterActionPerformed(evt);
/*      */       }
/*      */     });
/*  504 */     GroupLayout panelInternalSetFiltersLayout = new GroupLayout(panelInternalSetFilters);
/*  505 */     panelInternalSetFilters.setLayout(panelInternalSetFiltersLayout);
/*  506 */     panelInternalSetFiltersLayout.setHorizontalGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addContainerGap().addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addComponent(jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(comboFieldList, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel7).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(comboCompareOpLsit, -2, 113, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(valueFilterDataColoumn, -2, 119, -2)).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addComponent(clickSetDateFilter, -1, -1, 32767).addComponent(radioAND).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(radioOR).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  508 */       .addComponent(clickRemoveFilterFromList, -1, -1, 32767).addComponent(clickAddFilterToList, -1, -1, 32767)).addGap(20, 20, 20)))).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addGap(190, 190, 190).addComponent(jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))).addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jLabel9, -2, 108, -2).addComponent(jScrollPane1, -2, 93, -2)).addContainerGap(68, 32767)));
/*  509 */     panelInternalSetFiltersLayout.setVerticalGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addContainerGap(-1, 32767).addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel9, -2, 14, -2).addComponent(jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInternalSetFiltersLayout.createSequentialGroup().addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(comboFieldList, -2, -1, -2).addComponent(comboCompareOpLsit, -2, -1, -2).addComponent(jLabel7).addComponent(jLabel8).addComponent(valueFilterDataColoumn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(panelInternalSetFiltersLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(clickAddFilterToList).addComponent(clickSetDateFilter).addComponent(radioAND).addComponent(radioOR)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  511 */       .addComponent(clickRemoveFilterFromList)).addComponent(jScrollPane1, -2, 97, -2)).addGap(25, 25, 25)));
/*      */ 
/*  513 */     jTabbedPane2.addTab("Custom Filters", panelInternalSetFilters);
/*      */ 
/*  515 */     GroupLayout panelInteranlCustomSetLayout = new GroupLayout(panelInteranlCustomSet);
/*  516 */     panelInteranlCustomSet.setLayout(panelInteranlCustomSetLayout);
/*  517 */     panelInteranlCustomSetLayout.setHorizontalGroup(panelInteranlCustomSetLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, panelInteranlCustomSetLayout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(jTabbedPane2, -2, 661, -2).addGap(85, 85, 85)));
/*  518 */     panelInteranlCustomSetLayout.setVerticalGroup(panelInteranlCustomSetLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInteranlCustomSetLayout.createSequentialGroup().addContainerGap(-1, 32767).addComponent(jTabbedPane2, -2, 176, -2)));
/*      */ 
/*  520 */     jLabel2.setText("Family:");
/*      */ 
/*  522 */     comboFamilyList.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  525 */         HBaseManagerViewTable.this.comboFamilyListActionPerformed(evt);
/*      */       }
/*      */     });
/*  529 */     comboTableList.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  532 */         HBaseManagerViewTable.this.comboTableListActionPerformed(evt);
/*      */       }
/*      */     });
/*  536 */     labelLoadingData.setText("Loading Table Data ...");
/*      */ 
/*  538 */     clickScan.setText("Scan Now");
/*  539 */     clickScan.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  542 */         HBaseManagerViewTable.this.clickScanActionPerformed(evt);
/*      */       }
/*      */     });
/*  546 */     toggleCustomScan.setText("Customize Scan");
/*  547 */     toggleCustomScan.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  550 */         HBaseManagerViewTable.this.toggleCustomScanActionPerformed(evt);
/*      */       }
/*      */     });
/*  554 */     GroupLayout controlPaneLayout = new GroupLayout(controlPane);
/*  555 */     controlPane.setLayout(controlPaneLayout);
/*  556 */     controlPaneLayout.setHorizontalGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(controlPaneLayout.createSequentialGroup().addContainerGap().addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(controlPaneLayout.createSequentialGroup().addComponent(jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(labelLoadingData).addComponent(comboTableList, -2, 126, -2))).addGroup(controlPaneLayout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(comboFamilyList, -2, 126, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(toggleCustomScan).addComponent(clickScan)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(panelInteranlCustomSet, -2, 674, -2).addContainerGap(86, 32767)));
/*  557 */     controlPaneLayout.setVerticalGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(controlPaneLayout.createSequentialGroup().addContainerGap().addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelInteranlCustomSet, -2, 184, -2).addGroup(controlPaneLayout.createSequentialGroup().addComponent(labelLoadingData).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel1).addComponent(comboTableList, -2, -1, -2).addComponent(toggleCustomScan)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(controlPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(comboFamilyList, -2, -1, -2).addComponent(clickScan)))).addContainerGap(24, 32767)));
/*      */ 
/*  559 */     jPanel4.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*      */ 
/*  561 */     userTable.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
/*  562 */     userTable.setModel(new DefaultTableModel(new Object[0][], 
/*  564 */       new String[0]));
/*      */ 
/*  567 */     jScrollPane2.setViewportView(userTable);
/*      */ 
/*  569 */     clickInsert.setText("Insert");
/*  570 */     clickInsert.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  573 */         HBaseManagerViewTable.this.clickInsertActionPerformed(evt);
/*      */       }
/*      */     });
/*  577 */     clickSave.setText("Save");
/*  578 */     clickSave.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  581 */         HBaseManagerViewTable.this.clickSaveActionPerformed(evt);
/*      */       }
/*      */     });
/*  585 */     clickNEWCQ.setText("New CQ");
/*  586 */     clickNEWCQ.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  589 */         HBaseManagerViewTable.this.clickNEWCQActionPerformed(evt);
/*      */       }
/*      */     });
/*  593 */     clickDelete.setText("Delete");
/*  594 */     clickDelete.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  597 */         HBaseManagerViewTable.this.clickDeleteActionPerformed(evt);
/*      */       }
/*      */     });
/*  601 */     clickRefreshTableData.setText("Refresh");
/*  602 */     clickRefreshTableData.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  605 */         HBaseManagerViewTable.this.clickRefreshTableDataActionPerformed(evt);
/*      */       }
/*      */     });
/*  609 */     clickPaginateBackward.setText("< Previous");
/*  610 */     clickPaginateBackward.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  613 */         HBaseManagerViewTable.this.clickPaginateBackwardActionPerformed(evt);
/*      */       }
/*      */     });
/*  617 */     clickPaginateForward.setText("Next >");
/*  618 */     clickPaginateForward.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  621 */         HBaseManagerViewTable.this.clickPaginateForwardActionPerformed(evt);
/*      */       }
/*      */     });
/*  625 */     rowLimitSpinner.addChangeListener(new ChangeListener()
/*      */     {
/*      */       public void stateChanged(ChangeEvent evt) {
/*  628 */         HBaseManagerViewTable.this.rowLimitSpinnerStateChanged(evt);
/*      */       }
/*      */     });
/*  632 */     jLabel10.setText("Show Rows ");
/*      */ 
/*  634 */     clickConvert.setText("Convert");
/*  635 */     clickConvert.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  638 */         HBaseManagerViewTable.this.clickConvertActionPerformed(evt);
/*      */       }
/*      */     });
/*  642 */     clickVersions.setText("Versions");
/*  643 */     clickVersions.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  646 */         HBaseManagerViewTable.this.clickVersionActionPerformed(evt);
/*      */       }
/*      */     });
/*  650 */     labelTotalRows.setText("Waiting for Scanner");
/*      */ 
/*  652 */     GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
/*  653 */     jPanel4.setLayout(jPanel4Layout);
/*  654 */     jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(jPanel4Layout.createSequentialGroup().addComponent(clickPaginateBackward).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(clickPaginateForward, -2, 75, -2).addGap(37, 37, 37).addComponent(jLabel10).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(rowLimitSpinner, -2, 93, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(clickInsert).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickSave).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickNEWCQ).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickDelete).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickRefreshTableData).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickConvert).addGap(72, 72, 72).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(clickVersions).addGap(72, 72, 72)).addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(jScrollPane2, -2, 1059, -2).addGroup(jPanel4Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(labelTotalRows))).addContainerGap()))));
/*  655 */     jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(clickSave, -2, 23, -2).addComponent(clickInsert).addComponent(clickRefreshTableData, -2, 23, -2).addComponent(clickDelete).addComponent(clickNEWCQ, -2, 23, -2).addComponent(clickPaginateBackward).addComponent(clickPaginateForward).addComponent(rowLimitSpinner, -2, -1, -2).addComponent(jLabel10).addComponent(clickConvert).addComponent(clickVersions)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane2, -1, 500, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(labelTotalRows, -2, 14, -2)));
/*      */ 
/*  657 */     GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
/*      */ 
/*  659 */     jPanel1.setLayout(jPanel1Layout);
/*  660 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(controlPane, -2, -1, -2).addComponent(jPanel4, -2, -1, -2)).addContainerGap(-1, 32767)));
/*  661 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(controlPane, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jPanel4, -1, -1, 32767).addContainerGap()));
/*  662 */     menuConnections.setText("Connection");
/*  663 */     menuNewConnection.setAccelerator(KeyStroke.getKeyStroke(78, 8));
/*  664 */     menuNewConnection.setText("New Connection");
/*  665 */     menuNewConnection.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  668 */         HBaseManagerViewTable.this.menuNewConnectionActionPerformed(evt);
/*      */       }
/*      */     });
/*  671 */     menuConnections.add(menuNewConnection);
/*  672 */     menuExit.setAccelerator(KeyStroke.getKeyStroke(88, 8));
/*  673 */     menuExit.setText("Exit");
/*  674 */     menuExit.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  677 */         HBaseManagerViewTable.this.menuExitActionPerformed(evt);
/*      */       }
/*      */     });
/*  680 */     menuConnections.add(menuExit);
/*  681 */     jMenuBar1.add(menuConnections);
/*  682 */     menuAdministrator.setText("Admin");
/*  683 */     menuOpenTableDesigner.setAccelerator(KeyStroke.getKeyStroke(72, 10));
/*  684 */     menuOpenTableDesigner.setText("HBase Table Designer");
/*  685 */     menuOpenTableDesigner.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  688 */         HBaseManagerViewTable.this.menuOpenTableDesignerActionPerformed(evt);
/*      */       }
/*      */     });
/*  691 */     menuAdministrator.add(menuOpenTableDesigner);
/*  692 */     menuOpenAdmin.setAccelerator(KeyStroke.getKeyStroke(65, 10));
/*  693 */     menuOpenAdmin.setText("HBase Administrator Bench");
/*  694 */     menuOpenAdmin.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  697 */         HBaseManagerViewTable.this.menuOpenAdminActionPerformed(evt);
/*      */       }
/*      */     });
/*  700 */     menuAdministrator.add(menuOpenAdmin);
/*  701 */     jMenuBar1.add(menuAdministrator);
/*  702 */     menuClickBackUp.setText("Backup and Restore");
/*  703 */     jMenuItem1.setText("Backup/Restore");
/*  704 */     jMenuItem1.addActionListener(new ActionListener()
/*      */     {
/*      */       public void actionPerformed(ActionEvent evt) {
/*  707 */         HBaseManagerViewTable.this.jMenuItem1ActionPerformed(evt);
/*      */       }
/*      */     });
/*  710 */     menuClickBackUp.add(jMenuItem1);
/*      */ 
/*  717 */     jMenuBar1.add(menuClickBackUp);
/*  718 */     setJMenuBar(jMenuBar1);
/*  719 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  720 */     getContentPane().setLayout(layout);
/*  721 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(jPanel1, -1, -1, 32767).addGap(0, 10, 32767)));
/*  722 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(jPanel1, -2, -1, 32767).addContainerGap()));
/*  723 */     pack();
/*      */   }
/*      */ 
/*      */   public void clickVersionActionPerformed(ActionEvent evt)
/*      */   {
/*  728 */     String rowKey = (String)userTable.getModel().getValueAt(userTable.getSelectedRow(), 0);
/*  729 */     new HbaseManagerColoumnVersionDetailView(rowKey, (String)comboTableList.getSelectedItem(), (String)comboFamilyList.getSelectedItem()).setVisible(true);
/*      */   }
/*      */ 
/*      */   private void comboTableListActionPerformed(ActionEvent evt)
/*      */   {
/*  735 */     toggleCustomScan.setSelected(false);
/*  736 */     clearTogglePane();
/*  737 */     mannualRange = true;
/*  738 */     AddColoumnList((String)comboTableList.getSelectedItem());
/*  739 */     mannualRange = false;
/*      */ 
/*  741 */     labelLoadingData.setVisible(false);
/*      */   }
/*      */ 
/*      */   private void comboFamilyListActionPerformed(ActionEvent evt)
/*      */   {
/*  746 */     this.coloumnList.clear();
/*  747 */     if ((mannualRange) && (comboFamilyList.getItemCount() > 0))
/*      */     {
/*  750 */       populateColoumnList();
/*      */     }
/*      */ 
/*  753 */     resetPaginators("Family List Changed");
/*  754 */     coloumnTypeList.clear();
/*      */   }
/*      */ 
/*      */   private static void resetPaginators(String origin)
/*      */   {
/*  759 */     System.out.println("\n Restting Paginators by " + origin);
/*      */ 
/*  761 */     paginateBackWardLimits = new ArrayList();
/*  762 */     paginateForwardLimits = new ArrayList();
/*      */ 
/*  767 */     clickPaginateForward.setEnabled(false);
/*  768 */     clickPaginateBackward.setEnabled(false);
/*      */ 
/*  770 */     PRESSED_SCAN_ONCE = false;
/*  771 */     ALLOW_UPDATE_COUNT = true;
/*  772 */     PAGINATED_ALREADY = false;
/*  773 */     PRESSED_BACKWARD_SEEK = false;
/*      */ 
/*  775 */     paginationCount = 0;
/*  776 */     totalRowsBrought = 0L;
/*      */ 
/*  778 */     labelTotalRows.setText("Waiting for Scanner");
/*      */   }
/*      */ 
/*      */   private void clickAddFilterToListActionPerformed(ActionEvent evt)
/*      */   {
/*  783 */     String colname = (String)comboFieldList.getSelectedItem();
/*  784 */     String operation = (String)comboCompareOpLsit.getSelectedItem();
/*  785 */     String val = valueFilterDataColoumn.getText();
/*      */ 
/*  787 */     String[] filterString = new String[3];
/*  788 */     filterString[0] = colname;
/*  789 */     filterString[1] = operation;
/*  790 */     filterString[2] = val;
/*  791 */     filterStrings.put(colname, filterString);
/*      */ 
/*  793 */     String listText = colname + ":" + val;
/*      */ 
/*  795 */     DefaultListModel lmodel = (DefaultListModel)listFiltersSet.getModel();
/*      */ 
/*  797 */     lmodel.addElement(listText);
/*  798 */     FILTER_SET = true;
/*      */ 
/*  800 */     resetPaginators("Filter Added");
/*      */   }
/*      */ 
/*      */   private void clickSaveActionPerformed(ActionEvent evt)
/*      */   {
/*  808 */     int[] selectedRow = userTable.getSelectedRows();
/*  809 */     String rowKey = null;
/*  810 */     String[][] tableData = null;
/*  811 */     for (int i = 0; i < selectedRow.length; i++) {
/*  812 */       rowKey = (String)userTable.getValueAt(selectedRow[i], 0);
/*  813 */       if (StringUtils.isNotEmpty(rowKey)) {
/*  814 */         tableData = getRowData(selectedRow[i]);
/*      */ 
/*  816 */         String tableName = (String)comboTableList.getSelectedItem();
/*  817 */         String tableFamily = (String)comboFamilyList.getSelectedItem();
/*      */ 
/*  819 */         HBaseTableManager.insertRowToDb(tableData, tableFamily, tableName);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clickInsertActionPerformed(ActionEvent evt)
/*      */   {
/*  828 */     int pos = userTable.getRowCount();
/*      */ 
/*  832 */     DefaultTableModel tmodel = (DefaultTableModel)userTable.getModel();
/*  833 */     if (pos <= 0)
/*      */     {
/*  835 */       pos = 0;
/*      */ 
/*  838 */       this.coloumnList.clear();
/*  839 */       tmodel.getDataVector().removeAllElements();
/*  840 */       userTable.removeAll();
/*      */ 
/*  842 */       tmodel.addColumn("Row Key");
/*  843 */       this.coloumnList.add("Row Key");
/*  844 */       userTable.getColumnModel().getColumn(0).setMinWidth(400);
/*      */ 
/*  846 */       clickNEWCQActionPerformed(null);
/*      */     }
/*      */ 
/*  850 */     String[] x = new String[0];
/*  851 */     tmodel.insertRow(pos, x);
/*      */ 
/*  853 */     userTable.setAutoscrolls(true);
/*  854 */     userTable.setRowSelectionInterval(pos, pos);
/*  855 */     userTable.setRowSelectionAllowed(true);
/*      */   }
/*      */ 
/*      */   private void clickRefreshTableDataActionPerformed(ActionEvent evt)
/*      */   {
/*  860 */     addTableList();
/*      */   }
/*      */ 
/*      */   private void clickDeleteActionPerformed(ActionEvent evt)
/*      */   {
/*  867 */     int[] selectedRow = userTable.getSelectedRows();
/*      */ 
/*  872 */     DefaultTableModel tm = (DefaultTableModel)userTable.getModel();
/*      */ 
/*  874 */     String rowKey = null;
/*  875 */     int row = 0;
/*      */ 
/*  877 */     for (int i = 0; i < selectedRow.length; i++) {
/*  878 */       row = userTable.getSelectedRow();
/*  879 */       rowKey = (String)userTable.getValueAt(row, 0);
/*  880 */       deleteHbaseRow(rowKey);
/*  881 */       tm.removeRow(row);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void clickNEWCQActionPerformed(ActionEvent evt)
/*      */   {
/*  889 */     DefaultTableModel tm = (DefaultTableModel)userTable.getModel();
/*  890 */     String str = JOptionPane.showInputDialog(null, "New Coloumn Qualifier Name : ", "HbaseManager : VG", 1);
/*  891 */     tm.addColumn(str);
/*  892 */     this.coloumnList.add(str);
/*      */   }
/*      */ 
/*      */   private void toggleCustomScanActionPerformed(ActionEvent evt)
/*      */   {
/*  898 */     AbstractButton abstractButton = (AbstractButton)evt.getSource();
/*  899 */     boolean selected = abstractButton.getModel().isSelected();
/*      */ 
/*  901 */     if (selected) {
/*  902 */       panelInteranlCustomSet.setVisible(true);
/*  903 */       mannualRange = true;
/*      */ 
/*  905 */       populateColoumnList();
/*      */     }
/*      */     else
/*      */     {
/*  909 */       clearTogglePane();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void clearTogglePane()
/*      */   {
/*  915 */     comboFieldList.removeAllItems();
/*  916 */     valueStartRow.setText("0");
/*  917 */     valueStopRow.setText("zzz");
/*  918 */     valueFilterDataColoumn.setText("");
/*  919 */     DefaultListModel lm = (DefaultListModel)listFiltersSet.getModel();
/*  920 */     lm.clear();
/*  921 */     filterStrings.clear();
/*  922 */     panelInteranlCustomSet.setVisible(false);
/*  923 */     mannualRange = false;
/*      */   }
/*      */ 
/*      */   private void clickScanActionPerformed(ActionEvent evt)
/*      */   {
/*  928 */     DefaultTableModel tableModel = (DefaultTableModel)userTable.getModel();
/*      */ 
/*  931 */     String firstRow = "0";
/*  932 */     FilterList newFilterList = null;
/*  933 */     String dateFilterMessage = "";
/*      */ 
/*  935 */     if (isDateFilterSet()) {
/*  936 */       dateFilterMessage = " (With Active Date Filter) ";
/*      */     }
/*      */     else {
/*  939 */       dateFilterMessage = " ";
/*      */     }
/*      */ 
/*  942 */     System.out.print("Clearing Table");
/*  943 */     setTitle("HBase Manager: Action- Scan Table: " + comboTableList.getSelectedItem() + " Family: " + comboFamilyList.getSelectedItem());
/*  944 */     this.coloumnList.clear();
/*  945 */     this.coloumnMap.clear();
/*      */ 
/*  947 */     this.currentRowLimit = ((Integer)rowLimitSpinner.getValue()).intValue();
/*      */ 
/*  949 */     if (this.currentRowLimit != CURRENT_SPINNER_COUNT) {
/*  950 */       resetPaginators("Spinner Value Mannual Change");
/*      */ 
/*  952 */       if (mannualRange) {
/*  953 */         this.CURRENT_START_RANGE = valueStartRow.getText();
/*  954 */         this.CURRENT_STOP_RANGE = valueStopRow.getText();
/*      */       }
/*      */       else {
/*  957 */         this.CURRENT_START_RANGE = "0";
/*  958 */         this.CURRENT_STOP_RANGE = "zz";
/*      */       }
/*      */ 
/*  961 */       CURRENT_SPINNER_COUNT = this.currentRowLimit;
/*      */     }
/*      */ 
/*  965 */     if (!PAGINATED_ALREADY)
/*      */     {
/*  967 */       if (mannualRange)
/*      */       {
/*  969 */         this.CURRENT_START_RANGE = valueStartRow.getText();
/*  970 */         this.CURRENT_STOP_RANGE = valueStopRow.getText();
/*      */ 
/*  972 */         if (StringUtils.isEmpty(this.CURRENT_START_RANGE)) {
/*  973 */           this.CURRENT_START_RANGE = "0";
/*      */         }
/*  975 */         else if (StringUtils.isEmpty(this.CURRENT_STOP_RANGE)) {
/*  976 */           this.CURRENT_STOP_RANGE = "zz";
/*      */         }
/*      */ 
/*  979 */         CURRENT_SPINNER_COUNT = this.currentRowLimit;
/*      */ 
/*  981 */         if (FILTER_SET) {
/*  982 */           System.out.println("Filter Set, Getting all Filterds");
/*  983 */           newFilterList = obtainFilterList();
/*  984 */           System.out.print("Filters; " + newFilterList.getFilters().isEmpty());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  990 */         this.CURRENT_START_RANGE = "0";
/*  991 */         this.CURRENT_STOP_RANGE = "zzz";
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  996 */     String colFamily = (String)comboFamilyList.getSelectedItem();
/*  997 */     String tblName = (String)comboTableList.getSelectedItem();
/*  998 */     if (newFilterList == null) {
/*  999 */       System.out.print("\nNo Filters Applied");
/* 1000 */       newFilterList = new FilterList();
/*      */     }
/*      */ 
/* 1003 */     System.out.println("\nStart@:" + this.CURRENT_START_RANGE + "\n" + "Stop @: " + this.CURRENT_STOP_RANGE + "\nRow Limit @: " + this.currentRowLimit);
/* 1004 */     System.out.println("\nPagination Count :" + paginationCount + "\nPaginatorSize :" + paginateBackWardLimits.size() + " , " + paginateForwardLimits.size());
/*      */ 
/* 1006 */     ResultScanner resultScan = HBaseTableManager.getList(this.CURRENT_START_RANGE, this.CURRENT_STOP_RANGE, Bytes.toBytes(colFamily), null, this.currentRowLimit, newFilterList, tblName);
/*      */ 
/* 1008 */     int row = 0;
/*      */ 
/* 1010 */     tableModel.getDataVector().removeAllElements();
/* 1011 */     userTable.removeAll();
/*      */ 
/* 1013 */     boolean readFirstRow = false;
/*      */ 
/* 1015 */     if (resultScan == null) {
/* 1016 */       return;
/*      */     }
/* 1018 */     for (Result resultObj : resultScan)
/*      */     {
/* 1021 */       firstRow = Bytes.toString(resultObj.getRow());
/* 1022 */       if (!readFirstRow)
/*      */       {
/* 1024 */         readFirstRow = true;
/* 1025 */         paginateBackWardLimits.add(firstRow);
/*      */ 
/* 1027 */         System.out.println("Added Backward Index : " + firstRow);
/*      */       }
/*      */ 
/* 1031 */       TableRowObject rowOb = new TableRowObject(resultObj, colFamily, false);
/* 1032 */       Object[] tableRowData = rowOb.getAllRowInfo();
/*      */ 
/* 1034 */       if (tableRowData != null) {
/* 1035 */         String[] colData = (String[])tableRowData[0];
/*      */ 
/* 1037 */         for (int i = 0; i < colData.length; i++)
/*      */         {
/* 1039 */           if (!this.coloumnList.contains(colData[i])) {
/* 1040 */             this.coloumnList.add(colData[i]);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1045 */         String[] newData = new String[this.coloumnList.size()];
/* 1046 */         for (int j = 0; j < this.coloumnList.size(); j++) {
/* 1047 */           newData[j] = ((String)this.coloumnList.get(j));
/*      */         }
/* 1049 */         tableModel.setColumnIdentifiers(newData);
/* 1050 */         updateColoumnNameMap(tableModel);
/* 1051 */         String[][] tblData = (String[][])tableRowData[1];
/* 1052 */         tableModel.insertRow(row++, new String[this.coloumnList.size()]);
/* 1053 */         setRowData(row - 1, tblData, tableModel);
/* 1054 */         userTable.getColumnModel().getColumn(0).setMinWidth(400);
/* 1055 */         userTable.setShowVerticalLines(true);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1061 */     paginateForwardLimits.add(firstRow);
/* 1062 */     System.out.println("Added Forward Index : " + firstRow);
/* 1063 */     System.out.println("Rendered @:" + tableModel.getRowCount() + " Rows , Limit was to @: " + this.currentRowLimit);
/*      */ 
/* 1065 */     if (tableModel.getRowCount() < this.currentRowLimit) {
/* 1066 */       System.out.println("Pagination Disabled");
/*      */ 
/* 1068 */       clickPaginateForward.setEnabled(false);
/*      */ 
/* 1070 */       if (!PAGINATED_ALREADY) {
/* 1071 */         totalRowsBrought = tableModel.getRowCount();
/* 1072 */         clickPaginateBackward.setEnabled(false);
/*      */       }
/*      */       else
/*      */       {
/* 1076 */         if (ALLOW_UPDATE_COUNT) {
/* 1077 */           totalRowsBrought += tableModel.getRowCount();
/*      */         }
/* 1079 */         clickPaginateBackward.setEnabled(true);
/*      */       }
/* 1081 */       ALLOW_UPDATE_COUNT = false;
/* 1082 */       paginationCount -= 1;
/*      */     }
/*      */     else
/*      */     {
/* 1087 */       System.out.println("Pagination Enabled");
/* 1088 */       clickPaginateForward.setEnabled(true);
/*      */     }
/*      */ 
/* 1092 */     if (paginationCount < 0)
/*      */     {
/* 1095 */       clickPaginateBackward.setEnabled(false);
/*      */ 
/* 1097 */       if (PAGINATED_ALREADY) {
/* 1098 */         System.out.println("No more Previous records, Restting paginators and Limits");
/*      */ 
/* 1100 */         clickPaginateForward.setEnabled(true);
/* 1101 */         paginationCount = -1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1106 */     if ((!PRESSED_SCAN_ONCE) && (ALLOW_UPDATE_COUNT)) {
/* 1107 */       PRESSED_SCAN_ONCE = true;
/* 1108 */       totalRowsBrought = tableModel.getRowCount();
/*      */     }
/* 1112 */     else if ((!PRESSED_BACKWARD_SEEK) && (ALLOW_UPDATE_COUNT)) {
/* 1113 */       totalRowsBrought += tableModel.getRowCount();
/*      */     }
/* 1115 */     this.currentRowLimit = ((Integer)rowLimitSpinner.getValue()).intValue();
/*      */ 
/* 1117 */     labelTotalRows.setText("Retrieved " + totalRowsBrought + " rows of " + comboTableList.getSelectedItem() + dateFilterMessage);
/*      */ 
/* 1119 */     if (tableModel.getRowCount() > 0)
/*      */     {
/* 1121 */       userTable.getColumnModel().getColumn(0).setMinWidth(400);
/* 1122 */       userTable.setShowVerticalLines(true);
/*      */     }
/*      */     else
/*      */     {
/* 1128 */       labelLoadingData.setText("Table Empty. No rows to fetch".concat(dateFilterMessage));
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean setRowData(int row, String[][] tblData, DefaultTableModel tableModel)
/*      */   {
/* 1136 */     for (String[] keyval : tblData)
/*      */     {
/* 1138 */       if (StringUtils.isNotEmpty(keyval[1]))
/*      */       {
/* 1140 */         int cIndex = ((Integer)this.coloumnMap.get(keyval[1])).intValue();
/* 1141 */         tableModel.setValueAt(keyval[0], row, cIndex);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1146 */     return true;
/*      */   }
/*      */ 
/*      */   private void updateColoumnNameMap(DefaultTableModel tableModel)
/*      */   {
/* 1151 */     for (int i = 0; i < tableModel.getColumnCount(); i++)
/*      */     {
/* 1153 */       String colName = tableModel.getColumnName(i);
/* 1154 */       this.coloumnMap.put(colName, Integer.valueOf(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void comboFieldListActionPerformed(ActionEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void radioANDActionPerformed(ActionEvent evt)
/*      */   {
/* 1165 */     radioOR.setSelected(false);
/* 1166 */     this.PASS_ALL = true;
/*      */   }
/*      */ 
/*      */   private void radioORActionPerformed(ActionEvent evt)
/*      */   {
/* 1171 */     radioAND.setSelected(false);
/* 1172 */     this.PASS_ALL = false;
/*      */   }
/*      */ 
/*      */   private void clickRemoveFilterFromListActionPerformed(ActionEvent evt)
/*      */   {
/* 1177 */     DefaultListModel lmodel = (DefaultListModel)listFiltersSet.getModel();
/* 1178 */     String selVal = (String)listFiltersSet.getSelectedValue();
/* 1179 */     selVal = selVal.split(":")[0];
/*      */ 
/* 1181 */     filterStrings.remove(selVal);
/*      */ 
/* 1183 */     resetPaginators("Filter Removed");
/* 1184 */     lmodel.remove(listFiltersSet.getSelectedIndex());
/* 1185 */     if ((lmodel.isEmpty()) && (!dateFilterSet))
/* 1186 */       FILTER_SET = false;
/*      */   }
/*      */ 
/*      */   private void menuExitActionPerformed(ActionEvent evt)
/*      */   {
/* 1193 */     dispose();
/*      */   }
/*      */ 
/*      */   private void menuNewConnectionActionPerformed(ActionEvent evt)
/*      */   {
/*      */     try {
/* 1199 */       comboFamilyList.removeAll();
/* 1200 */       comboTableList.removeAllItems();
/*      */     }
/*      */     catch (Exception e) {
/* 1203 */       System.out.println("Event Bubbling Stopped");
/*      */     }
/* 1205 */     this.newConnectionDialog = new HBaseManagerNewConnection(this, true);
/* 1206 */     this.newConnectionDialog.setLocationRelativeTo(this);
/* 1207 */     this.newConnectionDialog.setVisible(true);
/* 1208 */     initUI();
/*      */   }
/*      */ 
/*      */   private void jMenuItem1ActionPerformed(ActionEvent evt)
/*      */   {
/* 1214 */     this.dataBackupDialog = new HbaseDataBackupRestoreDialog(this, true);
/* 1215 */     this.dataBackupDialog.setLocationRelativeTo(this);
/* 1216 */     this.dataBackupDialog.setVisible(true);
/*      */   }
/*      */ 
/*      */   private void menuOpenTableDesignerActionPerformed(ActionEvent evt)
/*      */   {
/* 1221 */     this.newTableDesign = new HBaseManagerTableDesign(this, true);
/* 1222 */     this.newTableDesign.setLocationRelativeTo(this);
/* 1223 */     this.newTableDesign.setVisible(true);
/* 1224 */     initUI();
/*      */   }
/*      */ 
/*      */   private void menuOpenAdminActionPerformed(ActionEvent evt)
/*      */   {
/* 1229 */     this.adminBenchDialog = new HBaseManagerAdminPanel(this, true);
/* 1230 */     this.adminBenchDialog.setLocationRelativeTo(this);
/* 1231 */     this.adminBenchDialog.setVisible(true);
/* 1232 */     initUI();
/*      */   }
/*      */ 
/*      */   private void clickPaginateBackwardActionPerformed(ActionEvent evt)
/*      */   {
/* 1237 */     clickPaginateForward.setEnabled(true);
/*      */ 
/* 1239 */     PRESSED_BACKWARD_SEEK = true;
/* 1240 */     PAGINATED_ALREADY = true;
/* 1241 */     this.CURRENT_START_RANGE = ((String)paginateBackWardLimits.get(paginationCount));
/*      */ 
/* 1243 */     if (!mannualRange)
/* 1244 */       this.CURRENT_STOP_RANGE = "zzz";
/*      */     else
/* 1246 */       this.CURRENT_STOP_RANGE = valueStopRow.getText();
/* 1247 */     paginationCount -= 1;
/*      */ 
/* 1249 */     System.out.println("Setting Forward: Start @ :" + this.CURRENT_START_RANGE + " & " + "Stop @: " + this.CURRENT_STOP_RANGE);
/* 1250 */     clickScanActionPerformed(evt);
/*      */   }
/*      */ 
/*      */   private void clickPaginateForwardActionPerformed(ActionEvent evt)
/*      */   {
/* 1256 */     if (paginationCount == -1) {
/* 1257 */       paginationCount = 0;
/*      */ 
/* 1259 */       PAGINATED_ALREADY = true;
/*      */ 
/* 1261 */       this.CURRENT_START_RANGE = ((String)paginateForwardLimits.get(0));
/* 1262 */       if (!mannualRange)
/* 1263 */         this.CURRENT_STOP_RANGE = "zzz";
/*      */       else {
/* 1265 */         this.CURRENT_STOP_RANGE = valueStopRow.getText();
/*      */       }
/* 1267 */       System.out.println("Setting Forward: Start @ :" + this.CURRENT_START_RANGE + " & " + "Stop @: " + this.CURRENT_STOP_RANGE);
/*      */ 
/* 1269 */       clickScanActionPerformed(evt);
/* 1270 */       return;
/*      */     }
/* 1272 */     PAGINATED_ALREADY = true;
/* 1273 */     clickPaginateBackward.setEnabled(true);
/* 1274 */     this.CURRENT_START_RANGE = ((String)paginateForwardLimits.get(paginationCount));
/* 1275 */     if (!mannualRange)
/* 1276 */       this.CURRENT_STOP_RANGE = "zzz";
/*      */     else
/* 1278 */       this.CURRENT_STOP_RANGE = valueStopRow.getText();
/* 1279 */     paginationCount += 1;
/* 1280 */     System.out.println("Setting Forward: Start @ :" + this.CURRENT_START_RANGE + " & " + "Stop @: " + this.CURRENT_STOP_RANGE);
/* 1281 */     clickScanActionPerformed(evt);
/*      */   }
/*      */ 
/*      */   private void valueStartRowActionPerformed(ActionEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void valueStartRowInputMethodTextChanged(InputMethodEvent evt)
/*      */   {
/*      */   }
/*      */ 
/*      */   private void rowLimitSpinnerStateChanged(ChangeEvent evt)
/*      */   {
/* 1295 */     resetPaginators("Spinner Limit Changed");
/*      */   }
/*      */ 
/*      */   private void clickConvertActionPerformed(ActionEvent evt)
/*      */   {
/* 1300 */     ConvertColoumnsDialog cdialog = new ConvertColoumnsDialog(this, true);
/* 1301 */     cdialog.setLocationRelativeTo(this);
/*      */ 
/* 1303 */     cdialog.setVisible(true);
/*      */   }
/*      */ 
/*      */   public void convertColoumnstoUserData(HashMap<String, String> actionMap)
/*      */   {
/* 1309 */     int noColoumnstoConvert = actionMap.size();
/* 1310 */     System.out.println("Need to Convert " + noColoumnstoConvert + "to User Types");
/* 1311 */     String[] keyList = new String[noColoumnstoConvert];
/* 1312 */     actionMap.keySet().toArray(keyList);
/* 1313 */     DefaultTableModel tableModel = (DefaultTableModel)userTable.getModel();
/* 1314 */     int colIndex = -1;
/* 1315 */     int avRows = 0;
/*      */ 
/* 1321 */     List reloadType = new ArrayList();
/* 1322 */     reloadType.add("INTEGER");
/* 1323 */     reloadType.add("DOUBLE");
/* 1324 */     reloadType.add("LONG");
/* 1325 */     reloadType.add("SHORT");
/* 1326 */     reloadType.add("BOOLEAN");
/*      */     try
/*      */     {
/* 1330 */       for (int i = 0; i < noColoumnstoConvert; i++)
/*      */       {
/* 1332 */         colIndex = this.currentColoumnList.indexOf(keyList[i]);
/* 1333 */         String action = (String)actionMap.get(keyList[i]);
/* 1334 */         coloumnTypeList.put(keyList[i], action);
/* 1335 */         avRows = userTable.getRowCount();
/* 1336 */         coloumnTypeList.put(keyList[i], action);
/* 1337 */         if (reloadType.contains(action))
/*      */         {
/* 1339 */           clickScanActionPerformed(null);
/*      */         }
/*      */         else
/*      */         {
/* 1343 */           for (int j = 0; j < avRows; j++) {
/* 1344 */             String currentVal = (String)userTable.getValueAt(j, colIndex);
/* 1345 */             String convertedVal = getConvertedValue(currentVal, action);
/* 1346 */             tableModel.setValueAt(convertedVal, j, colIndex);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/* 1362 */     System.out.println("Ending session. Killing all active connections");
/*      */ 
/* 1364 */     HBaseTableManager.shutdownAliveConnection();
/*      */ 
/* 1366 */     System.exit(0);
/*      */   }
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*      */     try
/*      */     {
/* 1387 */       for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
/* 1388 */         if ("Nimbus".equals(info.getName())) {
/* 1389 */           UIManager.setLookAndFeel(info.getClassName());
/* 1390 */           break;
/*      */         }
/*      */     }
/*      */     catch (ClassNotFoundException ex)
/*      */     {
/* 1395 */       Logger.getLogger(HBaseManagerViewTable.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */     catch (InstantiationException ex) {
/* 1398 */       Logger.getLogger(HBaseManagerViewTable.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */     catch (IllegalAccessException ex) {
/* 1401 */       Logger.getLogger(HBaseManagerViewTable.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */     catch (UnsupportedLookAndFeelException ex) {
/* 1404 */       Logger.getLogger(HBaseManagerViewTable.class.getName()).log(Level.SEVERE, null, ex);
/*      */     }
/*      */ 
/* 1411 */     EventQueue.invokeLater(new Runnable()
/*      */     {
/*      */       public void run()
/*      */       {
/* 1415 */         new HBaseManagerViewTable().setVisible(true);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void populateColoumnList()
/*      */   {
/* 1492 */     comboFieldList.removeAllItems();
/* 1493 */     listFiltersSet.removeAll();
/* 1494 */     filterStrings.clear();
/*      */ 
/* 1496 */     String colFamily = (String)comboFamilyList.getSelectedItem();
/* 1497 */     String tblName = (String)comboTableList.getSelectedItem();
/*      */ 
/* 1499 */     ResultScanner resultScan = HBaseTableManager.getList("0", "zzz", Bytes.toBytes(colFamily), null, this.currentRowLimit, new FilterList(), tblName);
/*      */     String[] colData;
/*      */     int i;
/* 1508 */     /*for (Iterator localIterator = resultScan.iterator(); localIterator.hasNext(); 
 1518        i < colData.length)*/
	 for (Iterator localIterator = resultScan.iterator(); localIterator.hasNext();)
/*      */     {
/* 1508 */       Result resultObj = (Result)localIterator.next();
/*      */ 
/* 1511 */       if (resultObj.isEmpty()) {
/*      */         break;
/*      */       }
/* 1514 */       HBaseTableManager.getDataFiller(resultObj, colFamily);
/*      */ 
/* 1516 */       colData = HBaseTableManager.getColumnQualifiers();
/*      */ 
/* 1518 */       i = 0; //continue;//syxiong
/*      */ 
/* 1520 */       if (!this.coloumnList.contains(colData[i]))
/* 1521 */         this.coloumnList.add(colData[i]);
/* 1518 */       i++;
/*      */     }
/*      */ 
/* 1528 */     for (int ii = 1; ii < this.coloumnList.size(); ii++)
/* 1529 */       comboFieldList.addItem(this.coloumnList.get(ii));
/*      */   }
/*      */ 
/*      */   private FilterList obtainFilterList()
/*      */   {
/* 1536 */     FilterList filters = null;
/* 1537 */     if (this.PASS_ALL)
/*      */     {
/* 1539 */       filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
/* 1540 */       System.out.print("Init:  AND filter");
/*      */     }
/*      */     else {
/* 1543 */       filters = new FilterList();
/* 1544 */       System.out.print("Init:  OR filter");
/*      */     }
/*      */ 
/* 1548 */     String[] vals = new String[3];
/*      */ 
/* 1550 */     byte[] family = Bytes.toBytes((String)comboFamilyList.getSelectedItem());
/*      */ 
/* 1552 */     SingleColumnValueFilter valFilter = null;
/* 1553 */     SingleColumnValueFilter valFilter1 = null;
/*      */ 
/* 1572 */     System.out.print("ListSize :" + listFiltersSet.getModel().getSize());
/* 1573 */     for (int i = 0; i < listFiltersSet.getModel().getSize(); i++) {
/* 1574 */       String val = (String)listFiltersSet.getModel().getElementAt(i);
/* 1575 */       val = val.split(":")[0];
/* 1576 */       vals = (String[])filterStrings.get(val);
/*      */ 
/* 1578 */       System.out.println("Filter String:" + val + "Searching:" + vals[1] + " for :" + vals[2]);
/*      */ 
/* 1581 */       if (vals[1].equals("EQUAL"))
/*      */       {
/* 1583 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.EQUAL, Bytes.toBytes(vals[2]));
/* 1584 */         valFilter.setFilterIfMissing(true);
/* 1585 */         filters.addFilter(valFilter);
/* 1586 */         System.out.println("Appending Filter EQUAL");
/*      */       }
/* 1589 */       else if (vals[1].equals("NOT EQUAL"))
/*      */       {
/* 1591 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.NOT_EQUAL, Bytes.toBytes(vals[2]));
/* 1592 */         valFilter.setFilterIfMissing(true);
/* 1593 */         filters.addFilter(valFilter);
/* 1594 */         System.out.println("Appending Filter NOT EQUAL");
/*      */       }
/* 1597 */       else if (vals[1].equals("GREATER")) {
/* 1598 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.GREATER, Bytes.toBytes(vals[2]));
/* 1599 */         filters.addFilter(valFilter);
/* 1600 */         System.out.println("Appending Filter GREATER");
/*      */       }
/* 1603 */       else if (vals[1].equals("LESS")) {
/* 1604 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.LESS, Bytes.toBytes(vals[2]));
/* 1605 */         filters.addFilter(valFilter);
/* 1606 */         System.out.println("Appending Filter LESS");
/*      */       }
/* 1609 */       else if (vals[1].equals("GREATER OR EQUAL")) {
/* 1610 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(vals[2]));
/* 1611 */         filters.addFilter(valFilter);
/* 1612 */         System.out.println("Appending Filter GREATER OR EQUAL");
/*      */       }
/*      */ 
/* 1615 */       if (vals[1].equals("LESSER OR EQUAL")) {
/* 1616 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(vals[2]));
/* 1617 */         filters.addFilter(valFilter);
/* 1618 */         System.out.println("Appending Filter LESSER OR EQUAL");
/*      */       }
/* 1621 */       else if (vals[1].equals("NOT OP")) {
/* 1622 */         valFilter = new SingleColumnValueFilter(family, Bytes.toBytes(vals[0]), CompareOp.NO_OP, Bytes.toBytes(vals[2]));
/* 1623 */         filters.addFilter(valFilter);
/* 1624 */         System.out.println("Appending Filter NOT OP");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1633 */     return filters;
/*      */   }
/*      */ 
/*      */   public static void disableControls()
/*      */   {
/* 1639 */     comboFamilyList.setEnabled(false);
/* 1640 */     comboTableList.setEnabled(false);
/* 1641 */     clickDelete.setEnabled(false);
/* 1642 */     clickInsert.setEnabled(false);
/* 1643 */     clickNEWCQ.setEnabled(false);
/* 1644 */     clickRefreshTableData.setEnabled(false);
/* 1645 */     clickSave.setEnabled(false);
/* 1646 */     clickScan.setEnabled(false);
/* 1647 */     toggleCustomScan.setEnabled(false);
/* 1648 */     panelInteranlCustomSet.setVisible(false);
/* 1649 */     userTable.setEnabled(false);
/*      */ 
/* 1651 */     clickPaginateBackward.setEnabled(false);
/* 1652 */     clickPaginateForward.setEnabled(false);
/* 1653 */     clickConvert.setEnabled(false);
/* 1654 */     clickVersions.setEnabled(false);
/*      */ 
/* 1656 */     menuAdministrator.setEnabled(false);
/* 1657 */     menuClickBackUp.setEnabled(false);
/*      */   }
/*      */ 
/*      */   public static void enableControls()
/*      */   {
/* 1663 */     comboFamilyList.setEnabled(true);
/* 1664 */     comboTableList.setEnabled(true);
/* 1665 */     clickDelete.setEnabled(true);
/* 1666 */     clickInsert.setEnabled(true);
/* 1667 */     clickNEWCQ.setEnabled(true);
/* 1668 */     clickRefreshTableData.setEnabled(true);
/* 1669 */     clickSave.setEnabled(true);
/* 1670 */     clickScan.setEnabled(true);
/* 1671 */     toggleCustomScan.setEnabled(true);
/* 1672 */     panelInteranlCustomSet.setVisible(true);
/* 1673 */     userTable.setEnabled(true);
/*      */ 
/* 1675 */     clickPaginateBackward.setEnabled(true);
/* 1676 */     clickPaginateForward.setEnabled(true);
/* 1677 */     clickConvert.setEnabled(true);
/* 1678 */     clickVersions.setEnabled(true);
/*      */ 
/* 1680 */     menuAdministrator.setEnabled(true);
/* 1681 */     menuClickBackUp.setEnabled(true);
/*      */   }
/*      */ 
/*      */   private String getConvertedValue(String currentVal, String action)
/*      */   {
/* 1688 */     byte[] value = Bytes.toBytes(currentVal);
/*      */ 
/* 1690 */     String convertedValue = "";
/* 1691 */     if (action.equals("DATE"))
/*      */     {
/* 1693 */       convertedValue = new Date(Long.parseLong(currentVal)).toLocaleString();
/*      */     }
/* 1696 */     else if (action.equals("SHORT"))
/*      */     {
/* 1698 */       convertedValue = String.valueOf(Bytes.toShort(value));
/*      */     }
/* 1701 */     else if (action.equals("LONG")) {
/* 1702 */       convertedValue = String.valueOf(Bytes.toLong(value));
/*      */     }
/* 1704 */     else if (action.equals("BOOLEAN")) {
/* 1705 */       convertedValue = String.valueOf(Bytes.toBoolean(value));
/*      */     }
/* 1707 */     else if (action.equals("INTEGER")) {
/* 1708 */       convertedValue = String.valueOf(Bytes.toInt(value));
/*      */     }
/* 1711 */     else if (action.equals("DOUBLE")) {
/* 1712 */       convertedValue = String.valueOf(Bytes.toDouble(value));
/*      */     }
/* 1715 */     else if (action.equals("TIMESTAMP")) {
/* 1716 */       Long ts = Long.valueOf(new Date(currentVal).getTime());
/* 1717 */       convertedValue = String.valueOf(ts);
/*      */     }
/*      */ 
/* 1721 */     return convertedValue;
/*      */   }
/*      */ 
/*      */   public static long getFilterDateFrom()
/*      */   {
/* 1726 */     return filterDateFrom;
/*      */   }
/*      */ 
/*      */   public static void setFilterDateFrom(long filterDateFrom)
/*      */   {
/* 1731 */     filterDateFrom = filterDateFrom;
/* 1732 */     dateFilterSet = true;
/* 1733 */     clickSetDateFilter.setForeground(Color.RED);
/* 1734 */     setDateFilterOnCq((String)comboFieldList.getSelectedItem());
/* 1735 */     clickSetDateFilter.setBackground(Color.BLACK);
/* 1736 */     clickSetDateFilter.setText("Date Filter Active!");
/*      */ 
/* 1739 */     FILTER_SET = true;
/*      */   }
/*      */ 
/*      */   public static long getFilterDateTo()
/*      */   {
/* 1744 */     return filterDateTo;
/*      */   }
/*      */ 
/*      */   public static void setFilterDateTo(long filterDateTo)
/*      */   {
/* 1750 */     filterDateTo = filterDateTo;
/* 1751 */     dateFilterSet = true;
/* 1752 */     setDateFilterOnCq((String)comboFieldList.getSelectedItem());
/*      */ 
/* 1754 */     clickSetDateFilter.setForeground(Color.RED);
/* 1755 */     clickSetDateFilter.setBackground(Color.BLACK);
/* 1756 */     clickSetDateFilter.setText("Date Filter Active!");
/*      */ 
/* 1759 */     FILTER_SET = true;
/*      */   }
/*      */ 
/*      */   private void clickSetDateFilterActionPerformed(ActionEvent evt)
/*      */   {
/* 1765 */     if (dateFilterSet)
/*      */     {
/* 1767 */       int confirm = JOptionPane.showConfirmDialog(this, "Deactivate Date Filter?");
/* 1768 */       if (confirm == 0) {
/* 1769 */         dateFilterSet = false;
/* 1770 */         filterDateFrom = 0L;
/* 1771 */         filterDateTo = 0L;
/* 1772 */         clickSetDateFilter.setForeground(Color.BLACK);
/* 1773 */         clickSetDateFilter.setBackground(buttonDefault);
/* 1774 */         clickSetDateFilter.setText("Set Date Filter");
/* 1775 */         setDateFilterOnCq("");
/* 1776 */         JOptionPane.showMessageDialog(this, "Date Filter Deactivated", "Information", 1);
/*      */       }
/*      */       else {
/* 1779 */         JOptionPane.showMessageDialog(this, "Date Filter Still Active", "Warning", 2);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1784 */       JOptionPane.showMessageDialog(this, "Make sure the selected field has long values, else the filter wont work", "Warning", 2);
/* 1785 */       this.dateFilterDialog.setVisible(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String getDateFilterOnCq()
/*      */   {
/* 1791 */     return dateFilterOnCq;
/*      */   }
/*      */ 
/*      */   public static void setDateFilterOnCq(String dateFilterOnCq)
/*      */   {
/* 1796 */     dateFilterOnCq = dateFilterOnCq;
/*      */   }
/*      */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.ui.HBaseManagerViewTable
 * JD-Core Version:    0.6.2
 */
