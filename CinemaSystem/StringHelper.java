package CinemaSystem;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringHelper {
	public static String cut(String string, int len){
		int now=len;
		StringBuilder sBuilder=new StringBuilder(string);
		while(now<sBuilder.toString().length()){
			while(now<sBuilder.toString().length() && !Character.isWhitespace(sBuilder.toString().charAt(now)))
				now++;
			System.out.println(now);
			if(now+1<sBuilder.toString().length())
				sBuilder.insert(now+1, "\r\n");
			now+=len;
		}
		return sBuilder.toString();
	}
	public static boolean ifExceed(String now_date, String film_date){
		int len=now_date.length();
		for(int i=0; i<len; i++){
			if(film_date.charAt(i)<now_date.charAt(i))
				return true;
			else if(film_date.charAt(i)>now_date.charAt(i)) {
				return false;
			}
		}
		return true;
	}
	public static String getDate(){
		Date date=new Date();
		long times=date.getTime();
		SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
		String dateString=format.format(times);
		return dateString;
	}
	public static void main(String[] args) {
		String now_date="06-01 20:47";
		String film_date="06-05 12:00";
		System.out.println(ifExceed(now_date, film_date));
	}
}
