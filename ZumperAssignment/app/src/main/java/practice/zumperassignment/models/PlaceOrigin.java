package practice.zumperassignment.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adao1 on 5/30/2017.
 */

public class PlaceOrigin {

    @SerializedName("results")
    private Restaurant[] restaurants;
    private String next_page_token;

    public Restaurant[] getRestaurants() {
        return restaurants;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public class Restaurant{
        private Geometry geometry;
        private String icon;
        private String id;
        private String name;
        private String place_id;
        private float rating;
        private String scope;
        private String vicinity;
        private String reference;

        public Geometry getGeometry() {
            return geometry;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public float getRating() {
            return rating;
        }

        public String getPlace_id() {
            return place_id;
        }

        public String getScope() {
            return scope;
        }

        public String getVicinity() {
            return vicinity;
        }

        public String getReference() {
            return reference;
        }

        public class Geometry{
            private GeoLocation location;

            public GeoLocation getLocation() {
                return location;
            }

            public class GeoLocation{
                private float lat;
                private float lng;

                public float getLat() {
                    return lat;
                }

                public float getLng() {
                    return lng;
                }
            }
        }
    }
}
