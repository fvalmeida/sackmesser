package org.sackmesser.web.rest;

import com.codahale.metrics.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.sackmesser.domain.User;
import org.sackmesser.repository.UserRepository;
import org.sackmesser.security.AuthoritiesConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.Optional;

/**
 * REST controller for managing users.
 */
@Slf4j
@RestController
@RequestMapping("/app")
public class UserResource {

    @Inject
    private UserRepository userRepository;

    /**
     * GET  /rest/users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/rest/users/{login}",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    ResponseEntity<User> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return Optional.ofNullable(userRepository.findOne(login))
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
