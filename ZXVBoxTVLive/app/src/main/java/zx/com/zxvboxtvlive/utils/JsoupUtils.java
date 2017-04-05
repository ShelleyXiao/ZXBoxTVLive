package zx.com.zxvboxtvlive.utils;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zx.com.zxvboxtvlive.Constants;
import zx.com.zxvboxtvlive.mode.ShowPlayTimes;
import zx.com.zxvboxtvlive.mode.TvSource;

import static zx.com.zxvboxtvlive.Constants.TAG_HREF;

/**
 * User: ShaudXiao
 * Date: 2017-03-29
 * Time: 15:41
 * Company: zx
 * Description:
 * FIXME
 */


public class JsoupUtils {

    public static Document getHtmlDocument(String baseUrl, String url) throws IOException {
        return  Jsoup.connect(baseUrl + url)
                .timeout(5000)
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .get();
    }

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
        source.setPinyingLog(processTVPinyinLog(elementP.text()));
        Elements elementa = element.select("a");
        for(Element e :  elementa) {
            if(TAG_HREF.equals(e.text().trim())) {
                source.setTvDataSource(Constants.BASE_CHANNEL_URL + e.attr("href"));

                break;
            }
        }
        Logger.getLogger().i(source.toString());
        return source;
    }

    /**
     * 首页获取频道播放预估列表
//     * @param document
     * @return
     */
    public static void getShowPlayTimeList(/**Document document*/) {
        try {
            Document document = getHtmlDocument("http://www.tvsou.com/epg/yangshi", "");
            Elements elements = document.getElementsByClass("channel-box");
            Logger.getLogger().i(" Class channel-box size = " + elements.size());
            Element element = elements.first();
            Elements channelElements = element.getElementsByTag("li");
            Logger.getLogger().i("" + channelElements.size());
            Iterator iterator = channelElements.iterator();
            while (iterator.hasNext()) {
                Element element_li = (Element) iterator.next();
                Elements elementHref = element_li.getElementsByTag("a");
                Element elementTag_a = elementHref.first();
                Logger.getLogger().d("channel href = " + elementTag_a.attr("href")
                        + " ,title = " + elementTag_a.attr("title") );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<ShowPlayTimes> getShowPlayTimeList(final String channelName, final String url) {
        List<ShowPlayTimes> showPlayTimesList = new ArrayList<>();
        try {
            Document document = getHtmlDocument(Constants.BASE_EPG_URL, url);
            Elements elements = document.getElementsByClass("play-time-more");
            Element element_div = elements.first();
            Elements elements_li = element_div.getElementsByTag("li");
            Iterator liIterator = elements_li.iterator();
            while(liIterator.hasNext()) {
                ShowPlayTimes showPlayTime = new ShowPlayTimes();
                showPlayTime.setChanelName(channelName);
                Element element = (Element) liIterator.next();
                showPlayTime.setPic(element.attr("data-pic"));
                showPlayTime.setShowName(element.attr("data-name"));
                String showTime = element.attr("data-mainstars");
                String[] timeArr = showTime.split("-");
                showPlayTime.setShowStartTime(timeArr[0]);
                showPlayTime.setShowEndTime(timeArr[1]);
                showPlayTime.setShowContent(element.attr("data-content"));

                Logger.getLogger().i(showPlayTime.toString());

                showPlayTimesList.add(showPlayTime);
            }
        } catch (IOException e) {
            return null;
        }

        return showPlayTimesList;
    }

    private static String processTVPinyinLog(String tvName) {
        if(TextUtils.isEmpty(tvName)) {
            return null;
        }

        if(tvName.contains("CCTV")) {
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(tvName);

            return "cctv-" + m.replaceAll("").trim();
        }

        String pinyin = PinyinComparator.getPingYin(tvName).toUpperCase();
        if(pinyin.contains("WS")) {

            return pinyin.substring(0, 2);
        }

        return null;
    }

}
