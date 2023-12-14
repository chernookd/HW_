package edu.project3;

import java.time.LocalDateTime;

public record LogRecord(
    String remoteAddr,
    String remoteUser,
    LocalDateTime timeLocal,
    String request,
    int status,
    long bodyBytesSent,
    String httpReferer,
    String htppUserAgent
) { }
//record User(String name, String surname) {}
