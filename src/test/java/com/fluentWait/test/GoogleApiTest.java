package com.fluentWait.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.fluentWait.bin.Google;
import com.fluentWait.bin.Item;
import com.fluentWait.bin.VolumeInfo;
import com.fluentWait.common.EndPoint;
import com.fluentWait.framework.RestAssuredConfiguration;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fluent Wait on 3/11/2017.
 */
public class GoogleApiTest {
	
    @Test(groups = {"demo", "google"})
    public void googleApiTest() {
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("q", "turing");
        Response response =
                new RestAssuredConfiguration().getResponse(requestSpecification, EndPoint.GOOGLE_API, HttpStatus.SC_OK);
        Google google = response.as(Google.class, ObjectMapperType.GSON);
        SoftAssert softAssert = new SoftAssert();
        AssertJUnit.assertTrue(response.getTimeIn(TimeUnit.SECONDS) <= 10);
        List<Item> items = google.getItems();
        for (Item item : items) {
            System.out.println(item.toString());
            AssertJUnit.assertTrue(!item.getId().equalsIgnoreCase(""));
            VolumeInfo volumeInfo = item.getVolumeInfo();
            AssertJUnit.assertTrue(!volumeInfo.getTitle().equalsIgnoreCase(""));
        }
        softAssert.assertAll();
    }

}
