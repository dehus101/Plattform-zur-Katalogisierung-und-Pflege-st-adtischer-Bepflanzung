package de.hhu.cs.dbs.dbwk.project.presentation.rest.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class SqlExceptionMapper implements ExceptionMapper<SQLException> {

    @Override
    public Response toResponse(SQLException exception) {
        int code = 500;
        if (exception.getErrorCode() == 19) code = 400;
        return Response.status(code)
                .entity(new ExceptionResponse(exception.getMessage(), code))
                .build();
    }
}
