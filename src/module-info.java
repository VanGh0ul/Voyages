module Practika {
		requires javafx.controls;
		requires javafx.fxml;
		requires javafx.graphics;
		requires java.xml;
		requires javafx.base;
		requires java.base;
		requires java.sql;
		requires com.jfoenix;
	//	requires de.jensd.fx.glyphs.fontawesome;
		

		
		opens application to javafx.graphics, javafx.fxml, javafx.base;
		

}
