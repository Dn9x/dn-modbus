package com.dn9x.modbus.msg;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.wimpi.modbus.Modbus;

public class UnityIllegalFunctionRequest extends UnityModbusRequest {

	/**
	 * Constructs a new <tt>IllegalFunctionRequest</tt> instance for a given
	 * function code.
	 * 
	 * @param fc
	 *            the function code as <tt>int</tt>.
	 */
	public UnityIllegalFunctionRequest(int fc) {
		setFunctionCode(fc);
	}// constructor

	public UnityModbusResponse createResponse() {
		return this.createExceptionResponse(Modbus.ILLEGAL_FUNCTION_EXCEPTION);
	}// createResponse

	public void writeData(DataOutput dout) throws IOException {
		throw new RuntimeException();
	}// writeData

	public void readData(DataInput din) throws IOException {
		// skip all following bytes
		int length = getDataLength();
		for (int i = 0; i < length; i++) {
			din.readByte();
		}
	}// readData

}// IllegalFunctionRequest
