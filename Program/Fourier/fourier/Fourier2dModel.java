package fourier;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

/**
 *
 */
public class Fourier2dModel extends FourierModel {

	private int previousX;
	private int previousY;
	/**
	 *
	 */
	protected double[][] sourceData;

	/**
	 *
	 */
	protected double[][] realPart;

	/**
	 *
	 */
	protected double[][] imaginaryPart;

	/**
	 *
	 */
	protected double[][] powerSpectrum;

	/**
	 *
	 */
	protected double[][] interactiveRealPart;

	/**
	 *
	 */
	protected double[][] interactiveImarginaryPart;

	/**
	 *
	 */
	protected double[][] interactivePowerSpectrum;

	/**
	 *
	 */
	protected double[][] inverseData;

	/**
	 *
	 */
	protected FourierPaneModel sourceDataPaneModel = null;

	/**
	 *
	 */
	protected FourierPaneModel powerSpectrumPaneModel = null;

	/**
	 *
	 */
	protected FourierPaneModel interactivePowerSpectrumPaneModel = null;

	/**
	 *
	 */

	protected FourierPaneModel interactiveDataPaneModel = null;
	/**
	 *
	 */
	double[][][] yuvSourceData;

	double[][][] interactiveYuvSourceData;

	double[][] inverseDataU;
	double[][] inverseDataV;

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 */
	public Fourier2dModel() {
		super();
		this.setSourceData(FourierModel.dataFourierGrayScale(), yuvSourceData);

	}

	/**
	 *
	 *
	 * @author Nakamura Hayami
	 * @version 1.2
	 * @date 7/15
	 * @param anActionEvent
	 */
	public void actionPerformed(ActionEvent anActionEvent) {
		if (yuvSourceData == null) {
			sourceDataPaneModel.picture(FourierModel.generateImageForData(sourceData));
			DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
			powerSpectrumPaneModel.picture(FourierModel.generateImageForData(aTransformation.swap(powerSpectrum)));
			interactivePowerSpectrumPaneModel
					.picture(FourierModel.generateImageForData(aTransformation.swap(interactivePowerSpectrum)));
			computeInverseData();
			interactiveDataPaneModel.picture(FourierModel.generateImageForData(inverseData));
		} else {
			sourceDataPaneModel.picture(FourierModel.generateImageForData(yuvSourceData));
			DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(yuvSourceData[0]);
			powerSpectrumPaneModel.picture(FourierModel.generateImageForData(aTransformation.swap(powerSpectrum)));
			interactivePowerSpectrumPaneModel
					.picture(FourierModel.generateImageForData(aTransformation.swap(interactivePowerSpectrum)));
			computeInverseData();
			if (FourierModel.isAllZero(inverseData) == true) {
				double[][][] yuvInverseMatrixes = new double[][][] { inverseData,
						inverseData, inverseData };
				interactiveDataPaneModel.picture(FourierModel.generateImageForData(yuvInverseMatrixes));
			} else {
				double[][][] yuvInverseMatrixes = new double[][][] { inverseData,
						yuvSourceData[1], yuvSourceData[2] };
				interactiveDataPaneModel.picture(FourierModel.generateImageForData(yuvInverseMatrixes));
			}

		}

		sourceDataPaneModel.changed();
		powerSpectrumPaneModel.changed();
		interactiveDataPaneModel.changed();
		interactivePowerSpectrumPaneModel.changed();
	}

