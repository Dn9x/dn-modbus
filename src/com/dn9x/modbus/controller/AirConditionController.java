package com.dn9x.modbus.controller;


public class AirConditionController extends BaseController implements Runnable{

	@Override
	public void showTest() {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>AirConditionController");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 50000; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("AA运行     " + i);
        }
	}
}
