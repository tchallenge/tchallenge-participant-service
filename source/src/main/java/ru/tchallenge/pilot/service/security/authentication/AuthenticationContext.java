package ru.tchallenge.pilot.service.security.authentication;

import java.util.Optional;

/**
 * Request authentication context.
 *
 * @author Ilia Gubarev
 */
public interface AuthenticationContext {

    /**
     * Checks if authentication is available for the current request.
     *
     * @return <code>true</code> if authentication is available.
     */
    boolean isAuthenticationAvailable();

    /**
     * Gets authentication for the current request.
     *
     * @return authentication for the current request.
     * @throws RuntimeException if authentication is not available.
     */
    Authentication getAuthentication();

    /**
     * Gets authentication for the current request.
     *
     * @return optional authentication for the current request.
     */
    Optional<Authentication> getAuthenticationIfAvailable();

    /**
     * Removes authentication from the current request.
     */
    void removeAuthentication();

    /**
     * Sets authentication for teh current request.
     *
     * @param authentication to be set.
     */
    void setAuthentication(Authentication authentication);
}
