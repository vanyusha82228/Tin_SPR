//package edu.java.scrapper;
//
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import org.junit.jupiter.api.Test;
//import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
//import static com.github.tomakehurst.wiremock.client.WireMock.get;
//import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
//import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
//
//@WireMockTest(httpPort = 8080)
//public class StackOverflowTest {
//    @Test
//    public void testFetchRepositoryInfoStackOverflow(){
//        stubFor(get(urlPathMatching("/questions/1"))
//            .willReturn(aResponse())
//            .)
//    }
//}
