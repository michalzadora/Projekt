package Project;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlagTest {
    @Test
    public void isWriteTest() throws Exception {
    assertFalse(Flag.isWrite());
    Flag.setWrite(true);
    assertTrue(Flag.isWrite());
    }

}