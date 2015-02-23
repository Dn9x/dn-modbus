package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;

public class UnityExceptionResponse extends UnityModbusResponse {

	// instance attributes
	private int m_ExceptionCode = -1;

	/**
	 * Constructs a new <tt>ExceptionResponse</tt> instance.
	 */
	public UnityExceptionResponse() {
		// exception code, unitid and function code not counted.
		setDataLength(1);
	}// constructor

	/**
	 * Constructs a new <tt>ExceptionResponse</tt> instance with a given
	 * function code. Adds the exception offset automatically.
	 * 
	 * @param fc
	 *            the function code as <tt>int</tt>.
	 */
	public UnityExceptionResponse(int fc) {
		// unitid and function code not counted.
		setDataLength(1);
		setFunctionCode(fc + Modbus.EXCEPTION_OFFSET);
	}// constructor

	/**
	 * Constructs a new <tt>ExceptionResponse</tt> instance with a given
	 * function code and an exception code. The function code will be
	 * automatically increased with the exception offset.
	 * 
	 * 
	 * @param fc
	 *            the function code as <tt>int</tt>.
	 * @param exc
	 *            the exception code as <tt>int</tt>.
	 */
	public UnityExceptionResponse(int fc, int exc) {
		// exception code, unitid and function code not counted.
		setDataLength(1);
		setFunctionCode(fc + Modbus.EXCEPTION_OFFSET);
		m_ExceptionCode = exc;
	}// constructor

	/**
	 * Returns the Modbus exception code of this <tt>ExceptionResponse</tt>.
	 * <p>
	 * 
	 * @return the exception code as <tt>int</tt>.
	 */
	public int getExceptionCode() {
		return m_ExceptionCode;
	}// getExceptionCode

	public void writeData(DataOutput dout) throws IOException {
		dout.writeByte(getExceptionCode());
	}// writeData

	public void readData(DataInput din) throws IOException {
		m_ExceptionCode = din.readUnsignedByte();
	}// readData

}// ExceptionResponse
