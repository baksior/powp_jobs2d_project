package edu.kis.powp.jobs2d;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.ConfigurableLine;
import edu.kis.powp.jobs2d.drivers.adapter.DrawerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectCommandSquareOptionListener;
import edu.kis.powp.jobs2d.events.SelectCommandTriangleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestJaneFigureOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestSecondFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());
		SelectTestSecondFigureOptionListener selectTestSecondFigureOptionListener = new SelectTestSecondFigureOptionListener(
				DriverFeature.getDriverManager());
		SelectTestJaneFigureOptionListener selectTestJaneFigureOptionListener = new SelectTestJaneFigureOptionListener(
				DriverFeature.getDriverManager());
		SelectCommandTriangleOptionListener triangle  =
				new SelectCommandTriangleOptionListener(DriverFeature.getDriverManager());
		SelectCommandSquareOptionListener square  =
				new SelectCommandSquareOptionListener(DriverFeature.getDriverManager());

		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
		application.addTest("Figure Joe 2", selectTestSecondFigureOptionListener);
		application.addTest("Figure Jane", selectTestJaneFigureOptionListener);
		application.addTest("Triangle", triangle);
		application.addTest("Square", square);
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawerAdapter();
		DriverFeature.addDriver("Buggy Simulator", testDriver);
		
		Job2dDriver testDriver2 = new LineDrawerAdapter(LineFactory.getSpecialLine());
		DriverFeature.addDriver("Special Line", testDriver2);
		
		Job2dDriver testDriver3 = new LineDrawerAdapter(LineFactory.getDottedLine());
		DriverFeature.addDriver("Dotted Line", testDriver3);
		
		Job2dDriver testDriver4 = new LineDrawerAdapter(new ConfigurableLine(Color.RED, 9.0f, false));
		DriverFeature.addDriver("Configurable Line test", testDriver4);
		
		Job2dDriver testDriver5 = new FiguresJaneDriver(0, 0, new ConfigurableLine(Color.GREEN, 4.0f, true));
		DriverFeature.addDriver("Jane Driver", testDriver5);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
