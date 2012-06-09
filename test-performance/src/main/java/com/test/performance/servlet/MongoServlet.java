package com.test.performance.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MongoServlet
 */
@WebServlet("/servlet/test")
public class MongoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() {
		MongoHelper.getInstance();
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MongoServlet() {
        super();        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");				
		try {
			String desc = null;
			if(method != null ) {
				String key = request.getParameter("key");			
				switch (method) {					
				case "query":				
					desc = MongoHelper.getInstance().queryTest(key);
					break;
				default:
					desc = "method have not result returned";					
				}
			} else {
				desc = "Get Hello world !";
			}
			response.setContentType("text/plain");
			response.getOutputStream().write(desc.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");		

		try {		    	
			String desc = null;
			if(method != null ) {
				String key = request.getParameter("key");
				
				switch (method) {
				case "insert":
					desc = MongoHelper.getInstance().insertTest(key);
					break;
				case "query":
					desc = MongoHelper.getInstance().queryTest(key);
					break;
				case "update":
					desc = MongoHelper.getInstance().updateTest(key);
					break;
				case "remove":
					desc = MongoHelper.getInstance().removeTest(key);
					break;
				case "queryupdate":
					desc = MongoHelper.getInstance().queryUpdateTest(key);
					break;
				default:
					desc = "method have not result returned";
				}	
			} else {
				desc = "Post Hello world !";
			}
			
			response.setContentType("text/plain");
			response.getOutputStream().write(desc.getBytes());	    	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw new ServletException();
		}
	}
}
