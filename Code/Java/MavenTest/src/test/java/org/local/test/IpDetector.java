package org.local.test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class IpDetector
{

	public static void main(String args[]) throws SocketException
	{
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface netint : Collections.list(nets))
			displayInterfaceInformation(netint);
	}

	static void displayInterfaceInformation(NetworkInterface netint) throws SocketException
	{
		List<InetAddress> inetAddresses = Collections.list(netint.getInetAddresses());
		if (inetAddresses.size() < 2) return;
		System.out.println(netint.getDisplayName());
		System.out.println(netint.getName());
		for (InetAddress inetAddress : (inetAddresses))
		{
			System.out.println(inetAddress);
		}
		System.out.println();
	}

}
