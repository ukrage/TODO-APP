package com.urkg.todoapi.api;

import org.dbunit.Assertion;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.csv.CsvURLDataSet;
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
    @MethodSource("createTestProvider")
    public void createTest(String requestBody, String expectedBody, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        var givenUrl = this.getClass().getResource("/data/create/" + dbPath + "/given/");
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/tasks")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(result -> JSONAssert.assertEquals(
                        expectedBody,
                        result.getResponse().getContentAsString(),
                        false
                ));

        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualChannelsTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource("/data/create/" + dbPath + "/expected/");
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedChannelsTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);
    }

    private static Stream<Arguments> createTestProvider() {
        return Stream.of(
                Arguments.arguments(
                        """
                                {
                                    "title": "task1",
                                    "content": "APIで追加したタスク"
                                  }             
                                """,
                        """
                                {
                                    "id": 1,
                                    "title": "task1",
                                    "content": "APIで追加したタスク",
                                    "finishedFlg": false
                                }
                                """,
                        "no-record"
                ),
                Arguments.arguments(
                        """
                                {
                                    "title": "task3",
                                    "content": "APIで追加したタスク"
                                  }                      
                                """,
                        """
                                {
                                    "id": 3,
                                    "title": "task3",
                                    "content": "APIで追加したタスク",
                                    "finishedFlg": false
                                }
                                """,
                        "multi-record"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("findTestProvider")
    public void findTest(String url, String expectedBody, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        URL givenURL = this.getClass().getResource("/data/find/" + dbPath + "/given/");
        databaseTester.setDataSet(new CsvURLDataSet(givenURL));
        databaseTester.onSetup();

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(result -> JSONAssert.assertEquals(
                        expectedBody,
                        result.getResponse().getContentAsString(),
                        false
                ));

        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualTestTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource("/data/find/" + dbPath + "/expected/");
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedTestTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedTestTable, actualTestTable);
    }

    private static Stream<Arguments> findTestProvider() {
        return Stream.of(
                Arguments.arguments(
                        "/tasks",
                        """
                                [
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
                                ]
                                """,
                        "multi-record"
                ),
                Arguments.arguments(
                        "/tasks",
                        """
                                []
                                 """,
                        "no-record"
                ),
                Arguments.arguments(
                        "/tasks",
                        """
                                []
                                 """,
                        "no-record"
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
                        "multi-record"
                ),
                Arguments.arguments(
                        "/tasks/2",
                        """
                                        {
                                             "id": 2,
                                             "title": "タスク2",
                                             "content": "レポートを作成する",
                                             "finishedFlg": false
                                         }
                                """,
                        "multi-record"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("updateTestProvider")
    public void updateTest(String url, String requestBody, String expectedBody) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        var givenUrl = this.getClass().getResource("/data/update/given/");
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/tasks/" + url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> JSONAssert.assertEquals(
                        expectedBody,
                        result.getResponse().getContentAsString(),
                        false
                ));

        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualChannelsTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource("/data/update/expected/");
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedChannelsTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);
    }

    private static Stream<Arguments> updateTestProvider() {
        return Stream.of(
                Arguments.arguments(
                        "1",
                        """
                                {
                                  "title": "タスク1",
                                  "content": "バグを調べる"
                                }
                                    """,
                        """
                                {
                                  "id": 1,
                                  "title": "タスク1",
                                  "content": "バグを調べる",
                                  "finishedFlg": false
                                }
                                """
                )
        );
    }

    @ParameterizedTest
    @MethodSource("patchTestProvider")
    public void patchTest(String url, String requestBody, String expectedBody) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        var givenUrl = this.getClass().getResource("/data/patch/given/");
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/tasks/" + url)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> JSONAssert.assertEquals(
                        expectedBody,
                        result.getResponse().getContentAsString(),
                        false
                ));

        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualChannelsTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource("/data/patch/expected/");
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedChannelsTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);
    }

    private static Stream<Arguments> patchTestProvider() {
        return Stream.of(
                Arguments.arguments(
                        "1",
                        """
                                {
                                  "finishedFlg": true
                                }
                                """,
                        """
                                {
                                    "id": 1,
                                    "title": "タスク1",
                                    "content": "バグを調べる",
                                    "finishedFlg": true
                                }
                                """
                )
        );
    }

}
