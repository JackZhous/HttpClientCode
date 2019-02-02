import java.util.Arrays;
import java.util.List;
import java.net.URI;
import java.lang.String;

public class test{
	public static void main(String[] args){
		URI uri = URI.create("http://zhushou.sogou.com/open/user/app/index.html");
		String text = uri.getHost();
		String scheme = uri.getScheme();
		int port = uri.getPort();
		System.out.println(text + "  " + port + "  " + scheme);
	}
}
