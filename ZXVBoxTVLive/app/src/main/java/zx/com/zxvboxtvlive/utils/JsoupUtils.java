package zx.com.zxvboxtvlive.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zx.com.zxvboxtvlive.mode.TvSource;

/**
 * User: ShaudXiao
 * Date: 2017-03-29
 * Time: 15:41
 * Company: zx
 * Description:
 * FIXME
 */


public class JsoupUtils {

    public static String BASE_URL = "http://ivi.bupt.edu.cn/";
    public static String TAG_HREF = "移动端";


    /**
     * 首页获取电视台播放源
     * @param document
     * @return
     */
    public static List<TvSource> getTVSourceList(Document document) {
        List<TvSource> tvSources = new ArrayList<>();
        Elements elements = document.getElementsByClass("2u");
        Iterator iterator = elements.iterator();
        while(iterator.hasNext()) {
            Element element = (Element)iterator.next();
//            Logger.getLogger().i(element.toString());
            TvSource source = getTvSouceByElement(element);
            tvSources.add(source);
        }
        Logger.getLogger().i("*****************");
        return tvSources;
    }

    private static TvSource getTvSouceByElement(Element element) {
        TvSource source = new TvSource();
//        Element element = (Element) iterator.next();
        Element elementP = element.select("p").first();
        source.setTvName(elementP.text());
        Elements elementa = element.select("a");
        for(Element e :  elementa) {
            if(TAG_HREF.equals(e.text().trim())) {
                source.setTvDataSource(BASE_URL + e.attr("href"));
                break;
            }
        }
//        Logger.getLogger().i(source.toString());
        return source;
    }

}
