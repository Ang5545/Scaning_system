package ru.ang5545;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

import org.bytedeco.javacpp.opencv_core.IplImage;

import ru.ang5545.cam_acces.DeviceManager;
import ru.ang5545.gui.ShotingPanel;
import ru.ang5545.image_processing.ImageHandler;
import ru.ang5545.image_processing.ImageHelper;
import ru.ang5545.image_processing.ImageLoader;

public class App {
	
	
	public static final String FRAME_NAME = "Scaning system";
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 350;

	private static final int SHOTING_PAN_HEIGHT = 300;
	private static final int SHOTING_PAN_WIDTH = 300;
	
	public static final String START_GRUB_BTT 	= "Start work";
	public static final String STOP_GRUB_BTT 	= "Stop work";
	public static final String BACK_SHOT_BTT 	= "Take background";
	
	public static final String BACK_PANEL_NAME 	= "Background";
	public static final String OBJ_PANEL_NAME 	= "Object";
	
	
	// ~ Swing components ========================================================================================================
	
	private JFrame jFrame;
	private JButton startGrab;
	private JButton stopGrab;
	private JButton backShoot;
	
	
	// ~ GUI components ==========================================================================================================
	
	private ShotingPanel backPanel;
	private ShotingPanel objPanel;
	
	
	// ~ other components =========================================================================================================	
	
	private DeviceManager devManager;
	private AnswerWorker anWorker;
	//private Grabber grabber;
	private ImageLoader grabber;
	private ImageHandler imHandler;
	
	
	private App() {
		// ---- init jframe ----
		this.jFrame = new JFrame(FRAME_NAME);
		this.jFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.jFrame.setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.jFrame.setResizable(false);
		this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jFrame.getContentPane().setLayout(new BorderLayout());
		
		// ---- init gui components ----
		this.startGrab = new JButton(START_GRUB_BTT);
		this.startGrab.addActionListener(new StartGrub());
		this.stopGrab = new JButton(STOP_GRUB_BTT);
		this.stopGrab.addActionListener(new StopGrub());
		this.backShoot = new JButton(BACK_SHOT_BTT);
		this.backShoot.addActionListener(new TakeBackground());
		
		JPanel buttons = new JPanel();
		buttons.add(startGrab);
		buttons.add(stopGrab);
		buttons.add(backShoot);
		this.jFrame.add(buttons, BorderLayout.PAGE_START);
		
		this.backPanel = new ShotingPanel(BACK_PANEL_NAME,SHOTING_PAN_HEIGHT, SHOTING_PAN_WIDTH);
		this.objPanel = new ShotingPanel(OBJ_PANEL_NAME, SHOTING_PAN_HEIGHT, SHOTING_PAN_WIDTH);
		this.jFrame.add(backPanel, BorderLayout.LINE_START);
		this.jFrame.add(objPanel, BorderLayout.LINE_END);
		
		// ---- init components ----
		this.devManager = new DeviceManager();
		
		
	}
	
	public void show() {
		jFrame.setVisible(true);
	}
	

	
	class AnswerWorker extends SwingWorker<String, Object> {
		private boolean doIt = true;
		protected String doInBackground() throws Exception {
			while(doIt) {
				
				imHandler.processImage(grabber.grab());
				backPanel.setImage(imHandler.getBackImage());
				objPanel.setImage(imHandler.getObjImage());
//				
				imHandler.release();
//				grabbedImage = grabber.grab();
//				if (!backIsShoting) {
//					backPanel.setImage(ImageHelper.getBufferedImage(grabbedImage));
//				} else {
//					objImage = grabbedImage;
//					objPanel.setImage(ImageHelper.getBufferedImage(objImage));
//					
//				}
			}
			return "succes";
		}
		protected void done() {
			try {
				doIt = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class StartGrub implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//grabber = new Grabber(dm.getCamIndex());
			grabber = new ImageLoader();
			imHandler = new ImageHandler(grabber.getResolution());
			anWorker = new AnswerWorker();
			anWorker.execute();
		}
	}
	
	private class StopGrub implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			anWorker.cancel(true);
			//grabber.stopGrub();
			//ih.release();
		}
	}	
	
	private class TakeBackground implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			imHandler.saveBackImage();
		}
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				App app = new App();
				app.show();
             }
        });
	}
	
}
