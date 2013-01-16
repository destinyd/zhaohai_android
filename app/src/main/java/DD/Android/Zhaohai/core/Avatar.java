package DD.Android.Zhaohai.core;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-1-10
 * Time: 下午12:53
 * To change this template use File | Settings | File Templates.
 */
public class Avatar {
    public class Thumb {
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Icon {
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public String url;
    public Thumb thumb;
    public Icon icon;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
