package com.mt.es.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;

import static org.apache.commons.lang3.reflect.FieldUtils.writeDeclaredStaticField;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    @Mock
    private static SpringApplication springApplication;

    @Test
    public void shouldCreateAndRunApp() throws Exception {
        writeDeclaredStaticField(Application.class, "springApplication", springApplication, true);
        Application.main(new String[]{});
        verify(springApplication, atLeastOnce()).run();
    }

    @Test
    public void shouldCreate(){
        Application app = new Application();
        assertNotNull(app);
    }

}

