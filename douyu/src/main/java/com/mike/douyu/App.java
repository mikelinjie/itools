package com.mike.douyu;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

/**
 * Hello world!
 *
 */
public class App {
	static String regex = "/content@=(.*?)/";
	static Pattern pattern = Pattern.compile(regex);

	public static void main(String[] args) throws Exception {
		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		jpcap.NetworkInterface eth = null;
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].addresses[1].address.toString().equals(
					"/192.168.1.144")) {
				eth = devices[i];
			}
		}
		JpcapCaptor jpcap = JpcapCaptor.openDevice(eth, 65535, false, 20);
		jpcap.setFilter("host 119.188.98.99", true);
		jpcap.loopPacket(-1, new MyJpcapReciver());
	}

	static class MyJpcapReciver implements PacketReceiver {

		@Override
		public void receivePacket(Packet packet) {
			TCPPacket p = (TCPPacket) packet;

			String data = "";
			try {
				data = new String(p.data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("xxxxxxxxxxxxxx--------------------------");
			}
			if (data.contains("content")) {
				Matcher m = pattern.matcher(data);
				m.find();
				System.out.println(m.group(1));
			}
		}

	}

}
