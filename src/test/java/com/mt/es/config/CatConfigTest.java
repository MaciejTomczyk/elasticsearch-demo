package com.mt.es.config;

import com.mt.es.model.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CatConfigTest {

    @Test
    public void testCatConfiguration() {
        // given
        CatConfig config = new CatConfig();

        // when
        List<Cat> cats = config.getCats();

        // then
        assertNotNull(cats);
        assertEquals(10,cats.size());
    }
}
