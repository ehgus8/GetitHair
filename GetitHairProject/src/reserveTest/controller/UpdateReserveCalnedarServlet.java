package reserveTest.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import customer.model.service.CustomerService;
import reserveTest.model.service.ReserveTestService;
import reserveTest.model.vo.ReserveTest;

/**
 * Servlet implementation class UpdateReserveCalnedarServlet
 */
@WebServlet(name = "UpdateReserveCalnedar", urlPatterns = { "/updateReserveCalnedar" })
public class UpdateReserveCalnedarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateReserveCalnedarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startDateString = request.getParameter("reserveStartDate");
		String endDateString = request.getParameter("reserveEndDate");
		String dateString = request.getParameter("reserveDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh시 mm분");
		java.sql.Date startDate = null;
		java.sql.Date endDate = null;
		java.sql.Date date = null;
		try {
			Date utilStartDate = sdf.parse(startDateString);
			Date utillEndDate = sdf.parse(endDateString);
			Date utillDate = sdf.parse(dateString);
			startDate = new java.sql.Date(utilStartDate.getTime());
			endDate = new java.sql.Date(utillEndDate.getTime());
			date = new java.sql.Date(utillDate.getTime());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// 여기까지 String Date -> Sql.Date로 변환
		System.out.println(request.getParameter("reserveNo"));
		// 2. view에서 넘어온 데이터 처리
		ReserveTest rt = new ReserveTest();
		rt.setReserveTitle(request.getParameter("reserveTitle"));
		int reserveNo = request.getParameter("reserveNo")==null || request.getParameter("reserveNo").equals("")  ? 0 : Integer.parseInt(request.getParameter("reserveNo"));
		rt.setReserveNo(reserveNo);
		
		rt.setReserveStartDate(startDate);
		rt.setReserveEndDate(endDate);
		rt.setReserveDate(date);
		rt.setReserveStatus(request.getParameter("reserveStatus"));
		rt.setReserveCustReq(request.getParameter("reserveCustReq"));
		rt.setReserveDesignerReq(request.getParameter("reserveDesignerReq"));
		rt.setReserveDesignerMemo(request.getParameter("reserveDesignerMemo"));
		// 3. 비지니스로직 처리
		int result = new ReserveTestService().updateReserveCalnedar(rt);
		System.out.println("resut : " + result);

		// 4. 결과출력
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reserve/designer/designerReserve.jsp");
		if(result!=0) {
			request.setAttribute("msg", "예약 상세정보 수정 완료.");
			request.setAttribute("loc", "/WEB-INF/view/reserve/designer/designerReserve.jsp");
		}else {
			request.setAttribute("msg", "예약 상세정보 수정 실패.");
			request.setAttribute("loc", "/WEB-INF/view/reserve/designer/designerReserve.jsp");
		}
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
