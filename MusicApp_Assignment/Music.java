import java.util.ArrayList;

public class Music {
	int musicID;
	String musicTitle;
	String lyricist; // 작사 
	String composer; // 작곡 
	String arranger; // 편곡 
	int hitsNum; // 조회수 (음악 재생 -> 조회수 +1) 
	int albumIdx; // [외래키] 앨범참조 
	
	ArrayList<String> artistName = new ArrayList<String>();; // 아티스트 이름 
	String albumTitle; // 앨범 명 
	ArrayList<String> mGenre = new ArrayList<String>(); // 장르 

	Music(){}
	Music(String musicTitle){
		this.musicTitle = musicTitle;
	}
	Music(int musicID, String musicTitle, String albumTitle){
		this.musicID = musicID;
		this.musicTitle = musicTitle;
		this.albumTitle = albumTitle;
	}
	Music(int musicID, String musicTitle, String lyricist, String composer, String arranger, int hitsNum, int albumIdx){
		this.musicID = musicID;
		this.musicTitle = musicTitle;
		this.lyricist = lyricist;
		this.composer = composer;
		this.arranger = arranger;
		this.hitsNum = hitsNum;
		this.albumIdx = albumIdx;
	}
}
