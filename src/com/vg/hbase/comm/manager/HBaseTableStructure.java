/*    */ package com.vg.hbase.comm.manager;
/*    */ 
/*    */ import com.vg.hbase.operations.base.HBaseConfigurationManager;

/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;

/*    */ import org.apache.hadoop.hbase.HColumnDescriptor;
/*    */ import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
/*    */ import org.apache.hadoop.hbase.client.HBaseAdmin;
/*    */ import org.apache.hadoop.hbase.util.Bytes;
/*    */ 
/*    */ public class HBaseTableStructure
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4757579306171103892L;
/*    */   HashMap<String, String> tableDescriptor;
/*    */   String HTableName;
/*    */ 
/*    */   public String getHTableName()
/*    */   {
/* 29 */     return this.HTableName;
/*    */   }
/*    */ 
/*    */   public void setHTableName(String HTableName) {
/* 33 */     this.HTableName = HTableName;
/*    */   }
/*    */ 
/*    */   public void createWriteTableStructure(String tableName)
/*    */   {
/*    */     try {
/* 39 */       this.tableDescriptor = new HashMap();
/* 40 */       this.HTableName = tableName;
/* 41 */       HTableDescriptor descriptor = HBaseConfigurationManager.admin.getTableDescriptor(TableName.valueOf(Bytes.toBytes(tableName))
/* 42 */         );
/* 43 */       HColumnDescriptor[] cols = descriptor.getColumnFamilies();
/* 44 */       for (int i = 0; i < cols.length; i++) {
/* 45 */         this.tableDescriptor.put(cols[i].getNameAsString(), String.valueOf(cols[i].getMaxVersions()));
/*    */       }
/*    */ 
/* 49 */       Logger.getLogger(HBaseTableStructure.class.getName()).log(Level.INFO, 
/* 50 */         "TableName Descriptor Initaed:" + this.HTableName);
/*    */     }
/*    */     catch (IOException ex) {
/* 53 */       Logger.getLogger(HBaseTableStructure.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HTableDescriptor createReadTableStructure() {
/* 58 */     HTableDescriptor descriptor = null;
/* 59 */     HColumnDescriptor colDesc = null;
/* 60 */     String[] familyNames = new String[this.tableDescriptor.size()];
/* 61 */     this.tableDescriptor.keySet().toArray(familyNames);
/*    */     try
/*    */     {
/* 64 */       descriptor = new HTableDescriptor(this.HTableName);
/*    */ 
/* 66 */       for (int i = 0; i < this.tableDescriptor.size(); i++) {
/* 67 */         colDesc = new HColumnDescriptor(familyNames[i]);
/* 68 */         int versions = Integer.parseInt((String)this.tableDescriptor.get(familyNames[i]));
/* 69 */         colDesc.setMaxVersions(versions);
/* 70 */         descriptor.addFamily(colDesc);
/*    */       }
/*    */ 
/* 74 */       Logger.getLogger(HBaseTableStructure.class.getName()).log(Level.INFO, 
/* 75 */         "TableName Descriptor Initaed:" + this.HTableName);
/*    */     }
/*    */     catch (Exception ex) {
/* 78 */       Logger.getLogger(HBaseTableStructure.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/* 80 */     return descriptor;
/*    */   }
/*    */ 
/*    */   public byte[][] getAllColoumnFamilies() {
/* 84 */     byte[][] familyBytes = null;
/*    */ 
/* 86 */     Set fams = this.tableDescriptor.keySet();
/* 87 */     String[] family = new String[fams.size()];
/* 88 */     familyBytes = new byte[fams.size()][];
/* 89 */     fams.toArray(family);
/*    */ 
/* 91 */     for (int i = 0; i < family.length; i++) {
/* 92 */       familyBytes[i] = Bytes.toBytes(family[i]);
/*    */     }
/* 94 */     return familyBytes;
/*    */   }
/*    */ }

/* Location:           E:\spark_windows\documents\hbase\HBase-Manager-1.4-RC1\
 * Qualified Name:     com.vg.hbase.comm.manager.HBaseTableStructure
 * JD-Core Version:    0.6.2
 */