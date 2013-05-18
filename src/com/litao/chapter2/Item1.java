package com.litao.chapter2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* Service provider framework sketch */

//Service interface
interface Service {
	public void fun();
}

// Service provider interface
interface Provider {
	Service newService();
}

// Noninstantiable class for service registration and access
class Services {
	// Prevents instantiation
	private Services() {
	}

	// Maps service names to services
	private static final Map<String, Provider> providers = new ConcurrentHashMap<String, Provider>();
	public static final String DEFAULT_PROVIDER_NAME = "<def>";

	// Provider registration API
	public static void registerDefaultProvider(Provider p) {
		registerProvider(DEFAULT_PROVIDER_NAME, p);
	}

	public static void registerProvider(String name, Provider p) {
		providers.put(name, p);
	}

	// Service access API
	public static Service newInstance() {
		return newInstance(DEFAULT_PROVIDER_NAME);
	}

	public static Service newInstance(String name) {
		Provider p = providers.get(name);
		if (p == null)
			throw new IllegalArgumentException("No provider registered with name: " + name);
		return p.newService();
	}

}

class MyService implements Service {
	private MyService() {
	}

	@Override
	public void fun() {
		System.out.println(this + " fun()");
	}

	public static Provider provider = new Provider() {

		@Override
		public Service newService() {
			return new MyService();
		}
	};

}

class MyService2 implements Service {
	private MyService2() {
	}

	@Override
	public void fun() {
		System.out.println(this + " fun()");
	}

	public static Provider provider = new Provider() {

		@Override
		public Service newService() {
			return new MyService2();
		}
	};

}

public class Item1 {

	public static void main(String[] args) {
		Services.registerDefaultProvider(MyService.provider);
		Services.registerProvider("MyService2", MyService2.provider);
		Service service = Services.newInstance();
		Service service2 = Services.newInstance("MyService2");
		service.fun();
		service2.fun();
	}

}
