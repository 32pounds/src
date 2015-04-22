package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;

public class clientLL{

	private Node head;
	private int listCount;

	// Initialize values.
	public clientLL(){
		head = new Node(null, 0);
		listCount = 0;
	}

	public void CreateClient(InetAddress address, int port){
		Node client = new Node(address, port);
		client.SetNext(null);
		Node temp = head;

		if( head != null){
			while(temp.GetNext() != null){
				temp = temp.GetNext();
			}

		} else if ( head == null ){
			head = client;
		}
		temp.SetNext(client);
		listCount++;
	}

	public InetAddress GetIP(int index){
		Node curr = head;
		InetAddress temp = null;
		for(int i = 0; i < listCount && curr.GetNext() != null; i++){
			temp = curr.GetDataIP();
		}
		return temp;
	}

	public int GetPort(int index){
		Node curr = head;
		int tempPort = 0;
		for(int i = 0; i < listCount && curr.GetNext() != null; i++){
			tempPort = curr.GetDataPort();
		}
		return tempPort;
	}

	public void RemoveClient(InetAddress clientAddress){
		Node curr = head;
		Node tail = curr;

		while( curr != null){
			if(curr.GetDataIP() == clientAddress ){
				tail.SetNext(curr.GetNext());
			}else{
				tail = curr;
				curr = curr.GetNext();
			}
		}
	}

	public int Size(){
		return listCount;
	}

	private class Node{
		Node next;
		InetAddress ipAddress;
		int portNum;

		public Node(InetAddress ip, int givenPort){
			ipAddress = ip;
			portNum = givenPort;
			next = null;
		}

		public InetAddress GetDataIP(){
			return ipAddress;
		}

		public int GetDataPort(){
			return portNum;
		}

		public Node GetNext(){
			return next;
		}

		public void SetNext(Node nextNode){
			next = nextNode;
		}
	}
}