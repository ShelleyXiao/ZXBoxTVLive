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

    private int index;
    private String chanelName;
    private String pic;
    private String showName;
    private String showStartTime;
    private String showEndTime;
    private String showContent;
    private boolean isPlaying;

    public ShowPlayTimes() {
    }

    public ShowPlayTimes(ShowPlayTimes time) {
        index = time.getIndex();
        chanelName = time.getChanelName();
        pic = time.getPic();
        showName = time.getShowName();
        showStartTime = time.getShowStartTime();
        showEndTime = time.getShowEndTime();
        showContent = time.getShowContent();
        isPlaying = time.isPlaying();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
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
                "index=" + index +
                ", chanelName='" + chanelName + '\'' +
                ", pic='" + pic + '\'' +
                ", showName='" + showName + '\'' +
                ", showStartTime='" + showStartTime + '\'' +
                ", showEndTime='" + showEndTime + '\'' +
                ", showContent='" + showContent + '\'' +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
