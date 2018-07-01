package ru.tchallenge.pilot.service.security.authentication;

/**
 * Authentication method types.
 *
 * @author Ilia Gubarev
 */
public enum AuthenticationMethod {

    /**
     * User credentials must be sent in the body of the request.
     */
    PASSWORD,

    /**
     * Security token payload must be sent among query parameters.
     */
    TOKEN,

    /**
     * Voucher payload must be sent among query parameters.
     */
    VOUCHER
}
