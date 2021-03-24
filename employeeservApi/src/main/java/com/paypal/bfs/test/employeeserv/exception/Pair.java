package com.paypal.bfs.test.employeeserv.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pair<T, R> {
    T field;
    R value;
}
