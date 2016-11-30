/*     */ package com.vg.hbase.comm.manager;
/*     */ 
/*     */ import com.vg.hbase.manager.ui.HBaseManagerViewTable;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import org.apache.hadoop.hbase.client.Result;
/*     */ import org.apache.hadoop.hbase.util.Bytes;
/*     */ 
/*     */ public class TableRowObject
/*     */ {
/*     */   private String[] coloumns;
/*  21 */   private List<String> coloumnList = new ArrayList();
/*  22 */   private Map<Long, String[][]> coloumnData = new HashMap();
/*  23 */   private List<String[][]> coloumnsDataList = new ArrayList();
/*     */ 
/*  25 */   List<Long> timestamps = new ArrayList();
/*     */   private Result resultOb;
/*     */   private String colFamily;
/*     */   private Object[] all;
/*     */ 
/*     */   public TableRowObject(Result resultObject, String coloumnFamily, boolean needVersion)
/*     */   {
/*  33 */     this.resultOb = resultObject;
/*  34 */     this.colFamily = coloumnFamily;
/*  35 */     if (passDateFilter())
/*     */     {
/*  37 */       if (needVersion) {
/*  38 */         createVersionDataObjects();
/*     */       }
/*     */       else
/*  41 */         createNoVersionDataObjects();
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean passDateFilter()
/*     */   {
/*  50 */     boolean pass = true;
/*     */ 
/*  52 */     if (HBaseManagerViewTable.isDateFilterSet())
/*     */     {
/*  54 */       pass = false;
/*     */ 
/*  56 */       String cq = HBaseManagerViewTable.getDateFilterOnCq();
/*     */ 
/*  58 */       long dateFrom = HBaseManagerViewTable.getFilterDateFrom();
/*  59 */       long dateTo = HBaseManagerViewTable.getFilterDateTo();
/*  60 */       long resultDate = 0L;
/*     */ 
/*  62 */       byte[] resultVal = this.resultOb.getValue(Bytes.toBytes(this.colFamily), Bytes.toBytes(cq));
/*  63 */       if (resultVal != null) {
/*  64 */         resultDate = Bytes.toLong(resultVal);
/*     */ 
/*  66 */         if ((resultDate >= dateFrom) && (resultDate <= dateTo)) {
/*  67 */           pass = true;
/*  68 */           HBaseManagerViewTable.coloumnTypeList.put(cq, "DATEANDTIME");
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  73 */     if (!pass) {
/*  74 */       this.all = null;
/*     */     }
/*     */ 
/*  77 */     return pass;
/*     */   }
/*     */ 
/*     */   private Object[] createNoVersionDataObjects()
/*     */   {
/*  83 */     NavigableMap resultMap = this.resultOb.getNoVersionMap();
/*  84 */     this.all = new Object[2];
/*     */ 
/*  86 */     byte[] key = Bytes.toBytes(this.colFamily);
/*     */ 
/*  88 */     NavigableMap innerMap = null;
/*  89 */     innerMap = (NavigableMap)resultMap.get(key);
/*     */ 
/*  91 */     if (innerMap == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     this.coloumns = new String[innerMap.size() + 1];
/*  95 */     String[][] data = new String[innerMap.size() + 1][2];
/*     */ 
/*  97 */     byte[] innerKey = (byte[])innerMap.firstKey();
/*  98 */     this.coloumns[0] = "Row Key";
/*  99 */     data[0][0] = Bytes.toString(this.resultOb.getRow());
/* 100 */     data[0][1] = "Row Key";
/* 101 */     int i = 1;
/* 102 */     while (innerKey != null) {
/* 103 */       this.coloumns[i] = Bytes.toString(innerKey);
/*     */ 
/* 105 */       if (HBaseManagerViewTable.coloumnTypeList.containsKey(this.coloumns[i])) {
/* 106 */         byte[] value = this.resultOb.getValue(key, innerKey);
/* 107 */         String action = (String)HBaseManagerViewTable.coloumnTypeList.get(this.coloumns[i]);
/*     */ 
/* 109 */         System.out.print("Converting to type:" + action);
/*     */ 
/* 111 */         data[i][0] = HBaseTableManager.getConvertedValue(value, action, this.coloumns[i]);
/*     */       }
/*     */       else {
/* 114 */         data[i][0] = Bytes.toString(this.resultOb.getValue(key, innerKey));
/*     */       }
/* 116 */       data[i][1] = Bytes.toString(innerKey);
/*     */ 
/* 118 */       i++;
/* 119 */       innerKey = (byte[])innerMap.higherKey(innerKey);
/*     */     }
/*     */ 
/* 122 */     this.all[0] = this.coloumns;
/* 123 */     this.all[1] = data;
/*     */ 
/* 125 */     return data;
/*     */   }
/*     */ 
/*     */   private void createVersionDataObjects()
/*     */   {
/* 131 */     if ((this.resultOb != null) && (!this.resultOb.isEmpty())) {
/* 132 */       NavigableMap resultMap = this.resultOb.getNoVersionMap();
/* 133 */       byte[] key = Bytes.toBytes(this.colFamily);
/*     */ 
/* 136 */       NavigableMap innerMap = null;
/* 137 */       innerMap = (NavigableMap)resultMap.get(key);
/* 138 */       Map versionColoumns = new HashMap();
/* 139 */       List listVersionColoumns = new ArrayList();
/*     */ 
/* 141 */       byte[] innerKey = (byte[])innerMap.firstKey();
/* 142 */       while (innerKey != null) {
/* 143 */         this.coloumnList.add(Bytes.toString(innerKey));
/* 144 */         innerKey = (byte[])innerMap.higherKey(innerKey);
/*     */       }
/*     */ 
/* 147 */       Iterator coloumnIterator = this.coloumnList.iterator();
/* 148 */       while (coloumnIterator.hasNext()) {
/* 149 */         String coloumn = (String)coloumnIterator.next();
/* 150 */         List values = this.resultOb.getColumn(Bytes.toBytes(this.colFamily), Bytes.toBytes(coloumn));
/* 151 */         listVersionColoumns.add(values);
/* 152 */         versionColoumns.put(coloumn, values);
/*     */       }
/*     */ 
/* 156 */       this.all = new Object[3];
/* 157 */       this.all[0] = this.coloumnList;
/* 158 */       this.all[1] = versionColoumns;
/* 159 */       this.all[2] = listVersionColoumns;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String[] getColoumns()
/*     */   {
/* 166 */     return this.coloumns;
/*     */   }
/*     */ 
/*     */   public List<String[][]> getColoumnData()
/*     */   {
/* 171 */     return this.coloumnsDataList;
/*     */   }
/*     */ 
/*     */   public void resetRowObject()
/*     */   {
/* 176 */     this.coloumnData.clear();
/* 177 */     this.coloumnList.clear();
/* 178 */     this.coloumnsDataList.clear();
/*     */   }
/*     */ 
/*     */   public Object[] getAllRowInfo()
/*     */   {
/* 184 */     return this.all;
/*     */   }
/*     */ 
/*     */   public static List<byte[]> getContainedQualifiers(Result resultOb, byte[] coloumnFamily)
/*     */   {
/* 190 */     List coloumnList = new ArrayList();
/* 191 */     NavigableMap innerMap = null;
/* 192 */     if ((resultOb != null) && (!resultOb.isEmpty())) {
/* 193 */       NavigableMap resultMap = resultOb.getNoVersionMap();
/* 194 */       byte[] key = coloumnFamily;
/*     */ 
/* 196 */       innerMap = (NavigableMap)resultMap.get(key);
/* 197 */       if (innerMap != null) {
/* 198 */         byte[] innerKey = (byte[])innerMap.firstKey();
/* 199 */         while (innerKey != null) {
/* 200 */           coloumnList.add(innerKey);
/* 201 */           innerKey = (byte[])innerMap.higherKey(innerKey);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 206 */     return coloumnList;
/*     */   }
/*     */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.TableRowObject
 * JD-Core Version:    0.6.2
 */