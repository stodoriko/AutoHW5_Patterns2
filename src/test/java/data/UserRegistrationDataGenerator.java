package data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserRegistrationDataGenerator {

    private final static String activeStatus = "active";
    private final static String blockedStatus = "blocked";

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(io.restassured.http.ContentType.JSON)
            .setContentType(io.restassured.http.ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static Gson gson = new Gson();
    private static Faker faker = new Faker(new Locale("en"));


    private UserRegistrationDataGenerator() {
    }

    private static void registrationUsers(UserRegistrationData userData) {
        given()
                .spec(requestSpec)
                .body(gson.toJson(userData))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static UserRegistrationData generateValidActive() {
        UserRegistrationData userData = new UserRegistrationData(faker.name().username(),
                faker.internet().password(true), activeStatus);
        registrationUsers(userData);
        return userData;
    }
}
