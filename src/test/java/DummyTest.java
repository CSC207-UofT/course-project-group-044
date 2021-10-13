import org.junit.*;

import static org.junit.Assert.*;

import java.io.*;

public class DummyTest {
    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(timeout = 50)
    public void testTask1() {
        Dummy dummy = new Dummy();

        assertFalse(dummy.isMeaningOfLife(41));
        assertTrue(dummy.isMeaningOfLife(42));
        assertFalse(dummy.isMeaningOfLife(43));
    }
}
