

package DD.Android.Zhaohai.core;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        private Auth() {}

//        /**
//         * Account type id
//         */
//        public static final String BOOTSTRAP_ACCOUNT_TYPE = "DD.Android.Zhaohai";
//
//        /**
//         * Account name
//         */
//        public static final String BOOTSTRAP_ACCOUNT_NAME = "Zhaohai";
//
//        /**
//         * Provider id
//         */
//        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "DD.Android.Zhaohai.sync";
//
//        /**
//         * Auth token type
//         */
//        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;

        public static final String ZHAOHAI_ACCOUNT_TYPE = "DD.Android.Zhaohai";

        /**
         * Account name
         */
        public static final String ZHAOHAI_ACCOUNT_NAME = "Zhaohai";

        /**
         * Provider id
         */
        public static final String ZHAOHAI_PROVIDER_AUTHORITY = "DD.Android.Zhaohai.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = ZHAOHAI_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for zhaohai!
     */
    public static class Http {
        private Http() {}


        public static final String URL_BASE = "http://192.168.1.4:3002";
//        public static final String URL_BASE = "http://zhaohai.com";

        public static final String API_BASE = URL_BASE + "/api/v1";
        /**
         * Authentication URL
         */
        public static final String URL_AUTH = URL_BASE + "/oauth/token";

        /**
         * List Users URL
         */
        public static final String URL_USERS = API_BASE + "/users.json";

        /**
         * List Friend URL
         */
        public static final String URL_FRIEND = API_BASE + "/users/friend.json";

        /**
         * List Checkin's URL
         */
        public static final String URL_ACTIVITIES = API_BASE + "/activities.json";

        public static final String PARSE_APP_ID = "78e00ce282ab3e3357af2e40c38050ef4e3fa85a8584790950efcfdb717786e3";
        public static final String PARSE_REST_API_KEY = "976d0d4ab74708ae5c3b8ef0f5723a5fc72df5692c5f452c20d87c5673c1b6e4";
        public static final String HEADER_PARSE_REST_API_KEY = "client_secret";
            public static final String HEADER_PARSE_APP_ID = "client_id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";

        public static final String HEADER_PARSE_GRANT_TYPE = "grant_type";
        public static final String GRANT_TYPE = "password";
        public static final String HEADER_PARSE_ACCESS_TOKEN = "access_token";



        /**
         * Base URL for all requests
         */
//        public static final String URL_BASE = "https://api.parse.com";

        /**
         * Authentication URL
         */
//        public static final String URL_AUTH = URL_BASE + "/1/login";

        /**
         * List Users URL
         */
//        public static final String URL_USERS = URL_BASE + "/1/users";
//
//        /**
//         * List News URL
//         */
//        public static final String URL_NEWS = URL_BASE + "/1/classes/News";
//
//        /**
//         * List Checkin's URL
//         */
//        public static final String URL_CHECKINS = URL_BASE + "/1/classes/Locations";
    }


    public static class Extra {
        private Extra() {}

//        public static final String NEWS_ITEM = "news_item";

//        public static final String ABUSER = "ABUser";

        public static final String USER = "User";

        public static final String FRIEND = "friend";

        public static final String ACTIVITY = "activity";

    }

    public static class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "DD.Android.Zhaohai.";

    }

    public static class Other{
        private Other(){}

        public static final SimpleDateFormat POST_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public static final TimeZone CHINA_TIME_ZONE = TimeZone.getTimeZone("Asia/Shanghai");
        public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    }

}


