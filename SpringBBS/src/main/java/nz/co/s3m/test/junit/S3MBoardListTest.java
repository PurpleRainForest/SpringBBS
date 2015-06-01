/* JUnit Examples from javacodegeeks */ 
package nz.co.s3m.test.junit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import nz.co.s3m.boards.service.BoardService;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/config/spring/applicationContext.xml"})
public class S3MBoardListTest {
	
	@Autowired
	private BoardService svc;
	
	@Ignore
	@Test
	public void testBoardList() {
		fail("Not yet implemented");
	}

	@Test
	public void testBoardListJSON() {
		svc.boardListJSON(1, 15, "title", "", "tb_clientside");
	}
	
	@Test
	public void totalBoardLists() {
		svc.totalBoardLists("title", "", "tb_clientside");
	}	

	/*
	assertEquals(obj1, obj2);
	assertSame(obj3, obj4);
	assertNotSame(obj2, obj4);
	assertNotNull(obj1);
	assertNull(obj5);
	assertTrue(var1 != var2);
	assertArrayEquals(arithmetic1, arithmetic2);
	*/
	
}
