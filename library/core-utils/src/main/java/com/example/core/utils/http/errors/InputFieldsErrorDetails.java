package com.example.core.utils.http.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class InputFieldsErrorDetails implements Serializable {

    private List<String> invalidFields;

}
