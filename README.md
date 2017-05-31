# RestaurantMap

The Restaurant Map application is uses the Google Places API to search for restaurants near SF (specifically near Zumper) and display them in both a map and a list.

Features:

Bottom Navigation Bar - Follows Material Design Guidelines. The user selects which way they want the data to be presented, in a list or map. 

Fragments - Since the API call is made in activity and then the data is passed to either activity, the activity makes much less api calls when switching between fragments then if the calls were made in the fragment itself (or two different activities). 

Unlimited Scrolling - Uses interfaces to let the acitivity know when list is at the end, to make another API call to add more to the list. 

Google Maps - The list of restaurants is plotted on a Google Map Fragment where you can see the name of the restaurants and then navigate to the detail screen. 


If given more time, I wish to and still plan to add:

User location - Since the task was to find restaurants in SF area, I initially hard-coded it to SF, but I would like to give the user the option to use their location (with permission) and make the API calls with that location. 

Load more restaurants on map - For the list, I was able to implement unlimited scrolling by using interfaces to make api calls for next pages, but I did get the chance to do that in map quite yet. I would like to load more restaurants on the map when the user moves their map focus. 

Refresh - I would like to implement the standard pull-down-to-refresh. 

Place Images - Unfortunately, I did get a chance to make another api call to get images for Google Photo (since the image or image_url itself is not part of the original result). Currently it loads the icon associated with each restaurant object. 
XML updates - I felt that playing around too much with the XML is less important for this assignment than the functionality, but I would love to add custom views and animations to greatly improve the user experience. 


Libraries used:
Retrofit - To make Api Calls and turning them into Java Objects
Picasso - To display images 
OkHttp Logging - To analyze and debug API calls
Google Maps


Build Instructions:
1. Clone repository 
2. Open in Android Studio
3. Run project 