/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.exception;

/**
 * Error thrown when an EDT violation is detected. For more details, please read
 * the <a
 * href="http://java.sun.com/javase/6/docs/api/javax/swing/package-summary.html#threading" target="_blank">Swing's
 * Threading Policy</a>.
 *
 * @author Alex Ruiz
 */
public class EdtViolationException extends RuntimeException {

    /**
     * Creates a new {@link EdtViolationException}.
     *
     * @param message the detail message.
     */
    public EdtViolationException(String message) {
        super(message);
    }
}
