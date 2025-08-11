package Dao;

import entities.User;
import managers.UserManager;
import thrilliod.DataStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import constants.Gender;
import constants.UserType;

public class UserDao {
    public List<User> getUsers(){
        return DataStore.getUsers();
    }

	public User getUsers(long userId) {
		User user = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println();
			e.printStackTrace();
		}
		  try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio?user=root&password=Amit@015&ServerTimeZone='UTC'");
	                Statement stmt =  conn.createStatement(); ){
			  

			  String query = "select * from user where id = "+userId;
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

	               user = UserManager.getInstance().createUser(id,email,password,firstName,lastName,gender,userType);
	              
	          }

	        } catch (SQLException throwables) {
	            throwables.printStackTrace();
	        }

		
		return user;
	}

	public long authenticate(String email, String password) {
		
		//load the driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//create the connection
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thrillio?user=root&password=Amit@015&ServerTimeZone='UTC'");
				Statement stmt = conn.createStatement();)
		{
			//Write the query
			String query = "select id from User where email ='"+email+"' AND password ='"+ password +"';";
			System.out.println(query);
			//Get the result
			ResultSet  rs = stmt.executeQuery(query);
			
			//return the result
			while(rs.next()) {
				return rs.getLong("id");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
  

}
