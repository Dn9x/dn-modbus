package com.dn9x.modbus.msg;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.procimg.DefaultProcessImageFactory;
import net.wimpi.modbus.procimg.ProcessImage;
import net.wimpi.modbus.procimg.ProcessImageFactory;

public class UnityModbusCoupler {


	  //instance attributes
	  private ProcessImage m_ProcessImage;
	  private int m_UnitID = Modbus.DEFAULT_UNIT_ID;
	  private boolean m_Master = true;
	  private ProcessImageFactory m_PIFactory;


	  public UnityModbusCoupler() {
	    m_PIFactory = new DefaultProcessImageFactory();
	  }//constructor

	  /**
	   * Private constructor to prevent multiple
	   * instantiation.
	   * <p/>
	   *
	   * @param procimg a <tt>ProcessImage</tt>.
	   */
	  public UnityModbusCoupler(ProcessImage procimg) {
	    setProcessImage(procimg);
	  }//contructor(ProcessImage)

	  /**
	   * Returns the actual <tt>ProcessImageFactory</tt> instance.
	   *
	   * @return a <tt>ProcessImageFactory</tt> instance.
	   */
	  public ProcessImageFactory getProcessImageFactory() {
	    return m_PIFactory;
	  }//getProcessImageFactory

	 /**
	  * Sets the <tt>ProcessImageFactory</tt> instance.
	  *
	  * @param factory the instance to be used for creating process
	  *        image instances.
	  */
	  public void setProcessImageFactory(ProcessImageFactory factory) {
	    m_PIFactory = factory;
	  }//setProcessImageFactory

	  /**
	   * Returns a reference to the <tt>ProcessImage</tt>
	   * of this <tt>ModbusCoupler</tt>.
	   * <p/>
	   *
	   * @return the <tt>ProcessImage</tt>.
	   */
	  public synchronized ProcessImage getProcessImage() {
	    return m_ProcessImage;
	  }//getProcessImage

	  /**
	   * Sets the reference to the <tt>ProcessImage</tt>
	   * of this <tt>ModbusCoupler</tt>.
	   * <p/>
	   *
	   * @param procimg the <tt>ProcessImage</tt> to be set.
	   */
	  public synchronized void setProcessImage(ProcessImage procimg) {
	    m_ProcessImage = procimg;
	  }//setProcessImage

	  /**
	   * Returns the identifier of this unit.
	   * This identifier is required to be set
	   * for serial protocol slave implementations.
	   *
	   * @return the unit identifier as <tt>int</tt>.
	   */
	  public int getUnitID() {
	    return m_UnitID;
	  }//getUnitID

	  /**
	   * Sets the identifier of this unit, which is needed
	   * to be determined in a serial network.
	   *
	   * @param id the new unit identifier as <tt>int</tt>.
	   */
	  public void setUnitID(int id) {
	    m_UnitID = id;
	  }//setUnitID

	  /**
	   * Tests if this instance is a master device.
	   *
	   * @return true if master, false otherwise.
	   */
	  public boolean isMaster() {
	    return m_Master;
	  }//isMaster

	  /**
	   * Tests if this instance is not a master device.
	   *
	   * @return true if slave, false otherwise.
	   */
	  public boolean isSlave() {
	    return !m_Master;
	  }//isSlave

	  /**
	   * Sets this instance to be or not to be
	   * a master device.
	   *
	   * @param master true if master device, false otherwise.
	   */
	  public void setMaster(boolean master) {
	    m_Master = master;
	  }//setMaster

}
