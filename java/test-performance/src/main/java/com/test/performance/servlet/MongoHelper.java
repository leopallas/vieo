package com.test.performance.servlet;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.bson.types.ObjectId;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;

public class MongoHelper {
    static {
        CONNECTIONS_PER_HOST = Integer.parseInt(System.getProperty("MONGO.POOLSIZE", "50"));
        THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = 2000;
        AUTO_CONNECT_RETRY = true;
        MAX_WAIT_TIME = 1000 * 60 * 2;

        DB_NAME = "ehg";
        DB_HOST = "localhost";
        DB_HOST_PORT = 27017;
    }

    private static final MongoHelper server = new MongoHelper();

    private static String DB_NAME;

    private static String DB_HOST;

    private static int    DB_HOST_PORT;

    private static String COLLECTION_NAME;

    private static int     CONNECTIONS_PER_HOST;

    private static int     THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER;

    private static boolean AUTO_CONNECT_RETRY;

    private static int     MAX_WAIT_TIME;

    private static final String ID              = "_id";

    private static final String PROPERTIES_NAME = "servlet-config.properties";

    private Mongo mongo;

    public static void main(String[] args) {
        try {
            String key = "186_10840609";
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                MongoHelper.getInstance().queryTest(key);
                MongoHelper.getInstance().updateTest(key);
                MongoHelper.getInstance().removeTest(key);
                System.out.println(Calendar.getInstance().getTime());
            }
            System.out.println("update used " + (System.currentTimeMillis() - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoHelper getInstance() {
        return server;
    }

    private MongoHelper() {
        if (mongo == null) {
            try {
                ClassLoader cl = null;
                try {
                    cl = Thread.currentThread().getContextClassLoader();
                } catch (Throwable ex) {
                    // Cannot access thread context ClassLoader - falling back to system class loader...
                }
                if (cl == null) {
                    // No thread context class loader -> use class loader of this class.
                    cl = ClassUtils.class.getClassLoader();
                }

                InputStream in = cl.getResourceAsStream(PROPERTIES_NAME);
                Properties property = new Properties();
                property.load(in);
                in.close();

                String dbName = (String) property.get("mongodb.dbname");
                if (dbName != null && !"".equals(dbName)) {
                    DB_NAME = dbName;
                }
                String dbHost = (String) property.get("mongodb.host");
                if (dbHost != null && !"".equals(dbHost)) {
                    DB_HOST = dbHost;
                }
                String dbPort = (String) property.get("mongodb.port");
                if (dbPort != null && !"".equals(dbPort)) {
                    DB_HOST_PORT = Integer.parseInt(dbPort);
                }

                String collectionName = (String) property.get("mongodb.collection");
                if (collectionName != null && !"".equals(collectionName)) {
                    COLLECTION_NAME = collectionName;
                }

                String connectionsPerHost = (String) property.get("mongodb.options.connectionsPerHost");
                if (connectionsPerHost != null && !"".equals(connectionsPerHost)) {
                    CONNECTIONS_PER_HOST = Integer.parseInt(System.getProperty("MONGO.POOLSIZE", connectionsPerHost));
                }

                String threadsAllowedToBlockForConnectionMultiplier = (String) property.get("mongodb.options.threadsAllowedToBlockForConnectionMultiplier");
                if (threadsAllowedToBlockForConnectionMultiplier != null && !"".equals(threadsAllowedToBlockForConnectionMultiplier)) {
                    THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER = Integer.parseInt(threadsAllowedToBlockForConnectionMultiplier);
                }

                String autoConnectRetry = (String) property.get("mongodb.options.autoConnectRetry");
                if (autoConnectRetry != null && !"".equals(autoConnectRetry)) {
                    AUTO_CONNECT_RETRY = Boolean.parseBoolean(autoConnectRetry);
                }

                String maxWaitTime = (String) property.get("mongodb.options.maxWaitTime");
                if (maxWaitTime != null && !"".equals(maxWaitTime)) {
                    MAX_WAIT_TIME = Integer.parseInt(maxWaitTime);
                }

                MongoOptions options = new MongoOptions();
                options.autoConnectRetry = AUTO_CONNECT_RETRY;
                options.connectionsPerHost = CONNECTIONS_PER_HOST;
                options.threadsAllowedToBlockForConnectionMultiplier = THREADS_ALLOWED_TO_BLOCK_FOR_CONNECTION_MULTIPLIER;
                options.maxWaitTime = MAX_WAIT_TIME;
//				options.socketKeepAlive = "";
//				options.maxAutoConnectRetryTime = 
                mongo = new Mongo(new ServerAddress(DB_HOST, DB_HOST_PORT), options);
                System.out.println(String.format("init MongoHelper() : connect to %s:%s, DB Name is %s, set %s", DB_HOST, DB_HOST_PORT, DB_NAME, options.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public DB getDB() throws Exception {
        DB db = mongo.getDB(DB_NAME);
        if (null == db) {
            throw new Exception("Can not get DB, the DB Name is" + DB_NAME);
        }
        return db;
    }

    public String insertTest(String key) throws MongoException, Exception {
        if (key == null || "".equals(key)) {
            throw new MongoException("parameter error !");
        }
        DB db = getDB();
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        String desc = String.format("insert key '%s' into %s collection.", key, COLLECTION_NAME);
//		DBObject document = new BasicDBObject();
        DBObject document = new BasicDBObject(ID, new ObjectId(key));
        document.put("objID", key);
        document.put("active", "true");
        document.put("desc", desc);
        document.put("createTime", Calendar.getInstance().getTime());
        document.put("updateTime", Calendar.getInstance().getTime());
        coll.insert(document, WriteConcern.SAFE);
        return desc;
    }

    public String updateTest(String key) throws MongoException, Exception {
        if (key == null || "".equals(key)) {
            throw new MongoException("parameter error !");
        }

        DB db = getDB();
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        String desc = String.format("update key '%s' in %s collection.", key, COLLECTION_NAME);
//		DBObject document = new BasicDBObject();
        DBObject document = new BasicDBObject(ID, new ObjectId(key));
        document.put("objID", key);
        document.put("active", "false");
        document.put("desc", desc);
        document.put("createTime", Calendar.getInstance().getTime());
        document.put("updateTime", Calendar.getInstance().getTime());
        coll.update(new BasicDBObject(ID, new ObjectId(key)), document, false, false, WriteConcern.SAFE);
        return desc;
    }

    public String removeTest(String key) throws MongoException, Exception {
        if (key == null || "".equals(key)) {
            throw new MongoException("parameter error !");
        }

        DB db = getDB();
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        DBObject searchById = new BasicDBObject(ID, new ObjectId(key));
//		searchQuery.put(PRIMAYR_KEY, new ObjectId(key));
        coll.remove(searchById, WriteConcern.SAFE);
        return String.format("remove key '%s' in %s collection.", key, COLLECTION_NAME);
    }

    public String queryTest(String key) throws MongoException, Exception {
        if (key == null || "".equals(key)) {
            throw new MongoException("parameter error !");
        }

        DB db = getDB();
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        DBObject searchById = new BasicDBObject(ID, new ObjectId(key));
        DBObject found = coll.findOne(searchById);
        if (found == null) {
            throw new MongoException("DB collection can not find specified record !");
        }
        return String.format("query key '%s' in %s collection.", key, COLLECTION_NAME);
    }

    public String queryUpdateTest(String key) throws MongoException, Exception {
        queryTest(key);
        updateTest(key);
        return String.format("query and update key '%s' in %s collection.", key, COLLECTION_NAME);
    }
}