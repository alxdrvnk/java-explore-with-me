package ru.practicum.main.repository

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import ru.practicum.main.configuration.DbUnitConfig
import ru.practicum.main.model.category.Category
import spock.lang.Specification

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = [DbUnitConfig.class])
@TestExecutionListeners([TransactionDbUnitTestExecutionListener, DependencyInjectionTestExecutionListener])
class CategoryRepositorySpec extends Specification {

    @Autowired
    private CategoryRepository repository

    def "Should create Category with generated id"() {
        given:
        Category category = Category.builder()
                .name("TestName")
                .build()

        when:
        def dbCategory = repository.save(category)

        then:
        dbCategory.getId() == 1
    }

    @DatabaseSetup(value = "classpath:database/admin/categories/set_categories.xml",
            connection = "dbUnitDatabaseConnection")
    def "Should return Category when get by id"() {
        when:
        def dbCategory = repository.findById(1L).get()
        then:
        dbCategory.name == "TestName"
    }
}
