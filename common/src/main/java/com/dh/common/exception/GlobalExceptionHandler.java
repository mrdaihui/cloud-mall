package com.dh.common.exception;

import com.dh.common.models.CodeDefined;
import com.dh.common.models.R;
import com.dh.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 全局统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String MESSAGE = "message";

    /**
     * 自定义异常处理
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = RException.class)
    @ResponseBody
    public R baseExceptionHandler(HttpServletRequest req, RException ex) throws Exception {
        return R.error(ex);
    }

    /**
     * 404 异常处理
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public R noHandlerExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        return R.error(CodeDefined.URL_NOT_FOUND);
    }

    /**
     * 未知异常处理
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R exceptionHandler(HttpServletRequest req, Exception ex) throws Exception {

        logger.error("未知异常:", ex);
        return R.error(CodeDefined.ERROR);
    }

    /**
     * 参数验证异常处理
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public R parameterExceptionHandler(HttpServletRequest req, Exception ex) {
        List<Map<String, String>> resErrors = new ArrayList<>();
        List<ObjectError> errors = null;
        BindingResult bindingResult = null;
        if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            errors = bindingResult.getAllErrors();
        } else if (ex instanceof BindException) {
            bindingResult = ((BindException) ex).getBindingResult();
            errors = bindingResult.getAllErrors();
        }

        logDetails(req, Objects.requireNonNull(bindingResult));

        for (ObjectError error : errors) {
            Map<String, String> result = new HashMap<>();
            result.put(MESSAGE, "未知的参数错误");

            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                String messageFormat = fieldError.getDefaultMessage();

                result.put("field", fieldError.getField());
                result.put(MESSAGE, messageFormat);

                String value = String.valueOf(fieldError.getRejectedValue());
                if (!StringUtils.isNulltoString(value)) {
                    result.put("value", value);
                }
            } else {
                result.put(MESSAGE, error.getDefaultMessage());
            }
            resErrors.add(result);
        }

        return R.error(CodeDefined.ERROR_PARAMETER).put("data", resErrors);
    }

    private void logDetails(HttpServletRequest req, BindingResult bindingResult) {
        logger.warn(" url:{},请求参数绑定错误,{} ", req.getRequestURL(), bindingResult.getTarget() != null ? bindingResult.getTarget().toString() : "");
    }

    /**
     * 请求参数语法错误
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public R httpMessageNotReadableExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        logger.error("json语法错误异常:", ex);
        return R.error(CodeDefined.ERROR_SYNTAX);
    }

    /**
     * 请求方法错误
     *
     * @param req
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public R HttpRequestMethodNotSupportedException(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) throws Exception {
        logger.error("请求方法类型错误:", ex);
        return R.error(CodeDefined.METHOD_ERROR.getValue(), String.format(
                CodeDefined.METHOD_ERROR.getDesc(), splicing(",", ex.getSupportedMethods())));
    }

    public String splicing(String regex, String... sourceArray) {

        StringBuffer stringBuffer = new StringBuffer();

        Arrays.stream(sourceArray).forEach(s -> {
            stringBuffer.append(s).append(regex);
        });

        return stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public R handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, String> result = new HashMap<>();
        result.put("field", ex.getMessage().split("'")[1]);
        result.put(MESSAGE, "不能为空");
        return R.error(CodeDefined.ERROR_PARAMETER).put("data",result);
    }

}
