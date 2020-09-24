package com.fluentWait.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.fluentWait.bin.EmployeeBin;
import com.fluentWait.common.EndPoint;
import com.fluentWait.framework.RestAssuredConfiguration;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Fluent Wait on 12/10/2016.
 */
public class Employee {

    @Test(groups = "demo")
    public void validateEmployee() {
        given().get(EndPoint.GET_EMPLOYEE).then().statusCode(200).log().all();
    }

    //http://localhost:8080/SprintRestServices/employee/getEmployeeQuery?employeeId=100
    @Test(groups = {"demo", "response"})
    public void validateQueryParameter() {
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("employeeId", 200).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_QUERY_PARAM).
                then().statusCode(200).log().all();
        //Getting Response
        Response response = given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_QUERY_PARAM);
        //Inline Validation
        //1.Hard Assertion
        response.then().body("firstName", equalTo("Fluent")).body("lastName", equalTo("Wait")).
                body("address", equalTo("New York"));
        //2.Soft Assertion
        response.then().body("firstName", equalTo("Fluent"), "lastName", equalTo("Wait"), "address", equalTo("New York"));
        //Path Validation
        //1.Hard Assertion
        AssertJUnit.assertEquals(response.path("firstName"), "Fluent");
        AssertJUnit.assertEquals(response.path("lastName"), "Wait");
        //2.Soft Assertion
        SoftAssert softAssert = new SoftAssert();
        AssertJUnit.assertEquals(response.path("firstName"), "Fluent");
        AssertJUnit.assertEquals(response.path("lastName"), "Wait");
        AssertJUnit.assertEquals(response.path("address"), "New York");
        softAssert.assertAll();
        //Java Object
        EmployeeBin employeeBin = response.as(EmployeeBin.class);
        //1.Hard Assertion
        AssertJUnit.assertEquals(employeeBin.getFirstName(),"Fluent");
        //2.Soft Assertion
        SoftAssert newSoftAssert = new SoftAssert();
        AssertJUnit.assertEquals(employeeBin.getFirstName(), "Fluent", "First Name Not Equal");
        AssertJUnit.assertEquals(employeeBin.getLastName(), "Wait");
        AssertJUnit.assertEquals(employeeBin.getAddress(), "New York", "City is not equal");
        newSoftAssert.assertAll();

    }

    //http://localhost:8080/SprintRestServices/employee/getEmployee/100
    @Test(groups = "demo")
    public void validatePathParameter() {
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.pathParam("employeeId", 100).log().all();
        given().spec(requestSpecification).get(EndPoint.GET_EMPLOYEE_PATH_PARAM).
                then().statusCode(200).log().all();
    }

    //http://localhost:8080/SprintRestServices/employee/addEmployee
    @Test(groups = "demo")
    public void validateFormParameters() {
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.accept(ContentType.JSON).formParams("employeeId", 100).log().all();
        given().spec(requestSpecification).post(EndPoint.POST_EMPLOYEE_PARAM).
                then().statusCode(200).log().all();
    }
}
