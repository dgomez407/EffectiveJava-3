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

		// ʹ��getClass()�ж�obj������
		if (obj == null || obj.getClass() != getClass())
			return false;

		Point p = (Point) obj;
		// float��double����ʹ��Float.compare()��Double.compare()�������⴦��
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
		// 1. ��objΪthis������true
		if (obj == this)
			return true;

		// 2. ��֤objΪColorPoint���ͣ��˴���objΪnull����instanceof����false��
		if (!(obj instanceof ColorPoint))
			return false;

		// 3. objתΪColorPoint
		ColorPoint cp = (ColorPoint) obj;

		// 4. ���ÿһ���ֶΡ���Щ�����ֶ�Ϊnull������ģ���������·��������ж�
		// a. �ֶ�Ϊ�������� (field == obj.field || field != null && field.equals(obj.field))
		// b. �ֶ�Ϊ��float��double�Ļ������� field == obj.field
		// c. �ֶ�Ϊfloat��double���� Double.compare(field, obj.field) == 0
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
