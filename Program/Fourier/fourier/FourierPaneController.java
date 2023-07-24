package fourier;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


/**
 * 
 * 
 */
public class FourierPaneController extends pane.PaneController {

	/**
	 * メニューポップアップを出すかどうか(真・偽)を保持するフィールド
	 */
	//private boolean isMenuPopuping = true;

	/**
	 * フーリエ変換の窓のコントローラー
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 */
	public FourierPaneController() {
		super();
	}

	/**
	 * アクションイベントが起きることをモデルに通知する
	 *
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {

	};

	/**
	 * マウスクリックした一をピクチャ座標にしてモデルに通知する。
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent aMouseEvent) {

		super.mouseClicked(aMouseEvent);

	}

	/**
	 * マウスドラッグをピクチャ座標にしてモデルに通知する。
	 * 
	 * 
	 * @author Tkakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	@Override
	public void mouseDragged(MouseEvent aMouseEvent) {

		super.mouseDragged(aMouseEvent);
	}

	/**
	 * マウスが動いた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void mouseMoved(MouseEvent aMouseEvent) {
		/*
		 * {
		 * try
		 * {
		 * ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
		 * PaneView aView = this.getView();
		 * aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
		 * new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new
		 * RuntimeException(); });
		 * aView.getModel().mouseMoved(aPoint.get(), aMouseEvent);
		 * }
		 * catch (RuntimeException anException) { return; }
		 * return;
		 * }
		 */
	}

	/**
	 * マウスが押し続けられた位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakkura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */

	/*
	 * public void mousePressed(MouseEvent aMouseEvent) {
	 * {
	 * if (aMouseEvent.MOUSE_DRAGGED==506) {
	 * try {
	 * ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
	 * PaneView aView = this.getView();
	 * aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
	 * new Condition(() -> aPoint.get() == null).ifTrue(() -> {
	 * throw new RuntimeException();
	 * });
	 * aView.getModel().mouseDragged(aPoint.get(), aMouseEvent);
	 * } catch (RuntimeException anException) {
	 * return;
	 * }
	 * return;
	 * }
	 * }
	 * }
	 */
	
	/**
	 * @author Takakkura,Omori,Hayami
	 * 
	 * @version 1.2
	 * 
	 * @date 2023 7/9
	 * 
	 * @param aMouseEvent
	 */
	/* public void mousePressed(MouseEvent aMouseEvent) {
		{
			try {
				ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
				PaneView aView = this.getView();
				aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
				new Condition(() -> aPoint.get() == null).ifTrue(() -> {
					throw new RuntimeException();
				});
				this.previous = aPoint;
			} catch (RuntimeException anException) {
				return;
			}
			return;
		}
	} */

	/**
	 * マウスが押し続けられていたのが離された位置をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	/*
	 * public void mouseReleased(MouseEvent aMouseEvent) {
	 * {
	 * try
	 * {
	 * ValueHolder<Point> aPoint = new ValueHolder<Point>(aMouseEvent.getPoint());
	 * PaneView aView = this.getView();
	 * aPoint.set(aView.convertViewPointToPicturePoint(aPoint.get()));
	 * new Condition(() -> aPoint.get() == null).ifTrue(() -> { throw new
	 * RuntimeException(); });
	 * aView.getModel().mouseReleased(aPoint.get(), aMouseEvent);
	 * }
	 * catch (RuntimeException anException) { return; }
	 * return;
	 * }
	 * 
	 * }
	 */

	/**
	 * ポップアップメニューが出される場所をピクチャ座標にしてモデルに通知する
	 * 
	 * @author Takakura,Omori
	 * @version
	 * @date
	 * @param aMouseEvent
	 */
	public void showPopupMenu(MouseEvent aMouseEvent) {

	}

}
