package com.target.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Geektrust {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			WaterBillServiceStep billServiceStep = new WaterBillServiceStep(0, 0, 0, 0, 0, 0, 0);
			// Scanner reader = new Scanner(new FileInputStream(args[0]));
			testDataProvider(billServiceStep,args[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDataProvider(WaterBillServiceStep billServiceStep, String args) throws FileNotFoundException {
		
		BufferedReader br = new BufferedReader(new FileReader(args));
		String st;

		try {
			while ((st = br.readLine()) != null) {
				billServiceStep = billServiceStep.executeAction(st, billServiceStep);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
