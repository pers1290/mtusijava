package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SpyTest {

    @Test
    void shouldVerifyAddOnSpyList() {
        List<String> list = new ArrayList<>();
        List<String> spyList = spy(list);

        spyList.add("Spring");

        verify(spyList).add("Spring");
        assertEquals(1, spyList.size());
        assertEquals("Spring", spyList.get(0));
    }
}
