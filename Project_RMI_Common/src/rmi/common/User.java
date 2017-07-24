package rmi.common;

public class User {
	
	private String userName;
	private String groupName;
	
	public User(String userName, String groupName){
		this.userName = userName;
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
}