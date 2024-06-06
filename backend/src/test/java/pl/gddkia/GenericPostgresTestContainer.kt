package pl.gddkia

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@DirtiesContext
@Testcontainers
open class GenericPostgresTestContainer {
    companion object {
        @Container
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres")
            .apply {
                withDatabaseName("testDB")
                withUsername("postgres")
                withPassword("postgres")
                withInitScript("sql/init.sql")
            }

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }

    }

    @Test
    fun `dbContainer is running`() {
        Assertions.assertTrue(postgres.isRunning)
    }


}