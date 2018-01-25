import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider  {

	 static String sendGet(String url) {

	  String result = "";

	  BufferedReader in = null;
	  try {
	  
	   URL realUrl = new URL(url);
	   
	   URLConnection connection = realUrl.openConnection();

	   connection.connect();
	
	   in = new BufferedReader(new InputStreamReader(
	     connection.getInputStream(), "UTF-8"));
	   
	   String line;
	   while ((line = in.readLine()) != null) {

	    result += line;
	   }
	  } catch (Exception e) {
	   System.out.println("发送GET请求出现异常！" + e);
	   e.printStackTrace();
	  }
	  finally {
	   try {
	    if (in != null) {
	     in.close();
	    }
	   } catch (Exception e2) {
	    e2.printStackTrace();
	   }
	  }
	  return result;
	 }
	 
	 static ArrayList<知道> GetRecommendations(String content) throws UnsupportedEncodingException {
	  ArrayList<知道> results = new ArrayList<知道>();
	  Pattern pattern = Pattern
	    .compile("daily-cont-top.+?<h2>.+?href=\"(.+?)\".+?</h2>");
	  Matcher matcher = pattern.matcher(content);
	  Boolean isFind = matcher.find();
	  while (isFind) {
	   知道 zhidaoTemp = new 知道(matcher.group(1));
	   results.add(zhidaoTemp);
	   isFind = matcher.find();
	  }
	  return results;
	 }
	
}
