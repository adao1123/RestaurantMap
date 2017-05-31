package practice.zumperassignment.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao1 on 5/31/2017.
 */

public class PlaceDetailOrigin {

    @SerializedName("result")
    private RestaurantDetail restaurantDetail;

    public RestaurantDetail getRestaurantDetail() {
        return restaurantDetail;
    }

    public class RestaurantDetail {
        private String formatted_address;
        private String formatted_phone_number;
        private String icon;
        private String id;
        private String international_phone_number;
        private String name;
        private String place_id;
        private float rating;
        private Review[] reviews;

        public String getFormatted_address() {
            return formatted_address;
        }

        public String getFormatted_phone_number() {
            return formatted_phone_number;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getInternational_phone_number() {
            return international_phone_number;
        }

        public String getName() {
            return name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public float getRating() {
            return rating;
        }

        public Review[] getReviews() {
            return reviews;
        }

        public class Review{
            private String author_name;
            private String profile_photo_url;
            private int rating;
            private String text;

            public String getAuthor_name() {
                return author_name;
            }

            public String getProfile_photo_url() {
                return profile_photo_url;
            }

            public int getRating() {
                return rating;
            }

            public String getText() {
                return text;
            }
        }
    }
}
