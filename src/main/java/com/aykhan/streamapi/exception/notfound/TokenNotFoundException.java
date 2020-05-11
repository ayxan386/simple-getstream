package com.aykhan.streamapi.exception.notfound;

public class TokenNotFoundException extends NotFound {

    public TokenNotFoundException() {
        super("Token was not provided");
    }
}
