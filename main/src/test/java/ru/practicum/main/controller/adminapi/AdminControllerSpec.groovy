package ru.practicum.main.controller.adminapi

import groovy.json.JsonBuilder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.practicum.main.dto.user.NewUserRequest
import ru.practicum.main.exception.EwmAlreadyExistsException
import ru.practicum.main.handler.MainServiceHandler
import ru.practicum.main.mapper.UserMapper
import ru.practicum.main.model.User
import ru.practicum.main.service.admin.AdminService
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AdminControllerSpec extends Specification {

   private final UserMapper userMapper = new UserMapper()

   def "Should return 409 when create user with existed email"() {
      given:
      def service = Mock(AdminService)
      def controller = new AdminController(service, userMapper)
      def server = MockMvcBuilders
              .standaloneSetup(controller)
              .setControllerAdvice(MainServiceHandler)
              .build()
      and:
      def newUserRequest = NewUserRequest.builder()
              .name("TestName")
              .email("testmail@mail.mail")
              .build()

      when:
      def request = post("/admin/users")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content(new JsonBuilder(newUserRequest).toString())

      and:
      server.perform(request)
              .andExpect(status().isConflict())

      then:
      interaction {
         1 * service.createUser(_ as User) >> { throw new EwmAlreadyExistsException("") }
      }
   }

   def "Should return 201 when new user created"() {
      given:
      def service = Mock(AdminService)
      def controller = new AdminController(service, userMapper)
      def server = MockMvcBuilders
              .standaloneSetup(controller)
              .setControllerAdvice(MainServiceHandler)
              .build()

      and:
      def newUserRequest = NewUserRequest.builder()
              .name("TestName")
              .email("testmail@mail.mail")
              .build()
      when:
      def request = post("/admin/users")
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .content(new JsonBuilder(newUserRequest).toString())

      and:
      server.perform(request)
              .andExpect(status().isCreated())

      then:
      interaction {
         1 * service.createUser(_ as User) >> { User.builder().id(1L).name("").email("").build() }
      }
   }

}
