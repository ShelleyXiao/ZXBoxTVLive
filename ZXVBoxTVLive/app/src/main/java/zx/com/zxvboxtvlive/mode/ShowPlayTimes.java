package zx.com.zxvboxtvlive.mode;

/**
 * User: ShaudXiao
 * Date: 2017-03-30
 * Time: 14:27
 * Company: zx
 * Description:
 * FIXME
 */


public class ShowPlayTimes {

    private String chanelName;
    private String pic;
    private String showName;
    private String showStartTime;
    private String showEndTime;
    private String showContent;

    public ShowPlayTimes() {
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(String showStartTime) {
        this.showStartTime = showStartTime;
    }

    public String getShowEndTime() {
        return showEndTime;
    }

    public void setShowEndTime(String showEndTime) {
        this.showEndTime = showEndTime;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

    @Override
    public String toString() {
        return "ShowPlayTimes{" +
                "pic='" + pic + '\'' +
                ", showName='" + showName + '\'' +
                ", showStartTime='" + showStartTime + '\'' +
                ", showEndTime='" + showEndTime + '\'' +
                ", showContent='" + showContent + '\'' +
                '}';
    }
}
