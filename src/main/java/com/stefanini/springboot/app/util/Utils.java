package com.stefanini.springboot.app.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(value = "utils")
public class Utils implements IUtils {

    @Override
    public boolean validBindingResult(BindingResult result, Map<String, Object> response) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return true;
        }
        return false;
    }

}
