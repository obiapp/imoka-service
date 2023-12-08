package org.obi.web.app.jsf;

import org.obi.web.app.entities.EntitiesSet;
import org.obi.web.app.jsf.util.JsfUtil;
import org.obi.web.app.jsf.util.PaginationHelper;
import org.obi.web.app.sessions.EntitiesSetFacade;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.obi.web.app.entities.EntitiesSetGroup;

@Named("entitiesSetController")
@SessionScoped
public class EntitiesSetController implements Serializable {

    private EntitiesSet current;
    private DataModel items = null;
    @EJB
    private org.obi.web.app.sessions.EntitiesSetFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    @Inject
    private EntitiesSetGroupController entitiesSetGroupController;
    
    
    public EntitiesSetController() {
    }

    public EntitiesSet getSelected() {
        if (current == null) {
            current = new EntitiesSet();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EntitiesSetFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (EntitiesSet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new EntitiesSet();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("EntitiesSetCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (EntitiesSet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("EntitiesSetUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (EntitiesSet) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/obi").getString("EntitiesSetDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/obi").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public EntitiesSet getEntitiesSet(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    public EntitiesSet findByGroupAndVal(int esg_id, int setValue){
        
        EntitiesSetGroup esg = entitiesSetGroupController.getEntitiesSetGroup(esg_id);
        
//        Map<String, Object> filters = new HashMap<>();
//        filters.put("es_group", esg_id);
//        filters.put("es_value", setValue);
//        List<EntitiesSet> lst = ejbFacade.findByCriterias(0, 1, null, filters);

        List<EntitiesSet> lst = ejbFacade.findByGroupAndVal(esg, setValue);

        if(lst == null)
            return null;
        
        return lst.get(0);
    }

    @FacesConverter(forClass = EntitiesSet.class)
    public static class EntitiesSetControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntitiesSetController controller = (EntitiesSetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "entitiesSetController");
            return controller.getEntitiesSet(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EntitiesSet) {
                EntitiesSet o = (EntitiesSet) object;
                return getStringKey(o.getEsId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + EntitiesSet.class.getName());
            }
        }

    }

}
