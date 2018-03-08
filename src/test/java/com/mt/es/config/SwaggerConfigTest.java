package com.mt.es.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SwaggerConfigTest {


    @Test
    public void testSwaggerConfiguration(){
        // given
        SwaggerConfig config = new SwaggerConfig();

        // when
        Docket api = config.SwaggerApi();

        // then
        assertNotNull(api);
        assertThat(api.getDocumentationType(),is(DocumentationType.SWAGGER_2));

    }
}