	/**
	 *
	 *
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/15
	 * @param aPoint
	 * @param isAltDown
	 */
	public void computeFromPoint(Point aPoint, boolean isAltDown) {
		int x = (int) aPoint.getX();
		int y = (int) aPoint.getY();

		int[] xySwap = int2dSwap(x, y, powerSpectrum[0].length);
		int swapX = xySwap[0];
		int swapY = xySwap[1];
		xySwap = int2dSwap(previousX, previousY, powerSpectrum[0].length);
		int swapPreviousX = xySwap[0];
		int swapPreviousY = xySwap[1];

		if (previousX == -1 || previousY == -1 || Math.abs(swapX -
				swapPreviousX) > 30 || Math.abs(swapY - swapPreviousY) > 30) {
			previousX = x;
			previousY = y;
		}

		if (isAltDown) {
			if (previousX >= x && previousY >= y) {
				x = x - 5;
				y = y - 5;
				previousX = previousX + 5;
				previousY = previousY + 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}
			} else if (previousX >= x && previousY < y) {
				x = x - 5;
				y = y + 5;
				previousX = previousX + 5;
				previousY = previousY - 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}

				}
			} else if (previousX < x && previousY >= y) {
				x = x + 5;
				y = y - 5;
				previousX = previousX - 5;
				previousY = previousY + 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}
			} else if (previousX < x && previousY < y) {
				x = x + 5;
				y = y + 5;
				previousX = previousX - 5;
				previousY = previousY - 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = 0.0;
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = 0.0;
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = 0.0;
					}
				}

			}

			else {
				interactiveRealPart[y][x] = 0.0;
				interactivePowerSpectrum[y][x] = 0.0;
				interactivePowerSpectrum[y][x] = 0.0;
				previousX = -1;
				previousY = -1;
			}
			return;
		} else {

			if (previousX >= x && previousY >= y) {
				x = x - 5;
				y = y - 5;
				previousX = previousX + 5;
				previousY = previousY + 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}
			} else if (previousX >= x && previousY < y) {
				x = x - 5;
				y = y + 5;
				previousX = previousX + 5;
				previousY = previousY - 5;

				for (int j = x; j <= previousX; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}

				}
			} else if (previousX < x && previousY >= y) {
				x = x + 5;
				y = y - 5;
				previousX = previousX - 5;
				previousY = previousY + 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = y; i <= previousY; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}
			} else if (previousX < x && previousY < y) {
				x = x + 5;
				y = y + 5;
				previousX = previousX - 5;
				previousY = previousY - 5;

				for (int j = previousX; j <= x; j++) {
					for (int i = previousY; i <= y; i++) {
						xySwap = int2dSwap(j, i, powerSpectrum[0].length);
						interactiveRealPart[xySwap[1]][xySwap[0]] = realPart[xySwap[1]][xySwap[0]];
						interactiveImarginaryPart[xySwap[1]][xySwap[0]] = imaginaryPart[xySwap[1]][xySwap[0]];
						interactivePowerSpectrum[xySwap[1]][xySwap[0]] = powerSpectrum[xySwap[1]][xySwap[0]];
					}
				}

			}

			else {
				interactiveRealPart[y][x] = realPart[y][x];
				interactivePowerSpectrum[y][x] = powerSpectrum[y][x];
				interactivePowerSpectrum[y][x] = powerSpectrum[y][x];
				previousX = -1;
				previousY = -1;
			}
			return;
		}
	}

	/**
	 *
	 *
	 * @author Nakamura
	 * @version 1.0
	 * @date 7/8
	 */
	public void computeInverseData() {
		// マウス操作後呼び出される？
		// 復元画像の処理(逆変換)を行う？

		DiscreteFourier2dTransformation anInverseTransform = new DiscreteFourier2dTransformation(interactiveRealPart,
				interactiveImarginaryPart);
		double[][] inverseRealPart = anInverseTransform.inverseRealPart();
		// double[][] inverseImaginaryPart = anInverseTransform.inverseImaginaryPart();
		inverseData = inverseRealPart;
		return;
	}

	/**
	 *
	 *
	 * @author Nakamura
	 * @version 1.1
	 * @date 7/15
	 */
	public void doAllSpectrum() {
		interactivePowerSpectrum = powerSpectrum;
		interactiveRealPart = realPart;
		interactiveImarginaryPart = imaginaryPart;
		interactiveYuvSourceData = yuvSourceData;
		DiscreteFourier2dTransformation anInverseTransform = new DiscreteFourier2dTransformation(interactiveRealPart,
				interactiveImarginaryPart);
		double[][] inverseRealPart = anInverseTransform.inverseRealPart();
		// double[][] inverseImaginaryPart = anInverseTransform.inverseImaginaryPart();
		inverseData = inverseRealPart;
		this.actionPerformed(null);
	}

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 */
	public void doClearSpectrum() {
		if (yuvSourceData == null) {
			this.setSourceData(sourceData, null);
		} else {
			this.setSourceData(yuvSourceData[0], yuvSourceData);
		}
		this.actionPerformed(null);
	}

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 */
	public void doFourierColor() {
		double[][][] yuvMatrixes = FourierModel.dataFourierColor();
		this.setSourceData(yuvMatrixes[0], yuvMatrixes);
		this.actionPerformed(null);
		return;
	}

	/**
	 *
	 *
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/15
	 */
	public void doFourierGrayScale() {
		sourceData = FourierModel.dataFourierGrayScale();
		yuvSourceData = null;
		this.setSourceData(sourceData, null);
		this.actionPerformed(null);
		return;

	}

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseClicked(Point aPoint, MouseEvent aMouseEvent) {
		//int x = aPoint.x;
		//int y = aPoint.y;
		// System.out.printf("x = %d, y = %d", x, y);
		computeFromPoint(aPoint, aMouseEvent.isAltDown());
		// Alt(option)が押されると消すモード、逆は書くモード

		this.actionPerformed(null);
		return;
	}

	/**
	 *
	 *
	 * @author Takakura,Nakamura
	 * @version 1.1
	 * @date 7/14
	 * @param aPoint
	 * @param aMouseEvent
	 */
	public void mouseDragged(Point aPoint, MouseEvent aMouseEvent) {
		computeFromPoint(aPoint, aMouseEvent.isAltDown());
		// Alt(option)が押されると消すモード、逆は書くモード

		this.actionPerformed(null);
		previousX = (int) aPoint.getX();
		previousY = (int) aPoint.getY();
		return;
	}

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 */
	public void open() {
		GridLayout aLayout = new GridLayout(2, 2);
		JPanel aPanel = new JPanel(aLayout);
		Dimension aDimension = new Dimension();
		aDimension.width = sourceData[0].length;
		aDimension.height = sourceData[1].length;
		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		BufferedImage imageSourceData = FourierModel.generateImageForData(sourceData);
		this.sourceDataPaneModel = new FourierPaneModel(imageSourceData, "sourceData", this);
		FourierPaneView aView = new FourierPaneView(sourceDataPaneModel, new FourierPaneController());
		this.sourceDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imagePowerSpectrum = FourierModel.generateImageForData(aTransformation.swap(powerSpectrum));
		this.powerSpectrumPaneModel = new FourierPaneModel(imagePowerSpectrum, "powerSpectrum", this);
		aView = new FourierPaneView(powerSpectrumPaneModel, new FourierPaneController());
		this.powerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imageInverseData = FourierModel.generateImageForData(inverseData);
		this.interactiveDataPaneModel = new FourierPaneModel(imageInverseData, "inverseData", this);
		aView = new FourierPaneView(interactiveDataPaneModel, new FourierPaneController());
		this.interactiveDataPaneModel.addDependent(aView);
		aPanel.add(aView);

		BufferedImage imageinteractivePowerSpectrum = FourierModel
				.generateImageForData(aTransformation.swap(interactivePowerSpectrum));
		this.interactivePowerSpectrumPaneModel = new FourierPaneModel(imageinteractivePowerSpectrum,
				"interactivePowerSpectrum", this);
		aView = new FourierPaneView(interactivePowerSpectrumPaneModel, new FourierPaneController());
		this.interactivePowerSpectrumPaneModel.addDependent(aView);
		aPanel.add(aView);

		Example2d.open(aPanel, aDimension);

	}

	/**
	 *
	 *
	 * @author Takakura,Hayami,Nakamura
	 * @version 1.1
	 * @date 7/14
	 * @param sourceDataMatrix
	 * @param yuvMatrixes
	 */
	public void setSourceData(double[][] sourceDataMatrix, double[][][] yuvMatrixes) {
		if (yuvMatrixes == null) {
			this.sourceData = sourceDataMatrix;
		} else {
			this.sourceData = yuvMatrixes[0];
			this.yuvSourceData = yuvMatrixes;
		}

		DiscreteFourier2dTransformation aTransformation = new DiscreteFourier2dTransformation(sourceData);
		this.realPart = aTransformation.realPart();
		this.imaginaryPart = aTransformation.imaginaryPart();
		this.powerSpectrum = aTransformation.normalizedLogarithmicPowerSpectrum();

		interactiveRealPart = Arrays.stream(realPart)
				.map(double[]::clone)
				.toArray(double[][]::new);
		Arrays.stream(interactiveRealPart).forEach(val -> Arrays.fill(val, 0.0));
		interactiveImarginaryPart = Arrays.stream(imaginaryPart)
				.map(double[]::clone)
				.toArray(double[][]::new);
		Arrays.stream(interactiveImarginaryPart).forEach(val -> Arrays.fill(val, 0.0));
		interactivePowerSpectrum = Arrays.stream(powerSpectrum)
				.map(double[]::clone)
				.toArray(double[][]::new);
		Arrays.stream(interactivePowerSpectrum).forEach(val -> Arrays.fill(val, 0.0));
		inverseData = Arrays.stream(sourceDataMatrix)
				.map(double[]::clone)
				.toArray(double[][]::new);
		Arrays.stream(inverseData).forEach(val -> Arrays.fill(val, 0.0));

		if (yuvSourceData != null) {
			interactiveYuvSourceData = new double[yuvSourceData[0].length][yuvSourceData[1].length][yuvSourceData[2].length];
			for (int i = 0; i <= 2; i++) {
				interactiveYuvSourceData[i] = new double[yuvSourceData[1].length][yuvSourceData[2].length];
				for (int j = 0; j < yuvSourceData[2].length; j++) {
					Arrays.fill(interactiveYuvSourceData[i][j], 0.0);
				}
			}
		}
		previousX = -1;
		previousY = -1;

		return;
	}

	/**
	 *
	 *
	 * @author Takakura
	 * @version
	 * @date
	 * @param aMouseEvent
	 * @param aController
	 */
	public void showPopupMenu(MouseEvent aMouseEvent, FourierPaneController aController) {
		JPopupMenu popupMenu = new JPopupMenu();
		// 項目の作成と追加
		JMenuItem allSpectrumItem = new JMenuItem();
		Runnable allSpectrumRunner = () -> {
			doAllSpectrum();
		};
		allSpectrumItem.setAction(new Method2dAction("all Spectrum", allSpectrumRunner));

		JMenuItem clearSpectrumItem = new JMenuItem();
		Runnable clearSpectrumRunner = () -> {
			doClearSpectrum();
		};
		clearSpectrumItem.setAction(new Method2dAction("clear Spectrum", clearSpectrumRunner));

		JMenuItem fourierColorItem = new JMenuItem();
		Runnable fourierColorRunner = () -> {
			doFourierColor();
		};
		fourierColorItem.setAction(new Method2dAction("fourier Color", fourierColorRunner));

		JMenuItem fourierGrayScaleItem = new JMenuItem();
		Runnable fourierGrayScaleRunner = () -> {
			doFourierGrayScale();
		};
		fourierGrayScaleItem.setAction(new Method2dAction("fourier Gray Scale", fourierGrayScaleRunner));

		popupMenu.add(fourierColorItem);
		popupMenu.add(fourierGrayScaleItem);

		popupMenu.addSeparator();

		popupMenu.add(allSpectrumItem);
		popupMenu.add(clearSpectrumItem);

		popupMenu.show((JComponent) aMouseEvent.getSource(), aMouseEvent.getX(), aMouseEvent.getY());
	}

	/**
	 * 
	 * @author Nakamura
	 * @version 1.1
	 * @date 7/15
	 * @param swapX
	 * @param swapY
	 * @param length
	 * @return
	 */
	public int[] int2dSwap(int swapX, int swapY, int length) {
		if (swapX < 0)
			swapX = 0;
		if (swapY < 0)
			swapY = 0;
		if (length < swapX)
			swapX = length;
		if (length < swapY)
			swapY = length;
		int halfLength = length / 2;
		int aValue[] = new int[2];
		if (swapX < halfLength && swapY < halfLength) {

			aValue[0] = swapX + halfLength;
			aValue[1] = swapY + halfLength;
		} else if (swapX < halfLength && swapY >= halfLength) {
			aValue[0] = swapX + halfLength;
			aValue[1] = swapY - halfLength;
		} else if (swapX >= halfLength && swapY < halfLength) {
			aValue[0] = swapX - halfLength;
			aValue[1] = swapY + halfLength;
		} else {
			aValue[0] = swapX - halfLength;
			aValue[1] = swapY - halfLength;
		}
		return aValue;
	}

}

@SuppressWarnings("serial")
class Method2dAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private Runnable handleCallback = null;

	public Method2dAction(String actionName, Runnable handleCallback) {
		super(actionName);
		this.handleCallback = handleCallback;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.handleCallback.run();
	}

}