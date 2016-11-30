/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class HbaseTableObject
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -2311782994212110598L;
/*    */   private HBaseTableStructure tableStructure;
/*    */   private HBaseTableData tableData;
/*    */   private boolean lastObject;
/*    */   private boolean linkedFileAvailable;
/*    */   private int totalLinkedFiles;
/*    */ 
/*    */   public HBaseTableData getTableData()
/*    */   {
/* 29 */     return this.tableData;
/*    */   }
/*    */ 
/*    */   public void setTableData(HBaseTableData tableData)
/*    */   {
/* 34 */     this.tableData = tableData;
/*    */   }
/*    */ 
/*    */   public HBaseTableStructure getTableStructure()
/*    */   {
/* 39 */     return this.tableStructure;
/*    */   }
/*    */ 
/*    */   public void setTableStructure(HBaseTableStructure tableStructure)
/*    */   {
/* 44 */     this.tableStructure = tableStructure;
/*    */   }
/*    */ 
/*    */   public boolean isLastObject()
/*    */   {
/* 49 */     return this.lastObject;
/*    */   }
/*    */ 
/*    */   public void setLastObject(boolean lastObject)
/*    */   {
/* 54 */     this.lastObject = lastObject;
/*    */   }
/*    */ 
/*    */   public boolean isLinkedFileAvailable()
/*    */   {
/* 59 */     return this.linkedFileAvailable;
/*    */   }
/*    */ 
/*    */   public void setLinkedFileAvailable(boolean linkedFileAvailable)
/*    */   {
/* 64 */     this.linkedFileAvailable = linkedFileAvailable;
/*    */   }
/*    */ 
/*    */   public int getTotalLinkedFiles()
/*    */   {
/* 69 */     return this.totalLinkedFiles;
/*    */   }
/*    */ 
/*    */   public void setTotalLinkedFiles(int totalLinkedFiles)
/*    */   {
/* 74 */     this.totalLinkedFiles = totalLinkedFiles;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HbaseTableObject
 * JD-Core Version:    0.6.2
 */