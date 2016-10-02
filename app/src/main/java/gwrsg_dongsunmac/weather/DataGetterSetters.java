package gwrsg_dongsunmac.weather;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */

public class DataGetterSetters {
    public MainItem getMainItem() {
        return mainItem;
    }

    public void setMainItem(MainItem mainItem) {
        this.mainItem = mainItem;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    private String mTitle;
    private String source;
    private String description;
    private MainItem mainItem;
}

