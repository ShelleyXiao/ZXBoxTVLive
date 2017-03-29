package zx.com.zxvboxtvlive.mode;

/**
 * User: ShaudXiao
 * Date: 2017-03-29
 * Time: 15:41
 * Company: zx
 * Description:
 * FIXME
 */


public class TvSource {

    private String tvName;
    private String tvDataSource;

    public TvSource() {}

    public TvSource(String name , String source) {
        tvName = name;
        tvDataSource = source;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvDataSource() {
        return tvDataSource;
    }

    public void setTvDataSource(String tvDataSource) {
        this.tvDataSource = tvDataSource;
    }

    @Override
    public String toString() {
        return "TvSource{" +
                "tvName='" + tvName + '\'' +
                ", tvDataSource='" + tvDataSource + '\'' +
                '}';
    }
}
