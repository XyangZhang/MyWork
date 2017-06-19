package com.zxy.test;

import java.util.Date;

import com.zxy.factory.ServiceFactory;
import com.zxy.vo.Emp;

public class TestEmpInsert {

	public static void main(String[] args) throws Exception {
		Emp vo = new Emp();
		vo.setEmpno(8889);
		vo.setEname("³Â¹Úµv");
		vo.setJob("ÉãÓ°Ê¦");
		vo.setHiredate(new Date());
		vo.setSal(10.0);
		vo.setComm(0.5);
		
		System.out.println(ServiceFactory.getIEmpServiceInstance().insert(vo));
	}

}
