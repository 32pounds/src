package com.multi;

import java.net.*;
import java.io.*;
import java.util.*;
import com.comms.GameID;

public class clientLL{

	private Node head;
	private int listCount;

	// Initialize values.
	public clientLL(){
		head = null;
		listCount = 0;
	}

	public boolean Contains(InetAddress address){
		int index = GetByAddress(address);
		return (index != -1);
	}

	public int GetByAddress(InetAddress testAddress){
		boolean toReturn = false;
		Node temp 		 = head;
		int index 		 = 0;
		while(temp != null){
			if( temp.GetDataIP().equals(testAddress) ){
				return index;
			}
			temp = temp.GetNext();
			index++;
		}
		return -1;
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

	public void CreateClient(InetAddress address, int port, GameID player){
		Node client = new Node(address, port, player);
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

	public GameID GetPlayerID(int index){
		if(head == null) return null;
		Node curr = head;
		InetAddress temp = null;
		for(int i = 0; i < index; i++){
			curr = curr.GetNext();
			if(curr == null) return null;
		}
		return curr.GetPlayerID();
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
		if(head == null) return;
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
		GameID pairedPlayer;
		int portNum;

		public Node(InetAddress ip, int givenPort, GameID paired){
			ipAddress = ip;
			portNum = givenPort;
			next = null;
			pairedPlayer = paired;
		}

		public InetAddress GetDataIP(){
			return ipAddress;
		}

		public int GetDataPort(){
			return portNum;
		}

		public GameID GetPlayerID(){
			return pairedPlayer;
		}

		public Node GetNext(){
			return next;
		}

		public void SetNext(Node nextNode){
			next = nextNode;
		}
	}
}
