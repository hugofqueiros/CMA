package org.projfinal.cma;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class Phonogram extends JFrame {
	
	RealTimeChart rtcp;
	JToolBar toolbar;

	public Phonogram() {
		super();
		setTitle("Phonogram program");
		rtcp = new RealTimeChart("Phonogram");
		toolbar = createToolBar();
		getContentPane().add(rtcp, BorderLayout.CENTER);
		getContentPane().add(toolbar, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		try {
			Thread.sleep(4000); // it should be 2000
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		(new Thread(rtcp)).start();
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent windowevent) {
				rtcp.port.close();
				System.exit(0);
			}
		});
	}
	
	private JToolBar createToolBar() {
		JToolBar tb = new JToolBar(JToolBar.HORIZONTAL);
		
		JButton startBtn = new JButton();
		startBtn.setText("Start");
		startBtn.setToolTipText("Start Serial Comms with arduino");
		startBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rtcp.port.sendStart();
			}
		});
		
		JButton pauseBtn = new JButton();
		pauseBtn.setText("Pause");
		pauseBtn.setToolTipText("Pause Serial Comms");
		pauseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rtcp.port.sendPause();
			}
		});
		
		JButton breakBtn = new JButton();
		breakBtn.setText("Break");
		breakBtn.setToolTipText("Break Serial Comms for 2s");
		breakBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rtcp.port.sendBreak();
			}
		});
		
		JButton stopBtn = new JButton();
		stopBtn.setText("Stop");
		stopBtn.setToolTipText("Stop Serial Comms");
		stopBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rtcp.port.sendStop();
			}
		});
		
		tb.add(startBtn);
		tb.add(pauseBtn);
		tb.add(breakBtn);
		tb.add(stopBtn);
		
		return tb;
	}
	
	public String toString() {
		return "Phonogram Porgram starting!";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Phonogram phonogram = new Phonogram();
		phonogram.toString();
	}

}
