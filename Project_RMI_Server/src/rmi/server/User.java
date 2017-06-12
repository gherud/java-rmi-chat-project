package rmi.server;

public class User {
	
	private String nick;
	private String groupName;
	
	public User(String nick, String groupName){
		this.nick = nick;
		this.groupName = groupName;
	}

	public String getNick() {
		return nick;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
}