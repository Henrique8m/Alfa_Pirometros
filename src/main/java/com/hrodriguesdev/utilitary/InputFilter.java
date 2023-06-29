package com.hrodriguesdev.utilitary;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

public class InputFilter<T> implements ChangeListener<String>{

	private ComboBox<T> box;
	private FilteredList<T> items;
	
	/**
	 * @param box
	 *            The combo box to whose textProperty this listener is
	 *            added.
	 * @param items
	 *            The {@link FilteredList} containing the items in the list.
	 */
	public InputFilter(ComboBox<T> box, FilteredList<T> items) {
	    this.box = box;
	    this.items = items;
	    this.box.setItems(items);

	}

	public FilteredList<T> getItems(){
		return items;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		
	    StringProperty value = new SimpleStringProperty(newValue);
	    	if(!newValue.isBlank() && box.getSelectionModel().isEmpty()){	
	        items.setPredicate(item -> {

	            T itemString = item;
	            if (this.box.getConverter().toString(itemString).toUpperCase().contains(value.get().toUpperCase())) {
	                return true;
	            } else {
	                return false;
	            }
	        });
        
    	}

        if (!newValue.isEmpty()) {
        	box.setValue((T) newValue);
        	Platform.runLater(() -> {
	        	if(box.getSelectionModel().isEmpty())
	        		box.hide();
	        	box.show();
	        	box.getEditor().end();
        	});
        }

	    
	}
		
}

