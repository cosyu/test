package com.example.controlleradvice;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
* @ExceptionHandler、@InitBinder、@ModelAttribute for the all controllers
* It can also handle exception for Interceptor
* */
@ControllerAdvice
public class ControllerHandler implements ProblemHandling {

    //add global attribute for all controller, it will be run before controller
    @ModelAttribute
    public void addModelAttribute(Model model){
        model.addAttribute("key1","value1");
        System.out.println(model);
    }

    //register date formatter for all controller, it will be run before controller
    @InitBinder
    public void initWebBinder(WebDataBinder binder){

       //binder.addCustomFormatter(new SimpleDateFormat("yyyy-MM-dd"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

    }

    /*
    @ResponseBody // add @ResponseBody to display error with JSON format
    @ExceptionHandler({RuntimeException.class})
    public Map errorHandler(Exception ex, NativeWebRequest request) {

        HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);

        Map errorMap = new HashMap();
        errorMap.put("code", 400);
        errorMap.put("msg", ex.getMessage());
        errorMap.put("path",(req != null ? req.getRequestURI() : ""));

        return errorMap;
    }
    */

    //handle specify exception for all controllers
    //It can also handle exception for Interceptor
    @ExceptionHandler({AppInvalidException.class})
    public ResponseEntity<Problem> handleAppInvalidException(AppInvalidException ex, NativeWebRequest request) {

        HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);

        ProblemBuilder builder = Problem.builder()
                .withStatus(ex.getHttpStatus())
                .withTitle(ex.getHttpStatus().getReasonPhrase())
                .with("code",ex.getErrCode())
                .with("message",ex.getErrMessage())
                .with("path",(req != null ? req.getRequestURI() : ""));

        return create(ex,builder.build(),request);
        /*
        It returns json like this
        stackTrace : [...]
        "type": "about:blank",
        "title": "Method Not Allowed",
        "status": "METHOD_NOT_ALLOWED",
        "detail": null,
        "instance": null,
        //customer information
        "parameters": {
            "code": 301,
            "message": "test error message",
            "path": "/advice4"
        },
        "message": "Method Not Allowed",
        "suppressed": [],
        "localizedMessage": "Method Not Allowed"
        *
        * */
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Problem> handleException(Exception ex, NativeWebRequest request) {

        HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);

        ProblemBuilder builder = Problem.builder()
                .with("message",ex.getMessage())
                .with("path",(req != null ? req.getRequestURI() : ""));

        return create(ex,builder.build(),request);

    }

    /**
     * Post-process Problem payload to add the customer key for front-end if needed
     */
    //this function process ResponseEntity<Problem> entity from exception handler  e.g. handleAppInvalidException
    //frond-end will get the error from this function
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request){

        if (entity == null || entity.getBody() == null) {
            return entity;
        }

        Problem problem = entity.getBody();//this entity is from handleAppInvalidException
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        Object errCode = problem.getParameters().get("code");//parameter is customer values
        if (errCode == null) {
            errCode = 59999;
        }

        String message = StringUtils.trimToEmpty((String) problem.getParameters().get("message"));

        HttpServletRequest req = request.getNativeRequest(HttpServletRequest.class);
        ProblemBuilder builder = Problem.builder()
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                //.withType(problem.getType())
                //.withDetail(problem.getDetail())
                .with("code",errCode)
                .with("message",message)
                .with("path", (req != null ? req.getRequestURI() : ""));

        if (problem instanceof ConstraintViolationProblem){
            //for server side validation
            errCode = 40011;
            builder.with("violations", ((ConstraintViolationProblem) problem).getViolations())
                    .with("code", errCode);
        }
        Problem problem1 = builder.build();
        HttpHeaders headers = entity.getHeaders();//Content-Type: Application/problem+json
        HttpStatus status = entity.getStatusCode();

        return new ResponseEntity<Problem>(problem1, headers, status);
    }


    /*
    return stackTrace or not , it is working, to fix this,
    please refer to WebConfig.java which register ProblemModule && ConstraintViolationProblemModule
    to ObjectMapper
    * */
    @Override
    public boolean isCausalChainsEnabled(){
        return false;
    }


}
