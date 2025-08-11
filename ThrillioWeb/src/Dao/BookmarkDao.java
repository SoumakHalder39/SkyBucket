package Dao;

import entities.*;
import managers.BookmarkManager;
import thrilliod.DataStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import constants.BookGenre;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveUserBookmark(UserBookmark userBookmark) {
        //  DataStore.add(userBookmark);
        //interacting with database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
             Statement stmt = conn.createStatement();) {
        	 if (userBookmark.getBookmark() instanceof Book) {
        		 saveUserBook(userBookmark,stmt);
        	 }else if (userBookmark.getBookmark() instanceof Movie) {
        		 saveUserMovie(userBookmark,stmt);
        	 }else {
        		 saveUserWeblink(userBookmark,stmt);
        	 }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserWeblink(UserBookmark userBookmark, Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
    	String query = "insert into user_weblink(user_id , weblink_id ) values (" + userBookmark.getUser().getId()
                + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
		
	}

	private void saveUserMovie(UserBookmark userBookmark, Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
    	String query = "insert into user_movie(user_id , movie_id ) values (" + userBookmark.getUser().getId()
                + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
	}

	private void saveUserBook(UserBookmark userBookmark, Statement stmt) throws SQLException {
		// TODO Auto-generated method stub
    	String query = "insert into user_book(user_id , book_id ) values (" + userBookmark.getUser().getId()
                + "," + userBookmark.getBookmark().getId() + ")";
        stmt.executeUpdate(query);
		
	}

	

    // In real application we use sql or hibernate queries
	/*
	 * public List<Weblink> getAllWeblinks() { List<Weblink> result = new
	 * ArrayList<>(); List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
	 * List<Bookmark> allWeblinks = bookmarks.get(0); for (Bookmark bookmark :
	 * allWeblinks) { result.add((Weblink) bookmark); }
	 * 
	 * return result; }
	 * 
	 * public List<Weblink> getWebLinks(Weblink.DownloadStatus downloadStatus) {
	 * List<Weblink> result = new ArrayList<>();
	 * 
	 * List<Weblink> allWebLinks = getAllWeblinks(); for (Weblink webLink :
	 * allWebLinks) { if (webLink.getDownloadStatus().equals(downloadStatus)) {
	 * 
	 * } }
	 * 
	 * return result; }
	 */
    public void updateKidFriendlyStatus(Bookmark bookmark) {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof Movie) {
            tableToUpdate = "Movie";
        } else if (bookmark instanceof Weblink) {
            tableToUpdate = "Weblink";
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
            Statement stmt = conn.createStatement();) {
            String query =" update "+ tableToUpdate + " set kid_friendly_status = "
                    + kidFriendlyStatus +  ", Kid_friendly_marked_by  = "+ userId + " where id = "+ bookmark.getId();
            System.out.println("query(updateKidFriendlyStatus): " +query);
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //sharing method
    
    public void sharedByInfo(Bookmark bookmark) {
        long userId = bookmark.getSharedBy().getId();
        String tableToUpdate = "Book";
        if(bookmark instanceof Weblink){
            tableToUpdate = "Weblink";
        }
    try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
        Statement stmt = conn.createStatement();){
        String query = "update "+ tableToUpdate +  " set shared_by = " + userId + " where id = "+ bookmark.getId();
        System.out.println("query(sharedByInfo)"+ query);
        stmt.executeUpdate(query);
    }
    catch(SQLException e){
        e.printStackTrace();
    }
    }

// get Books method
    public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		Collection<Bookmark> result = new ArrayList<>();
		
	//Loading the jdbc driver
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	//creating connection 
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
				Statement stmt = conn.createStatement();) {
		String query="";
		if(!isBookmarked) {
			 query ="select b.id ,title, image_url,publication_year,GROUP_CONCAT(a.name SEPARATOR ',') AS authors,"
			 		+ "book_genre_id,amazon_rating from book b,author a , book_author ba where b.id = ba.book_id and "
			 		+ "ba.author_id=a.id and  b.id NOT IN (select ub.book_id from user u, user_book ub where u.id = "+userId +" and u.id =ub.user_id ) group by b.id";
			// System.out.println(query);
		}else {
			 query ="select b.id ,title, image_url,publication_year,GROUP_CONCAT(a.name SEPARATOR ',') AS authors,"
				 		+ "book_genre_id,amazon_rating from book b,author a , book_author ba where b.id = ba.book_id and "
				 		+ "ba.author_id=a.id and  b.id  IN (select ub.book_id from user u, user_book ub where u.id = "+userId +" and u.id =ub.user_id ) group by b.id";
				// System.out.println(query);
		}
		 ResultSet rs = stmt.executeQuery(query);
	       
	        while(rs.next()){
	            long id = rs.getLong("id");
	            String title =rs.getString("title");
	            String image_url = rs.getString("image_url");
	            int publicationYear = rs.getInt("publication_year");
	           // String publisher = rs.getString("name");
	            String[] authors = rs.getString("authors").split(",");
	            int genreId=rs.getInt("book_genre_id");
	            BookGenre genre = BookGenre.values()[genreId];
	            double amazonRating = rs.getDouble("amazon_rating");
	            
	           //System.out.println("id: " + id + ", title: " + title +"image_url :"+image_url +", publication year: " + publicationYear +  ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

	            Bookmark bookmark= BookmarkManager.getInstance().createBook(id, title, image_url,publicationYear,  authors, genre,amazonRating );
	            result.add(bookmark);
	            
	        }
		}
		catch (SQLException throwables) {
            throwables.printStackTrace();
        }
		return result;
	}

	public Bookmark getBooks(long b_id) {
		Bookmark bookmark = null;
		try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
				Statement stmt = conn.createStatement();) {
			  String query ="Select b.id ,title,image_url,publication_year,p.name ,GROUP_CONCAT(a.name SEPARATOR ',') as authors ,book_genre_id , amazon_rating,created_date"
		                +" from Book b ,publisher p , author a ,book_author ba "+"  where b.id = " + b_id
		                +" and  b.publisher_id= p.id  and b.id= ba.book_id and ba.author_id = a.id group by b.id";


	        ResultSet rs = stmt.executeQuery(query);
	       
	        while(rs.next()){
	            long id = rs.getLong("id");
	            String title =rs.getString("title");
	            String image_url = rs.getString("image_url");
	            int publicationYear = rs.getInt("publication_year");
	           // String publisher = rs.getString("name");
	            String[] authors = rs.getString("authors").split(",");
	            int genreId=rs.getInt("book_genre_id");
	            BookGenre genre = BookGenre.values()[genreId];
	            double amazonRating = rs.getDouble("amazon_rating");
	            
	          //  System.out.println("id: " + id + ", title: " + title +"image_url :"+image_url +", publication year: " + publicationYear +  ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

	          bookmark= BookmarkManager.getInstance().createBook(id, title, image_url,publicationYear,  authors, genre,amazonRating );
	         
	            
	        }
	        }
	        catch (SQLException throwables) {
	            throwables.printStackTrace();
	        }
		
		return bookmark;
	}
	

}
    

