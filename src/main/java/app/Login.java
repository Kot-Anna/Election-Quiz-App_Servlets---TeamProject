package app;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import data.Admin;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Dao dao;

	@Override
	public void init() {
		dao = new Dao();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(false);

		boolean isLoggedIn = false;
		if (session == null) {
		} else {
			if (session.getAttribute("isLoggedIn") == null) {

			} else {
				isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
			}
		}

		if (isLoggedIn) {
			System.out.println("logged in");
			response.sendRedirect("/jsp/admin-dashboard.jsp");
		} else {
			response.sendRedirect("/jsp/login.jsp");
		}
	}

	/**
	 * @throws ServletException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Admin adm = new Admin();
		adm.setEmail(request.getParameter("email"));
		adm.setPass(crypt(request.getParameter("password")));

		if (session.getAttribute("isLoggedIn") == null) {
			session.setAttribute("isLoggedIn", false);
		}

		if (dao.getConnection()) {
			if (dao.adminLogin(adm)) {
				session.setAttribute("isLoggedIn", true);
			}
		} else {
			System.out.println("No connection to database");
		}

		if ((boolean) session.getAttribute("isLoggedIn")) {
			System.out.println("logged in");
			response.sendRedirect("/jsp/admin-dashboard.jsp");
		} else {
			System.out.println(dao.matchedAdminInt(adm));
			System.out.println("not logged in");

			response.sendRedirect("/jsp/login.jsp?loginError=true");

//			request.setAttribute("loginError", true);
//			RequestDispatcher rd = request.getRequestDispatcher("/jsp/login.jsp");
//			rd.forward(request, response);

		}
	}

	private void matchedAdminArrPrintEmail(Admin adm) {
		ArrayList<Admin> ad = null;
		ad = dao.matchedAdmin(adm);
		for (Admin admin : ad) {
			System.out.println(admin.getEmail());
		}
	}

	private void matchedAdminPrintInt(Admin adm) {
		int ai = 0;
		ai = dao.matchedAdminInt(adm);
		System.out.println(ai);
	}

	private static String crypt(String str) {
//		if (str == null || str.length() == 0) {
//			throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
//		}

		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");

			digester.update(str.getBytes());
			byte[] hash = digester.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
