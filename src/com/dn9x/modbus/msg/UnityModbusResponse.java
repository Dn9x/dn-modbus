package com.dn9x.modbus.msg;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.msg.ModbusMessageImpl;

public abstract class UnityModbusResponse extends ModbusMessageImpl {

	/**
	 * Utility method to set the raw data of the message. Should not be used
	 * except under rare circumstances.
	 * <p>
	 * 
	 * @param msg
	 *            the <tt>byte[]</tt> resembling the raw modbus response
	 *            message.
	 */
	protected void setMessage(byte[] msg) {
		try {
			readData(new DataInputStream(new ByteArrayInputStream(msg)));
		} catch (IOException ex) {

		}
	}// setMessage

	/**
	 * Factory method creating the required specialized <tt>ModbusResponse</tt>
	 * instance.
	 * 
	 * @param functionCode
	 *            the function code of the response as <tt>int</tt>.
	 * @return a ModbusResponse instance specific for the given function code.
	 */
	public static UnityModbusResponse createModbusResponse(int functionCode, UnityModbusCoupler umc) {
		UnityModbusResponse response = null;

		switch (functionCode) {
	    	case Modbus.READ_MULTIPLE_REGISTERS:
	    		response = new UnityReadMultipleRegistersResponse(umc);
	    		break;
	    	case Modbus.READ_INPUT_DISCRETES:
	    		response = new UnityReadInputDiscretesResponse(umc);
	    		break;
			case Modbus.READ_INPUT_REGISTERS:
				response = new UnityReadInputRegistersResponse(umc);
				break;
			case Modbus.READ_COILS:
				response = new UnityReadCoilsResponse(umc);
				break;
			case Modbus.WRITE_MULTIPLE_REGISTERS:
				response = new UnityWriteMultipleRegistersResponse(umc);
				break;
			case Modbus.WRITE_SINGLE_REGISTER:
				response = new UnityWriteSingleRegisterResponse(umc);
				break;
			case Modbus.WRITE_COIL:
				response = new UnityWriteCoilResponse(umc);
				break;
			case Modbus.WRITE_MULTIPLE_COILS:
				response = new UnityWriteMultipleCoilsResponse(umc);
				break;
			default:
				response = new UnityExceptionResponse();
				break;
		}
		
		
		return response;
	}// createModbusResponse

	  protected void setFunctionCode(int code) {
		  super.setFunctionCode(code);
	    //setChanged(true);
	  }//setFunctionCode
}// class ModbusResponse
