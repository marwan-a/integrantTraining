package com.javatpoint.validations;

import javax.validation.GroupSequence;
 
@GroupSequence({FirstOrder.class, SecondOrder.class})
public interface ValidationSequence {
}