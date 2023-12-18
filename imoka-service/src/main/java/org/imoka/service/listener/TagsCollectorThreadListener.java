/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.imoka.service.listener;

/**
 *
 * @author r.hendrick
 */
public interface TagsCollectorThreadListener {

    Boolean running = false;

    /**
     * <h3>onProcessingThread</h3>
     *
     * <p>
     * Main process is on going in the run loop
     * </p>
     */
    void onProcessingThread();

    /**
     * <h3>onOldingThread</h3>
     *
     * <p>
     * On olding thread, main loop still processing waiting for restart. In this
     * case the thread is still alive (check existing method).
     * </p>
     */
    void onOldingThread();
}
