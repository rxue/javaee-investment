package org.apache.pdfbox.debugger;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.geom.GeneralPath;
import java.util.List;

import javax.swing.JComponent;

public class GraphicsDebugger {
	
	public static void showGraphics(List<GeneralPath> pathList) {
		PDFDebugger debugger = new PDFDebugger();
		Container panel = debugger.getContentPane();
		JComponent comp = new JComponent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				g.drawRect(10, 10, 10, 20);
			}
		};
		panel.add(comp);
		debugger.setSize(600, 600);
		debugger.setVisible(true);
	}

}
