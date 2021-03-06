package com.seu.vCampus.Database;
/**
 * 数据库连接测试（已成功）
 * 完成人：毛泊涵（2019/8/23）
 */

import com.seu.vCampus.util.Person;

import java.sql.*;

public class DatabaseConnection{

    private static final String dbURL = "jdbc:ucanaccess://" +
            System.getProperty("user.dir")+"/DataBase/VirtualCampus.accdb";

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
    }

    public static Connection getConn() {
        try {
            return DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            System.out.println("AccessDB connection failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void ShowTable(Connection c) throws SQLException {   //展示某些数据
        Statement st = c.createStatement();
        ResultSet res = st.executeQuery("select*from Users");
        while (res.next()) {
            System.out.print(res.getString("ECardNumber") + "\t");
            System.out.print(res.getString("userName") + "\t");
            System.out.print(res.getString("password") + "\t");
            System.out.println();
        }
    }

    public void insertScore(Connection c) throws SQLException {   //添加新用户数据
        try {
            PreparedStatement sql = c.prepareStatement("insert into Users(ECardNumber,userName,password)values(?,?,?)");
            sql.setString(1, "");
            sql.setString(2, "");
            sql.setString(3, "");
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Connection c) throws SQLException {  //删除最近添加的用户（可改成特定用户）
        Statement st = c.createStatement();
        st.executeUpdate("delete from Users where UID=(select max(UID)from Users)");
    }

    public void Search(Connection c,String ecardn) throws SQLException {  //通过UID搜索用户
        Statement sql = c.createStatement();
        ResultSet res = sql.executeQuery("select*from Users where ECardNumber="+ecardn);
        if (res.isLast()) {
            while (res.next()) {
                String id = res.getString("ECardNumber");
                String name = res.getString("userName");
                System.out.print("一卡通号：" + id);
                System.out.print("用户名：" + name);
            }
        } else
            System.out.println("未查到这名学生。");
    }

    public void indistinctSearch(Connection c) throws SQLException {   //模糊查询
        Statement sql = c.createStatement();
        ResultSet res = sql.executeQuery("select*from Users where ECardNumber like '09017%'");
        while (res.next()) {
            String id = res.getString("ECardNumber");
            String name = res.getString("userName");
            System.out.print("一卡通号：" + id);
            System.out.print("用户名：" + name);
        }
    }
}

