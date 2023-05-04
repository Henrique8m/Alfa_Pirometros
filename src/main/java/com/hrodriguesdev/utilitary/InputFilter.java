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
	private boolean upperCase;
	private int maxLength;
	private String restriction;
	private int count = 0;
	
	/**
	 * @param box
	 *            The combo box to whose textProperty this listener is
	 *            added.
	 * @param items
	 *            The {@link FilteredList} containing the items in the list.
	 */
	public InputFilter(ComboBox<T> box, FilteredList<T> items, boolean upperCase, int maxLength,
	        String restriction) {
	    this.box = box;
	    this.items = items;
	    this.upperCase = upperCase;
	    this.maxLength = maxLength;
	    this.restriction = restriction;
	    this.box.setItems(items);
   	

//		 this.box.showingProperty().addListener(new ChangeListener<Boolean>() {
//	
//	        @Override
//	        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//	        	System.out.println("old " + oldValue);
//	        	System.out.println("New "+ newValue );
//	            if (newValue == false) {
//	            	System.out.println("IF entrou");
//	                items.setPredicate(null);
//	               // box.getParent().requestFocus();
//	            }
//	
//	        }
//	
//	    });
	}
	
	public InputFilter(ComboBox<T> box, FilteredList<T> items, boolean upperCase, int maxLength) {
	    this(box, items, upperCase, maxLength, null);
	}
	
	public InputFilter(ComboBox<T> box, FilteredList<T> items, boolean upperCase) {
	    this(box, items, upperCase, -1, null);
	}
	
	
	
	public InputFilter(ComboBox<T> box, FilteredList<T> items) {
	    this(box, items, false);
	}

	public FilteredList<T> getItems(){
		return items;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	    StringProperty value = new SimpleStringProperty(newValue);
	    this.count++;
//	    System.out.println(this.count);
//	    oldValor = oldValue;
//	    newValor = newValue;
//	    System.out.println(oldValue);
//	    System.out.println(newValue);
//	    System.out.println( observable.getValue() );
	    T selected = box.getSelectionModel().getSelectedItem() != null
	            ? box.getSelectionModel().getSelectedItem() : null;
	
	    @SuppressWarnings("unused")
		String selectedString = null;
	    

	    
	    if (selected != null) {
//	    	System.out.println(selected.toString());
	        selectedString = selected.toString();
	    }
	
	    if (upperCase) {
	        value.set(value.get().toUpperCase());
	    }
	
	    if (maxLength >= 0 && value.get().length() > maxLength) {
	        value.set(oldValue);
	    }
	
	    if (restriction != null) {
	        if (!value.get().matches(restriction + "*")) {
	            value.set(oldValue);
	        }
	    }

	    if (selected != null && selected != "") {
	        selected = null;
//	        System.out.println("If selected");
	        box.getEditor().setText( value.get() );
	        Platform.runLater(() -> box.getEditor().end());
	        return;
	        
	    } else if(!newValue.isBlank()){	
//	    	System.out.println("setPredicate");
	       // System.out.println(value.get());
	        items.setPredicate(item -> {

	            T itemString = item;
	            if (this.box.getConverter().toString(itemString).toUpperCase().contains(value.get().toUpperCase())) {
	                return true;
	            } else {
	                return false;
	            }
	        });
	    }else if((oldValue.length() - 1 ) > newValue.length()) {
//	    	System.out.println("Entrou no set");
	    	box.getEditor().setText( oldValue );
	    }

	    if (!box.isShowing()) {
	        if (!newValue.isEmpty() && box.isFocused()) {
//	        	System.out.println("If 1");
	            box.show();
	        }
	    }

	    else {
	        if (items.size() == 1) {
	            T item = items.get(0);
	            T comparableItem = item;
	
	            if (value.get().equals(comparableItem)) {
//	            	System.out.println("else do if 1");
	                Platform.runLater(() -> box.hide());
	            }
	        }
	    }
	    
	   
	    
	    
	    
	}
		
}

