/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HBaseTableData
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 7939170531079486032L;
/* 18 */   private List<HBaseRow> hbaseTableData = new ArrayList();
/*    */ 
/*    */   public List<HBaseRow> getHbaseTableData() {
/* 21 */     return this.hbaseTableData;
/*    */   }
/*    */ 
/*    */   public void setHbaseTableData(List<HBaseRow> hbaseTableData) {
/* 25 */     this.hbaseTableData = hbaseTableData;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseTableData
 * JD-Core Version:    0.6.2
 */