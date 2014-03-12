//package org.projfinal.cma;
//
//import gnu.io.CommPortIdentifier;
//import gnu.io.SerialPort;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.Enumeration;
//
//public class mySerialPortRXTX implements SerialPortEventListener {
//	
//	SerialPort serialPort;
//	
//	private static final String PORT_NAMES[] = {
//        "/dev/tty.usbserial-A9007UX1", // Mac OS X
//        //"/dev/ttyUSB0", // Linux
//        "/dev/ttyACM0",
//        "COM3", // Windows		
//	};
//	
//	private BufferedReader input;
//	private static OutputStream output;
//	private static final int TIME_OUT = 2000;
//	private static final int BAUD_RATE = 9600;
//	
//	public mySerialPortRXTX(String ncom) {
////		if(Integer.parseInt(ncom)>=3 && Integer.parseInt(ncom)<=9)
////            PORT_NAMES[2] = "COM" + ncom;
//        initialize();
//        Thread t=new Thread() {
//            public void run() {
//                try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
//            }
//        };
//        t.start();
//        System.out.println("Serial Comms Started");
//	}
//
//	public void initialize() {
//		CommPortIdentifier portId = null;
////		try {
////			portId = CommPortIdentifier.getPortIdentifier("/dev/tty/ACM0");
////		} catch (NoSuchPortException e1) {
////			// TODO Auto-generated catch block
////			e1.printStackTrace();
////		}
//		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
//		
//		while (portEnum.hasMoreElements()) {
//			
//			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
//			System.out.println("Port id: " + currPortId.getName());
//			for (String portName : PORT_NAMES) {
//	            if (currPortId.getName().equals(portName)) {
//	                portId = currPortId;
//	                break;
//	            }
//	        }
//		}
//		if (portId == null) {
//	        System.out.println("Could not find COM port.");
//	        return;
//	    }
//
//	    try {
//	        serialPort = (SerialPort) portId.open(this.getClass().getName(),
//	                TIME_OUT);
//	        serialPort.setSerialPortParams(BAUD_RATE,
//	                SerialPort.DATABITS_8,
//	                SerialPort.STOPBITS_1,
//	                SerialPort.PARITY_NONE);
//
//	        // open the streams
//	        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
//	        output = serialPort.getOutputStream();
//
//	        serialPort.addEventListener(this);
//	        serialPort.notifyOnDataAvailable(true);
//	    } catch (Exception e) {
//	        System.err.println(e.toString());
//	    }
//	}
//	
//	@Override
//	public synchronized void serialEvent(SerialPortEvent event) {
//		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			System.out.println("Data is available");
//            try {
//
//                String inputLine=null;
//                if (input.ready()) {
//                    inputLine = input.readLine();
//                    System.out.println("Input: " + inputLine);
//                    //panel.read(inputLine);
//                }
//
//            } catch (Exception e) {
//                System.err.println(e.toString());
//            }
//        }   
//		
//	}
//	
//	public synchronized void send(int b){
//        try{
//            output.write(b);
//        }
//        catch (Exception e) {
//            System.err.println(e.toString());
//        }
//    }
//
//    public synchronized int read(){
//        int b = 0;
//
//        try{
//            b = (int)input.read();
//        }
//        catch (Exception e) {
//            System.err.println(e.toString());
//        }
//        return b;
//    }
//	
//    public synchronized void close() {
//        if (serialPort != null) {
//            serialPort.removeEventListener();
//            serialPort.close();
//        }
//    }
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		mySerialPortRXTX myserial = new mySerialPortRXTX("/dev/tty/ACM0");
//		// myserial.initialize();
//
//	}
//
//}
