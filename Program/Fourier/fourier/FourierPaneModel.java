package fourier;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;


import utility.ImageUtility;

/**
 * 
 */

public class FourierPaneModel extends pane.PaneModel {

	/**
	 * 4つのウィンドウのどれかを判別するラベル
	 */
	private String label = "";

	/**
	 * 自身を作成したFourierModelを持つ
	 * Fourier1d or Fourier2d
	 */
	private FourierModel listener = null;

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 */
	public FourierPaneModel() {
		super();
		return;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 */
	public FourierPaneModel(String aString) {
		super();
		BufferedImage anImage = ImageUtility.readImage(aString);
		this.picture(anImage);
		return;
	}

	/**
	 * *
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anlmage
	 * @param aString
	 */
	public FourierPaneModel(BufferedImage anImage, String aString) {
		super(aString);
		this.picture(anImage);
		return;
		/*
		 * super();
		 * FourierPaneModel aModel = new FourierPaneModel();
		 * FourierPaneView aView = new FourierPaneView(aModel, new
		 * FourierPaneController());
		 * aPanel.add(aView);
		 */
	}

	/**
	 * 
	 * 
	 * @author 
	 * @version
	 * @date
	 * @param aString
	 * @param anlmage
	 * @param aString
	 * @param aModel
	 */
	public FourierPaneModel(BufferedImage anImage, String aString, FourierModel aModel) {
		this.label = aString;
		this.picture(anImage);// anlmage を処理するなどの追加の初期化処理を行う
		this.listener = aModel; // フィールドに渡された FourierModel を設定する
		return;
	}

	/**
	 *
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aString
	 * @param anActonEvent
	 */
	public void actionPerformed(ActionEvent anActonEvent){
		
	}

	/**
	 * 現在のユーザからインタラクションを受け付ける状態かどうかを返す。
	 * 
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public boolean isInteractive() {
		if(this.label.equals("interactivePowerSpectrum")){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public boolean isNotInteractive() {
		return false;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @return
	 */
	public String label() {
		return this.label;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		FourierPaneController aController=new FourierPaneController();
		if(aMouseEvent.getButton()==MouseEvent.BUTTON1){
			this.listener.mouseClicked(aPoint, aMouseEvent);
		}
		if(aMouseEvent.getButton()==MouseEvent.BUTTON3){
			this.showPopupMenu(aMouseEvent, aController);
		}
		return;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aPoint
	 * @param aMouseEvent
	 */
	
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		if(this.isInteractive()){
			listener.mouseDragged(aPoint, aMouseEvent);
		}
		return;
	}

	/**
	 * 
	 * 
	 * @author
	 * @version
	 * @date
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		listener.showPopupMenu(aMouseEvent, aController);
	}
}
