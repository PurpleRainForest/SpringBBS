package nz.co.s3m.test.mockito.examples;

import nz.co.s3m.test.mockito.examples.UserManager;

public class UserService {

	private final UserManager manager;

	UserService(UserManager manager) {
		this.manager = manager;
	}

	public UserService() {
		this(new UserManager());
	}

	public int getUserCount() {
		try {
			return manager.getUserCount();
		} catch (Exception e) {
			return -1;
		}
	}
}
