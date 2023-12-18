/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.imoka.service.Form.Docking;

import java.awt.Component;
import javax.swing.JFrame;

/**
 *
 * @author r.hendrick
 */
class ButtonAction extends DockAction {

        public String getTitle() {
            return "Buttons and Tool Bars";
        }

        public String getDescription() {
            return "Simple demo with a draggable buttons and draggable tool bars";
        }

        public Component createContentPane(JFrame frame) {
            return new ButtonSample(frame);
        }
    }
