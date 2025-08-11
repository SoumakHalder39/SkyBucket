package thrilliod;

import entities.Bookmark;
import entities.User;
import managers.BookmarkManager;
import managers.UserManager;
//import bgjobs.WebPageDownloaderTask;

import java.util.List;

public class Launch {
    private static List<User> users ;
    private static List<List<Bookmark>> bookmarks;
    private static void loadData() {
    System.out.println("1..loading Data...");
     DataStore.loadData();

     users = UserManager.getInstance().getUsers();
    bookmarks = BookmarkManager.getInstance().getBookmarks();

        System.out.println("printing Data ....");
        printUserData();
        System.out.println("\n");
        printBookmarkData();
    }



    private static void printUserData() {
        for (User user:users) {
            System.out.println(user);
        }
    }
    private static void printBookmarkData() {
        for( List<Bookmark> bookmarkList:bookmarks){
            for (Bookmark bookmark :bookmarkList) {
                System.out.println(bookmark);
            }System.out.println("\n");
        }
    }
    private static void Start() {
     //   System.out.println("\n 2 .. Bookmarking...");
        for (User user:users) {
            View.browse(user,bookmarks);
        }
    }

    public static void main(String[] args) {
        loadData();
       Start();

        //background jobs
        //runDownloaderJob();
    }

//    private static void runDownloaderJob() {
//        WebPageDownloaderTask task = new WebPageDownloaderTask(true);
//        (new Thread(task)).start();
//    }


}
