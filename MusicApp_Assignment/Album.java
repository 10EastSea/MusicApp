import java.util.ArrayList;
import java.util.Date;

public class Album {
	int albumID;
	String albumTitle;
	String albumType; // 앨범타입: Single/EP(싱글/EP), Studio album(정규앨범), Album(기타앨범) 
	String agency; // 발매사 
	String releaseCompany; // 기획사 
	Date releaseDate; // 날짜 
	
	ArrayList<String> artistName = new ArrayList<String>();; // 아티스트 이름 
	ArrayList<Integer> artistID = new ArrayList<Integer>();; // 아티스트 id 
	ArrayList<String> musicTitle = new ArrayList<String>(); // 음악 제목 
	ArrayList<Integer> musicID = new ArrayList<Integer>();; // 음악 id 
	ArrayList<String> aGenre = new ArrayList<String>(); // 장르 

	Album(){}
	Album(int albumID){
		this.albumID = albumID;
	}
	Album(int albumID, String albumTitle, String albumType, String agency, String releaseCompany, Date releaseDate){
		this.albumID = albumID;
		this.albumTitle = albumTitle;
		this.albumType = albumType;
		this.agency = agency;
		this.releaseCompany = releaseCompany;
		this.releaseDate = releaseDate;
	}
}
