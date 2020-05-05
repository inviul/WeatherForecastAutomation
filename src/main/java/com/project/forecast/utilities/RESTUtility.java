package com.project.forecast.utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;

public class RESTUtility {


    public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
    }

    public static void resetBaseURI(String baseUrl){
        RestAssured.baseURI = null;
    }

    public static RequestSpecification requestSpecification(){
        return RestAssured.given();
    }

    public static Response getDefaultResponse(RequestSpecification httpReq){
        return httpReq.get();
    }

    public static Response restGetCall(RequestSpecification httpReq, String trailPath){
        return httpReq.request(Method.GET, trailPath);
    }


    public static Response restPostCallJson(JSONObject jsonObject, RequestSpecification httpReq, String trailPath){
        httpReq.header("Content-Type", "application/json");
        httpReq.body(jsonObject.toJSONString());
        return httpReq.request(Method.POST, trailPath);
    }

    public static Response restPutCallJson(JSONObject jsonObject, RequestSpecification httpReq, String trailPath){
        httpReq.header("Content-Type", "application/json");
        httpReq.body(jsonObject.toJSONString());
        return httpReq.request(Method.PUT, trailPath);
    }

    public static Response restPatchCallJson(JSONObject jsonObject, RequestSpecification httpReq, String trailPath){
        httpReq.header("Content-Type", "application/json");
        httpReq.body(jsonObject.toJSONString());
        return httpReq.request(Method.PATCH, trailPath);
    }

    public static Response restDeleteCall(RequestSpecification httpReq, String trailPath){
        return httpReq.request(Method.DELETE, trailPath);
    }

    public static int getStatusCode(Response response){
        return response.getStatusCode();
    }

    public static String getStringBody(Response response){
        return response.getBody().asString();
    }

    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }

    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }
}