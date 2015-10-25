package com.marcinmajkowski.robotics.kawasaki.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.Instruction;
import com.marcinmajkowski.robotics.kawasaki.rest.domain.InstructionParameter;
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
import org.springframework.http.MediaType;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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

    @Test
    public void driveInstruction() throws Exception {
        InstructionParameter parameter = new InstructionParameter();
        parameter.setJointNumber(1);
        parameter.setDisplacement(45.0);
        parameter.setSpeed(100.0);

        Instruction instruction = new Instruction();
        instruction.setKeyword("drive");
        instruction.setParameter(parameter);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String instructionJson = objectMapper.writeValueAsString(instruction);

        this.mockMvc.perform(
                post("/robots/1/instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(instructionJson)
        ).andExpect(
                status()
                        .isOk()
        ).andDo(document("drive-instruction",
                        responseFields(
                                fieldWithPath("keyword").description("keyword descritpion"),
                                fieldWithPath("parameter.jointNumber").description("jointNumber"),
                                fieldWithPath("parameter.displacement").description("displacement"),
                                fieldWithPath("parameter.speed").description("speed")
                        )
                )
        );
    }
}
