package com.example.demo.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String errorPage = "error";
        String pageTittle = "Error";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                pageTittle = "Page not found";
                errorPage = "error/404";
                LOGGER.error("Error 404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                pageTittle = "Internal Server Error";
                errorPage = "error/500";
                LOGGER.error("Error 500");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                pageTittle = "Forbiden Error";
                errorPage = "error/403";
                LOGGER.error("Error 403");
            }
        }
        model.addAttribute("pageTittle", pageTittle);
        return errorPage;
    }
}
