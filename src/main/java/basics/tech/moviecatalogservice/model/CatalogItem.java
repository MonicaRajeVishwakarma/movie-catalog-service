package basics.tech.moviecatalogservice.model;

public class CatalogItem {

    private String name;
    private String deesc;
    private int rating;

    public CatalogItem(String name, String deesc, int rating) {
        this.name = name;
        this.deesc = deesc;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeesc() {
        return deesc;
    }

    public void setDeesc(String deesc) {
        this.deesc = deesc;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
