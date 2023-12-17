/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.exception;

import javax.swing.*;

/**
 * <p>
 * Fails a test when a Event Dispatch Thread rule violation is detected. See <a
 * href="http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html">How
 * to Use Threads</a> for more information.
 * </p>
 *
 * @author Alex Ruiz
 */
public class FailOnThreadViolationRepaintManager extends CheckThreadViolationRepaintManager {

    /**
     * the {@link RepaintManager} that was installed before {@link #install()}
     * has been called.
     */
    private static RepaintManager previousRepaintManager;

    /**
     * <p>
     * Creates a new {@link FailOnThreadViolationRepaintManager} and sets it as
     * the current repaint manager.
     * </p>
     *
     * <p>
     * On Sun JVMs, this method will install the new repaint manager the first
     * time only. Once installed, subsequent calls to this method will not
     * install new repaint managers. This optimization may not work on non-Sun
     * JVMs, since we use reflection to check if a
     * {@code CheckThreadViolationRepaintManager.java} is already installed.
     * </p>
     *
     * @return the created (and installed) repaint manager.
     * @see #uninstall()
     * @see RepaintManager#setCurrentManager(RepaintManager)
     */
    public static FailOnThreadViolationRepaintManager install() {
        Object m = currentRepaintManager();
        if (m instanceof FailOnThreadViolationRepaintManager) {
            return (FailOnThreadViolationRepaintManager) m;
        }
        return installNew();
    }

    /**
     * <p>
     * Tries to restore the repaint manager before installing the
     * {@link FailOnThreadViolationRepaintManager} via {@link #install()}.
     * </p>
     *
     * @return the restored (and installed) repaint manager.
     * @see #install()
     * @see RepaintManager#setCurrentManager(RepaintManager)
     */
    public static RepaintManager uninstall() {
        RepaintManager restored = previousRepaintManager;
        setCurrentManager(restored);
        previousRepaintManager = null;
        return restored;
    }

    private static RepaintManager currentRepaintManager() {
        try {
            RepaintManager repaintManager = RepaintManager.currentManager(null);

            if (repaintManager != null) {
                return repaintManager;
            }
        } catch (RuntimeException e) {
            return null;
        }
        return null;
    }

    private static FailOnThreadViolationRepaintManager installNew() {
        FailOnThreadViolationRepaintManager m = new FailOnThreadViolationRepaintManager();
        previousRepaintManager = currentRepaintManager();
        setCurrentManager(m);
        return m;
    }

    public FailOnThreadViolationRepaintManager() {
    }

    public FailOnThreadViolationRepaintManager(boolean completeCheck) {
        super(completeCheck);
    }

    /**
     * Throws a {@link EdtViolationException} when an EDT access violation is
     * found.
     *
     * @param c the component involved in the EDT violation.
     * @param stackTraceElements stack trace elements to be set to the thrown
     * exception.
     * @throws EdtViolationException when an EDT access violation is found.
     */
    @Override
    void violationFound(JComponent c, StackTraceElement[] stackTraceElements) {
        EdtViolationException e = new EdtViolationException("EDT violation detected");
        e.setStackTrace(stackTraceElements);
        throw e;
    }
}
