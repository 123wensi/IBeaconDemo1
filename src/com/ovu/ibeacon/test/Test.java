package com.ovu.ibeacon.test;

import java.awt.EventQueue;

import com.ovu.ibeacon.view.SceneBackgroudFrame;
import com.ovu.ibeacon.view.SceneBackgroundPanel;
import com.ovu.ibeacon.view.SceneBackgroundPanel2;

/**
 * �����࣬������������в���
 * @author zz
 *
 */
public class Test {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				SceneBackgroudFrame sbg = new SceneBackgroudFrame();
				sbg.setContentPane(new SceneBackgroundPanel2());
//				sbg.setContentPane(new JButton("Change Color"));
			}
			
		});
		
	}

}
