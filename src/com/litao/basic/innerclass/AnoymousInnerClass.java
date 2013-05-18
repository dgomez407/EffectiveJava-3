package com.litao.basic.innerclass;

interface Destination {
	public String readLabel();
}

class Parcel {
	public Destination destination(final String dest, final float price) {
		return new Destination() {
			private int cost;
			private String label = dest;

			// instance initialization for each object
			{
				cost = Math.round(price);
				if(cost > 100) {
					System.out.println("Over Budget!");
				}
			}

			@Override
			public String readLabel() {
				return label;
			}
		};
	}
}

public class AnoymousInnerClass {

	public static void main(String[] args) {
		Parcel parcel = new Parcel();
		Destination destination = parcel.destination("beijing", 101.295F);
		System.out.println(destination.readLabel());
	}

}
