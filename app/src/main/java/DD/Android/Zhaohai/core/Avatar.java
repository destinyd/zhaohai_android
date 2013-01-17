package DD.Android.Zhaohai.core;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-1-10
 * Time: 下午12:53
 * To change this template use File | Settings | File Templates.
 */
public class Avatar implements Serializable {

    private static final long serialVersionUID = -7021300822736333813L;

    public class Thumb implements Serializable {
        private static final long serialVersionUID = -4455210258042968460L;
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Icon implements Serializable {
        private static final long serialVersionUID = -2085974644325139582L;
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
