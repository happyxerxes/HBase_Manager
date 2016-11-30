/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.apache.hadoop.hbase.client.HTable;
/*    */ import org.apache.hadoop.hbase.client.Put;
/*    */ import org.apache.hadoop.hbase.client.Result;
/*    */ 
/*    */ public class HBaseRow
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 2666078248584185425L;
/* 21 */   private List<HBaseCellVersions> rowWithAllVersions = new ArrayList();
/*    */ 
/*    */   public HBaseRow(Result result, byte[][] coloumnFamily)
/*    */   {
/* 27 */     for (byte[] colFamily : coloumnFamily)
/*    */     {
/* 29 */       List coloumnList = TableRowObject.getContainedQualifiers(result, colFamily);
/* 30 */       Iterator coloumnQualifierList = coloumnList.iterator();
/* 31 */       while (coloumnQualifierList.hasNext()) {
/* 32 */         HBaseCellVersions versions = new HBaseCellVersions(result, (byte[])coloumnQualifierList.next(), colFamily);
/* 33 */         this.rowWithAllVersions.add(versions);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void putRow(HTable table)
/*    */     throws IOException
/*    */   {
/* 41 */     List putList = new ArrayList();
/* 42 */     Iterator iterateRows = this.rowWithAllVersions.iterator();
/* 43 */     HBaseCellVersions cellVersions = null;
/*    */     Iterator cellIterator;
/* 45 */     for (; iterateRows.hasNext(); 
/* 50 */       cellIterator.hasNext())
/*    */     {
/* 46 */       cellVersions = (HBaseCellVersions)iterateRows.next();
/* 47 */       List cells = cellVersions.getCellVersions();
/*    */ 
/* 49 */       cellIterator = cells.iterator();
/* 50 */     //  continue;
/* 51 */       HBaseCell cell = (HBaseCell)cellIterator.next();
/*    */ 
/* 53 */       table.put(getPutObject(cell));
/*    */     }
/*    */   }
/*    */ 
/*    */   private Put getPutObject(HBaseCell cell)
/*    */   {
/* 63 */     Put cellPut = new Put(cell.getRowKey());
/* 64 */     cellPut.add(cell.getColoumnFamily(), cell.getColoumnQualifier(), cell.getColoumnValue());
/* 65 */     return cellPut;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseRow
 * JD-Core Version:    0.6.2
 */