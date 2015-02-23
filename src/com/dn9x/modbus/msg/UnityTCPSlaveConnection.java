package com.dn9x.modbus.msg;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import net.wimpi.modbus.Modbus;

public class UnityTCPSlaveConnection {

	  //instance attributes
	  private Socket m_Socket;
	  private int m_Timeout = Modbus.DEFAULT_TIMEOUT;
	  private boolean m_Connected;
	  private UnityModbusTCPTransport m_ModbusTransport;
	  private UnityModbusCoupler m_umc;

	  /**
	   * Constructs a <tt>TCPSlaveConnection</tt> instance
	   * using a given socket instance.
	   *
	   * @param socket the socket instance to be used for communication.
	   */
	  public UnityTCPSlaveConnection(Socket socket, UnityModbusCoupler umc) {
		  m_umc = umc;
	    try {
	      setSocket(socket);
	    } catch (IOException ex) {
	       if(Modbus.debug) System.out.println("TCPSlaveConnection::Socket invalid.");
	      //@commentstart@
	      throw new IllegalStateException("Socket invalid.");
	      //@commentend@
	    }
	  }//constructor

	  /**
	   * Closes this <tt>TCPSlaveConnection</tt>.
	   */
	  public void close() {
	    if(m_Connected) {
	      try {
	        m_ModbusTransport.close();
	        m_Socket.close();
	      } catch (IOException ex) {
	        if(Modbus.debug) ex.printStackTrace();
	      }
	      m_Connected = false;
	    }
	  }//close

	  /**
	   * Returns the <tt>ModbusTransport</tt> associated with this
	   * <tt>TCPMasterConnection</tt>.
	   *
	   * @return the connection's <tt>ModbusTransport</tt>.
	   */
	  public UnityModbusTransport getModbusTransport() {
	    return m_ModbusTransport;
	  }//getIO

	  /**
	   * Prepares the associated <tt>ModbusTransport</tt> of this
	   * <tt>TCPMasterConnection</tt> for use.
	   *
	   * @param socket the socket to be used for communication.
	   * @throws IOException if an I/O related error occurs.
	   */
	  private void setSocket(Socket socket) throws IOException {
	    m_Socket = socket;
	    if (m_ModbusTransport == null) {
	      m_ModbusTransport = new UnityModbusTCPTransport(m_Socket, m_umc);
	    } else {
	      m_ModbusTransport.setSocket(m_Socket);
	    }
	    m_Connected = true;
	  }//prepareIO

	  /**
	   * Returns the timeout for this <tt>TCPMasterConnection</tt>.
	   *
	   * @return the timeout as <tt>int</tt>.
	   */
	  public int getTimeout() {
	    return m_Timeout;
	  }//getReceiveTimeout

	  /**
	   * Sets the timeout for this <tt>TCPSlaveConnection</tt>.
	   *
	   * @param timeout the timeout as <tt>int</tt>.
	   */
	  public void setTimeout(int timeout) {
	    m_Timeout = timeout;
	    try {
	      m_Socket.setSoTimeout(m_Timeout);
	    } catch (IOException ex) {
	      //handle?
	    }
	  }//setReceiveTimeout

	  /**
	   * Returns the destination port of this
	   * <tt>TCPMasterConnection</tt>.
	   *
	   * @return the port number as <tt>int</tt>.
	   */
	  public int getPort() {
	    return m_Socket.getLocalPort();
	  }//getPort

	  /**
	   * Returns the destination <tt>InetAddress</tt> of this
	   * <tt>TCPMasterConnection</tt>.
	   *
	   * @return the destination address as <tt>InetAddress</tt>.
	   */
	  public InetAddress getAddress() {
	    return m_Socket.getLocalAddress();
	  }//getAddress

	  /**
	   * Tests if this <tt>TCPMasterConnection</tt> is connected.
	   *
	   * @return <tt>true</tt> if connected, <tt>false</tt> otherwise.
	   */
	  public boolean isConnected() {
	    return m_Connected;
	  }//isConnected

	}//class TCPSlaveConnection
