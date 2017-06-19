package com.zxy.factory;

import java.sql.Connection;

import com.zxy.dao.IEmpDAO;
import com.zxy.dao.impl.EmpDAOImpl;

public class DAOFactory {
	public static IEmpDAO getIEmpDAOInstance(Connection conn) {
		return new EmpDAOImpl(conn);
	}
}
