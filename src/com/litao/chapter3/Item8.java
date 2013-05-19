package com.litao.chapter3;

import java.awt.Color;

class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		// 使用getClass()判断obj的类型
		if (obj == null || obj.getClass() != getClass())
			return false;

		Point p = (Point) obj;
		// float和double域需使用Float.compare()和Double.compare()进行特殊处理
		return Double.compare(x, p.x) == 0 && Double.compare(y, p.y) == 0;
	}
}

class ColorPoint {
	private final Point point;
	private final Color color;

	public ColorPoint(int x, int y, Color color) {
		point = new Point(x, y);
		this.color = color;
	}

	public Point asPoint() {
		return point;
	}

	@Override
	public boolean equals(Object obj) {
		// 1. 若obj为this，返回true
		if (obj == this)
			return true;

		// 2. 保证obj为ColorPoint类型（此处若obj为null，则instanceof返回false）
		if (!(obj instanceof ColorPoint))
			return false;

		// 3. obj转为ColorPoint
		ColorPoint cp = (ColorPoint) obj;

		// 4. 检查每一个字段。有些对象，字段为null是允许的，则采用已下方法进行判断
		// a. 字段为引用类型 (field == obj.field || field != null && field.equals(obj.field))
		// b. 字段为非float、double的基本类型 field == obj.field
		// c. 字段为float、double类型 Double.compare(field, obj.field) == 0
		return (point == cp.point || point != null && point.equals(cp.point)) && (color == cp.color || color != null && color.equals(cp.color));
	}
}

public class Item8 {

	public static void main(String[] args) {
		ColorPoint cp1 = new ColorPoint(1, 2, Color.BLUE);
		ColorPoint cp2 = new ColorPoint(1, 2, Color.BLUE);
		ColorPoint cp3 = new ColorPoint(1, 3, Color.RED);

		System.out.println(String.format("%s, %s, %b", cp1, cp1, cp1.equals(cp1)));
		System.out.println(String.format("%s, %s, %b", cp1, cp2, cp1.equals(cp2)));
		System.out.println(String.format("%s, %s, %b", cp2, cp1, cp2.equals(cp1)));
		System.out.println(String.format("%s, %s, %b", cp1, cp3, cp1.equals(cp3)));
	}

}
