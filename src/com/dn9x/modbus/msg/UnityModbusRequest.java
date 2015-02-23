package com.dn9x.modbus.msg;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.msg.ModbusMessageImpl;

public abstract class UnityModbusRequest extends ModbusMessageImpl {

	/**
	 * Returns the <tt>ModbusResponse</tt> that correlates with this
	 * <tt>ModbusRequest</tt>.
	 * <p>
	 * 
	 * @return the corresponding <tt>ModbusResponse</tt>.
	 * 
	 *         public abstract ModbusResponse getResponse();
	 */

	/**
	 * Returns the <tt>ModbusResponse</tt> that represents the answer to this
	 * <tt>ModbusRequest</tt>.
	 * <p>
	 * The implementation should take care about assembling the reply to this
	 * <tt>ModbusRequest</tt>.
	 * <p>
	 * 
	 * @return the corresponding <tt>ModbusResponse</tt>.
	 */
	public abstract UnityModbusResponse createResponse();

	/**
	 * Factory method for creating exception responses with the given exception
	 * code.
	 * 
	 * @param EXCEPTION_CODE
	 *            the code of the exception.
	 * @return a ModbusResponse instance representing the exception response.
	 */
	public UnityModbusResponse createExceptionResponse(int EXCEPTION_CODE) {
		UnityExceptionResponse response = new UnityExceptionResponse(
				this.getFunctionCode(), EXCEPTION_CODE);
		if (!isHeadless()) {
			response.setTransactionID(this.getTransactionID());
			response.setProtocolID(this.getProtocolID());
			response.setUnitID(this.getUnitID());
		} else {
			response.setHeadless();
		}
		return response;
	}// createExceptionResponse

	/**
	 * Factory method creating the required specialized <tt>ModbusRequest</tt>
	 * instance.
	 * 
	 * @param functionCode
	 *            the function code of the request as <tt>int</tt>.
	 * @return a ModbusRequest instance specific for the given function type.
	 */
	public static UnityModbusRequest createModbusRequest(int functionCode, UnityModbusCoupler umc) {
		UnityModbusRequest request = null;

		switch (functionCode) {
			case Modbus.READ_MULTIPLE_REGISTERS:
				request = new UnityReadMultipleRegistersRequest(umc);
				break;
			case Modbus.READ_INPUT_DISCRETES:
				request = new UnityReadInputDiscretesRequest(umc);
				break;
			case Modbus.READ_INPUT_REGISTERS:
				request = new UnityReadInputRegistersRequest(umc);
				break;
			case Modbus.READ_COILS:
				request = new UnityReadCoilsRequest(umc);
				break;
			case Modbus.WRITE_MULTIPLE_REGISTERS:
				request = new UnityWriteMultipleRegistersRequest(umc);
				break;
			case Modbus.WRITE_SINGLE_REGISTER:
				request = new UnityWriteSingleRegisterRequest(umc);
				break;
			case Modbus.WRITE_COIL:
				request = new UnityWriteCoilRequest(umc);
				break;
			case Modbus.WRITE_MULTIPLE_COILS:
				request = new UnityWriteMultipleCoilsRequest(umc);
				break;
			default:
				request = new UnityIllegalFunctionRequest(functionCode);
				break;
		}
		return request;
	}// createModbusRequest

}// class ModbusRequest
