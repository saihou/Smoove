package hackathon.money2020.smoove;

/**
 * Created by sai on 10/24/15.
 */
public class ListViewRow {
    public int icon;
    public String title;
    public String desc;
    public String id;

    public ListViewRow() {
        super();
    }

    public ListViewRow(int icon, String title, String desc, String id) {
        super();
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.id = id;
    }
}
