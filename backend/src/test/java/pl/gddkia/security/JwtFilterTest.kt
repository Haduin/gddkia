package pl.gddkia.security

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.junit.jupiter.Testcontainers
import pl.gddkia.GenreTests
import kotlin.test.assertTrue


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext             // make sure to share spring context when running on testcontainers
@Testcontainers
class JwtFilterTest : GenreTests() {

    val client: TestRestTemplate = TestRestTemplate()

    @LocalServerPort
    var localServerPort: Int = 0

    @Test
    fun `example test 1`() {
        val entity = client.getForEntity<String>("http://localhost:$localServerPort/region")
        assertTrue { entity.statusCode == HttpStatus.FORBIDDEN }
    }

}