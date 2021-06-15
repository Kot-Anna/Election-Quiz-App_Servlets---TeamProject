package app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.Dao;
import data.Candidate;

@WebServlet(name = "updateCan", urlPatterns = { "/updateCan" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

/**
 * 
 * Servlet allowing logged in admin to change candidates data
 *
 */
public class UpdateCan extends HttpServlet {
	private Dao dao;

	public void init() {
		dao = new Dao();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		boolean isLoggedIn = false;
		if (session == null) {
		} else {
			if (session.getAttribute("isLoggedIn") == null) {

			} else {
				isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
			}
		}

		if (!isLoggedIn) {
			response.sendRedirect("/index.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		boolean isLoggedIn = false;
		if (session == null) {
		} else {
			if (session.getAttribute("isLoggedIn") == null) {

			} else {
				isLoggedIn = (boolean) session.getAttribute("isLoggedIn");
			}
		}

		if (!isLoggedIn) {
			response.sendRedirect("/index.jsp");
		} else {
			String img = "";

			// get the file chosen by the user
			Part filePart = request.getPart("profile_pic");

			boolean isthereafile = false;
			if (request.getPart("profile_pic").getSize() > 0) {
				isthereafile = true;
			}

			if (isthereafile) {
				// get the InputStream to store the file somewhere
				InputStream fileInputStream = filePart.getInputStream();

				// for example, you can copy the uploaded file to the server
				// note that you probably don't want to do this in real life!
				// upload it to a file host like S3 or GCS instead

				File fileToSave = new File(
						dao.getUploadPath() + filePart.getSubmittedFileName());
				Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);

				System.out.println(request.getParameter("profile_pic"));
			} else {
				img = request.getParameter("img");
			}

			String imgVal = isthereafile ? "/img/" + filePart.getSubmittedFileName() : img;
			System.out.println(imgVal);

			String id = request.getParameter("id");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String city = request.getParameter("city");
			String age = request.getParameter("age");
			String profession = request.getParameter("profession");
			String political_party = request.getParameter("political_party");
			String why_candidate = request.getParameter("why_candidate");
			String about = request.getParameter("about");
			String profile_pic = imgVal;

			Candidate c = new Candidate(id, fname, lname, city, age, profession, political_party, why_candidate, about,
					profile_pic);

			ArrayList<Candidate> list = new ArrayList<>();
			if (dao.getConnection()) {
				list = dao.updateCandidate(c, dao);
			}

			request.setAttribute("candidatelist", list);
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/admin-candidate.jsp");
			rd.forward(request, response);
		}
	}

}
