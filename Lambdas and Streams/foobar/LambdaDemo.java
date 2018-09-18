/**
 * Demonstrates one difference (namely the use of the {@code this} keyword)
 * between lambda expressions and traditional classes, static nested classes,
 * inner classes, and anonymous classes.
 *
 * @see Foo
 * @see Bar
 *
 * @see FunctionalInterface
 */
public class LambdaDemo {

	public static class Baz implements Foo {
		@Override
		public void foo() {
			System.out.println(this.getClass().getTypeName());
		}
	}

	public class Qux implements Foo {
		@Override
		public void foo() {
			System.out.println(this.getClass().getTypeName());
		}
	}

	public void normalClass() {
		Foo foobar = new Bar();
		foobar.foo();
	}

	public void nestedClass() {
		Foo foobar = new Baz();
		foobar.foo();
	}

	public void innerClass() {
		Foo foobar = new Qux();
		foobar.foo();
	}

	public void anonymousClass() {
		Foo foobar = new Foo() {
			@Override
			public void foo() {
				System.out.println(this.getClass().getTypeName());
			}
		};
		foobar.foo();
	}

	public void lambdaExpression() {
		Foo foobar = () -> {
			System.out.println(this.getClass().getTypeName());
		};
		foobar.foo();
	}

	public static void main(String[] args) {
		LambdaDemo demo = new LambdaDemo();
		demo.normalClass();
		demo.nestedClass();
		demo.innerClass();
		demo.anonymousClass();
		demo.lambdaExpression();

		Foo foobar = new Foo() {
			@Override
			public void foo() {
				System.out.println(this.getClass().getTypeName());
			}
		};
		foobar.foo();

		/*
		 * Since we are in a static method, there is no "this" context for the
		 * lambda expression below to reference.
		 */

		Foo foobaz = () -> {
			// System.out.println(this.getClass().getTypeName());
			System.out.println("Foobaz");
		};
		foobaz.foo();

		System.out.println(foobaz.getClass().getTypeName());
	}
}
