package thrilliod;

import Controllers.BookmarksController;
import Partner.Shareable;
import constants.KidFriendlyStatus;
import constants.UserType;
import entities.Bookmark;
import entities.User;

import java.util.List;

public class View {//correspond to the ui
  public static void browse(User user , List<List<Bookmark>> bookmarks){
        System.out.println("\n"+user.getEmail()+"  is browsing items...");

       //   trial
        //user will browse through all the bookmark and for each bookmark we will use a randomised method to say if user bookmark that or not
        int bookmarkCount =0;

        for(List<Bookmark> bookmarkList:bookmarks){
            for(Bookmark bookmark :bookmarkList){
                //BOOKMARKING !!!!!!
          //     if(bookmarkCount < USER_BOOKMARK_LIMIT){
                boolean isBookmarked =getBookmarkDecision(bookmark);
                if(isBookmarked){
                    bookmarkCount++;
                    BookmarksController.getInstance().saveUserBookmark(user,bookmark);//server side request
                    System.out.println("New Item Bookmarked ----"+ bookmark);

                   //Mark as Kid Friendly
                      if(user.getUserType().equals(UserType.EDITOR)
                          ||user.getUserType().equals(UserType.CHIEF_EDITOR)){

                          if(bookmark.isKidFriendlyEligible()&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)){
                              KidFriendlyStatus kidFriendlyStatus =getKidFriendlySatusDecision(bookmark);
                              if(!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)){
                                        BookmarksController.getInstance().setKidFriendlyStatus(user,kidFriendlyStatus,bookmark);
                                }
                            }
                            //Sharing bookmark
                            if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)&& bookmark instanceof Shareable)
                            {
                                boolean isShared = getSharedDecision();
                                if(isShared){
                                    BookmarksController.getInstance().share(user,bookmark);
                                }
                            }
                        }
                    }
               // }
            }
        }



    
  }
    //Todo: below methods will be simulate input by user later
    private static boolean getSharedDecision() {
        return Math.random()<0.5 ? true : false;
    }

    private static KidFriendlyStatus getKidFriendlySatusDecision(Bookmark bookmark) {
      double decision = Math.random();
      return decision< 0.4? KidFriendlyStatus.APPROVED:
                (decision >=0.4
                        && decision <0.8)?KidFriendlyStatus.REJECTED:KidFriendlyStatus.UNKNOWN;
    }


    private static boolean getBookmarkDecision(Bookmark bookmark) {//Random method
         return Math.random()< 0.5 ? true : false;
    }



    /* public static void bookmark(User user , Bookmark[][] bookmarks){
        System.out.println("\n"+user.getEmail()+"is bookmarking");
        for(int  i=0;i<DataStore.USER_BOOKMARK_LIMIT;i++){
        //randomly select 5 of bookmarks from 15 bookmarks stored in "bookmarks "array
            int typeOffSet=(int)(Math.random()*DataStore.BOOKMARK_TYPES_COUNT);
            int bookmarkOffSet=(int)(Math.random()*DataStore.BOOKMARK_COUNT_PER_TYPE);

            Bookmark bookmark = bookmarks[typeOffSet][bookmarkOffSet];

            BookmarksController.getInstance().saveUserBookmark(user,bookmark);
            System.out.println(bookmark);
        }

    }*/
}
