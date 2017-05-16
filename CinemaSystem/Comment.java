package CinemaSystem;

public class Comment {
	private String comment_film;
	private String comment_customer;
	private String comment_content;
	private String comment_time;
	private int comment_score;
	public Comment(String comment_film, String comment_customer, String comment_content, String comment_time,
			int comment_score) {
		super();
		this.comment_film = comment_film;
		this.comment_customer = comment_customer;
		this.comment_content = comment_content;
		this.comment_time = comment_time;
		this.comment_score = comment_score;
	}
	@Override
	public String toString() {
		return "Comment [comment_film=" + comment_film + ", comment_customer=" + comment_customer + ", comment_content="
				+ comment_content + ", comment_time=" + comment_time + ", comment_score=" + comment_score + "]";
	}
	public String getComment_film() {
		return comment_film;
	}
	public void setComment_film(String comment_film) {
		this.comment_film = comment_film;
	}
	public String getComment_customer() {
		return comment_customer;
	}
	public void setComment_customer(String comment_customer) {
		this.comment_customer = comment_customer;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_time() {
		return comment_time;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public int getComment_score() {
		return comment_score;
	}
	public void setComment_score(int comment_score) {
		this.comment_score = comment_score;
	}
	
}
