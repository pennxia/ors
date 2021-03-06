package com.lsc.ors.views.analysis;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Date;

import com.lsc.ors.beans.OutpatientLog;
import com.lsc.ors.beans.OutpatientLogCharacters;
import com.lsc.ors.debug.ConsoleOutput;
import com.lsc.ors.resource.StringSet;
import com.lsc.ors.util.DataExtractor;
import com.lsc.ors.util.TimeFormatter;

public abstract class AnalysisBoard extends Canvas{

	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = -935687137278334166L;
	public int getID(){
		return (int)serialVersionUID;
	}
	
	protected static final int WIDTH = 600;
	protected static final int HEIGHT = 400;
	
	protected static final int DIAGRAMTYPE_CHART = 0;
	protected static final int DIAGRAMTYPE_PIE = 1;
	protected static final int DIAGRAMTYPE_BAR = 2;
	protected static final int DIAGRAMTYPE_PERCENTAGE = 3;
	protected int diagramType;
	
	protected OutpatientLogCharacters[] dataList;
	/**
	 * 将响应传递给board的调用者
	 */
	protected ActionListener al = null;
	/**
	 * mouse action listener
	 */
	private BoardMouseListener bml = new BoardMouseListener();
	
	public AnalysisBoard(OutpatientLogCharacters[] logList, ActionListener listener) {
		super();
		this.dataList = logList;
		diagramType = DIAGRAMTYPE_CHART;
		al = listener;
		
		setBounds(0, 0, WIDTH, HEIGHT);
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		
		addMouseListener(bml);
		addMouseMotionListener(bml);
		addMouseWheelListener(bml);
		
		new Thread(new AnimThread()).start();
	}

	

	boolean isRepaintable = false;
	class AnimThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.currentThread().isInterrupted()){
				try {
					///debug
					if(isRepaintable){
						repaint();
					}
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	protected int startX = 0, startY = 0;
	protected int originalX = 0, originalY = 0;
	protected boolean isReleased = true; 
	class BoardMouseListener implements MouseListener,MouseMotionListener,MouseWheelListener{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			onMouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			startX = e.getX();
			startY = e.getY();
			originalX = offsetX;
			originalY = offsetY;
			isReleased =false;
			isRepaintable = true;
			onMousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			isReleased = true;
			onMouseReleased(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			onMouseExit(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			offsetX = originalX + (e.getX() - startX);
			offsetY = originalY + (e.getY() - startY);
			isRepaintable = true;
			onMouseDragged(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			onMouseMoved(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			onMouseWheel(e);
		}
		
	}
	
	protected static final int RULER_WIDTH = 20;
	protected static final int SLASH_LENGTH = 10;
	protected int offsetX = 0,offsetY = 0;
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		beforePaint();
		super.paint(g);
		if(dataList == null){
			g.drawString(StringSet.VACANT_CONTENT, WIDTH / 2 - 10, HEIGHT / 2);
		}else{
			onPaint(g);
		}
	}
	
	/**
	 * 获得总看病人数
	 * @return
	 */
	public int getTotalOutpatientNumber(){
		if(dataList == null) return -1;
		return dataList.length;
	}

	public abstract void setData(OutpatientLogCharacters[] list);
	public abstract void miningAllData();
	protected abstract void onMouseClicked(MouseEvent e);
	protected abstract void onMousePressed(MouseEvent e);
	protected abstract void onMouseReleased(MouseEvent e);
	protected abstract void onMouseExit(MouseEvent e);
	protected abstract void onMouseDragged(MouseEvent e);
	protected abstract void onMouseMoved(MouseEvent e);
	protected abstract void onMouseWheel(MouseWheelEvent e);
	protected abstract void beforePaint();
	protected abstract void onPaint(Graphics g);
	
}
