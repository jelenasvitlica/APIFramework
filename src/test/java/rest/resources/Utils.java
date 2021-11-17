package rest.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {

        if(req==null) {

            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            RequestSpecBuilder builder = new RequestSpecBuilder();
            builder.setBaseUri(getGlobalValue("baseUrl"));
            builder.addQueryParam("key", "qaclick123");
            builder.setContentType(ContentType.JSON);
            builder.addFilter(RequestLoggingFilter.logRequestTo(log));
            builder.addFilter(ResponseLoggingFilter.logResponseTo(log));
            req = builder.build();
            return req;
        }
        return req;
    }
    public static String getGlobalValue(String key) throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\Jelena\\API Framework\\src\\test\\java\\rest\\resources\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }

    public static String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }


}
