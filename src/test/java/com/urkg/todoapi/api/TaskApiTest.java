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
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    private void dbSetUp(IDatabaseTester databaseTester, String givenPath) throws Exception {
        var givenUrl = this.getClass().getResource(givenPath);
        databaseTester.setDataSet(new CsvURLDataSet(givenUrl));
        databaseTester.onSetup();
    }

    private void dbEquals(IDatabaseTester databaseTester, String expectedPath) throws Exception {
        var actualDataSet = databaseTester.getConnection().createDataSet();
        var actualChannelsTable = actualDataSet.getTable("tasks");
        var expectedUrl = this.getClass().getResource(expectedPath);
        var expectedDataSet = new CsvURLDataSet(expectedUrl);
        var expectedChannelsTable = expectedDataSet.getTable("tasks");
        Assertion.assertEquals(expectedChannelsTable, actualChannelsTable);
    }

    @ParameterizedTest
    @MethodSource("createTestProvider")
    public void createTest(String requestBody, String expectedBody, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        dbSetUp(databaseTester, "/data/create/" + dbPath + "/given/");

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

        dbEquals(databaseTester, "/data/create/" + dbPath + "/expected/");
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
        dbSetUp(databaseTester, "/data/find/" + dbPath + "/given/");

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

        dbEquals(databaseTester, "/data/find/" + dbPath + "/expected/");
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
        dbSetUp(databaseTester, "/data/update/given/");

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

        dbEquals(databaseTester, "/data/update/expected/");
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
    public void patchTest(String url, String requestBody, String expectedBody, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        dbSetUp(databaseTester, "/data/patch/" + dbPath + "/given/");

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

        dbEquals(databaseTester, "/data/patch/" + dbPath + "/expected/");
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
                                """,
                        "true"
                ),
                Arguments.arguments(
                        "1",
                        """
                                {
                                  "finishedFlg": false
                                }
                                """,
                        """
                                {
                                    "id": 1,
                                    "title": "タスク1",
                                    "content": "バグを調べる",
                                    "finishedFlg": false
                                }
                                """,
                        "false"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("deleteTestProvider")
    public void deleteTest(String url, String dbPath) throws Exception {
        IDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource);
        dbSetUp(databaseTester, "/data/delete/" + dbPath + "/given/");

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/tasks/" + url))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        dbEquals(databaseTester, "/data/delete/" + dbPath + "/expected/");
    }

    private static Stream<Arguments> deleteTestProvider() {
        return Stream.of(
                Arguments.arguments("1", "middle"),
                Arguments.arguments("2", "last")
        );
    }
}
