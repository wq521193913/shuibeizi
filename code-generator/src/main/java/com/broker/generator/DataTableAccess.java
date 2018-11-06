package com.broker.generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28 0028.
 */
public class DataTableAccess {
    private Connection connection;

    String dataBase = "shuibeizi";
    String ip = "127.0.0.1";
    String port = "3306";
    String userName = "root";
    String password = "root";


    private void connect(){
        try {
            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://")
                    .append(ip)
                    .append(":")
                    .append(port)
                    .append("/")
                    .append(dataBase)
                    .append("?user=")
                    .append(userName)
                    .append("&password=")
                    .append(password)
                    .append("&useUnicode=true&characterEncoding=utf-8");
//            String url = "jdbc:mysql://123.206.176.29:3306/" + dataBase + "?user=root&password=weishier123456&useUnicode=true&characterEncoding=utf-8";
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<TableColumnInfo> getTableColumn(String table) throws Exception{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData = null;
        List<TableColumnInfo> tableColumnInfos = new ArrayList<TableColumnInfo>();
        try {
            String sql = String.format("select * from information_schema.COLUMNS where TABLE_NAME='%s' and table_schema='" + dataBase + "'",table);
            this.connect();
            statement = this.connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            String columnName = null;
            String dataType = null;
            String columnComment = null;
            while (resultSet.next()){
                columnName = resultSet.getString("COLUMN_NAME");
                dataType = resultSet.getString("DATA_TYPE");
                columnComment = resultSet.getString("COLUMN_COMMENT");
                TableColumnInfo tableColumnInfo = new TableColumnInfo();
                tableColumnInfo.setColumnName(columnName);
                tableColumnInfo.setColumnType(dataType);
                tableColumnInfo.setColumnComment(columnComment);
                tableColumnInfos.add(tableColumnInfo);
                columnName = null;
                dataType = null;
                columnComment = null;
            }
        }catch (Exception e){
            if(null != resultSet) resultSet.close();
            if(null != statement) statement.close();
            if(null != this.connection) this.connection.close();
            e.printStackTrace();
        }
        return tableColumnInfos;
    }
}
