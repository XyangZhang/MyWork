package com.zxy.factory;

import com.zxy.service.IEmpService;
import com.zxy.service.impl.EmpServiceImpl;

public class ServiceFactory {
	public static IEmpService getIEmpServiceInstance() {
		return new EmpServiceImpl();
	}
}
