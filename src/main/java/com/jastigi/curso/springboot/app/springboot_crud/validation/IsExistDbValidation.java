package com.jastigi.curso.springboot.app.springboot_crud.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jastigi.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistDbValidation implements ConstraintValidator<IsExistDb, String> {

    @Autowired
    private ProductService service;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        return !service.existsBySku(value);
    }

}
