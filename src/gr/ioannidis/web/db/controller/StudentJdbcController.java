package gr.ioannidis.web.db.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import gr.ioannidis.web.db.model.Student;


public class StudentJdbcController {

	private DataSource dataSource;

	
	public StudentJdbcController(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	public List<Student> getStudents() {
		List<Student> listOfStudent = new ArrayList<>();

		try (Connection conn = dataSource.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("select * from student order by last_name")) {

			while (rs.next()) {
				int id = rs.getInt("id");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
				String email = rs.getString("email");
				listOfStudent.add(new Student(id, firstname, lastname, email));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listOfStudent;
	}

	public void create(Student student) {
		String query = "insert into student (first_name, last_name, email) values (?, ?, ? )";

		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(query)) {

			preparedStmt.setString(1, student.getFirstname());
			preparedStmt.setString(2, student.getLastname());
			preparedStmt.setString(3, student.getEmail());

			preparedStmt.execute();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Student find(int id) throws ServletException {
		Student std = new Student();
		String query = "SELECT first_name, last_name, email FROM student WHERE id = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(query)) {

			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			if (rs.next()) {
				std.setId(id); // must-have for knowing which student to update
				std.setFirstname(rs.getString("first_name"));
				std.setLastname(rs.getString("last_name"));
				std.setEmail(rs.getString("email"));
			}
		} catch (SQLException ex) {
			throw new ServletException("Could not find the Student, " + ex.getMessage());
		}
		return std;
	}

	public void update(Student std) throws ServletException {
		String query = "UPDATE student SET first_name = ?, last_name = ?, email = ? WHERE id = ? ";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(query)) {

			preparedStmt.setString(1, std.getFirstname());
			preparedStmt.setString(2, std.getLastname());
			preparedStmt.setString(3, std.getEmail());
			preparedStmt.setInt(4, std.getId());

			preparedStmt.executeUpdate();

		} catch (SQLException ex) {
			throw new ServletException("Something got wrong during updating the student: " + ex.getMessage());
		}
	}

	public void delete(int studentId) throws ServletException {
		String query = "DELETE FROM student WHERE id = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement preparedStmt = conn.prepareStatement(query)) {

			preparedStmt.setInt(1, studentId);
			preparedStmt.execute();

		} catch (SQLException ex) {
			throw new ServletException("Something got wrong during deleting the student: " + ex.getMessage());
		}
	}

}
