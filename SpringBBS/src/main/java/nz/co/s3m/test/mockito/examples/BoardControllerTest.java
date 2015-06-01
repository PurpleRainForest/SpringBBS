package nz.co.s3m.test.mockito.examples;

import nz.co.s3m.boards.service.BoardService;
import nz.co.s3m.controllers.BoardController;


import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;

public class BoardControllerTest {
	@Mock
	private BoardService svc;
	
	@InjectMocks
	private BoardController boardController;
	
	 //private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		

	}
	

}
