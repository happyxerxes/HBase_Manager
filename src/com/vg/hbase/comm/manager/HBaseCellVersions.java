/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.hadoop.hbase.KeyValue;
/*    */ import org.apache.hadoop.hbase.client.Result;
/*    */ 
/*    */ public class HBaseCellVersions
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -7230902164364243166L;
/* 16 */   private List<HBaseCell> cellVersions = new ArrayList();
/*    */   private byte[] rowKey;
/*    */ 
/*    */   public HBaseCellVersions(Result resultOb, byte[] coloumnQualifier, byte[] coloumnFamily)
/*    */   {
/* 21 */     this.rowKey = resultOb.getRow();
/* 22 */     List columnVersions = resultOb.getColumn(coloumnFamily, coloumnQualifier);
/*    */ 
/* 24 */     for (int i = columnVersions.size() - 1; i >= 0; i--)
/*    */     {
/* 26 */       HBaseCell singleCell = new HBaseCell(this.rowKey, coloumnFamily, coloumnQualifier, ((KeyValue)columnVersions.get(i)).getValue());
/* 27 */       getCellVersions().add(singleCell);
/*    */     }
/*    */   }
/*    */ 
/*    */   public List<HBaseCell> getCellVersions() {
/* 32 */     return this.cellVersions;
/*    */   }
/*    */ 
/*    */   public void setCellVersions(List<HBaseCell> cellVersions) {
/* 36 */     this.cellVersions = cellVersions;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseCellVersions
 * JD-Core Version:    0.6.2
 */