package com.zxy.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * ����ר�Ÿ������ݿ��������رղ�������ʵ�����������ʱ����ζ��Ҫ�������ݿ�Ŀ���
 * �����ڱ���Ĺ��췽����Ҫ�������ݿ��������������ݿ�����ȡ��
 * @author Zhangxy
 *
 */
public class DatabaseConnection {

	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/zxy";
	private static final String DBUSER = "root";
	private static final String DBPASS = "mysqladmin";
	
	private Connection conn = null;
	/**
	 * �ڹ��췽������Ϊconn�������ʵ����������ֱ��ȡ�����ݿ�����Ӷ���
	 * �������еĲ������ǻ������ݿ���ɵģ�������ݿ�ȡ�ò������ӣ���ôҲ����ζ�����еĲ���������ֹͣ��
	 */
	public DatabaseConnection() {
		try {
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (Exception e) { // ��Ȼ�˴����쳣�������׳������岻��
			e.printStackTrace();
		}
	}
	/**
	 * ȡ��һ�����ݿ�����Ӷ���
	 * @return Connectionʵ��������
	 */
	public Connection getConnection() {
		return this.conn;
	}
	/**
	 * �������ݿ�Ĺر�
	 */
	public void close() {
		if (this.conn != null) { // ��ʾ���ڴ��������Ӷ���
			try {
				this.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
