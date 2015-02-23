package com.dn9x.modbus.msg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.io.BytesInputStream;
import net.wimpi.modbus.msg.ModbusMessage;
import net.wimpi.modbus.util.ModbusUtil;

public class UnityModbusTCPTransport implements UnityModbusTransport {

	// instance attributes
	private DataInputStream m_Input; // input stream
	private DataOutputStream m_Output; // output stream
	private BytesInputStream m_ByteIn;
	private UnityModbusCoupler m_umc;

	/**
	 * Constructs a new <tt>ModbusTransport</tt> instance, for a given
	 * <tt>Socket</tt>.
	 * <p>
	 * 
	 * @param socket
	 *            the <tt>Socket</tt> used for message transport.
	 */
	public UnityModbusTCPTransport(Socket socket, UnityModbusCoupler umc) {
		m_umc = umc;
		try {
			setSocket(socket);
		} catch (IOException ex) {
			if (Modbus.debug)
				System.out.println("ModbusTCPTransport::Socket invalid.");
			// @commentstart@
			throw new IllegalStateException("Socket invalid.");
			// @commentend@
		}
	}// constructor

	/**
	 * Sets the <tt>Socket</tt> used for message transport and prepares the
	 * streams used for the actual I/O.
	 * 
	 * @param socket
	 *            the <tt>Socket</tt> used for message transport.
	 * @throws IOException
	 *             if an I/O related error occurs.
	 */
	public void setSocket(Socket socket) throws IOException {
		prepareStreams(socket);
	}// setSocket

	public void close() throws IOException {
		m_Input.close();
		m_Output.close();
	}// close

	public void writeMessage(ModbusMessage msg) throws ModbusIOException {
		try {
			msg.writeTo((DataOutput) m_Output);
			m_Output.flush();
			// write more sophisticated exception handling
		} catch (Exception ex) {
			throw new ModbusIOException("I/O exception - failed to write.");
		}
	}// write

	public UnityModbusRequest readRequest() throws ModbusIOException {

		// System.out.println("readRequest()");
		try {

			UnityModbusRequest req = null;
			synchronized (m_ByteIn) {
				// use same buffer
				byte[] buffer = m_ByteIn.getBuffer();

				// read to byte length of message
				if (m_Input.read(buffer, 0, 6) == -1) {
					throw new EOFException(
							"Premature end of stream (Header truncated).");
				}
				// extract length of bytes following in message
				int bf = ModbusUtil.registerToShort(buffer, 4);
				// read rest
				if (m_Input.read(buffer, 6, bf) == -1) {
					throw new ModbusIOException(
							"Premature end of stream (Message truncated).");
				}
				m_ByteIn.reset(buffer, (6 + bf));
				m_ByteIn.skip(7);
				int functionCode = m_ByteIn.readUnsignedByte();
				m_ByteIn.reset();
				req = UnityModbusRequest.createModbusRequest(functionCode, m_umc);
				req.readFrom(m_ByteIn);
			}
			return req;
			/*
			 * int transactionID = m_Input.readUnsignedShort(); int protocolID =
			 * m_Input.readUnsignedShort(); int dataLength =
			 * m_Input.readUnsignedShort(); if (protocolID !=
			 * Modbus.DEFAULT_PROTOCOL_ID || dataLength > 256) { throw new
			 * ModbusIOException(); } int unitID = m_Input.readUnsignedByte();
			 * int functionCode = m_Input.readUnsignedByte(); ModbusRequest
			 * request = ModbusRequest.createModbusRequest(functionCode,
			 * m_Input, false); if (request instanceof IllegalFunctionRequest) {
			 * //skip rest of bytes for (int i = 0; i < dataLength - 2; i++) {
			 * m_Input.readByte(); } } //set read parameters
			 * request.setTransactionID(transactionID);
			 * request.setProtocolID(protocolID); request.setUnitID(unitID);
			 * return request;
			 */
		} catch (EOFException eoex) {
			throw new ModbusIOException(true);
		} catch (SocketException sockex) {
			// connection reset by peer, also EOF
			throw new ModbusIOException(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ModbusIOException("I/O exception - failed to read.");
		}
	}// readRequest

	public UnityModbusResponse readResponse() throws ModbusIOException {
		// System.out.println("readResponse()");

		try {

			UnityModbusResponse res = null;
			synchronized (m_ByteIn) {
				// use same buffer
				byte[] buffer = m_ByteIn.getBuffer();

				// read to byte length of message
				if (m_Input.read(buffer, 0, 6) == -1) {
					throw new ModbusIOException(
							"Premature end of stream (Header truncated).");
				}
				// extract length of bytes following in message
				int bf = ModbusUtil.registerToShort(buffer, 4);
				// read rest
				if (m_Input.read(buffer, 6, bf) == -1) {
					throw new ModbusIOException(
							"Premature end of stream (Message truncated).");
				}
				m_ByteIn.reset(buffer, (6 + bf));
				m_ByteIn.skip(7);
				int functionCode = m_ByteIn.readUnsignedByte();
				m_ByteIn.reset();
				res = UnityModbusResponse.createModbusResponse(functionCode, m_umc);
				res.readFrom(m_ByteIn);
			}
			return res;
			/*
			 * try { int transactionID = m_Input.readUnsignedShort();
			 * //System.out.println("Read tid="+transactionID); int protocolID =
			 * m_Input.readUnsignedShort();
			 * //System.out.println("Read pid="+protocolID); int dataLength =
			 * m_Input.readUnsignedShort();
			 * //System.out.println("Read length="+dataLength); int unitID =
			 * m_Input.readUnsignedByte();
			 * //System.out.println("Read uid="+unitID); int functionCode =
			 * m_Input.readUnsignedByte();
			 * //System.out.println("Read fc="+functionCode); ModbusResponse
			 * response = ModbusResponse.createModbusResponse(functionCode,
			 * m_Input, false); if (response instanceof ExceptionResponse) {
			 * //skip rest of bytes for (int i = 0; i < dataLength - 2; i++) {
			 * m_Input.readByte(); } } //set read parameters
			 * response.setTransactionID(transactionID);
			 * response.setProtocolID(protocolID); response.setUnitID(unitID);
			 * return response;
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ModbusIOException("I/O exception - failed to read.");
		}
	}// readResponse

	/**
	 * Prepares the input and output streams of this <tt>ModbusTCPTransport</tt>
	 * instance based on the given socket.
	 * 
	 * @param socket
	 *            the socket used for communications.
	 * @throws IOException
	 *             if an I/O related error occurs.
	 */
	private void prepareStreams(Socket socket) throws IOException {

		m_Input = new DataInputStream(new BufferedInputStream(
				socket.getInputStream()));
		m_Output = new DataOutputStream(new BufferedOutputStream(
				socket.getOutputStream()));
		m_ByteIn = new BytesInputStream(Modbus.MAX_MESSAGE_LENGTH);
	}// prepareStreams

}// class ModbusTCPTransport
