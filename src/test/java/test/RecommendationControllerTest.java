//package test;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import controller.RecommendationController;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
//@WebMvcTest(RecommendationController.class)
//public class RecommendationControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void testExample() throws Exception {
//        this.mvc.perform(get("/recommendatoin?bedroomCount=1&bathroomCount=1&squareFeet=1").accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//}