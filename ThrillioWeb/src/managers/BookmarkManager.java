package managers;

import Dao.BookmarkDao;
import constants.BookGenre;
import constants.KidFriendlyStatus;
import constants.MovieGenre;
import entities.*;

import java.util.Collection;
import java.util.List;

public class BookmarkManager {
    private static BookmarkManager  instance = new BookmarkManager();
    private static BookmarkDao dao =new BookmarkDao();

    private BookmarkManager(){
    }
    public static BookmarkManager getInstance() {
        return instance;
    }

    public Movie createMovie(long id, String title, int releaseYear, String[] cast, String[]directors, MovieGenre genre , double imdbrating){
        Movie  movie =new Movie();
        movie.setId(id);
        movie.setTitle(title);
/*     movie.setProfileUrl(profileUrl);*/
        movie.setReleaseYear(releaseYear);
        movie.setCast(cast);
        movie.setDirectors(directors);
        movie.setGenre(genre);
        movie.setImdbRating(imdbrating);
        return movie;
    }

    public Book createBook(long id, String title,String imageUrl, int publicationYear, String[] author, BookGenre genre, double amazonRating){
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setImageUrl(imageUrl);
        book.setPublicationYear(publicationYear);
        //book.setPublisher(publisher);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAmazonRating(amazonRating);
        return book;
    }

    public Weblink createWebLink(long id,String title ,String url,String host){
        Weblink weblink = new Weblink();
        weblink.setId(id);
        weblink.setTitle(title);
        weblink.setUrl(url);
        weblink.setHost(host);
        return weblink;
    }

    public  List<List<Bookmark>> getBookmarks(){
        return dao.getBookmarks();
    }

    public void  saveUserBookmark(User user, Bookmark bookmark) {
        UserBookmark userBookmark = new UserBookmark();
        userBookmark.setUser(user);
        userBookmark.setBookmark(bookmark);
        dao.saveUserBookmark(userBookmark);
        
    }
    /*
    if(bookmark instanceof  Weblink){
        try{
            String url = ((Weblink)bookmark).getUrl();
            if(!url.endsWith(".pdf")){

                String webpage = HttpConnect.download(((Weblink)bookmark).getUrl());
                if(webpage!=null){
                    IOUtil.write(webpage,bookmark.getId());
                }
            }else{

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
}*/
    public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
        bookmark.setKidFriendlyStatus(kidFriendlyStatus);
        bookmark.setKidFriendlyMarkedBy(user);
        dao.updateKidFriendlyStatus(bookmark);
        System.out.println("Kid-Friendly status "+kidFriendlyStatus +" , Marked By : "  + user.getEmail() +" , " +bookmark);
    }

    public void share(User user,Bookmark bookmark) {
        bookmark.setSharedBy(user);
        System.out.println("Data to be Shared " );
        if(bookmark instanceof Book){
            System.out.println(((Book)bookmark).getItemData());
        }else if (bookmark instanceof Weblink) {
            System.out.println(((Weblink) bookmark).getItemData());
        }
        dao.sharedByInfo(bookmark);
    }
	public Collection<Bookmark> getBooks(boolean isBookmarked, long id) {
		return dao.getBooks(isBookmarked,id);
		
	}
	public Bookmark getBooks(long b_id) {
		return dao.getBooks(b_id);
		
	}
}
