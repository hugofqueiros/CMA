package org.projfinal.cma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import jssc.SerialPortList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class RealTimeChart extends ChartPanel implements Runnable {

    private static final long serialVersionUID = 1L;

    private static TimeSeries micTimeSeries;
    public MySerialPortJSSC port;

    static JFreeChart chart;
    static XYPlot plot;

    public static final String NEW_LINE = System.getProperty("line.separator");

    public RealTimeChart(String title) {
	super(createChart(title));
	super.setMouseWheelEnabled(true);
	super.setFillZoomRectangle(true);

	MySerialPortJSSC myserialPorttest = new MySerialPortJSSC();
	myserialPorttest.serialNativeInterface();
	String portName1 = null;

	// get computer serial ports names
	String[] portNames = SerialPortList.getPortNames();
	for (String port : portNames) {
	    System.out.println("Main port Names: " + port);
	    portName1 = port;
	}

	if (portName1 != null) {

	    if (portName1.equals("/dev/ttyS0"))
		portName1 = "/dev/ttyACM0";

	    port = new MySerialPortJSSC(portName1);
	} else {
	    System.out.println("Couldn't find any port.");
	}

    }

    private static JFreeChart createChart(String title) {
	micTimeSeries = new TimeSeries("Phonogram value");

	TimeSeriesCollection phonogramDataSet = new TimeSeriesCollection();
	phonogramDataSet.addSeries(micTimeSeries);

	chart = ChartFactory.createTimeSeriesChart(title, "Time(s)", // x-axis
		// label
		"Amplitude Value", // y-axis label
		phonogramDataSet, // data
		true, // create legend
		true, // create tooltips
		false); // create urls

	chart.setBackgroundPaint(Color.WHITE);
	plot = chart.getXYPlot();
	plot.setBackgroundPaint(Color.LIGHT_GRAY);
	plot.setDomainGridlinePaint(Color.WHITE);
	plot.setRangeGridlinePaint(Color.BLACK);
	plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	plot.setDomainCrosshairVisible(true);
	plot.setRangeCrosshairVisible(true);

	ValueAxis valueaxis = plot.getDomainAxis();
	// valueaxis.setAutoRange(true);
	valueaxis.setFixedAutoRange(3000D);
	// valueaxis = plot.getRangeAxis();
	valueaxis.setAutoRange(true);

	return chart;
    }

    public long map(long inputValue, long in_min, long in_max, long out_min,
	    long out_max) {
	return (inputValue - in_min) * (out_max - out_min) / (in_max - in_min)
		+ out_min;
    }

    private Float map(Float inputValue, int in_min, int in_max, int out_min,
	    int out_max) {
	return (inputValue - in_min) * (out_max - out_min) / (in_max - in_min)
		+ out_min;
    }

    @Override
    public synchronized void run() {
	while (true) {
	    try {
		String data = port.getData();
		if (data != null) {
		    data.trim();
		    String[] data1 = data.split("\r");
		    if (data1.length < 2) {
			//System.out.println("data: " + data);
			Float value = Float.parseFloat(data);
			value = map(value, 100, 999, 0, 5);
			if (value != 0) {
			    micTimeSeries.addOrUpdate(new Millisecond(), value);
			    chart.fireChartChanged();
			}
		    }

		}
		Thread.sleep(1);
	    } catch (NullPointerException | InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	JFrame frame = new JFrame("Phonogram program");
	final RealTimeChart rtcp = new RealTimeChart("Phonogram");
	frame.getContentPane().add(rtcp, BorderLayout.CENTER);
	frame.pack();
	frame.setVisible(true);
	try {
	    Thread.sleep(4000); // it should be 2000
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	(new Thread(rtcp)).start();
	frame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent windowevent) {
		rtcp.port.close();
		System.exit(0);
	    }
	});
    }
}
