package CinemaSystem;

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
	public static void main(String[] args) {
		String string2="aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab aaab";
		System.out.println(Character.isWhitespace(string2.charAt(24)));
		String string="aaaaaaaaaaaaaaaaaaaaaaaaaab aaaaaaaaaaaaaaaaaaaaaaaaaaab aaaaaaa"
	 			+ "aaaaab aaaaaaaab aaaaaaaaaab aaaaaaab aaaaaab aaaaab aaaaab aab aa"
				+ "aab aab aaaaaaab aaaaaaab aaaaaaaaab aaaaaab aaaaaaaaab aaaaaaaab aaaaaaaab aaa"
				+ "aaaaab aab aaaaaab aaaaaaab aaaaaaab aaaaaaaab aaaaaab aaaaaaab aaaaaab aaa";
//		StringBuilder stringBuilder=new StringBuilder(string);
//		stringBuilder.insert(3, "\r\n");
		System.out.println(cut(string, 70));
	}
}
