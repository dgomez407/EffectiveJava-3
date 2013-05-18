package com.litao.basic.innerclass;

interface Iterator {
	public Object current();

	public boolean next();
}

class Sequence {
	private Object[] items;
	private int next = 0;

	public Sequence(int size) {
		items = new Object[size];
	}

	public void add(Object x) {
		if (next < items.length) {
			items[next++] = x;
		}
	}

	private class SequenceIterator implements Iterator {
		private int i = -1;

		@Override
		public Object current() {
			return items[i];
		}

		@Override
		public boolean next() {
			i++;
			if (i >= items.length) {
				return false;
			} else {
				return true;
			}
		}

	}

	public Iterator iterator() {
		return new SequenceIterator();
	}

	public static void main(String[] args) {
		Sequence sequence = new Sequence(10);
		for (int i = 0; i < 10; i++) {
			sequence.add(i);
		}

		Iterator iterator = sequence.iterator();
		while (iterator.next()) {
			System.out.print(iterator.current() + " ");
		}
	}
}