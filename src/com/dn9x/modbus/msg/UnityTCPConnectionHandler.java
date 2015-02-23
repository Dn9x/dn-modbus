package com.dn9x.modbus.msg;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusIOException;

public class UnityTCPConnectionHandler implements Runnable {

	  private UnityTCPSlaveConnection m_Connection;
	  private UnityModbusTransport m_Transport;
	  private UnityModbusCoupler m_umc;

	  /**
	   * Constructs a new <tt>TCPConnectionHandler</tt> instance.
	   *
	   * @param con an incoming connection.
	   */
	  public UnityTCPConnectionHandler(UnityTCPSlaveConnection con, UnityModbusCoupler umc) {
		  m_umc = umc;
	    setConnection(con);
	  }//constructor

	  /**
	   * Sets a connection to be handled by this <tt>
	   * TCPConnectionHandler</tt>.
	   *
	   * @param con a <tt>TCPSlaveConnection</tt>.
	   */
	  public void setConnection(UnityTCPSlaveConnection con) {
	    m_Connection = con;
	    m_Transport = m_Connection.getModbusTransport();
	  }//setConnection

	  public void run() {
	    try {
	      do {
	        //1. read the request
	        UnityModbusRequest request = m_Transport.readRequest();
	        //System.out.println("Request:" + request.getHexMessage());
	        UnityModbusResponse response = null;

	        //test if Process image exists
	        if (m_umc.getProcessImage() == null) {
	          response =
	              request.createExceptionResponse(Modbus.ILLEGAL_FUNCTION_EXCEPTION);
	        } else {
	          response = request.createResponse();
	        }
	        /*DEBUG*/
	        if (Modbus.debug) System.out.println("Request:" + request.getHexMessage());
	        if (Modbus.debug) System.out.println("Response:" + response.getHexMessage());

	        //System.out.println("Response:" + response.getHexMessage());
	        m_Transport.writeMessage(response);
	      } while (true);
	    } catch (ModbusIOException ex) {
	      if (!ex.isEOF()) {
	        //other troubles, output for debug
	        ex.printStackTrace();
	      }
	    } finally {
	      try {
	        m_Connection.close();
	      } catch (Exception ex) {
	        //ignore
	      }

	    }
	  }//run

	}//TCPConnectionHandler

