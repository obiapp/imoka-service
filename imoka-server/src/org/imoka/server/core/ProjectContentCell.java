/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imoka.server.core;

import javafx.event.EventDispatchChain;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import org.imoka.server.util.JfxUtil;

/**
 * <h1>ProjectContentCell</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class ProjectContentCell extends TreeCell<ProjectContent> {

    private TextField textField;
    
    private ProjectContent content;

    @Override
    public void startEdit() {
        JfxUtil.out("Stat Edit now");
        super.startEdit();

        if (content == null) {
            JfxUtil.out("Stat Edit now");
            //createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        JfxUtil.out("cancel edit now");
        super.cancelEdit();
        setText((String) getItem().getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(ProjectContent item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            if (textField != null) {
                textField.setText(getString());
            }
            setText(null);
            setGraphic(textField);
        } else {
            setText(getString());
            setGraphic(getTreeItem().getGraphic());
        }

    }


    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
