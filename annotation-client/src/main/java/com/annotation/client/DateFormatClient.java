package com.annotation.client;

import annotations.DateFormat;

import java.time.LocalDate;


public class DateFormatClient {
    @DateFormat("yyyy-MM-dd")
    private LocalDate date;
}
