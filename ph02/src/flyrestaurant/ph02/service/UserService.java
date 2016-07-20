package flyrestaurant.ph02.service;

import flyrestaurant.ph02.entity.User;

public class UserService {
	public User login(String code,String password) {
		if ("test".equals(code) && "test".equals(password)) {
			User u = new User();
			u.id = 1;
			u.code = code;
			u.password = password;
			u.name = "’≈Õı’‘¿Ó";
			return u;
		}
		return null;
	}
}
