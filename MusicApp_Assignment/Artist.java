import java.util.ArrayList;

public class Artist {
	int artistID;
	String artistName;
	
	ArrayList<Music> musics = new ArrayList<Music>();
	ArrayList<Album> albums = new ArrayList<Album>();
	
	Artist(){}
	Artist(int artistID, String artistName){
		this.artistID = artistID;
		this.artistName = artistName;
	}
}
