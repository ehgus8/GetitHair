package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import reserveTest.model.service.ReserveTestService;

/**
 * Servlet implementation class test2FormServlet
 */
@WebServlet(name = "test2Form", urlPatterns = { "/test2Form" })
public class test2FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test2FormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		String Title = request.getParameter("JsonTitles");
		String Start = request.getParameter("JsonStartDate");
		String End = request.getParameter("JsonEndDate");
		String reserveNo = request.getParameter("JsonReserveNo");
		JsonParser parser = new JsonParser();
		System.out.println(Title);
		System.out.println(Start);
		System.out.println(End);
		System.out.println(reserveNo);
		JsonArray jsonTitle = (JsonArray)parser.parse(Title);
		JsonArray jsonStart = (JsonArray)parser.parse(Start);
		JsonArray jsonEnd = (JsonArray)parser.parse(End);
		JsonArray jsonNo = (JsonArray)parser.parse(reserveNo);
		//값 받기 끝
		for(int i = 0 ; i < jsonTitle.size() ; ++i) {
			String title = jsonTitle.get(i).toString().replaceAll("\"", "");
			String start = jsonStart.get(i).toString();
			String end = jsonEnd.get(i).toString();
			int no = Integer.parseInt(jsonNo.get(i).toString().replaceAll("\"", ""));
		
			DateFormat dateFormat = new SimpleDateFormat("\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\"");
			java.sql.Date startDate = null;
			java.sql.Date endDate = null;
			try {
				java.util.Date date = dateFormat.parse(start);
				startDate = new java.sql.Date(date.getTime());
				java.util.Date date1 = dateFormat.parse(end);
				endDate= new java.sql.Date(date1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			result += new ReserveTestService().updateDateReserveTest(no,title,startDate,endDate);
			System.out.println(result);
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
