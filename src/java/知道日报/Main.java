
import java.io.*;

import java.util.ArrayList;

public class Main {
 public static void main(String[] args) throws Exception{
  // 定义即将访问的链接https://p.baidu.com/daily/1604   \u8bf4\u7684\u6ca1\u9519\u3002
  File f = new File("a.txt");
  FileOutputStream fop = new FileOutputStream(f);
  OutputStreamWriter writer = new OutputStreamWriter(fop, "UTF-8");
  for(int temp=1;temp<=1605;temp++){
   String url = "https://p.baidu.com/daily/?period="+temp;
   System.out.println(url);

   String content = Spider.sendGet(url);
   ArrayList<知道> myZhidao = Spider.GetRecommendations(content);
   for(int k=0;k<myZhidao.size();k++)
    writer.append(myZhidao.get(k).toString());
  }
  writer.close();
  fop.close();

 }
}