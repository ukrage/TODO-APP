package com.urkg.todoapi.api;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.net.URL;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    @ParameterizedTest
    @MethodSource("findTestProvider")
    public void findTest(String url, String expectedBody, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        URL givenURL = this.getClass().getResource("/data/" + dbPath + "/given/");
        databaseTester.setDataSet(new CsvURLDataSet(givenURL));
        databaseTester.onSetup();

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(result -> expectedBody.equals(
                        result.getResponse().getContentAsString()
                ));

        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualTestTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource("/data/" + dbPath + "/expected/");
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedTestTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedTestTable, actualTestTable);
    }

    private static Stream<Arguments> findTestProvider() {
        return Stream.of(
                Arguments.arguments(
                        "/tasks",
                        """
                                        {
                                            "id": 1,
                                            "title": "タスク",
                                            "content": "○○を調べる",
                                            "finishedFlg": false
                                        },
                                        {
                                            "id": 2,
                                            "title": "タスク2",
                                            "content": "レポートを作成する",
                                            "finishedFlg": false
                                        }
                                """,
                        "default"
                ),
                Arguments.arguments(
                        "/tasks/1",
                        """
                                        {
                                            "id": 1,
                                            "title": "タスク",
                                            "content": "○○を調べる",
                                            "finishedFlg": false
                                        }
                                """,
                        "default"
                )
        );
    }
}
