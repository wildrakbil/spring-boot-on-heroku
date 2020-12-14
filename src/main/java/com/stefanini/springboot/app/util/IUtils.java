package com.stefanini.springboot.app.util;

import org.springframework.validation.BindingResult;

import java.util.Map;

public interface IUtils {

    boolean validBindingResult(BindingResult result, Map<String, Object> response);
}
