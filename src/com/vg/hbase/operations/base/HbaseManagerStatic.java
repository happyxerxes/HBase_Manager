/*    */ package com.vg.hbase.operations.base;
/*    */ 
/*    */ public final class HbaseManagerStatic
/*    */ {
/*    */   private static String HBASE_ZOOKEEPER_QUORUM;
/*    */   private static String HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT;
/*    */   private static String HBASE_MASTER;

/*    */   private static String currentConnectionAlias;
/* 11 */   public static boolean SERVER_ERROR = false;
/* 12 */   public static boolean SERVER_NOT_CONNECTED = true;
/*    */ 
/*    */   public static String getHBASE_ZOOKEEPER_QUORUM()
/*    */   {
/* 16 */     return HBASE_ZOOKEEPER_QUORUM;
/*    */   }
/*    */ 
/*    */   public static void setHBASE_ZOOKEEPER_QUORUM(String hBASE_ZOOKEEPER_QUORUM)
/*    */   {
/* 21 */     HBASE_ZOOKEEPER_QUORUM = hBASE_ZOOKEEPER_QUORUM;
/*    */   }
/*    */ 
/*    */   public static String getHBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT()
/*    */   {
/* 26 */     return HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT;
/*    */   }
/*    */ 
/*    */   public static void setHBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT(String hBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT)
/*    */   {
/* 31 */     HBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT = hBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT;
/*    */   }
/*    */ 
/*    */   public static String getHBASE_MASTER()
/*    */   {
/* 36 */     return HBASE_MASTER;
/*    */   }
/*    */ 
/*    */   public static void setHBASE_MASTER(String hBASE_MASTER)
/*    */   {
/* 41 */     HBASE_MASTER = hBASE_MASTER;
/*    */   }
/*    */ 
/*    */   public static void clearAllConfigs()
/*    */   {
/* 46 */     setHBASE_MASTER("");
/* 47 */     setHBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT("");
/* 48 */     setHBASE_ZOOKEEPER_QUORUM("");
/*    */   }
/*    */ 
/*    */   public static String getCurrentConnectionAlias()
/*    */   {
/* 53 */     return currentConnectionAlias;
/*    */   }
/*    */ 
/*    */   public static void setCurrentConnectionAlias(String currentConnectionAlias)
/*    */   {
/* 58 */     currentConnectionAlias = currentConnectionAlias;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.operations.base.HbaseManagerStatic
 * JD-Core Version:    0.6.2
 */