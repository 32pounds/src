package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;

public class clientLL{

	private Node head;
	private int listCount;

	// Initialize values.
	public clientLL(){
		head = null;
		listCount = 0;
	}

	public boolean Contains(InetAddress testAddress){
		boolean toReturn = false;
		Node temp = head;

		while(temp != null){
			if( temp.GetDataIP().equals(testAddress) ){
				toReturn = true;
			}
			temp = temp.GetNext();
		}

		return toReturn;
	}

	public Node GetNode(int index){
		boolean toReturn = false;
		Node temp = head;

		for(int i = 0; i < index; i++){
			if( temp == null) return null;

			temp = temp.GetNext();
		}

		return temp;
	}

	public void CreateClient(InetAddress address, int port){
		Node client = new Node(address, port);
		client.SetNext(null);
		Node temp = head;

		if( head != null){
			while(temp.GetNext() != null){
				temp = temp.GetNext();
			}
			temp.SetNext(client);
		} else{
			temp = client;
			head = temp;
		}
		
		listCount++;
	}

	public InetAddress GetIP(int index){
		if(head == null) return null;
		Node curr = head;
		InetAddress temp = null;
		for(int i = 0; i < index; i++){
			curr = curr.GetNext();
			if(curr == null) return null;
		}
		temp = curr.GetDataIP();
		return temp;
	}

	public int GetPort(int index){
		if(head == null) return 0;
		Node curr = head;
		int temp;
		for(int i = 0; i < index; i++){
			curr = curr.GetNext();
			if(curr == null) return 0;
		}
		temp = curr.GetDataPort();
		return temp;
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