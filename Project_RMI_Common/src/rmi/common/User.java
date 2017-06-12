package rmi.common;

public class User {
	
	private String nick;
	private String grpName;
	
	public User(String nick, String grpName){
		this.nick = nick;
		this.grpName = grpName;
	}

	public String getNick() {
		return nick;
	}

	public String getGrpName() {
		return grpName;
	}
}