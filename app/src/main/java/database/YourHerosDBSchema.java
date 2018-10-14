package database;

/**
 * Created by xxsti on 10/6/2018.
 */

public class YourHerosDBSchema {
    public static final class YourHerosTable{
        public static final String NAME = "yourHeros";

        public static final class Cols {
            public static final String uuid = "uuid";
            public static final String name = "name";
            public static final String realname = "realname";
            public static final String team = "team";
            public static final String firstappearance = "firstappearance";
            public static final String createdby = "createdby";
            public static final String publisher = "publisher";
            public static final String imageurl = "imageurl";
            public static final String bio = "bio";
        }
    }
}
