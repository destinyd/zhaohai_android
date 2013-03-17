
package DD.Android.Zhaohai.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static DD.Android.Zhaohai.core.Constants.Http.*;
import static DD.Android.Zhaohai.core.Constants.Other.CHINA_TIME_ZONE;
import static DD.Android.Zhaohai.core.Constants.Other.POST_DATE_FORMAT;
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
//    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

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
//
//    private static class JsonException extends IOException {
//
//        private static final long serialVersionUID = 3774706606129390273L;
//
//        /**
//         * Create exception from {@link com.google.gson.JsonParseException}
//         *
//         * @param cause
//         */
//        public JsonException(JsonParseException cause) {
//            super(cause.getMessage());
//            initCause(cause);
//        }
//    }


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
        if (apiKey == null)
            this.apiKey = DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity.getAuthToken();
        else
        {
            this.apiKey = apiKey;
            DD.Android.Zhaohai.authenticator.ZhaohaiAuthenticatorActivity.setAuthToken(apiKey);
        }
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

        if (isPostOrPut(request))
            request.contentType(Constants.Http.CONTENT_TYPE_JSON); // All PUT & POST requests to Parse.com api must be in JSON - https://www.parse.com/docs/rest#general-requests

        return addCredentialsTo(request);
    }

    private boolean isPostOrPut(HttpRequest request) {
        return request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_POST)
                || request.getConnection().getRequestMethod().equals(HttpRequest.METHOD_PUT);

    }

    private HttpRequest addCredentialsTo(HttpRequest request) {

        // Required params for
        request.header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY);
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
        return get(url + "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
    }

    public List<User> getFriend(
//            int page
    ) throws IOException {
        try {
            if (apiKey == null)
                return Collections.emptyList();
//            String url = String.format(FORMAT_URL_FRIEND,page) + getTokenParam();
            HttpRequest request = get(URL_FRIEND + "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
//            HttpRequest request = get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            String body = request.body();
            List<User> response = JSON.parseArray(body, User.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public List<User> getUsers(int page) throws IOException {

        try {
//            Type collectionType = new TypeToken<List<User>>(){}.getType();

            if (apiKey == null)
                return Collections.emptyList();
            String url = String.format(FORMAT_URL_USERS,page) + getTokenParam();
            HttpRequest request = //get(URL_USERS + "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                    get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            String body = request.body();
            List<User> response = JSON.parseArray(body, User.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public List<Activity> getActivities(int page) throws IOException {
        try {
            if (apiKey == null)
                return Collections.emptyList();
//                return null;
            String url = String.format(FORMAT_URL_ACTIVITIES,page) + getTokenParam();
//            HttpRequest request = get(URL_ACTIVITIES + "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
            HttpRequest request = get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            String body = request.body();
            List<Activity> response = JSON.parseArray(body, Activity.class);
//            if(request.ok())
//            {
                return response;
//            }
//            else
//            {
//                return Collections.emptyList();
////                return null;
//            }
//            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public List<ActivityRequest> getActivityRequests(String activity_id) throws IOException {
        if (apiKey == null)
            return Collections.emptyList();
        String url = String.format(FORMAT_URL_ACTIVITY_REQUEST, activity_id) + "?" + getTokenParam();
        HttpRequest request = get(url)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
//            Type collectionType = new TypeToken<List<ActivityRequest>>() {
//            }.getType();
        try {
//            List<ActivityRequest> response = GSON.fromJson(request.bufferedReader(), collectionType);
            List<ActivityRequest> response = JSON.parseArray(request.body(), ActivityRequest.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public List<ZNotification> getNotifications() throws IOException {
        try {
            if (apiKey == null)
                return Collections.emptyList();
            String url = URL_NOTIFICATIONS + "?" + getTokenParam();
            HttpRequest request = get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
//            Type collectionType = new TypeToken<List<ZNotification>>() {
//            }.getType();
//            List<ZNotification> response = GSON.fromJson(request.bufferedReader(), collectionType);
            List<ZNotification> response = JSON.parseArray(request.body(), ZNotification.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public ZNotification getNotification(String notification_id) throws IOException {
        try {
            if (apiKey == null)
                return null;
            String url = String.format(FORMAT_URL_NOTIFICATION, notification_id) + "?" + getTokenParam();
            HttpRequest request = get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
//            ZNotification response = GSON.fromJson(request.bufferedReader(), ZNotification.class);
            String body = request.body();
            ZNotification response = JSON.parseObject(body, ZNotification.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public boolean replyNotification(String notification_id, String reply) throws IOException {
        if (apiKey == null || (!reply.equals("accept") && !reply.equals("deny")) )
            return false;
        String url = String.format(FORMAT_URL_REPLY_NOTIFICATION, notification_id,reply) + "?" + getTokenParam();
        HttpRequest request = post(url)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);

        try {
            if (request.ok()) {
//                String tmp = request.body();
//                return GSON.fromJson(request.bufferedReader(), Activity.class);
                return true;
//                GSON.fromJson(request.body(),GSON);
//                return tmp;
            } else {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public User getMe() throws IOException {
        if (apiKey == null)
            return null;
        String url = URL_ME + "?" + getTokenParam();
        HttpRequest request = get(url)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
        try {
//        User response = GSON.fromJson(request.bufferedReader(), User.class);
            User response = JSON.parseObject(request.body(), User.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public Activity getActivity(String activity_id) throws IOException {
        if (apiKey == null)
            return null;
        String url = String.format(FORMAT_URL_ACTIVITY, activity_id) + "?" + getTokenParam();
        HttpRequest request = get(url)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
//        Activity response = GSON.fromJson(request.bufferedReader(), Activity.class);
        try {
            String body = request.body();
            Activity response = JSON.parseObject(body, Activity.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }

    }


    public User getUser(String user_id)  throws IOException {
        if (apiKey == null)
            return null;
        String url = String.format(FORMAT_URL_USER, user_id) + "?" + getTokenParam();
        HttpRequest request = get(url)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
//        Activity response = GSON.fromJson(request.bufferedReader(), Activity.class);
        try {
            String body = request.body();
            User response = JSON.parseObject(body, User.class);
            return response;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }

    }


    public Activity createActivity(Activity activity) throws IOException {
        if (apiKey == null)
            return null;
        POST_DATE_FORMAT.setTimeZone(CHINA_TIME_ZONE);
        HttpRequest request = post(URL_ACTIVITIES + "?" + HEADER_PARSE_ACCESS_TOKEN + "=" + apiKey)
                .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                .part("activity[title]", activity.getTitle())
                .part("activity[desc]", activity.getDesc())
                .part("activity[started_at]", POST_DATE_FORMAT.format(activity.getStarted_at()))
                .part("activity[address]", activity.getAddress())
                .part("activity[lat]", activity.getLat())
                .part("activity[lng]", activity.getLng());

        try {
            if (request.ok()) {
//                String tmp = request.body();
//                return GSON.fromJson(request.bufferedReader(), Activity.class);
                return JSON.parseObject(request.body(), Activity.class);
//                GSON.fromJson(request.body(),GSON);
//                return tmp;
            } else {
                return null;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public boolean joinActivity(String activity_id, String message) throws IOException {
        try {
            if (apiKey == null)
                return false;
            String url = String.format(FORMAT_URL_ACTIVITY_REQUEST, activity_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                    .part("activity_request[text]", message);
            if (request.ok()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public boolean quitActivity(String activity_id) throws IOException {
        try {
            if (apiKey == null)
                return false;
            String url = String.format(FORMAT_URL_ACTIVITY_QUIT, activity_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            if (request.ok()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public boolean closeActivity(String activity_id) throws IOException {
        try {
            if (apiKey == null)
                return false;
            String url = String.format(FORMAT_URL_ACTIVITY_CLOSE, activity_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            if (request.ok()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public boolean inviteFriend(String activity_id, List<String> ids) throws IOException {
        try {
            if (apiKey == null)
                return false;
            String friend_ids = TextUtils.join(",", ids);
            String url = String.format(FORMAT_URL_ACTIVITY_INVITE_FRIEND, activity_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID)
                    .part("ids", friend_ids);
            if (request.ok()) {
                return true;
            } else {
                return false;
            }
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }


    public String follow(String user_id) throws IOException {
        try {
            if (apiKey == null)
                return null;
            String url = String.format(FORMAT_URL_USER_FOLLOW, user_id) + "?" + getTokenParam();
            HttpRequest request = post(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);

            if(request.ok())
                return request.body();
            return null;
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }




    private String getTokenParam() {
        return String.format(FORMAT_ACCESS_TOKEN, HEADER_PARSE_ACCESS_TOKEN, apiKey);
    }
}
