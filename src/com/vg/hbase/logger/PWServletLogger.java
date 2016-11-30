/*    */ package com.vg.hbase.logger;
/*    */ 
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PWServletLogger
/*    */ {
/*    */   private Logger pwLogger;
/*    */ 
/*    */   public PWServletLogger(Class<?> className)
/*    */   {
/* 16 */     this.pwLogger = Logger.getLogger(className);
/* 17 */     this.pwLogger.setLevel(Level.ALL);
/*    */   }
/*    */ 
/*    */   public void logInfo(String message)
/*    */   {
/* 23 */     this.pwLogger.info(message);
/*    */   }
/*    */ 
/*    */   public void logDebug(String message) {
/* 27 */     this.pwLogger.debug(message);
/*    */   }
/*    */ 
/*    */   public void logWarn(String message) {
/* 31 */     this.pwLogger.warn(message);
/*    */   }
/*    */ 
/*    */   public void logError(String message) {
/* 35 */     this.pwLogger.error(message);
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.logger.PWServletLogger
 * JD-Core Version:    0.6.2
 */