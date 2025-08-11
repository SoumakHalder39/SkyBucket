package Controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constants.KidFriendlyStatus;
//import entities.Book;
import entities.Bookmark;
import entities.User;
import managers.BookmarkManager;
import managers.UserManager;

//@WebServlet({ "/bookmark", "/bookmark/save", "/bookmark/mybooks" })
/*
 * it would act like a controller or a servlets
 */

public class BookmarksController /*extends HttpServlet*/ {

	
	  private static BookmarksController instance = new BookmarksController();
	  private BookmarksController(){
	 
	  }
	 
	  public static BookmarksController getInstance(){ return instance; } 
	 // tomcat will automatically create a singleton class
	 

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
//	public BookmarksController() {
//
//	}

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		RequestDispatcher disp = null;
//		if( request.getSession().getAttribute("userId")!=null && request.getSession(false)!=null) {
//			long userId =(long)request.getSession().getAttribute("userId");
//
//		System.out.println("path is :" + request.getServletPath());
//
//		if (request.getServletPath().contains("save")) {
//			// save
//			disp = request.getRequestDispatcher("/mybooks.jsp");
//			
//			String b_id = request.getParameter("bid");
//			
//			System.out.println("book is" + b_id);
//			
//			User user = UserManager.getInstance().getUsers(userId);
//			Bookmark book = BookmarkManager.getInstance().getBooks(Long.parseLong(b_id));
//			
//			System.out.println("boookmark is "+book);
//			
//			BookmarkManager.getInstance().saveUserBookmark(user, book);
//
//			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
//			request.setAttribute("books", list);
//
//			
//		} else if (request.getServletPath().contains("mybooks")) {
//			disp = request.getRequestDispatcher("/mybooks.jsp");
//			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(true, userId);
//			request.setAttribute("books", list);
//		}
//
//		else {
//			disp = request.getRequestDispatcher("/browse.jsp");
//			Collection<Bookmark> list = BookmarkManager.getInstance().getBooks(false, userId);
//			request.setAttribute("books", list);
//		}
//		}
//		else{
//			System.out.println("nahi mila");
//			disp = request.getRequestDispatcher("login.jsp");
//		}
//		disp.forward(request, response);
//		
//	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		BookmarkManager.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
	}

	public void share(User user, Bookmark bookmark) {
		BookmarkManager.getInstance().share(user, bookmark);
	}
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
}
