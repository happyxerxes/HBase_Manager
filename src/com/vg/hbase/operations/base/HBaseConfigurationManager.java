/*    */ package com.vg.hbase.operations.base;
/*    */ 
/*    */ import org.apache.hadoop.conf.Configuration;
/*    */ import org.apache.hadoop.hbase.HBaseConfiguration;
/*    */ import org.apache.hadoop.hbase.MasterNotRunningException;
/*    */ import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
/*    */ //import org.apache.hadoop.hbase.client.HBaseAdmin;
/*    */ 
/*    */ public class HBaseConfigurationManager
/*    */ {
/* 16 */   public static Configuration hbaseConf = null;
/* 17 */   public static Admin admin = null;
		   public  static Connection connection = null;

/*    */ 
/*    */   public HBaseConfigurationManager(String newConf)
/*    */   {
/* 21 */     hbaseConf = getConfiguration();
/*    */   }
/*    */ 
/*    */   public HBaseConfigurationManager()
/*    */   {
/* 27 */     if (hbaseConf == null)
/* 28 */       hbaseConf = getConfiguration();
/*    */   }
/*    */ 
/*    */   private Configuration getConfiguration()
/*    */   {
/* 37 */     Configuration config = HBaseConfiguration.create();
/* 38 */    // config.clear();
/*    */ 
/* 40 */     config.set("hbase.zookeeper.quorum", HbaseManagerStatic.getHBASE_ZOOKEEPER_QUORUM());
/* 41 */     config.set("hbase.zookeeper.property.clientPort", HbaseManagerStatic.getHBASE_ZOOKEEPER_PROPERTY_CLIENT_PORT());
/* 42 */     config.set("hbase.master", HbaseManagerStatic.getHBASE_MASTER());
/*    */     try
/*    */     {
/* 45 */     //  hbaseAdmin = new HBaseAdmin(config);
	 		connection = ConnectionFactory.createConnection(config);
	 		admin = connection.getAdmin();
	
/*    */     }
/*    */     catch (MasterNotRunningException e)
/*    */     {
/* 49 */       HbaseManagerStatic.SERVER_ERROR = true;
/*    */     }
/*    */     catch (ZooKeeperConnectionException e)
/*    */     {
/* 53 */       HbaseManagerStatic.SERVER_ERROR = true;
/*    */     }
/*    */     catch (Exception e) {
/* 56 */       HbaseManagerStatic.SERVER_ERROR = true;
/*    */     }
/* 58 */     return config;
/*    */   }
/*    */ 
/*    */   public static Configuration getHbaseConf()
/*    */   {
/* 64 */     return hbaseConf;
/*    */   }
/*    */ 
/*    */   /*public static HBaseAdmin getHbaseAdmin()
       {
 69      return hbaseAdmin;
       }*/
/*    */ 
/*    */   public static void setHbaseConf(HBaseConfiguration hbaseConf)
/*    */   {
/* 75 */     hbaseConf = hbaseConf;
/*    */   }
/*    */
			public static Admin getHbaseAdmin() {
	// TODO Auto-generated method stub
					return admin;
			} 
		}

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.operations.base.HBaseConfigurationManager
 * JD-Core Version:    0.6.2
 */