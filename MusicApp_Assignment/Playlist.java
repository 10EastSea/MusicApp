import java.util.ArrayList;
import java.util.Date;

public class Playlist {
	int playlistID;
	String playlistTitle;
	Date creationDate; // 날짜 타입은 다시한번 확인.. 
	int userIdx; // [외래키] 유저참조 
	
	// numOfMusic 삭제 -> musics.size()로 구할 수 있기 때문 
	String userName;
	ArrayList<Music> musics = new ArrayList<Music>();
	
	Playlist(){}
	Playlist(int playlistID, String playlistTitle){
		this.playlistID = playlistID;
		this.playlistTitle = playlistTitle;
	}
	Playlist(int playlistID, String playlistTitle, Date creationDate, int userIdx){
		this.playlistID = playlistID;
		this.playlistTitle = playlistTitle;
		this.creationDate = creationDate;
		this.userIdx = userIdx;
	}
}
