package thrilliod;

import constants.BookGenre;
import constants.Gender;
import constants.MovieGenre;
import constants.UserType;
import entities.Bookmark;
import entities.User;
import entities.UserBookmark;
import managers.BookmarkManager;
import managers.UserManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
//    public static final int BOOKMARK_TYPES_COUNT = 3;
//    public static final int BOOKMARK_COUNT_PER_TYPE = 5;
//    public static final int TOTAL_USER_COUNT = 5;
//    public static final int USER_BOOKMARK_LIMIT = 5;


        private static List<User> users = new ArrayList<>();// User[TOTAL_USER_COUNT];
        public static List<User>  getUsers() {
             return users;
    }

        private static List<List<Bookmark>> bookmarks=new ArrayList<>();//Bookmark[BOOKMARK_TYPES_COUNT][BOOKMARK_COUNT_PER_TYPE];
        public static List<List<Bookmark>>  getBookmarks() {
            return bookmarks;
    }

     private static List<UserBookmark> userBookmarks = new ArrayList<>();// UserBookmark[TOTAL_USER_COUNT * USER_BOOKMARK_LIMIT];
/*
        public static UserBookmark[] getUserBookmarks() {
            return userBookmarks;
    }
   private static int bookmarkIndex=0;
*/
public static void loadData(){
      /*  loadUsers();
        loadWebLinks();
        loadMovies();
        
();
         */

//    try {
//        Class.forName("com.mysql.jdbc.Driver");
//
//    } catch (ClassNotFoundException e) {
//        e.printStackTrace();
//    }

    /*
    try with resources => conn and stmt queries will be closed .
    Connection string :<protocol>:<sb-protocol>:<data-source details>
     */
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio","root","Riku@123");
                Statement stmt =  conn.createStatement(); ){

                loadUsers(stmt);
                loadWebLinks(stmt);
                loadMovies(stmt);
                loadBooks(stmt);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

}
    private static void loadUsers( Statement stmt) throws SQLException{
      /*  users[0] =UserManager.getInstance().createUser(1000,"user0@semanticsquare.com","test"	,"John","K", Gender.MALE, UserType.USER);
        users[1]= UserManager.getInstance().createUser(1001,"user1@semanticsquare.com","test",	"Sam",	"T"	,Gender.MALE	,UserType.USER);
        users[2]= UserManager.getInstance().createUser(1002,"user2@semanticsquare.com","test","Anita","S",Gender.FEMALE	,UserType.EDITOR);
        users[3]= UserManager.getInstance().createUser(1003,"user3@semanticsquare.com","test","Sara",	"M",	Gender.FEMALE	,UserType.EDITOR);
        users[4]= UserManager.getInstance().createUser(1004,"ak244@gmail.com","Nowayin","Amit","Kumar",Gender.MALE,UserType.CHIEF_EDITOR);

       // String [] data = new String[TOTAL_USER_COUNT]; */
       /*     List<String> data = new ArrayList<>();
        IOUtil.read(data ,"User");

        for (String row:data) {
            String[] values = row.split("\t");
            Gender gender = Gender.MALE;
            if(values[5].equals("f"))
                 gender = Gender.FEMALE;
            else if(values[5].equals("f"))
                    gender = Gender.TRANSGENDER;
            User user = UserManager.getInstance().createUser(Long.parseLong(values[0]),values[1],values[2],values[3],values[4],gender, UserType.valueOf(values[6]));
            users.add(user);
        }*/

        String query = "select * from user";
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()){
            long id = rs.getLong("id");
            String email = rs.getString("email");
            String password =rs.getString("password");
            String firstName=rs.getString("first_name");
           String lastName= rs.getString("last_name");
           int gender_id = rs.getInt("gender_id");
           Gender gender = Gender.values()[gender_id];
           int userTypeId=rs.getInt("user_type_id");
           UserType userType= UserType.values()[userTypeId];

            User user = UserManager.getInstance().createUser(id,email,password,firstName,lastName,gender,userType);
            users.add(user);
        }
    }



    private static void loadWebLinks(Statement stmt)throws SQLException {
      /*  bookmarks[0][0]= BookmarkManager.getInstance().createWebLink(2000,"Taming Tiger, Part 2",	"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html","	http://www.javaworld.com");
        bookmarks[0][1]= BookmarkManager.getInstance().createWebLink(2001,"How do I import a pre-existing Java project into Eclipse and get up and running?","http://stackoverflow.com/questions/142863/how-do-i-import-a-pre-existing-java-project-into-eclipse-and-get-up-and-running","http://www.stackoverflow.com");
        bookmarks[0][2]= BookmarkManager.getInstance().createWebLink(2002,"Interface vs Abstract Class","http://mindprod.com/jgloss/interfacevsabstract.html"	,"http://mindprod.com");
        bookmarks[0][3]= BookmarkManager.getInstance().createWebLink(2003,"NIO tutorial by Greg Travis",	"http://cs.brown.edu/courses/cs161/papers/j-nio-ltr.pdf",	"http://cs.brown.edu"	);
        bookmarks[0][4]= BookmarkManager.getInstance().createWebLink(2004,"Virtual Hosting and Tomcat"	,"http://tomcat.apache.org/tomcat-6.0-doc/virtual-hosting-howto.html","http://tomcat.apache.org");
    */
   //   String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
  /*      List<String> data = new ArrayList<>();
      IOUtil.read(data,"Weblink");
      List<Bookmark> bookmarkList = new ArrayList<>();
      for(String row : data){
          String [] values = row.split("\t");
          Bookmark bookmark =BookmarkManager.getInstance().createWebLink(Long.parseLong(values[0]),values[1],values[2],values[3]);
           bookmarkList.add(bookmark);
      }*/
        String query = "Select * from Weblink";
        ResultSet rs = stmt.executeQuery(query);
        List<Bookmark> bookmarkList = new ArrayList<>();
        while(rs.next()){
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String url = rs.getString("url");
        String host = rs.getString("host");

        Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id,title,url,host);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }




    private static void loadMovies(Statement stmt)throws SQLException {
        /*
        bookmarks[1][0]= BookmarkManager.getInstance().createMovie(3000,	"Citizen Kane"	,"",1941,new String[]{"Orson Welles","Joseph Cotten"} , new String[]{"Orson Welles"}, MovieGenre.CLASSICS,8.5);
        bookmarks[1][1]= BookmarkManager.getInstance().createMovie(3001	,"The Grapes of Wrath",	"",1940, new String[]{	"Henry Fonda","Jane Darwell"},new String[]{"John Ford"},MovieGenre.CLASSICS	,8.2);
        bookmarks[1][2]= BookmarkManager.getInstance().createMovie(3002	,"A Touch of Greatness",""	,2004,new String[]{	"Albert Cullum"} ,	new String[]{"Leslie Sullivan"},	MovieGenre.DOCUMENTARIES,7.3);
        bookmarks[1][3]= BookmarkManager.getInstance().createMovie(3003	,"The Big Bang Theory"," "	,2007,new String[]{"Kaley Cuoco","Jim Parsons"},new String[]{"Chuck Lorre","Bill Prady"},	MovieGenre.TV_SHOWS,	8.7	);
        bookmarks[1][4]= BookmarkManager.getInstance().createMovie(3004	,"Ikiru",""	 ,1952	,new String[]{"Takashi Shimura","Minoru Chiaki	"},new String[]{"Akira Kurosawa"},MovieGenre.FOREIGN_MOVIES,8.4);

        //String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
        */
        /*
        List<String> data = new ArrayList<>();

        IOUtil.read(data,"Movie");
        List<Bookmark> bookmarkList = new ArrayList<>();
        for(String row : data){
            String [] values = row.split("\t");
            String [] cast = values[3].split(",");
            String [] directors = values[4].split(",");
          //  bookmarks[1][colNo++]=BookmarkManager.getInstance().createMovie(Long.parseLong(values[0]), values[1],"", Integer.parseInt(values[2]), cast, directors, values[5] ,Double.parseDouble(values[6]));
         */
        String query = "SELECT m.id, title, release_year, " +
                "GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, " +
                "GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS director, " +
                "movie_genre_id, imdb_rating " +
                "FROM movie m " +
                "JOIN movie_actor ma ON m.id = ma.movie_id " +
                "JOIN actor a ON ma.actor_id = a.id " +
                "JOIN movie_director md ON m.id = md.movie_id " +
                "JOIN director d ON md.id = d.id " +
                "GROUP BY m.id, title, release_year, movie_genre_id, imdb_rating";

        ResultSet rs= stmt.executeQuery(query);
        List<Bookmark> bookmarkList = new ArrayList<>();

        while(rs.next()){
            long id  = rs.getLong("id");
            String title = rs.getString("title");
            int releaseYear = rs.getInt("release_year");
            String [] cast = rs.getString("cast").split(",");
            String [] directors = rs.getString("director").split(",");
            int genre_id = rs.getInt("movie_genre_id");
            MovieGenre genre = MovieGenre.values()[genre_id];
            double imdbRating = rs.getDouble("imdb_rating");
            Bookmark bookmark= BookmarkManager.getInstance().createMovie(id,title,releaseYear,cast,directors,genre,imdbRating);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }
    private static void loadBooks(Statement stmt )throws SQLException {
    /*   bookmarks[2][0]= BookmarkManager.getInstance().createBook(4000,	"Walden",	"",1854,	"Wilder Publications",	new String[]{"Henry David Thoreau"}	, BookGenre.PHILOSOPHY,4.3);
        bookmarks[2][1]= BookmarkManager.getInstance().createBook(4001,	"Self-Reliance and Other Essays",""	,1993	,"Dover Publications"	,new String[]{"Ralph Waldo Emerson"},BookGenre.PHILOSOPHY,	4.5);
        bookmarks[2][2]= BookmarkManager.getInstance().createBook(4002,"Light From Many Lamps","",	1988	,"Touchstone" ,	new String[]{"Lillian Eichler Watson"},	BookGenre.PHILOSOPHY ,5.0);
        bookmarks[2][3]= BookmarkManager.getInstance().createBook(4003,	"Head First Design Patterns",	"",2004	,"O'Reilly Media"	, new String[]{"Eric Freeman","Bert Bates","Kathy Sierra","Elisabeth Robson"	},BookGenre.TECHNICAL,	4.5);
        bookmarks[2][4]= BookmarkManager.getInstance().createBook(4004,"Power of Your Subconscious Mind","",2019,"Amazing Reads", new String[]{"Dr. Joseph Murphy"}, BookGenre.SELF_HELP,5.0);


    //String []data = new String[BOOKMARK_COUNT_PER_TYPE];
    */
     /*   List<String> data = new ArrayList<>();
        IOUtil.read(data,"Book");
        List<Bookmark>bookmarkList = new ArrayList<>();
        for (String row:data) {
            String [] values = row.split("\t");
            String[]  authors = values[5].split(",");
            Bookmark bookmark= BookmarkManager.getInstance().createBook(Long.parseLong(values[0]), values[1], " ",Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5]), Double.parseDouble(values[6]));
            bookmarkList.add(bookmark);
        }*/
        String query ="Select b.id ,title,publication_year,p.name ,GROUP_CONCAT(a.name SEPARATOR ',') as authors ,book_genre_id , amazon_rating,created_date"
                +" from Book b ,publisher p , author a ,book_author ba "
                +"where b.publisher_id= p.id  and b.id= ba.book_id and ba.author_id = a.id group by b.id";


        ResultSet rs = stmt.executeQuery(query);
        List<Bookmark> bookmarkList = new ArrayList<>();
        while(rs.next()){
            long id = rs.getLong("id");
            String title =rs.getString("title");
            int publicationYear = rs.getInt("publication_year");
            String publisher = rs.getString("name");
            String[] authors = rs.getString("authors").split(",");
            int genreId=rs.getInt("book_genre_id");
            BookGenre genre = BookGenre.values()[genreId];
            double amazonRating = rs.getDouble("amazon_rating");

            Date createdDate = rs.getDate("created_date");
            System.out.println("createdDate: " + createdDate);
            Timestamp timeStamp = rs.getTimestamp(8);
            System.out.println("timeStamp: " + timeStamp);
            System.out.println("localDateTime: " + timeStamp.toLocalDateTime());

            System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

          //  Bookmark bookmark= BookmarkManager.getInstance().createBook(id, title, publicationYear, publisher, authors, genre,amazonRating );
          //  bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }

    public static void add(UserBookmark userBookmark) {
         userBookmarks.add(userBookmark);
//        bookmarkIndex += 1;
    }
}
