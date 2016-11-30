/*     */ package com.vg.hbase.comm.manager;
/*     */ 
/*     */ import com.vg.hbase.manager.ui.HBaseManagerViewTable;
/*     */ import com.vg.hbase.operations.base.HBaseConfigurationManager;
/*     */ import com.vg.hbase.operations.base.HbaseManagerStatic;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.exception.ExceptionUtils;
/*     */ import org.apache.hadoop.hbase.HColumnDescriptor;
/*     */ import org.apache.hadoop.hbase.HTableDescriptor;
/*     */ import org.apache.hadoop.hbase.KeyValue;
/*     */ import org.apache.hadoop.hbase.client.Delete;
/*     */ import org.apache.hadoop.hbase.client.HBaseAdmin;
/*     */ import org.apache.hadoop.hbase.client.HConnectionManager;
/*     */ import org.apache.hadoop.hbase.client.HTable;
/*     */ import org.apache.hadoop.hbase.client.Put;
/*     */ import org.apache.hadoop.hbase.client.Result;
/*     */ import org.apache.hadoop.hbase.client.ResultScanner;
/*     */ import org.apache.hadoop.hbase.client.Scan;
/*     */ import org.apache.hadoop.hbase.filter.FilterList;
/*     */ import org.apache.hadoop.hbase.filter.PageFilter;
/*     */ import org.apache.hadoop.hbase.util.Bytes;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class HBaseTableManager
/*     */ {
/*  42 */   static HashMap<String, HTable> tableMap = null;
/*     */   private static HBaseConfigurationManager hBaseConfigManager;
/*     */   private static String[] columnQualifiers;
/*     */   private static String[][] ResultMap;
/*     */   private static HBaseTableManager currentTableManager;
/*     */ 
/*     */   public static String[] getColumnQualifiers()
/*     */   {
/*  50 */     String[] clone = (String[])columnQualifiers.clone();
/*  51 */     columnQualifiers = null;
/*  52 */     return clone;
/*     */   }
/*     */ 
/*     */   public void setColumnQualifiers(String[] columnQualifiers)
/*     */   {
/*  57 */     columnQualifiers = columnQualifiers;
/*     */   }
/*     */ 
/*     */   public String[][] getResultMap()
/*     */   {
/*  62 */     String[][] clone = (String[][])ResultMap.clone();
/*  63 */     ResultMap = null;
/*  64 */     return clone;
/*     */   }
/*     */ 
/*     */   public HBaseTableManager()
/*     */   {
/*  70 */     if (hBaseConfigManager == null) {
/*  71 */       hBaseConfigManager = new HBaseConfigurationManager();
/*     */     }
/*     */ 
/*  74 */     if (tableMap == null) {
/*  75 */       tableMap = new HashMap();
/*     */     }
/*  77 */     createHTableMap();
/*     */   }
/*     */ 
/*     */   public HBaseTableManager(String newConf)
/*     */   {
/*  82 */     hBaseConfigManager = new HBaseConfigurationManager(newConf);
/*  83 */     tableMap = new HashMap();
/*  84 */     createHTableMap();
/*     */   }
/*     */ 
/*     */   public static void closeActiveConnection()
/*     */   {
/*  89 */     HBaseConfigurationManager.hbaseConf = null;
/*     */   }
/*     */ 
/*     */   private void createHTableMap()
/*     */   {
/*     */     try
/*     */     {
/* 101 */       tableMap.put("LatestId", new HTable(HBaseConfigurationManager.getHbaseConf(), "LatestId"));
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void addHTableIntoMap(String tableName)
/*     */   {
/*     */     try
/*     */     {
/* 112 */       tableMap.put(tableName, new HTable(HBaseConfigurationManager.getHbaseConf(), tableName));
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HTable getTable(String tableName) {
/* 120 */     HTable table = (HTable)tableMap.get(tableName);
/*     */ 
/* 122 */     if (table == null) {
/* 123 */       addHTableIntoMap(tableName);
/* 124 */       table = (HTable)tableMap.get(tableName);
/*     */     }
/*     */ 
/* 127 */     return table;
/*     */   }
/*     */ 
/*     */   public static String[] getColFamilies(String tableName)
/*     */   {
/* 132 */     String[] families = null;
/*     */     try {
/* 134 */       Set familySet = getTable(tableName).getTableDescriptor().getFamiliesKeys();
/*     */ 
/* 136 */       Object[] allFamiles = familySet.toArray();
/* 137 */       families = new String[allFamiles.length];
/* 138 */       int i = 0;
/* 139 */       for (Object family2 : allFamiles) {
/* 140 */         byte[] family = (byte[])family2;
/*     */ 
/* 142 */         families[i] = Bytes.toString(family);
/* 143 */         i++;
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 148 */       System.out.println("Exception :" + e);
/*     */     }
/*     */ 
/* 151 */     return families;
/*     */   }
/*     */ 
/*     */   public static void deleteTableColoumnQualifier(String tableName, String rowName, String qualifierName, String family)
/*     */   {
/* 156 */     Delete del = new Delete(Bytes.toBytes(rowName));
/*     */     try
/*     */     {
/* 159 */       del = del.deleteColumn(Bytes.toBytes(family), Bytes.toBytes(qualifierName));
/* 160 */       getTable(tableName).delete(del);
/* 161 */       System.out.println("Coloumn " + rowName + ":" + qualifierName + " deleted");
/*     */     }
/*     */     catch (IOException e) {
/* 164 */       System.out.println("Exception deleting coloumn Qualifier");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String deleteTableRow(byte[] rowName, String tableName)
/*     */   {
/* 171 */     String result = null;
/*     */     try
/*     */     {
/* 174 */       System.out.println("Deleting row: " + Bytes.toString(rowName));
/* 175 */       Delete delRowData = new Delete(rowName);
/*     */ 
/* 177 */       getTable(tableName).delete(delRowData);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 181 */       System.out.println("Exception occured in retrieving data");
/*     */     }
/* 183 */     return result;
/*     */   }
/*     */ 
/*     */   public static String[] getColFamilyVersions(String tableName)
/*     */   {
/* 205 */     String[] families = null;
/*     */     try {
/* 207 */       Set familySet = getTable(tableName).getTableDescriptor().getFamiliesKeys();
/*     */ 
/* 209 */       Object[] allFamiles = familySet.toArray();
/* 210 */       families = new String[allFamiles.length];
/* 211 */       int i = 0;
/* 212 */       for (Object family2 : allFamiles) {
/* 213 */         byte[] family = (byte[])family2;
/* 214 */         int maxversions = getTable(tableName).getTableDescriptor().getFamily(family).getMaxVersions();
/* 215 */         families[i] = String.valueOf(maxversions);
/* 216 */         i++;
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 221 */       System.out.println("Exception :" + e);
/*     */     }
/*     */ 
/* 224 */     return families;
/*     */   }
/*     */ 
/*     */   public static String[] getAllTableNames()
/*     */   {
/* 229 */     String[] tblnames = null;
/*     */     try {
/* 231 */       HTableDescriptor[] tables = HBaseConfigurationManager.getHbaseAdmin().listTables();
/* 232 */       tblnames = new String[tables.length];
/* 233 */       int i = 0;
/* 234 */       for (HTableDescriptor tbl : tables) {
/* 235 */         tblnames[i] = tbl.getNameAsString();
/* 236 */         i++;
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 240 */       Logger.getLogger(HBaseTableManager.class).log(Level.ERROR, ExceptionUtils.getFullStackTrace(e));
/*     */     }
/*     */ 
/* 243 */     return tblnames;
/*     */   }
/*     */ 
/*     */   public static boolean getDataFiller(Result resultObject, String colFamily)
/*     */   {
/* 248 */     String[] coloumns = null;
/* 249 */     String[][] data = null;
/* 250 */     String action = "";
/*     */ 
/* 257 */     if (!resultObject.isEmpty())
/*     */     {
/* 259 */       NavigableMap resultMap = resultObject.getNoVersionMap();
/* 260 */       NavigableMap resultVersionMap = resultObject.getMap();
/*     */ 
/* 262 */       byte[] key = Bytes.toBytes(colFamily);
/*     */ 
/* 264 */       NavigableMap innerMap = null;
/* 265 */       innerMap = (NavigableMap)resultMap.get(key);
/*     */ 
/* 267 */       if (innerMap == null) {
/* 268 */         return false;
/*     */       }
/* 270 */       coloumns = new String[innerMap.size() + 1];
/* 271 */       data = new String[innerMap.size() + 1][2];
/*     */ 
/* 273 */       byte[] innerKey = (byte[])innerMap.firstKey();
/* 274 */       coloumns[0] = "Row Key";
/* 275 */       data[0][0] = Bytes.toString(resultObject.getRow());
/* 276 */       data[0][1] = "Row Key";
/* 277 */       int i = 1;
/* 278 */       while (innerKey != null) {
/* 279 */         coloumns[i] = Bytes.toString(innerKey);
/*     */ 
/* 285 */         if (HBaseManagerViewTable.coloumnTypeList.containsKey(coloumns[i])) {
/* 286 */           byte[] value = resultObject.getValue(key, innerKey);
/* 287 */           action = (String)HBaseManagerViewTable.coloumnTypeList.get(coloumns[i]);
/*     */ 
/* 289 */           System.out.print("Converting to type:" + action);
/*     */ 
/* 291 */           data[i][0] = getConvertedValue(value, action, coloumns[i]);
/*     */         }
/*     */         else {
/* 294 */           data[i][0] = Bytes.toString(resultObject.getValue(key, innerKey));
/*     */         }
/* 296 */         data[i][1] = Bytes.toString(innerKey);
/*     */ 
/* 298 */         i++;
/* 299 */         innerKey = (byte[])innerMap.higherKey(innerKey);
/*     */       }
/*     */     }
/* 302 */     columnQualifiers = coloumns;
/* 303 */     ResultMap = data;
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   public static KeyValue[] getDataFillerKeyValues(Result resultObject)
/*     */   {
/* 309 */     KeyValue[] values = null;
/* 310 */     if (!resultObject.isEmpty()) {
/* 311 */       values = resultObject.raw();
/*     */     }
/*     */ 
/* 314 */     return values;
/*     */   }
/*     */ 
/*     */   public static String getConvertedValue(byte[] value, String action, String coloumn)
/*     */   {
/* 319 */     String cnvValue = "";
/*     */     try
/*     */     {
/* 322 */       if (action.equals("INTEGER")) {
/* 323 */         cnvValue = String.valueOf(Bytes.toInt(value));
/*     */       }
/* 325 */       else if (action.equals("DOUBLE")) {
/* 326 */         cnvValue = String.valueOf(Bytes.toDouble(value));
/*     */       }
/* 328 */       else if (action.equals("SHORT")) {
/* 329 */         cnvValue = String.valueOf(Bytes.toShort(value));
/*     */       }
/* 331 */       else if (action.equals("LONG")) {
/* 332 */         cnvValue = String.valueOf(Bytes.toLong(value));
/*     */       }
/* 334 */       else if (action.equals("BOOLEAN")) {
/* 335 */         cnvValue = String.valueOf(Bytes.toBoolean(value));
/*     */       }
/* 337 */       else if (action.equals("DATEANDTIME")) {
/* 338 */         cnvValue = String.valueOf(Bytes.toBoolean(value));
/* 339 */         long val = Bytes.toLong(value);
/* 340 */         cnvValue = String.valueOf(new Date(val));
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 345 */       System.out.println("InConvertible");
/* 346 */       HBaseManagerViewTable.coloumnTypeList.remove(coloumn);
/*     */     }
/* 348 */     return cnvValue;
/*     */   }
/*     */ 
/*     */   public static byte[] getConvertedValue(String value, String action, String coloumn)
/*     */   {
/* 354 */     byte[] cnvValue = "".getBytes();
/*     */     try
/*     */     {
/* 357 */       if (action.equals("INTEGER")) {
/* 358 */         cnvValue = Bytes.toBytes(Integer.parseInt(value));
/*     */       }
/* 360 */       else if (action.equals("DOUBLE")) {
/* 361 */         cnvValue = Bytes.toBytes(Double.parseDouble(value));
/*     */       }
/* 363 */       else if (action.equals("SHORT")) {
/* 364 */         cnvValue = Bytes.toBytes(Short.parseShort(value));
/*     */       }
/* 366 */       else if (action.equals("LONG")) {
/* 367 */         cnvValue = Bytes.toBytes(Long.parseLong(value));
/*     */       }
/* 369 */       else if (action.equals("BOOLEAN")) {
/* 370 */         cnvValue = Bytes.toBytes(Boolean.parseBoolean(value));
/*     */       }
/* 372 */       else if (action.equals("DATEANDTIME"))
/*     */       {
/* 374 */         SimpleDateFormat format = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss a");
/* 375 */         Date thisDate = format.parse(value);
/* 376 */         cnvValue = Bytes.toBytes(thisDate.getTime());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 381 */       System.out.println("InConvertible");
/* 382 */       HBaseManagerViewTable.coloumnTypeList.remove(coloumn);
/*     */     }
/* 384 */     return cnvValue;
/*     */   }
/*     */ 
/*     */   public static ResultScanner getAllDataInRangeOfFamily(String startRowRange, String stopRowRange, byte[][] cfs, String ctableName)
/*     */   {
/* 390 */     Scan scan = new Scan();
/* 391 */     scan.setMaxVersions();
/* 392 */     scan.setStartRow(Bytes.toBytes(startRowRange));
/*     */ 
/* 394 */     for (byte[] family : cfs) {
/* 395 */       scan.addFamily(family);
/*     */     }
/*     */ 
/* 398 */     if (stopRowRange != null) {
/* 399 */       scan.setStopRow(Bytes.toBytes(stopRowRange));
/*     */     }
/*     */ 
/* 402 */     ResultScanner resultScanner = null;
/*     */     try
/*     */     {
/* 405 */       resultScanner = getTable(ctableName).getScanner(scan);
/*     */     }
/*     */     catch (Exception localException1)
/*     */     {
/*     */     }
/*     */ 
/* 411 */     return resultScanner;
/*     */   }
/*     */ 
/*     */   public static ResultScanner getList(String startRowRange, String stopRowRange, byte[] cf1, byte[] cf2, long limit, FilterList filterList, String ctableName)
/*     */   {
/* 416 */     Scan scan = new Scan();
/* 417 */     scan.addFamily(cf1);
/* 418 */     scan.setStartRow(Bytes.toBytes(startRowRange));
/*     */ 
/* 420 */     if (stopRowRange != null) {
/* 421 */       scan.setStopRow(Bytes.toBytes(stopRowRange));
/*     */     }
/* 423 */     if (limit != 0L)
/*     */     {
/* 425 */       filterList.addFilter(new PageFilter(limit));
/*     */     }
/*     */     else {
/* 428 */       filterList.addFilter(new PageFilter(100L));
/*     */     }
/* 430 */     scan.setFilter(filterList);
/* 431 */     ResultScanner resultScanner = null;
/*     */     try
/*     */     {
/* 434 */       resultScanner = getTable(ctableName).getScanner(scan);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */ 
/* 440 */     return resultScanner;
/*     */   }
/*     */ 
/*     */   public static void insertRowToDb(String[][] tableData, String colFamily, String tableName)
/*     */   {
/* 445 */     String rowKey = tableData[0][0];
/*     */ 
/* 447 */     Put resourcePut = new Put(Bytes.toBytes(rowKey));
/*     */ 
/* 449 */     String[] userDataList = new String[tableData.length];
/* 450 */     String[] userColList = new String[tableData.length];
/*     */ 
/* 452 */     String valueString = null;
/* 453 */     byte[] putValue = null;
/* 454 */     String action = null;
/*     */ 
/* 456 */     for (int i = 0; i < tableData.length; i++) {
/* 457 */       userDataList[i] = tableData[i][0];
/* 458 */       userColList[i] = tableData[i][1];
/*     */     }
/*     */ 
/* 461 */     for (int i = 1; i < userDataList.length; i++) {
/* 462 */       valueString = userDataList[i];
/* 463 */       if (HBaseManagerViewTable.coloumnTypeList.containsKey(userColList[i])) {
/* 464 */         action = (String)HBaseManagerViewTable.coloumnTypeList.get(userColList[i]);
/* 465 */         putValue = getConvertedValue(valueString, action, userColList[i]);
/*     */       }
/*     */       else {
/* 468 */         putValue = Bytes.toBytes(userDataList[i]);
/*     */       }
/* 470 */       resourcePut.add(Bytes.toBytes(colFamily), Bytes.toBytes(userColList[i]), putValue);
/*     */     }
/*     */ 
/* 473 */     _insert(resourcePut, tableName);
/*     */   }
/*     */ 
/*     */   public static void _insert(Put put, String tableName)
/*     */   {
/*     */     try
/*     */     {
/* 481 */       getTable(tableName).put(put);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void _insert(List<Put> putList, String tableName)
/*     */   {
/*     */     try
/*     */     {
/* 494 */       getTable(tableName).put(putList);
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HBaseTableManager getInstance()
/*     */   {
/* 504 */     if (currentTableManager == null) {
/* 505 */       currentTableManager = new HBaseTableManager();
/*     */     }
/* 507 */     return currentTableManager;
/*     */   }
/*     */ 
/*     */   public static void resetConnection()
/*     */   {
/* 512 */     currentTableManager = new HBaseTableManager("reset");
/*     */   }
/*     */ 
/*     */   public static void shutdownAliveConnection()
/*     */   {
/* 517 */     if (HBaseConfigurationManager.getHbaseConf() != null) {
	HConnectionManager.deleteConnection(HBaseConfigurationManager.getHbaseConf());
/* 518 */    //   HConnectionManager.deleteConnection(HBaseConfigurationManager.getHbaseConf(), true);
/*     */     }
/* 520 */     closeActiveConnection();
/* 521 */     HbaseManagerStatic.clearAllConfigs();
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseTableManager
 * JD-Core Version:    0.6.2
 */