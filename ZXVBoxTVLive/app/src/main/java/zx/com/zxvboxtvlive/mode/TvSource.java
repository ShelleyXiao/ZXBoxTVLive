package zx.com.zxvboxtvlive.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: ShaudXiao
 * Date: 2017-03-29
 * Time: 15:41
 * Company: zx
 * Description:
 * FIXME
 */


public class TvSource implements Parcelable{

    private int id;
    private String tvName;
    private String tvDataSource;
    private String pinyingLog;

    public TvSource() {}

    public TvSource(String name , String source) {
        tvName = name;
        tvDataSource = source;
    }

    public TvSource(Parcel src) {
        id = src.readInt();
        tvName = src.readString();
        tvDataSource = src.readString();
        pinyingLog = src.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPinyingLog() {
        return pinyingLog;
    }

    public void setPinyingLog(String pinyingLog) {
        this.pinyingLog = pinyingLog;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tvName);
        dest.writeString(tvDataSource);
        dest.writeString(pinyingLog);
    }

    public static final Parcelable.Creator<TvSource> CREATOR = new Parcelable.Creator<TvSource>() {

        public TvSource createFromParcel(Parcel src) {
            return new TvSource(src);
        }

        public TvSource[] newArray(int size) {
            return new TvSource[size];
        }
    };

    @Override
    public String toString() {
        return "TvSource{" +
                "tvName='" + tvName + '\'' +
                ", tvDataSource='" + tvDataSource + '\'' +
                ", pinyingLog='" + pinyingLog + '\'' +
                '}';
    }


}
