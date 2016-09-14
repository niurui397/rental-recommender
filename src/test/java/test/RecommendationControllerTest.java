package test;

import java.nio.charset.Charset;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import controller.RecommendationController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RecommendationController.class)
public class RecommendationControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private MockMvc mockMvc;
	
	
}
