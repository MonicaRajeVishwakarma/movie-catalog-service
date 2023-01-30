package basics.tech.moviecatalogservice.model;

import java.util.List;

public class UserRating {

    private List<Rating> userRating;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public UserRating(List<Rating> userRating,String userId) {

        this.userRating = userRating;
        this.userId = userId;
    }

    public UserRating() {
    }

    public List<Rating> getUserRating() {
        return userRating;
    }

    public void setUserRating(List<Rating> userRating) {
        this.userRating = userRating;
    }
}
