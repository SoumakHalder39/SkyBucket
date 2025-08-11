package Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.UserManager;

/**
 * Servlet implementation class AuthController
 */
@WebServlet({"/auth" , "/auth/logout"})
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("auth me aaya");
		System.out.println("path is :" + request.getServletPath());
		if(!request.getServletPath().contains("logout")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			long userId =UserManager.getInstance().authenticate(email,password);
			if(userId==-1) {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
			else {
				HttpSession session  =request.getSession();//session id expire for 30 min
				session.setAttribute("userId", userId);
				request.getRequestDispatcher("bookmark/mybooks").forward(request, response);
				//for servlets no need of "/"
			}
		}else {
			System.out.println("logout huya");
			request.getSession().invalidate();
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
