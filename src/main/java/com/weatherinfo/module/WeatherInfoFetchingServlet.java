package com.weatherinfo.module;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weatherinfo.core.ReadWeatherJSON;
import com.weatherinfo.core.WeatherDataObject;

/**
 * Servlet implementation class WeatherInfoFetchingServlet
 */
@WebServlet("/check")
public class WeatherInfoFetchingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WeatherInfoFetchingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int numberOfDays = 0;
		String cityName = request.getParameter("cityName");
		String days = request.getParameter("days");

		if (days == null || days.equals("")) {
			numberOfDays = 0;
		} else {
			numberOfDays = Integer.parseInt(days);
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (numberOfDays > 16) {
			out.println("You can only see forecast for 16 days ahead.");
		} else {
			out.println("<p>City Name is : " + cityName + "</p>");
			out.println();
			List<WeatherDataObject> weatherInfoList = ReadWeatherJSON.readJSONData(cityName, numberOfDays);
			
			request.setAttribute("weatherResponse", weatherInfoList);
			request.getRequestDispatcher("/WEB-INF/response.jsp").forward(request, response);
		}
	}
}
