import java.io.UnsupportedEncodingException;
import com.alibaba.fastjson.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//各文章标题、链接、作者、阅读量、评论前5条
public class 知道 extends Main {
	 public String title;// 标题
	 public String author;// 作者
	 public String zhidaoUrl;// 网页链接
	 public String readNum;	//阅读量
	 public ArrayList<String> comments;// 存储评论
	 // 构造方法初始化数据
	 public String pvUrl;
	 public String zUrl;
	 public String toString() {
		 String a="";
		 for(int i=0;i<comments.size();i++)
		 a= a+(comments.get(i)+"\n");
		  return "文章标题：" + title + "\n" + "作者：" + author + "\n"+"阅读量："+readNum+"\n"
		    + "链接：" + zhidaoUrl + "\n评论：\n" + a + "\n";
		 }
	 public 知道(String url) throws UnsupportedEncodingException {
		 title = "";
		 author = "";
		 zhidaoUrl = "";
		 comments = new ArrayList<String>();
		 readNum="";
		 pvUrl="";
		 zUrl="";
		 if(getRealUrl(url)) {
			   System.out.println("正在抓取" + zhidaoUrl);
			   // 根据url获取该问答的细节
			   String content = Spider.sendGet(zhidaoUrl);
			   Pattern pattern;
			   Matcher matcher;
			   // 匹配标题
			   //<h1 id="daily-title" class="title">小儿发热无需马上“灭火”，适度发热能增强免疫力</h1>
			   pattern = Pattern.compile("detail.+?<h1.+?>(.*)</h1>");
			   matcher = pattern.matcher(content);
			   if (matcher.find()) {
			    title = matcher.group(1);
			   }
			   // 匹配作者
			   pattern = Pattern
			     .compile("detail-author.+?<span.+?>(.+?)</span>");
			   matcher = pattern.matcher(content);
			   if (matcher.find()) {
			    author = matcher.group(1);
			   }
			   //匹配阅读量
			   String contentpv=Spider.sendGet(pvUrl);
			   pattern = Pattern.compile("data\".+?pv\":(.+?)}}");
			   matcher = pattern.matcher(contentpv);
			 
			   if (matcher.find()) {
			    readNum = matcher.group(1);
			   } 
			   // 匹配comments
			   String contentz=Spider.sendGet(zUrl);
			   pattern = Pattern.compile("content\":\"(.*?)\",\"score");
			 JSONObject jsonObject = JSON.parseObject(contentz);
			   matcher = pattern.matcher(jsonObject.toString());
			   boolean isFind = matcher.find();
			   int i=5;
			   while (isFind && i-->0) {
			   	comments.add(matcher.group(1));
			    isFind = matcher.find();
			   }
		 }
	 }
	 boolean getRealUrl(String url) {
		  Pattern pattern = Pattern.compile("id=(.*)");
		  Matcher matcher = pattern.matcher(url);
		  if (matcher.find()) {
		   zhidaoUrl = "https://p.baidu.com/daily/view?id=" + matcher.group(1);
		   pvUrl="https://p.baidu.com/daily/ajax/getpv?type=daily&key="+matcher.group(1);
		   zUrl="https://p.baidu.com/daily/ajax/comment?method=get_reply&app=article&thread_id="+matcher.group(1);
		  } else {
		   return false;
		  }
		  return true;
		 }
}
