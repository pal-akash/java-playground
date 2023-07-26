package com.akash.empmanagesys.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "COUCH_URL";
    }

    @Override
    public String getUserName() {
        return "COUCH_USER_NAME";
    }

    @Override
    public String getPassword() {
        return "COUCH_PASSWORD";
    }

    @Override
    public String getBucketName() {
        return "COUCH_BUCKET";
    }
}
