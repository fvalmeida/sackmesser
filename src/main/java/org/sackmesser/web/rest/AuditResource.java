package org.sackmesser.web.rest;

import org.joda.time.LocalDateTime;
import org.sackmesser.security.AuthoritiesConstants;
import org.sackmesser.service.AuditEventService;
import org.sackmesser.web.propertyeditors.LocaleDateTimeEditor;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for getting the audit events.
 */
@RestController
@RequestMapping("/app")
public class AuditResource {

    @Inject
    private AuditEventService auditEventService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new LocaleDateTimeEditor("yyyy-MM-dd", false));
    }

    @RequestMapping(value = "/rest/audits/all",
            method = RequestMethod.GET,
            produces = "application/json")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findAll() {
        return auditEventService.findAll();
    }

    @RequestMapping(value = "/rest/audits/byDates",
            method = RequestMethod.GET,
            produces = "application/json")
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public List<AuditEvent> findByDates(@RequestParam(value = "fromDate") LocalDateTime fromDate,
                                        @RequestParam(value = "toDate") LocalDateTime toDate) {
        return auditEventService.findByDates(fromDate, toDate);
    }
}
