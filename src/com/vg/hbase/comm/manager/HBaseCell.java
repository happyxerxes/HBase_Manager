/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class HBaseCell
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -8367790555462692931L;
/*    */   private byte[] coloumnQualifier;
/*    */   private byte[] coloumnValue;
/*    */   private byte[] coloumnFamily;
/*    */   private byte[] rowKey;
/*    */ 
/*    */   public byte[] getColoumnQualifier()
/*    */   {
/* 14 */     return this.coloumnQualifier;
/*    */   }
/*    */ 
/*    */   public byte[] getColoumnValue() {
/* 18 */     return this.coloumnValue;
/*    */   }
/*    */ 
/*    */   public byte[] getColoumnFamily() {
/* 22 */     return this.coloumnFamily;
/*    */   }
/*    */ 
/*    */   public byte[] getRowKey() {
/* 26 */     return this.rowKey;
/*    */   }
/*    */ 
/*    */   public HBaseCell(byte[] rowKey, byte[] coloumnFamily, byte[] coloumnQualifier, byte[] coloumnValue)
/*    */   {
/* 35 */     this.coloumnQualifier = coloumnQualifier;
/* 36 */     this.coloumnValue = coloumnValue;
/* 37 */     this.coloumnFamily = coloumnFamily;
/* 38 */     this.rowKey = rowKey;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseCell
 * JD-Core Version:    0.6.2
 */