package com.example.axon.rabbit.camunda.launcher;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Groups;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;

@SpringBootApplication
@EnableProcessApplication
public class CamundaLauncher {
    public static void main(final String... args) throws Exception {
        SpringApplication.run(CamundaLauncher.class, args);
        ProcessEngine engine = BpmPlatform.getDefaultProcessEngine(); // https://github.com/berndruecker/camunda-spring-boot-amqp-microservice-cloud-example/blob/master/src/main/java/com/camunda/demo/springboot/Application.java
        createDefaultUser(engine);
    }

    private static void createDefaultUser(ProcessEngine engine) {
        if (engine.getIdentityService().createUserQuery().userId("demo").count() == 0) {
            User user = engine.getIdentityService().newUser("demo");
            user.setFirstName("Demo");
            user.setLastName("Demo");
            user.setPassword("demo");
            user.setEmail("demo@camunda.org");
            engine.getIdentityService().saveUser(user);

            Group group = engine.getIdentityService().newGroup(Groups.CAMUNDA_ADMIN);
            group.setName("Administrators");
            group.setType(Groups.GROUP_TYPE_SYSTEM);
            engine.getIdentityService().saveGroup(group);

            for (Resource resource : Resources.values()) {
                Authorization auth = engine.getAuthorizationService().createNewAuthorization(AUTH_TYPE_GRANT);
                auth.setGroupId(Groups.CAMUNDA_ADMIN);
                auth.addPermission(ALL);
                auth.setResourceId(ANY);
                auth.setResource(resource);
                engine.getAuthorizationService().saveAuthorization(auth);
            }

            engine.getIdentityService().createMembership("demo", Groups.CAMUNDA_ADMIN);
        }

    }
}
