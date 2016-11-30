/*    */ package com.vg.hbase.manager.exception;
/*    */ 
/*    */ import org.apache.commons.lang.exception.ExceptionUtils;
/*    */ 
/*    */ public class HbaseManagerException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String exceptionMessage;
/*    */   private String exceptionStackTrace;
/* 22 */   public static String ERROR_CODE_1 = "?Error=1";
/* 23 */   public static String ERROR_CODE_2 = "?Error=2";
/* 24 */   public static String ERROR_CODE_3 = "?Error=3";
/* 25 */   public static String ERROR_CODE_4 = "?Error=4";
/*    */ 
/*    */   public String getExceptionMessage() {
/* 28 */     return this.exceptionMessage;
/*    */   }
/*    */ 
/*    */   public String getExceptionStackTrace() {
/* 32 */     return this.exceptionStackTrace;
/*    */   }
/*    */ 
/*    */   public HbaseManagerException()
/*    */   {
/* 37 */     this.exceptionMessage = "Exception";
/* 38 */     this.exceptionStackTrace = "";
/*    */   }
/*    */ 
/*    */   public HbaseManagerException(String exMessage) {
/* 42 */     super(exMessage);
/* 43 */     this.exceptionMessage = exMessage;
/*    */   }
/*    */ 
/*    */   public HbaseManagerException(Throwable ex) {
/* 47 */     super(ex);
/* 48 */     this.exceptionMessage = ex.getMessage();
/* 49 */     this.exceptionStackTrace = ExceptionUtils.getFullStackTrace(ex);
/*    */   }
/*    */ 
/*    */   public HbaseManagerException(String exMessage, Throwable ex)
/*    */   {
/* 54 */     super(exMessage, ex);
/* 55 */     this.exceptionMessage = exMessage;
/* 56 */     this.exceptionStackTrace = ExceptionUtils.getFullStackTrace(ex);
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.manager.exception.HbaseManagerException
 * JD-Core Version:    0.6.2
 */