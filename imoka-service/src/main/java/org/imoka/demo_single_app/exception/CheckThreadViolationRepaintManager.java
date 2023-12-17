/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.demo_single_app.exception;

import javax.swing.*;
import java.lang.ref.WeakReference;
import java.util.Objects;

import static javax.swing.SwingUtilities.isEventDispatchThread;

abstract class CheckThreadViolationRepaintManager extends RepaintManager {

    private final boolean completeCheck;

    private WeakReference<JComponent> lastComponent;

    CheckThreadViolationRepaintManager() {
        // it is recommended to pass the complete check
        this(true);
    }

    CheckThreadViolationRepaintManager(boolean completeCheck) {
        this.completeCheck = completeCheck;
    }

    @Override
    public synchronized void addInvalidComponent(JComponent component) {
        checkThreadViolations(Objects.requireNonNull(component));
        super.addInvalidComponent(component);
    }

    @Override
    public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
        checkThreadViolations(Objects.requireNonNull(component));
        super.addDirtyRegion(component, x, y, w, h);
    }

    private void checkThreadViolations(JComponent c) {
        if (!isEventDispatchThread() && (completeCheck || c.isShowing())) {
            boolean imageUpdate = false;
            boolean repaint = false;
            boolean fromSwing = false;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement st : stackTrace) {
                if (repaint && st.getClassName().startsWith("javax.swing.")) {
                    fromSwing = true;
                }
                if (repaint && "imageUpdate".equals(st.getMethodName())) {
                    imageUpdate = true;
                }
                if ("repaint".equals(st.getMethodName())) {
                    repaint = true;
                    fromSwing = false;
                }
            }
            if (imageUpdate) {
                // assuming it is java.awt.image.ImageObserver.imageUpdate(...)
                // image was asynchronously updated, that's ok
                return;
            }
            if (repaint && !fromSwing) {
                // no problems here, since repaint() is thread safe
                return;
            }
            // ignore the last processed component
            if (lastComponent != null && c == lastComponent.get()) {
                return;
            }
            lastComponent = new WeakReference<>(c);
            violationFound(c, stackTrace);
        }
    }

    abstract void violationFound(JComponent c, StackTraceElement[] stackTrace);
}
