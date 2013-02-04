
package DD.Android.Zhaohai.core;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Http.*;
import static DD.Android.Zhaohai.core.Constants.Other.*;
import static com.github.kevinsawicki.http.HttpRequest.get;
import static com.github.kevinsawicki.http.HttpRequest.post;

/**
 * Bootstrap API service
 */
public class ZhaohaiService {

    private UserAgentProvider userAgentProvider;

    /**
     * GSON instance to use for all request  with date format set up for proper parsing.
     */
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * You can also configure GSON with different naming policies for your API. Maybe your api is Rails
     * api and all json values are lower case with an underscore, like this "first_name" instead of "firstName".
     * You can configure GSON as such below.
     *
     * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
     *
     */


    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;



//    private static class UsersWrapper {
//
//        private List<User> results;
//    }

    private static class ActivitiesWrapper {

        private List<Activity> results;

    }

    private static class JsonException extends IOException {

        private static final long serialVersionUID = 3774706606129390273L;

        /**
         * Create exception from {@link com.google.gson.JsonParseException}
         *
         * @param cause
         */
        public JsonException(JsonParseException cause) {
            super(cause.getMessage());
            initCause(cause);
        }
    }


    private final String apiKey;
    private final String username;
    private final String password;

    /**
     * Create zhaohai service
     *
     * @param username
     * @param password
     */
    public ZhaohaiService(final String username, final String password) {
        this.username = username;
        this.password = password;
//        this.apiKey = null;
        this.apiKey = DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity.getAuthToken();
    }

    /**
     * Create zhaohai service
     *
     * @param userAgentProvider
     * @param apiKey
     */
    public ZhaohaiService(final String apiKey, final UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
        this.username = null;
        this.password = null;
        if(apiKey == null)
            this.apiKey = DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity.getAuthToken();
        else
            this.apiKey = apiKey;
    }

    /**
     * Execute request
     *
     * @param request
     * @return request
     * @throws java.io.IOException
     */
    protected HttpRequest execute(HttpRequest request) throws IOException {
        if (!configure(request).ok())
            throw new IOException("Unexpected response code: " + request.code());
        return request;
    }

    private HttpRequest configure(final HttpRequest request) {
        request.connectTimeout(TIMEOUT).readTimeout(TIMEOUT);
        request.userAgent(userAgentProvider.get());

        if(isPostOrPut(request))
            request.contentType(Constants.Http.CONTENT_TYPE_JSON); // All PUT & POST requests to Parse.com api must be in JSON - https://www.parse.com/docs/rest#general-requests

        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
               || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(HttpRequest request) {

        // Required params for
        request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY );
        request.header(HEADER_PARSE_APP_ID, PARSE_APP_ID);

        /**
         * NOTE: This may be where you want to add a header for the api token that was saved when you
         * logged in. In the zhaohai sample this is where we are saving the session id as the token.
         * If you actually had received a token you'd take the "apiKey" (aka: token) and add it to the
         * header or form values before you make your requests.
          */

        /**
         * Add the ABUser name and password to the request here if your service needs username or password for each
         * request. You can do this like this:
         * request.basic("myusername", "mypassword");
         */

        return request;
    }

//    private <V> List<V> fromJson(HttpRequest request, Class<V> target) throws IOException {
//        Type collectionType = new TypeToken<List<V>>(){}.getType();
//        Reader reader = request.bufferedReader();
//        try {
//            return GSON.fromJson(reader, collectionType);
//        } catch (JsonParseException e) {
//            throw new JsonException(e);
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException ignored) {
//                // Ignored
//            }
//        }
//    }
    private HttpRequest getUrl(String url) {
        return get(url+ "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
    }

    /**
     * Get all zhaohai Friend that exists on Parse.com
     *
     * @return non-null but possibly empty list of zhaohai
     * @throws IOException
     */
    public List<User> getFriend() throws IOException {
        try {
            if(apiKey == null)
                return Collections.emptyList();
            HttpRequest request = get(URL_FRIEND+ "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY )
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            Type collectionType = new TypeToken<List<User>>(){}.getType();
            List<User> response = GSON.fromJson(request.bufferedReader(), collectionType);
//            List<User> response = fromJson(request, User.class);
//            if (response != null && response.size() > 0)
//            {
                return response;
//            }
//            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all zhaohai Users that exist on Parse.com
     *
     * @return non-null but possibly empty list of zhaohai
     * @throws java.io.IOException
     */
    public List<User> getUsers() throws IOException {

        try {
//            Type collectionType = new TypeToken<List<User>>(){}.getType();

            if(apiKey == null)
                return Collections.emptyList();
            HttpRequest request = get(URL_USERS+ "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY )
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            Type collectionType = new TypeToken<List<User>>(){}.getType();
            List<User> response = GSON.fromJson(request.bufferedReader(), collectionType);
//            List<User> response = fromJson(request, User.class);
            response = (List<User>)response;
////            String strUsers = "[{\"name\":\"dd\",\"_id\":\"123456\",\"avatar\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\",\"thumb\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\"},\"icon\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\"}}},{\"name\":\"ee\",\"_id\":\"111111\",\"avatar\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\",\"thumb\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\"},\"icon\":{\"url\":\"http://shenmajia.tk/images/nopic.gif\"}}}]";
////            String strUsers = "[]";
////            UsersWrapper response = GSON.fromJson(strUsers, UsersWrapper.class);
//            if (response != null && response.results != null)
//                return response.results;
//            if (response != null && response.size() > 0)
//            {
                return response;
//            }
//            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    /**
     * Get all zhaohai Checkins that exists on Parse.com
     *
     * @return non-null but possibly empty list of zhaohai
     * @throws java.io.IOException
     */
    public List<Activity> getActivities() throws IOException {
        try {
            if(apiKey == null)
                return Collections.emptyList();
            HttpRequest request = get(URL_ACTIVITIES+ "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY )
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            Type collectionType = new TypeToken<List<Activity>>(){}.getType();
            List<Activity> response = GSON.fromJson(request.bufferedReader(), collectionType);
//            List<Activity> response = fromJson(request, Activity.class);
//            if (response != null && response.size() > 0)
//            {
                return response;
//            }
//            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public Activity createActivity(Activity activity) throws IOException {
        try {
            if(apiKey == null)
                return null;
            POST_DATE_FORMAT.setTimeZone(CHINA_TIME_ZONE);
            HttpRequest request = post(URL_ACTIVITIES+ "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY )
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                    .part("activity[title]",activity.getTitle())
                    .part("activity[desc]",activity.getDesc())
                    .part("activity[started_at]",POST_DATE_FORMAT.format(activity.getStarted_at()))
                    .part("activity[address]",activity.getAddress())
                    .part("activity[lat]",activity.getLat())
                    .part("activity[lng]",activity.getLng())
                    ;
            if(request.ok()){
//                String tmp = request.body();
                return GSON.fromJson(request.bufferedReader(),Activity.class);
//                GSON.fromJson(request.body(),GSON);
//                return tmp;
            }
            else
            {
                return null;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public boolean joinActivity(String activity_id,String message)  throws IOException {
        try {
            if(apiKey == null)
                return false;
            String url = String.format(FORMAT_URL_ACTIVITY_REQUEST,activity_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                    .part("activity_request[text]",message)
                    ;
            if(request.ok()){
                return true;
            }
            else
            {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    private String getTokenParam() {
        return String.format(FORMAT_ACCESS_TOKEN,HEADER_PARSE_ACCESS_TOKEN,apiKey);
    }
}
