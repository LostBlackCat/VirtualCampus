package connectTest;

import java.sql.*;

public class ConnectTest {
	public static void main(String[] args) throws Exception{
		AccessConnection student=new AccessConnection();
		Connection conn=student.getConn();
		System.out.println("�����������ݣ�");
		student.ShowTable(conn);
	}
}
