import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	static int endApp = 0;
	static Connection con;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	
	
	public static int loginMenuCheck(User currentUser) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("               Menu                ");
		System.out.println(" 1. Log in                         ");
		System.out.println(" 2. Create account                 ");
		System.out.println("                                   ");
		System.out.println(" 0. Exit MusicApp                  ");
		System.out.println("-----------------------------------");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		int loginOK = 0;
		if(num == 0) {
			endApp = 1;
			loginOK = 1;
		}
		else if(num == 1)
			try {
				loginOK = login(currentUser);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Login'.");
			}
		else if(num == 2)
			try {
				createAccount();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Create account'.");
			}
		else {
			System.out.println("[ERROR] It is not allowed cmd!");
			loginOK = 0;
		}
		
		return loginOK;
	}
	public static int login(User currentUser) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("              Log in               ");
		System.out.println("-----------------------------------");
		
		int id = 0;
		String str_id = "";
		String pw = "";
		System.out.print("ID >> ");
		str_id = sc.nextLine();
		System.out.print("Password >> ");
		pw = sc.nextLine();
		try {
			id = Integer.parseInt(str_id);
		} catch(NumberFormatException e) {
			System.out.println("[ERROR] ID must be integer!");
			return 0;
		}
		
		currentUser.userID = -1;
		//////////////////////////////////////////////////////////////////
		String sql = "select * from USER where User_id = ? AND User_pw = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, pw);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			int index = 1;
			currentUser.userID = rs.getInt(index++);
			currentUser.userPW = rs.getString(index++);
			currentUser.userName = rs.getString(index++);
			currentUser.phone = rs.getString(index++);
			currentUser.ssn = rs.getString(index++);
			currentUser.address = rs.getString(index++);
			currentUser.isAdmin = rs.getInt(index++);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		if(currentUser.userID == -1) {
			System.out.println();
			System.out.println("[ERROR] Log in faild!");
			return 0;
		} else {
			System.out.println();
			System.out.println("Log in success! Hello~");
			return 1;
		}
	}
	public static int createAccount() throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Create account           ");
		System.out.println("-----------------------------------");
		
		int id = 0;
		//////////////////////////////////////////////////////////////////
		String sql = "select MAX(User_id) from USER";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int index = 1;
			id = rs.getInt(index++) + 1;
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		System.out.println("Your ID is [" + id + "]");
		System.out.println("Please don't forget this!");
		System.out.println("This number is your ID to use MusicApp.");
		
		String pw = "";
		String name = "";
		String phone = "";
		String ssn = "";
		String addr = "";
		int isAdmin = 0;
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Add please write this form");
			
			System.out.print("1. Password(less than 15 characters) >> ");
			pw = sc.nextLine();
			
			System.out.print("2. Name(less than 30 characters) >> ");
			name = sc.nextLine();
			
			System.out.print("3. Phone(less than 15 characters) >> ");
			phone = sc.nextLine();
			
			System.out.print("4. SSN(less than 15 characters) >> ");
			ssn = sc.nextLine();
			
			System.out.print("5. Address(less than 30 characters) >> ");
			addr = sc.nextLine();
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. ID      : " + id);
				System.out.println(" 2. Password: " + pw);
				System.out.println(" 3. Name    : " + name);
				System.out.println(" 4. Phone   : " + phone);
				System.out.println(" 5. SSN     : " + ssn);
				System.out.println(" 6. Address : " + addr);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		String adminCode = "0";
		System.out.println();
		System.out.println("If you are admin, please enter the AdminCode.");
		System.out.println("If you are not admin, please enter 0.");
		System.out.print(">> ");
		adminCode = sc.nextLine();
		
		if(adminCode.equals("2017029916")) {
			System.out.println("You are Admin!!");
			isAdmin = 1;
		} else isAdmin = 0;
		
		//////////////////////////////////////////////////////////////////
		sql = "insert into USER values (?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		ps.setString(2, pw);
		ps.setString(3, name);
		ps.setString(4, phone);
		ps.setString(5, ssn);
		ps.setString(6, addr);
		ps.setInt(7, isAdmin);
		ps.executeUpdate();
		
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("Now, account creation is complete.");
		System.out.println("Please log in from the menu.");
		
		return 0;
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void adminInterface(User user) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("            Admin Menu             ");
		System.out.println(" 1. Music Search                   ");
		System.out.println(" 2. Album Search    (Only search)  ");
		System.out.println(" 3. Artist Search   (Only search)  ");
		System.out.println(" 4. Playlist Search                ");
		System.out.println(" 5. Create Playlist                ");
		System.out.println(" 6. My Playlist                    ");
		System.out.println(" 7. Top Music                      ");
		System.out.println("                                   ");
		System.out.println(" 11. Music Registration            "); // 음악 등록 
		System.out.println(" 12. Album Registration            "); // 앨범 등록 
		System.out.println(" 13. Artist Registration           "); // 아티스트 등록 
		System.out.println("                                   ");
		System.out.println(" 21. Music Deletion                "); // 음악 삭제 
		System.out.println(" 22. Album Deletion                "); // 앨범 삭제 
		System.out.println(" 23. Artist Deletion               "); // 아티스트 삭제 
		System.out.println("                                   ");
		System.out.println(" 31. User Management               "); // 유저 관리 
		System.out.println("                                   ");
		System.out.println(" 0. Exit MusicApp                  ");
		System.out.println("-----------------------------------");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(num == 0) endApp = 1;
		else if(num == 1) { // 음악 검색 
			try {
				musicSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Music Search'.");
			}
		} else if(num == 2) { // 앨범 검색
			try {
				albumSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Album Search'.");
			}
		} else if(num == 3) { // 아티스트 검색 
			try {
				artistSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Artist Search'.");
			}
		} else if(num == 4) { // 플레이리스트 검색 
			try {
				playlistSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Playlist Search'.");
			}
		} else if(num == 5) { // 플레이리스트 제작 
			try {
				createPlaylist(user);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Create Playlist'.");
			}
		} else if(num == 6) { // 나의 플레이리스트
			try {
				myPlaylist(user);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'My Playlist'.");
			}
		} else if(num == 7) { // 음원 순위 
			try {
				topMusic();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Top Music'.");
			}
		} else if(num == 11) {
			try {
				musicRegistration();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Music Registration'.");
			}
		} else if(num == 12) {
			try {
				albumRegistration();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Album Registration'.");
			}
		} else if(num == 13) {
			try {
				artistRegistration();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Artist Registration'.");
			}
		} else if(num == 21) {
			try {
				musicDeletion();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Music Deletion'.");
			}
		} else if(num == 22) {
			try {
				albumDeletion();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Album Deletion'.");
			}
		} else if(num == 23) {
			try {
				artistDeletion();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Artist Deletion'.");
			}
		} else if(num == 31) {
			try {
				userManagement(user);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'User management'.");
			}
		} else { 
			System.out.println("[ERROR] It is not allowed cmd!");
		}
	}
	public static void userInterface(User user) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("               Menu                ");
		System.out.println(" 1. Music Search                   ");
		System.out.println(" 2. Album Search    (Only search)  ");
		System.out.println(" 3. Artist Search   (Only search)  ");
		System.out.println(" 4. Playlist Search                ");
		System.out.println(" 5. Create Playlist                ");
		System.out.println(" 6. My Playlist                    ");
		System.out.println(" 7. Top Music                      "); // 음원 통계 
		System.out.println("                                   ");
		System.out.println(" 0. Exit MusicApp                  ");
		System.out.println("-----------------------------------");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(num == 0) endApp = 1;
		else if(num == 1) { // 음악 검색 
			try {
				musicSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Music Search'.");
			}
		} else if(num == 2) { // 앨범 검색
			try {
				albumSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Album Search'.");
			}
		} else if(num == 3) { // 아티스트 검색 
			try {
				artistSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Artist Search'.");
			}
		} else if(num == 4) { // 플레이리스트 검색 
			try {
				playlistSearch();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Playlist Search'.");
			}
		} else if(num == 5) { // 플레이리스트 제작 
			try {
				createPlaylist(user);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Create Playlist'.");
			}
		} else if(num == 6) { // 나의 플레이리스트
			try {
				myPlaylist(user);
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'My Playlist'.");
			}
		} else if(num == 7) { // 음원 순위 
			try {
				topMusic();
			} catch (SQLException e) {
				System.out.println("[ERROR] An error occurred during 'Top Music'.");
			}
		} else { 
			System.out.println("[ERROR] It is not allowed cmd!");
		}
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void musicSearch() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("           Music Search            ");
		System.out.println("-----------------------------------");
		System.out.println("Enter the music title you want to find.");
		System.out.println("Just press enter and the all music will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		// DB에서 그 음악의 제목, 아티스트, 장르, 작사, 작곡, 편곡, 앨범 보여줌 
		// 재생버튼 구현 -> 재생하면 조회수 1증가 
		ArrayList<Music> musics = new ArrayList<Music>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from MUSIC where LOWER(Music_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Music tmpMusic = new Music();
			int index = 1;
			tmpMusic.musicID = rs.getInt(index++);
			tmpMusic.musicTitle = rs.getString(index++);
			tmpMusic.lyricist = rs.getString(index++);
			tmpMusic.composer = rs.getString(index++);
			tmpMusic.arranger = rs.getString(index++);
			tmpMusic.hitsNum = rs.getInt(index++);
			tmpMusic.albumIdx = rs.getInt(index++);
			musics.add(tmpMusic);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(musics.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any songs..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            Music list             ");
			System.out.println("  No.                   Music Title"); // 5칸, 30칸 
			for(int i=0; i<musics.size(); i++) {
				int no = i + 1;
				String tmpTitle = musics.get(i).musicTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the music.");
			System.out.println("And you can play the music!");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > musics.size()) { // 음악 영역 밖 
				System.out.println("[ERROR] There is no music!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 음악 선택 & 아티스트, 장르, 앨범 연결 
			Music tmpMusic = musics.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 앨범, 아티스트 연결 
			tmpMusic.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Album_title, Artist_name "
					+ "from MUSIC, MUSIC_MAKE, ARTIST, ALBUM "
					+ "where Music_id = ? AND Music_id = MM_Music_idx AND MM_Artist_idx = Artist_id AND Album_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			ps.setInt(2, tmpMusic.albumIdx);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.albumTitle = rs.getString(index++);
				tmpMusic.artistName.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpMusic.mGenre.clear(); // 중복 들어간거 제거 
			sql = "select MGenre "
					+ "from MUSIC_GENRE "
					+ "where MIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.mGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endMusicInfo = 0;
			while(endMusicInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Music info             ");
				System.out.println(" 1. Title   : " + tmpMusic.musicTitle);
				System.out.print(" 2. Artist  : ");
				for(String tmpArtist: tmpMusic.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre   : ");
				for(String tmpGenre: tmpMusic.mGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Lyricist: " + tmpMusic.lyricist);
				System.out.println(" 5. Composer: " + tmpMusic.composer);
				System.out.println(" 6. Arranger: " + tmpMusic.arranger);
				System.out.println(" 7. Hits    : " + tmpMusic.hitsNum);
				System.out.println(" 8. in Album: " + tmpMusic.albumTitle);
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Play to music: 1, Return to music list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 음악재생 
					tmpMusic.hitsNum = tmpMusic.hitsNum + 1;
					
					//////////////////////////////////////////////////////////////////
					sql = "update MUSIC set Hits_num = ? where Music_id = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.hitsNum);
					ps.setInt(2, tmpMusic.musicID);
					ps.executeUpdate();

					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println("                                   ");
					System.out.println("       ~~~~ Music play ~~~~        ");
					System.out.println("                                   ");
					System.out.println("Music has been played successfully!");
					System.out.println("Return to music info.");
				} else if(cmd == 2) { // music list로 돌아가기 
					endMusicInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // music info 끝 
		} // while 끝 
	}
	public static void albumSearch() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("           Album Search            ");
		System.out.println("-----------------------------------");
		System.out.println("Enter the album title you want to find.");
		System.out.println("Just press enter and the all album will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		// DB에서 그 앨범의 제목, 아티스트, 앨범유형, 발매일, 장르, 발매사, 기획사 보여줌 
		// 그 뒤에 음악 목록 쭉 보여줌 
		ArrayList<Album> albums = new ArrayList<Album>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from ALBUM where LOWER(Album_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Album tmpAlbum = new Album();
			int index = 1;
			tmpAlbum.albumID = rs.getInt(index++);
			tmpAlbum.albumTitle = rs.getString(index++);
			tmpAlbum.albumType = rs.getString(index++);
			tmpAlbum.agency = rs.getString(index++);
			tmpAlbum.releaseCompany = rs.getString(index++);
			tmpAlbum.releaseDate = rs.getDate(index++);
			albums.add(tmpAlbum);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(albums.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any albums..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            Album list             ");
			System.out.println("  No.                   Album Title"); // 5칸, 30칸 
			for(int i=0; i<albums.size(); i++) {
				int no = i + 1;
				String tmpTitle = albums.get(i).albumTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the album.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > albums.size()) { // 앨범 영역 밖 
				System.out.println("[ERROR] There is no album!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 앨범 선택 & 아티스트, 장르, 음악 연결 
			Album tmpAlbum = albums.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 아티스트 연결 
			tmpAlbum.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Artist_name "
					+ "from ALBUM, ALBUM_MAKE, ARTIST "
					+ "where Album_id = ? AND Album_id = AM_Album_idx AND AM_Artist_idx = Artist_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.artistName.add(rs.getString(index++));
			}
			
			// 음악 연결 
			tmpAlbum.musicTitle.clear(); // 중복 들어간거 제거 
			sql = "select Music_title "
					+ "from MUSIC "
					+ "where Album_idx = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.musicTitle.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpAlbum.aGenre.clear(); // 중복 들어간거 제거 
			sql = "select AGenre "
					+ "from ALBUM_GENRE "
					+ "where AIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.aGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endAlbumInfo = 0;
			while(endAlbumInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Album info             ");
				System.out.println(" 1. Title          : " + tmpAlbum.albumTitle);
				System.out.print(" 2. Artist         : ");
				for(String tmpArtist: tmpAlbum.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre          : ");
				for(String tmpGenre: tmpAlbum.aGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Type           : " + tmpAlbum.albumType);
				System.out.println(" 5. Agency         : " + tmpAlbum.agency);
				System.out.println(" 6. Release Company: " + tmpAlbum.releaseCompany);
				System.out.println(" 7. Release Date   : " + dateFormat.format(tmpAlbum.releaseDate));
				System.out.println("                     ");
				System.out.println(" There is " + tmpAlbum.musicTitle.size() + " music(s) in the album");
				for(String tmpMusic: tmpAlbum.musicTitle) {
					System.out.println(" -> " + tmpMusic);
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Return to album list: 1)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // album list로 돌아가기 
					endAlbumInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // album info 끝 
		} // while 끝 
	}
	public static void artistSearch() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("           Artist Search           ");
		System.out.println("-----------------------------------");
		System.out.println("Enter the artist name you want to find.");
		System.out.println("Just press enter and the all artist will be shown.");
		System.out.print(">> ");
		
		String name = "";
		name = sc.nextLine();
	
		// DB에서 그 아티스트의 음원(제목, 앨범) 쭉 보여줌
		ArrayList<Artist> artists = new ArrayList<Artist>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from Artist where LOWER(Artist_name) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		name = "%" + name + "%";
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Artist tmpArtist = new Artist();
			int index = 1;
			tmpArtist.artistID = rs.getInt(index++);
			tmpArtist.artistName = rs.getString(index++);
			artists.add(tmpArtist);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(artists.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any artist..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("           Artist List             ");
			System.out.println("  No.                   Artist Name"); // 5칸, 30칸 
			for(int i=0; i<artists.size(); i++) {
				int no = i + 1;
				String tmpName = artists.get(i).artistName;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpName));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the artist.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > artists.size()) { // 영역 밖 
				System.out.println("[ERROR] There is no artist!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 아티스트 선택 & 아티스트, 음악 연결 -> 음악, 앨범 연결 
			Artist tmpArtist = artists.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 음악, 앨범 연결 
			tmpArtist.musics.clear(); // 중복 들어간거 제거 
			sql = "select Music_title, Album_title "
					+ "from ARTIST, MUSIC_MAKE, MUSIC, ALBUM "
					+ "where Artist_id = ? AND Artist_id = MM_Artist_idx AND MM_Music_idx = Music_id AND Album_idx = Album_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpArtist.artistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpArtist.musics.add(new Music(rs.getString(index++)));
				tmpArtist.musics.get(tmpArtist.musics.size()-1).albumTitle = rs.getString(index++);
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endArtistInfo = 0;
			while(endArtistInfo != 1) {
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("           Artist info             ");
				System.out.println(" 1. Name: " + tmpArtist.artistName);
				System.out.println("                                   ");
				System.out.println(" There is " + tmpArtist.musics.size() + " music(s) in the playlist");
				if(tmpArtist.musics.size() != 0) {
					System.out.println("    Music / Album");
					for(Music tmpMusic: tmpArtist.musics) {
						System.out.println(" -> " + tmpMusic.musicTitle + " / " + tmpMusic.albumTitle);
					}
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Return to artist list: 1)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // artist list로 돌아가기 
					endArtistInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // artist info 끝 
		} // while 끝 
	}
	public static void playlistSearch() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Playlist Search          ");
		System.out.println("-----------------------------------");
		System.out.println("Enter the playlist title you want to find.");
		System.out.println("Just press enter and the all playlist will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
	
		// DB에서 그 플레이리스트의 이름, 전체 곡수, 만든유저, 보여줌 
		// 그 뒤 음원(제목, 아티스트, 앨범) 쭉 보여줌 
		// 음악 전체재생 -> 모든 음악 조회수 1증가 
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from PLAYLIST where LOWER(Playlist_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Playlist tmpPlaylist = new Playlist();
			int index = 1;
			tmpPlaylist.playlistID = rs.getInt(index++);
			tmpPlaylist.playlistTitle = rs.getString(index++);
			tmpPlaylist.creationDate = rs.getDate(index++);
			tmpPlaylist.userIdx = rs.getInt(index++);
			playlists.add(tmpPlaylist);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(playlists.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any playlist..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("          Playlist list            ");
			System.out.println("  No.                Playlist Title"); // 5칸, 30칸 
			for(int i=0; i<playlists.size(); i++) {
				int no = i + 1;
				String tmpTitle = playlists.get(i).playlistTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the playlist.");
			System.out.println("And you can play the music!");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > playlists.size()) { // 영역 밖 
				System.out.println("[ERROR] There is no playlist!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 유저 이름 & 음악 목록 
			Playlist tmpPlaylist = playlists.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 유저 연결 
			sql = "select User_name "
					+ "from PLAYLIST, USER "
					+ "where Playlist_id = ? AND User_idx = User_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpPlaylist.userName = rs.getString(index++);
			}
			
			// 음악, 앨범 연결  
			tmpPlaylist.musics.clear();
			sql = "select Music_id, Music_title, Album_title "
					+ "from PLAYLIST, MUSIC_IN_LIST, MUSIC, ALBUM "
					+ "where Playlist_id = ? AND MIL_Playlist_idx = Playlist_id AND MIL_Music_idx = Music_id AND Album_idx = Album_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpPlaylist.musics.add(new Music(rs.getInt(index++), rs.getString(index++), rs.getString(index++)));
			}
			
			// 음악 -> 아티스트 연결 
			for(Music tmpMusic: tmpPlaylist.musics) {
				tmpMusic.artistName.clear();
				sql = "select Artist_name "
						+ "from MUSIC, MUSIC_MAKE, ARTIST "
						+ "where Music_id = ? AND MM_Music_idx = Music_id AND MM_Artist_idx = Artist_id";
				ps = con.prepareStatement(sql);
				ps.setInt(1, tmpMusic.musicID);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int index = 1;
					tmpMusic.artistName.add(rs.getString(index++));
				}
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endPlaylistInfo = 0;
			while(endPlaylistInfo != 1) {
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("           Playlist info           ");
				System.out.println(" 1. Title        : " + tmpPlaylist.playlistTitle);
				System.out.println(" 2. Creator      : " + tmpPlaylist.userName);
				System.out.println(" 3. Creation Date: " + dateFormat.format(tmpPlaylist.creationDate));
				System.out.println("                                   ");
				System.out.println(" There is " + tmpPlaylist.musics.size() + " music(s) in the playlist");
				if(tmpPlaylist.musics.size() != 0) {
					System.out.println("    Music / Album / Artist");
					for(Music tmpMusic: tmpPlaylist.musics) {
						String str_artist = "";
						System.out.print(" -> " + tmpMusic.musicTitle + " / " + tmpMusic.albumTitle + " / ");
						for(String tmpArtist: tmpMusic.artistName) {
							str_artist += tmpArtist + ", ";
						}
						System.out.println(str_artist.substring(0, str_artist.length()-2));
					}
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Play all music: 1, Return to playlist list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 전체 음악재생 
					for(Music tmpMusic: tmpPlaylist.musics) {
						
						//////////////////////////////////////////////////////////////////
						sql = "update MUSIC set Hits_num = Hits_num + 1 where Music_id = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();

						ps.close();
						//////////////////////////////////////////////////////////////////
						
					}
					if(tmpPlaylist.musics.size() == 0) {
						System.out.println("                                   ");
						System.out.println("No music in the playlist!");
					} else {
						System.out.println("                                   ");
						System.out.println("       ~~~~ Music play ~~~~        ");
						System.out.println("                                   ");
						System.out.println("Music has been played successfully!");
					}
					System.out.println("Return to playlist info.");
				} else if(cmd == 2) { // playlist list로 돌아가기 
					endPlaylistInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // playlist info 끝 
		} // while 끝 
	}
	public static void createPlaylist(User user) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Create Playlist          ");
		System.out.println("-----------------------------------");
		
		// 플레이리스트 제목입력
		// (user는 지금 사용자, 만든날짜 현재날짜) 
		// 플레이리스트 만들기 
		Playlist playlist = new Playlist();
		playlist.userIdx = user.userID;
		playlist.creationDate = new Date();
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write the title of the playlist you want to make.");
			
			System.out.print("1. Title(less than 30 characters) >> ");
			playlist.playlistTitle = sc.nextLine();
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. Title   : " + playlist.playlistTitle);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		//////////////////////////////////////////////////////////////////
		String sql = "select MAX(Playlist_id) from PLAYLIST";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			int index = 1;
			playlist.playlistID = rs.getInt(index++) + 1;
		}
		ps.close();
		
		sql = "insert into PLAYLIST values (?,?,?,?)";
		ps = con.prepareStatement(sql);
		ps.setInt(1, playlist.playlistID);
		ps.setString(2, playlist.playlistTitle);
		ps.setString(3, dateFormat.format(playlist.creationDate));
		ps.setInt(4, playlist.userIdx);
		ps.executeUpdate();
		
		ps.close();
		rs.close();
		//////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("Now, playlist creation is complete.");
		System.out.println("Do you want to add music in this playlist?");
		System.out.println("Please press the command. (Yes: 1, No(Go to menu): 0)");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int cmd = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(cmd == 0) return;
		else if(cmd == 1) { // 지금 만든 플레이리스트에 음악 추가 
			addMusicInPlaylist(playlist);
		} else {
			System.out.println("[ERROR] It is not allowed cmd!");
			System.out.println("Go to menu..");
			return;
		}
	}
	public static void myPlaylist(User user) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		// 나의 플레이리스트 이름 쭉 보여줌 
		// 그 중 하나 선택!
		// 그 플레이리스트 정보 보여줌 (Playlist search랑 비슷한 동작..) 
		// 거기서 곡 추가, 곡 삭제, 플레이리스트 이름 변경, 플레이리스트 삭제 기능 구현 
		ArrayList<Playlist> playlists = new ArrayList<Playlist>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from PLAYLIST where User_idx = ?"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, user.userID);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Playlist tmpPlaylist = new Playlist();
			int index = 1;
			tmpPlaylist.playlistID = rs.getInt(index++);
			tmpPlaylist.playlistTitle = rs.getString(index++);
			tmpPlaylist.creationDate = rs.getDate(index++);
			tmpPlaylist.userIdx = rs.getInt(index++);
			playlists.add(tmpPlaylist);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(playlists.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("There is no playlist..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            My Playlist            ");
			System.out.println("  No.                Playlist Title"); // 5칸, 30칸 
			for(int i=0; i<playlists.size(); i++) {
				int no = i + 1;
				String tmpTitle = playlists.get(i).playlistTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the playlist.");
			System.out.println("And you can play the music or edit the playlist!");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > playlists.size()) {
				System.out.println("[ERROR] There is no playlist!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 유저 이름 & 음악 목록 
			Playlist tmpPlaylist = playlists.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 유저 연결 
			sql = "select User_name "
					+ "from PLAYLIST, USER "
					+ "where Playlist_id = ? AND User_idx = User_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpPlaylist.userName = rs.getString(index++);
			}
			
			// 음악, 앨범 연결  
			tmpPlaylist.musics.clear();
			sql = "select Music_id, Music_title, Album_title "
					+ "from PLAYLIST, MUSIC_IN_LIST, MUSIC, ALBUM "
					+ "where Playlist_id = ? AND MIL_Playlist_idx = Playlist_id AND MIL_Music_idx = Music_id AND Album_idx = Album_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpPlaylist.musics.add(new Music(rs.getInt(index++), rs.getString(index++), rs.getString(index++)));
			}
			
			// 음악 -> 아티스트 연결 
			for(Music tmpMusic: tmpPlaylist.musics) {
				tmpMusic.artistName.clear();
				sql = "select Artist_name "
						+ "from MUSIC, MUSIC_MAKE, ARTIST "
						+ "where Music_id = ? AND MM_Music_idx = Music_id AND MM_Artist_idx = Artist_id";
				ps = con.prepareStatement(sql);
				ps.setInt(1, tmpMusic.musicID);
				rs = ps.executeQuery();
				
				while(rs.next()) {
					int index = 1;
					tmpMusic.artistName.add(rs.getString(index++));
				}
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endPlaylistInfo = 0;
			while(endPlaylistInfo != 1) {
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("           Playlist info           ");
				System.out.println(" 1. Title        : " + tmpPlaylist.playlistTitle);
				System.out.println(" 2. Creator      : " + tmpPlaylist.userName);
				System.out.println(" 3. Creation Date: " + dateFormat.format(tmpPlaylist.creationDate));
				System.out.println("                                   ");
				System.out.println(" There is " + tmpPlaylist.musics.size() + " music(s) in the playlist");
				if(tmpPlaylist.musics.size() != 0) {
					System.out.println("    Music / Album / Artist");
					for(Music tmpMusic: tmpPlaylist.musics) {
						String str_artist = "";
						System.out.print(" -> " + tmpMusic.musicTitle + " / " + tmpMusic.albumTitle + " / ");
						for(String tmpArtist: tmpMusic.artistName) {
							str_artist += tmpArtist + ", ";
						}
						System.out.println(str_artist.substring(0, str_artist.length()-2));
					}
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Play all music: 1, Edit to Playlist: 2, Return to playlist list: 3)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 전체 음악재생 
					for(Music tmpMusic: tmpPlaylist.musics) {
						
						//////////////////////////////////////////////////////////////////
						sql = "update MUSIC set Hits_num = Hits_num + 1 where Music_id = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();

						ps.close();
						//////////////////////////////////////////////////////////////////
						
					}
					if(tmpPlaylist.musics.size() == 0) {
						System.out.println("                                   ");
						System.out.println("No music in the playlist!");
					} else {
						System.out.println("                                   ");
						System.out.println("       ~~~~ Music play ~~~~        ");
						System.out.println("                                   ");
						System.out.println("Music has been played successfully!");
					}
					System.out.println("Return to playlist info.");
				} else if(cmd == 2) { 
					// Edit!!
					// 플레이리스트 이름 변경, 음악 추가, 음악 제거, 플레이리스트 삭제 
					// 1. renamePlaylist
					// 2. addMusicInPlaylist
					// 3. deleteMusicInPlaylist
					// 4. deletePlaylist
					System.out.println();
					System.out.println("What do you want to do?");
					System.out.println(" 1. Rename playlist");
					System.out.println(" 2. Add music in playlist");
					System.out.println(" 3. Delete music in playlist");
					System.out.println(" 4. Delete playlist");
					System.out.println();
					System.out.println(" 0. Return to playlist info");
					System.out.print(">> ");
					
					while (!sc.hasNextInt()) {
						sc.next(); 
						System.out.print(">> ");
					}
					int cmd2 = sc.nextInt();
					sc.nextLine(); // enter 
					
					if(cmd2 == 0) continue;
					else if(cmd2 == 1) renamePlaylist(tmpPlaylist);
					else if(cmd2 == 2) addMusicInPlaylist(tmpPlaylist);
					else if(cmd2 == 3) deleteMusicInPlaylist(tmpPlaylist);
					else if(cmd2 == 4) {
						if(deletePlaylist(playlists, tmpPlaylist) == 0) continue;
					}
					else {
						System.out.println("[ERROR] It is not allowed cmd!");
						System.out.println("Go to menu..");
						return;
					}
					
					System.out.println("                                   ");
					System.out.println("Playlist has been edited successfully!");
					System.out.println("Press the enter key to return to the main menu.");
					sc.nextLine();
					return;
				} else if(cmd == 3) { // playlist list로 돌아가기 
					endPlaylistInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // playlist info 끝 
		} // while 끝 
	}
	public static void topMusic() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("             Top Music             ");
		System.out.println(" 1. Top 10                         ");
		System.out.println(" 2. Top 100                        ");
		System.out.println(" 3. Top 200                        ");
		System.out.println("-----------------------------------");
		System.out.println("Enter 0 to go to the menu.");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int check = sc.nextInt();
		sc.nextLine(); // enter 
		
		// Top10, Top100, Top200 중에 하나 선택 
		// 선택한 것에 맞게 조회수기준 정렬하여 출력 
		// 출력 갯수 세팅 
		int printSize = 0;
		if(check == 0) return;
		else if(check == 1) printSize = 10;
		else if(check == 2) printSize = 100;
		else if(check == 3) printSize = 200;
		else {
			System.out.println("[ERROR] It is not allowed cmd!");
			System.out.println("Go to menu..");
			return;
		}
		
		while(true) {
			ArrayList<Music> musics = new ArrayList<Music>();
			//////////////////////////////////////////////////////////////////
			String sql = "select * from MUSIC order by Hits_num desc";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				Music tmpMusic = new Music();
				int index = 1;
				tmpMusic.musicID = rs.getInt(index++);
				tmpMusic.musicTitle = rs.getString(index++);
				tmpMusic.lyricist = rs.getString(index++);
				tmpMusic.composer = rs.getString(index++);
				tmpMusic.arranger = rs.getString(index++);
				tmpMusic.hitsNum = rs.getInt(index++);
				tmpMusic.albumIdx = rs.getInt(index++);
				musics.add(tmpMusic);
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			System.out.println();
			
			if(musics.isEmpty()) { // 리스트가 비어있는 경우 
				System.out.println("I couldn't find any songs..");
				System.out.println("Press the enter key to return to the main menu.");
				sc.nextLine();
				return;
			}
			
			System.out.println("-----------------------------------");
			System.out.println("            Music list             ");
			System.out.println("  No. Music Title / Hits"); // 5칸, 30칸 
			for(int i=0; (i<musics.size()) && (i<printSize); i++) {
				int no = i + 1;
				String tmpTitle = musics.get(i).musicTitle;
				System.out.println(String.format("%5d", no) + " " + String.format("%s", tmpTitle) + " / " + musics.get(i).hitsNum);
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the music.");
			System.out.println("And you can play the music!");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > musics.size()) { // 음악 영역 밖 
				System.out.println("[ERROR] There is no music!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 음악 선택 & 아티스트, 장르, 앨범 연결 
			Music tmpMusic = musics.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 앨범, 아티스트 연결 
			tmpMusic.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Album_title, Artist_name "
					+ "from MUSIC, MUSIC_MAKE, ARTIST, ALBUM "
					+ "where Music_id = ? AND Music_id = MM_Music_idx AND MM_Artist_idx = Artist_id AND Album_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			ps.setInt(2, tmpMusic.albumIdx);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.albumTitle = rs.getString(index++);
				tmpMusic.artistName.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpMusic.mGenre.clear(); // 중복 들어간거 제거 
			sql = "select MGenre "
					+ "from MUSIC_GENRE "
					+ "where MIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.mGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endMusicInfo = 0;
			while(endMusicInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Music info             ");
				System.out.println(" 1. Title   : " + tmpMusic.musicTitle);
				System.out.print(" 2. Artist  : ");
				for(String tmpArtist: tmpMusic.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre   : ");
				for(String tmpGenre: tmpMusic.mGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Lyricist: " + tmpMusic.lyricist);
				System.out.println(" 5. Composer: " + tmpMusic.composer);
				System.out.println(" 6. Arranger: " + tmpMusic.arranger);
				System.out.println(" 7. Hits    : " + tmpMusic.hitsNum);
				System.out.println(" 8. in Album: " + tmpMusic.albumTitle);
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Play to music: 1, Return to music list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 음악재생 
					tmpMusic.hitsNum = tmpMusic.hitsNum + 1;
					
					//////////////////////////////////////////////////////////////////
					sql = "update MUSIC set Hits_num = ? where Music_id = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.hitsNum);
					ps.setInt(2, tmpMusic.musicID);
					ps.executeUpdate();

					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println("                                   ");
					System.out.println("       ~~~~ Music play ~~~~        ");
					System.out.println("                                   ");
					System.out.println("Music has been played successfully!");
					System.out.println("Return to music info.");
				} else if(cmd == 2) { // music list로 돌아가기 
					endMusicInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // music info 끝 
		} // while 끝 
	}
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void renamePlaylist(Playlist tmpPlaylist) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write the title of the playlist you want to rename.");
			
			System.out.print("1. Title(less than 30 characters) >> ");
			tmpPlaylist.playlistTitle = sc.nextLine();
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. Title   : " + tmpPlaylist.playlistTitle);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		//////////////////////////////////////////////////////////////////
		String sql = "update PLAYLIST set Playlist_title = ? where Playlist_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tmpPlaylist.playlistTitle);
		ps.setInt(2, tmpPlaylist.playlistID);
		ps.executeUpdate();

		ps.close();
		//////////////////////////////////////////////////////////////////
	}
	public static void addMusicInPlaylist(Playlist tmpPlaylist) throws SQLException { // 플레이리스트에 음악 추가 
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("             Add Music             ");
		System.out.println("-----------------------------------");
		System.out.println("Enter the music title you want to add.");
		System.out.println("Just press enter and the all music will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		// DB에서 그 음악의 제목, 아티스트, 장르, 작사, 작곡, 편곡, 앨범 보여줌 
		ArrayList<Music> musics = new ArrayList<Music>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from MUSIC where LOWER(Music_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Music tmpMusic = new Music();
			int index = 1;
			tmpMusic.musicID = rs.getInt(index++);
			tmpMusic.musicTitle = rs.getString(index++);
			tmpMusic.lyricist = rs.getString(index++);
			tmpMusic.composer = rs.getString(index++);
			tmpMusic.arranger = rs.getString(index++);
			tmpMusic.hitsNum = rs.getInt(index++);
			tmpMusic.albumIdx = rs.getInt(index++);
			musics.add(tmpMusic);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(musics.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any songs..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            Music list             ");
			System.out.println("  No.                   Music Title"); // 5칸, 30칸 
			for(int i=0; i<musics.size(); i++) {
				int no = i + 1;
				String tmpTitle = musics.get(i).musicTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the music.");
			System.out.println("And you can add the music!");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > musics.size()) { // 음악 영역 밖 
				System.out.println("[ERROR] There is no music!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 음악 선택 & 아티스트, 장르, 앨범 연결 
			Music tmpMusic = musics.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 앨범, 아티스트 연결 
			tmpMusic.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Album_title, Artist_name "
					+ "from MUSIC, MUSIC_MAKE, ARTIST, ALBUM "
					+ "where Music_id = ? AND Music_id = MM_Music_idx AND MM_Artist_idx = Artist_id AND Album_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			ps.setInt(2, tmpMusic.albumIdx);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.albumTitle = rs.getString(index++);
				tmpMusic.artistName.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpMusic.mGenre.clear(); // 중복 들어간거 제거 
			sql = "select MGenre "
					+ "from MUSIC_GENRE "
					+ "where MIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.mGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endMusicInfo = 0;
			while(endMusicInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Music info             ");
				System.out.println(" 1. Title   : " + tmpMusic.musicTitle);
				System.out.print(" 2. Artist  : ");
				for(String tmpArtist: tmpMusic.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre   : ");
				for(String tmpGenre: tmpMusic.mGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Lyricist: " + tmpMusic.lyricist);
				System.out.println(" 5. Composer: " + tmpMusic.composer);
				System.out.println(" 6. Arranger: " + tmpMusic.arranger);
				System.out.println(" 7. Hits    : " + tmpMusic.hitsNum);
				System.out.println(" 8. in Album: " + tmpMusic.albumTitle);
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Add to music: 1, Return to music list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 음악 넣기 
					
					//////////////////////////////////////////////////////////////////
					sql = "insert into MUSIC_IN_LIST " // 음악 중복 검사 
							+ "select ?,? "
							+ "from DUAL "
							+ "where NOT EXISTS ( "
							+ " select * "
							+ " from MUSIC_IN_LIST "
							+ " where MIL_Playlist_idx = ? AND MIL_Music_idx = ?)";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpPlaylist.playlistID);
					ps.setInt(2, tmpMusic.musicID);
					ps.setInt(3, tmpPlaylist.playlistID);
					ps.setInt(4, tmpMusic.musicID);
					ps.executeUpdate();

					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println();
					System.out.println("Music successfully added!");
					System.out.println("Return to music list.");
					endMusicInfo = 1;
				} else if(cmd == 2) {
					endMusicInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // music info 끝 
		} // while 끝 
	}
	public static void deleteMusicInPlaylist(Playlist tmpPlaylist) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		int endPlaylistInfo = 0;
		while(endPlaylistInfo != 1) {
			System.out.println();
			System.out.println("-----------------------------------");
			System.out.println("         Music In Playlist         ");
			System.out.println("  No.                   Music Title"); // 5칸, 30칸 
			for(int i=0; i<tmpPlaylist.musics.size(); i++) {
				int no = i + 1;
				String tmpTitle = tmpPlaylist.musics.get(i).musicTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to delete the music.");
			System.out.println("Enter 0 to exit.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int cmd = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(cmd == 0) endPlaylistInfo = 1;
			else if(cmd > tmpPlaylist.musics.size()) { 
				System.out.println("[ERROR] There is no music!");
				System.out.println("Automatically exit...");
				return;
			} else { 
				Music tmpMusic = tmpPlaylist.musics.get(cmd-1);
				
				// MUSIC_IN_LIST에서 삭제 
				//////////////////////////////////////////////////////////////////
				String sql = "delete from MUSIC_IN_LIST "
						+ "where MIL_Playlist_idx = ? AND MIL_Music_idx = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, tmpPlaylist.playlistID);
				ps.setInt(2, tmpMusic.musicID);
				ps.executeUpdate();

				ps.close();
				//////////////////////////////////////////////////////////////////
				
				tmpPlaylist.musics.remove(tmpMusic);
				System.out.println("                                   ");
				System.out.println("Music has been deleted successfully!");
			}
		} // playlist info 끝 
	}
	public static int deletePlaylist(ArrayList<Playlist> playlists, Playlist tmpPlaylist) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("Are you sure you want to delete the playlist? (Yes: 1, No:2)");
		System.out.print(">> ");
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(num == 2) {
			System.out.println();
			System.out.println("OK! I do not delete this playlist.");
			return 0;
		} else if(num != 1) {
			System.out.println("[ERROR] It is not allowed cmd!");
			System.out.println("Automatically exit...");
			return 0;
		}
		
		// 플레이리스트 삭제 
		//////////////////////////////////////////////////////////////////
		// MUSIC_IN_LIST 안에 데이터 삭제 
		String sql = "delete from MUSIC_IN_LIST where MIL_Playlist_idx = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, tmpPlaylist.playlistID);
		ps.executeUpdate();
		ps.close();
		
		// PLAYLIST 삭제 
		sql = "delete from PLAYLIST where Playlist_id = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpPlaylist.playlistID);
		ps.executeUpdate();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		playlists.remove(tmpPlaylist);
		System.out.println("                              ");
		System.out.println("Playlist successfully removed!");
		return 1;
	}

	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void musicRegistration() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("        Music Registration         ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the title for the album you want to put music in.");
		System.out.println("Just press enter and the all album will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		ArrayList<Album> albums = new ArrayList<Album>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from ALBUM where LOWER(Album_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Album tmpAlbum = new Album();
			int index = 1;
			tmpAlbum.albumID = rs.getInt(index++);
			tmpAlbum.albumTitle = rs.getString(index++);
			tmpAlbum.albumType = rs.getString(index++);
			tmpAlbum.agency = rs.getString(index++);
			tmpAlbum.releaseCompany = rs.getString(index++);
			tmpAlbum.releaseDate = rs.getDate(index++);
			albums.add(tmpAlbum);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(albums.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any albums..");
			System.out.println("Please make an album first.");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		System.out.println("-----------------------------------");
		System.out.println("            Album list             ");
		System.out.println("  No.                   Album Title"); // 5칸, 30칸 
		for(int i=0; i<albums.size(); i++) {
			int no = i + 1;
			String tmpTitle = albums.get(i).albumTitle;
			System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
		}
		System.out.println("-----------------------------------");
		System.out.println("Please select an album for the song. (Enter No.)");
		System.out.println("Enter 0 to go to the menu.");
		System.out.print(">> ");
		
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(num == 0) return;
		if(num > albums.size()) { // 앨범 영역 밖 
			System.out.println("[ERROR] There is no album!");
			System.out.println("Go to menu..");
			return;
		}
		
		// 앨범 선택 
		Album tmpAlbum = albums.get(num-1);
		Music tmpMusic = new Music();
		
		//////////////////////////////////////////////////////////////////
		// 아티스트 연결 
		tmpAlbum.artistName.clear(); // 중복 들어간거 제거
		tmpAlbum.artistID.clear();
		sql = "select Artist_id, Artist_name "
				+ "from ALBUM, ALBUM_MAKE, ARTIST "
				+ "where Album_id = ? AND Album_id = AM_Album_idx AND AM_Artist_idx = Artist_id";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpAlbum.albumID);
		rs = ps.executeQuery();

		while(rs.next()) {
			int index = 1;
			tmpAlbum.artistID.add(rs.getInt(index++));
			tmpAlbum.artistName.add(rs.getString(index++));
		}
		
		// 음악 연결 
		tmpAlbum.musicTitle.clear(); // 중복 들어간거 제거 
		sql = "select Music_title "
				+ "from MUSIC "
				+ "where Album_idx = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpAlbum.albumID);
		rs = ps.executeQuery();

		while(rs.next()) {
			int index = 1;
			tmpAlbum.musicTitle.add(rs.getString(index++));
		}
		
		// 장르 연결 
		tmpAlbum.aGenre.clear(); // 중복 들어간거 제거 
		sql = "select AGenre "
				+ "from ALBUM_GENRE "
				+ "where AIndex = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpAlbum.albumID);
		rs = ps.executeQuery();

		while(rs.next()) {
			int index = 1;
			tmpAlbum.aGenre.add(rs.getString(index++));
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		String str_artist = "";
		String str_genre = "";
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("            Album info             ");
		System.out.println(" 1. Title          : " + tmpAlbum.albumTitle);
		System.out.print(" 2. Artist         : ");
		for(String tmpArtist: tmpAlbum.artistName) {
			str_artist += tmpArtist + ", ";
		}
		System.out.println(str_artist.substring(0, str_artist.length()-2));
		System.out.print(" 3. Genre          : ");
		for(String tmpGenre: tmpAlbum.aGenre) {
			str_genre += tmpGenre + "/";
		}
		System.out.println(str_genre.substring(0, str_genre.length()-1));
		System.out.println(" 4. Type           : " + tmpAlbum.albumType);
		System.out.println(" 5. Agency         : " + tmpAlbum.agency);
		System.out.println(" 6. Release Company: " + tmpAlbum.releaseCompany);
		System.out.println(" 7. Release Date   : " + dateFormat.format(tmpAlbum.releaseDate));
		System.out.println("                     ");
		System.out.println(" There is " + tmpAlbum.musicTitle.size() + " music(s) in the album");
		for(String tmpMusicTitle: tmpAlbum.musicTitle) {
			System.out.println(" -> " + tmpMusicTitle);
		}
		System.out.println("-----------------------------------");
		
		//////////////////////////////////////////////////////////////////
		sql = "select MAX(Music_id) from MUSIC";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			int index = 1;
			tmpMusic.musicID = rs.getInt(index++) + 1;
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write this form");
			
			System.out.print("1. Music Title(less than 30 characters) >> ");
			tmpMusic.musicTitle = sc.nextLine();
			
			System.out.print("2. Lyricist(less than 30 characters) >> ");
			tmpMusic.lyricist = sc.nextLine();
			
			System.out.print("3. Composer(less than 30 characters) >> ");
			tmpMusic.composer = sc.nextLine();
			
			System.out.print("4. Arranger(less than 30 characters) >> ");
			tmpMusic.arranger = sc.nextLine();
			
			System.out.print("5. Genre(Split by slash. ex) Hip Hop/Rap/Ballad) >> ");
			String tmpGenre = sc.nextLine(); tmpMusic.mGenre.clear();
			StringTokenizer st = new StringTokenizer(tmpGenre, "/");
			while(st.hasMoreElements()) tmpMusic.mGenre.add(st.nextToken());
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. Music Title: " + tmpMusic.musicTitle);
				System.out.println(" 2. Lyricist   : " + tmpMusic.lyricist);
				System.out.println(" 3. Composer   : " + tmpMusic.composer);
				System.out.println(" 4. Arranger   : " + tmpMusic.arranger);
				System.out.println(" 5. Genre      : " + tmpGenre);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		//////////////////////////////////////////////////////////////////
		// 음악 추가 
		sql = "insert into MUSIC values (?,?,?,?,?,?,?)";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpMusic.musicID);
		ps.setString(2, tmpMusic.musicTitle);
		ps.setString(3, tmpMusic.lyricist);
		ps.setString(4, tmpMusic.composer);
		ps.setString(5, tmpMusic.arranger);
		ps.setInt(6, 0);
		ps.setInt(7, tmpAlbum.albumID);
		ps.executeUpdate();
		ps.close();
		
		// 장르 추가 
		for(String tmpGenre: tmpMusic.mGenre) {
			sql = "insert into MUSIC_GENRE values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			ps.setString(2, tmpGenre);
			ps.executeUpdate();
			ps.close();
		}
		
		// 아티스트 추가 
		for(Integer tmpArtist: tmpAlbum.artistID) {
			sql = "insert into MUSIC_MAKE values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpArtist);
			ps.setInt(2, tmpMusic.musicID);
			ps.executeUpdate();
			ps.close();
		}
		//////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("Now, music creation is complete.");
		System.out.println("Go to menu..");
		return;
	}
	@SuppressWarnings("deprecation")
	public static void albumRegistration() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("        Album Registration         ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the name of the artist create the album.");
		System.out.println("Just press enter and the all artist will be shown.");
		System.out.print(">> ");
		
		String name = "";
		name = sc.nextLine();
		
		ArrayList<Artist> artists = new ArrayList<Artist>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from ARTIST where LOWER(Artist_name) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		name = "%" + name + "%";
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Artist tmpArtist = new Artist();
			int index = 1;
			tmpArtist.artistID = rs.getInt(index++);
			tmpArtist.artistName = rs.getString(index++);
			artists.add(tmpArtist);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(artists.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any artists..");
			System.out.println("Please make an artist first.");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		// 아티스트 고르기 
		Album tmpAlbum = new Album();
		
		System.out.println("-----------------------------------");
		System.out.println("            Artist list            ");
		System.out.println("  No.                  Artist Title"); // 5칸, 30칸 
		for(int i=0; i<artists.size(); i++) {
			int no = i + 1;
			String tmpName = artists.get(i).artistName;
			System.out.println(String.format("%5d", no) + String.format("%30s", tmpName));
		}
		System.out.println("-----------------------------------");
		System.out.println("Please select an artist for the album. (Enter No.)");
		System.out.println("If there are many people, please separate the numbers by comma. (ex) 1,4,5)");
		System.out.print(">> ");
		
		String str_nums = sc.nextLine();
		StringTokenizer st = new StringTokenizer(str_nums, ",");
		while(st.hasMoreElements()) {
			try {
				int nums = Integer.parseInt(st.nextToken());
				tmpAlbum.artistID.add(artists.get(nums-1).artistID);
			} catch(NumberFormatException e) {
				System.out.println("[ERROR] Only Integrer and comma can be entered!");
				return;
			}
		}

		// 앨범 만들기 
		//////////////////////////////////////////////////////////////////
		sql = "select MAX(Album_id) from ALBUM";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			int index = 1;
			tmpAlbum.albumID = rs.getInt(index++) + 1;
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write this form");
			
			System.out.print("1. Album Title(less than 30 characters) >> ");
			tmpAlbum.albumTitle = sc.nextLine();
			
			System.out.print("2. Album Type(Single/EP: 1, Studio album: 2, Album: all but 1,2) >> ");
			tmpAlbum.albumType = sc.nextLine();
			if(tmpAlbum.albumType.equals("1")) tmpAlbum.albumType = "Single/EP";
			else if(tmpAlbum.albumType.equals("2")) tmpAlbum.albumType = "Studio album";
			else tmpAlbum.albumType = "Album";
			
			System.out.print("3. Agency(less than 15 characters) >> ");
			tmpAlbum.agency = sc.nextLine();
			
			System.out.print("4. Release Company(less than 15 characters) >> ");
			tmpAlbum.releaseCompany = sc.nextLine();
			
			System.out.print("5. Release Date(ex) 2020-01-01) >> ");
			String str_date = sc.nextLine();
			st = new StringTokenizer(str_date, "-");
			tmpAlbum.releaseDate = new Date(Integer.parseInt(st.nextToken())-1900, Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken()));
			
			System.out.print("6. Genre(Split by slash. ex) Hip Hop/Rap/Ballad) >> ");
			String tmpGenre = sc.nextLine(); tmpAlbum.aGenre.clear();
			st = new StringTokenizer(tmpGenre, "/");
			while(st.hasMoreElements()) tmpAlbum.aGenre.add(st.nextToken());
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. Album Title    : " + tmpAlbum.albumTitle);
				System.out.println(" 2. Album Type     : " + tmpAlbum.albumType);
				System.out.println(" 3. Agency         : " + tmpAlbum.agency);
				System.out.println(" 4. Release Company: " + tmpAlbum.releaseCompany);
				System.out.println(" 5. Release Date   : " + dateFormat.format(tmpAlbum.releaseDate));
				System.out.println(" 5. Genre          : " + tmpGenre);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		//////////////////////////////////////////////////////////////////
		// 앨범 추가 
		sql = "insert into ALBUM values (?,?,?,?,?,?)";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpAlbum.albumID);
		ps.setString(2, tmpAlbum.albumTitle);
		ps.setString(3, tmpAlbum.albumType);
		ps.setString(4, tmpAlbum.agency);
		ps.setString(5, tmpAlbum.releaseCompany);
		ps.setString(6, dateFormat.format(tmpAlbum.releaseDate));
		ps.executeUpdate();
		ps.close();
		
		// 장르 추가 
		for(String tmpGenre: tmpAlbum.aGenre) {
			sql = "insert into ALBUM_GENRE values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			ps.setString(2, tmpGenre);
			ps.executeUpdate();
			ps.close();
		}
		
		// 아티스트 추가 
		for(Integer tmpArtist: tmpAlbum.artistID) {
			sql = "insert into ALBUM_MAKE values (?,?)";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpArtist);
			ps.setInt(2, tmpAlbum.albumID);
			ps.executeUpdate();
			ps.close();
		}
		//////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("Now, album creation is complete.");
		System.out.println("Go to menu..");
		return;
	}
	public static void artistRegistration() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("        Artist Registration        ");
		System.out.println("-----------------------------------");
		
		Artist tmpArtist = new Artist();

		// 아티스트 만들기 
		//////////////////////////////////////////////////////////////////
		String sql = "select MAX(Artist_id) from ARTIST";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int index = 1;
			tmpArtist.artistID = rs.getInt(index++) + 1;
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write this form");
			
			System.out.print("1. Artist Name(less than 30 characters) >> ");
			tmpArtist.artistName = sc.nextLine();
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. Artist Name: " + tmpArtist.artistName);
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		// 아티스트 추가 
		//////////////////////////////////////////////////////////////////
		sql = "insert into ARTIST values (?,?)";
		ps = con.prepareStatement(sql);
		ps.setInt(1, tmpArtist.artistID);
		ps.setString(2, tmpArtist.artistName);
		ps.executeUpdate();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		System.out.println();
		System.out.println("Now, artist creation is complete.");
		System.out.println("Go to menu..");
		return;
	}

	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void musicDeletion() throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Music Deletion           ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the title for the music you want to delete.");
		System.out.println("Just press enter and the all music will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		ArrayList<Music> musics = new ArrayList<Music>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from MUSIC where LOWER(Music_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Music tmpMusic = new Music();
			int index = 1;
			tmpMusic.musicID = rs.getInt(index++);
			tmpMusic.musicTitle = rs.getString(index++);
			tmpMusic.lyricist = rs.getString(index++);
			tmpMusic.composer = rs.getString(index++);
			tmpMusic.arranger = rs.getString(index++);
			tmpMusic.hitsNum = rs.getInt(index++);
			tmpMusic.albumIdx = rs.getInt(index++);
			musics.add(tmpMusic);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(musics.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any songs..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            Music list             ");
			System.out.println("  No.                   Music Title"); // 5칸, 30칸 
			for(int i=0; i<musics.size(); i++) {
				int no = i + 1;
				String tmpTitle = musics.get(i).musicTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the music.");
			System.out.println("And you can delete the music.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > musics.size()) { // 음악 영역 밖 
				System.out.println("[ERROR] There is no music!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 음악 선택 & 아티스트, 장르, 앨범 연결 
			Music tmpMusic = musics.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 앨범, 아티스트 연결 
			tmpMusic.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Album_title, Artist_name "
					+ "from MUSIC, MUSIC_MAKE, ARTIST, ALBUM "
					+ "where Music_id = ? AND Music_id = MM_Music_idx AND MM_Artist_idx = Artist_id AND Album_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			ps.setInt(2, tmpMusic.albumIdx);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.albumTitle = rs.getString(index++);
				tmpMusic.artistName.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpMusic.mGenre.clear(); // 중복 들어간거 제거 
			sql = "select MGenre "
					+ "from MUSIC_GENRE "
					+ "where MIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpMusic.musicID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpMusic.mGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endMusicInfo = 0;
			while(endMusicInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Music info             ");
				System.out.println(" 1. Title   : " + tmpMusic.musicTitle);
				System.out.print(" 2. Artist  : ");
				for(String tmpArtist: tmpMusic.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre   : ");
				for(String tmpGenre: tmpMusic.mGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Lyricist: " + tmpMusic.lyricist);
				System.out.println(" 5. Composer: " + tmpMusic.composer);
				System.out.println(" 6. Arranger: " + tmpMusic.arranger);
				System.out.println(" 7. Hits    : " + tmpMusic.hitsNum);
				System.out.println(" 8. in Album: " + tmpMusic.albumTitle);
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Delete to music: 1, Return to music list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 음악삭제
					
					//////////////////////////////////////////////////////////////////
					// 장르 삭제 
					sql = "delete from MUSIC_GENRE where mIndex = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.musicID);
					ps.executeUpdate();
					ps.close();
					
					// make 삭제 
					sql = "delete from MUSIC_MAKE where MM_Music_idx = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.musicID);
					ps.executeUpdate();
					ps.close();
					
					// 플레이리스트 인 삭제 
					sql = "delete from MUSIC_IN_LIST where MIL_Music_idx = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.musicID);
					ps.executeUpdate();
					ps.close();
					
					// 음악 삭제 
					sql = "delete from MUSIC where Music_id = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpMusic.musicID);
					ps.executeUpdate();
					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println("                                   ");
					System.out.println("Music was successfully deleted.");
					System.out.println("Return to main menu.");
					return;
				} else if(cmd == 2) { // music list로 돌아가기 
					endMusicInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // music info 끝 
		} // while 끝 
	}
	public static void albumDeletion() throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Album Deletion           ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the title for the album you want to delete.");
		System.out.println("Just press enter and the all album will be shown.");
		System.out.print(">> ");
		
		String title = "";
		title = sc.nextLine();
		
		ArrayList<Album> albums = new ArrayList<Album>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from ALBUM where LOWER(Album_title) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		title = "%" + title + "%";
		ps.setString(1, title);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Album tmpAlbum = new Album();
			int index = 1;
			tmpAlbum.albumID = rs.getInt(index++);
			tmpAlbum.albumTitle = rs.getString(index++);
			tmpAlbum.albumType = rs.getString(index++);
			tmpAlbum.agency = rs.getString(index++);
			tmpAlbum.releaseCompany = rs.getString(index++);
			tmpAlbum.releaseDate = rs.getDate(index++);
			albums.add(tmpAlbum);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(albums.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any albums..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("            Album list             ");
			System.out.println("  No.                   Album Title"); // 5칸, 30칸 
			for(int i=0; i<albums.size(); i++) {
				int no = i + 1;
				String tmpTitle = albums.get(i).albumTitle;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpTitle));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the album.");
			System.out.println("And you can delete the album.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > albums.size()) { // 앨범 영역 밖 
				System.out.println("[ERROR] There is no album!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 앨범 선택 & 아티스트, 장르, 음악 연결 
			Album tmpAlbum = albums.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 아티스트 연결 
			tmpAlbum.artistName.clear(); // 중복 들어간거 제거 
			sql = "select Artist_name "
					+ "from ALBUM, ALBUM_MAKE, ARTIST "
					+ "where Album_id = ? AND Album_id = AM_Album_idx AND AM_Artist_idx = Artist_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.artistName.add(rs.getString(index++));
			}
			
			// 음악 연결 
			tmpAlbum.musicTitle.clear(); // 중복 들어간거 제거 
			tmpAlbum.musicID.clear(); // 중복 들어간거 제거 
			sql = "select Music_id, Music_title "
					+ "from MUSIC "
					+ "where Album_idx = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.musicID.add(rs.getInt(index++));
				tmpAlbum.musicTitle.add(rs.getString(index++));
			}
			
			// 장르 연결 
			tmpAlbum.aGenre.clear(); // 중복 들어간거 제거 
			sql = "select AGenre "
					+ "from ALBUM_GENRE "
					+ "where AIndex = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpAlbum.albumID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpAlbum.aGenre.add(rs.getString(index++));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endAlbumInfo = 0;
			while(endAlbumInfo != 1) {
				String str_artist = "";
				String str_genre = "";
				
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("            Album info             ");
				System.out.println(" 1. Title          : " + tmpAlbum.albumTitle);
				System.out.print(" 2. Artist         : ");
				for(String tmpArtist: tmpAlbum.artistName) {
					str_artist += tmpArtist + ", ";
				}
				System.out.println(str_artist.substring(0, str_artist.length()-2));
				System.out.print(" 3. Genre          : ");
				for(String tmpGenre: tmpAlbum.aGenre) {
					str_genre += tmpGenre + "/";
				}
				System.out.println(str_genre.substring(0, str_genre.length()-1));
				System.out.println(" 4. Type           : " + tmpAlbum.albumType);
				System.out.println(" 5. Agency         : " + tmpAlbum.agency);
				System.out.println(" 6. Release Company: " + tmpAlbum.releaseCompany);
				System.out.println(" 7. Release Date   : " + dateFormat.format(tmpAlbum.releaseDate));
				System.out.println("                     ");
				System.out.println(" There is " + tmpAlbum.musicTitle.size() + " music(s) in the album");
				for(String tmpMusic: tmpAlbum.musicTitle) {
					System.out.println(" -> " + tmpMusic);
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Delete to album: 1, Return to album list: 2)");
				System.out.println("If you delete this album, the music included in the album will be deleted as well.");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) { // 앨범 삭제 
					
					//////////////////////////////////////////////////////////////////
					// 장르 삭제 
					sql = "delete from ALBUM_GENRE where aIndex = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpAlbum.albumID);
					ps.executeUpdate();
					ps.close();
					
					// make 삭제 
					sql = "delete from ALBUM_MAKE where AM_Album_idx = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpAlbum.albumID);
					ps.executeUpdate();
					ps.close();
					
					// 음악 삭제 
					for(Integer tmpMusicID: tmpAlbum.musicID) {
						// 장르 삭제 
						sql = "delete from MUSIC_GENRE where mIndex = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusicID);
						ps.executeUpdate();
						ps.close();
						
						// make 삭제 
						sql = "delete from MUSIC_MAKE where MM_Music_idx = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusicID);
						ps.executeUpdate();
						ps.close();
						
						// 플레이리스트 인 삭제 
						sql = "delete from MUSIC_IN_LIST where MIL_Music_idx = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusicID);
						ps.executeUpdate();
						ps.close();
						
						// 음악 삭제 
						sql = "delete from MUSIC where Music_id = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusicID);
						ps.executeUpdate();
						ps.close();
					}
					
					// 앨범 삭제 
					sql = "delete from ALBUM where Album_id = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpAlbum.albumID);
					ps.executeUpdate();
					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println("                                   ");
					System.out.println("Album was successfully deleted.");
					System.out.println("Return to main menu.");
					return;
				}
				else if(cmd == 2) { // album list로 돌아가기 
					endAlbumInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // album info 끝 
		} // while 끝 
	}
	public static void artistDeletion() throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          Artist Deletion          ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the name for the artist you want to delete.");
		System.out.println("Just press enter and the all artist will be shown.");
		System.out.print(">> ");
		
		String name = "";
		name = sc.nextLine();
	
		// DB에서 그 아티스트의 음원(제목, 앨범) 쭉 보여줌
		ArrayList<Artist> artists = new ArrayList<Artist>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from Artist where LOWER(Artist_name) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		name = "%" + name + "%";
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			Artist tmpArtist = new Artist();
			int index = 1;
			tmpArtist.artistID = rs.getInt(index++);
			tmpArtist.artistName = rs.getString(index++);
			artists.add(tmpArtist);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		if(artists.isEmpty()) { // 리스트가 비어있는 경우 
			System.out.println("I couldn't find any artist..");
			System.out.println("Press the enter key to return to the main menu.");
			sc.nextLine();
			return;
		}
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("           Artist List             ");
			System.out.println("  No.                   Artist Name"); // 5칸, 30칸 
			for(int i=0; i<artists.size(); i++) {
				int no = i + 1;
				String tmpName = artists.get(i).artistName;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpName));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the artist.");
			System.out.println("And you can delete the artist.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > artists.size()) { // 영역 밖 
				System.out.println("[ERROR] There is no artist!");
				System.out.println("Go to menu..");
				return;
			}
			
			// 아티스트 선택 & 아티스트, 음악 연결 -> 음악, 앨범 연결 
			Artist tmpArtist = artists.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 음악, 앨범 연결 
			tmpArtist.musics.clear(); // 중복 들어간거 제거 
			sql = "select Music_id, Music_title, Album_title "
					+ "from ARTIST, MUSIC_MAKE, MUSIC, ALBUM "
					+ "where Artist_id = ? AND Artist_id = MM_Artist_idx AND MM_Music_idx = Music_id AND Album_idx = Album_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpArtist.artistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpArtist.musics.add(new Music(rs.getInt(index++), rs.getString(index++), rs.getString(index++)));
			}
			
			rs.close();
			ps.close();
			
			// 앨범만 따로 
			tmpArtist.albums.clear(); // 중복 들어간거 제거 
			sql = "select Album_id "
					+ "from ARTIST, ALBUM_MAKE, ALBUM "
					+ "where Artist_id = ? AND Artist_id = AM_Artist_idx AND AM_Album_idx = Album_id";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpArtist.artistID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpArtist.albums.add(new Album(rs.getInt(index++)));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endArtistInfo = 0;
			while(endArtistInfo != 1) {
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("           Artist info             ");
				System.out.println(" 1. Name: " + tmpArtist.artistName);
				System.out.println("                                   ");
				System.out.println(" There is " + tmpArtist.musics.size() + " music(s) in the playlist");
				if(tmpArtist.musics.size() != 0) {
					System.out.println("    Music / Album");
					for(Music tmpMusic: tmpArtist.musics) {
						System.out.println(" -> " + tmpMusic.musicTitle + " / " + tmpMusic.albumTitle);
					}
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Delete to artist: 1, Return to artist list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) {
					//////////////////////////////////////////////////////////////////
					// MM 삭제
					sql = "delete from MUSIC_MAKE where MM_Artist_idx = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpArtist.artistID);
					ps.executeUpdate();
					ps.close();
					
					// AM 삭제
					sql = "delete from ALBUM_MAKE where AM_Artist_idx = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpArtist.artistID);
					ps.executeUpdate();
					ps.close();
					
					// MM의 음악들 삭제 (tmpArtist.musics) 
					for(Music tmpMusic: tmpArtist.musics) {
						//////////////////////////////////////////////////////////////////
						// 장르 삭제 
						sql = "delete from MUSIC_GENRE where mIndex = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();
						ps.close();
						
						// make 삭제 
						sql = "delete from MUSIC_MAKE where MM_Music_idx = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();
						ps.close();
						
						// 플레이리스트 인 삭제 
						sql = "delete from MUSIC_IN_LIST where MIL_Music_idx = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();
						ps.close();
						
						// 음악 삭제 
						sql = "delete from MUSIC where Music_id = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpMusic.musicID);
						ps.executeUpdate();
						ps.close();
						//////////////////////////////////////////////////////////////////
					}
					
					// AM의 앨범들 삭제 (tmpArtist.albums) 
					for(Album tmpAlbum: tmpArtist.albums) {
						//////////////////////////////////////////////////////////////////
						// 장르 삭제 
						sql = "delete from ALBUM_GENRE where aIndex = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpAlbum.albumID);
						ps.executeUpdate();
						ps.close();
						
						// make 삭제 
						sql = "delete from ALBUM_MAKE where AM_Album_idx = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpAlbum.albumID);
						ps.executeUpdate();
						ps.close();
						
						// 음악 삭제 
						for(Integer tmpMusicID: tmpAlbum.musicID) {
							// 장르 삭제 
							sql = "delete from MUSIC_GENRE where mIndex = ?";
							ps = con.prepareStatement(sql);
							ps.setInt(1, tmpMusicID);
							ps.executeUpdate();
							ps.close();
							
							// make 삭제 
							sql = "delete from MUSIC_MAKE where MM_Music_idx = ?";
							ps = con.prepareStatement(sql);
							ps.setInt(1, tmpMusicID);
							ps.executeUpdate();
							ps.close();
							
							// 플레이리스트 인 삭제 
							sql = "delete from MUSIC_IN_LIST where MIL_Music_idx = ?";
							ps = con.prepareStatement(sql);
							ps.setInt(1, tmpMusicID);
							ps.executeUpdate();
							ps.close();
							
							// 음악 삭제 
							sql = "delete from MUSIC where Music_id = ?";
							ps = con.prepareStatement(sql);
							ps.setInt(1, tmpMusicID);
							ps.executeUpdate();
							ps.close();
						}
						
						// 앨범 삭제 
						sql = "delete from ALBUM where Album_id = ?";
						ps = con.prepareStatement(sql);
						ps.setInt(1, tmpAlbum.albumID);
						ps.executeUpdate();
						ps.close();
						//////////////////////////////////////////////////////////////////
					}
					
					// 아티스트 삭제 
					sql = "delete from ARTIST where Artist_id = ?";
					ps = con.prepareStatement(sql);
					ps.setInt(1, tmpArtist.artistID);
					ps.executeUpdate();
					ps.close();
					//////////////////////////////////////////////////////////////////
					
					System.out.println("                                   ");
					System.out.println("Artist was successfully deleted.");
					System.out.println("Return to main menu.");
					return;
				}
				else if(cmd == 2) { // artist list로 돌아가기 
					endArtistInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // artist info 끝 
		} // while 끝 
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void userManagement(User currentUser) throws SQLException{
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("          User Management          ");
		System.out.println("-----------------------------------");
		System.out.println("Please enter the user name.");
		System.out.println("Just press enter and the all user will be shown.");
		System.out.print(">> ");
		
		String name = "";
		name = sc.nextLine();
		
		ArrayList<User> users = new ArrayList<User>();
		//////////////////////////////////////////////////////////////////
		String sql = "select * from User where LOWER(User_name) LIKE LOWER(?)"; // 제목 대소문자 구분없이 검색 및 포함으로 검색 
		PreparedStatement ps = con.prepareStatement(sql);
		name = "%" + name + "%";
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			User tmpUser = new User();
			int index = 1;
			tmpUser.userID = rs.getInt(index++);
			tmpUser.userPW = rs.getString(index++);
			tmpUser.userName = rs.getString(index++);
			tmpUser.phone = rs.getString(index++);
			tmpUser.ssn = rs.getString(index++);
			tmpUser.address = rs.getString(index++);
			tmpUser.isAdmin = rs.getInt(index++);
			users.add(tmpUser);
		}
		
		rs.close();
		ps.close();
		//////////////////////////////////////////////////////////////////
		System.out.println();
		
		while(true) {
			System.out.println("-----------------------------------");
			System.out.println("             User List             ");
			System.out.println("  No.                     User Name"); // 5칸, 30칸 
			for(int i=0; i<users.size(); i++) {
				int no = i + 1;
				String tmpName = users.get(i).userName;
				System.out.println(String.format("%5d", no) + String.format("%30s", tmpName));
			}
			System.out.println("-----------------------------------");
			System.out.println("Enter the 'No.' to see more about the user.");
			System.out.println("Enter 0 to go to the menu.");
			System.out.print(">> ");
			
			while (!sc.hasNextInt()) {
				sc.next(); 
				System.out.print(">> ");
			}
			int num = sc.nextInt();
			sc.nextLine(); // enter 
			
			if(num == 0) return;
			if(num > users.size()) { // 영역 밖 
				System.out.println("[ERROR] There is no artist!");
				System.out.println("Go to menu..");
				return;
			}
			
			User tmpUser = users.get(num-1);
			//////////////////////////////////////////////////////////////////
			// 플레이리스트 연결 
			tmpUser.playlists.clear(); // 중복 들어간거 제거 
			sql = "select Playlist_id, Playlist_title "
					+ "from PLAYLIST "
					+ "where User_idx = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, tmpUser.userID);
			rs = ps.executeQuery();

			while(rs.next()) {
				int index = 1;
				tmpUser.playlists.add(new Playlist(rs.getInt(index++), rs.getString(index++)));
			}
			
			rs.close();
			ps.close();
			//////////////////////////////////////////////////////////////////
			
			int endUserInfo = 0;
			while(endUserInfo != 1) {
				System.out.println();
				System.out.println("-----------------------------------");
				System.out.println("             User info             ");
				System.out.println(" 1. User ID  : " + tmpUser.userID);
				System.out.println(" 2. User PW  : " + tmpUser.userPW);
				System.out.println(" 3. User Name: " + tmpUser.userName);
				System.out.println(" 4. Phone    : " + tmpUser.phone);
				System.out.println(" 5. SSN      : " + tmpUser.ssn);
				System.out.println(" 6. Address  : " + tmpUser.address);
				System.out.print(" 7. Admin    : ");
				if(tmpUser.isAdmin == 1) System.out.println("O");
				else System.out.println("X");
				System.out.println("                                   ");
				System.out.println(" There is " + tmpUser.playlists.size() + " playlist(s)");
				for(Playlist tmpPlaylist: tmpUser.playlists) {
					System.out.println(" -> " + tmpPlaylist.playlistTitle);
				}
				System.out.println("-----------------------------------");
				System.out.println("Please press the command. (Edit to user: 1, Return to user list: 2)");
				System.out.println("Enter 0 to go to the menu.");
				System.out.print(">> ");
				
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				int cmd = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(cmd == 0) return;
				else if(cmd == 1) {
					// Edit!!
					// 유저 정보변경, 유저삭제 
					// 1. reviceUserInfo
					// 2. deleteUser
					System.out.println();
					System.out.println("What do you want to do?");
					System.out.println(" 1. Revice user info");
					System.out.println(" 2. Delete user");
					System.out.println();
					System.out.println(" 0. Return to user info");
					System.out.print(">> ");
					
					while (!sc.hasNextInt()) {
						sc.next(); 
						System.out.print(">> ");
					}
					int cmd2 = sc.nextInt();
					sc.nextLine(); // enter 
					
					if(cmd2 == 0) continue;
					else if(cmd2 == 1) reviceUserInfo(tmpUser);
					else if(cmd2 == 2) {
						if(deleteUser(currentUser, tmpUser, users) == 0) continue;
					}
					else {
						System.out.println("[ERROR] It is not allowed cmd!");
						System.out.println("Go to menu..");
						return;
					}
					
					System.out.println("                                   ");
					System.out.println("User has been edited successfully!");
					System.out.println("Press the enter key to return to the main menu.");
					sc.nextLine();
					return;
				}
				else if(cmd == 2) { // User list로 돌아가기 
					endUserInfo = 1;
				} else {
					System.out.println("[ERROR] It is not allowed cmd!");
					System.out.println("Go to menu..");
					return;
				}
			} // artist info 끝 
		} // while 끝 
	}
	public static void reviceUserInfo(User tmpUser) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		int doThis = 2;
		while(doThis == 2) {
			System.out.println();
			System.out.println("Please write this form.");
			
			System.out.print("1. Password(less than 15 characters) >> ");
			tmpUser.userPW = sc.nextLine();
			
			System.out.print("2. Name(less than 30 characters) >> ");
			tmpUser.userName = sc.nextLine();
			
			System.out.print("3. Phone(less than 15 characters) >> ");
			tmpUser.phone = sc.nextLine();
			
			System.out.print("4. SSN(less than 15 characters) >> ");
			tmpUser.ssn = sc.nextLine();
			
			System.out.print("5. Address(less than 30 characters) >> ");
			tmpUser.address = sc.nextLine();
			
			System.out.print("6. isAdmin(Admin: 1, Not admin: all but 1) >> ");
			String str_isAdmin = sc.nextLine();
			if(str_isAdmin.equals("1")) tmpUser.isAdmin = 1;
			else tmpUser.isAdmin = 2;
			
			int tmpCheck = 0;
			while(tmpCheck == 0) {
				System.out.println();
				System.out.println(" 1. ID      : " + tmpUser.userID);
				System.out.println(" 2. Password: " + tmpUser.userPW);
				System.out.println(" 3. Name    : " + tmpUser.userName);
				System.out.println(" 4. Phone   : " + tmpUser.phone);
				System.out.println(" 5. SSN     : " + tmpUser.ssn);
				System.out.println(" 6. Address : " + tmpUser.address);
				System.out.print(" 7. Admin   : ");
				if(tmpUser.isAdmin == 1) System.out.println("O");
				else System.out.println("X");
				System.out.print("-> Is that true? (Yes:1, No:2) >> ");
				while (!sc.hasNextInt()) {
					sc.next(); 
					System.out.print(">> ");
				}
				doThis = sc.nextInt();
				sc.nextLine(); // enter 
				
				if(doThis == 1 || doThis == 2) tmpCheck = 1;
				else System.out.println("[ERROR] It is not allowed cmd!");
			}
		}
		
		//////////////////////////////////////////////////////////////////
		String sql = "update USER "
				+ "set User_pw = ?, User_name = ?, Phone = ?, Ssn = ?, Address = ?, Is_admin = ? where User_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, tmpUser.userPW);
		ps.setString(2, tmpUser.userName);
		ps.setString(3, tmpUser.phone);
		ps.setString(4, tmpUser.ssn);
		ps.setString(5, tmpUser.address);
		ps.setInt(6, tmpUser.isAdmin);
		ps.setInt(7, tmpUser.userID);
		ps.executeUpdate();

		ps.close();
		//////////////////////////////////////////////////////////////////
	}
	public static int deleteUser(User currentUser, User tmpUser, ArrayList<User> users) throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("Are you sure you want to delete this user? (Yes: 1, No:2)");
		System.out.print(">> ");
		while (!sc.hasNextInt()) {
			sc.next(); 
			System.out.print(">> ");
		}
		int num = sc.nextInt();
		sc.nextLine(); // enter 
		
		if(num == 2) {
			System.out.println();
			System.out.println("OK! I do not delete user.");
			return 0;
		} else if(num != 1) {
			System.out.println("[ERROR] It is not allowed cmd!");
			System.out.println("Automatically exit...");
			return 0;
		}
		
		if(currentUser.userID == tmpUser.userID) {
			System.out.println();
			System.out.println("You cannot delete yourself.");
			System.out.println("Automatically exit...");
			return 0;
		}
		
		// 유저 삭제 
		//////////////////////////////////////////////////////////////////
		// 유저안에 있던 플레이리스트 삭제 
		// MUSIC_IN_LIST 안에 데이터 삭제 
		for(Playlist tmpPlaylist: tmpUser.playlists) {
			String sql = "delete from MUSIC_IN_LIST where MIL_Playlist_idx = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			ps.executeUpdate();
			ps.close();
		}
		
		// PLAYLIST 삭제 
		for(Playlist tmpPlaylist: tmpUser.playlists) {
			String sql = "delete from PLAYLIST where Playlist_id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, tmpPlaylist.playlistID);
			ps.executeUpdate();
			ps.close();
		}
		
		// 유저 삭제 
		String sql = "delete from User where User_id = ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, tmpUser.userID);
		ps.executeUpdate();
		ps.close();
		//////////////////////////////////////////////////////////////////
		
		users.remove(tmpUser);
		System.out.println("                              ");
		System.out.println("Playlist successfully removed!");
		return 1;
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("===================================");
		System.out.println("                                   ");
		System.out.println("       Welcome to MusicApp!        ");
		System.out.println("                                   ");
		System.out.println("===================================");
		System.out.println();
		
		try {
			Class.forName("org.mariadb.jdbc.Driver"); // 여기에 path 정보 들어가야함!! 
			con = DriverManager.getConnection(
					"jdbc:mariadb://127.0.0.1:3306/music_service",
					"eastsea",
					"east100");
			
			// login & create account
			User currentUser = new User();
			currentUser.isAdmin = 0;
			int loginMenuCheckNum = 0;
			while(loginMenuCheckNum == 0) loginMenuCheckNum = loginMenuCheck(currentUser);
			
			// music menu
			if(currentUser.isAdmin == 1) { // 관리자 화면.. 
				while(endApp != 1) adminInterface(currentUser);
			} else { // 관리자가 아닌 유저의 화면.. 
				while(endApp != 1) userInterface(currentUser);
			}
			
			con.close();
		} catch(ClassNotFoundException e) {
			System.out.println("[ERROR] Class not found.");
		} catch(SQLException e) {
			System.out.println("[ERROR] DB connection failed.");
		}
		
		System.out.println();
		System.out.println("===================================");
		System.out.println("                                   ");
		System.out.println("             Good Bye~             ");
		System.out.println("                                   ");
		System.out.println("===================================");
		System.out.println();
	}
	
}
