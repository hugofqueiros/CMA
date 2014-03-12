package org.projfinal.cma;

import java.nio.charset.StandardCharsets;

import jssc.SerialNativeInterface;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortTimeoutException;

public class MySerialPortJSSC implements SerialPortEventListener {
    private SerialPort serialPort;
    private String portID;

    private String data = null;
    private Float value = 0f;

    private int osType;

    private byte[] dataByte = new byte[8];
    private int byteCount = 0;

    private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
	// "/dev/ttyUSB0",
	"/dev/ttyACM0", // Linux
	"COM3", // Windows
    };

    public enum SerialMode {
	START, PAUSE, BREAK, STOP
    }

    SerialMode serialMode = SerialMode.STOP;

    public MySerialPortJSSC() {

    }

    public MySerialPortJSSC(String port) {

	System.out.println("Port to be opened: " + port);
	open(port);
	Thread t = new Thread() {
	    @Override
	    public void run() {
		try {
		    Thread.sleep(1000000);
		} catch (InterruptedException ie) {
		    System.out.println("InterrupedException");
		    ie.printStackTrace();
		}
	    }
	};
	t.start();
	System.out.println("Serial Comms Started");
	if (!t.isAlive()) {
	    close();
	}
    }

    public void serialNativeInterface() {
	System.out.println("Lib base version: "
		+ SerialNativeInterface.getLibraryBaseVersion());
	System.out.println("OS type: " + SerialNativeInterface.getOsType());
	osType = SerialNativeInterface.getOsType();
	switch (osType) {
	case SerialNativeInterface.OS_WINDOWS:
	    System.out.println("Windows OS type");
	    break;
	case SerialNativeInterface.OS_LINUX:
	    System.out.println("Linux OS type.");
	    break;
	case SerialNativeInterface.OS_MAC_OS_X:
	    System.out.println("Mac OS type.");
	    break;
	case SerialNativeInterface.OS_SOLARIS:
	    System.out.println("Solaris OS type.");
	    break;
	}
    }

    public void find(String s) {

	String[] portlist = SerialPortList.getPortNames();

	for (String port : portlist) {
	    System.out.println("Port name: " + port);
	    if (port.equals(s)) {
		portID = port;
		System.out.println("Port Id is: " + portID);
	    }
	}
    }

    public void open(String s) {
	serialNativeInterface();
	find(s);
	try {
	    serialPort = new SerialPort(s);
	    serialPort.openPort();
	    // Arduino :
	    // An optional second argument configures the data, parity, and stop
	    // bits. The default is 8 data bits, no parity, one stop bit.
	    // serialPort.setParams(9600, 8, 1, 0);//Set params
	    serialPort.setParams(SerialPort.BAUDRATE_115200,
		    SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
		    SerialPort.PARITY_NONE);
	    // Preparing a mask. In a mask, we need to specify the types of
	    // events that we want to track.
	    // Well, for example, we need to know what came some data, thus in
	    // the mask must have the
	    // following value: MASK_RXCHAR. If we, for example, still need to
	    // know about changes in states
	    // of lines CTS and DSR, the mask has to look like this:
	    // SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS +
	    // SerialPort.MASK_DSR
	    int mask = SerialPort.MASK_RXCHAR; // + SerialPort.MASK_CTS +
	    // SerialPort.MASK_DSR;
	    // Set the prepared mask
	    serialPort.setEventsMask(mask);
	    serialPort.addEventListener(this);

	} catch (SerialPortException spe) {
	    spe.printStackTrace();
	}
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent event) {
	switch (event.getEventType()) {
	case SerialPortEvent.BREAK:
	    System.out.println("SerialPortEvent.BREAK");
	    break;
	case SerialPortEvent.CTS:
	    System.out.println("SerialPortEvent.CTS");
	    if (event.getEventValue() == 1) { // if line is ON
		System.out.println("CTS - ON");
	    } else {
		System.out.println("CTS - OFF");
	    }
	    break;
	case SerialPortEvent.DSR:
	    System.out.println("SerialPortEvent.DSR");
	    if (event.getEventValue() == 1) { // if DSR line has changed state
		System.out.println("DSR - ON");
	    } else {
		System.out.println("DRS - OFF");
	    }
	    break;
	case SerialPortEvent.ERR:
	    System.out.println("SerialPortEvent.ERR");
	    break;
	case SerialPortEvent.RING:
	    System.out.println("SerialPortEvent.RING");
	    break;
	case SerialPortEvent.RLSD:
	    System.out.println("SerialPortEvent.RLSD");
	    break;
	case SerialPortEvent.RXCHAR:
	    byte[] bd = new byte[4];

	    byte[] temp = null;

	    try {
		temp = serialPort.readBytes(1, 1000);
	    } catch (SerialPortException | SerialPortTimeoutException e) {
		e.printStackTrace();
	    }
	    String tempStr = new String(temp, StandardCharsets.US_ASCII);
	    if (tempStr.equals("\n")) {
		try {
		    bd = serialPort.readBytes(3, 1000);
		    data = new String(bd, StandardCharsets.US_ASCII);
		} catch (SerialPortException | SerialPortTimeoutException e1) {
		    e1.printStackTrace();
		}
	    }
	    break;
	case SerialPortEvent.RXFLAG:
	    System.out.println("SerialPortEvent.RXFLAG");
	    break;
	case SerialPortEvent.TXEMPTY:
	    System.out.println("SerialPortEvent.TXEMPTY");
	    break;
	}
    }

    public String getData() {
	return data;
    }

    public Float getValue() {
	return value;
    }

    public synchronized void close() {
	try {
	    if (serialPort != null) {
		serialPort.removeEventListener();
		serialPort.closePort();
	    }
	} catch (SerialPortException e) {
	    e.printStackTrace();
	}
    }

    public void sendStart() {
	if (serialMode != SerialMode.START) {
	    byte start = 1;
	    try {
		serialPort.writeByte(start);
		serialMode = SerialMode.START;
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    }
	}
    }

    public void sendBreak() {
	if (serialMode != SerialMode.BREAK) {
	    try {
		serialPort.sendBreak(2000);
		serialMode = SerialMode.BREAK;
		data = "100";
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    }
	}
    }

    public void sendPause() {
	if (serialMode != SerialMode.PAUSE) {
	    byte pause = 2;
	    try {
		serialPort.writeByte(pause);
		serialMode = SerialMode.PAUSE;
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    }
	}
    }

    public void sendStop() {
	if (serialMode != SerialMode.STOP) {
	    byte stop = 0;
	    try {
		serialPort.writeByte(stop);
		serialMode = SerialMode.STOP;
	    } catch (SerialPortException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void main(String[] args) {
	MySerialPortJSSC myserialPorttest = new MySerialPortJSSC();
	myserialPorttest.serialNativeInterface();

	String portName = null;

	// get computer serial ports names
	String[] portNames = SerialPortList.getPortNames();
	for (String port : portNames) {
	    System.out.println("Main port Names: " + port);
	    portName = port;
	}

	if (portName.equals("/dev/ttyS0"))
	    portName = "/dev/ttyACM0";

	if (portName != null) {
	    MySerialPortJSSC serial = new MySerialPortJSSC(portName);
	}
    }
}
