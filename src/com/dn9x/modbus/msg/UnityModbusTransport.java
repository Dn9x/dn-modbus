package com.dn9x.modbus.msg;

import java.io.IOException;

import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.msg.ModbusMessage;

public interface UnityModbusTransport {
	/**
	 * Closes the raw input and output streams of this <tt>ModbusTransport</tt>.
	 * <p>
	 * 
	 * @throws IOException
	 *             if a stream cannot be closed properly.
	 */
	public void close() throws IOException;

	/**
	 * Writes a <tt<ModbusMessage</tt> to the output stream of this
	 * <tt>ModbusTransport</tt>.
	 * <p>
	 * 
	 * @param msg
	 *            a <tt>ModbusMessage</tt>.
	 * @throws ModbusIOException
	 *             data cannot be written properly to the raw output stream of
	 *             this <tt>ModbusTransport</tt>.
	 */
	public void writeMessage(ModbusMessage msg) throws ModbusIOException;

	/**
	 * Reads a <tt>ModbusRequest</tt> from the input stream of this
	 * <tt>ModbusTransport<tt>.
	 * <p>
	 * 
	 * @return req the <tt>ModbusRequest</tt> read from the underlying stream.
	 * @throws ModbusIOException
	 *             data cannot be read properly from the raw input stream of
	 *             this <tt>ModbusTransport</tt>.
	 */
	public UnityModbusRequest readRequest() throws ModbusIOException;

	/**
	 * Reads a <tt>ModbusResponse</tt> from the input stream of this
	 * <tt>ModbusTransport<tt>.
	 * <p>
	 * 
	 * @return res the <tt>ModbusResponse</tt> read from the underlying stream.
	 * @throws ModbusIOException
	 *             data cannot be read properly from the raw input stream of
	 *             this <tt>ModbusTransport</tt>.
	 */
	public UnityModbusResponse readResponse() throws ModbusIOException;

}// class ModbusTransport

