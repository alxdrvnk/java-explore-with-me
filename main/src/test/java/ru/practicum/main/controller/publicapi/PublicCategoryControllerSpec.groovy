package ru.practicum.main.controller.publicapi

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.exception.EwmNotFoundException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.category.CategoryMapper
import ru.practicum.main.model.category.Category
import ru.practicum.main.service.category.CategoryService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class PublicCategoryControllerSpec extends Specification {

    def "Should return 200 when get all categories"() {
        given:
        def service = Mock(CategoryService)
        def categoryMapper = new CategoryMapper()
        def controller = new PublicCategoryController(service, categoryMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isOk())
        then:
        interaction {
            1 * service.getAllCategories(0, 10) >> { List.of(Category.builder().build()) }
        }
    }

    def "Should return 404 when not found category"() {
        given:
        def service = Mock(CategoryService)
        def categoryMapper = new CategoryMapper()
        def controller = new PublicCategoryController(service, categoryMapper)
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        when:
        def request = get("/categories/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        and:
        server.perform(request)
                .andExpect(status().isNotFound())
        then:
        interaction {
            1 * service.getById(1L) >> { throw new EwmNotFoundException("") }
        }
    }
}
