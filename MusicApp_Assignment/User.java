import java.util.ArrayList;

public class User {
	int userID;
	String userPW; // 추가함!! 
	String userName;
	String phone;
	String ssn;
	String address;
	int isAdmin;
	
	ArrayList<Playlist> playlists = new ArrayList<Playlist>();

	User(){}
	User(int userID, String userPW, String userName, String phone, String ssn, String address, int isAdmin){
		this.userID = userID;
		this.userPW = userPW;
		this.userName = userName;
		this.phone = phone;
		this.ssn = ssn;
		this.address = address;
		this.isAdmin = isAdmin;
	}
}
