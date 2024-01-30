package com.hrodriguesdev.utilitary.tabsLoad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.dependency.InjecaoDependency;
import com.hrodriguesdev.utilitary.Log;
import com.hrodriguesdev.utilitary.NewView;
import com.hrodriguesdev.utilitary.fxml.FXMLPath;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabsMainView {
	public static TabPane TAB_PANE_MAIN;
	
	public static void loadMain() {
		try {
			AnchorPane anchorPane = (AnchorPane) NewView.loadFXML(FXMLPath.TAB_MAIN, InjecaoDependency.MAIN_TAB_CONTROLLER);
			TAB_PANE_MAIN = (TabPane) anchorPane.getChildren().get(0);
			anchorPane.getChildren().clear();			
			
			TabPane tabPaneFind = (TabPane) NewView.loadFXML(FXMLPath.TAB_FIND, InjecaoDependency.TAB_FIND_CONTROLLER);
			TabPane tabPaneCertificate = (TabPane) NewView.loadFXML(FXMLPath.TAB_CERTIFICATE, InjecaoDependency.TAB_CERTIFICATE_CONTROLLER);
			TabPane tabPaneCompany = (TabPane) NewView.loadFXML(FXMLPath.TAB_COMPANY, InjecaoDependency.TAB_COMPANY_CONTROLLER);	
			TabPane tabPaneOs = (TabPane) NewView.loadFXML(FXMLPath.TAB_OS, InjecaoDependency.TAB_OS_CONTROLLER);	
			TabPane tabPaneCertificateexpired  = (TabPane) NewView.loadFXML(FXMLPath.TAB_CERTIFICATE_EXPIRED, InjecaoDependency.TAB_CERTIFICATE_EXPIRED_CONTROLLER);
			
			List<Tab> tabs = new ArrayList<>();
			tabs.add(tabPaneFind.getTabs().get(0));
			tabs.add(tabPaneCertificate.getTabs().get(0));
			tabs.add(tabPaneCompany.getTabs().get(0));
			tabs.add(tabPaneOs.getTabs().get(0));
			tabs.add(tabPaneCertificateexpired.getTabs().get(0));
						
			TAB_PANE_MAIN.getTabs().addAll(tabs);			
			TAB_PANE_MAIN.getTabs().get(1).setDisable(false);
			TAB_PANE_MAIN.getTabs().get(2).setDisable(false);
			TAB_PANE_MAIN.getTabs().get(3).setDisable(false);
			TAB_PANE_MAIN.getTabs().get(4).setDisable(false);
			TAB_PANE_MAIN.getTabs().get(5).setDisable(false);
			
			anchorPane.getChildren().addAll(TAB_PANE_MAIN);			
			NewView.SCENE_MAIN_VIEW = new Scene(anchorPane);

		} catch (IOException e) {
			Log.logString("TabsMainView", e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		
	}
}
