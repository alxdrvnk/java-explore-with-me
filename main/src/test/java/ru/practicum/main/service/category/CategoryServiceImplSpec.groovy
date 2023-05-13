package ru.practicum.main.service.category

import org.springframework.dao.DataIntegrityViolationException
import ru.practicum.main.exception.EwmAlreadyExistsException
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.model.category.Category
import ru.practicum.main.repository.CategoryRepository
import spock.lang.Specification


class CategoryServiceImplSpec extends Specification {

    def "Should throw EwmAlreadyExistsException when create Category with existing name"() {
        given:
        def category = Category.builder().name("Test").build()
        def repository = Stub(CategoryRepository) {
            save(category) >> { throw new DataIntegrityViolationException("") }
        }

        def service = new CategoryServiceImpl(repository)

        when:
        service.createCategory(category)
        then:
        thrown(EwmAlreadyExistsException)
    }

    def "Should throw EwmNotFoundException when Category not found by id"() {
        given:
        def repository = Stub(CategoryRepository) {
            existsById(1L) >> false
        }

        def service = new CategoryServiceImpl(repository)

        when:
        service.deleteCategory(1L)

        then:
        thrown(EwmNotFoundException)
    }

    def "Should throw EwmAlreadyExistsException when update Category with existing name"() {
        given:
        def category = Category.builder().name("Test").build()
        def repository = Stub(CategoryRepository) {
            findById(1L) >> Optional.of(Category.builder().name("Category").build())
            existsByName("Test") >> true
        }

        def service = new CategoryServiceImpl(repository)

        when:
        service.updateCategory(1L, category)
        then:
        thrown(EwmAlreadyExistsException)
    }
}
