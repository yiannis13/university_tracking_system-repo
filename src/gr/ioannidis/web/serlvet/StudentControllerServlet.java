package gr.ioannidis.web.serlvet;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import gr.ioannidis.web.db.controller.StudentJdbcController;
import gr.ioannidis.web.db.model.Student;


@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// reference to the datasource/connection pool. The container injects the dataSource
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;
	private StudentJdbcController studentJdbcController;

	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentJdbcController = new StudentJdbcController(dataSource);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Read the "command" parameter
		String command = request.getParameter("command");
		if (command == null) {
			command = "LIST";
		}
		switch (command) {
		case "LIST":
			// list the student. . . in MVC manner		
			getListOfStudentFromDbAndPassItToJsp(request, response);
			break;
		case "LOAD":			
			loadStudent(request, response);
			break;
		case "UPDATE":
			updateStudent(request,response);
			break;
		case "DELETE":
			deleteStudent(request, response);
			break;
		default:
			getListOfStudentFromDbAndPassItToJsp(request, response);
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		if (command == null) {
			getListOfStudentFromDbAndPassItToJsp(request, response);
		}
		if ("ADD".equals(command)) {
			addStudent(request, response);			
		}
	}
	

	private void getListOfStudentFromDbAndPassItToJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Student> list = studentJdbcController.getStudents();

		// add the list in the request object
		request.setAttribute("student_list", list);

		// send the data to the view (JSP page)
		RequestDispatcher dispatcher = request.getRequestDispatcher("list-students.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Student newStudent = new Student();
		newStudent.setFirstname(request.getParameter("firstName"));
		newStudent.setLastname(request.getParameter("lastName"));
		newStudent.setEmail(request.getParameter("email"));

		studentJdbcController.create(newStudent);

		// Redirect to Student-list to avoid multiple-browser reload issue. 
		// (Post/Redirect/Get design pattern)
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet?command=LIST");
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String studentId = request.getParameter("studentId");
		Student student = studentJdbcController.find(Integer.valueOf(studentId));
		request.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student updatedStudent = new Student();
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		updatedStudent.setId(studentId);
		updatedStudent.setFirstname(request.getParameter("firstName"));
		updatedStudent.setLastname(request.getParameter("lastName"));
		updatedStudent.setEmail(request.getParameter("email"));
		
		studentJdbcController.update(updatedStudent);
		
		getListOfStudentFromDbAndPassItToJsp(request, response);		
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		studentJdbcController.delete(studentId);
		getListOfStudentFromDbAndPassItToJsp(request, response);		
	}

}
