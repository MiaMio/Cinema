package CinemaSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Film {
	private String film_name;
	private String film_abstract;
	public Film(String film_name, String film_abstract) {
		super();
		this.film_name = film_name;
		this.film_abstract = film_abstract;
	}
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}
	public String getFilm_abstract() {
		return film_abstract;
	}
	public void setFilm_abstract(String film_abstract) {
		this.film_abstract = film_abstract;
	}
	@Override
	public String toString() {
		return "Film [film_name=" + film_name + ", film_abstract=" + film_abstract + "]";
	}
	public void printInfo(Connection connection)throws Exception{
		System.out.println("Name: "+film_name);
		System.out.println(film_abstract);
		PreparedStatement preparedStatement=connection.prepareStatement("select comment_customer, comment_content, comment_time, comment_score from comments where comment_film=?");
		preparedStatement.setString(1, film_name);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()){
			String comment=resultSet.getString("comment_content");
			String by_who=resultSet.getString("comment_customer");
			String time=resultSet.getString("comment_time");
			int score=resultSet.getInt("comment_score");
			System.out.println("----"+by_who+"     Scores: "+score);
			System.out.println(comment);
			System.out.println(time);
			System.out.println();
		}
	}
}
