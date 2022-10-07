package com.target.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WaterBillServiceStep {
	private double tenantWaterPrice;
	private double guestWaterPrice;
	private int totalWaterUsedByTenant;
	private double corPorateBill;
	private double borewellBill;
	private double totalPrice;
	private int totalWaterConsumedByGuest;

	private static WaterBillServiceStep billServiceStep;;

	public WaterBillServiceStep(double tenantWaterPrice, double guestWaterPrice, int totalWaterUsedByTenant,
			double corPorateBill, double borewellBill, double totalPrice, int totalWaterConsumedByGuest) {
		super();
		this.tenantWaterPrice = tenantWaterPrice;
		this.guestWaterPrice = guestWaterPrice;
		this.totalWaterUsedByTenant = totalWaterUsedByTenant;
		this.corPorateBill = corPorateBill;
		this.borewellBill = borewellBill;
		this.totalPrice = totalPrice;
		this.totalWaterConsumedByGuest = totalWaterConsumedByGuest;
	}

	public WaterBillServiceStep executeAction(String waterBill, WaterBillServiceStep billServiceStep) {
		if (waterBill.contains("ALLOT_WATER")) {
			tenantWaterPrice = billServiceStep.calculateTeantWaterBill(waterBill, billServiceStep);
		}
		if (waterBill.contains("ADD_GUESTS")) {
			guestWaterPrice = calculateGuestWaterBill(waterBill, billServiceStep);
		}
		if (waterBill.contains("BILL")) {
			// System.out.println(totalWaterUsedByTenant+totalWaterConsumedByGuest);
			totalPrice = tenantWaterPrice + guestWaterPrice;
			billServiceStep = new WaterBillServiceStep(0, 0, 0, 0, 0, 0, 0);
			System.out.println((int)(totalWaterUsedByTenant + totalWaterConsumedByGuest) + " " +(int) totalPrice);
		}
		return billServiceStep;

	}

	private double calculateGuestWaterBill(String waterBill, WaterBillServiceStep billServiceStep) {
		// TODO Auto-generated method stub
		String[] arr = waterBill.split(" ");
		String numberOfGuests = arr[1];
		totalWaterConsumedByGuest += Integer.valueOf(numberOfGuests) * WaterBillConstant.TOTAL_DAYS_IN_A_MONTH
				* WaterBillConstant.TOTAL_WATER_CONSUMED_BY_INDIVIDUAL;
		guestWaterPrice = getWaterPriceByRange(totalWaterConsumedByGuest);
		return guestWaterPrice;

	}

	private double getWaterPriceByRange(int totalWaterConsumedByGuest) {
		// TODO Auto-generated method stub

		if (totalWaterConsumedByGuest > WaterBillConstant.TOTAL_WATER_LIMIT_3) {
			guestWaterPrice = getWaterPriceRange4(totalWaterConsumedByGuest);
		} else if (totalWaterConsumedByGuest > WaterBillConstant.TOTAL_WATER_LIMIT_2
				&& totalWaterConsumedByGuest <= WaterBillConstant.TOTAL_WATER_LIMIT_3) {
			guestWaterPrice = getWaterPriceRange3(totalWaterConsumedByGuest);
		} else if (totalWaterConsumedByGuest > WaterBillConstant.TOTAL_WATER_LIMIT_1
				&& totalWaterConsumedByGuest <= WaterBillConstant.TOTAL_WATER_LIMIT_2) {
			guestWaterPrice = guestWaterPriceRange2(totalWaterConsumedByGuest);
		} else {
			guestWaterPrice = totalWaterConsumedByGuest * WaterBillConstant.TANKER_WATER_RANGE_1;
		}
		return guestWaterPrice;
	}

	private double calculateTeantWaterBill(String waterBill, WaterBillServiceStep billServiceStep) {
		// TODO Auto-generated method stub
		String[] arr = waterBill.split(" ");
		String[] splittedString = arr[2].split(":");
		int corporatePercent = Integer.valueOf(splittedString[0]);
		int borewellPercentage = Integer.valueOf(splittedString[1]);
		if (Integer.valueOf(arr[1]) == WaterBillConstant.TENANT_IN_2BHK) {
			billServiceStep.totalWaterUsedByTenant = 3 * WaterBillConstant.TOTAL_WATER_CONSUMED_BY_INDIVIDUAL
					* WaterBillConstant.TOTAL_DAYS_IN_A_MONTH;
		} else {
			billServiceStep.totalWaterUsedByTenant = 5 * WaterBillConstant.TOTAL_WATER_CONSUMED_BY_INDIVIDUAL
					* WaterBillConstant.TOTAL_DAYS_IN_A_MONTH;
		}

		int totalByIndividualSupply = totalWaterUsedByTenant / (corporatePercent + borewellPercentage);
		int corporateWater = corporatePercent * totalByIndividualSupply;
		int borewellWater = borewellPercentage * totalByIndividualSupply;
		corPorateBill = WaterBillConstant.CORPORATE_WATER_PRICE * corporateWater;
		borewellBill = WaterBillConstant.BOREWELL_WATER_PRICE * borewellWater;
		tenantWaterPrice = corPorateBill + borewellBill;
		return tenantWaterPrice;

	}

	private static double guestWaterPriceRange2(int totalWaterConsumedByGuest) {
		// TODO Auto-generated method stub
		double guestWaterPrice = WaterBillConstant.TOTAL_WATER_LIMIT_1 * WaterBillConstant.TANKER_WATER_RANGE_1;
		int remainingWater = totalWaterConsumedByGuest - WaterBillConstant.TOTAL_WATER_LIMIT_1;
		guestWaterPrice += remainingWater * WaterBillConstant.TANKER_WATER_RANGE_2;
		return guestWaterPrice;
	}

	private static double getWaterPriceRange3(int totalWaterConsumedByGuest) {
		// TODO Auto-generated method stub
		double guestWaterPrice = WaterBillConstant.TOTAL_WATER_LIMIT_1 * WaterBillConstant.TANKER_WATER_RANGE_1;
		int remainingWater = totalWaterConsumedByGuest - WaterBillConstant.TOTAL_WATER_LIMIT_1;
		if (remainingWater > WaterBillConstant.TOTAL_WATER_LIMIT_1
				&& remainingWater <= WaterBillConstant.TOTAL_WATER_LIMIT_2) {
			guestWaterPrice += remainingWater * WaterBillConstant.TANKER_WATER_RANGE_2;
		} else {
			remainingWater = remainingWater - WaterBillConstant.TOTAL_WATER_LIMIT_2;
			guestWaterPrice += WaterBillConstant.TOTAL_WATER_LIMIT_2 * WaterBillConstant.TANKER_WATER_RANGE_3;
		}
		return guestWaterPrice;
	}

	private static double getWaterPriceRange4(int totalWaterConsumedByGuest) {
		// TODO Auto-generated method stub
		double guestWaterPrice = WaterBillConstant.TOTAL_WATER_LIMIT_1 * WaterBillConstant.TANKER_WATER_RANGE_1;
		int remainingWater = totalWaterConsumedByGuest - WaterBillConstant.TOTAL_WATER_LIMIT_1;
		guestWaterPrice += WaterBillConstant.TOTAL_WATER_LIMIT_2 * WaterBillConstant.TANKER_WATER_RANGE_2;
		remainingWater = remainingWater - WaterBillConstant.TOTAL_WATER_LIMIT_2;
		if (remainingWater > WaterBillConstant.TOTAL_WATER_LIMIT_2
				&& remainingWater <= WaterBillConstant.TOTAL_WATER_LIMIT_3) {
			guestWaterPrice += remainingWater * WaterBillConstant.TANKER_WATER_RANGE_3;
		} else {
			remainingWater = remainingWater - WaterBillConstant.TOTAL_WATER_LIMIT_3;
			guestWaterPrice += remainingWater * WaterBillConstant.TANKER_WATER_RANGE_4;
		}
		return guestWaterPrice;
	}

}
