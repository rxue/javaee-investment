package org.apache.pdfbox.contentstream;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;

/**
 * Keyword: linear algebra
 * 
 * @author ruixueru
 *
 */
public class PDFPageGraphicsProcessor extends PDFGraphicsStreamEngine
{
    private final List<Rectangle2D> lines;
    private final List<GeneralPath> generalPaths;
    private GeneralPath currentGeneralPath;
    private int clipWindingRule;
    private AffineTransform translateInstance;
    private AffineTransform scaleInstance;
    private float pageHeight;
    private float pageWidth;

    public PDFPageGraphicsProcessor(PDPage page)
    {
    	super(page);
    	this.pageHeight = page.getCropBox().getHeight();
    	this.pageWidth = page.getCropBox().getWidth();
    	this.translateInstance = AffineTransform.getTranslateInstance(0, -this.pageHeight);
    	this.scaleInstance = AffineTransform.getScaleInstance(1, -1);
        this.clipWindingRule = -1;
        this.lines = new ArrayList<>();
        this.generalPaths = new ArrayList<>();
        this.currentGeneralPath = new GeneralPath();
    }
    
    public int getPageHeightInt() {
    	return Math.round(this.pageHeight);
    }
    
    public int getPageWidth() {
    	return Math.round(this.pageWidth);
    }
    
    protected void transformGeneralPathToTextQuadrant(GeneralPath generalPath) {
    	//generalPath.get
    	generalPath.transform(this.translateInstance);
    	generalPath.transform(this.scaleInstance);
    }

    @Override
    public void appendRectangle(Point2D p0, Point2D p1, Point2D p2, Point2D p3) throws IOException
    {
    	//System.out.println("appendRectangle");
        // to ensure that the path is created in the right direction, we have to create
        // it by combining single lines instead of creating a simple rectangle
    	this.currentGeneralPath.moveTo((float) p0.getX(), (float) p0.getY());
    	this.currentGeneralPath.lineTo((float) p1.getX(), (float) p1.getY());
    	this.currentGeneralPath.lineTo((float) p2.getX(), (float) p2.getY());
    	this.currentGeneralPath.lineTo((float) p3.getX(), (float) p3.getY());
        //System.out.println("appendRectangle:" + this.currentGeneralPath.getBounds());
        // close the subpath instead of adding the last line so that a possible set line
        // cap style isn't taken into account at the "beginning" of the rectangle
        this.currentGeneralPath.closePath();
    }

    @Override
    public void drawImage(PDImage pdi) throws IOException
    {
    }

    @Override
    public void clip(int windingRule) throws IOException
    {
    	//System.out.println("clip");
        // the clipping path will not be updated until the succeeding painting operator is called
        clipWindingRule = windingRule;
    }

    @Override
    public void moveTo(float x, float y) throws IOException
    {
    	//System.out.println("moveTo");
        this.currentGeneralPath.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) throws IOException
    {
    	//System.out.println("lineTo");
        this.currentGeneralPath.lineTo(x, y);
    }

    @Override
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException
    {
        this.currentGeneralPath.curveTo(x1, y1, x2, y2, x3, y3);
        //System.out.println("curveTo");
    }

    @Override
    public Point2D getCurrentPoint() throws IOException
    {
    	//System.out.println("getCurrentPoint");
        return this.currentGeneralPath.getCurrentPoint();
    }
    /**
     * Close the current path
     */
    @Override
    public void closePath() throws IOException
    {
    	//System.out.println("closePath");
        this.currentGeneralPath.closePath();
    }
    /**
     * Ends the current path without filling or stroking it. The clipping path is updated here.
     */
    @Override
    public void endPath() throws IOException
    {
    	//System.out.println("endPath");
        if (clipWindingRule != -1)
        {
            this.currentGeneralPath.setWindingRule(clipWindingRule);
            super.getGraphicsState().intersectClippingPath(this.currentGeneralPath);
            clipWindingRule = -1;
        }
        //this.currentGeneralPath.reset();
        this.generalPaths.add(this.currentGeneralPath);
        this.currentGeneralPath = new GeneralPath();
    }
    
    private void addLine(Rectangle2D lineRectangle) {
    	if (lineRectangle.getWidth() > 0) {
    		this.lines.add(lineRectangle);
    	}
    }
    /**
     * Stroke the path.
     * 
     * @throws IOException If there is an IO error while stroking the path.
     */
    @Override
    public void strokePath() throws IOException
    {
    	//System.out.println("strokePath");
        //add path line to list
        //this.currentGeneralPath.reset();
    	//this.transformGeneralPathToTextQuadrant(this.currentGeneralPath);
    	this.addLine(this.currentGeneralPath.getBounds2D());
    	this.generalPaths.add(this.currentGeneralPath);
        this.currentGeneralPath = new GeneralPath();
    }

    @Override
    public void fillPath(int windingRule) throws IOException
    {
    	//System.out.println("fillPath");
        //this.currentGeneralPath.reset();
    	this.generalPaths.add(this.currentGeneralPath);
        this.currentGeneralPath = new GeneralPath();
    }

    @Override
    public void fillAndStrokePath(int windingRule) throws IOException
    {
        this.currentGeneralPath.reset();
        this.generalPaths.add(this.currentGeneralPath);
        this.currentGeneralPath = new GeneralPath();
    }

    @Override
    public void shadingFill(COSName cosn) throws IOException
    {
    }
    
    public void processPage() throws IOException {
    	super.processPage(super.getPage());
    }
    
    public List<GeneralPath> getGeneralPathList() {
    	return this.generalPaths;
    }
    
    public void transformGeneralPathsToTextQuadrant() {
    	this.generalPaths.forEach(e -> {
    		this.transformGeneralPathToTextQuadrant(e);
    	});
    }
}