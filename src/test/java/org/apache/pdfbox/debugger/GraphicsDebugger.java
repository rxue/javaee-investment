package org.apache.pdfbox.debugger;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.List;

import javax.swing.JComponent;

public class GraphicsDebugger {
	
	public static void showGraphics(List<GeneralPath> pathList) {
		PDFDebugger debugger = new PDFDebugger();
		Container panel = debugger.getContentPane();
		//Ref: https://kodejava.org/how-do-i-draw-a-generalpath-in-java-2d/
		JComponent comp = new JComponent() {
			/**
			 * 
			 */
			//private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2D = (Graphics2D) g;
				pathList.stream().forEach(e ->
					g2D.draw(e));	
			}
		};
		panel.add(comp);
		debugger.setSize(600, 600);
		debugger.setVisible(true);
	}

}
