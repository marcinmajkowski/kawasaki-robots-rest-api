package com.marcinmajkowski.robotics.kawasaki.rest;

import com.marcinmajkowski.robotics.kawasaki.rest.domain.RealVariable;
import com.marcinmajkowski.robotics.kawasaki.rest.service.LocationService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RealService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.RobotService;
import com.marcinmajkowski.robotics.kawasaki.rest.service.StringService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApiDocumentationContext.class)
@WebAppConfiguration
public class ApiDocumentation {
    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LocationService locationServiceMock;

    @Autowired
    private RealService realServiceMock;

    @Autowired
    private RobotService robotServiceMock;

    @Autowired
    private StringService stringServiceMock;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        reset(locationServiceMock);
        reset(realServiceMock);
        reset(robotServiceMock);
        reset(stringServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void doublesSetExample() throws Exception {
        Set<RealVariable> expectedResults = new HashSet<>();
        expectedResults.add(new RealVariable("half", 0.5));
        expectedResults.add(new RealVariable("one", 1.0));
        expectedResults.add(new RealVariable("two", 2.0));

        when(realServiceMock.getAll()).thenReturn(expectedResults);

        this.mockMvc.perform(get("/reals"))
                .andExpect(status().isOk())
                .andDo(document("reals"));
    }
}
