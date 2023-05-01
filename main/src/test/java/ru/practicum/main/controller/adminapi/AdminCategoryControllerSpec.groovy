package ru.practicum.main.controller.adminapi

import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.category.NewCategoryDto
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.category.CategoryMapper
import ru.practicum.main.model.category.Category
import ru.practicum.main.service.category.CategoryService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminCategoryControllerSpec extends Specification {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    def "Should return 400 when create request is incorrect"() {
        given:
        def service = Mock(CategoryService)
        def controller = new AdminCategoryController(service, categoryMapper);
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def newCategoryRequest = NewCategoryDto.builder().build()

        expect:
        def request = post("/admin/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(newCategoryRequest).toString())
        and:
        server.perform(request)
                .andExpect(status().isBadRequest())

    }

    def "Should return 201 when create new category"() {
        given:
        def service = Mock(CategoryService)
        def controller = new AdminCategoryController(service, categoryMapper);
        def server = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(MainServiceHandler)
                .build()

        and:
        def newCategoryRequest = NewCategoryDto.builder()
                .name("test")
                .build()

        when:
        def request = post("/admin/categories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new JsonBuilder(newCategoryRequest).toString())
        and:
        server.perform(request)
                .andExpect(status().isCreated())
        then:
        interaction {
            1 * service.createCategory(_ as Category) >> {
                Category.builder()
                        .id(1L)
                        .name("test").build()
            }
        }
    }
}
