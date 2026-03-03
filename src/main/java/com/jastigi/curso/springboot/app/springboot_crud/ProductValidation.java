package com.jastigi.curso.springboot.app.springboot_crud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jastigi.curso.springboot.app.springboot_crud.entities.Product;

@Component
public class ProductValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        // NAME
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "El nombre es requerido");
        if (!errors.hasFieldErrors("name")) {
            if (product.getName().length() < 3 || product.getName().length() > 20) {
                errors.rejectValue("name", null, "El nombre debe tener entre 3 y 20 caracteres");
            }
        }

        // DESCRIPTION
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null,
                "La descripcion es requerida");
        if (!errors.hasFieldErrors("description")) {
            if (product.getDescription().length() < 10 || product.getDescription().length() > 100) {
                errors.rejectValue("description", null,
                        "La descripcion debe tener entre 10 y 100 caracteres");
            }
        }

        // PRICE
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", null, "El precio es requerido");
        if (!errors.hasFieldErrors("price")) {
            if (product.getPrice() < 500) {
                errors.rejectValue("price", null, "El precio debe ser mayor o igual a 500");
            }
        }

    }

}
